package com.example.TripPick_backend_PubDataBridge.service;

import com.example.TripPick_backend_PubDataBridge.config.KafkaConfig;
import com.example.TripPick_backend_PubDataBridge.config.PropertiesConfig;
import com.example.TripPick_backend_PubDataBridge.domain.DetailCommon;
import com.example.TripPick_backend_PubDataBridge.domain.event.DetailCommonEvent;
import com.example.TripPick_backend_PubDataBridge.dto.response.DetailCommonResponse;
import com.example.TripPick_backend_PubDataBridge.event.producer.DetailCommonProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Service
@RequiredArgsConstructor
public class DetailCommonService {
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;
    private final DetailCommonProducer detailCommonProducer;
    private final PropertiesConfig propertiesConfig;

    public void fetchAndSend(String contentId) {
        String serviceKey = propertiesConfig.getServiceKey();

        try {
            fetchAndSendInfo(serviceKey, contentId);
            log.info("리스트 전송 완료: contentId={}", contentId);
        } catch (Exception e) {
            log.error("contentId={} 리스트 전송 실패: ", contentId, e);
        }
    }

    @Transactional
    public void fetchAndSendInfo(String serviceKey, String contentId) {
        String rawUrl = "https://apis.data.go.kr/B551011/KorService2/detailCommon2";
        String url = UriComponentsBuilder.newInstance()
                .uri(URI.create(rawUrl))
                .queryParam("serviceKey", serviceKey)
                .queryParam("MobileApp", "AppTest")
                .queryParam("MobileOS", "ETC")
                .queryParam("contentId", contentId)
                //.queryParam("modifiedtime", today)
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

        if (response == null
                || response.getResponse() == null
                || response.getResponse().getBody() == null
                || response.getResponse().getBody().getItems() == null
                || response.getResponse().getBody().getItems().getItem() == null
                || response.getResponse().getBody().getItems().getItem().isEmpty()) {
            log.warn("API 응답 데이터가 없거나 비어 있습니다.");
            return;
        }

        for (DetailCommonResponse.Item dto : response.getResponse().getBody().getItems().getItem()) {
            DetailCommon entity = DetailCommon.builder()
                    .contentid(dto.getContentid())
                    .contentTypeId(dto.getContenttypeid())
                    .areacode(dto.getAreacode())
                    .addr1(dto.getAddr1())
                    .addr2(dto.getAddr2())
                    .firstImage(dto.getFirstimage())
                    .firstImage2(dto.getFirstimage2())
                    .tel(dto.getTel())
                    .title(dto.getTitle())
                    .zipcode(dto.getZipcode())
                    .createdTime(dto.getCreatedtime())
                    .modifiedTime(dto.getCreatedtime())
                    .build();

            DetailCommonEvent event = DetailCommonEvent.fromEntity("detailcommon", entity);
            detailCommonProducer.send(KafkaConfig.topics.DETAILCOMMON, event);
        }
    }
}