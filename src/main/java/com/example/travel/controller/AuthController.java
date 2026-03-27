package com.example.travel.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.travel.dto.request.LoginRequestDTO;
import com.example.travel.dto.request.RegisterRequestDTO;
import com.example.travel.service.AuthService;
import com.example.travel.service.JwtService;
import com.example.travel.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {

        try {
            //  Kiểm tra username và pass
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getUserName(), 
                    request.getPassWord()
                )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            // Nếu không có lỗi (passed), lấy thông tin user
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // Tạo cả 2 loại Token
            String accessToken = jwtService.generateAccessToken(userDetails);
            String refreshToken = jwtService.generateRefreshToken(userDetails);
            
            // Đóng gói Access Token vào Cookie
            ResponseCookie jwtCookie = ResponseCookie.from("accessToken", accessToken)
                .httpOnly(true)       // Bật HttpOnly: Javascript không thể đọc được (Chống XSS)
                .secure(false)        // Để false khi chạy localhost HTTP. Lên production (HTTPS) bắt buộc đổi thành true!
                .sameSite("Strict") // Giảm nguy cơ CSRF.
                .path("/")            // Cookie có tác dụng trên toàn bộ đường dẫn của web
                .maxAge(jwtService.getExpiration() / 1000)      // Sống 15 phút, chia 1000 vì đơn vị trong file yaml và maxAge() khác nhau
                // file yaml dùng milliseconds
                // maxage() dùng seconds
                .build();

            // Đóng gói Refresh Token vào Cookie thứ 2
            ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(false)
                .sameSite("Strict") // Giảm nguy cơ CSRF.
                .path("/api/auth/refresh") // Mẹo bảo mật: Chỉ gửi Cookie này khi gọi API Refresh
                .maxAge(jwtService.getRefreshExpiration() / 1000) // Sống 7 ngày
                .build();

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Đăng nhập thành công!");
            
            String role = userDetails.getAuthorities().iterator().next().getAuthority();
            responseBody.put("role", role);

            // Gắn Cookie vào Header của Response gửi về cho Trình duyệt
            return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body(responseBody);

        } catch (BadCredentialsException e) {
            // Lỗi này xảy ra khi sai tên đăng nhập hoặc sai mật khẩu
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                .body("Tên đăng nhập hoặc mật khẩu không chính xác!");
                                
        } catch (DisabledException e) {
            // Lỗi này xảy ra nếu tài khoản bị vô hiệu hóa (TrangThai = khóa)
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                .body("Tài khoản của bạn đã bị khóa hoặc chưa được kích hoạt!");
                                
        } catch (AuthenticationException e) {
            // Bắt các lỗi xác thực chung khác của Spring Security
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                .body("Đăng nhập thất bại: " + e.getMessage());
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@CookieValue(name = "refreshToken", required = false) String refreshToken) {

        if (refreshToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Không tìm thấy Refresh Token");
        }

        try {
            // 2. Giải mã và kiểm tra Refresh Token (dùng chung hàm hoặc viết hàm riêng trong JwtService)
            String username = jwtService.extractUsername(refreshToken); // Giả sử bạn có hàm này
            
            // 3. Nếu hợp lệ, tải thông tin user lên
            UserDetails user = authService.loadUserByUsername(username);

            if (!jwtService.isTokenValid(refreshToken, user)) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body("Refresh Token không hợp lệ!");
            }
            // (Tùy chọn nâng cao: Bạn nên kiểm tra xem token này có bị thu hồi trong Database chưa)

            // 4. In ra một Access Token MỚI TINH
            String newAccessToken = jwtService.generateAccessToken(user);

            // 5. Nhét Access Token mới vào Cookie và trả về
            ResponseCookie newJwtCookie = ResponseCookie.from("accessToken", newAccessToken)
                    .httpOnly(true)
                    .secure(false)
                    .sameSite("Strict") // Giảm nguy cơ CSRF.
                    .path("/")
                    .maxAge(jwtService.getExpiration() / 1000)
                    .build();

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, newJwtCookie.toString())
                    .body("Đã cấp lại Access Token mới");

        } catch (Exception e) {
            // Token hết hạn 7 ngày, hoặc bị sai chữ ký
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh Token không hợp lệ. Vui lòng đăng nhập lại!");
        }
    }
    

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDTO request) {
        
        return ResponseEntity.ok(userService.register(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        
        // 1. Tạo một Cookie rỗng đè lên Access Token cũ
        ResponseCookie cleanJwtCookie = ResponseCookie.from("accessToken", "") // Giá trị rỗng
                .httpOnly(true)
                .secure(false) // Nhớ đổi thành true khi lên HTTPS (Production)
                .path("/")
                .maxAge(0) // ⏳ Cốt lõi: Đặt tuổi thọ về 0 để trình duyệt xóa ngay lập tức
                .build();

        // 2. Tạo một Cookie rỗng đè lên Refresh Token cũ
        ResponseCookie cleanRefreshCookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(false)
                .path("/api/auth/refresh") // Phải khớp với path lúc tạo thì mới ghi đè được
                .maxAge(0)
                .build();

        // 3. Gửi 2 Cookie này về cho trình duyệt
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cleanJwtCookie.toString())
                .header(HttpHeaders.SET_COOKIE, cleanRefreshCookie.toString())
                .body("Đã đăng xuất thành công!");
    }
}
