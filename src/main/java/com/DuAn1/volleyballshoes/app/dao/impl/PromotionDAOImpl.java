package com.DuAn1.volleyballshoes.app.dao.impl;

import com.DuAn1.volleyballshoes.app.dao.PromotionDAO;
import com.DuAn1.volleyballshoes.app.entity.Promotion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional
public class PromotionDAOImpl implements PromotionDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Promotion> findActivePromotions(LocalDateTime now) {
        return entityManager.createQuery(
            "SELECT p FROM Promotion p WHERE p.active = true AND p.startDate <= :now AND p.endDate >= :now", 
            Promotion.class)
            .setParameter("now", now)
            .getResultList();
    }

    @Override
    public List<Promotion> search(String keyword, Boolean active) {
        String jpql = "SELECT p FROM Promotion p WHERE " +
                     "(LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
                     "LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%')))";
        
        if (active != null) {
            jpql += " AND p.active = :active";
        }
        
        var query = entityManager.createQuery(jpql, Promotion.class)
                               .setParameter("keyword", keyword);
        
        if (active != null) {
            query.setParameter("active", active);
        }
        
        return query.getResultList();
    }

    @Override
    public List<Promotion> findByDateRange(LocalDateTime startDate, LocalDateTime endDate, Boolean active) {
        String jpql = "SELECT p FROM Promotion p WHERE " +
                     "p.startDate <= :endDate AND p.endDate >= :startDate";
        
        if (active != null) {
            jpql += " AND p.active = :active";
        }
        
        var query = entityManager.createQuery(jpql, Promotion.class)
                               .setParameter("startDate", startDate)
                               .setParameter("endDate", endDate);
        
        if (active != null) {
            query.setParameter("active", active);
        }
        
        return query.getResultList();
    }

    @Override
    public List<Promotion> findByDiscountType(String discountType, Boolean active) {
        String jpql = "SELECT p FROM Promotion p WHERE p.discountType = :discountType";
        
        if (active != null) {
            jpql += " AND p.active = :active";
        }
        
        var query = entityManager.createQuery(jpql, Promotion.class)
                               .setParameter("discountType", discountType);
        
        if (active != null) {
            query.setParameter("active", active);
        }
        
        return query.getResultList();
    }
}
