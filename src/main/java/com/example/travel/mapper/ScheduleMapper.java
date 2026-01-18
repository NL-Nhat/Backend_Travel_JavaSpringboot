package com.example.travel.mapper;

import org.mapstruct.Mapper;

import com.example.travel.dto.response.ScheduleResponseDTO;
import com.example.travel.entity.ScheduleEntity;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {

    ScheduleResponseDTO toScheduleResponseDTO(ScheduleEntity scheduleEntity);
}
