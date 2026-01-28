package com.example.travel.entity;

import java.time.LocalDate;
import java.time.LocalTime;
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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "LichKhoiHanh")
@DynamicUpdate
@DynamicInsert
public class DepartureScheduleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maLichKhoiHanh")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maTour")
    private TourEntity tour;

    @Column(name = "ngayKhoiHanh", nullable = false)
    private LocalDate startDate;

    @Column(name = "gioKhoiHanh", nullable = false)
    private LocalTime startTime;

    @Column(name = "ngayKetThuc", nullable = false)
    private LocalDate endDate;

    @Column(name = "gioKetThuc")
    private LocalTime endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "huongDanVien")
    private UserEntity huongDanVien;

    @Column(name = "soLuongKhachToiDa")
    private Integer maxGuest;

    @Column(name = "soLuongKhachDaDat")
    private Integer numberGuestBooked;

    @OneToMany(mappedBy = "departureSchedule", fetch = FetchType.LAZY)
    private List<BookingEntity> bookings = new ArrayList<BookingEntity>();

    @OneToMany(mappedBy = "departureSchedule", fetch = FetchType.LAZY)
    private List<ScheduleEntity> schedules = new ArrayList<ScheduleEntity>();

}
