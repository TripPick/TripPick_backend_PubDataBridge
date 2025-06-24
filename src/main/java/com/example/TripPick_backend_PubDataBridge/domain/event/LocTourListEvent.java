package com.example.TripPick_backend_PubDataBridge.domain.event;

import com.example.TripPick_backend_PubDataBridge.domain.LocTourList;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocTourListEvent {
    public static final String Topic = "searchinfo";
    private String action;
    private LocTourList locTourList;

    public static LocTourListEvent fromEntity(String action, LocTourList locTourList) {
        LocTourListEvent message = new LocTourListEvent();

        message.action = action;
        message.locTourList = locTourList;

        return message;
    }
}
