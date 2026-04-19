package com.example.travel.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.travel.dto.request.TourRequest;
import com.example.travel.dto.response.PageResponse;
import com.example.travel.dto.response.ReviewResponse;
import com.example.travel.dto.response.TourDetailResponse;
import com.example.travel.dto.response.TourResponse;
import com.example.travel.entity.DestinationEntity;
import com.example.travel.entity.ReviewEntity;
import com.example.travel.entity.TourEntity;
import com.example.travel.mapper.TourMapper;
import com.example.travel.repository.DepartureScheduleRepository;
import com.example.travel.repository.DestinationRepository;
import com.example.travel.repository.ReviewRepository;
import com.example.travel.repository.TourRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TourService {

    private final TourMapper tourMapper;
    private final TourRepository tourRepository;
    private final DestinationRepository destinationRepository;
    private final DepartureScheduleRepository departureScheduleRepository;
    private final ReviewRepository reviewRepository;
    private final CloudinaryService cloudinaryService;

    public long countAllTour() {
        return tourRepository.count();
    }

    public PageResponse<TourResponse> getToursByStatus(String status, Pageable pageable) {

        Page<TourEntity> page;

        if (status == null || status.isBlank()) {
            page = tourRepository.findAll(pageable); // lấy tất cả
        } else {
            page = tourRepository.findByStatus(status, pageable); // lọc theo status
        }

        PageResponse<TourResponse> res = new PageResponse<>();
        res.setContent(
            page.getContent().stream()
                .map(tourMapper::toTourResponseDTO)
                .toList()
        );
        res.setPage(page.getNumber());
        res.setSize(page.getSize());
        res.setTotalElements(page.getTotalElements());
        res.setTotalPages(page.getTotalPages());

        return res;
    }

    public TourDetailResponse getDetailTour(Integer id) {
        TourEntity tourEntity = tourRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy tour có id này"));

        TourDetailResponse tourDetailResponseDTO =  tourMapper.toTourDetailResponseDTO(tourEntity);
        
        return tourDetailResponseDTO;
    }

    public List<ReviewResponse> getAllReview(Integer idTour) {

        List<ReviewEntity> reviews = reviewRepository.findByTourId(idTour);

        if (reviews.isEmpty()) {
            throw new RuntimeException("Ko tim thay danh gia voi idtour nay");
        }

        return reviews.stream().map(review -> {
            ReviewResponse dto = new ReviewResponse();
            dto.setNumberStar(review.getNumberStar());
            dto.setComment(review.getComment());
            dto.setFullName(review.getUser().getFullName());
            dto.setAvatar(review.getUser().getAvatar());
            dto.setCreateAt(review.getCreateAt());
            return dto;
        }).toList();
    }


    @Transactional
    public Map<String, Object> addTour(MultipartFile file, TourRequest tourRequestDTO) {

        if(tourRepository.existsByTourName(tourRequestDTO.getTourName())) {
            throw new RuntimeException("Tên tour '" + tourRequestDTO.getTourName() + "' đã tồn tại!");
        }

        DestinationEntity d = destinationRepository.findById(tourRequestDTO.getIdDestination())
            .orElseThrow(() -> new IllegalArgumentException("Ko tìm thấy điểm đến với id này"));

        TourEntity tour = tourMapper.toTourEntity(tourRequestDTO);
        tour.setDestination(d);

        TourEntity savedTour = tourRepository.save(tour);

        if (file != null && !file.isEmpty()) {
            try {
                String fileName = "tour/tour_" + savedTour.getId();
                String imageURL = cloudinaryService.uploadImage(file, fileName);

                tour.setImage(imageURL);

                tourRepository.save(savedTour);
            } catch (Exception e) {
                throw new RuntimeException("Upload ảnh thất bại: " + e.getMessage(), e);
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("idTour", savedTour.getId());
        response.put("message", "Tạo tour thành công");

        return response;
    }

    @Transactional
    public Map<String, Object> updateTour(MultipartFile file, TourRequest tourRequestDTO, Integer idTour) {

        TourEntity tour = tourRepository.findById(idTour)
            .orElseThrow(() -> new IllegalArgumentException("ko tìm thấy tour với id này"));

        tourMapper.updateTourFromDto(tourRequestDTO, tour);

        if(tourRequestDTO.getIdDestination() != null) {
            DestinationEntity d = destinationRepository.findById(tourRequestDTO.getIdDestination())
                .orElseThrow(() -> new RuntimeException("Ko tìm thấy điểm đến với id này"));

            tour.setDestination(d);
        }

        if (file != null && !file.isEmpty()) {
            try {
                String fileName = "tour/tour_" + idTour;
                String imageURL = cloudinaryService.uploadImage(file, fileName);

                tour.setImage(imageURL);
            } catch (Exception e) {
                throw new RuntimeException("Upload ảnh thất bại: " + e.getMessage(), e);
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("idTour", tour.getId());
        response.put("message", "Sửa tour thành công");

        return response;
    }

    @Transactional
    public String deleteTour(Integer id) {
        TourEntity tour = tourRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Khong tim thay tour voi id nay"));  
            //IllegalArgumentException dùng để báo rằng tham số truyền vào hàm là không hợp lệ.

        boolean hasBooking = tour.getDepartureSchedules().stream()
        .anyMatch(ds -> !ds.getBookings().isEmpty());  //kiểm tra tour có người đặt chưa

        if (hasBooking) {
            throw new IllegalStateException("Tour đã có người đặt, không thể xóa");  //IllegalStateException → sai trạng thái
        }

        cloudinaryService.deleteTourImage(id);

        departureScheduleRepository.deleteAll(tour.getDepartureSchedules());
        tourRepository.delete(tour);

        return "Xóa tour thành công";
    }

}
