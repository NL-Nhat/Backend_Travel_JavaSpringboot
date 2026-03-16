package com.example.travel.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.travel.dto.request.RegisterRequestDTO;
import com.example.travel.entity.UserEntity;
import com.example.travel.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public long countNumberUser() {
        return userRepository.count(); 
    }

    @Transactional
    public String register(RegisterRequestDTO request) {
        try {
            UserEntity userEntity = new UserEntity();

            userEntity.setFullName(request.getFullName());
            userEntity.setUserName(request.getUserName());
            userEntity.setPassWord(passwordEncoder.encode(request.getPassWord()));
            userEntity.setGender(request.getGender());
            userEntity.setPhoneNumber(request.getPhoneNumber());
            userEntity.setEmail(request.getEmail());

            userRepository.save(userEntity);

            return "Đăng ký thành công";

        } catch (Exception e) {
            throw new RuntimeException("Username, email hoặc số điện thoại đã tồn tại");
        }
    }
}
