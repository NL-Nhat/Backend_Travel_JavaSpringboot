package com.example.travel.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.Data;

@Data
public class SearchRequestDTO {

    private List<String> city;
    private BigDecimal priceFrom;
    private BigDecimal priceTo;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private List<Integer> numberStar;
}
