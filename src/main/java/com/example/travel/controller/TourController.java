package com.example.travel.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.travel.dto.response.TourResponseDTO;
import com.example.travel.service.TourService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/tours")
@RequiredArgsConstructor
@Tag(name = "Tour", description = "Quản lý tour du lịch")  // hiển thị trong swagger
public class TourController {

    private final TourService tourService;

    @GetMapping("/top5tour")
    @Operation(summary = "Top 5 tour được đặt nhiều nhất") // hiển thị trong swagger
    public ResponseEntity<List<TourResponseDTO>> getFiveTourHot() {
        return ResponseEntity.ok(tourService.getFiveTourHot());
    }
    
    @GetMapping("/count-tour")
    @Operation(summary = "Đếm số tour")
    public ResponseEntity<?> countNumberTour() {
        return ResponseEntity.ok(tourService.countNumberTour());
    }

}
