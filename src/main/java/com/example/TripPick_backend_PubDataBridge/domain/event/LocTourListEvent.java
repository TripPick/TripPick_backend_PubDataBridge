package com.example.TripPick_backend_PubDataBridge.domain.event;

import com.example.TripPick_backend_PubDataBridge.domain.Search;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocTourListEvent {
    public static final String Topic = "searchinfo";
    private String action;
    private Search search;

    public static LocTourListEvent fromEntity(String action, Search search) {
        LocTourListEvent message = new LocTourListEvent();

        message.action = action;
        message.search = search;

        return message;
    }
}
