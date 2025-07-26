package com.DuAn1.volleyballshoes.app.dao.impl;

import com.DuAn1.volleyballshoes.app.dao.CrudDAO;
import com.DuAn1.volleyballshoes.app.entity.Product;
import com.DuAn1.volleyballshoes.app.utils.XJdbc;

import java.sql.Timestamp;
import java.util.List;

public class ProductDAOImpl implements CrudDAO<Product, Integer> {

    @Override
    public Product create(Product entity) {
        String sql = "INSERT INTO Product (brand_id, category_id, product_name, product_desc, product_create_at, product_updated_at) VALUES (?, ?, ?, ?, ?, ?); SELECT SCOPE_IDENTITY();";
        Object idObj = XJdbc.getValue(sql,
                entity.brand_id,
                entity.category_id,
                entity.product_name,
                entity.product_desc,
                Timestamp.valueOf(entity.product_create_at),
                Timestamp.valueOf(entity.product_updated_at)
        );
        int id = (idObj instanceof Number) ? ((Number) idObj).intValue() : Integer.parseInt(idObj.toString());
        entity.product_id = id;
        return entity;
    }

    @Override
    public void update(Product entity) {
        String sql = "UPDATE Product SET brand_id=?, category_id=?, product_name=?, product_desc=?, product_updated_at=? WHERE product_id=?";
        XJdbc.executeUpdate(sql,
                entity.brand_id,
                entity.category_id,
                entity.product_name,
                entity.product_desc,
                Timestamp.valueOf(entity.product_updated_at),
                entity.product_id
        );
    }

    @Override
    public void deleteById(Integer id) {
        deleteById(id.toString());
    }

    public void deleteById(String id) {
        String sql = "DELETE FROM Product WHERE product_id=?";
        XJdbc.executeUpdate(sql, Integer.parseInt(id));
    }

    @Override
    public List<Product> findAll() {
        String sql = "SELECT * FROM Product";
        return XJdbc.query(sql, rs -> Product.builder()
                .product_id(rs.getInt("product_id"))
                .brand_id(rs.getInt("brand_id"))
                .category_id(rs.getInt("category_id"))
                .product_name(rs.getString("product_name"))
                .product_desc(rs.getString("product_desc"))
                .product_create_at(rs.getTimestamp("product_create_at").toLocalDateTime())
                .product_updated_at(rs.getTimestamp("product_updated_at").toLocalDateTime())
                .build()
        );
    }

    @Override
    public Product findById(Integer id) {
        String sql = "SELECT * FROM Product WHERE product_id=?";
        return XJdbc.queryForObject(sql, rs -> Product.builder()
                .product_id(rs.getInt("product_id"))
                .brand_id(rs.getInt("brand_id"))
                .category_id(rs.getInt("category_id"))
                .product_name(rs.getString("product_name"))
                .product_desc(rs.getString("product_desc"))
                .product_create_at(rs.getTimestamp("product_create_at").toLocalDateTime())
                .product_updated_at(rs.getTimestamp("product_updated_at").toLocalDateTime())
                .build(), id);
    }
}
