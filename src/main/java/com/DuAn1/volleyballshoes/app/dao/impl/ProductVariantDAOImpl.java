package com.DuAn1.volleyballshoes.app.dao.impl;

import com.DuAn1.volleyballshoes.app.dao.ProductVariantDAO;
import com.DuAn1.volleyballshoes.app.entity.ProductVariant;
import com.DuAn1.volleyballshoes.app.utils.XJdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductVariantDAOImpl implements ProductVariantDAO {

    private ProductVariant mapResultSetToProductVariant(ResultSet rs) throws SQLException {
        ProductVariant variant = new ProductVariant();
        variant.setVariantId(rs.getInt("variant_id"));
        variant.setProductId(rs.getInt("product_id"));
        variant.setSizeId(rs.getInt("size_id"));
        variant.setColorId(rs.getInt("color_id"));
        variant.setSoleId(rs.getInt("sole_id"));
        variant.setVariantSku(rs.getString("variant_sku"));
        variant.setVariantOrigPrice(rs.getBigDecimal("variant_orig_price"));
        variant.setVariantImgUrl(rs.getString("variant_img_url"));
        return variant;
    }

    @Override
    public List<ProductVariant> findByProductId(int productId) {
        String sql = "SELECT * FROM ProductVariant WHERE product_id = ?";
        return XJdbc.query(sql, this::mapResultSetToProductVariant, productId);
    }

    @Override
    public ProductVariant create(ProductVariant entity) {
        String sql = "INSERT INTO ProductVariant (product_id, size_id, color_id, sole_id, variant_sku, variant_orig_price, variant_img_url) "
                + "OUTPUT INSERTED.* "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        return XJdbc.queryForObject(sql, this::mapResultSetToProductVariant,
                entity.getProductId(),
                entity.getSizeId(),
                entity.getColorId(),
                entity.getSoleId(),
                entity.getVariantSku(),
                entity.getVariantOrigPrice(),
                entity.getVariantImgUrl()
        );
    }

    @Override
    public ProductVariant update(ProductVariant entity) {
        String sql = "UPDATE ProductVariant SET product_id = ?, size_id = ?, color_id = ?, "
                + "sole_id = ?, variant_sku = ?, variant_orig_price = ?, variant_img_url = ? "
                + "OUTPUT INSERTED.* WHERE variant_id = ?";

        return XJdbc.queryForObject(sql, this::mapResultSetToProductVariant,
                entity.getProductId(),
                entity.getSizeId(),
                entity.getColorId(),
                entity.getSoleId(),
                entity.getVariantSku(),
                entity.getVariantOrigPrice(),
                entity.getVariantImgUrl(),
                entity.getVariantId()
        );
    }

    @Override
    public boolean deleteById(Integer id) {
        String sql = "DELETE FROM ProductVariant WHERE variant_id = ?";
        return XJdbc.executeUpdate(sql, id) > 0;
    }

    @Override
    public List<ProductVariant> findAll() {
        String sql = "SELECT * FROM ProductVariant";
        return XJdbc.query(sql, this::mapResultSetToProductVariant);
    }

    @Override
    public List<ProductVariant> findWithPagination(int page, int pageSize, String filter) {
        int offset = (page - 1) * pageSize;
        String sql = "SELECT * FROM ProductVariant "
                + "WHERE variant_sku LIKE ? OR variant_img_url LIKE ? "
                + "ORDER BY variant_id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        String searchPattern = "%" + (filter != null ? filter : "") + "%";
        return XJdbc.query(sql, this::mapResultSetToProductVariant,
                searchPattern, searchPattern, offset, pageSize);
    }

    @Override
    public int count(String filter) {
//        String sql = "SELECT COUNT(*) FROM ProductVariant "
//                   + "WHERE variant_sku LIKE ? OR variant_img_url LIKE ?";
//        
//        String searchPattern = "%" + (filter != null ? filter : "") + "%";
//        Long count = XJdbc.getValue(sql, searchPattern, searchPattern);
//        return count != null ? count.intValue() : 0;
        return 0;
    }

    @Override
    public ProductVariant findById(Integer id) {
        String sql = "SELECT * FROM ProductVariant WHERE variant_id = ?";
        List<ProductVariant> list = XJdbc.query(sql, this::mapResultSetToProductVariant, id);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public boolean existsBySku(String sku) {
//        String sql = "SELECT COUNT(*) FROM ProductVariant WHERE variant_sku = ?";
//        Integer count = XJdbc.getValue(sql, sku);
//        return count != null && count > 0;
        return false;
    }
}
