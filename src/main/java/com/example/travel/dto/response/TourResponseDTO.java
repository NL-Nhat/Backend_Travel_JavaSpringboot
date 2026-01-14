package com.example.travel.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class TourResponseDTO {

    private Integer id;
    private String tourName;
    private String tourImage;
    private BigDecimal adultPrice;
    private BigDecimal averageRating;
    private String describe;
    private String destination;
    private Integer numberOfReview;
    private Integer slot;
    private LocalDate startDate;
    private LocalTime startTime;
}
