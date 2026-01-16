package com.example.travel.mapper;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.example.travel.dto.response.ReviewResponseDTO;

@Component
public class ReviewMapper {

    public ReviewResponseDTO mapToReviewResponseDTO(Object [] row) {
        return ReviewResponseDTO.builder()
                .numberStar(((Number) row[0]).intValue())
                .tourName((String) row[1])
                .comment((String) row[2])
                .userName((String) row[3])
                .avatar((String) row[4])
                .createAt((LocalDateTime) row[5])
                .build();
    }
}
