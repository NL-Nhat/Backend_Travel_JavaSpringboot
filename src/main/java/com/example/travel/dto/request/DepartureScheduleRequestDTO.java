package com.example.travel.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DepartureScheduleRequestDTO {

    private Integer idTour;
    private LocalDate startDate;
    private LocalTime startTime;
    private LocalDate endDate;
    private LocalTime endTime;
    private Integer huongDanVien;
    private Integer maxGuest;
    private List<ScheduleRequestDTO> schedules;
}
