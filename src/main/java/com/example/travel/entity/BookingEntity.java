package com.example.travel.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
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
@DynamicUpdate
@DynamicInsert
@Table(name = "DatTour")
public class BookingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maDatTour")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maNguoiDung")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maLichKhoiHanh")
    private DepartureCheduleEntity departureChedule;

    @Column(name = "soNguoiLon", nullable = false)
    private Integer adultNumber;

    @Column(name = "soTreEm", nullable = false)
    private Integer childNumber;

    @Column(name = "tongTien", nullable = false, precision = 18, scale = 3)
    private BigDecimal totalAmount;

    @Column(name = "trangThaiDatTour")
    private String bookingStatus;

    @Column(name = "trangThaiThanhToan")
    private String paymentStatus;

    @CreationTimestamp
    @Column(name = "ngayDat", nullable = false, updatable = false)
    private LocalDateTime bookedDate;

    @Column(name = "ngayHuy")
    private LocalDateTime cancelDate;

    @Column(name = "lyDoHuy")
    private String cancelReason;

    @OneToMany(mappedBy = "booking", fetch = FetchType.LAZY)
    private List<PaymentEntity> payments = new ArrayList<PaymentEntity>();

    @OneToMany(mappedBy = "booking", fetch = FetchType.LAZY)
    private List<ParticipantEntity> participants = new ArrayList<ParticipantEntity>();
}
