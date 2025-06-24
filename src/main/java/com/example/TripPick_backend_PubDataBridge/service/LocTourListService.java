package com.example.TripPick_backend_PubDataBridge.service;

import com.example.TripPick_backend_PubDataBridge.domain.LocTourList;
import com.example.TripPick_backend_PubDataBridge.domain.event.LocTourListEvent;
import com.example.TripPick_backend_PubDataBridge.dto.request.LocTourListRequest;
import com.example.TripPick_backend_PubDataBridge.dto.response.LocTourListResponse;
import com.example.TripPick_backend_PubDataBridge.event.producer.KafkaMessageProducer;
import com.example.TripPick_backend_PubDataBridge.repository.LocTourListRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocTourListService {
    private final LocTourListRepository locTourListRepository;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;
    private final KafkaMessageProducer kafkaMessageProducer;

    String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

    @Scheduled(cron = "*/10 * * * * *")
    public void autoFetchAndSave() {
        String serviceKey = "service key";
        //12: 관광지, 14: 문화시설, 15: 축제공연행사 25: 여행코스
        List<String> contentTypeIds = List.of("12", "14", "15", "25");

        for (String contentTypeId : contentTypeIds) {
            try {
                fetchAndSaveTourList(serviceKey, contentTypeId);
                log.info("리스트 전송 완료: contentId={}", contentTypeId);
            } catch (Exception e) {
                log.error("contentTypeId={} 리스트 전송 실패: ", contentTypeId, e);
            }
        }
    }

    @Transactional
    public void fetchAndSaveTourList(String serviceKey, String contentTypeID) {
        String rawUrl = "https://apis.data.go.kr/B551011/KorService2/areaBasedList2";
        String url = UriComponentsBuilder.newInstance()
                .uri(URI.create(rawUrl))
                .queryParam("serviceKey", serviceKey)
                .queryParam("MobileApp", "AppTest")
                .queryParam("MobileOS", "ETC")
                .queryParam("contentTypeId", contentTypeID)
                //.queryParam("modifiedtime", today)
                .queryParam("_type", "json")
                .build()
                .toUriString();

        log.info("요청 URL: {}", url);

        LocTourListResponse response = null;

        try {
            response = restTemplate.getForObject(url, LocTourListResponse.class);

            if (response != null) {
                log.info("API 응답 객체 (JSON): {}", objectMapper.writeValueAsString(response));
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

//        if (response == null
//                || response.getResponse() == null
//                || response.getResponse().getBody() == null
//                || response.getResponse().getBody().getItems() == null
//                || response.getResponse().getBody().getItems().getItem() == null) {
//            log.warn("API 응답 데이터가 없습니다.");
//            return;
//        }

        for (LocTourListResponse.Item dto : response.getResponse().getBody().getItems().getItem()) {
            if (locTourListRepository.existsById(dto.getContentid())) {
                log.info("이미 존재하는 데이터입니다. contentId={}", dto.getContentid());
                continue;
            }

            LocTourList entity = LocTourList.builder()
                    .contentid(dto.getContentid())
                    .contenttypeid(dto.getContenttypeid())
                    .addr1(dto.getAddr1())
                    .addr2(dto.getAddr2())
                    .areacode(dto.getAreacode())
                    .cat1(dto.getCat1())
                    .cat2(dto.getCat2())
                    .cat3(dto.getCat3())
                    .firstimage(dto.getFirstimage())
                    .firstimage2(dto.getFirstimage2())
                    .tel(dto.getTel())
                    .title(dto.getTitle())
                    .zipcode(dto.getZipcode())
                    .lDongRegnCd(dto.getLDongRegnCd())
                    .lDongSignguCd(dto.getLDongSigunguCd())
                    .build();

            entity.setCreatedTimeFromString(dto.getCreatedtime());
            entity.setModifiedTimeFromString(dto.getModifiedtime());

            LocTourListEvent event = LocTourListEvent.fromEntity("list", entity);

            log.info("전송 시도: contentId={}", dto.getContentid());
            kafkaMessageProducer.send(LocTourListEvent.Topic, event);
            log.info("전송 완료: contentId={}", dto.getContentid());
        }
    }

    public List<LocTourList> searchByConditions(LocTourListRequest cond) {
        Specification<LocTourList> spec = (root, query, cb) -> cb.conjunction();
        //관광타입ID
        if (cond.getContentTypeId() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("contenttypeid"), cond.getContentTypeId()));
        }
        //대분류
        if (cond.getCat1() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("cat1"), cond.getCat1()));
        }
        //중분류
        if (cond.getCat2() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("cat2"), cond.getCat2()));
        }
        //소분류
        if (cond.getCat3() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("cat3"), cond.getCat3()));
        }
        //법정동 시도 코드
        if (cond.getLDongRegnCd() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("lDongRegnCd"), cond.getLDongRegnCd()));
        }
        //법정동 시군구 코드
        if (cond.getLDongSignguCd() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("lDongSignguCd"), cond.getLDongSignguCd()));
        }

        return locTourListRepository.findAll(spec);
    }

}