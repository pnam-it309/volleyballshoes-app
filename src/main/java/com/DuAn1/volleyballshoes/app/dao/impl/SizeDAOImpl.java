package com.DuAn1.volleyballshoes.app.dao.impl;

import com.DuAn1.volleyballshoes.app.dao.SizeDAO;
import com.DuAn1.volleyballshoes.app.entity.Size;
import com.DuAn1.volleyballshoes.app.utils.XJdbc;
import java.util.*;
import java.sql.*;

public class SizeDAOImpl implements SizeDAO {

    private static final String TABLE_NAME = "Size";

    public List<Size> findAll() {
        String sql = "SELECT * FROM " + TABLE_NAME;
        return XJdbc.query(sql, this::mapResultSetToSize);
    }

    private Size mapResultSetToSize(ResultSet rs) throws SQLException {
        Size sz = new Size();
        sz.setSizeId(rs.getInt("size_id"));
        sz.setSizeCode(rs.getString("size_code"));
        sz.setSizeValue(rs.getString("size_value"));
        return sz;
    }

    public Size findByCode(String code) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE size_code = ?";
        try (ResultSet rs = XJdbc.executeQuery(sql, code)) {
            if (rs.next()) {
                return mapResultSetToSize(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteById(int id) {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE size_id = ?";
        return XJdbc.executeUpdate(sql, id) > 0;
    }

    @Override
    public boolean deleteById(Integer id) {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE size_id = ?";
        return XJdbc.executeUpdate(sql, id) > 0;
    }

    @Override
    public Size findById(Integer id) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE size_id = ?";
        List<Size> list = XJdbc.query(sql, this::mapResultSetToSize, id);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public Size create(Size entity) {
        String sql = "INSERT INTO " + TABLE_NAME + " (size_code, size_value) VALUES (?, ?)";
        XJdbc.executeUpdate(sql, entity.getSizeCode(), entity.getSizeValue());
        // Get the last inserted ID and return the complete entity
        return findByCode(entity.getSizeCode());
    }

    @Override
    public Size update(Size entity) {
        String sql = "UPDATE " + TABLE_NAME + " SET size_code = ?, size_value = ? WHERE size_id = ?";
        XJdbc.executeUpdate(sql, entity.getSizeCode(), entity.getSizeValue(), entity.getSizeId());
        return findById(entity.getSizeId());
    }
}
