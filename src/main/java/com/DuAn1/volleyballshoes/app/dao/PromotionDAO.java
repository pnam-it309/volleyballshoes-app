package com.DuAn1.volleyballshoes.app.dao;

import com.DuAn1.volleyballshoes.app.entity.Promotion;
import java.time.LocalDateTime;
import java.util.List;

public interface PromotionDAO extends CrudDAO<Promotion, Integer> {

    List<Promotion> findActivePromotions();

    List<Promotion> search();

    List<Promotion> findByDateRange();

    List<Promotion> findByDiscountType();

    Promotion update(Promotion promotion);

    boolean existsById(Long id);

    void deleteById();
    
    Promotion findByCode(String code);
}
