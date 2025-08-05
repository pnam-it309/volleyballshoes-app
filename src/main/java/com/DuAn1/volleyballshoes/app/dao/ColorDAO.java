package com.DuAn1.volleyballshoes.app.dao;

import com.DuAn1.volleyballshoes.app.entity.Color;
import java.util.List;

public interface ColorDAO extends CrudDAO<Color, Integer> {
    List<Color> findAll();
    Color findByCode(String code);
    void deleteByCode(String code);
}
