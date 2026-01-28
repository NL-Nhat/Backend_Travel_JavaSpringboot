package com.example.travel.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImageTourRequestDTO {

    @NotBlank(message = "image ko đc để trống")
    private String image;

    private String describe;
}
