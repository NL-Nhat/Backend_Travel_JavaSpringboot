package com.example.travel.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.travel.dto.request.PaymentRequestDTO;
import com.example.travel.dto.response.InfoTicketQR;
import com.example.travel.entity.BookingEntity;
import com.example.travel.entity.PaymentEntity;
import com.example.travel.entity.PaymentMethodEntity;
import com.example.travel.mapper.BookingMapper;
import com.example.travel.repository.BookingRepository;
import com.example.travel.repository.PaymentMethodRepository;
import com.example.travel.repository.PaymentRepository;
import com.example.travel.service.PaymentService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService{

    private final PaymentRepository pr;
    private final BookingMapper bm;
    private final BookingRepository br;
    private final PaymentMethodRepository pmr;

    @Override
    @Transactional
    public Map<String, Object> paymentBookTour(PaymentRequestDTO p) {
        BookingEntity b = br.findById(p.getIdBooking())
                .orElseThrow(() -> new RuntimeException("Ko tìm thấy tour đã đặt với id này"));

        if(b.getBookingStatus().equals("Đã thanh toán")) {
            throw new RuntimeException("Đơn đặt tour này đã được thanh toán");
        }

        PaymentMethodEntity pme = pmr.findById(p.getIdMethod())
                .orElseThrow(() -> new RuntimeException("Ko tìm thấy phương thức thanh toán với id này"));

        PaymentEntity pe = new PaymentEntity();
        pe.setBooking(b);
        pe.setPaymentMethod(pme);
        pe.setAmount(p.getTotalAmount());

        pr.save(pe);

        b.setPaymentStatus("Đã thanh toán");
        br.save(b);

        InfoTicketQR infoTicketQR = bm.toInfoTicketQR(b);

        Map<String, Object> result = new HashMap<>();
        result.put("data", infoTicketQR);
        result.put("message", "Thanh toán thành công");

        return result;
    }
}
