package com.example.travel.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.travel.dto.request.TourRequestDTO;
import com.example.travel.dto.response.PageResponse;
import com.example.travel.dto.response.ReviewResponseDTO;
import com.example.travel.dto.response.TourDetailResponseDTO;
import com.example.travel.dto.response.TourResponseDTO;
import com.example.travel.entity.DestinationEntity;
import com.example.travel.entity.ReviewEntity;
import com.example.travel.entity.TourEntity;
import com.example.travel.mapper.TourMapper;
import com.example.travel.repository.DestinationRepository;
import com.example.travel.repository.ReviewRepository;
import com.example.travel.repository.TourRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TourService {

    private final TourMapper tourMapper;
    private final TourRepository tourRepository;
    private final DestinationRepository destinationRepository;
    private final ReviewRepository reviewRepository;

    public long countAllTour() {
        return tourRepository.count();
    }

    public PageResponse<TourResponseDTO> getToursByStatus(String status, Pageable pageable) {

        Page<TourEntity> page;

        if (status == null || status.isBlank()) {
            page = tourRepository.findAll(pageable); // lấy tất cả
        } else {
            page = tourRepository.findByStatus(status, pageable); // lọc theo status
        }

        PageResponse<TourResponseDTO> res = new PageResponse<>();
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

    public TourDetailResponseDTO getDetailTour(Integer id) {
        TourEntity tourEntity = tourRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy tour có id này"));

        TourDetailResponseDTO tourDetailResponseDTO =  tourMapper.toTourDetailResponseDTO(tourEntity);
        
        return tourDetailResponseDTO;
    }

    public List<ReviewResponseDTO> getAllReview(Integer idTour) {

        List<ReviewEntity> reviews = reviewRepository.findByTourId(idTour);

        if (reviews.isEmpty()) {
            throw new RuntimeException("Ko tim thay danh gia voi idtour nay");
        }

        return reviews.stream().map(review -> {
            ReviewResponseDTO dto = new ReviewResponseDTO();
            dto.setNumberStar(review.getNumberStar());
            dto.setComment(review.getComment());
            dto.setFullName(review.getUser().getFullName());
            dto.setAvatar(review.getUser().getAvatar());
            dto.setCreateAt(review.getCreateAt());
            return dto;
        }).toList();
    }


    @Transactional
    public Integer addTour(TourRequestDTO tourRequestDTO) {

        if(tourRepository.existsByTourName(tourRequestDTO.getTourName())) {
            throw new RuntimeException("Tên tour '" + tourRequestDTO.getTourName() + "' đã tồn tại!");
        }

        DestinationEntity d = destinationRepository.findById(tourRequestDTO.getIdDestination())
            .orElseThrow(() -> new RuntimeException("Ko tìm thấy điểm đến với id này"));

        TourEntity t = tourMapper.toTourEntity(tourRequestDTO);
        t.setDestination(d);

        TourEntity tourEntity = tourRepository.save(t);

        return tourEntity.getId();
    }

    @Transactional
    public String updateTour(TourRequestDTO tourRequestDTO, Integer idTour) {

        TourEntity tourEntity = tourRepository.findById(idTour).orElseThrow(() -> new RuntimeException("ko tìm thấy tour với id này"));
        tourMapper.updateTourFromDto(tourRequestDTO, tourEntity);

        DestinationEntity d = destinationRepository.findById(tourRequestDTO.getIdDestination())
            .orElseThrow(() -> new RuntimeException("Ko tìm thấy điểm đến với id này"));

        tourEntity.setDestination(d);

        return "Sửa tour thành công";
    }

}
