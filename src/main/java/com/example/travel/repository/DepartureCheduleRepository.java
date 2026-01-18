package com.example.travel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.travel.entity.DepartureCheduleEntity;

@Repository
public interface DepartureCheduleRepository extends JpaRepository<DepartureCheduleEntity, Integer>{

}
