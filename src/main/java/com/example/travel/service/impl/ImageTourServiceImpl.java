package com.example.travel.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.travel.dto.request.ImageTourRequestDTO;
import com.example.travel.entity.ImageTourEntity;
import com.example.travel.entity.TourEntity;
import com.example.travel.mapper.ImageTourMapper;
import com.example.travel.repository.ImageTourRepository;
import com.example.travel.repository.TourRepository;
import com.example.travel.service.ImageTourService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageTourServiceImpl implements ImageTourService{

    private final ImageTourRepository imageTourRepository;
    private final TourRepository tourRepository;
    private final ImageTourMapper imageTourMapper;

    @Override
    public String addImageTour(List<ImageTourRequestDTO> i, Integer idTour) {

        TourEntity t = tourRepository.findById(idTour)
            .orElseThrow(() -> new RuntimeException("Ko tìm thấy tour với id này"));

            //Tạo danh sách tạm để chứa các Entity
        List<ImageTourEntity> listImage = new ArrayList<>();

        for (ImageTourRequestDTO dto : i) {
            ImageTourEntity imageTour = imageTourMapper.toImageTourEntity(dto);
            imageTour.setTour(t);
            listImage.add(imageTour);
        }

        imageTourRepository.saveAll(listImage);

        return "Thêm " + listImage.size() +" ảnh tour thành công";
    }
}
