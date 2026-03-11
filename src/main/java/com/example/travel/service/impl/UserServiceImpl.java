package com.example.travel.service.impl;

import java.util.Collections;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.travel.entity.UserEntity;
import com.example.travel.repository.UserRepository;
import com.example.travel.service.UserService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService{

    private final UserRepository userRepository;

    @Override
    public long countNumberUser() {
        return userRepository.count(); 
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException{
        UserEntity user = userRepository.findByUserName(userName)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if(user.getStatus().equals("Khóa")) {
            throw new DisabledException("Tài khoản của bạn đã bị khóa");
        }

        return new User(
            user.getUserName(),
            user.getPassWord(),
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRoles()))
        );
    }
}
