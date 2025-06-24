package com.example.TripPick_backend_PubDataBridge.domain.event;

import com.example.TripPick_backend_PubDataBridge.domain.TourSpotInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TourSpotEvent {
    private String action;
    private TourSpotInfo tourSpotInfo;

    public static TourSpotEvent fromEntity(String action, TourSpotInfo tourSpotInfo) {
        TourSpotEvent message = new TourSpotEvent();

        message.action = action;
        message.tourSpotInfo = tourSpotInfo;

        return message;
    }
}