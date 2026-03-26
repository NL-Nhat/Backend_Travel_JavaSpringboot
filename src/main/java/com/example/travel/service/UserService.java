package com.example.travel.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.travel.dto.request.RegisterRequestDTO;
import com.example.travel.dto.response.UserResponseDTO;
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

    public UserResponseDTO getProfile(Integer id) {
        UserEntity user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Ko tim thay User voi id nay"));

        UserResponseDTO dto = new UserResponseDTO();

        dto.setAddress(user.getAddress());
        dto.setAvatar(user.getAvatar());
        dto.setDoB(user.getDoB());
        dto.setEmail(user.getEmail());
        dto.setFullName(user.getFullName());
        dto.setGender(user.getGender());
        dto.setPhoneNumber(user.getPhoneNumber());

        return dto;
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
