package com.example.travel.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.travel.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer>{
    Optional<UserEntity> findByUserName(String userName);

    public Boolean existsByUserName(String userName);

    public Boolean existsByEmail(String email);

    public List<UserEntity> findAllByRole(String role);
}
