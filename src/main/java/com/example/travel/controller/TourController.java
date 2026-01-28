package com.example.travel.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.travel.dto.request.SearchRequestDTO;
import com.example.travel.dto.request.TourRequestDTO;
import com.example.travel.dto.response.TourDetailResponseDTO;
import com.example.travel.dto.response.TourResponseDTO;
import com.example.travel.service.SearchService;
import com.example.travel.service.TourService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Validated //thêm vào để sử dụng Bean Validation cho @PathVariable
@RestController
@RequestMapping("/api/tours")
@RequiredArgsConstructor
@Tag(name = "Tour", description = "Quản lý tour du lịch")  // hiển thị trong swagger
public class TourController {

    private final TourService tourService;
    private final SearchService searchService;

    @Operation(summary = "Danh sách tour đang mở, có phân trang") //Phân trang này được viết bằng thủ tục trong mysql
    @GetMapping("/all-by-status")
    public Map<String, Object> getAllTourByStatus(
        @RequestParam(defaultValue = "1") @NotNull(message = "page ko đc null") @Min(value = 1, message = "page phải >= 1") Integer page, // page: số trang, vd: trang 1, trang 2,..
        @RequestParam(defaultValue = "10") @NotNull(message = "size ko đc null") @Min(value = 1, message = "size phải >= 1") Integer size) { // size: số tour trong 1 trang
        
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
    public ResponseEntity<TourDetailResponseDTO> getDetailTour(@PathVariable(value = "id") 
                                                                @NotNull(message = "id tour ko được null")
                                                                @Min(value = 1, message = "id tour phải >= 1")
                                                                Integer id) {

        return ResponseEntity.ok(tourService.getDetailTour(id));
    }

    @GetMapping("/filter-tour")
    public ResponseEntity<List<TourResponseDTO>> filterTour(@RequestBody SearchRequestDTO s) {
        return ResponseEntity.ok(searchService.filterTour(s));
    }

    @GetMapping("/search-tour")
    public ResponseEntity<List<TourResponseDTO>> searchTour(@RequestParam(value = "text") String text) {
        return ResponseEntity.ok(searchService.searchTour(text));
    }

    @PostMapping("/add-tour")
    public ResponseEntity<String> addTour(@Valid @RequestBody TourRequestDTO t, @Valid @RequestParam(value = "idDestination") //Thêm @Valid để hiện lỗi đã đặt trong RequestDto
                                        @NotNull(message = "id điểm đến ko đc null")
                                        @Min(value = 1, message = "id điểm đến phải >= 1")    
                                        Integer id) {
        
        return ResponseEntity.ok(tourService.addTour(t, id));
    }
}
