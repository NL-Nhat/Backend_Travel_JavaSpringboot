package com.example.travel.specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import com.example.travel.dto.request.SearchRequestDTO;
import com.example.travel.entity.DepartureCheduleEntity;
import com.example.travel.entity.DestinationEntity;
import com.example.travel.entity.TourEntity;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;

public class TourSpecification {

    public static Specification<TourEntity> filterTour(SearchRequestDTO s) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add((Predicate) cb.equal(root.get("status"), "Đang mở"));

            // Thực hiện Join với bảng Entity
            // "destination" và "departureChedules" chính là tên biến (khóa ngoại, manytoone) trong TourEntity
            Join<TourEntity, DestinationEntity> destinationJoin = root.join("destination", JoinType.INNER);

            // Dùng LEFT JOIN cho lịch khởi hành để tránh mất Tour nếu Tour đó chưa có lịch
            Join<TourEntity, DepartureCheduleEntity> departureJoin = root.join("departureChedules", JoinType.LEFT);

            //Lọc theo danh sách thành phố (nếu có yêu cầu lọc)
            if(s.getCity() != null && !s.getCity().isEmpty()) {
                //"city" ở đây phải là tên biến trong DestinationEntity
                predicates.add((Predicate) destinationJoin.get("city").in(s.getCity()));
            }

            // Lọc theo ngày bắt đầu từ(dateFrom)
            if (s.getDateFrom() != null) {
                // Giả sử trong DepartureCheduleEntity có trường tên là "departureDate"
                predicates.add((Predicate) cb.greaterThanOrEqualTo(departureJoin.get("startDate"), s.getDateFrom()));
            }

            // Lọc theo ngày bắt đầu đến (dateTo)
            if (s.getDateTo() != null) {
                predicates.add((Predicate) cb.lessThanOrEqualTo(departureJoin.get("startDate"), s.getDateTo()));
            }

            //Lọc theo giá đến (priceTo)
            if (s.getPriceTo() != null) {
                // "adultPrice" là tên biến trong TourEntity
                predicates.add((Predicate) cb.lessThanOrEqualTo(root.get("adultPrice"), s.getPriceTo()));
            }

            //Lọc theo giát từ (priceFrom)
            if (s.getPriceFrom() != null) {
                // "adultPrice" là tên biến trong TourEntity
                predicates.add((Predicate) cb.greaterThanOrEqualTo(root.get("adultPrice"), s.getPriceFrom()));
            }

            //Lọc theo số sao
            if (s.getNumberStar() != null && !s.getNumberStar().isEmpty()) {
                List<Predicate> starPredicates = new ArrayList<>();
                
                for (Integer star : s.getNumberStar()) {
                    // Tạo điều kiện cho từng số sao
                    BigDecimal start = BigDecimal.valueOf(star);
                    BigDecimal end = BigDecimal.valueOf(star + 1);
                    
                    starPredicates.add((Predicate) cb.between(root.get("averageRating"), start, end));
                }
                
                // Kết hợp các điều kiện bằng phép HOẶC (OR)
                predicates.add((Predicate) cb.or((jakarta.persistence.criteria.Predicate[]) starPredicates.toArray(new Predicate[0])));
            }

            // Đảm bảo kết quả không bị trùng lặp tour
            query.distinct(true);
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
