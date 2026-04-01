package com.example.travel.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.travel.dto.request.BookingRequestDTO;
import com.example.travel.dto.response.BookingResponseDTO;
import com.example.travel.service.BookingService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingResponseDTO> bookTour(@Valid @RequestBody BookingRequestDTO request,
                                                        @AuthenticationPrincipal UserDetails userDetails) {

        String userName = userDetails.getUsername();

        BookingResponseDTO response = bookingService.bookTour(request, userName);

        return ResponseEntity.status(201).body(response);  //Khi tạo mới resource → nên dùng 201 Created
    }

    // public void checkError(BookingRequestDTO b) throws FieldRequiredException {
    //     if(b.getNameGuest() == null || b.getNameGuest().isEmpty()) {
    //         throw new FieldRequiredException("Tên khách hàng bị null");
    //     }
    // }
}
