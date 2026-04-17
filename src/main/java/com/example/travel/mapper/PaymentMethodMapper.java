package com.example.travel.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.example.travel.dto.response.PaymentMethodResponse;
import com.example.travel.entity.PaymentMethodEntity;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE) 
// Để Spring quản lý Mapper như một Bean, bỏ qua các giá trị null
public interface PaymentMethodMapper {

    PaymentMethodResponse toPaymentMethodResponseDTO(PaymentMethodEntity p);
}
