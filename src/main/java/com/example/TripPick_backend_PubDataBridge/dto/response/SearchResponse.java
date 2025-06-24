package com.example.TripPick_backend_PubDataBridge.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchResponse {
    private Response response;

    @Getter
    @Setter
    public static class Response {
        private Header header;
        private Body body;
    }

    //응답항목명 0: 옵션, 1: 필수
    @Getter
    @Setter
    public static class Header {
        private String resultCode;  //결과코드 1
        private String resultMsg;   //결과메시지 1
    }

    @Getter
    @Setter
    public static class Body {
        private Items items;
        private int numOfRows;  //한페이지결과수 1
        private int pageNo;     //페이지번호 1
        private int totalCount; //전체결과수 1
    }

    @Getter
    @Setter
    public static class Items {
        private List<Item> item;
    }

    @Getter
    @Setter
    public static class Item {
        private String contentid;
        private String contenttypeid;
        private String cat1;
        private String cat2;
        private String cat3;
        private String lDongSignguCd;
        private String lDongRegnCd;
        private String areacode;
        private String addr1;
        private String addr2;
        private String firstimage;
        private String firstimage2;
        private String tel;
        private String title;
        private String zipcode;
        private String modifiedtime;
        private String createdtime;
    }
}