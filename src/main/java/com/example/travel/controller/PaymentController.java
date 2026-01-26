package com.example.travel.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.travel.dto.request.PaymentRequestDTO;
import com.example.travel.dto.response.InfoTicketQR;
import com.example.travel.service.PaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService ps;

    @PostMapping("/payment-book-tour")
    public ResponseEntity<InfoTicketQR> paymentBookTour(@RequestBody PaymentRequestDTO p) {
        return ResponseEntity.ok(ps.paymentBookTour(p));
    }
}
