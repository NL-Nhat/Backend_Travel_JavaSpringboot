package com.example.travel.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.travel.dto.request.BookingRequestDTO;
import com.example.travel.service.BookingService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Validated //thêm vào để sử dụng Bean Validation cho @PathVariable
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getDetailBooking(@PathVariable(value = "id") 
                                            @NotNull(message = "id lịch khởi hành không được null")
                                            @Min(value = 1, message = "id lịch khởi hành phải >= 1")
                                            Integer id) {
        
        return ResponseEntity.ok(bookingService.getDetailBooking(id));
    }
    
    @GetMapping("/booking-payment/{id}")
    public ResponseEntity<?> getDetailBookingAndPayment(@PathVariable(value = "id") 
                                            @NotNull(message = "id lịch khởi hành không được null")
                                            @Min(value = 1, message = "id lịch khởi hành phải >= 1")
                                            Integer id) {
        return ResponseEntity.ok(bookingService.getDetailBookingAndPayment(id));
    }
    

    @PostMapping
    public ResponseEntity<?> bookTour(@Valid @RequestBody BookingRequestDTO request,
                                                        @AuthenticationPrincipal UserDetails userDetails) {

        String userName = userDetails.getUsername();

        Map<String, Object> result = new HashMap<>();

        result = bookingService.bookTour(request, userName);

        return ResponseEntity.status(201).body(result);  //Khi tạo mới resource → nên dùng 201 Created
    }

    // public void checkError(BookingRequestDTO b) throws FieldRequiredException {
    //     if(b.getNameGuest() == null || b.getNameGuest().isEmpty()) {
    //         throw new FieldRequiredException("Tên khách hàng bị null");
    //     }
    // }
}
