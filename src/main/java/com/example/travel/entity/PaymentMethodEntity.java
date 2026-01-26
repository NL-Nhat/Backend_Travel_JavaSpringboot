package com.example.travel.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@DynamicInsert
@Table(name = "PhuongThucThanhToan")
public class PaymentMethodEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maPTTT")
    private Integer id;

    @Column(name = "tenPTTT", nullable = false, unique = true)
    private String nameMethod;

    @Column(name = "trangThai")
    private String status;

    @OneToMany(mappedBy = "paymentMethod", fetch = FetchType.LAZY)
    private List<PaymentEntity> payments = new ArrayList<PaymentEntity>();
}
