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
        private String contentid;       //콘텐츠ID 1
        private String contenttypeid;   //콘텐츠타입ID 1
        private String title;           //콘텐츠명(제목) 1
        private String createdtime;     //등록일 1
        private String modifiedtime;    //수정일 1
        private String tel;             //전화번호 0
        private String telname;         //전화번호명 0
        private String homepage;        //홈페이지주소 0
        private String firstimage;      //대표이미지(원본) 0
        private String firstimage2;     //대표이미지(썸네일) 0
        private String cpyrhtDivCd;     //저작권유형 0
        private String areacode;       //지역코드 0
        private String lDongRegnCd;     //법정동 시도 코드 0
        private String lDongSigunguCd;  //법정동 시군구 코드 0
        private String lclsSystm1;      //분류체계 대분류 0
        private String lclsSystm2;      //분류체계 중분류 0
        private String lclsSystm3;      //분류체계 소분류 0
        private String cat1;            //대분류 0
        private String cat2;            //중분류 0
        private String cat3;            //소분류 0
        private String addr1;           //주소 0
        private String addr2;           //상세주소 0
        private String zipcode;         //우편번호 0
        private Double mapx;            //GPS X좌표 0
        private Double mapy;            //GPS Y좌표 0
        private String mlevel;          //Map Level 0
        private String overview;        //개요 0
    }
}