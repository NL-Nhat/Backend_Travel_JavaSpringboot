package com.example.travel.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.travel.dto.request.CreateGroup;
import com.example.travel.dto.request.SearchRequest;
import com.example.travel.dto.request.TourRequest;
import com.example.travel.dto.request.UpdateGroup;
import com.example.travel.dto.response.ReviewResponse;
import com.example.travel.dto.response.TourDetailResponse;
import com.example.travel.dto.response.TourResponse;
import com.example.travel.service.SearchService;
import com.example.travel.service.TourService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@Validated //thêm vào để sử dụng Bean Validation cho @PathVariable
@RestController
@RequestMapping("/api/tours")
@RequiredArgsConstructor
@Tag(name = "Tour", description = "Quản lý tour du lịch")  // hiển thị trong swagger
public class TourController {

    private final TourService tourService;
    private final SearchService searchService;

    @GetMapping("/status")
    public ResponseEntity<?> getToursByStatus(@RequestParam String status, Pageable pageable) {
        
        return ResponseEntity.ok(tourService.getToursByStatus(status, pageable));
    }
    
    @GetMapping("/count-all-tour")
    @Operation(summary = "Đếm số tour")
    public ResponseEntity<Long> countNumberTour() {
        return ResponseEntity.ok(tourService.countAllTour());
    }

    @GetMapping("/all-review")
    public ResponseEntity<List<ReviewResponse>> getAllReview(@RequestParam(value = "id", required = false)
                                                                @NotNull(message = "id tour ko được null")
                                                                @Positive(message = "id phải > 0")
                                                                Integer id) {

        return ResponseEntity.ok(tourService.getAllReview(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TourDetailResponse> getDetailTour(@PathVariable(value = "id") 
                                                                @NotNull(message = "id tour ko được null")
                                                                @Min(value = 1, message = "id tour phải >= 1")
                                                                Integer id) {

        return ResponseEntity.ok(tourService.getDetailTour(id));
    }

    @PostMapping("/filter-tour")
    public ResponseEntity<List<TourResponse>> filterTour(@RequestBody SearchRequest s) {
        return ResponseEntity.ok(searchService.filterTour(s));
    }

    @GetMapping("/search-tour")
    public ResponseEntity<List<TourResponse>> searchTour(@RequestParam(value = "text") String text) {
        return ResponseEntity.ok(searchService.searchTour(text));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createTour(@Validated(CreateGroup.class) @RequestPart("dto") TourRequest dto, //dùng @Validated để sử dụng Validation Group
                                    @RequestPart (required = false) MultipartFile file) {
        
        return ResponseEntity.ok(tourService.addTour(file, dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTour(@Validated(UpdateGroup.class) @RequestPart("dto") TourRequest dto,
                                            @PathVariable
                                            @Min(value = 1, message = "idTour phải >= 1") Integer id,
                                            @RequestPart (required = false) MultipartFile file) {
        
        return ResponseEntity.ok(tourService.updateTour(file, dto, id));
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTour(@PathVariable @Min(value = 1, message = "idTour phải >= 1") Integer id) {

        return ResponseEntity.ok(tourService.deleteTour(id));
    }



}
