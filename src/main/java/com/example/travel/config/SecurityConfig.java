package com.example.travel.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@EnableMethodSecurity //để dùng được @PreAuthorize(hasrole...) ở controller
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Tắt CSRF vì dùng JWT

            // Gọi cấu hình CORS đã được tách ra thành Bean ở dưới
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))

            // Cấu hình phân quyền các endpoint (URL)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/register").permitAll()
                .requestMatchers("/api/auth/login").permitAll()
                .requestMatchers("/api/auth/refresh").permitAll()
                .requestMatchers("/api/auth/logout").permitAll()
                .requestMatchers("/api/users/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/tours/**").permitAll()
                .requestMatchers("/api/tours/**").hasRole("ADMIN")
                .requestMatchers("/api/schedules/**").permitAll()
                .requestMatchers("/api/reviews/**").permitAll()
                .requestMatchers("/api/departureChedules/**").permitAll()
                .requestMatchers("/api/destinations/**").permitAll()
                .anyRequest().authenticated() // Các API khác đều yêu cầu có thẻ thông hành
            )
            // Cấu hình quản lý Session
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            // Thêm JwtAuthFilter vào chuỗi
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // Cấu hình CORS
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        
        // Bắt buộc phải ghi rõ domain của React, KHÔNG dùng "*" khi có Cookie
        config.setAllowedOrigins(List.of("http://localhost:5173")); 
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        
        // config.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept"));
        config.setAllowedHeaders(List.of("*"));
        
        // Cho phép nhận và gửi HTTP-Only Cookie
        config.setAllowCredentials(true); 

         config.setExposedHeaders(List.of("Set-Cookie"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // Áp dụng cấu hình này cho mọi API
        
        return source;
    }
}
