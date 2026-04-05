package com.example.travel.dto.response;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookingResponseDTO {

    InfoBookingResponseDTO infoBookingResponseDTO;

    private String nameGuest;
    private String phoneNumber;
    private String email;
    private String address;

    private Integer adultNumber;
    private Integer childNumber;
    private BigDecimal totalAmount;
    
}
