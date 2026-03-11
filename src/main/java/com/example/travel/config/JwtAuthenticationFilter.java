package com.example.travel.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.travel.service.impl.UserServiceImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

// Lớp kiểm tra mọi yêu cầu (Request) gửi đến server xem có đính kèm token JWT hợp lệ hay không
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter { // OncePerRequestFilter -> đảm bảo bộ lọc chỉ chạy
                                                                    // duy nhất một lần cho mỗi yêu cầu

    private final JwtProperties jwtProperties;
    private final UserServiceImpl userServiceImpl;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Lấy token nguyen ban từ repuest (loại bỏ chữ bearer từ request)
        String bearerToken = request.getHeader("Authorization");

        // Kiểm tra xem header có chứa thông tin Bearer không
        if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); // Không có token? Cho đi tiếp tới Filter sau (sẽ bị chặn lại nếu
                                                     // API yêu cầu quyền)
            return;
        }

        // Lấy phần chuỗi từ ký tự thứ 7 trở đi để loại bỏ từ "bearer "
        String jwtToken = bearerToken.substring(7);

        try {
            // Giải mã và lấy username (Subject)
            Claims payload = Jwts.parserBuilder()
                    .setSigningKey(jwtProperties.getSigningKey())
                    .build()
                    .parseClaimsJws(jwtToken)
                    .getBody();

            String username = payload.getSubject();

            if (username != null) {
                // Lấy thông tin chi tiết của user từ Database
                UserDetails userDetails = userServiceImpl.loadUserByUsername(username);

                // Tạo "thẻ thông hành" (Authentication object)
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null, // Không cần mật khẩu vì token đã chứng minh danh tính
                    userDetails.getAuthorities() // Danh sách các quyền (VD: ROLE_ADMIN)
                );

                // Gắn thêm thông tin chi tiết của request (như địa chỉ IP)
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Đưa thẻ thông hành cho Spring Security giữ
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
