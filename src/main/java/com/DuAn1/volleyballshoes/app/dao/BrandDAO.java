package com.DuAn1.volleyballshoes.app.dao;

import com.DuAn1.volleyballshoes.app.entity.Brand;
import java.util.List;

public interface BrandDAO extends CrudDAO<Brand, Integer> {
    @Override
    Brand create(Brand brand);
    
    @Override
    Brand update(Brand brand);
    
    @Override
    boolean deleteById(Integer id);
    
    @Override
    Brand findById(Integer id);
    
    @Override
    List<Brand> findAll();
    
    Brand findByName(String name);
    
    List<Brand> search(String keyword);
    
    boolean existsByName(String name);
    
    Brand findByCode(String code);
}
