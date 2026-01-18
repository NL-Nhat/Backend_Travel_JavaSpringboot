package com.example.travel.dto.response;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TourDetailResponseDTO {
    
    private Integer id;
    private String tourName;
    private String describe; //mô tả
    private String destinationName; // tên điểm đến
    private String image;
    private BigDecimal averageRating;
    private Integer numberOfReview;
    private BigDecimal adultPrice;
    private BigDecimal childPrice;

    List<ImageTourResponseDTO> imageTours = new ArrayList<>();
    List<DepartureCheduleResponseDTO> departureChedules = new ArrayList<>();
}
