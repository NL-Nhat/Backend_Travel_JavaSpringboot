package com.example.travel.service;

import java.util.List;

import com.example.travel.dto.response.ReviewResponseDTO;

public interface ReviewService {

    public List<ReviewResponseDTO> getTop3Review();
}
