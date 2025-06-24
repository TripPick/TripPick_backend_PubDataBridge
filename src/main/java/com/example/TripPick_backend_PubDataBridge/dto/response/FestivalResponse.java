package com.example.TripPick_backend_PubDataBridge.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FestivalResponse {
    private Response response;

    @Getter
    @Setter
    public static class Response {
        private FestivalResponse.Header header;
        private FestivalResponse.Body body;
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
        private FestivalResponse.Items items;
        private int numOfRows;
        private int pageNo;
        private int totalCount;
    }

    @Getter
    @Setter
    public static class Items {
        private List<FestivalResponse.Item> item;
    }

    @Getter
    @Setter
    public static class Item {
        private String contentid;
        private String agelimit;
        private String bookingplace;
        private String discountinfofestival;
        private String eventhomepage;
        private String eventplace;
        private String eventstartdate;
        private String placeinfo;
        private String playtime;
        private String program;
        private String spendtimefestival;
        private String sponsor1;
        private String sponsor1tel;
        private String sponsor2;
        private String sponsor2tel;
        private String subevent;
        private String usetimefestival;
    }
}