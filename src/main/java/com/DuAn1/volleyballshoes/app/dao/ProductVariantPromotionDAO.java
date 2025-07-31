package com.DuAn1.volleyballshoes.app.dao;

import com.DuAn1.volleyballshoes.app.entity.ProductVariant;
import com.DuAn1.volleyballshoes.app.entity.ProductVariantPromotion;
import com.DuAn1.volleyballshoes.app.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductVariantPromotionDAO extends JpaRepository<ProductVariantPromotion, Long> {
    
    List<ProductVariantPromotion> findByProductVariantId(Long productVariantId);
    
    List<ProductVariantPromotion> findByPromotionId(Long promotionId);
    
    @Query("SELECT pvp FROM ProductVariantPromotion pvp " +
           "JOIN pvp.promotion p " +
           "WHERE pvp.productVariant.id = :productVariantId AND " +
           "p.active = true AND " +
           "p.startDate <= CURRENT_TIMESTAMP AND " +
           "p.endDate >= CURRENT_TIMESTAMP")
    List<ProductVariantPromotion> findActivePromotionsForProductVariant(@Param("productVariantId") Long productVariantId);
    
    @Modifying
    @Query("DELETE FROM ProductVariantPromotion pvp WHERE pvp.promotion.id = :promotionId")
    void deleteByPromotionId(@Param("promotionId") Long promotionId);
    
    @Modifying
    @Query("DELETE FROM ProductVariantPromotion pvp WHERE pvp.promotion = :promotion")
    void deleteByPromotion(@Param("promotion") Promotion promotion);
    
    @Query("SELECT pvp.productVariant FROM ProductVariantPromotion pvp WHERE pvp.promotion.id = :promotionId")
    List<ProductVariant> findProductVariantsByPromotionId(@Param("promotionId") Long promotionId);
    
    boolean existsByProductVariantIdAndPromotionId(Long productVariantId, Long promotionId);
}
