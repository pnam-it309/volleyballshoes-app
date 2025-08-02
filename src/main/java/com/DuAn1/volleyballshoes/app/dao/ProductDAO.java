package com.DuAn1.volleyballshoes.app.dao;

import com.DuAn1.volleyballshoes.app.entity.Product;
import java.util.List;

public interface ProductDAO {
    
    Product create(Product product);
    
    void update(Product product);
    
    void deleteById(Integer id);
    
    List<Product> findAll();
    
    Product findById(Integer id);
    
    Product findByCode(String productCode);
    
    List<Product> findByName(String productName);
    
    List<Product> findByBrandId(Integer brandId);
    
    List<Product> findByCategoryId(Integer categoryId);
    
    List<Product> findWithPagination(int offset, int limit);
    
    long countAll();
}
