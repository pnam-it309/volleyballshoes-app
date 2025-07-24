package com.DuAn1.volleyballshoes.app.repository;

import com.DuAn1.volleyballshoes.app.entity.ProductVariant;
import com.DuAn1.volleyballshoes.app.utils.XQuery;
import com.DuAn1.volleyballshoes.app.utils.XJdbc;
import java.util.List;

public class ProductVariantRepository {

    public void insert(ProductVariant variant) {
        String sql = "INSERT INTO ProductVariant (product_id, size_id, color_id, sole_id, variant_sku, variant_orig_price, variant_img_url) VALUES (?, ?, ?, ?, ?, ?, ?)";
        XJdbc.executeUpdate(sql,
                variant.product_id,
                variant.size_id,
                variant.color_id,
                variant.sole_id,
                variant.variant_sku,
                variant.variant_orig_price,
                variant.variant_img_url
        );
    }

    public void update(ProductVariant variant) {
        String sql = "UPDATE ProductVariant SET size_id=?, color_id=?, sole_id=?, variant_sku=?, variant_orig_price=?, variant_img_url=? WHERE variant_id=?";
        XJdbc.executeUpdate(sql,
                variant.size_id,
                variant.color_id,
                variant.sole_id,
                variant.variant_sku,
                variant.variant_orig_price,
                variant.variant_img_url,
                variant.variant_id
        );
    }

    public void delete(int variantId) {
        String sql = "DELETE FROM ProductVariant WHERE variant_id=?";
        XJdbc.executeUpdate(sql, variantId);
    }

    public List<ProductVariant> selectAll() {
        String sql = "SELECT * FROM ProductVariant";
        return XQuery.getBeanList(ProductVariant.class, sql);
    }

    public List<ProductVariant> findByProductId(int productId) {
        String sql = "SELECT * FROM ProductVariant WHERE product_id=?";
        return XQuery.getBeanList(ProductVariant.class, sql, productId);
    }
}
