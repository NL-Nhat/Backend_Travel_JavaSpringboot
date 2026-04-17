package com.example.travel.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImageTourResponse {

    private String id;
    private String image;
    private String describe;

}
