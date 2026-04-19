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
public class TourRequest {

    @NotBlank(message = "tên tour ko đc để trống", groups = CreateGroup.class)
    private String tourName;

    private String describe;

    @NotNull(message = "giá người lớn ko đc null", groups = CreateGroup.class)
    @Min(value = 0, message = "giá người lớn phải >= 0", groups = {CreateGroup.class, UpdateGroup.class})
    private BigDecimal adultPrice;

    @NotNull(message = "giá trẻ em ko đc null", groups = CreateGroup.class)
    @Min(value = 0, message = "giá trẻ em phải >= 0", groups = {CreateGroup.class, UpdateGroup.class})
    private BigDecimal childPrice;

    @NotBlank(message = "trạng thái không được để trống", groups = CreateGroup.class)
    @Pattern(regexp = "^(Đang mở|Tạm dừng)$", 
         message = "Trạng thái chỉ có thể là 'Đang mở' hoặc 'Tạm dừng'",
         groups = {CreateGroup.class, UpdateGroup.class})
    private String status;

    @NotNull(message = "idDestination không được null", groups = CreateGroup.class)
    @Min(value = 1, message = "idDestination phải >= 1", groups = {CreateGroup.class, UpdateGroup.class})
    private Integer idDestination;
}