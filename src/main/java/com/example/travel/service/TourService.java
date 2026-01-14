package com.example.travel.service;

import java.util.List;

import com.example.travel.dto.response.TourResponseDTO;

public interface TourService {

    public List<TourResponseDTO> getFiveTourHot();

}
