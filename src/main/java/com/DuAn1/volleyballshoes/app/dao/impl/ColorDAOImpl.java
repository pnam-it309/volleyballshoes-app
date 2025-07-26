package com.DuAn1.volleyballshoes.app.dao.impl;

import com.DuAn1.volleyballshoes.app.dao.ColorDAO;
import com.DuAn1.volleyballshoes.app.entity.Color;
import com.DuAn1.volleyballshoes.app.utils.XJdbc;
import java.util.List;

public class ColorDAOImpl implements ColorDAO {

    private static final XJdbc.RowMapper<Color> COLOR_MAPPER = rs -> Color.builder()
            .color_id(rs.getInt("color_id"))
            .color_name(rs.getString("color_name"))
            .color_hex_code(rs.getString("color_hex_code"))
            .build();

    @Override
    public List<Color> findAll() {
        String sql = "SELECT * FROM Color";
        return XJdbc.query(sql, COLOR_MAPPER);
    }

    @Override
    public Color create(Color entity) {
        String sql = "INSERT INTO Color (color_id, color_name, color_hex_code) VALUES (?, ?, ?)";
        XJdbc.executeUpdate(sql, entity.getColor_id(), entity.getColor_name(), entity.getColor_hex_code());
        return entity;
    }

    @Override
    public void update(Color entity) {
        String sql = "UPDATE Color SET color_name=?, color_hex_code=? WHERE color_id=?";
        XJdbc.executeUpdate(sql, entity.getColor_name(), entity.getColor_hex_code(), entity.getColor_id());
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM Color WHERE color_id=?";
        XJdbc.executeUpdate(sql, id);
    }

    @Override
    public Color findById(Integer id) {
        String sql = "SELECT * FROM Color WHERE color_id=?";
        return XJdbc.queryForObject(sql, COLOR_MAPPER, id);
    }
}
