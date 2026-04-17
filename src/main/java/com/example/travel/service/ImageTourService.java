package com.example.travel.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.travel.dto.request.ImageTourRequest;
import com.example.travel.entity.ImageTourEntity;
import com.example.travel.entity.TourEntity;
import com.example.travel.mapper.ImageTourMapper;
import com.example.travel.repository.ImageTourRepository;
import com.example.travel.repository.TourRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageTourService {

    private final ImageTourRepository imageTourRepository;
    private final TourRepository tourRepository;
    private final ImageTourMapper imageTourMapper;

    public String addImageTour(List<ImageTourRequest> i, Integer idTour) {

        TourEntity t = tourRepository.findById(idTour)
            .orElseThrow(() -> new RuntimeException("Ko tìm thấy tour với id này"));

            //Tạo danh sách tạm để chứa các Entity
        List<ImageTourEntity> listImage = new ArrayList<>();

        for (ImageTourRequest dto : i) {
            ImageTourEntity imageTour = imageTourMapper.toImageTourEntity(dto);
            imageTour.setTour(t);
            listImage.add(imageTour);
        }

        imageTourRepository.saveAll(listImage);

        return "Thêm " + listImage.size() +" ảnh tour thành công";
    }
}
