package com.example.travel.config;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtTokenProvider {

    private String secret;
    private long expiration;

    //phien ban 0.11 tro len
    //tao khoa ky JWT
    public Key getSigningKey(){
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes); // Keys.hmacShaKeyFor() yêu cầu secret tối thiểu 256 bit (~32 ký tự).
    }

    //Tao token
    public String generateToken(UserDetails userDetails) {

        Map<String, Object> claims = new HashMap<>();
        String role = userDetails.getAuthorities()
            .iterator()
            .next()
            .getAuthority();

        claims.put("role", role);

        return Jwts.builder()
                .setClaims(claims) // Có thể thêm các thông tin phụ (role, email...) vào đây
                .setSubject(userDetails.getUsername()) // Lưu định danh (thường là username/email) vào claim 'sub'
                .setIssuedAt(new Date()) // Thời điểm tạo
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) // Thời điểm hết hạn
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // Ký (đóng dấu) bằng Secret Key, phiên bản 0.11 trở lên dùng hàm getSigningKey đã viết ở trên
                .compact(); // Nén lại thành chuỗi JWT (Header.Payload.Signature)
    }

}
