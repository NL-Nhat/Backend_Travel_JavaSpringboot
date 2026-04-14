package com.example.travel.dto.response;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class TourResponseDTO {

    private Integer id;
    private String tourName;
    private String image;
    private BigDecimal adultPrice;
    private BigDecimal childPrice;
    private BigDecimal averageRating;
    private String describe;
    private String city;
    private Integer numberOfReview;
    private String status;
}
