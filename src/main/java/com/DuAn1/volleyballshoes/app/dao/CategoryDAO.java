package com.DuAn1.volleyballshoes.app.dao;

import com.DuAn1.volleyballshoes.app.entity.Category;
import java.util.List;

public interface CategoryDAO extends CrudDAO<Category, Integer> {
    List<Category> findAll();
}
