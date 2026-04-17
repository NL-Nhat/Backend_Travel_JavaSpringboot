package com.example.travel.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.travel.dto.request.DepartureScheduleRequest;
import com.example.travel.dto.response.InfoBookingResponse;
import com.example.travel.service.DepartureCheduleService;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Validated //thêm vào để sử dụng Bean Validation cho @PathVariable
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/departureChedules")
public class DepartureCheduleController {

    private final DepartureCheduleService departureCheduleService;

    @GetMapping("/{id}/info-booking")
    public ResponseEntity<InfoBookingResponse> getInfoBooking(@PathVariable(value = "id")
                                                                 @NotNull(message = "id lịch khởi hành không được null")
                                                                 @Min(value = 1, message = "id lịch khởi hành phải >= 1")
                                                                 Integer id) {
        return ResponseEntity.ok(departureCheduleService.getInfoBooking(id));
    }

    @PostMapping
    public ResponseEntity<String> addDepartureChedule(@RequestBody List<DepartureScheduleRequest> deps, @RequestParam(value = "idTour") Integer idTour) {
        
        return ResponseEntity.ok(departureCheduleService.addDepartureChedule(deps, idTour));
    }

}
