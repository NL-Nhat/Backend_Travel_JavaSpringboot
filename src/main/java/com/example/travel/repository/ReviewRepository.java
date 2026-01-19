package com.example.travel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.travel.entity.ReviewEntity;
import com.example.travel.projection.ReviewProjection;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Integer>{

    //sử dụng @Query với nativeQuery = true để gọi Stored Procedure (thủ tục) trong MySQL.
    @Query(value = "CALL sp_LayTop3DanhGiaNoiBat()", nativeQuery = true)
    List<ReviewProjection> getTop3Review();
}
