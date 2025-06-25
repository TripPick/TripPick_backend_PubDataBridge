package com.example.TripPick_backend_PubDataBridge.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "cultural_facility_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CulturalFacilityInfo {

    @Id
    @Column(name = "contentid")
    private String contentid;

    @Column(name = "accomcountculture", length = 225)
    private String accomcountculture;

    @Column(name = "chkbabycarriageculture", length = 225)
    private String chkbabycarriageculture;

    @Column(name = "chkcreditcardculture", length = 225)
    private String chkcreditcardculture;

    @Column(name = "chkpetculture", length = 225)
    private String chkpetculture;

    @Column(name = "discountinfo", length = 225)
    private String discountinfo;

    @Column(name = "infocenterculture", length = 225)
    private String infocenterculture;

    @Column(name = "parkingculture", length = 225)
    private String parkingculture;

    @Column(name = "parkingfee", length = 225)
    private String parkingfee;

    @Column(name = "restdateculture", length = 225)
    private String restdateculture;

    @Column(name = "usefee", length = 225)
    private String usefee;

    @Column(name = "usetimeculture", length = 225)
    private String usetimeculture;

    @Column(name = "scale", length = 225)
    private String scale;

    @Column(name = "spendtime", length = 225)
    private String spendtime;
}