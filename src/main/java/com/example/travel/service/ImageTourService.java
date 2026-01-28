package com.example.travel.service;

import java.util.List;

import com.example.travel.dto.request.ImageTourRequestDTO;

public interface ImageTourService {

    public String addImageTour(List<ImageTourRequestDTO> i, Integer idTour);
}
