package com.example.travel.mapper;

import org.springframework.stereotype.Component;
import com.example.travel.dto.response.ReviewResponse;
import com.example.travel.projection.ReviewProjection;

@Component
public class ReviewMapper {

    public ReviewResponse mapToReviewResponseDTO(ReviewProjection r) {
        return ReviewResponse.builder()
                .numberStar(r.getDiemSo())
                .tourName(r.getTenTour())
                .comment(r.getBinhLuan())
                .fullName(r.getHoTen())
                .avatar(r.getAnhDaiDien())
                .createAt(r.getThoiGianTao())
                .build();
    }
}
