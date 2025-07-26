package com.DuAn1.volleyballshoes.app.dao.impl;

import com.DuAn1.volleyballshoes.app.dao.CategoryDAO;
import com.DuAn1.volleyballshoes.app.entity.Category;
import com.DuAn1.volleyballshoes.app.utils.XJdbc;
import java.sql.SQLException;
import java.util.List;

public class CategoryDAOImpl implements CategoryDAO {

    private static final XJdbc.RowMapper<Category> CATEGORY_MAPPER = rs -> {
        try {
            return Category.builder()
                    .category_id(rs.getInt("category_id"))
                    .category_name(rs.getString("category_name"))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    };

    @Override
    public List<Category> findAll() {
        String sql = "SELECT * FROM Category";
        return XJdbc.query(sql, CATEGORY_MAPPER);
    }

    @Override
    public Category create(Category entity) {
        String sql = "INSERT INTO Category (category_id, category_name) VALUES (?, ?)";
        XJdbc.executeUpdate(sql, entity.getCategory_id(), entity.getCategory_name());
        return entity;
    }

    @Override
    public void update(Category entity) {
        String sql = "UPDATE Category SET category_name=? WHERE category_id=?";
        XJdbc.executeUpdate(sql, entity.getCategory_name(), entity.getCategory_id());
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM Category WHERE category_id=?";
        XJdbc.executeUpdate(sql, id);
    }

    @Override
    public Category findById(Integer id) {
        String sql = "SELECT * FROM Category WHERE category_id=?";
        return XJdbc.queryForObject(sql, CATEGORY_MAPPER, id);
    }
}
