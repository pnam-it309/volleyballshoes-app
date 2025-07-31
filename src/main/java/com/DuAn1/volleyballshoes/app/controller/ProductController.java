package com.DuAn1.volleyballshoes.app.controller;

import com.DuAn1.volleyballshoes.app.dao.ProductDAO;
import com.DuAn1.volleyballshoes.app.dao.impl.ProductDAOImpl;
import com.DuAn1.volleyballshoes.app.dto.request.ProductCreateRequest;
import com.DuAn1.volleyballshoes.app.dto.request.ProductUpdateRequest;
import com.DuAn1.volleyballshoes.app.dto.response.ProductResponse;
import com.DuAn1.volleyballshoes.app.entity.Product;
import java.util.List;
import java.util.stream.Collectors;

public class ProductController {
    
    private final ProductDAO productDAO;
    
    public ProductController() {
        this.productDAO = new ProductDAOImpl();
    }
    
    public ProductController(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }
    
    public ProductResponse createProduct(ProductCreateRequest request) {
        if (!request.isValid()) {
            throw new IllegalArgumentException("Invalid product data");
        }
        
        // Check if product code already exists
        if (productDAO.findByCode(request.getProductCode()) != null) {
            throw new IllegalArgumentException("Product code already exists");
        }
        
        Product product = Product.builder()
            .productCode(request.getProductCode())
            .productName(request.getProductName())
            .productDescription(request.getProductDescription())
            .brandId(request.getBrandId())
            .categoryId(request.getCategoryId())
            .build();
            
        Product createdProduct = productDAO.create(product);
        return ProductResponse.fromEntity(createdProduct);
    }
    
    public ProductResponse updateProduct(ProductUpdateRequest request) {
        if (!request.isValid()) {
            throw new IllegalArgumentException("Invalid product data");
        }
        
        Product existingProduct = productDAO.findById(request.getProductId());
        if (existingProduct == null) {
            throw new IllegalArgumentException("Product not found");
        }
        
        // Check if new product code conflicts with existing products
        Product productWithSameCode = productDAO.findByCode(request.getProductCode());
        if (productWithSameCode != null && productWithSameCode.getProductId() != request.getProductId()) {
            throw new IllegalArgumentException("Product code already exists");
        }
        
        Product product = Product.builder()
            .productId(request.getProductId())
            .productCode(request.getProductCode())
            .productName(request.getProductName())
            .productDescription(request.getProductDescription())
            .brandId(request.getBrandId())
            .categoryId(request.getCategoryId())
            .productCreateAt(existingProduct.getProductCreateAt())
            .build();
            
        productDAO.update(product);
        return ProductResponse.fromEntity(product);
    }
    
    public void deleteProduct(Integer productId) {
        if (productId == null || productId <= 0) {
            throw new IllegalArgumentException("Invalid product ID");
        }
        
        Product existingProduct = productDAO.findById(productId);
        if (existingProduct == null) {
            throw new IllegalArgumentException("Product not found");
        }
        
        productDAO.deleteById(productId);
    }
    
    public List<ProductResponse> getAllProducts() {
        return productDAO.findAll().stream()
            .map(ProductResponse::fromEntity)
            .collect(Collectors.toList());
    }
    
    public ProductResponse getProductById(Integer productId) {
        if (productId == null || productId <= 0) {
            throw new IllegalArgumentException("Invalid product ID");
        }
        
        Product product = productDAO.findById(productId);
        if (product == null) {
            throw new IllegalArgumentException("Product not found");
        }
        
        return ProductResponse.fromEntity(product);
    }
    
    public ProductResponse getProductByCode(String productCode) {
        if (productCode == null || productCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid product code");
        }
        
        Product product = productDAO.findByCode(productCode);
        if (product == null) {
            throw new IllegalArgumentException("Product not found");
        }
        
        return ProductResponse.fromEntity(product);
    }
    
    public List<ProductResponse> searchProductsByName(String productName) {
        if (productName == null || productName.trim().isEmpty()) {
            return getAllProducts();
        }
        
        return productDAO.findByName(productName).stream()
            .map(ProductResponse::fromEntity)
            .collect(Collectors.toList());
    }
    
    public List<ProductResponse> getProductsWithPagination(int page, int size) {
        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException("Invalid pagination parameters");
        }
        
        int offset = page * size;
        return productDAO.findWithPagination(offset, size).stream()
            .map(ProductResponse::fromEntity)
            .collect(Collectors.toList());
    }
    
    public long getTotalProductCount() {
        return productDAO.countAll();
    }
    
    public int getTotalPages(int pageSize) {
        if (pageSize <= 0) {
            throw new IllegalArgumentException("Invalid page size");
        }
        
        long totalCount = getTotalProductCount();
        return (int) Math.ceil((double) totalCount / pageSize);
    }
}
