package com.DuAn1.volleyballshoes.app.service.impl;

import com.DuAn1.volleyballshoes.app.entity.Product;
import com.DuAn1.volleyballshoes.app.repository.ProductRepository;
import com.DuAn1.volleyballshoes.app.service.ProductService;
import java.util.List;

public class ProductServiceImpl implements ProductService {
    private final ProductRepository repo = new ProductRepository();
    @Override
    public Product create(Product entity) {
        repo.insert(entity);
        return entity;
    }
    @Override
    public void update(Product entity) {
        repo.update(entity);
    }
    @Override
    public void deleteById(Integer id) {
        repo.delete(id);
    }
    @Override
    public List<Product> findAll() {
        return repo.selectAll();
    }
    @Override
    public Product findById(Integer id) {
        return repo.selectById(id);
    }
} 