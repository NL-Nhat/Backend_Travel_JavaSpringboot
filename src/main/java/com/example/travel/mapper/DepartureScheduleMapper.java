package com.example.travel.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.travel.dto.response.InfoBookingResponseDTO;
import com.example.travel.entity.DepartureScheduleEntity;

@Mapper(componentModel = "spring")
public interface DepartureScheduleMapper {

    //Tự động map qua InfoResponseDTO từ DepartureCheduleEntity
    
    @Mapping(source = "tour.tourName", target = "tourName")
    @Mapping(source = "tour.image", target = "image")
    @Mapping(source = "tour.destination.city", target = "city")
    @Mapping(source = "d.id", target = "idDepartureSchedule")
    InfoBookingResponseDTO toInfoResponseDTO(DepartureScheduleEntity d);
}
