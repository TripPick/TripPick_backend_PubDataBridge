package com.example.TripPick_backend_PubDataBridge.service;

import com.example.TripPick_backend_PubDataBridge.config.KafkaConfig;
import com.example.TripPick_backend_PubDataBridge.config.PropertiesConfig;
import com.example.TripPick_backend_PubDataBridge.domain.FestivalInfo;
import com.example.TripPick_backend_PubDataBridge.domain.event.FestivalEvent;
import com.example.TripPick_backend_PubDataBridge.dto.response.FestivalResponse;
import com.example.TripPick_backend_PubDataBridge.event.producer.FestivalProducer;
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
public class FestivalService {
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;
    private final FestivalProducer festivalProducer;
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

        FestivalResponse response = null;

        try {
            response = restTemplate.getForObject(url, FestivalResponse.class);

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

        for (FestivalResponse.Item dto : response.getResponse().getBody().getItems().getItem()) {
            FestivalInfo entity = FestivalInfo.builder()
                    .contentId(dto.getContentid())
                    .agelimit(dto.getAgelimit())
                    .bookingplace(dto.getBookingplace())
                    .discountinfofestival(dto.getDiscountinfofestival())
                    .eventhomepage(dto.getEventhomepage())
                    .eventplace(dto.getEventplace())
                    .eventstartdate(dto.getEventstartdate())
                    .placeinfo(dto.getPlaceinfo())
                    .playtime(dto.getPlaytime())
                    .program(dto.getProgram())
                    .spendtimefestival(dto.getSpendtimefestival())
                    .sponsor1(dto.getSponsor1())
                    .sponsor1tel(dto.getSponsor1tel())
                    .sponsor2(dto.getSponsor2())
                    .sponsor2tel(dto.getSponsor2tel())
                    .subevent(dto.getSubevent())
                    .usetimefestival(dto.getUsetimefestival())
                    .build();

            FestivalEvent event = FestivalEvent.fromEntity("festival", entity);
            festivalProducer.send(KafkaConfig.topics.FESTIVAL, event);
        }
    }
}