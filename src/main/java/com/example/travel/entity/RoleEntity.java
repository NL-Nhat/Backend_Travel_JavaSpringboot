package com.example.travel.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "VaiTro")
public class RoleEntity {

    @Id
    @Column(name = "maVaiTro")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "tenVaiTro", nullable = false, unique = true)
    private String roleName;

    //tạo bảng trung gian quan hệ many to many tự động mà không cần tạo bảng thủ công
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY) //mappedBy = "roles", roles là tên biến ở UserEntity phần tạo bảng trung gian
    private List<UserEntity> users = new ArrayList<>();
}
