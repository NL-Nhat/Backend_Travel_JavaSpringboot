package com.example.travel.dto.request;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {

    private String email;
    private String fullName;
    private String address;
    private Integer gender;
    private String phoneNumber;
    private LocalDate doB;
}
