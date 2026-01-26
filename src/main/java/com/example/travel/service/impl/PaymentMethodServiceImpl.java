package com.example.travel.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.travel.dto.response.PaymentMethodResponseDTO;
import com.example.travel.entity.PaymentMethodEntity;
import com.example.travel.mapper.PaymentMethodMapper;
import com.example.travel.repository.PaymentMethodRepository;
import com.example.travel.service.PaymentMethodService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentMethodServiceImpl implements PaymentMethodService{

    private final PaymentMethodRepository p;
    private final PaymentMethodMapper pm;

    @Override
    public List<PaymentMethodResponseDTO> getAllPaymentMethodByStatus(String status) {
        List<PaymentMethodEntity> pMethodEntities = p.findByStatus(status);

        return pMethodEntities.stream().map(pm::toPaymentMethodResponseDTO).collect(Collectors.toList());
    }

}
