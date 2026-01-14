package com.example.travel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.travel.entity.DestinationEntity;

@Repository
public interface DestinationRepository extends JpaRepository<DestinationEntity, Integer>{

}
