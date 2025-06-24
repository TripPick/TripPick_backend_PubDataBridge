package com.example.TripPick_backend_PubDataBridge.repository;

import com.example.TripPick_backend_PubDataBridge.domain.CulturalFacilityInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CulturalFacilityRepository extends JpaRepository<CulturalFacilityInfo, String> {
}
