package com.example.travel.mapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.stereotype.Component;

import com.example.travel.dto.response.TourResponseDTO;

@Component
public class TourMapper {

    public TourResponseDTO mapToTourResponseDTO(Object [] row) {
        return TourResponseDTO.builder()
                .id(((Number) row[0]).intValue())
                .tourName((String) row[1])
                .tourImage((String) row[2])
                .adultPrice(((BigDecimal) row[3]))
                .averageRating(((BigDecimal) row[4]))
                .describe((String) row[5])
                .destination((String) row[6])
                .numberOfReview(((Number) row[7]).intValue())
                .slot(((Number) row[8]).intValue())
                .startDate(((LocalDate) row[9]))
                .startTime((LocalTime) row[10])
                .build();
    }
}
