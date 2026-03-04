package com.example.travel.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.travel.dto.request.DepartureScheduleRequestDTO;
import com.example.travel.dto.request.ScheduleRequestDTO;
import com.example.travel.dto.response.InfoBookingResponseDTO;
import com.example.travel.dto.response.ScheduleResponseDTO;
import com.example.travel.entity.DepartureScheduleEntity;
import com.example.travel.entity.ScheduleEntity;
import com.example.travel.entity.TourEntity;
import com.example.travel.entity.UserEntity;
import com.example.travel.mapper.DepartureScheduleMapper;
import com.example.travel.mapper.ScheduleMapper;
import com.example.travel.repository.DepartureCheduleRepository;
import com.example.travel.repository.ScheduleRepository;
import com.example.travel.repository.TourRepository;
import com.example.travel.repository.UserRepository;
import com.example.travel.service.DepartureCheduleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartureCheduleServiceImpl implements DepartureCheduleService{

    private final DepartureCheduleRepository departureCheduleRepository;
    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;
    private final DepartureScheduleMapper departureCheduleMapper;
    private final TourRepository tourRepository;
    private final UserRepository userRepository;

    @Override
    public List<ScheduleResponseDTO> getAllScheduleOfDepartureChedule(Integer id) {
        DepartureScheduleEntity d = departureCheduleRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch khởi hành với id này"));

        return d.getSchedules().stream().map(scheduleMapper::toScheduleResponseDTO).collect(Collectors.toList());
    }

    @Override
    public InfoBookingResponseDTO getInfoBooking(Integer id) {
        DepartureScheduleEntity d = departureCheduleRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch khởi hành với id này"));

        InfoBookingResponseDTO i = departureCheduleMapper.toInfoResponseDTO(d);

        return i;
    }

    @Override
    @Transactional
    public String addDepartureChedule(List<DepartureScheduleRequestDTO> d, Integer idTour) {
        TourEntity tour = tourRepository.findById(idTour).orElseThrow(() -> new RuntimeException("Ko tìm thấy tour với id này"));

        for(DepartureScheduleRequestDTO de : d) {
            DepartureScheduleEntity dpe = new DepartureScheduleEntity();
            dpe.setTour(tour);
            dpe.setStartDate(de.getStartDate());
            dpe.setEndDate(de.getEndDate());
            dpe.setStartTime(de.getStartTime());
            dpe.setEndTime(de.getEndTime());
            dpe.setMaxGuest(de.getMaxGuest());

            UserEntity userEntity = userRepository.findById(de.getHuongDanVien()).orElseThrow(() -> new RuntimeException("Ko tìm thấy hướng dẫn viên với id này"));
            dpe.setHuongDanVien(userEntity);

            dpe = departureCheduleRepository.save(dpe);

            for(ScheduleRequestDTO schl : de.getSchedules()) {
                ScheduleEntity scheduleEntity = new ScheduleEntity();
                scheduleEntity.setDate(schl.getDate());
                scheduleEntity.setDepartureSchedule(dpe);
                scheduleEntity.setDescribe(schl.getDescribe());
                scheduleEntity.setTime(schl.getTime());
                scheduleEntity.setWork(schl.getWork());

                scheduleRepository.save(scheduleEntity);
            }

        }

        return "Thêm " + d.size() +" lịch khởi hành thành công";
    }
}
