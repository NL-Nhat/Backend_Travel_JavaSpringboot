package com.example.travel.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.travel.dto.request.SearchRequestDTO;
import com.example.travel.dto.request.TourRequestDTO;
import com.example.travel.dto.response.ReviewResponseDTO;
import com.example.travel.dto.response.TourDetailResponseDTO;
import com.example.travel.dto.response.TourResponseDTO;
import com.example.travel.service.SearchService;
import com.example.travel.service.TourService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
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
    public ResponseEntity<List<ReviewResponseDTO>> getAllReview(@RequestParam(value = "id", required = false)
                                                                @NotNull(message = "id tour ko được null")
                                                                @Positive(message = "id phải > 0")
                                                                Integer id) {

        return ResponseEntity.ok(tourService.getAllReview(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TourDetailResponseDTO> getDetailTour(@PathVariable(value = "id") 
                                                                @NotNull(message = "id tour ko được null")
                                                                @Min(value = 1, message = "id tour phải >= 1")
                                                                Integer id) {

        return ResponseEntity.ok(tourService.getDetailTour(id));
    }

    @PostMapping("/filter-tour")
    public ResponseEntity<List<TourResponseDTO>> filterTour(@RequestBody SearchRequestDTO s) {
        return ResponseEntity.ok(searchService.filterTour(s));
    }

    @GetMapping("/search-tour")
    public ResponseEntity<List<TourResponseDTO>> searchTour(@RequestParam(value = "text") String text) {
        return ResponseEntity.ok(searchService.searchTour(text));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add-tour")
    public ResponseEntity<Integer> addTour(@Valid @RequestBody TourRequestDTO t //Thêm @Valid để hiện lỗi đã đặt trong RequestDto, chỉ dùng cho @RequestBody
        //Vì @RequestParam luôn có thuộc tính required = true nên phải đặt lại thành false để @Notnull bắt lỗi được
        //Nếu ko thì cơ chế của Spring sẽ tự động chặn lại và ném ra lỗi: "Required request parameter... is not present". 
        //Nếu ko kiểm tra null(chỉ dùng @Min) thì ko cần thêm required = false vì cơ chế của Spring chỉ chặn null
        ) {
        
        return ResponseEntity.ok(tourService.addTour(t));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateTour(@Valid @RequestBody TourRequestDTO tourRequestDTO,
         @PathVariable
         @Min(value = 1, message = "idTour phải >= 1")
         @NotNull(message = "idTour ko đc null") Integer id) {
        
        return ResponseEntity.ok(tourService.updateTour(tourRequestDTO, id));
    }
}
