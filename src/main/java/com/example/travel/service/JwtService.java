package com.example.travel.service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.travel.entity.UserEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Data;

@Service
@Component
// @ConfigurationProperties(prefix = "security.jwt") dùng nếu không dùng @Value như dưới
@Data
public class JwtService {

    @Value("${security.jwt.secret-key}")
    private String secret;

    @Value("${security.jwt.expiration}")
    private long expiration;

    @Value("${security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    //phien ban 0.11 tro len
    //tao khoa ky JWT
    public Key getSigningKey(){
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes); // Keys.hmacShaKeyFor() yêu cầu secret tối thiểu 256 bit (~32 ký tự).
    }

    // Hàm in Access Token
    public String generateAccessToken(UserDetails userDetails) {
        return buildToken(new HashMap<>(), userDetails, expiration);
    }

    // Hàm in Refresh Token
    public String generateRefreshToken(UserDetails userDetails) {
        return buildToken(new HashMap<>(), userDetails, refreshExpiration); 
    }

    // Hàm dùng chung để build chuỗi JWT
    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration) {

        Map<String, Object> claims = new HashMap<>();
        String role = userDetails.getAuthorities()
            .iterator()
            .next()
            .getAuthority();

        claims.put("role", role);

        if (userDetails instanceof UserEntity) {
            claims.put("id", ((UserEntity) userDetails).getId());
        }

        return Jwts.builder()
                .setClaims(claims) // Có thể thêm các thông tin phụ (role, email...) vào đây
                .setSubject(userDetails.getUsername()) // Lưu định danh (thường là username/email) vào claim 'sub'
                .setIssuedAt(new Date()) // Thời điểm tạo
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) // Thời điểm hết hạn
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // Ký (đóng dấu) bằng Secret Key, phiên bản 0.11 trở lên dùng hàm getSigningKey đã viết ở trên
                .compact(); // Nén lại thành chuỗi JWT (Header.Payload.Signature)
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    //Hàm kiểm tra token hợp lệ
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);

        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    //Hàm kiểm tra token hết hạn
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }
}
