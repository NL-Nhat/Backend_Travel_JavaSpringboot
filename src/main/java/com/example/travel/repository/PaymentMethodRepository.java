package com.example.travel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.travel.entity.PaymentMethodEntity;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethodEntity, Integer>{

    public List<PaymentMethodEntity> findByStatus(String status);
}
