package com.DuAn1.volleyballshoes.app.dao;

import com.DuAn1.volleyballshoes.app.entity.Promotion;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PromotionDAO extends CrudDAO<Promotion, Integer> {

    List<Promotion> findActivePromotions();

    List<Promotion> search();

    List<Promotion> findByDateRange();

    List<Promotion> findByDiscountType();

    Promotion update(Promotion promotion);

    boolean existsById(@Param("id") Long id);

    void deleteById();
    
    Promotion findByCode(String code);
}
