package com.example.travel.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDTO {
    @NotBlank(message = "username ko đc trống")
    private String userName;

    @NotBlank(message = "pass ko đc trống")
    private String passWord;

    @NotBlank(message = "fullname ko đc trống")
    private String fullName;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    private String email;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(
        regexp = "^0\\d{9}$",
        message = "Số điện thoại không đúng định dạng"
    )
    private String phoneNumber;

    @NotNull(message = "Giới tính không được null")
    @Min(value = 1, message = "Giới tính không hợp lệ")
    @Max(value = 3, message = "Giới tính không hợp lệ")
    private Integer gender;
}
