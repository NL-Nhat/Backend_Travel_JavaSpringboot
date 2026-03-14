package com.example.travel.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.travel.config.JwtTokenProvider;
import com.example.travel.dto.request.LoginRequestDTO;
import com.example.travel.dto.request.RegisterRequestDTO;
import com.example.travel.service.AuthService;
import com.example.travel.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
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
            UserDetails userDetails = authService.loadUserByUsername(request.getUserName());

            // Tạo token
            String jwtToken = jwtTokenProvider.generateToken(userDetails);
            
            // Trả token về cho Client
            return ResponseEntity.ok(jwtToken);

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

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDTO request) {
        
        return ResponseEntity.ok(userService.register(request));
    }
    

}
