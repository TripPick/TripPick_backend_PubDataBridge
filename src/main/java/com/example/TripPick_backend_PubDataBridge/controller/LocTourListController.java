package com.example.TripPick_backend_PubDataBridge.controller;

import com.example.TripPick_backend_PubDataBridge.domain.LocTourList;
import com.example.TripPick_backend_PubDataBridge.dto.request.LocTourListRequest;
import com.example.TripPick_backend_PubDataBridge.service.LocTourListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/list")
public class LocTourListController {
    private final LocTourListService locTourListService;

    @PostMapping("/search")
    public ResponseEntity<List<LocTourList>> searchTourList(@RequestBody LocTourListRequest cond) {
        List<LocTourList> result = locTourListService.searchByConditions(cond);
        return ResponseEntity.ok(result);
    }
}
