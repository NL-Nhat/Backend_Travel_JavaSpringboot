package com.example.travel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.travel.entity.ImageTourEntity;

@Repository
public interface ImageTourRepository extends JpaRepository<ImageTourEntity, Integer>{

}
