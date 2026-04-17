package com.example.travel.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TourDetailResponse {
    
    private TourResponse tourResponseDTO;

    List<ImageTourResponse> imageTours;
    List<DepartureScheduleResponse> departureSchedules;
}
