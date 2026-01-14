package com.example.travel.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.travel.dto.response.TourResponseDTO;
import com.example.travel.service.TourService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/tours")
@RequiredArgsConstructor
public class TourController {

    private final TourService tourService;

    @GetMapping("/top5tour")
    public ResponseEntity<List<TourResponseDTO>> getFiveTourHot() {
        return ResponseEntity.ok(tourService.getFiveTourHot());
    }
    

}
