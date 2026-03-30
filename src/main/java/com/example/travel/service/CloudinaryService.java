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

    // public void deleteImage(String fileNameInDb) throws IOException {
    //     if (fileNameInDb == null || fileNameInDb.isEmpty()) return;

    //     // Lấy public_id bằng cách bỏ đuôi .jpg
    //     String publicId = fileNameInDb.contains(".") 
    //                     ? fileNameInDb.substring(0, fileNameInDb.lastIndexOf(".")) 
    //                     : fileNameInDb;

    //     // Gọi API xóa của Cloudinary
    //     cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
    // }
}
