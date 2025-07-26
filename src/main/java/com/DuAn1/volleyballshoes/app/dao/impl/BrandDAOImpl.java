package com.DuAn1.volleyballshoes.app.dao.impl;

import com.DuAn1.volleyballshoes.app.dao.BrandDAO;
import com.DuAn1.volleyballshoes.app.entity.Brand;
import com.DuAn1.volleyballshoes.app.utils.XJdbc;
import java.sql.ResultSet;
import java.util.List;

public class BrandDAOImpl implements BrandDAO {

    private static final XJdbc.RowMapper<Brand> BRAND_MAPPER = rs -> Brand.builder()
            .brand_id(rs.getInt("brand_id"))
            .brand_name(rs.getString("brand_name"))
            .brand_origin(rs.getString("brand_origin"))
            .build();

    @Override
    public List<Brand> findAll() {
        String sql = "SELECT * FROM Brand";
        return XJdbc.query(sql, BRAND_MAPPER);
    }

    @Override
    public Brand create(Brand entity) {
        String sql = "INSERT INTO Brand (brand_id, brand_name, brand_origin) VALUES (?, ?, ?)";
        XJdbc.executeUpdate(sql, entity.getBrand_id(), entity.getBrand_name(), entity.getBrand_origin());
        return entity;
    }

    @Override
    public void update(Brand entity) {
        String sql = "UPDATE Brand SET brand_name=?, brand_origin=? WHERE brand_id=?";
        XJdbc.executeUpdate(sql, entity.getBrand_name(), entity.getBrand_origin(), entity.getBrand_id());
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM Brand WHERE brand_id=?";
        XJdbc.executeUpdate(sql, id);
    }

    @Override
    public Brand findById(Integer id) {
        String sql = "SELECT * FROM Brand WHERE brand_id=?";
        return XJdbc.queryForObject(sql, BRAND_MAPPER, id);
    }
}
