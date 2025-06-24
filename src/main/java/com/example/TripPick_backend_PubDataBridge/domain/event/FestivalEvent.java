package com.example.TripPick_backend_PubDataBridge.domain.event;

import com.example.TripPick_backend_PubDataBridge.domain.FestivalInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FestivalEvent {
    private String action;
    private FestivalInfo festivalInfo;

    public static FestivalEvent fromEntity(String action, FestivalInfo festivalInfo) {
        FestivalEvent message = new FestivalEvent();

        message.action = action;
        message.festivalInfo = festivalInfo;

        return message;
    }
}