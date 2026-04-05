package com.example.travel.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class DetailBookingResponseDTO {

    //Info booking
    private String idTicket;
    private String paymentStatus;
    private String bookingStatus;
    private LocalDateTime bookedDate;
    private Integer adultNumber;
    private Integer childNumber;
    private BigDecimal totalAmount;
    private String nameGuest;
    private String phoneNumber;
    private String email;
    private LocalDate doB;

    //Info hướng dẫn viên
    private String guideName;
    private String avatar;

    //Info tour
    private Integer idTour;
    private String tourName;
    private String image;
    private String destination;
    private BigDecimal adultPrice;
    private BigDecimal childPrice;

    //Info thanh toán
    private String nameMethod;
    private LocalDateTime paymentDate;
    
    //Info lịch khởi hành và lịch trình
    DepartureScheduleResponseDTO departureScheduleResponseDTO;
    List<ScheduleResponseDTO> scheduleResponseDTOs;

}
