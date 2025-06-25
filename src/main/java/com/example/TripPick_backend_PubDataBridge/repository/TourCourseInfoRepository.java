package com.example.TripPick_backend_PubDataBridge.repository;

import com.example.TripPick_backend_PubDataBridge.domain.TourCourseInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TourCourseInfoRepository extends JpaRepository<TourCourseInfo, String> {
}
