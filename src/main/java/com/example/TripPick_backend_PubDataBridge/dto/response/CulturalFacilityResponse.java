package com.example.TripPick_backend_PubDataBridge.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CulturalFacilityResponse {
    private CulturalFacilityResponse.Response response;

    @Getter
    @Setter
    public static class Response {
        private CulturalFacilityResponse.Header header;
        private CulturalFacilityResponse.Body body;
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
        private CulturalFacilityResponse.Items items;
        private int numOfRows;
        private int pageNo;
        private int totalCount;
    }

    @Getter
    @Setter
    public static class Items {
        private List<CulturalFacilityResponse.Item> item;
    }

    @Getter
    @Setter
    public static class Item {
        private String contentid;
        private String accomcountculture;
        private String chkbabycarriageculture;
        private String chkcreditcardculture;
        private String chkpetculture;
        private String discountinfo;
        private String infocenterculture;
        private String parkingculture;
        private String parkingfee;
        private String restdateculture;
        private String usefee;
        private String usetimeculture;
        private String scale;
        private String spendtime;
    }
}