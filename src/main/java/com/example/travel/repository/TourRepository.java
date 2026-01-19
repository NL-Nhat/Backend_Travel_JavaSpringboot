package com.example.travel.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.travel.entity.TourEntity;

/*
    JpaSpecificationExecutor là một interface cho phép thực hiện các truy vấn động (dynamic queries)
    một cách linh hoạt thay vì viết các hàm tìm kiếm cố định trong Repository.

    Phần này đang sử dụng để làm chức năng lọc và tìm kiếm tour
*/
@Repository
public interface TourRepository extends JpaRepository<TourEntity, Integer>, JpaSpecificationExecutor<TourEntity> {

    //sử dụng @Query với nativeQuery = true để gọi Stored Procedure (thủ tục) trong MySQL.
    @Query(value = "CALL sp_LayTop5TourBanChay()", nativeQuery = true)
    List<Object[]> getFiveTourHot();

    //Phân trang, lấy tất cả tour theo trạng thái
    // @Query(value = "CALL sp_LayDanhSachTourDangMo()", nativeQuery = true)
    // Page<Object[]> getAllTourByStatus(Pageable pageable);

    @Query(value = "CALL sp_LayDanhSachTourDangMo_Paging(?1, ?2)", nativeQuery = true)
    List<Object[]> getTourDangMoPaging(int offset, int limit);

    // @Query(value = "CALL sp_DemTongTourDangMo()", nativeQuery = true)
    long countByStatus(String status);

}
