package com.example.travel.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "LichTrinh")
@DynamicUpdate // Chỉ update những trường có thay đổi (rất tốt cho hiệu suất)
public class ScheduleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maLichTrinh")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maLichKhoiHanh")
    private DepartureScheduleEntity departureSchedule;

    @Column(name = "ngay", nullable = false)
    private LocalDate date;

    @Column(name = "gio", nullable = false)
    private LocalTime time;

    @Column(name = "hoatDong", nullable = false)
    private String work;

    @Column(name = "moTa")
    private String describe;
}
