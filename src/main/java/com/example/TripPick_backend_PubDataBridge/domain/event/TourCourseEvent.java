package com.example.TripPick_backend_PubDataBridge.domain.event;

import com.example.TripPick_backend_PubDataBridge.domain.TourCourseInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TourCourseEvent {
    private String action;
    private TourCourseInfo tourCourseInfo;
    private List<TourCourseInfo> tourCourseInfoList;

//    public static TourCourseEvent fromEntity(String action, TourCourseInfo tourCourseInfo) {
//        TourCourseEvent message = new TourCourseEvent();
//
//        message.action = action;
//        message.tourCourseInfo = tourCourseInfo;
//
//        return message;
//    }

    public static TourCourseEvent fromEntityList(String action, List<TourCourseInfo> tourCourseInfoList) {
        TourCourseEvent message = new TourCourseEvent();
        message.action = action;
        message.tourCourseInfoList = tourCourseInfoList;
        return message;
    }
}