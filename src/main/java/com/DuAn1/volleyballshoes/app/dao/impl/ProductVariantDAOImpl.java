package com.DuAn1.volleyballshoes.app.dao.impl;

import com.DuAn1.volleyballshoes.app.dao.ProductVariantDAO;
import com.DuAn1.volleyballshoes.app.entity.ProductVariant;
import com.DuAn1.volleyballshoes.app.utils.XJdbc;
import java.util.List;

public class ProductVariantDAOImpl implements ProductVariantDAO {
    private static final XJdbc.RowMapper<ProductVariant> PRODUCT_VARIANT_MAPPER = rs -> ProductVariant.builder()
        .variant_id(rs.getInt("variant_id"))
        .product_id(rs.getInt("product_id"))
        .size_id(rs.getInt("size_id"))
        .color_id(rs.getInt("color_id"))
        .sole_id(rs.getInt("sole_id"))
        .variant_sku(rs.getString("variant_sku"))
        .variant_orig_price(rs.getDouble("variant_orig_price"))
        .variant_img_url(rs.getString("variant_img_url"))
        .build();

    @Override
    public ProductVariant create(ProductVariant entity) {
        String sql = "INSERT INTO ProductVariant (product_id, size_id, color_id, sole_id, variant_sku, variant_orig_price, variant_img_url) VALUES (?, ?, ?, ?, ?, ?, ?)";
        XJdbc.executeUpdate(sql,
            entity.product_id,
            entity.size_id,
            entity.color_id,
            entity.sole_id,
            entity.variant_sku,
            entity.variant_orig_price,
            entity.variant_img_url
        );
        return entity;
    }

    @Override
    public void update(ProductVariant entity) {
        String sql = "UPDATE ProductVariant SET size_id=?, color_id=?, sole_id=?, variant_sku=?, variant_orig_price=?, variant_img_url=? WHERE variant_id=?";
        XJdbc.executeUpdate(sql,
            entity.size_id,
            entity.color_id,
            entity.sole_id,
            entity.variant_sku,
            entity.variant_orig_price,
            entity.variant_img_url,
            entity.variant_id
        );
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM ProductVariant WHERE variant_id=?";
        XJdbc.executeUpdate(sql, id);
    }

    @Override
    public List<ProductVariant> findByProductId(int productId) {
        String sql = "SELECT * FROM ProductVariant WHERE product_id=?";
        return XJdbc.query(sql, PRODUCT_VARIANT_MAPPER, productId);
    }

    @Override
    public ProductVariant findById(Integer id) {
        String sql = "SELECT * FROM ProductVariant WHERE variant_id=?";
        return XJdbc.queryForObject(sql, PRODUCT_VARIANT_MAPPER, id);
    }

    @Override
    public List<ProductVariant> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
