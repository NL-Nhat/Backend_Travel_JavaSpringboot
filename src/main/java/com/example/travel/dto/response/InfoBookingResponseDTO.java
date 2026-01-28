package com.example.travel.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InfoBookingResponseDTO {

    private Integer idDepartureSchedule;
    private String tourName;
    private String city;
    private String image;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
}
