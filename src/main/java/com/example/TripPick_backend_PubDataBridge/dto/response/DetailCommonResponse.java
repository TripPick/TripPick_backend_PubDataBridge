package com.example.TripPick_backend_PubDataBridge.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DetailCommonResponse {
    private Response response;

    @Getter
    @Setter
    public static class Response {
        private DetailCommonResponse.Header header;
        private DetailCommonResponse.Body body;
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
        private DetailCommonResponse.Items items;
        private int numOfRows;
        private int pageNo;
        private int totalCount;
    }

    @Getter
    @Setter
    public static class Items {
        private List<DetailCommonResponse.Item> item;
    }

    @Getter
    @Setter
    public static class Item {
        private String contentid;
        private String contenttypeid;
        private String areacode;
        private String addr1;
        private String addr2;
        private String firstimage;
        private String firstimage2;
        private String tel;
        private String title;
        private String zipcode;
        private String createdtime;
        private String modifiedtime;
    }
}