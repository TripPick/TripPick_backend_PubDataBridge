package com.example.TripPick_backend_PubDataBridge.domain.event;

import com.example.TripPick_backend_PubDataBridge.domain.DetailCommon;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetailCommonEvent {
    private String action;
    private DetailCommon detailCommon;

    public static DetailCommonEvent fromEntity(String action, DetailCommon detailCommon) {
        DetailCommonEvent message = new DetailCommonEvent();

        message.action = action;
        message.detailCommon = detailCommon;

        return message;
    }
}