package com.example.travel.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.travel.dto.request.TourRequestDTO;
import com.example.travel.dto.response.TourDetailResponseDTO;
import com.example.travel.dto.response.TourResponseDTO;
import com.example.travel.entity.DepartureScheduleEntity;
import com.example.travel.entity.TourEntity;
import com.example.travel.projection.TourProjection;

@Mapper(componentModel = "spring") // Để Spring quản lý Mapper như một Bean
public interface TourMapper {

    public default TourResponseDTO mapToTourResponseDTO_Projection(TourProjection t) {
        return TourResponseDTO.builder()
                .id(t.getMaTour())
                .tourName(t.getTenTour())
                .tourImage(t.getUrlHinhAnhChinh())
                .adultPrice(t.getGiaNguoiLon())
                .averageRating(t.getDiemDanhGiaTrungBinh())
                .describe(t.getMoTa())
                .destination(t.getThanhPho())
                .numberOfReview(t.getSoLuongDanhGia())
                .slot(t.getSlot())
                .startDate(t.getStartDate())
                .startTime(t.getStartTime())
                .build();
    }

    public default TourResponseDTO mapToTourResponseDTO_Entity(TourEntity t) {
        if (t == null) return null;

        // Lấy thông tin từ lịch trình đầu tiên (nếu có)
        DepartureScheduleEntity firstSchedule = (t.getDepartureSchedules() != null && !t.getDepartureSchedules().isEmpty()) 
                                            ? t.getDepartureSchedules().get(0) 
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

    //Tự động map qua TourDetailResponseDTO từ TourEntity
    @Mapping(source = "destination.city", target = "city") 
    //map destinationName ở DestinationEntity vì trong TourEntity không có trường destinationName mà nó nằm ở DestinationEntity
    TourDetailResponseDTO toTourDetailResponseDTO(TourEntity tourEntity);

    TourEntity toTourEntity(TourRequestDTO tourRequestDTO);
}
