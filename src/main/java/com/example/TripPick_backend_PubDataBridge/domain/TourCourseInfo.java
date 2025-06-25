package com.example.TripPick_backend_PubDataBridge.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "tour_course_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TourCourseInfo {

    @Id
    @Column(name = "contentid")
    private String contentid;

    @Column(name = "distance")
    private String distance;

    @Column(name = "infocentertourcourse")
    private String infocentertourcourse;

    @Column(name = "schedule")
    private String schedule;

    @Column(name = "taketime")
    private String taketime;

    @Column(name = "theme")
    private String theme;
}
