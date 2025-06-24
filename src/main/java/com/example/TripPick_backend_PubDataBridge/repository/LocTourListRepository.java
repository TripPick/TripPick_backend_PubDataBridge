package com.example.TripPick_backend_PubDataBridge.repository;

import com.example.TripPick_backend_PubDataBridge.domain.Search;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LocTourListRepository extends JpaRepository<Search, String>, JpaSpecificationExecutor<Search> {
}
