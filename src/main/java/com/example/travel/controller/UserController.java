package com.example.travel.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.travel.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/count-user")
    public ResponseEntity<?> getMethodName() {
        return ResponseEntity.ok(userService.countNumberUser());
    }

    @GetMapping("/get-profile")
    public ResponseEntity<?> getProfile(@AuthenticationPrincipal UserDetails userDetails) {

        // Nếu userDetails là null nghĩa là chưa đăng nhập hoặc token không hợp lệ/hết hạn
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Vui lòng đăng nhập!");
        }

        String userName = userDetails.getUsername();

        return ResponseEntity.ok(userService.getProfile(userName));
    }
}
