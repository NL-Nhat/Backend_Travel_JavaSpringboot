package com.example.travel.service;

import com.example.travel.dto.request.RegisterRequestDTO;

public interface UserService {

    public long countNumberUser();

    public String register(RegisterRequestDTO request);
}
