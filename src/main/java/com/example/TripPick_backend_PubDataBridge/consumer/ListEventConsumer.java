package com.example.TripPick_backend_PubDataBridge.consumer;

import com.example.TripPick_backend_PubDataBridge.config.KafkaConfig;
import com.example.TripPick_backend_PubDataBridge.domain.event.SearchEvent;
import com.example.TripPick_backend_PubDataBridge.service.CulturalFacilityService;
import com.example.TripPick_backend_PubDataBridge.service.FestivalService;
import com.example.TripPick_backend_PubDataBridge.service.TourCourseService;
import com.example.TripPick_backend_PubDataBridge.service.TourSpotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ListEventConsumer {
    private final CulturalFacilityService culturalFacilityService;
    private final FestivalService festivalService;
    private final TourSpotService tourSpotService;
    private final TourCourseService tourCourseService;

    @KafkaListener(topics = KafkaConfig.topics.SEARCH)
    public void handlerListEvent(SearchEvent event) {
        String contentTypeId = event.getSearch().getContenttypeid();
        String contentId = event.getSearch().getContentid();

        switch (contentTypeId) {
            case KafkaConfig.contentTypeId.CULTURALFACILITY:
                culturalFacilityService.fetchAndSend(contentTypeId, contentId);
                break;
            case KafkaConfig.contentTypeId.FESTIVAL:
                festivalService.fetchAndSend(contentTypeId, contentId);
                break;
            case KafkaConfig.contentTypeId.TOURSPOT:
                tourSpotService.fetchAndSend(contentTypeId, contentId);
                break;
            case KafkaConfig.contentTypeId.TOURCOURSE:
                tourCourseService.fetchAndSend(contentTypeId, contentId);
                break;
            default:
                log.warn("지원하지 않는 contentTypeId: {}", contentTypeId);
        }

        culturalFacilityService.fetchAndSend(contentTypeId, contentId);
    }
}
