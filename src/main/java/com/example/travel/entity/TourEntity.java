package com.example.travel.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Tour")
public class TourEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maTour")
    private Integer id;

    @Column(name = "tenTour", nullable = false)
    private String tourName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maDiemDen")
    private DestinationEntity destination;

    @Column(name = "moTa")
    private String describe;

    @Column(name = "giaNguoiLon", precision = 18, scale = 3)
    private BigDecimal adultPrice;

    @Column(name = "giaTreEm", precision = 18, scale = 3)
    private BigDecimal childPrice;

    @Column(name = "urlHinhAnhChinh")
    private String image;

    @Column(name = "diemDanhGiaTrungBinh", precision = 3, scale = 1)
    private BigDecimal averageRating;

    @Column(name = "soLuongDanhGia")
    private Integer numberOfReview;

    @Column(name = "trangThai")
    private String status;

    @OneToMany(mappedBy = "tour", fetch = FetchType.LAZY)
    private List<DepartureCheduleEntity> departureChedules = new ArrayList<DepartureCheduleEntity>();

    @OneToMany(mappedBy = "tour", fetch = FetchType.LAZY)
    private List<ImageTourEntity> imageTours = new ArrayList<ImageTourEntity>();

    @OneToMany(mappedBy = "tour", fetch = FetchType.LAZY)
    private List<ReviewEntity> reviews = new ArrayList<ReviewEntity>();

    @OneToMany(mappedBy = "tour", fetch = FetchType.LAZY)
    private List<FavoriteTourEntity> favoriteTours = new ArrayList<FavoriteTourEntity>();
}
