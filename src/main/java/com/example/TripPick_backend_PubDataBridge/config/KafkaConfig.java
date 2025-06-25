package com.example.TripPick_backend_PubDataBridge.config;

public class KafkaConfig {
    public static class contentTypeId {
        public static final String TOURSPOT = "12";
        public static final String CULTURALFACILITY = "14";
        public static final String FESTIVAL = "15";
        public static final String TOURCOURSE = "25";
    }

    public static class topics {
        public static final String SEARCH = "searchinfo";
        public static final String DETAILCOMMON = "detailcommon";
        public static final String TOURSPOT = "tourspotinfo";
        public static final String CULTURALFACILITY = "culturalfacilityinfo";
        public static final String FESTIVAL = "festivalinfo";
        public static final String TOURCOURSEINFO = "tourcourseinfo";
        public static final String TOURCOURSEITEM = "tourcourseitem";
    }
}
