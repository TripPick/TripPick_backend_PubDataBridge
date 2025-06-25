package com.example.TripPick_backend_PubDataBridge.service;

import com.example.TripPick_backend_PubDataBridge.config.KafkaConfig;
import com.example.TripPick_backend_PubDataBridge.config.PropertiesConfig;
import com.example.TripPick_backend_PubDataBridge.domain.Search;
import com.example.TripPick_backend_PubDataBridge.domain.event.SearchEvent;
import com.example.TripPick_backend_PubDataBridge.dto.response.SearchResponse;
import com.example.TripPick_backend_PubDataBridge.event.producer.SearchProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class SearchService {
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;
    private final SearchProducer searchProducer;
    private final PropertiesConfig propertiesConfig;

    String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

    @Scheduled(cron = "*/10 * * * * *")
    public void autoFetchAndSend() {
        String serviceKey = propertiesConfig.getServiceKey();
        //12: 관광지, 14: 문화시설, 15: 축제공연행사 25: 여행코스
        List<String> contentTypeIds = List.of("12", "14", "15", "25");

        for (String contentTypeId : contentTypeIds) {
            try {
                fetchAndSendList(serviceKey, contentTypeId);
                log.info("리스트 전송 완료: contentId={}", contentTypeId);
            } catch (Exception e) {
                log.error("contentTypeId={} 리스트 전송 실패: ", contentTypeId, e);
            }
        }
    }

    @Transactional
    public void fetchAndSendList(String serviceKey, String contentTypeID) {
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

        SearchResponse response = null;

        try {
            response = restTemplate.getForObject(url, SearchResponse.class);

            if (response != null) {
                log.info("API 응답 객체 (JSON): {}", objectMapper.writeValueAsString(response));
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        if (response == null
                || response.getResponse() == null
                || response.getResponse().getBody() == null
                || response.getResponse().getBody().getItems() == null
                || response.getResponse().getBody().getItems().getItem() == null) {
            log.warn("API 응답 데이터가 없습니다.");
            return;
        }

        for (SearchResponse.Item dto : response.getResponse().getBody().getItems().getItem()) {
            Search entity = Search.builder()
                    .contentid(dto.getContentid())
                    .contentTypeid(dto.getContenttypeid())
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
                    .lDongSignguCd(dto.getLDongSignguCd())
                    .build();

            entity.setCreatedTimeFromString(dto.getCreatedtime());
            entity.setModifiedTimeFromString(dto.getModifiedtime());

            SearchEvent event = SearchEvent.fromEntity("list", entity);
            searchProducer.send(KafkaConfig.topics.SEARCH, event);
        }
    }

}