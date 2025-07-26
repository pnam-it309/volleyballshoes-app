package com.DuAn1.volleyballshoes.app.dao;

import com.DuAn1.volleyballshoes.app.entity.SoleType;
import java.util.List;

public interface SoleTypeDAO extends CrudDAO<SoleType, Integer> {
    List<SoleType> findAll();
}
