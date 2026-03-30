package com.example.travel.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.example.travel.dto.request.ImageTourRequestDTO;
import com.example.travel.entity.ImageTourEntity;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE) 
// Để Spring quản lý Mapper như một Bean, bỏ qua các giá trị null
public interface ImageTourMapper {

    ImageTourEntity toImageTourEntity(ImageTourRequestDTO i);

}
