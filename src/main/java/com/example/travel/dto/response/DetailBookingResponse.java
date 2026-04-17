package com.example.travel.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DetailBookingResponse {

    //Info booking
    private String idTicket;
    private String paymentStatus;
    private String bookingStatus;
    private LocalDateTime bookedDate;

    //Info hướng dẫn viên
    private UserResponse huongDanVien;

    /*id tour khi khách hàng muốn xem tour ở trang chi tiết booking 
    thì chỉ cần gọi api getDetailTour sử dụng idTour */
    private Integer idTour;

    private PaymentResponse payment;
    
    private BookingResponse bookingResponseDTO;

    //Info lịch trình
    private List<ScheduleResponse> scheduleResponseDTOs;

}
