package com.example.TripPick_backend_PubDataBridge.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocTourListRequest {
    private String serviceKey;  //인증키(서비스키)
    private String mobileApp;   //서비스명
    private String mobileOS;    //OS구분
    private String contentID;   //컨텐츠ID
    private String numOfRows;   //한페이지결과수
    private String pageNo;      //페이지번호
    private String arrange;     //정렬구분
    private String contentTypeId;   //관광타입ID
    private String cat1;            //대분류
    private String cat2;            //중분류
    private String cat3;            //소분류
    private String modifiedTime;    //콘텐츠변경일자
    private String lDongRegnCd;     //법정동 시도 코드
    private String lDongSignguCd;   //법정동 시군구 코드
}
