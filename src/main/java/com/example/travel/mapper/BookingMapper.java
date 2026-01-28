package com.example.travel.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.example.travel.dto.request.BookingRequestDTO;
import com.example.travel.dto.response.BookingResponseDTO;
import com.example.travel.dto.response.InfoTicketQR;
import com.example.travel.entity.BookingEntity;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    BookingEntity toBookingEntity(BookingRequestDTO bookingRequestDTO);

    @Mapping(source = "departureSchedule.startDate", target = "startDate")
    @Mapping(source = "departureSchedule.startTime", target = "startTime")
    @Mapping(source = "departureSchedule.tour.tourName", target = "tourName")
    BookingResponseDTO toBookingResponseDTO(BookingEntity bookingEntity);

    @Mapping(source = "departureSchedule.startDate", target = "startDate")
    @Mapping(source = "departureSchedule.startTime", target = "startTime")
    @Mapping(source = "departureSchedule.tour.tourName", target = "tourName")
    InfoTicketQR toInfoTicketQR(BookingEntity bookingEntity);
}
