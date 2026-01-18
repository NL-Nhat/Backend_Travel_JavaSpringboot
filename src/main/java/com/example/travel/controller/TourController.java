package com.example.travel.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.travel.dto.response.TourDetailResponseDTO;
import com.example.travel.dto.response.TourResponseDTO;
import com.example.travel.service.TourService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/tours")
@RequiredArgsConstructor
@Tag(name = "Tour", description = "Quản lý tour du lịch")  // hiển thị trong swagger
public class TourController {

    private final TourService tourService;

    @Operation(summary = "Danh sách tour đang mở, có phân trang") //Phân trang này được viết bằng thủ tục trong mysql
    @GetMapping("/all-by-status")
    public Map<String, Object> getAllTourByStatus(@RequestParam(defaultValue = "1") int page, // page: số trang, vd: trang 1, trang 2,..
                                                    @RequestParam(defaultValue = "10") int size) { // size: số tour trong 1 trang
    return tourService.getAllTourDangMo(page, size);
    }

    @GetMapping("/top5tour")
    @Operation(summary = "Top 5 tour được đặt nhiều nhất") // hiển thị trong swagger
    public ResponseEntity<List<TourResponseDTO>> getFiveTourHot() {
        return ResponseEntity.ok(tourService.getFiveTourHot());
    }
    
    @GetMapping("/count-all-tour")
    @Operation(summary = "Đếm số tour")
    public ResponseEntity<Long> countNumberTour() {
        return ResponseEntity.ok(tourService.countAllTour());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TourDetailResponseDTO> getDetailTour(@PathVariable Integer id) {

        return ResponseEntity.ok(tourService.getDetailTour(id));
    }

}
