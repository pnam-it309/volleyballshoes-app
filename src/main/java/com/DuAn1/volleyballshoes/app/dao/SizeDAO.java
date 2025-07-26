package com.DuAn1.volleyballshoes.app.dao;

import com.DuAn1.volleyballshoes.app.entity.Size;
import java.util.List;

public interface SizeDAO extends CrudDAO<Size, Integer> {
    List<Size> findAll();
}
