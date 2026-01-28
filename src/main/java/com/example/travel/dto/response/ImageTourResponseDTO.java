package com.example.travel.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImageTourResponseDTO {

    private String id;
    private String image;
    private String describe;

}
