package com.example.TripPick_backend_PubDataBridge.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "tour_course_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TourCourseItem {
    @Id
    @Column(name = "contentid")
    private String contentid;

    @Column(name = "subcontentid")
    private String subcontentid;

    @Column(name = "subdetailalt")
    private String subdetailalt;

    @Column(name = "subdetailimg")
    private String subdetailimg;

    @Column(name = "subdetailoverview")
    private String subdetailoverview;

    @Column(name = "subname")
    private String subname;

    @Column(name = "subnum")
    private String subnum;
}
