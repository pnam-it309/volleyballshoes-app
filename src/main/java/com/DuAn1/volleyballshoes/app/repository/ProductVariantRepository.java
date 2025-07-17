package com.DuAn1.volleyballshoes.app.repository;

import com.DuAn1.volleyballshoes.app.entity.ProductVariant;
import com.DuAn1.volleyballshoes.app.utils.XJdbc;
import com.DuAn1.volleyballshoes.app.utils.XQuery;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductVariantRepository {
    public void insert(ProductVariant variant) {
        String sql = "INSERT INTO ProductVariant (product_id, size_id, color_id, variant_sku, variant_orig_price, variant_img_url, stock) VALUES (?, ?, ?, ?, ?, ?, ?)";
        XJdbc.executeUpdate(sql,
            variant.getProductId(),
            variant.getSizeId(),
            variant.getColorId(),
            variant.getVariantSku(),
            variant.getVariantOrigPrice(),
            variant.getVariantImgUrl()
        );
    }
    public void update(ProductVariant variant) {
        String sql = "UPDATE ProductVariant SET product_id=?, size_id=?, color_id=?, variant_sku=?, variant_orig_price=?, variant_img_url=?, stock=? WHERE variant_id=?";
        XJdbc.executeUpdate(sql,
            variant.getProductId(),
            variant.getSizeId(),
            variant.getColorId(),
            variant.getVariantSku(),
            variant.getVariantOrigPrice(),
            variant.getVariantImgUrl(),
            variant.getVariantId()
        );
    }
    public void delete(int variantId) {
        String sql = "DELETE FROM ProductVariant WHERE variant_id=?";
        XJdbc.executeUpdate(sql, variantId);
    }
    public List<ProductVariant> findAll() {
        String sql = "SELECT * FROM ProductVariant";
        List<ProductVariant> list = new ArrayList<>();
        try (ResultSet rs = XJdbc.executeQuery(sql)) {
            while (rs.next()) {
                ProductVariant pv = new ProductVariant();
                pv.setVariantId(rs.getInt("variant_id"));
                pv.setProductId(rs.getInt("product_id"));
                pv.setSizeId(rs.getInt("size_id"));
                pv.setColorId(rs.getInt("color_id"));
                pv.setVariantSku(rs.getString("variant_sku"));
                pv.setVariantOrigPrice(rs.getDouble("variant_orig_price"));
                pv.setVariantImgUrl(rs.getString("variant_img_url"));
                list.add(pv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public List<ProductVariant> searchByKeyword(String keyword) {
        String sql = "SELECT * FROM ProductVariant WHERE variant_sku LIKE ? OR variant_id LIKE ?";
        List<ProductVariant> list = new ArrayList<>();
        try (ResultSet rs = XJdbc.executeQuery(sql, "%"+keyword+"%", "%"+keyword+"%")) {
            while (rs.next()) {
                ProductVariant pv = new ProductVariant();
                pv.setVariantId(rs.getInt("variant_id"));
                pv.setProductId(rs.getInt("product_id"));
                pv.setSizeId(rs.getInt("size_id"));
                pv.setColorId(rs.getInt("color_id"));
                pv.setVariantSku(rs.getString("variant_sku"));
                pv.setVariantOrigPrice(rs.getDouble("variant_orig_price"));
                pv.setVariantImgUrl(rs.getString("variant_img_url"));
                list.add(pv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
} 