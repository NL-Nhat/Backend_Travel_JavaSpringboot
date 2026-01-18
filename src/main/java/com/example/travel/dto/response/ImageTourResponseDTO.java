package com.example.travel.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImageTourResponseDTO {

    private String id;
    private String mainImage;
    private String describe;

}
