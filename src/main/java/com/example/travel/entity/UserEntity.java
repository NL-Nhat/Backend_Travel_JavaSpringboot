package com.example.travel.entity;

import java.time.LocalDate;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert // Chỉ insert những trường khách hàng có nhập
@DynamicUpdate // Chỉ update những trường có thay đổi (rất tốt cho hiệu suất)
@Table(name = "NguoiDung")
public class UserEntity {

    @Id
    @Column(name = "maNguoiDung")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "tenDangNhap", nullable = false, unique = true)
    private String userName;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "matKhau", nullable = false)
    private String passWord;

    @Column(name = "hoTen", nullable = false)
    private String fullName;

    @Column(name = "soDienThoai", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "diaChi")
    private String address;

    @Column(name = "gioiTinh", columnDefinition = "TINYINT DEFAULT 1")
    private Integer gender;

    @Column(name = "ngaySinh")
    private LocalDate doB;

    @Column(name = "anhDaiDien")
    private String avatar;

    @Column(name = "vaiTro", nullable = false)
    private String role;

    @Column(name = "trangThai", nullable = false)
    private String status;

    @CreationTimestamp
    @Column(name = "thoiGianTao", nullable = false, updatable = false)
    private LocalDateTime createAt;

    @OneToMany(mappedBy = "huongDanVien", fetch = FetchType.LAZY)
    private List<DepartureCheduleEntity> schedules =new ArrayList<DepartureCheduleEntity>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<BookingEntity> bookings = new ArrayList<BookingEntity>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<ReviewEntity> reviews = new ArrayList<ReviewEntity>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<FavoriteTourEntity> favoriteTours = new ArrayList<FavoriteTourEntity>();

}
