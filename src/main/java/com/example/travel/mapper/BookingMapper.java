package com.example.travel.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.example.travel.dto.request.BookingRequest;
import com.example.travel.dto.response.BookingHistoryResponse;
import com.example.travel.dto.response.BookingResponse;
import com.example.travel.dto.response.DetailBookingResponse;
import com.example.travel.dto.response.InfoTicketQRResponse;
import com.example.travel.entity.BookingEntity;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE) 
// Để Spring quản lý Mapper như một Bean, bỏ qua các giá trị null
public interface BookingMapper {

    BookingEntity toBookingEntity(BookingRequest bookingRequestDTO);

    @Mapping(source = "departureSchedule.tour.tourName", target = "infoBookingResponseDTO.tourName")
    @Mapping(source = "departureSchedule.tour.image", target = "infoBookingResponseDTO.image")
    @Mapping(source = "departureSchedule.tour.adultPrice", target = "infoBookingResponseDTO.adultPrice")
    @Mapping(source = "departureSchedule.tour.childPrice", target = "infoBookingResponseDTO.childPrice")
    @Mapping(source = "departureSchedule.tour.destination.city", target = "infoBookingResponseDTO.city")
    BookingResponse toBookingResponseDTO(BookingEntity bookingEntity);

    @Mapping(source = "id", target = "idBooking")
    @Mapping(source = "departureSchedule.tour.tourName", target = "tourName")
    InfoTicketQRResponse toInfoTicketQR(BookingEntity bookingEntity);

    @Mapping(source = "departureSchedule.huongDanVien", target = "huongDanVien")
    @Mapping(source = "departureSchedule.tour.id", target = "idTour")
    @Mapping(source = "departureSchedule.schedules", target = "scheduleResponseDTOs")
    @Mapping(source = "payment.paymentMethod.nameMethod", target = "payment.nameMethod")
    @Mapping(source = "bookingEntity", target = "bookingResponseDTO")
    DetailBookingResponse toDetailBookingResponseDTO(BookingEntity bookingEntity);

    @Mapping(source = "departureSchedule.tour.tourName", target = "infoBookingResponseDTO.tourName")
    @Mapping(source = "departureSchedule.tour.image", target = "infoBookingResponseDTO.image")
    @Mapping(source = "departureSchedule.tour.destination.city", target = "infoBookingResponseDTO.city")
    BookingHistoryResponse toBookingHistoryResponseDTO(BookingEntity bookingEntity);
}
