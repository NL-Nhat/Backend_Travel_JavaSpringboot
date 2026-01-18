package com.example.travel.mapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import org.mapstruct.Mapper;

import com.example.travel.dto.response.TourDetailResponseDTO;
import com.example.travel.dto.response.TourResponseDTO;
import com.example.travel.entity.DepartureCheduleEntity;
import com.example.travel.entity.TourEntity;

@Mapper(componentModel = "spring") // Để Spring quản lý Mapper như một Bean
public interface TourMapper {

    public default TourResponseDTO mapToTourResponseDTO_Object(Object [] row) {
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

    public default TourResponseDTO mapToTourResponseDTO_Entity(TourEntity t) {
        if (t == null) return null;

        // Lấy thông tin từ lịch trình đầu tiên (nếu có)
        DepartureCheduleEntity firstSchedule = (t.getDepartureChedules() != null && !t.getDepartureChedules().isEmpty()) 
                                            ? t.getDepartureChedules().get(0) 
                                            : null;

        return TourResponseDTO.builder()
            .id(t.getId())
            .tourName(t.getTourName())
            .tourImage(t.getImage()) // Lưu ý: DTO dùng 'tourImage', Entity dùng 'image'
            .adultPrice(t.getAdultPrice())
            .averageRating(t.getAverageRating())
            .describe(t.getDescribe())
            .destination(t.getDestination() != null ? t.getDestination().getCity() : null)
            .numberOfReview(t.getNumberOfReview())
            .slot(firstSchedule != null ? firstSchedule.getMaxGuest() - firstSchedule.getNumberGuestBooked() : 0)
            .startDate(firstSchedule != null ? firstSchedule.getStartDate() : null)
            .startTime(firstSchedule != null ? firstSchedule.getStartTime() : null)
            .build();
    }

    // Chuyển từ Request DTO sang Entity để lưu DB
    // User toUser(UserRegistrationRequest request);

    // // Chuyển từ Entity sang Response DTO để trả về Client
    // UserResponse toUserResponse(User user);

    TourDetailResponseDTO toTourDetailResponseDTO(TourEntity tourEntity);
}
