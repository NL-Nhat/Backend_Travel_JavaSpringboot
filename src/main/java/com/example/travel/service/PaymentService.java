package com.example.travel.service;

import com.example.travel.dto.request.PaymentRequestDTO;
import com.example.travel.dto.response.InfoTicketQR;

public interface PaymentService {

    public InfoTicketQR paymentBookTour(PaymentRequestDTO p);
}
