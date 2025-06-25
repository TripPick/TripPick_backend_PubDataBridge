package com.example.TripPick_backend_PubDataBridge.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "festival_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FestivalInfo {

    @Id
    @Column(name = "contentid")
    private String contentid;

    @Column(name = "agelimit", length = 225)
    private String agelimit;

    @Column(name = "bookingplace", length = 225)
    private String bookingplace;

    @Column(name = "discountinfofestival", length = 225)
    private String discountinfofestival;

    @Column(name = "eventenddate", length = 225)
    private String eventenddate;

    @Column(name = "eventhomepage", length = 225)
    private String eventhomepage;

    @Column(name = "eventplace", length = 225)
    private String eventplace;

    @Column(name = "eventstartdate", length = 225)
    private String eventstartdate;

    @Column(name = "placeinfo", length = 225)
    private String placeinfo;

    @Column(name = "playtime", length = 225)
    private String playtime;

    @Column(name = "program", length = 225)
    private String program;

    @Column(name = "spendtimefestival", length = 225)
    private String spendtimefestival;

    @Column(name = "sponsor1", length = 225)
    private String sponsor1;

    @Column(name = "sponsor1tel", length = 225)
    private String sponsor1tel;

    @Column(name = "sponsor2", length = 225)
    private String sponsor2;

    @Column(name = "sponsor2tel", length = 225)
    private String sponsor2tel;

    @Column(name = "subevent", length = 225)
    private String subevent;

    @Column(name = "usetimefestival", length = 225)
    private String usetimefestival;
}
