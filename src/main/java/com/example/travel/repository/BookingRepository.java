package com.example.travel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.travel.entity.BookingEntity;
import com.example.travel.entity.UserEntity;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Integer>{

    //Kiểm tra mã vé điện tử đã có trong db chưa
    boolean existsByIdTicket(String idTicket);

    List<BookingEntity> findByUser(UserEntity user);

}
