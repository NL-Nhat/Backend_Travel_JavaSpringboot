package com.example.travel.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor //Có @Builder thì phải thêm @NoArgsConstructor để sinh constructor rỗng vì có @Builder nên không tự sinh được
@AllArgsConstructor
public class BookingRequestDTO {

    @NotNull(message = "idUser không được null")
    @Min(value = 1, message = "idUser phải >= 1")
    private Integer idUser;

    @NotNull(message = "id lịch khởi hành không được null")
    @Min(value = 1, message = "id lich khởi hành phải >= 1")
    private Integer idDepartureSchedule;

    @NotNull(message = "Số người lớn không được null")
    @Min(value = 1, message = "số người lớn phải >= 1")
    private Integer adultNumber;

    @NotNull(message = "Số trẻ em không được null")
    @Min(value = 0, message = "số trẻ em phải >= 0")
    private Integer childNumber;

    @NotBlank(message = "Tên khách hàng không được để trống")
    private String nameGuest;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(
        regexp = "^0\\d{9}$",
        message = "Số điện thoại không đúng định dạng"
    )
    private String phoneNumber;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    private String email;

    @NotNull(message = "Giới tính không được null")
    @Min(value = 1, message = "Giới tính không hợp lệ")
    @Max(value = 3, message = "Giới tính không hợp lệ")
    private Integer gender;

    @NotNull(message = "Ngày sinh không được null")
    @Past(message = "Ngày sinh phải là ngày trong quá khứ")
    private LocalDate doB;

    private String note;
}
