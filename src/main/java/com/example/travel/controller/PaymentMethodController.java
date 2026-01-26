package com.example.travel.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.travel.dto.response.PaymentMethodResponseDTO;
import com.example.travel.service.PaymentMethodService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/paymentmethods")
@RequiredArgsConstructor
public class PaymentMethodController {

    private final PaymentMethodService ps;

    @GetMapping("/all-by-status")
    public ResponseEntity<List<PaymentMethodResponseDTO>> getAllPaymentMethodByStatus() {
        return ResponseEntity.ok(ps.getAllPaymentMethodByStatus("Hoạt động"));
    }

}
