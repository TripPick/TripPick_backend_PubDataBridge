package com.example.TripPick_backend_PubDataBridge.repository;

import com.example.TripPick_backend_PubDataBridge.domain.TourSpotInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TourSpotRepository extends JpaRepository<TourSpotInfo, String> {
}
