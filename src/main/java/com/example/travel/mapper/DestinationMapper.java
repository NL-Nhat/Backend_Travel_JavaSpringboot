package com.example.travel.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import com.example.travel.dto.response.DestinationResponse;
import com.example.travel.entity.DestinationEntity;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE) 
// Để Spring quản lý Mapper như một Bean, bỏ qua các giá trị null
public interface DestinationMapper {

    DestinationResponse toDestinationResponse(DestinationEntity destinationEntity);
}
