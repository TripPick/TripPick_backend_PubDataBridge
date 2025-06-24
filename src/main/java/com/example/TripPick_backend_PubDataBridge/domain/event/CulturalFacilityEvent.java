package com.example.TripPick_backend_PubDataBridge.domain.event;

import com.example.TripPick_backend_PubDataBridge.domain.CulturalFacilityInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CulturalFacilityEvent {
    private String action;
    private CulturalFacilityInfo culturalFacilityInfo;

    public static CulturalFacilityEvent fromEntity(String action, CulturalFacilityInfo culturalFacilityInfo) {
        CulturalFacilityEvent message = new CulturalFacilityEvent();

        message.action = action;
        message.culturalFacilityInfo = culturalFacilityInfo;

        return message;
    }
}
