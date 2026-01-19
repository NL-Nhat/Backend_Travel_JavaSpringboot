package com.example.travel.projection;

import java.time.LocalDateTime;

/*
    + Projection thay cho object để map dữ liệu dễ dàng hơn, dễ bảo trì và cập nhật hơn
    + Interface Projection tự xử lý việc chuyển đổi kiểu dữ liệu một cách an toàn không cần ép kiểu như Object.
*/

public interface ReviewProjection {

    //Tên khớp với tên cột trả về trong thủ tục SQL hoặc có thể dùng value để map với tên trong SQL

    //@Value("#{target.maTour}") Hạn chế dùng @Value nếu không cần thiết để giúp Spring Data JPA chạy nhanh hơn
    Integer getDiemSo();
    String getTenTour();
    String getBinhLuan();
    String getHoTen();
    String getAnhDaiDien();
    LocalDateTime getThoiGianTao();

}
