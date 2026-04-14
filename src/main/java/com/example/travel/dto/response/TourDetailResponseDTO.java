package com.example.travel.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TourDetailResponseDTO {
    
    private TourResponseDTO tourResponseDTO;

    List<ImageTourResponseDTO> imageTours;
    List<DepartureScheduleResponseDTO> departureSchedules;
}
