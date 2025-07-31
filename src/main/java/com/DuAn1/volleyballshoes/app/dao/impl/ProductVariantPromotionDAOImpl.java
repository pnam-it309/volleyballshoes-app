package com.DuAn1.volleyballshoes.app.dao.impl;

import com.DuAn1.volleyballshoes.app.dao.ProductVariantPromotionDAO;
import com.DuAn1.volleyballshoes.app.entity.ProductVariant;
import com.DuAn1.volleyballshoes.app.entity.ProductVariantPromotion;
import com.DuAn1.volleyballshoes.app.entity.Promotion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class ProductVariantPromotionDAOImpl implements ProductVariantPromotionDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ProductVariantPromotion> findByProductVariantId(Long productVariantId) {
        return entityManager.createQuery(
            "SELECT pvp FROM ProductVariantPromotion pvp WHERE pvp.productVariant.id = :productVariantId",
            ProductVariantPromotion.class)
            .setParameter("productVariantId", productVariantId)
            .getResultList();
    }

    @Override
    public List<ProductVariantPromotion> findByPromotionId(Long promotionId) {
        return entityManager.createQuery(
            "SELECT pvp FROM ProductVariantPromotion pvp WHERE pvp.promotion.id = :promotionId",
            ProductVariantPromotion.class)
            .setParameter("promotionId", promotionId)
            .getResultList();
    }

    @Override
    public List<ProductVariantPromotion> findActivePromotionsForProductVariant(Long productVariantId) {
        return entityManager.createQuery(
            "SELECT pvp FROM ProductVariantPromotion pvp " +
            "JOIN pvp.promotion p " +
            "WHERE pvp.productVariant.id = :productVariantId AND " +
            "p.active = true AND " +
            "p.startDate <= CURRENT_TIMESTAMP AND " +
            "p.endDate >= CURRENT_TIMESTAMP",
            ProductVariantPromotion.class)
            .setParameter("productVariantId", productVariantId)
            .getResultList();
    }

    @Override
    public void deleteByPromotionId(Long promotionId) {
        entityManager.createQuery("DELETE FROM ProductVariantPromotion pvp WHERE pvp.promotion.id = :promotionId")
                   .setParameter("promotionId", promotionId)
                   .executeUpdate();
    }

    @Override
    public void deleteByPromotion(Promotion promotion) {
        entityManager.createQuery("DELETE FROM ProductVariantPromotion pvp WHERE pvp.promotion = :promotion")
                   .setParameter("promotion", promotion)
                   .executeUpdate();
    }

    @Override
    public List<ProductVariant> findProductVariantsByPromotionId(Long promotionId) {
        return entityManager.createQuery(
            "SELECT pvp.productVariant FROM ProductVariantPromotion pvp WHERE pvp.promotion.id = :promotionId",
            ProductVariant.class)
            .setParameter("promotionId", promotionId)
            .getResultList();
    }

    @Override
    public boolean existsByProductVariantIdAndPromotionId(Long productVariantId, Long promotionId) {
        Long count = entityManager.createQuery(
            "SELECT COUNT(pvp) FROM ProductVariantPromotion pvp " +
            "WHERE pvp.productVariant.id = :productVariantId AND pvp.promotion.id = :promotionId",
            Long.class)
            .setParameter("productVariantId", productVariantId)
            .setParameter("promotionId", promotionId)
            .getSingleResult();
        return count != null && count > 0;
    }
}
