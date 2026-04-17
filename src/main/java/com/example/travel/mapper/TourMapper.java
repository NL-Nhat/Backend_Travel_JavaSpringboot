package com.example.travel.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.example.travel.dto.request.TourRequest;
import com.example.travel.dto.response.TourDetailResponse;
import com.example.travel.dto.response.TourResponse;
import com.example.travel.entity.TourEntity;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE) 
// Để Spring quản lý Mapper như một Bean, bỏ qua các giá trị null
public interface TourMapper {

    // public default TourResponseDTO mapToTourResponseDTO_Projection(TourProjection t) {
    //     return TourResponseDTO.builder()
    //             .id(t.getMaTour())
    //             .tourName(t.getTenTour())
    //             .image(t.getUrlHinhAnhChinh())
    //             .adultPrice(t.getGiaNguoiLon())
    //             .averageRating(t.getDiemDanhGiaTrungBinh())
    //             .describe(t.getMoTa())
    //             .city(t.getThanhPho())
    //             .numberOfReview(t.getSoLuongDanhGia())
    //             .build();
    // }

    @Mapping(source = "destination.city", target = "city")
    TourResponse toTourResponseDTO(TourEntity tourEntity);

    // Chuyển từ Request DTO sang Entity để lưu DB
    // User toUser(UserRegistrationRequest request);

    // Chuyển từ Entity sang Response DTO để trả về Client
    // UserResponse toUserResponse(User user);  

    //Tự động map qua TourDetailResponseDTO từ TourEntity
    @Mapping(source = "tourEntity", target = "tourResponseDTO") 
    TourDetailResponse toTourDetailResponseDTO(TourEntity tourEntity);

    TourEntity toTourEntity(TourRequest tourRequestDTO);

    // Hàm update
    void updateTourFromDto(TourRequest dto, @MappingTarget TourEntity entity);
}
