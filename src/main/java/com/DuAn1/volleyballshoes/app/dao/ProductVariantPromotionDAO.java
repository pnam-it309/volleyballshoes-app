package com.DuAn1.volleyballshoes.app.dao;

import com.DuAn1.volleyballshoes.app.entity.ProductVariant;
import com.DuAn1.volleyballshoes.app.entity.ProductVariantPromotion;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductVariantPromotionDAO {

    List<ProductVariantPromotion> findByProductVariantId(Long productVariantId);

    List<ProductVariantPromotion> findByPromotionId(Long promotionId);

    List<ProductVariantPromotion> findActivePromotionsForProductVariant();

    void deleteByPromotionId(int promotionId);

    void deleteByPromotion(ProductVariantPromotion promotion);

    List<ProductVariant> findProductVariantsByPromotionId();

    boolean existsByProductVariantIdAndPromotionId(Long productVariantId, Long promotionId);
}
