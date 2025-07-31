package com.DuAn1.volleyballshoes.app.dao;

import com.DuAn1.volleyballshoes.app.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PromotionDAO extends JpaRepository<Promotion, Long> {
    
    @Query("SELECT p FROM Promotion p WHERE p.active = true AND p.startDate <= :now AND p.endDate >= :now")
    List<Promotion> findActivePromotions(@Param("now") LocalDateTime now);
    
    @Query("SELECT p FROM Promotion p WHERE " +
           "(LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
           "(:active IS NULL OR p.active = :active)")
    List<Promotion> search(@Param("keyword") String keyword, @Param("active") Boolean active);
    
    @Query("SELECT p FROM Promotion p WHERE " +
           "p.startDate <= :endDate AND p.endDate >= :startDate AND " +
           "(:active IS NULL OR p.active = :active)")
    List<Promotion> findByDateRange(
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate,
        @Param("active") Boolean active
    );
    
    @Query("SELECT p FROM Promotion p WHERE " +
           "p.discountType = :discountType AND " +
           "(:active IS NULL OR p.active = :active)")
    List<Promotion> findByDiscountType(
        @Param("discountType") String discountType,
        @Param("active") Boolean active
    );
}
