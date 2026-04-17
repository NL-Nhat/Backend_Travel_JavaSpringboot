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
public class TourResponse {

    private Integer id;
    private String tourName;
    private String image;
    private String describe;
    private String city;
    private BigDecimal averageRating;
    private Integer numberOfReview;
    private String status;
    private BigDecimal adultPrice;
    private BigDecimal childPrice;
}
