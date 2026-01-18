package com.example.travel.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ScheduleResponseDTO {

    private Integer id;
    private String describe;
    private String work;
    private LocalDate date;
    private LocalTime time;
}
