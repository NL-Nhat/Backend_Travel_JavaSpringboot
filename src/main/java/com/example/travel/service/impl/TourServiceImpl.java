package com.example.travel.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        return listTour.stream().map(tourMapper::mapToTourResponseDTO_Object).collect(Collectors.toList());
    }

    @Override
    public long countNumberTour() {
        return tourRepository.count();
    }

    @Override
    public Map<String, Object> getAllTourDangMo(int page, int size) {

        int offset = (page - 1) * size; 
        //offset: số tour bị bỏ qua, vd: 1 trang có 10 tour, xem trang số 2 thì bỏ qua trang số 1 -> offset = 10, trang 3 thì offset = 20

        //Danh sách tour theo trang, size: số tour trong 1 trang
        List<TourResponseDTO> data = tourRepository.getTourDangMoPaging(offset, size)
                                                        .stream()
                                                        .map(tourMapper::mapToTourResponseDTO_Object)
                                                        .toList();
    
        /* 
        Lấy tổng số tour đang mở sau đó tính tổng số trang theo tổng số tour.
        vd: 1 trang có 10 tour, trổng có 21 tour -> 21/10 = 2.1 -> có 3 trang: trang 1,2 có 10 tour, trang 3 có 1 tour
        */ 
        long total = tourRepository.countTourDangMo();
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

}
