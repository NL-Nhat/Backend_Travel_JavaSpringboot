package com.example.travel.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public String uploadImage(MultipartFile file, String fileName) throws IOException {

        Map params = ObjectUtils.asMap(
            "public_id", fileName,
            "overwrite", true, // Nếu trùng tên thì ghi đè
            "resource_type", "image"
        );

        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), params);

        // Trả về đường dẫn ảnh đầy đủ (URL) để frontend dùng hoặc lưu vào DB nếu muốn đổi mới
        return uploadResult.get("secure_url").toString();
    }

    public void deleteTourImage(Integer idTour) {
        try {
            // Tên image đã tạo
            String publicId = "tour/tour_" + idTour;
            
            // Gọi API xóa của Cloudinary
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            
        } catch (Exception e) {
            System.err.println("Lỗi xóa ảnh trên Cloudinary: " + e.getMessage());
        }
    }
}
