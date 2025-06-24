package com.example.TripPick_backend_PubDataBridge.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class TourSpotResponse {
    private Response response;

    @Getter
    @Setter
    public static class Response {
        private TourSpotResponse.Header header;
        private TourSpotResponse.Body body;
    }

    @Getter
    @Setter
    public static class Header {
        private String resultCode;
        private String resultMsg;
    }

    @Getter
    @Setter
    public static class Body {
        private TourSpotResponse.Items items;
        private int numOfRows;
        private int pageNo;
        private int totalCount;
    }

    @Getter
    @Setter
    public static class Items {
        private List<TourSpotResponse.Item> item;
    }

    @Getter
    @Setter
    public static class Item {
        private String contentid;
        private String accomcount;
        private String chkbabycarriage;
        private String chkcreditcard;
        private String chkpet;
        private String expagerange;
        private String expguide;
        private String heritage1;
        private String heritage2;
        private String heritage3;
        private String infocenter;
        private LocalDateTime opendate;
        private String parking;
        private String restdate;
        private String useseason;
        private String usetime;
    }
}