package com.example.travel.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.travel.service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

// Lớp kiểm tra mọi yêu cầu (Request) gửi đến server xem có đính kèm token JWT hợp lệ hay không
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter { // OncePerRequestFilter -> đảm bảo bộ lọc chỉ chạy
                                                                    // duy nhất một lần cho mỗi yêu cầu

    private final JwtService jwtService;
    private final UserDetailsService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String jwtToken = null;
        
        // Tìm Token trong danh sách Cookies của request
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("accessToken".equals(cookie.getName())) {
                    jwtToken = cookie.getValue();
                    break;
                }
            }
        }

        // Nếu không có Cookie tên 'accessToken', cho đi tiếp (sẽ bị Spring Security chặn nếu API cần quyền)
        if (jwtToken == null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // Giải mã và lấy username (Subject)
            Claims payload = Jwts.parserBuilder()
                    .setSigningKey(jwtService.getSigningKey())
                    .build()
                    .parseClaimsJws(jwtToken)
                    .getBody();

            String username = payload.getSubject();

            // Nếu lấy được username và trong Context chưa có ai đăng nhập
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Lấy thông tin chi tiết của user từ Database
                UserDetails userDetails = userService.loadUserByUsername(username);

                // Tạo "thẻ thông hành" (Authentication object)
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null, // Không cần mật khẩu vì token đã chứng minh danh tính
                    userDetails.getAuthorities() // Danh sách các quyền (VD: ROLE_ADMIN)
                );

                // Gắn thêm thông tin chi tiết của request (như địa chỉ IP)
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Cấp quyền vào hệ thống
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

        } catch (Exception e) {
            // Nếu token sai chữ ký, hết hạn... lỗi sẽ bay vào đây
            System.out.println("Token không hợp lệ: " + e.getMessage());
        }

        // Chuyển request cho Filter tiếp theo trong chuỗi
        filterChain.doFilter(request, response);

    }
}
