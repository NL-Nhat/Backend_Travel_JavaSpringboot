package com.example.travel.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

import com.example.travel.dto.request.TourRequestDTO;
import com.example.travel.dto.response.TourDetailResponseDTO;
import com.example.travel.dto.response.TourResponseDTO;
import com.example.travel.entity.DestinationEntity;
import com.example.travel.entity.TourEntity;
import com.example.travel.mapper.TourMapper;
import com.example.travel.projection.TourProjection;
import com.example.travel.repository.DestinationRepository;
import com.example.travel.repository.TourRepository;
import com.example.travel.service.TourService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TourServiceImpl implements TourService{

    private final TourMapper tourMapper;
    private final TourRepository tourRepository;
    private final DestinationRepository destinationRepository;

    @Override
    public List<TourResponseDTO> getFiveTourHot() {
        List<TourProjection> listTour = tourRepository.getFiveTourHot();
        
         /* Sử dụng lớp mapper*/
        return listTour.stream().map(tourMapper::mapToTourResponseDTO_Projection).collect(Collectors.toList());
    }

    @Override
    public long countAllTour() {
        return tourRepository.count();
    }

    @Override
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

    @Override
    public TourDetailResponseDTO getDetailTour(Integer id) {
        TourEntity tourEntity = tourRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy tour có id này"));

        TourDetailResponseDTO tourDetailResponseDTO =  tourMapper.toTourDetailResponseDTO(tourEntity);
        //tourDetailResponseDTO.setCity(tourEntity.getDestination().getCity());
        
        return tourDetailResponseDTO;
    }

    @Transactional
    @Override
    public String addTour(TourRequestDTO tourRequestDTO, Integer idDestination) {
        DestinationEntity d = destinationRepository.findById(idDestination)
            .orElseThrow(() -> new RuntimeException("Ko tìm thấy điểm đến với id này"));

        TourEntity t = tourMapper.toTourEntity(tourRequestDTO);
        t.setDestination(d);

        tourRepository.save(t);

        return "Thêm tour thành công";
    }

}
