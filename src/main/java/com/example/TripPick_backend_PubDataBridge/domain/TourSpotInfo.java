package com.example.TripPick_backend_PubDataBridge.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tour_spot_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TourSpotInfo {

    @Id
    @Column(name = "contentId")
    private String contentId;

    @Column(name = "accomcount", length = 255)
    private String accomcount;

    @Column(name = "chkbabycarriage", length = 225)
    private String chkbabycarriage;

    @Column(name = "chkcreditcard", length = 225)
    private String chkcreditcard;

    @Column(name = "chkpet", length = 225)
    private String chkpet;

    @Column(name = "expagerange", length = 225)
    private String expagerange;

    @Column(name = "expguide", length = 225)
    private String expguide;

    @Column(name = "heritage1", length = 225)
    private String heritage1;

    @Column(name = "heritage2", length = 225)
    private String heritage2;

    @Column(name = "heritage3", length = 225)
    private String heritage3;

    @Column(name = "infocenter", length = 225)
    private String infocenter;

    @Column(name = "opendate")
    private LocalDateTime opendate;

    @Column(name = "parking")
    private String parking;

    @Column(name = "restdate")
    private String restdate;

    @Column(name = "useseason")
    private String useseason;

    @Column(name = "usetime")
    private String usetime;
}