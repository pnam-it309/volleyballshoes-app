package com.DuAn1.volleyballshoes.app.controller;

import com.DuAn1.volleyballshoes.app.dto.request.ProductRequest;
import com.DuAn1.volleyballshoes.app.dto.response.ProductResponse;
import com.DuAn1.volleyballshoes.app.entity.Product;
import com.DuAn1.volleyballshoes.app.service.ProductService;
import com.DuAn1.volleyballshoes.app.service.impl.ProductServiceImpl;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ProductController {
    private final ProductService productService = new ProductServiceImpl();

    public void create(ProductRequest req) {
        Product product = Product.builder()
                .brand_id(req.brand_id)
                .category_id(req.category_id)
                .product_name(req.product_name)
                .product_desc(req.product_desc)
                .product_create_at(LocalDateTime.now())
                .product_updated_at(LocalDateTime.now())
                .build();
        productService.create(product);
    }

    public void update(int id, ProductRequest req) {
        Product product = productService.findById(id);
        if (product != null) {
            product.brand_id = req.brand_id;
            product.category_id = req.category_id;
            product.product_name = req.product_name;
            product.product_desc = req.product_desc;
            product.product_updated_at = LocalDateTime.now();
            productService.update(product);
        }
    }

    public void delete(int id) {
        productService.deleteById(id);
    }

    public List<ProductResponse> findAll() {
        return productService.findAll().stream().map(p -> ProductResponse.builder()
                .productId(p.product_id)
                .brandId(p.brand_id)
                .categoryId(p.category_id)
                .productName(p.product_name)
                .productDesc(p.product_desc)
                .productCreateAt(p.product_create_at)
                .productUpdatedAt(p.product_updated_at)
                .build()).collect(Collectors.toList());
    }

    public ProductResponse findById(int id) {
        Product p = productService.findById(id);
        if (p == null) return null;
        return ProductResponse.builder()
                .productId(p.product_id)
                .brandId(p.brand_id)
                .categoryId(p.category_id)
                .productName(p.product_name)
                .productDesc(p.product_desc)
                .productCreateAt(p.product_create_at)
                .productUpdatedAt(p.product_updated_at)
                .build();
    }
} 