package com.example.TripPick_backend_PubDataBridge.domain.event;

import com.example.TripPick_backend_PubDataBridge.domain.Search;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchEvent {
    private String action;
    private Search search;

    public static SearchEvent fromEntity(String action, Search search) {
        SearchEvent message = new SearchEvent();

        message.action = action;
        message.search = search;

        return message;
    }
}
