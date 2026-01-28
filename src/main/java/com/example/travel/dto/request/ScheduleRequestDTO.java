package com.example.travel.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ScheduleRequestDTO {

    @NotNull(message = "id LKH ko đc null")
    @Min(value = 1, message = "id LKH phải >=1")
    private Integer idDepartureSchedule;

    @NotNull(message = "ngày ko đc null")
    @FutureOrPresent(message = "Ngày lên lịch không được là ngày trong quá khứ")
    private LocalDate date;

    @NotNull(message = "giờ ko đc null")
    private LocalTime time;

    @NotBlank(message = "hoạt động ko đc để trống")
    private String work;

    private String describe;
}
