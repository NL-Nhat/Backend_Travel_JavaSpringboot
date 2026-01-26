package com.example.travel.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.travel.dto.response.ScheduleResponseDTO;
import com.example.travel.service.DepartureCheduleService;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@Validated //thêm vào để sử dụng Bean Validation cho @PathVariable
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedules")
public class ScheduleController {

    private final DepartureCheduleService d;

    @GetMapping("/{id}")
    public ResponseEntity<List<ScheduleResponseDTO>> getAllScheduleOfDepartureChedule(@PathVariable(value = "id") 
                                                                                      @NotNull(message = "id lkh ko được null")
                                                                                      @Min(value = 1, message = "id lkh ko hợp lệ")
                                                                                      Integer id) {
        return ResponseEntity.ok(d.getAllScheduleOfDepartureChedule(id));
    }
}
