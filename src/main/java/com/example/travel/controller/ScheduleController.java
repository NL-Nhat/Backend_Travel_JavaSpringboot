package com.example.travel.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.travel.dto.response.ScheduleResponseDTO;
import com.example.travel.service.DepartureCheduleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedules")
public class ScheduleController {

    private final DepartureCheduleService d;

    @GetMapping("/{id}")
    public ResponseEntity<List<ScheduleResponseDTO>> getAllScheduleOfDepartureChedule(@PathVariable Integer id) {
        return ResponseEntity.ok(d.getAllScheduleOfDepartureChedule(id));
    }
}
