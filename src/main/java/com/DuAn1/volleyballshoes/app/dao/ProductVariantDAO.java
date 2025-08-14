package com.DuAn1.volleyballshoes.app.dao;

import com.DuAn1.volleyballshoes.app.entity.ProductVariant;
import java.util.List;
import java.util.Optional;

public interface ProductVariantDAO extends CrudDAO<ProductVariant, Integer> {
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
