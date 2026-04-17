package com.example.travel.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.travel.dto.response.PaymentMethodResponse;
import com.example.travel.entity.PaymentMethodEntity;
import com.example.travel.mapper.PaymentMethodMapper;
import com.example.travel.repository.PaymentMethodRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentMethodService {

    private final PaymentMethodRepository p;
    private final PaymentMethodMapper pm;

    public List<PaymentMethodResponse> getAllPaymentMethodByStatus(String status) {
        List<PaymentMethodEntity> pMethodEntities = p.findByStatus(status);

        return pMethodEntities.stream().map(pm::toPaymentMethodResponseDTO).collect(Collectors.toList());
    }

}
