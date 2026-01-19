package com.example.travel.service;

import java.util.List;

import com.example.travel.dto.request.SearchRequestDTO;
import com.example.travel.dto.response.TourResponseDTO;

public interface SearchService {

    public List<TourResponseDTO> filterTour(SearchRequestDTO s);

    public List<TourResponseDTO> searchTour(String text);
}
