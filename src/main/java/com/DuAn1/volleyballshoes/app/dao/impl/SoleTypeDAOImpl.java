package com.DuAn1.volleyballshoes.app.dao.impl;

import com.DuAn1.volleyballshoes.app.dao.SoleTypeDAO;
import com.DuAn1.volleyballshoes.app.entity.SoleType;
import com.DuAn1.volleyballshoes.app.utils.XJdbc;
import java.sql.ResultSet;
import java.util.List;

public class SoleTypeDAOImpl implements SoleTypeDAO {

    private static final XJdbc.RowMapper<SoleType> SOLE_TYPE_MAPPER = rs -> SoleType.builder()
            .sole_id(rs.getInt("sole_id"))
            .sole_name(rs.getString("sole_name"))
            .build();

    @Override
    public List<SoleType> findAll() {
        String sql = "SELECT * FROM SoleType";
        return XJdbc.query(sql, SOLE_TYPE_MAPPER);
    }

    @Override
    public SoleType create(SoleType entity) {
        String sql = "INSERT INTO SoleType (sole_id, sole_name) VALUES (?, ?)";
        XJdbc.executeUpdate(sql, entity.getSole_id(), entity.getSole_name());
        return entity;
    }

    @Override
    public void update(SoleType entity) {
        String sql = "UPDATE SoleType SET sole_name=? WHERE sole_id=?";
        XJdbc.executeUpdate(sql, entity.getSole_name(), entity.getSole_id());
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM SoleType WHERE sole_id=?";
        XJdbc.executeUpdate(sql, id);
    }

    @Override
    public SoleType findById(Integer id) {
        String sql = "SELECT * FROM SoleType WHERE sole_id=?";
        return XJdbc.queryForObject(sql, SOLE_TYPE_MAPPER, id);
    }
}
