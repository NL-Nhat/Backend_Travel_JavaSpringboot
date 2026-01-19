package com.example.travel.mapper;

import org.springframework.stereotype.Component;
import com.example.travel.dto.response.ReviewResponseDTO;
import com.example.travel.projection.ReviewProjection;

@Component
public class ReviewMapper {

    public ReviewResponseDTO mapToReviewResponseDTO(ReviewProjection r) {
        return ReviewResponseDTO.builder()
                .numberStar(r.getDiemSo())
                .tourName(r.getTenTour())
                .comment(r.getBinhLuan())
                .userName(r.getHoTen())
                .avatar(r.getAnhDaiDien())
                .createAt(r.getThoiGianTao())
                .build();
    }
}
