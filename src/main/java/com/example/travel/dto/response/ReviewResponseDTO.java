package com.example.travel.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ReviewResponseDTO {

    private Integer numberStar;
    private String tourName;
    private String comment;
    private String userName;
    private String avatar;
    private LocalDateTime createAt;
}
