package com.example.TripPick_backend_PubDataBridge.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "DetailCommon")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetailCommon {

    @Id
    @Column(name = "contentid", length = 20, nullable = false)
    private String contentid;

    @Column(name = "contenttypeid", length = 2)
    private String contentTypeId;

    @Column(name = "areacode", length = 2)
    private String areacode;

    @Column(name = "addr1", columnDefinition = "TEXT")
    private String addr1;

    @Column(name = "addr2", columnDefinition = "TEXT")
    private String addr2;

    @Column(name = "firstimage", columnDefinition = "TEXT")
    private String firstImage;

    @Column(name = "firstimage2", columnDefinition = "TEXT")
    private String firstImage2;

    @Column(name = "tel", length = 20)
    private String tel;

    @Column(name = "title", length = 20)
    private String title;

    @Column(name = "zipcode", length = 5)
    private String zipcode;

    @Column(name = "createdtime")
    private String createdTime;

    @Column(name = "modifiedtime")
    private String modifiedTime;
}