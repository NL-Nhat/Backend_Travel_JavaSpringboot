package com.example.travel.service;

import java.util.Map;

import com.example.travel.dto.request.PaymentRequestDTO;

public interface PaymentService {

    public Map<String, Object> paymentBookTour(PaymentRequestDTO p);
}
