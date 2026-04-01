package com.example.travel.mapper;

import org.springframework.stereotype.Component;
import com.example.travel.dto.response.ReviewResponseDTO;
import com.example.travel.projection.ReviewProjection;

@Component
public class ReviewMapper {

    public ReviewResponseDTO mapToReviewResponseDTO(ReviewProjection r) {
        return ReviewResponseDTO.builder()
                .numberStar(r.getNumberStar())
                .tourName(r.getTourName())
                .comment(r.getComment())
                .fullName(r.getFullName())
                .avatar(r.getAvatar())
                .createAt(r.getCreateAt())
                .build();
    }
}
