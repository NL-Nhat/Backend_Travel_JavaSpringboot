package com.example.travel.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TourYeuThich")
public class FavoriteTourEntity {

    @EmbeddedId
    private FavoriteTourId id; // dùng class id để làm khóa chính

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "maNguoiDung")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("tourId")
    @JoinColumn(name = "maTour")
    private TourEntity tour;

    @CreationTimestamp // Tự động lấy thời gian hiện tại khi insert
    @Column(name = "ngayLuu", nullable = false, updatable = false) // updatable = false không cho phép cập nhật lại khi đã nhập dữ liệu
    private LocalDateTime createAt;
}
