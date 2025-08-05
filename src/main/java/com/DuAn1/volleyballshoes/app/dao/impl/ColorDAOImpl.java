package com.DuAn1.volleyballshoes.app.dao.impl;

import com.DuAn1.volleyballshoes.app.dao.ColorDAO;
import com.DuAn1.volleyballshoes.app.entity.Color;
import com.DuAn1.volleyballshoes.app.utils.XJdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ColorDAOImpl implements ColorDAO {

    private static final String TABLE_NAME = "Color";

    @Override
    public Color create(Color entity) {
        String sql = "INSERT INTO " + TABLE_NAME + " (color_name, color_hex_code) VALUES (?, ?)";
        XJdbc.executeUpdate(sql, entity.getColorName(), entity.getColorHexCode());
        return entity;
    }

    @Override
    public boolean deleteById(Integer id) {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE color_id = ?";
        return XJdbc.executeUpdate(sql, id) > 0;
    }

    @Override
    public Color findById(Integer id) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE color_id = ?";
        List<Color> list = XJdbc.query(sql, this::mapResultSetToColor, id);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public List<Color> findAll() {
        String sql = "SELECT * FROM " + TABLE_NAME;
        return XJdbc.query(sql, this::mapResultSetToColor);
    }

    private Color mapResultSetToColor(ResultSet rs) throws SQLException {
        return Color.builder()
                .colorId(rs.getInt("color_id"))
                .colorName(rs.getString("color_name"))
                .colorHexCode(rs.getString("color_hex_code"))
                .build();
    }

    @Override
    public Color update(Color entity) {
        String sql = "UPDATE " + TABLE_NAME + " SET color_name = ?, color_hex_code = ? WHERE color_id = ?";
        XJdbc.executeUpdate(sql, entity.getColorName(), entity.getColorHexCode(), entity.getColorId());
        return findById(entity.getColorId());
    }

    @Override
    public Color findByCode(String code) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE color_hex_code = ?";
        List<Color> list = XJdbc.query(sql, this::mapResultSetToColor, code);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public void deleteByCode(String code) {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE color_hex_code = ?";
        XJdbc.executeUpdate(sql, code) ;
    }
}
