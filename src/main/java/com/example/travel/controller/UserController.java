package com.example.travel.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.travel.dto.response.UserResponseDTO;
import com.example.travel.service.UserService;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


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

    @GetMapping("/get-profile/{id}")
    public ResponseEntity<UserResponseDTO> getProfile(@PathVariable(value = "id")
                            @NotNull(message = "id user không được null")
                            @Min(value = 1, message = "id user phải >= 1")
                            Integer id) {

        return ResponseEntity.ok(userService.getProfile(id));
    }
}
