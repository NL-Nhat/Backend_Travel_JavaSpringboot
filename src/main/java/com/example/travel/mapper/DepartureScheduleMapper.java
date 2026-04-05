package com.example.travel.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.example.travel.dto.response.InfoBookingResponseDTO;
import com.example.travel.entity.DepartureScheduleEntity;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE) 
// Để Spring quản lý Mapper như một Bean, bỏ qua các giá trị null
public interface DepartureScheduleMapper {

    //Tự động map qua InfoResponseDTO từ DepartureCheduleEntity
    
    @Mapping(source = "tour.tourName", target = "tourName")
    @Mapping(source = "tour.image", target = "image")
    @Mapping(source = "tour.destination.city", target = "city")
    @Mapping(source = "tour.adultPrice", target = "adultPrice")
    @Mapping(source = "tour.childPrice", target = "childPrice")
    InfoBookingResponseDTO toInfoResponseDTO(DepartureScheduleEntity d);

}
