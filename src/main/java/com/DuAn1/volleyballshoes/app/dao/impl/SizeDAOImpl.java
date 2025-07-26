package com.DuAn1.volleyballshoes.app.dao.impl;

import com.DuAn1.volleyballshoes.app.dao.SizeDAO;
import com.DuAn1.volleyballshoes.app.entity.Size;
import com.DuAn1.volleyballshoes.app.utils.XJdbc;
import java.util.List;

public class SizeDAOImpl implements SizeDAO {

    private static final XJdbc.RowMapper<Size> SIZE_MAPPER = rs -> Size.builder()
            .size_id(rs.getInt("size_id"))
            .size_value(rs.getString("size_value"))
            .size_desc(rs.getString("size_desc"))
            .build();

    @Override
    public List<Size> findAll() {
        String sql = "SELECT * FROM Size";
        return XJdbc.query(sql, SIZE_MAPPER);
    }

    @Override
    public Size create(Size entity) {
        String sql = "INSERT INTO Size (size_id, size_value, size_desc) VALUES (?, ?, ?)";
        XJdbc.executeUpdate(sql, entity.getSize_id(), entity.getSize_value(), entity.getSize_desc());
        return entity;
    }

    @Override
    public void update(Size entity) {
        String sql = "UPDATE Size SET size_value=?, size_desc=? WHERE size_id=?";
        XJdbc.executeUpdate(sql, entity.getSize_value(), entity.getSize_desc(), entity.getSize_id());
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM Size WHERE size_id=?";
        XJdbc.executeUpdate(sql, id);
    }

    @Override
    public Size findById(Integer id) {
        String sql = "SELECT * FROM Size WHERE size_id=?";
        return XJdbc.queryForObject(sql, SIZE_MAPPER, id);
    }
}
