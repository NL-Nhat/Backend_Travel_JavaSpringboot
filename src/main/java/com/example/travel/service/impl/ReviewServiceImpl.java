package com.example.travel.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.example.travel.dto.response.ReviewResponseDTO;
import com.example.travel.mapper.ReviewMapper;
import com.example.travel.repository.ReviewRepository;
import com.example.travel.service.ReviewService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    @Override
    public List<ReviewResponseDTO> getTop3Review() {
        List<Object[]> listReview = reviewRepository.getTop3Review();

        return listReview.stream().map(reviewMapper::mapToReviewResponseDTO).collect(Collectors.toList());
    }

}
