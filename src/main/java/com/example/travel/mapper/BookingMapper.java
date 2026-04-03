package com.example.travel.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.example.travel.dto.request.BookingRequestDTO;
import com.example.travel.dto.response.BookingResponseDTO;
import com.example.travel.dto.response.InfoTicketQRResponseDTO;
import com.example.travel.entity.BookingEntity;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE) 
// Để Spring quản lý Mapper như một Bean, bỏ qua các giá trị null
public interface BookingMapper {

    BookingEntity toBookingEntity(BookingRequestDTO bookingRequestDTO);

    @Mapping(source = "departureSchedule.startDate", target = "startDate")
    @Mapping(source = "departureSchedule.startTime", target = "startTime")
    @Mapping(source = "departureSchedule.tour.tourName", target = "tourName")
    BookingResponseDTO toBookingResponseDTO(BookingEntity bookingEntity);

    @Mapping(source = "id", target = "idBooking")
    @Mapping(source = "departureSchedule.tour.tourName", target = "tourName")
    InfoTicketQRResponseDTO toInfoTicketQR(BookingEntity bookingEntity);
}
