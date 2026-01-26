package com.example.travel.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentMethodResponseDTO {

    private Integer id;
    private String nameMethod;
}
