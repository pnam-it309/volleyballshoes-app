package com.DuAn1.volleyballshoes.app.dao;

import com.DuAn1.volleyballshoes.app.entity.Brand;
import java.util.List;

public interface BrandDAO extends CrudDAO<Brand, Integer> {
    List<Brand> findAll();
}
