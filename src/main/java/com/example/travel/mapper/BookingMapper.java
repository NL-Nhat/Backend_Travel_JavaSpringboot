package com.example.travel.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.example.travel.dto.request.BookingRequestDTO;
import com.example.travel.dto.response.BookingResponseDTO;
import com.example.travel.entity.BookingEntity;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    BookingEntity toBookingEntity(BookingRequestDTO bookingRequestDTO);

    @Mapping(source = "departureChedule.startDate", target = "startDate")
    @Mapping(source = "departureChedule.startTime", target = "startTime")
    @Mapping(source = "departureChedule.tour.tourName", target = "tourName")
    BookingResponseDTO toBookingResponseDTO(BookingEntity bookingEntity);
}
