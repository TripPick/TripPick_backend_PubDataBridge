package com.example.TripPick_backend_PubDataBridge.domain.event;

import com.example.TripPick_backend_PubDataBridge.domain.TourCourseInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TourCourseInfoEvent {
    private String action;
    private TourCourseInfo tourCourseInfo;

    public static TourCourseInfoEvent fromEntity(String action, TourCourseInfo tourCourseInfo) {
        TourCourseInfoEvent message = new TourCourseInfoEvent();

        message.action = action;
        message.tourCourseInfo = tourCourseInfo;

        return message;
    }
}