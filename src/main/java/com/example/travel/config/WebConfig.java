package com.example.travel.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// @Configuration là annotation của Spring dùng để đánh dấu một class là lớp cấu hình

/*
Kết hợp @Configuration + WebMvcConfigurer
Ý nghĩa:
    Spring nhận class này là cấu hình
    Áp dụng cấu hình CORS cho toàn bộ API
    Không ảnh hưởng cấu hình mặc định của Spring Boot
 */
@Configuration
public class WebConfig implements WebMvcConfigurer{

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Áp dụng cho tất cả API bắt đầu bằng /api
                .allowedOrigins("http://localhost:5173") // Domain của React
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Các phương thức cho phép
                .allowedHeaders("*") // Cho phép tất cả các Header
                .allowCredentials(true); // Cho phép gửi Cookie/Auth Header nếu cần
    }

}
