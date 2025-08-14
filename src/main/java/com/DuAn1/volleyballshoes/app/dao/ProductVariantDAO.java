package com.DuAn1.volleyballshoes.app.dao;

import com.DuAn1.volleyballshoes.app.entity.ProductVariant;
import java.util.List;
import java.util.Optional;

public interface ProductVariantDAO extends CrudDAO<ProductVariant, Integer> {
    /**
     * Tìm kiếm biến thể sản phẩm theo nhiều điều kiện (SKU, tên sản phẩm, giá)
     * @param sku SKU của biến thể (null hoặc rỗng nếu không lọc)
     * @param productName Tên sản phẩm (null hoặc rỗng nếu không lọc)
     * @param price Giá (null nếu không lọc)
     * @return Danh sách biến thể phù hợp
     */
    List<ProductVariant> searchVariants(String sku, String productName, java.math.BigDecimal price);
    List<ProductVariant> findAll();
    List<ProductVariant> findByProductId(int productId);
    List<ProductVariant> findWithPagination(int page, int pageSize, String filter);
    int count(String filter);
    boolean existsBySku(String sku);
    
    /**
     * Find a product variant by SKU
     * @param sku The SKU to search for
     * @return Optional containing the product variant if found
     */
    Optional<ProductVariant> findBySku(String sku);
    
    /**
     * Reduce the quantity of a product variant
     * @param variantId The ID of the variant to update
     * @param quantity The quantity to reduce by
     * @return true if the update was successful, false otherwise
     * @throws IllegalStateException if there's not enough stock
     */
    boolean reduceQuantity(int variantId, int quantity) throws IllegalStateException;
}
