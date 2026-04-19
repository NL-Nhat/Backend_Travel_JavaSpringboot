package com.example.travel.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.travel.dto.request.DepartureScheduleRequest;
import com.example.travel.dto.request.ScheduleRequest;
import com.example.travel.dto.response.InfoBookingResponse;
import com.example.travel.dto.response.ScheduleResponse;
import com.example.travel.entity.DepartureScheduleEntity;
import com.example.travel.entity.ScheduleEntity;
import com.example.travel.entity.TourEntity;
import com.example.travel.entity.UserEntity;
import com.example.travel.mapper.DepartureScheduleMapper;
import com.example.travel.mapper.ScheduleMapper;
import com.example.travel.repository.DepartureScheduleRepository;
import com.example.travel.repository.ScheduleRepository;
import com.example.travel.repository.TourRepository;
import com.example.travel.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartureScheduleService{

    private final DepartureScheduleRepository departureCheduleRepository;
    private final ScheduleRepository scheduleRepository;
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
    public String addDepartureChedule(List<DepartureScheduleRequest> d, Integer idTour) {
        TourEntity tour = tourRepository.findById(idTour).orElseThrow(() -> new RuntimeException("Ko tìm thấy tour với id này"));

        for(DepartureScheduleRequest de : d) {
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

            for(ScheduleRequest schl : de.getSchedules()) {
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
