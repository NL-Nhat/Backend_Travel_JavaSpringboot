package com.example.travel.mapper;

import org.mapstruct.Mapper;

import com.example.travel.dto.request.ImageTourRequestDTO;
import com.example.travel.entity.ImageTourEntity;

@Mapper(componentModel = "spring")
public interface ImageTourMapper {

    ImageTourEntity toImageTourEntity(ImageTourRequestDTO i);

}
