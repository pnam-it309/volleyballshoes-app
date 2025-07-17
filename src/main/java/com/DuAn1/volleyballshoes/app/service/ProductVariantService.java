package com.DuAn1.volleyballshoes.app.service;

import com.DuAn1.volleyballshoes.app.dto.request.ProductVariantRequest;
import com.DuAn1.volleyballshoes.app.dto.response.ProductVariantResponse;
import java.util.List;

public interface ProductVariantService {
    boolean add(ProductVariantRequest request);
    boolean update(ProductVariantRequest request);
    boolean delete(int variantId);
    List<ProductVariantResponse> findAll();
    List<ProductVariantResponse> search(String keyword);
} 