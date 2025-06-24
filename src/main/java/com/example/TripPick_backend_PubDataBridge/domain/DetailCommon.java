package com.example.TripPick_backend_PubDataBridge.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "detail_common")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetailCommon {
    @Id
    @Column(name = "contentid")
    private String contentid;

    @Column(name = "contenttypeid")
    private String contenttypeid;

    @Column(name = "title")
    private String title;

    @Column(name = "createdtime")
    private LocalDateTime createdtime;

    @Column(name = "modifiedtime")
    private LocalDateTime modifiedtime;

    @Column(name = "tel")
    private String tel;

    @Column(name = "telname")
    private String telname;

    @Column(name = "homepage")
    private String homepage;

    @Column(name = "firstimage")
    private String firstimage;

    @Column(name = "firstimage2")
    private String firstimage2;

    @Column(name = "cpyrhtDivCd")
    private String cpyrhtDivCd;

    @Column(name = "areacode")
    private String areacode;

    @Column(name = "lDongRegnCd")
    private String lDongRegnCd;

    @Column(name = "lDongSigunguCd")
    private String lDongSigunguCd;

    @Column(name = "lclsSystm1")
    private String lclsSystm1;

    @Column(name = "lclsSystm2")
    private String lclsSystm2;

    @Column(name = "lclsSystm3")
    private String lclsSystm3;

    @Column(name = "cat1")
    private String cat1;

    @Column(name = "cat2")
    private String cat2;

    @Column(name = "cat3")
    private String cat3;

    @Column(name = "addr1")
    private String addr1;

    @Column(name = "addr2")
    private String addr2;

    @Column(name = "zipcode")
    private String zipcode;

    @Column(name = "mapx")
    private double mapx;

    @Column(name = "mapy")
    private double mapy;

    @Column(name = "mlevel")
    private String mlevel;

    @Column(name = "overview")
    private String overview;

    public void setCreatedTimeFromString(String createdTimeStr) {
        this.createdtime = parseDateTime(createdTimeStr);
    }

    public void setModifiedTimeFromString(String modifiedTimeStr) {
        this.modifiedtime = parseDateTime(modifiedTimeStr);
    }

    private LocalDateTime parseDateTime(String dateTimeStr) {
        if (dateTimeStr == null || dateTimeStr.isEmpty()) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return LocalDateTime.parse(dateTimeStr, formatter);
    }
}