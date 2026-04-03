package com.example.travel.service;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.travel.dto.request.PaymentRequestDTO;
import com.example.travel.dto.response.InfoTicketQRResponseDTO;
import com.example.travel.entity.BookingEntity;
import com.example.travel.entity.PaymentEntity;
import com.example.travel.entity.PaymentMethodEntity;
import com.example.travel.mapper.BookingMapper;
import com.example.travel.repository.BookingRepository;
import com.example.travel.repository.PaymentMethodRepository;
import com.example.travel.repository.PaymentRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingMapper bookingMapper;
    private final BookingRepository bookingRepository;
    private final PaymentMethodRepository paymentMethodRepository;

    //Dùng để tạo mã vé điện tử ngẫu nhiên
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int TICKET_LENGTH = 10;
    private static final SecureRandom RANDOM = new SecureRandom();

    private String generateUniqueTicketCode() {
        String code;
        do {
            code = generateRandomCode();
        } while (bookingRepository.existsByIdTicket(code));
        return code;
    }

    private String generateRandomCode() {
        StringBuilder sb = new StringBuilder(TICKET_LENGTH);
        for (int i = 0; i < TICKET_LENGTH; i++) {
            sb.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }

    @Transactional
    public Map<String, Object> paymentBookTour(PaymentRequestDTO request) {
        BookingEntity bookingEntity = bookingRepository.findById(request.getIdBooking())
                .orElseThrow(() -> new RuntimeException("Ko tìm thấy tour đã đặt với id này"));

        if(bookingEntity.getBookingStatus().equals("Đã thanh toán")) {
            throw new RuntimeException("Đơn đặt tour này đã được thanh toán");
        }

        PaymentMethodEntity paymentMethodEntity = paymentMethodRepository.findById(request.getIdMethod())
                .orElseThrow(() -> new RuntimeException("Ko tìm thấy phương thức thanh toán với id này"));

        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setBooking(bookingEntity);
        paymentEntity.setPaymentMethod(paymentMethodEntity);
        paymentEntity.setAmount(request.getTotalAmount());

        paymentRepository.save(paymentEntity);

        bookingEntity.setPaymentStatus("Đã thanh toán");
        bookingEntity.setIdTicket(generateUniqueTicketCode());
        bookingRepository.save(bookingEntity);

        InfoTicketQRResponseDTO infoTicketQR = bookingMapper.toInfoTicketQR(bookingEntity);

        Map<String, Object> result = new HashMap<>();
        result.put("data", infoTicketQR);
        result.put("message", "Thanh toán thành công");

        return result;
    }
}
