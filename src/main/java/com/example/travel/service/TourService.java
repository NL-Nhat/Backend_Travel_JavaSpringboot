package com.example.travel.service;

import java.util.List;
import java.util.Map;

import com.example.travel.dto.response.TourResponseDTO;

public interface TourService {

    //Lấy top 5 tour đặt nhiều nhất
    public List<TourResponseDTO> getFiveTourHot();

    // Đếm số tour
    public long countNumberTour();

    //Lấy tất cả tour theo trạng thái
    //Phân trang viết trong MySQL
    public Map<String, Object> getAllTourDangMo(int page, int size);
}
