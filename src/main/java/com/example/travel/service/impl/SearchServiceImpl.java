package com.example.travel.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.example.travel.dto.request.SearchRequestDTO;
import com.example.travel.dto.response.TourResponseDTO;
import com.example.travel.entity.TourEntity;
import com.example.travel.mapper.TourMapper;
import com.example.travel.repository.TourRepository;
import com.example.travel.service.SearchService;
import com.example.travel.specification.TourSpecification;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

/*
    @PersistenceContext là annotation trong JPA (Jakarta Persistence) 
        dùng để inject (tiêm) EntityManager vào một class (thường là Repository, DAO, hoặc Service) để làm việc với cơ sở dữ liệu.

    Chức năng:
        Cung cấp EntityManager được quản lý bởi container (Spring / Java EE).
        Quản lý vòng đời, transaction, persistence context tự động.
        Tránh việc tự tạo và tự đóng EntityManager.
*/
public class SearchServiceImpl implements SearchService{

    //@PersistenceContext
    //private final EntityManager entityManager;
    private final TourMapper tourMapper;
    private final TourRepository tourRepository;

    //2 cách

    @Override
    public List<TourResponseDTO> filterTour(SearchRequestDTO s) {

        //Tạo đối tượng Specification từ DTO
        Specification<TourEntity> spec = TourSpecification.filterTour(s);

        List<TourEntity> tours = tourRepository.findAll(spec);

        return tours.stream().map(tourMapper::mapToTourResponseDTO_Entity).collect(Collectors.toList());
    }

    @Override
    public List<TourResponseDTO> searchTour(String text) {
        Specification<TourEntity> spec = TourSpecification.searchTour(text);

        List<TourEntity> tours = tourRepository.findAll(spec);

        return tours.stream().map(tourMapper::mapToTourResponseDTO_Entity).collect(Collectors.toList());
    }

    // @Override
    // public List<TourResponseDTO> searchTour(SearchRequestDTO s) {

    //     StringBuilder jpql_select = new StringBuilder("SELECT t.maTour, t.tenTour, t.urlHinhAnhChinh, t.giaNguoiLon, t.diemDanhGiaTrungBinh, t.moTa, d.thanhPho, t.soLuongDanhGia, IFNULL(SUM(l.soLuongKhachToiDa - l.soLuongKhachDaDat), 0) AS soChoConLai, MIN(l.ngayKhoiHanh) AS ngayBatDau, MIN(l.gioKhoiHanh) AS gioBatDau  ");
    //     StringBuilder jpql_join = new StringBuilder(" FROM Tour t JOIN DiemDen d ON t.maDiemDen = d.maDiemDen LEFT JOIN LichKhoiHanh l ON t.maTour = l.maTour AND (l.ngayKhoiHanh > CURDATE() OR (l.ngayKhoiHanh = CURDATE() AND l.gioKhoiHanh >= CURTIME())) ");
    //     StringBuilder jpql_where = new StringBuilder(" WHERE 1 = 1   ");
    //     StringBuilder jpql_groupBy = new StringBuilder("  GROUP BY \n" + 
    //                     "  t.maTour, \n" + 
    //                     "  t.tenTour, \n" + 
    //                     "  t.urlHinhAnhChinh, \n" + 
    //                     "  t.giaNguoiLon, \n" + 
    //                     "  t.diemDanhGiaTrungBinh, \n" + 
    //                     "  t.moTa, \n" + 
    //                     "  d.tenDiemDen, \n" + 
    //                     "  t.soLuongDanhGia; ");


    //     Map<String, Object> param = new HashMap<>(); //chứa các giá trị lọc kiểu key, value

    //     if(s.getCity() != null && !s.getCity().isEmpty()) {
    //         // d.thanhPho là trường trong database, :cities là tên tham số ta đặt
    //         jpql_where.append(" AND d.thanhPho IN :cities "); //dùng IN để lọc nhiều hơn 1 thành phố, vd: thanhPho IN ('Đà Nẵng', 'Quảng Trị')
    //         param.put("cities", s.getCity()); // Truyền nguyên cái List vào đây
    //     }

    //     String finalSql = jpql_select.append(jpql_join)
    //                                  .append(jpql_where)
    //                                  .append(jpql_groupBy)
    //                                  .toString();
        
    //     Query query = entityManager.createNativeQuery(finalSql);
        
    //     for (Map.Entry<String, Object> entry : param.entrySet()) {
    //         query.setParameter(entry.getKey(), entry.getValue());
    //     }

    //     List<Object[]> rawResults = query.getResultList();

    //     return rawResults.stream().map(tourMapper::mapToTourResponseDTO_Object).collect(Collectors.toList());
    // }
}
