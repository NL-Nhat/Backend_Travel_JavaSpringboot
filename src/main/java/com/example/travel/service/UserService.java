package com.example.travel.service;

import java.util.HashMap;
import java.util.Map;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.travel.dto.request.RegisterRequestDTO;
import com.example.travel.dto.request.UserRequestDTO;
import com.example.travel.dto.response.UserResponseDTO;
import com.example.travel.entity.UserEntity;
import com.example.travel.mapper.UserMapper;
import com.example.travel.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CloudinaryService cloudinaryService;
    private final UserMapper userMapper;

    public long countNumberUser() {
        return userRepository.count(); 
    }

    public UserResponseDTO getProfile(String userName) {
        
        UserEntity user = userRepository.findByUserName(userName)
            .orElseThrow(() -> new RuntimeException("ko tim thay user voi userName nay."));
            
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
    public Map<String, ?> updateProfile(MultipartFile file, UserRequestDTO dto, String userName) {
        UserEntity user = userRepository.findByUserName(userName)
            .orElseThrow(() -> new RuntimeException("ko tim thay user voi userName nay."));

        userMapper.updateUserFromDto(dto, user);

        if (file != null && !file.isEmpty()) {
            try {
                String fileName = "avatar" + System.currentTimeMillis();
                String imageURL = cloudinaryService.uploadImage(file, fileName);

                user.setAvatar(imageURL);
            } catch (Exception e) {
                throw new RuntimeException("Upload ảnh thất bại: " + e.getMessage(), e);
            }
        }

        userRepository.save(user);

        Map<String, Object> result = new HashMap<>();
        result.put("id", user.getId());
        result.put("message", "Update thành công");

        return result;
    }

    @Transactional
    public String register(RegisterRequestDTO request) {

        if (userRepository.existsByUserName(request.getUserName())) {
            throw new RuntimeException("Username đã tồn tại");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email đã tồn tại");
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setUserName(request.getUserName());
        userEntity.setFullName(request.getFullName());
        userEntity.setPassWord(passwordEncoder.encode(request.getPassWord()));
        userEntity.setGender(request.getGender());
        userEntity.setPhoneNumber(request.getPhoneNumber());
        userEntity.setEmail(request.getEmail());

        userRepository.save(userEntity);

        return "Đăng ký thành công";
    }
}
