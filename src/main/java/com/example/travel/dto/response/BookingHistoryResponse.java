package com.example.travel.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class BookingHistoryResponse {

    private Integer id;
    private String paymentStatus;
    private String bookingStatus;
    private LocalDateTime bookedDate;
    private BigDecimal totalAmount;

    private Integer adultNumber;
    private Integer childNumber;

    private InfoBookingResponse infoBookingResponseDTO;

}
