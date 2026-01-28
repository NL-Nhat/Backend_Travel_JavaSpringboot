package com.example.travel.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.travel.dto.request.ImageTourRequestDTO;
import com.example.travel.service.ImageTourService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/imagetours")
public class ImageTourController {

    private final ImageTourService imageTourService;

    @PostMapping("/add-image-tour")
    public ResponseEntity<String> addImageTour(@Valid @RequestBody List<ImageTourRequestDTO> i,//Thêm @Valid để hiện lỗi đã đặt trong RequestDto, chỉ dùng cho @RequestBody
        //Vì @RequestParam luôn có thuộc tính required = true nên phải đặt lại thành false để @Notnull bắt lỗi được
        //Nếu ko thì cơ chế của Spring sẽ tự động chặn lại và ném ra lỗi: "Required request parameter... is not present". 
        //Nếu ko kiểm tra null(chỉ dùng @Min) thì ko cần thêm required = false vì cơ chế của Spring chỉ chặn null
            @RequestParam(value = "idTour", required = false) @NotNull(message = "id tour ko đc null")
            @Positive(message = "id phải > 0") Integer id ) {

        return ResponseEntity.ok(imageTourService.addImageTour(i, id));
    }
}
