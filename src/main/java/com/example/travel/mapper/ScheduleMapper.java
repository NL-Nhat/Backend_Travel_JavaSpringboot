package com.example.travel.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.example.travel.dto.response.ScheduleResponse;
import com.example.travel.entity.ScheduleEntity;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE) 
// Để Spring quản lý Mapper như một Bean, bỏ qua các giá trị null
public interface ScheduleMapper {

    ScheduleResponse toScheduleResponseDTO(ScheduleEntity scheduleEntity);

}
