package com.example.travel.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.example.travel.service.ImageTourService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/imagetours")
public class ImageTourController {

    private final ImageTourService imageTourService;

    @PostMapping("/{idTour}")
    public ResponseEntity<?> addImageTour(
        @RequestPart (required = false) List<MultipartFile> files,
        @PathVariable(value = "idTour") 
        @Min(value = 1 ,message = "id phải > 0") 
        Integer idTour
    ) {

        return ResponseEntity.ok(imageTourService.createImageTour(files, idTour));
    }

}
