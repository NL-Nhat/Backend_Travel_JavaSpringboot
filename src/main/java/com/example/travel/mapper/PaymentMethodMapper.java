package com.example.travel.mapper;

import org.mapstruct.Mapper;
import com.example.travel.dto.response.PaymentMethodResponseDTO;
import com.example.travel.entity.PaymentMethodEntity;

@Mapper(componentModel = "spring")
public interface PaymentMethodMapper {

    PaymentMethodResponseDTO toPaymentMethodResponseDTO(PaymentMethodEntity p);
}
