package com.example.travel.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DepartureCheduleResponseDTO {

    private Integer id;
    private LocalDate startDate;
    private LocalTime startTime;
    private LocalDate endDate;
    private Integer numberGuestBooked;
    private Integer maxGuest;

}
