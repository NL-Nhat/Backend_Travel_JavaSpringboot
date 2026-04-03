package com.example.travel.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookingResponseDTO {

    private Integer id;
    private LocalDate startDate;
    private LocalTime startTime;
    private String tourName;
    private String nameGuest;
    private String phoneNumber;
    private String email;
    private Integer adultNumber;
    private Integer childNumber;
    private BigDecimal totalAmount;
}
