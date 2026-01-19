package com.example.travel.projection;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

/*
    + Projection thay cho object để map dữ liệu dễ dàng hơn, dễ bảo trì và cập nhật hơn
    + Interface Projection tự xử lý việc chuyển đổi kiểu dữ liệu một cách an toàn không cần ép kiểu như Object.
*/

public interface TourProjection {

    //Tên khớp với tên cột trả về trong thủ tục SQL hoặc có thể dùng value để map với tên trong SQL

    //@Value("#{target.maTour}") Hạn chế dùng @Value nếu không cần thiết để giúp Spring Data JPA chạy nhanh hơn
    Integer getMaTour();    
    String getTenTour();      
    String getUrlHinhAnhChinh();

    // Có thể làm thế này
    //@Value("#{target.giaNguoiLon * 0.9}") // Giả sử tính giá sau giảm thuế 10%
    BigDecimal getGiaNguoiLon(); 

    BigDecimal getDiemDanhGiaTrungBinh();
    String getMoTa();      
    String getTenDiemDen();  
    Integer getSoLuongDanhGia(); 
    Integer getSlot();         
    LocalDate getStartDate();   
    LocalTime getStartTime();   
}
