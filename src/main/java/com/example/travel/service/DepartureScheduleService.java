package com.example.travel.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.travel.dto.request.DepartureScheduleRequest;
import com.example.travel.dto.response.InfoBookingResponse;
import com.example.travel.dto.response.ScheduleResponse;
import com.example.travel.entity.DepartureScheduleEntity;
import com.example.travel.entity.TourEntity;
import com.example.travel.entity.UserEntity;
import com.example.travel.mapper.DepartureScheduleMapper;
import com.example.travel.mapper.ScheduleMapper;
import com.example.travel.repository.DepartureScheduleRepository;
import com.example.travel.repository.TourRepository;
import com.example.travel.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartureScheduleService{

    private final DepartureScheduleRepository departureCheduleRepository;
    private final ScheduleMapper scheduleMapper;
    private final DepartureScheduleMapper departureCheduleMapper;
    private final TourRepository tourRepository;
    private final UserRepository userRepository;

    public List<ScheduleResponse> getAllScheduleOfDepartureChedule(Integer id) {
        DepartureScheduleEntity d = departureCheduleRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch khởi hành với id này"));

        return d.getSchedules().stream().map(scheduleMapper::toScheduleResponseDTO).collect(Collectors.toList());
    }

    public InfoBookingResponse getInfoBooking(Integer id) {
        DepartureScheduleEntity d = departureCheduleRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch khởi hành với id này"));

        InfoBookingResponse i = departureCheduleMapper.toInfoResponseDTO(d);

        return i;
    }

    @Transactional
    public String createDepartureChedule(DepartureScheduleRequest request, Integer idTour) {

        DepartureScheduleEntity departureScheduleEntity = departureCheduleMapper.toDepartureScheduleEntity(request);
            
        UserEntity userEntity = userRepository.findById(request.getIdHuongDanVien())
            .orElseThrow(() -> new RuntimeException("Ko tìm thấy hướng dẫn viên với id này"));

        TourEntity tour = tourRepository.findById(idTour)
            .orElseThrow(() -> new IllegalArgumentException("Ko tìm thấy tour với id này"));

        departureScheduleEntity.setHuongDanVien(userEntity);
        departureScheduleEntity.setTour(tour);

        departureCheduleRepository.save(departureScheduleEntity);

        return "Thêm lịch khởi hành thành công";
    }
}
