package com.DuAn1.volleyballshoes.app.dao;

import com.DuAn1.volleyballshoes.app.entity.ProductVariant;
import java.util.List;
import java.util.Optional;

public interface ProductVariantDAO extends CrudDAO<ProductVariant, Integer> {

    List<ProductVariant> searchVariants(String sku, String productName, java.math.BigDecimal price);

    List<ProductVariant> findAll();

    List<ProductVariant> findByProductId(int productId);

    List<ProductVariant> findWithPagination(int page, int pageSize, String filter);

    int count(String filter);

    boolean existsBySku(String sku);

    ProductVariant findBySku(String sku);

    boolean reduceQuantity(int variantId, int quantity) throws IllegalStateException;
}
