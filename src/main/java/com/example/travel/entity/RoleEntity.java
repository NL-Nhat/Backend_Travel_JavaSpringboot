package com.example.travel.entity;

public class RoleEntity {

    // @Id
    // @Column(name = "maVaiTro")
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    // private Integer id;

    // @Column(name = "tenVaiTro", nullable = false, unique = true)
    // private String roleName;

    // //tạo bảng trung gian quan hệ many to many tự động mà không cần tạo bảng thủ công
    // // @ManyToMany và @OnteToMany có fetchType mặc định là LAZY
    // @ManyToMany(mappedBy = "roles") //mappedBy = "roles", roles là tên biến ở UserEntity phần tạo bảng trung gian
    // private List<UserEntity> users = new ArrayList<>();
}
