package com.example.travel.dto.request;

import java.math.BigDecimal;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TourRequestDTO {

    @NotBlank(message = "tên tour ko đc để trống")
    private String tourName;

    private String describe;

    @NotNull(message = "giá người lớn ko đc null")
    @Min(value = 0, message = "giá người lớn phải >= 0")
    private BigDecimal adultPrice;

    @NotNull(message = "giá trẻ em ko đc null")
    @Min(value = 0, message = "giá trẻ em phải >= 0")
    private BigDecimal childPrice;

    @NotBlank(message = "ảnh ko đc để trống")
    private String image;

    @NotBlank(message = "trạng thái không được để trống")
    @Pattern(regexp = "^(Đang mở|Tạm dừng)$", 
         message = "Trạng thái chỉ có thể là 'Đang mở' hoặc 'Tạm dừng'")
    private String status;
}
