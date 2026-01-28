package com.example.travel.service.impl;

import java.math.BigDecimal;
import java.security.SecureRandom;

import org.springframework.stereotype.Service;

import com.example.travel.dto.request.BookingRequestDTO;
import com.example.travel.dto.response.BookingResponseDTO;
import com.example.travel.entity.BookingEntity;
import com.example.travel.entity.DepartureScheduleEntity;
import com.example.travel.entity.UserEntity;
import com.example.travel.mapper.BookingMapper;
import com.example.travel.repository.BookingRepository;
import com.example.travel.repository.DepartureCheduleRepository;
import com.example.travel.repository.UserRepository;
import com.example.travel.service.BookingService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService{

    private final BookingMapper bookingMapper;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final DepartureCheduleRepository departureCheduleRepository;

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
    public BookingResponseDTO bookTour(BookingRequestDTO b) {
        BookingEntity bookingEntity = bookingMapper.toBookingEntity(b);

        UserEntity user = userRepository.findById(b.getIdUser())
            .orElseThrow(() -> new RuntimeException("Không tìm thấy user với id này"));

        DepartureScheduleEntity d = departureCheduleRepository.findById(b.getIdDepartureSchedule())
            .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch khởi hành với id này"));

        bookingEntity.setUser(user);
        bookingEntity.setDepartureSchedule(d);
        //Lưu mã vé ngẫu nhiên, unique
        bookingEntity.setIdTicket(generateUniqueTicketCode());

        //Lưu tổng tiền
        BigDecimal adultPrice = d.getTour().getAdultPrice();
        BigDecimal childPrice = d.getTour().getChildPrice();
        //Vì BigDecimal không có phép tính + - * / nên phải dùng meThod
        // Không thể + - * / Integer với BigDecimal nên phải ép kiểu qua BigDecimal
        BigDecimal totalAdult = adultPrice.multiply(BigDecimal.valueOf(b.getAdultNumber())); //ép liểu b.getAdultNumber() từ Integer sang BigDecimal 
        BigDecimal totalChild = childPrice.multiply(BigDecimal.valueOf(b.getChildNumber())); // multiply = phép nhân
        BigDecimal totalAmount = totalAdult.add(totalChild); // add = phép cộng, lấy totalAdult + totalChild
        bookingEntity.setTotalAmount(totalAmount);

        bookingEntity = bookingRepository.save(bookingEntity);

        return bookingMapper.toBookingResponseDTO(bookingEntity);

    }
}
