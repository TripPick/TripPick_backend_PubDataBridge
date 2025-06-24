package com.example.TripPick_backend_PubDataBridge.service;

import com.example.TripPick_backend_PubDataBridge.domain.DetailCommon;
import com.example.TripPick_backend_PubDataBridge.domain.LocTourList;
import com.example.TripPick_backend_PubDataBridge.dto.response.DetailCommonResponse;
import com.example.TripPick_backend_PubDataBridge.repository.DetailCommonRepository;
import com.example.TripPick_backend_PubDataBridge.repository.LocTourListRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DetailCommonService {
    private final LocTourListRepository locTourListRepository;
    private final DetailCommonRepository detailCommonRepository;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    @Scheduled(cron = "*/10 * * * * *")
    public void fetchAndSaveDetails() {
        log.info("==== [스케줄러 실행됨] ====");
        String serviceKey = "ggKSFIY8e2VWWAtrAJR9X0tpHxfG5xL/viLjukhurELWWaVwnS9PVma70rnMTdytv8mG1uY4qL59cWOMPmxrWA==";

        List<LocTourList> locTourLists = locTourListRepository.findAll();

        for (LocTourList listItem : locTourLists) {
            try {
                String contentId = listItem.getContentid();

                fetchAndSaveDetailCommon(serviceKey, contentId);
                log.info("상세정보 전송 완료: contentId={}", contentId);
            } catch (Exception e) {
                log.error("상세정보 전송 실패: contentId={}, error={}", listItem.getContentid(), e.getMessage());
            }
        }
    }

    @Transactional
    public void fetchAndSaveDetailCommon(String serviceKey, String contentId) {
        String rawUrl = "https://apis.data.go.kr/B551011/KorService2/dtailCommon2";
        String url = UriComponentsBuilder.newInstance()
                .uri(URI.create(rawUrl))
                .queryParam("serviceKey", serviceKey)
                .queryParam("MobileApp", "AppTest")
                .queryParam("MobileOS", "ETC")
                .queryParam("contentId", contentId)
                .queryParam("_type", "json")
                .build()
                .toUriString();

        log.info("요청 URL: {}", url);

        DetailCommonResponse response = null;

        try {
            response = restTemplate.getForObject(url, DetailCommonResponse.class);

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
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        for (DetailCommonResponse.Item dto : response.getResponse().getBody().getItems().getItem()) {

            String created = dto.getCreatedtime();
            String modified = dto.getModifiedtime();

            //createdtime 또는 modifiedtime 중 하나라도 오늘 날짜와 같으면 처리
            boolean isCreatedToday = created != null && created.startsWith(today);
            boolean isModifiedToday = modified != null && modified.startsWith(today);

            if (!(isCreatedToday || isModifiedToday)) {
                continue; //오늘 생성 또는 수정된 게 아니면 스킵
            }

            if (detailCommonRepository.existsById(dto.getContentid())) {
                log.info("이미 존재하는 데이터입니다. contentId={}", dto.getContentid());
                continue;
            }

            DetailCommon entity = DetailCommon.builder()
                    .contentid(dto.getContentid())
                    .contenttypeid(dto.getContenttypeid())
                    .title(dto.getTitle())
                    .tel(dto.getTel())
                    .telname(dto.getTelname())
                    .homepage(dto.getHomepage())
                    .firstimage(dto.getFirstimage())
                    .firstimage2(dto.getFirstimage2())
                    .cpyrhtDivCd(dto.getCpyrhtDivCd())
                    .areacode(dto.getAreacode())
                    .lDongRegnCd(dto.getLDongRegnCd())
                    .lDongSigunguCd(dto.getLDongSigunguCd())
                    .lclsSystm1(dto.getLclsSystm1())
                    .lclsSystm2(dto.getLclsSystm2())
                    .lclsSystm3(dto.getLclsSystm3())
                    .cat1(dto.getCat1())
                    .cat2(dto.getCat2())
                    .cat3(dto.getCat3())
                    .addr1(dto.getAddr1())
                    .addr2(dto.getAddr2())
                    .mapx(dto.getMapx())
                    .mapy(dto.getMapy())
                    .mlevel(dto.getMlevel())
                    .overview(dto.getOverview())
                    .build();

            entity.setCreatedTimeFromString(created);
            entity.setModifiedTimeFromString(modified);

            log.info("전송 시도: contentId={}", dto.getContentid());
            // Kafka
            log.info("전송 완료: contentId={}", dto.getContentid());
        }
    }
}
