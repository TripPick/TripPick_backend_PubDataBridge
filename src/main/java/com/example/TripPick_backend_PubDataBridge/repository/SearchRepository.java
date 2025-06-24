package com.example.TripPick_backend_PubDataBridge.repository;

import com.example.TripPick_backend_PubDataBridge.domain.Search;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchRepository extends JpaRepository<Search, String> {
}
