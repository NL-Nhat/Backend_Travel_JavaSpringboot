package com.example.travel.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.travel.dto.request.RegisterRequest;
import com.example.travel.dto.request.UserRequest;
import com.example.travel.dto.response.TourGuideResponse;
import com.example.travel.dto.response.UserResponse;
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

    public UserResponse getProfile(String userName) {
        
        UserEntity user = userRepository.findByUserName(userName)
            .orElseThrow(() -> new RuntimeException("ko tim thay user voi userName nay."));
            
        UserResponse response = userMapper.toUserResponse(user);

        return response;
    }

    @Transactional
    public Map<String, ?> updateProfile(MultipartFile file, UserRequest dto, String userName) {
        UserEntity user = userRepository.findByUserName(userName)
            .orElseThrow(() -> new RuntimeException("ko tim thay user voi userName nay."));

        userMapper.updateUserFromDto(dto, user);

        if (file != null && !file.isEmpty()) {
            try {
                String fileName = "avatar/avatar_" + user.getId();
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
    public String register(RegisterRequest request) {

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

    public List<TourGuideResponse> getTourGuide() {

        return userRepository.findAllByRole("HUONGDANVIEN")
            .stream()
            .map(user -> new TourGuideResponse(user.getId(), user.getFullName()))
            .toList();
    }
}
