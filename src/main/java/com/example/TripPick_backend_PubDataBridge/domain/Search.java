package com.example.TripPick_backend_PubDataBridge.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "search")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Search {
    @Id
    @Column(name = "contentid", length = 20)
    private String contentid;

    @Column(name = "contenttypeid", length = 2)
    private String contentTypeid;

    @Column(name = "cat1")
    private String cat1;

    @Column(name = "cat2")
    private String cat2;

    @Column(name = "cat3")
    private String cat3;

    @Column(name = "lDong_signgu_cd")
    private String lDongSignguCd;

    @Column(name = "l_dong_regn_cd")
    private String lDongRegnCd;

    @Column(name = "areacode")
    private String areacode;

    @Column(name = "addr1")
    private String addr1;

    @Column(name = "addr2")
    private String addr2;

    @Column(name = "firstimage")
    private String firstimage;

    @Column(name = "firstimage2")
    private String firstimage2;

    @Column(name = "tel")
    private String tel;

    @Column(name = "title")
    private String title;

    @Column(name = "zipcode")
    private String zipcode;

    @Column(name = "modifiedtime")
    private LocalDateTime modifiedtime;

    @Column(name = "createdtime")
    private LocalDateTime createdtime;


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

        try {
            if (dateTimeStr.length() == 14) {
                // yyyyMMddHHmmss
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
                return LocalDateTime.parse(dateTimeStr, formatter);
            } else if (dateTimeStr.length() == 8) {
                // yyyyMMdd → 날짜만 있고 시간 없음, 00:00:00 기본 설정
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                return LocalDateTime.parse(dateTimeStr, formatter).withHour(0).withMinute(0).withSecond(0);
            } else {
                // 지원하지 않는 형식일 경우 null 반환
                return null;
            }
        } catch (Exception e) {
            // 혹시나 파싱 예외가 생겼을 경우
            return null;
        }
    }
}