package com.DuAn1.volleyballshoes.app.dao.impl;

import com.DuAn1.volleyballshoes.app.dao.ProductVariantPromotionDAO;
import com.DuAn1.volleyballshoes.app.entity.ProductVariant;
import com.DuAn1.volleyballshoes.app.entity.ProductVariantPromotion;
import com.DuAn1.volleyballshoes.app.utils.XJdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductVariantPromotionDAOImpl implements ProductVariantPromotionDAO {
    
    private ProductVariantPromotion mapResultSetToProductVariantPromotion(ResultSet rs) throws SQLException {
        ProductVariantPromotion pvp = new ProductVariantPromotion();
        pvp.setProductVariantId(rs.getInt("productVariantId"));
        pvp.setPromotionId(rs.getInt("promotionId"));
        pvp.setActive(rs.getBoolean("isActive"));
        pvp.setDiscountedPrice(rs.getBigDecimal("discountedPrice"));
        return pvp;
    }
    
    private ProductVariant mapResultSetToProductVariant(ResultSet rs) throws SQLException {
        ProductVariant pv = new ProductVariant();
        pv.setVariantId(rs.getInt("variantId"));
        pv.setProductId(rs.getInt("productId"));
        pv.setVariantSku(rs.getString("variantSku"));
        pv.setVariantOrigPrice(rs.getBigDecimal("variantOrigPrice"));
        pv.setVariantImgUrl(rs.getString("variantImgUrl"));
        pv.setSizeId(rs.getInt("sizeId"));
        pv.setColorId(rs.getInt("colorId"));
        pv.setSoleId(rs.getInt("soleId"));
        return pv;
    }

    @Override
    public List<ProductVariantPromotion> findByProductVariantId(Long productVariantId) {
        String sql = "SELECT * FROM ProductVariantPromotion WHERE productVariantId = ?";
        return XJdbc.query(sql, this::mapResultSetToProductVariantPromotion, productVariantId);
    }

    @Override
    public List<ProductVariantPromotion> findByPromotionId(Long promotionId) {
        String sql = "SELECT * FROM ProductVariantPromotion WHERE promotionId = ?";
        return XJdbc.query(sql, this::mapResultSetToProductVariantPromotion, promotionId);
    }

    @Override
    public List<ProductVariantPromotion> findActivePromotionsForProductVariant() {
        String sql = "SELECT pvp.* FROM ProductVariantPromotion pvp "
                   + "JOIN Promotion p ON pvp.promotionId = p.promotionId "
                   + "WHERE p.promoStartDate <= GETDATE() AND p.promoEndDate >= GETDATE() AND pvp.isActive = 1";
        return XJdbc.query(sql, this::mapResultSetToProductVariantPromotion);
    }

    @Override
    public void deleteByPromotionId(int promotionId) {
        String sql = "DELETE FROM ProductVariantPromotion WHERE promotionId = ?";
        XJdbc.executeUpdate(sql, promotionId);
    }

    @Override
    public void deleteByPromotion(ProductVariantPromotion promotion) {
        if (promotion != null) {
            deleteByPromotionId(promotion.getPromotionId());
        }
    }

    @Override
    public List<ProductVariant> findProductVariantsByPromotionId() {
        String sql = "SELECT pv.* FROM ProductVariant pv "
                   + "JOIN ProductVariantPromotion pvp ON pv.variantId = pvp.productVariantId "
                   + "WHERE pvp.promotionId = ?";
        return XJdbc.query(sql, this::mapResultSetToProductVariant);
    }

    @Override
    public boolean existsByProductVariantIdAndPromotionId(Long productVariantId, Long promotionId) {
        String sql = "SELECT COUNT(*) FROM ProductVariantPromotion "
                   + "WHERE productVariantId = ? AND promotionId = ?";
        Integer count = XJdbc.getValue(sql, productVariantId, promotionId);
        return count != null && count > 0;
    }
}
