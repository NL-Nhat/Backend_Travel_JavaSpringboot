package com.example.travel.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

import com.example.travel.dto.request.TourRequestDTO;
import com.example.travel.dto.response.ReviewResponseDTO;
import com.example.travel.dto.response.TourDetailResponseDTO;
import com.example.travel.dto.response.TourResponseDTO;
import com.example.travel.entity.DestinationEntity;
import com.example.travel.entity.ReviewEntity;
import com.example.travel.entity.TourEntity;
import com.example.travel.mapper.TourMapper;
import com.example.travel.projection.TourProjection;
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

    public List<TourResponseDTO> getFiveTourHot() {
        List<TourProjection> listTour = tourRepository.getFiveTourHot();
        
         /* Sử dụng lớp mapper*/
        return listTour.stream().map(tourMapper::mapToTourResponseDTO_Projection).collect(Collectors.toList());
    }

    public long countAllTour() {
        return tourRepository.count();
    }

    public Map<String, Object> getAllTourDangMo(int page, int size) {

        int offset = (page - 1) * size; 
        //offset: số tour bị bỏ qua, vd: 1 trang có 10 tour, xem trang số 2 thì bỏ qua trang số 1 -> offset = 10, trang 3 thì offset = 20

        //Danh sách tour theo trang, size: số tour trong 1 trang
        List<TourResponseDTO> data = tourRepository.getTourDangMoPaging(offset, size)
                                                        .stream()
                                                        .map(tourMapper::mapToTourResponseDTO_Projection)
                                                        .toList();
    
        /* 
        Lấy tổng số tour đang mở sau đó tính tổng số trang theo tổng số tour.
        vd: 1 trang có 10 tour, trổng có 21 tour -> 21/10 = 2.1 -> có 3 trang: trang 1,2 có 10 tour, trang 3 có 1 tour
        */ 
        long total = tourRepository.countByStatus("Đang mở");
        int totalPages = (int) Math.ceil((double) total / size);

        //Thêm các thông tin gửi cho client
        Map<String, Object> result = new HashMap<>();
        result.put("data", data);
        result.put("page", page);
        result.put("size", size);
        result.put("total", total);
        result.put("totalPages", totalPages);
        
        return result;
    }

    public TourDetailResponseDTO getDetailTour(Integer id) {
        TourEntity tourEntity = tourRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy tour có id này"));

        TourDetailResponseDTO tourDetailResponseDTO =  tourMapper.toTourDetailResponseDTO(tourEntity);
        //tourDetailResponseDTO.setCity(tourEntity.getDestination().getCity());
        
        return tourDetailResponseDTO;
    }

    public List<ReviewResponseDTO> getAllReview(Integer idTour) {

        List<ReviewEntity> reviews = reviewRepository.findByTourId(idTour);

        if (reviews.isEmpty()) {
            throw new RuntimeException("Ko tim thay danh gia voi idtour nay");
        }

        // List<ReviewResponseDTO> results = new ArrayList<>();

        // reviews.stream().forEach(review -> {
        //     ReviewResponseDTO dto = new ReviewResponseDTO();
        //     dto.setNumberStar(review.getNumberStar());
        //     dto.setComment(review.getComment());
        //     dto.setUserName(review.getUser().getUserName());
        //     dto.setAvatar(review.getUser().getAvatar());
        //     dto.setCreateAt(review.getCreateAt());

        //     results.add(dto);
        // });
        
        // return results;

        return reviews.stream().map(review -> {
            ReviewResponseDTO dto = new ReviewResponseDTO();
            dto.setNumberStar(review.getNumberStar());
            dto.setComment(review.getComment());
            dto.setUserName(review.getUser().getUsername());
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
