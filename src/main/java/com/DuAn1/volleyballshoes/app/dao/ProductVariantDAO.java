package com.DuAn1.volleyballshoes.app.dao;

import com.DuAn1.volleyballshoes.app.entity.ProductVariant;
import java.util.List;

public interface ProductVariantDAO extends CrudDAO<ProductVariant, Integer> {
    List<ProductVariant> findAll();
    List<ProductVariant> findByProductId(int productId);
}
