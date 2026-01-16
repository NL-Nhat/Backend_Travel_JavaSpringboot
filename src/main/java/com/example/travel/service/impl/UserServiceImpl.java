package com.example.travel.service.impl;

import org.springframework.stereotype.Service;

import com.example.travel.repository.UserRepository;
import com.example.travel.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    public long countNumberUser() {
        return userRepository.count(); 
    }
}
