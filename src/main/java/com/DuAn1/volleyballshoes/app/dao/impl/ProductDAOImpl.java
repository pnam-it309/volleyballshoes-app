package com.DuAn1.volleyballshoes.app.dao.impl;

import com.DuAn1.volleyballshoes.app.dao.ProductDAO;
import com.DuAn1.volleyballshoes.app.entity.Product;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductDAOImpl implements ProductDAO {
    
    private List<Product> products;
    private int nextId = 1;
    
    public ProductDAOImpl() {
        this.products = new ArrayList<>();
        initializeData();
    }
    
    private void initializeData() {
        // Sample data initialization
        products.add(Product.builder()
            .productId(nextId++)
            .productCode("SP001")
            .productName("Nike Air Max 270")
            .productDescription("Giày thể thao cao cấp với công nghệ Air Max")
            .brandId(1)
            .categoryId(1)
            .productCreateAt(LocalDateTime.now())
            .build());
            
        products.add(Product.builder()
            .productId(nextId++)
            .productCode("SP002")
            .productName("Adidas Ultraboost 22")
            .productDescription("Giày chạy bộ chuyên nghiệp")
            .brandId(2)
            .categoryId(1)
            .productCreateAt(LocalDateTime.now())
            .build());
            
        products.add(Product.builder()
            .productId(nextId++)
            .productCode("SP003")
            .productName("Puma RS-X")
            .productDescription("Giày lifestyle thời trang")
            .brandId(3)
            .categoryId(2)
            .productCreateAt(LocalDateTime.now())
            .build());
    }
    
    @Override
    public Product create(Product product) {
        product.setProductId(nextId++);
        product.setProductCreateAt(LocalDateTime.now());
        products.add(product);
        return product;
    }
    
    @Override
    public void update(Product product) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProductId() == product.getProductId()) {
                product.setProductUpdatedAt(LocalDateTime.now());
                products.set(i, product);
                break;
            }
        }
    }
    
    @Override
    public void deleteById(Integer id) {
        products.removeIf(product -> product.getProductId() == id);
    }
    
    @Override
    public List<Product> findAll() {
        return new ArrayList<>(products);
    }
    
    @Override
    public Product findById(Integer id) {
        return products.stream()
            .filter(product -> product.getProductId() == id)
            .findFirst()
            .orElse(null);
    }
    
    @Override
    public Product findByCode(String productCode) {
        return products.stream()
            .filter(product -> product.getProductCode().equals(productCode))
            .findFirst()
            .orElse(null);
    }
    
    @Override
    public List<Product> findByName(String productName) {
        return products.stream()
            .filter(product -> product.getProductName().toLowerCase()
                .contains(productName.toLowerCase()))
            .collect(Collectors.toList());
    }
    
    @Override
    public List<Product> findByBrandId(Integer brandId) {
        return products.stream()
            .filter(product -> product.getBrandId() == brandId)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<Product> findByCategoryId(Integer categoryId) {
        return products.stream()
            .filter(product -> product.getCategoryId() == categoryId)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<Product> findWithPagination(int offset, int limit) {
        return products.stream()
            .skip(offset)
            .limit(limit)
            .collect(Collectors.toList());
    }
    
    @Override
    public long countAll() {
        return products.size();
    }
}
