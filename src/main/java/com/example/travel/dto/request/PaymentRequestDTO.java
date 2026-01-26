package com.example.travel.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentRequestDTO {

    @NotNull(message = "id đặt tour ko đc null")
    @Min(value = 1, message = "id đặt tour phải >= 1")
    private Integer idBooking;

    @NotNull(message = "id PTTT ko đc null")
    @Min(value = 1, message = "id PTTT phải >= 1")
    private Integer idMethod;

    @NotNull(message = "tổng tiền ko đc null")
    @Min(value = 0, message = "tổng tiền phải >= 0")
    private BigDecimal totalAmount;
}
