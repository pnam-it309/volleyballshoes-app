/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DuAn1.volleyballshoes.app.dao.impl;

import com.DuAn1.volleyballshoes.app.dao.StaffDAO;
import com.DuAn1.volleyballshoes.app.entity.Staff;
import com.DuAn1.volleyballshoes.app.utils.XJdbc;
import java.util.List;

/**
 *
 * @author letha
 */
public class StaffDAOImpl implements StaffDAO {   
     private static final XJdbc.RowMapper<Staff> Staff_MAPPER = rs -> Staff.builder()
            .staff_id(rs.getInt("staff_id"))
            .staff_username(rs.getString("staff_username"))
            .staff_password(rs.getString("staff_password"))
            .staff_full_name(rs.getString("staff_full_name"))
            .staff_sdt(rs.getString("staff_sdt"))
            .staff_role(rs.getInt("staff_role"))
            .build();

    @Override
    public List<Staff> findAll() {
        String sql = "SELECT * FROM Staff";
        return XJdbc.query(sql, Staff_MAPPER);
    }

    @Override
    public Staff create(Staff entity) {
        String sql = "INSERT INTO Staff (staff_id, staff_username, staff_password, staff_full_name, staff_role, staff_sdt) VALUES (?, ?, ?, ?, ?, ?)";
        XJdbc.executeUpdate(sql,
                entity.getStaff_id(),
                entity.getStaff_username(),
                entity.getStaff_password(),
                entity.getStaff_full_name(),
                entity.getStaff_role(),
                entity.getStaff_sdt());
        return entity;
    }

    @Override
    public void update(Staff entity) {
        String sql = "UPDATE Staff SET staff_username=?, staff_password=?, staff_full_name=?, staff_role=?, staff_sdt=? WHERE staff_id=?";
        XJdbc.executeUpdate(sql,
                entity.getStaff_username(),
                entity.getStaff_password(),
                entity.getStaff_full_name(),
                entity.getStaff_role(),
                entity.getStaff_sdt(),
                entity.getStaff_id());
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM Staff WHERE staff_id=?";
        XJdbc.executeUpdate(sql, id);
    }

    @Override
    public Staff findById(Integer id) {
        String sql = "SELECT * FROM Staff WHERE staff_id=?";
        return XJdbc.queryForObject(sql, Staff_MAPPER, id);
    }
}
    