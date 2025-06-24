package com.example.TripPick_backend_PubDataBridge.repository;

import com.example.TripPick_backend_PubDataBridge.domain.FestivalInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FestivalRepository extends JpaRepository<FestivalInfo, String> {
}