package com.DuAn1.volleyballshoes.app.dao.impl;

import com.DuAn1.volleyballshoes.app.entity.SoleType;
import com.DuAn1.volleyballshoes.app.utils.XJdbc;
import java.util.*;
import java.sql.*;

public class SoleTypeDAOImpl {
    private static final String TABLE_NAME = "SoleType";

    public int create(SoleType entity) {
        String sql = "INSERT INTO " + TABLE_NAME + " (sole_code, sole_name) VALUES (?, ?)";
        return XJdbc.executeUpdate(sql, entity.getSoleCode(), entity.getSoleName());
    }

    public List<SoleType> findAll() {
        String sql = "SELECT * FROM " + TABLE_NAME;
        return XJdbc.query(sql, this::mapResultSetToSoleType);
    }

    private SoleType mapResultSetToSoleType(ResultSet rs) throws SQLException {
        SoleType st = new SoleType();
        st.setSoleId(rs.getInt("sole_id"));
        st.setSoleCode(rs.getString("sole_code"));
        st.setSoleName(rs.getString("sole_name"));
        return st;
    }
    
    public SoleType findByCode(String code) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE sole_code = ?";
        try (ResultSet rs = XJdbc.executeQuery(sql, code)) {
            if (rs.next()) {
                return mapResultSetToSoleType(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean deleteById(int id) {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE sole_id = ?";
        return XJdbc.executeUpdate(sql, id) > 0;
    }
    
    public boolean update(SoleType entity) {
        String sql = "UPDATE " + TABLE_NAME + " SET sole_code = ?, sole_name = ? WHERE sole_id = ?";
        return XJdbc.executeUpdate(sql, entity.getSoleCode(), entity.getSoleName(), entity.getSoleId()) > 0;
    }
}
