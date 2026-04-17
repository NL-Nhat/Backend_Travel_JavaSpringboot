package com.example.travel.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.travel.dto.response.DestinationResponse;
import com.example.travel.mapper.DestinationMapper;
import com.example.travel.repository.DestinationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DestinationService {

    private final DestinationRepository destinationRepository;
    private final DestinationMapper destinationMapper;

    public List<DestinationResponse> getAll() {

        return destinationRepository.findAll()
            .stream()
            .map(destinationMapper::toDestinationResponse)
            .toList();
    }
}
