package com.DuAn1.volleyballshoes.app.controller;

import com.DuAn1.volleyballshoes.app.dao.ProductDAO;
import com.DuAn1.volleyballshoes.app.dao.impl.ProductDAOImpl;
import com.DuAn1.volleyballshoes.app.dto.request.*;
import com.DuAn1.volleyballshoes.app.dto.response.*;
import com.DuAn1.volleyballshoes.app.entity.Product;
import com.DuAn1.volleyballshoes.app.entity.ProductVariant;
import java.util.Collections;
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
        
        int offset = (page - 1) * size;
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
    
    /**
     * Tìm kiếm sản phẩm theo tên hoặc mã sản phẩm
     * @param searchText Từ khóa tìm kiếm
     * @return Danh sách sản phẩm phù hợp
     */
    public List<Product> searchProducts(String searchText) {
        if (searchText == null || searchText.trim().isEmpty()) {
            return productDAO.findAll();
        }
        
        // Tìm kiếm theo mã sản phẩm trước
        Product productByCode = productDAO.findByCode(searchText);
        if (productByCode != null) {
            return List.of(productByCode);
        }
        
        // Nếu không tìm thấy theo mã, tìm theo tên
        return productDAO.findByName("%" + searchText + "%");
    }
    
    /**
     * Lấy danh sách biến thể của sản phẩm
     * @param productId ID sản phẩm
     * @return Danh sách biến thể
     */
    public List<ProductVariant> getProductVariants(int productId) {
        // TODO: Implement this method to return product variants
        // You'll need to inject ProductVariantDAO and implement the logic
        return Collections.emptyList();
    }
}
