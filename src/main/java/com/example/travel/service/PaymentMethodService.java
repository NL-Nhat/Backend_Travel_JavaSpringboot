package com.example.travel.service;

import java.util.List;

import com.example.travel.dto.response.PaymentMethodResponseDTO;

public interface PaymentMethodService {

    public List<PaymentMethodResponseDTO> getAllPaymentMethodByStatus(String status);
}
