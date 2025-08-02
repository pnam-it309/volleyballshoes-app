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
            throw new IllegalArgumentException("Dữ liệu sản phẩm không hợp lệ");
        }
        
        // Kiểm tra mã sản phẩm đã tồn tại chưa
        if (productDAO.findByCode(request.getProductCode()) != null) {
            throw new IllegalArgumentException("Mã sản phẩm đã tồn tại");
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
            throw new IllegalArgumentException("Dữ liệu sản phẩm không hợp lệ");
        }
        
        Product existingProduct = productDAO.findById(request.getProductId());
        if (existingProduct == null) {
            throw new IllegalArgumentException("Không tìm thấy sản phẩm");
        }
        
        // Kiểm tra mã sản phẩm mới có trùng với sản phẩm khác không
        Product productWithSameCode = productDAO.findByCode(request.getProductCode());
        if (productWithSameCode != null && productWithSameCode.getProductId() != request.getProductId()) {
            throw new IllegalArgumentException("Mã sản phẩm đã tồn tại");
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
            throw new IllegalArgumentException("ID sản phẩm không hợp lệ");
        }
        
        Product existingProduct = productDAO.findById(productId);
        if (existingProduct == null) {
            throw new IllegalArgumentException("Không tìm thấy sản phẩm");
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
            throw new IllegalArgumentException("ID sản phẩm không hợp lệ");
        }
        
        Product product = productDAO.findById(productId);
        if (product == null) {
            throw new IllegalArgumentException("Không tìm thấy sản phẩm");
        }
        
        return ProductResponse.fromEntity(product);
    }
    
    public ProductResponse getProductByCode(String productCode) {
        if (productCode == null || productCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã sản phẩm không hợp lệ");
        }
        
        Product product = productDAO.findByCode(productCode);
        if (product == null) {
            throw new IllegalArgumentException("Không tìm thấy sản phẩm");
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
            throw new IllegalArgumentException("Tham số phân trang không hợp lệ");
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
            throw new IllegalArgumentException("Kích thước trang không hợp lệ");
        }
        
        long totalCount = getTotalProductCount();
        return (int) Math.ceil((double) totalCount / pageSize);
    }
}
