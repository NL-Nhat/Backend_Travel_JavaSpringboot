package com.example.travel.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.example.travel.entity.ImageTourEntity;
import com.example.travel.entity.TourEntity;
import com.example.travel.repository.ImageTourRepository;
import com.example.travel.repository.TourRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageTourService {

    private final ImageTourRepository imageTourRepository;
    private final TourRepository tourRepository;
    private final CloudinaryService cloudinaryService;

    @Transactional
    public String createImageTour(List<MultipartFile> files, Integer id) {

        if (files == null || files.isEmpty()) {
            throw new RuntimeException("Danh sách ảnh rỗng");
        }

        TourEntity tourEntity = tourRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Ko tìm thấy tour với id này"));

        for (MultipartFile file : files) {

            if (file == null || file.isEmpty()) {
                continue;
            }

            ImageTourEntity imageTourEntity = new ImageTourEntity();
            imageTourEntity.setTour(tourEntity);

            ImageTourEntity saveImageTourEntity = imageTourRepository.save(imageTourEntity);

            try {
                String fileName = "tour/imageTour_" + saveImageTourEntity.getId();
                String imageURL = cloudinaryService.uploadImage(file, fileName);

                saveImageTourEntity.setImage(imageURL);

                imageTourRepository.save(saveImageTourEntity);
            } catch (Exception e) {
                throw new RuntimeException("Upload ảnh thất bại: " + e.getMessage(), e);
            }
        }
        return "Thêm ảnh thành công";
    }

    @Transactional
    public String deleteImageTour(Integer id) {

        ImageTourEntity imageTourEntity = imageTourRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Ko tìm thấy ảnh với id này"));

        String path = "tour/imageTour_" + id;

        cloudinaryService.deleteImage(path);

        imageTourRepository.delete(imageTourEntity);

        return "Xóa ảnh thành công";
    }
}
