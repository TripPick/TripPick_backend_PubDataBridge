package com.example.TripPick_backend_PubDataBridge.domain.event;

import com.example.TripPick_backend_PubDataBridge.domain.TourCourseItem;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TourCourseItemEvent {
    private String action;
    private TourCourseItem tourCourseItem;

    public static TourCourseItemEvent fromEntity(String action, TourCourseItem tourCourseItem) {
        TourCourseItemEvent message = new TourCourseItemEvent();

        message.action = action;
        message.tourCourseItem = tourCourseItem;

        return message;
    }

//    public static TourCourseEvent fromEntityList(String action, List<TourCourseInfo> tourCourseInfoList) {
//        TourCourseEvent message = new TourCourseEvent();
//        message.action = action;
//        message.tourCourseInfoList = tourCourseInfoList;
//        return message;
//    }
}