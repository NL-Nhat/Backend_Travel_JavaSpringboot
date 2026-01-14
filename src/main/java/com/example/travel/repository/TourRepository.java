package com.example.travel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.travel.entity.TourEntity;

@Repository
public interface TourRepository extends JpaRepository<TourEntity, Integer>{

    //sử dụng @Query với nativeQuery = true để gọi Stored Procedure (thủ tục) trong MySQL.
    @Query(value = "CALL sp_LayTop5TourBanChay()", nativeQuery = true)
    List<Object[]> getFiveTourHot();

}
