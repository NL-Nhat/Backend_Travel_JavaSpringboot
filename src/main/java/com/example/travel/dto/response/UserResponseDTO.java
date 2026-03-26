package com.example.travel.dto.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

    private String email;
    private String fullName;
    private String address;
    private String avatar;
    private Integer gender;
    private String phoneNumber;
    private LocalDate doB;
    
}
