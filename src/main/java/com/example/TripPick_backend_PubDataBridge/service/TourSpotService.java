package com.example.TripPick_backend_PubDataBridge.service;

import com.example.TripPick_backend_PubDataBridge.config.KafkaConfig;
import com.example.TripPick_backend_PubDataBridge.config.PropertiesConfig;
import com.example.TripPick_backend_PubDataBridge.domain.TourSpotInfo;
import com.example.TripPick_backend_PubDataBridge.domain.event.TourSpotEvent;
import com.example.TripPick_backend_PubDataBridge.dto.response.TourSpotResponse;
import com.example.TripPick_backend_PubDataBridge.event.producer.TourSpotProducer;
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
public class TourSpotService {
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;
    private final TourSpotProducer tourSpotProducer;
    private final PropertiesConfig propertiesConfig;

    public void fetchAndSend(String contentTypeId, String contentId) {
        String serviceKey = propertiesConfig.getServiceKey();

        try {
            fetchAndSendInfo(serviceKey, contentTypeId, contentId);
            log.info("리스트 전송 완료: contentId={}", contentId);
        } catch (Exception e) {
            log.error("contentTypeId={} 리스트 전송 실패: ", contentId, e);
        }
    }

    @Transactional
    public void fetchAndSendInfo(String serviceKey, String contentTypeId, String contentId) {
        String rawUrl = "https://apis.data.go.kr/B551011/KorService2/detailIntro2";
        String url = UriComponentsBuilder.newInstance()
                .uri(URI.create(rawUrl))
                .queryParam("serviceKey", serviceKey)
                .queryParam("MobileApp", "AppTest")
                .queryParam("MobileOS", "ETC")
                .queryParam("contentTypeId", contentTypeId)
                .queryParam("contentId", contentId)
                //.queryParam("modifiedtime", today)
                .queryParam("_type", "json")
                .build()
                .toUriString();

        log.info("요청 URL: {}", url);

        TourSpotResponse response = null;

        try {
            response = restTemplate.getForObject(url, TourSpotResponse.class);

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

        for (TourSpotResponse.Item dto : response.getResponse().getBody().getItems().getItem()) {
            TourSpotInfo entity = TourSpotInfo.builder()
                    .contentid(dto.getContentid())
                    .accomcount(dto.getAccomcount())
                    .chkbabycarriage(dto.getChkbabycarriage())
                    .chkcreditcard(dto.getChkcreditcard())
                    .chkpet(dto.getChkpet())
                    .expagerange(dto.getExpagerange())
                    .expguide(dto.getExpguide())
                    .heritage1(dto.getHeritage1())
                    .heritage2(dto.getHeritage2())
                    .heritage3(dto.getHeritage3())
                    .infocenter(dto.getInfocenter())
                    .opendate(dto.getOpendate())
                    .parking(dto.getParking())
                    .restdate(dto.getRestdate())
                    .useseason(dto.getUseseason())
                    .usetime(dto.getUsetime())
                    .build();

            TourSpotEvent event = TourSpotEvent.fromEntity("tourspot", entity);
            tourSpotProducer.send(KafkaConfig.topics.TOURSPOT, event);
        }
    }

}
