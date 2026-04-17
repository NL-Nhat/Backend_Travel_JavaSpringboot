package com.example.travel.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.example.travel.dto.request.UserRequest;
import com.example.travel.entity.UserEntity;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE) 
// Để Spring quản lý Mapper như một Bean, bỏ qua các giá trị null
public interface UserMapper {

    // Tạo hàm cập nhật entity CÓ SẴN
    void updateUserFromDto(UserRequest dto, @MappingTarget UserEntity entity);
}
