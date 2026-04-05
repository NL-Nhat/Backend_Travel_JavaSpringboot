package com.example.travel.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentResponseDTO {

    private String nameMethod;
    private LocalDateTime paymentDate;
    private BigDecimal amount; //tiền đã thanh toán
}
