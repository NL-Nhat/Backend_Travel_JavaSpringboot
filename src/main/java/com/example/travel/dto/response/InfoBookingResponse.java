package com.example.travel.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InfoBookingResponse {

    private String tourName;
    private String city;
    private String image;
    private BigDecimal adultPrice;
    private BigDecimal childPrice;

    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
}
