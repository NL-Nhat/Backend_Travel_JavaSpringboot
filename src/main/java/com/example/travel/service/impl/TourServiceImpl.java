package com.example.travel.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.travel.dto.response.TourResponseDTO;
import com.example.travel.mapper.TourMapper;
import com.example.travel.repository.TourRepository;
import com.example.travel.service.TourService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TourServiceImpl implements TourService{

    private final TourMapper tourMapper;
    private final TourRepository tourRepository;

    @Override
    public List<TourResponseDTO> getFiveTourHot() {
        List<Object[]> listTour = tourRepository.getFiveTourHot();
        
         /* Sử dụng lớp mapper*/
        return listTour.stream().map(tourMapper::mapToTourResponseDTO).collect(Collectors.toList());
    }

}
