package com.example.travel.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.travel.dto.request.BookingRequest;
import com.example.travel.dto.response.BookingHistoryResponse;
import com.example.travel.dto.response.BookingResponse;
import com.example.travel.dto.response.DetailBookingResponse;
import com.example.travel.entity.BookingEntity;
import com.example.travel.entity.DepartureScheduleEntity;
import com.example.travel.entity.UserEntity;
import com.example.travel.mapper.BookingMapper;
import com.example.travel.repository.BookingRepository;
import com.example.travel.repository.DepartureScheduleRepository;
import com.example.travel.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingService{

    private final BookingMapper bookingMapper;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final DepartureScheduleRepository departureScheduleRepository;

    public BookingResponse getDetailBooking(Integer id) {
        BookingEntity bookingEntity = bookingRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Ko tim thay booking voi id nay"));

        return bookingMapper.toBookingResponseDTO(bookingEntity);
    }

    public DetailBookingResponse getDetailBookingAndPayment(Integer idBooking) {
        BookingEntity bookingEntity = bookingRepository.findById(idBooking)
            .orElseThrow(() -> new RuntimeException("ko tim thay booking voi id nay"));

        return bookingMapper.toDetailBookingResponseDTO(bookingEntity);
    }

    @Transactional
    public Map<String, Object> bookTour(BookingRequest request, String userName) {
        BookingEntity bookingEntity = bookingMapper.toBookingEntity(request);

        UserEntity user = userRepository.findByUserName(userName)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy user với userName này"));

        DepartureScheduleEntity d = departureScheduleRepository.findById(request.getIdDepartureSchedule())
            .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch khởi hành với id này"));

        bookingEntity.setUser(user);
        bookingEntity.setDepartureSchedule(d);

        //Lưu tổng tiền
        BigDecimal adultPrice = d.getTour().getAdultPrice();
        BigDecimal childPrice = d.getTour().getChildPrice();
        //Vì BigDecimal không có phép tính + - * / nên phải dùng meThod
        // Không thể + - * / Integer với BigDecimal nên phải ép kiểu qua BigDecimal
        BigDecimal totalAdult = adultPrice.multiply(BigDecimal.valueOf(request.getAdultNumber())); //ép liểu b.getAdultNumber() từ Integer sang BigDecimal 
        BigDecimal totalChild = childPrice.multiply(BigDecimal.valueOf(request.getChildNumber())); // multiply = phép nhân
        BigDecimal totalAmount = totalAdult.add(totalChild); // add = phép cộng, lấy totalAdult + totalChild
        bookingEntity.setTotalAmount(totalAmount);

        bookingEntity = bookingRepository.save(bookingEntity);

        Map<String, Object> result = new HashMap<>();
        result.put("message", "Đặt tour thành công");
        result.put("id", bookingEntity.getId());

        return result;

    }

    public List<BookingHistoryResponse> getBookingHistory(String userName) {
        UserEntity user = userRepository.findByUserName(userName)
            .orElseThrow(() -> new RuntimeException("Ko tim thấy user với userName này"));

        List<BookingEntity> bookings = bookingRepository.findByUser(user);

        List<BookingHistoryResponse> result = new ArrayList<>(); 

        for (BookingEntity bookingEntity : bookings) {
            BookingHistoryResponse bookingHistoryDTO = bookingMapper.toBookingHistoryResponseDTO(bookingEntity);
            result.add(bookingHistoryDTO);
        }

        return result;
    }
}
