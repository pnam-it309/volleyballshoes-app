package com.DuAn1.volleyballshoes.app.repository;

import com.DuAn1.volleyballshoes.app.entity.Product;
import com.DuAn1.volleyballshoes.app.utils.XJdbc;
import com.DuAn1.volleyballshoes.app.utils.XQuery;
import java.util.List;

public class ProductRepository {
    public void insert(Product product) {
        String sql = "INSERT INTO Product (brand_id, category_id, product_name, product_desc, product_create_at, product_updated_at) VALUES (?, ?, ?, ?, ?, ?)";
        XJdbc.executeUpdate(sql, product.brand_id, product.category_id, product.product_name, product.product_desc, product.product_create_at, product.product_updated_at);
    }
    public void update(Product product) {
        String sql = "UPDATE Product SET brand_id=?, category_id=?, product_name=?, product_desc=?, product_create_at=?, product_updated_at=? WHERE product_id=?";
        XJdbc.executeUpdate(sql, product.brand_id, product.category_id, product.product_name, product.product_desc, product.product_create_at, product.product_updated_at, product.product_id);
    }
    public void delete(int productId) {
        String sql = "DELETE FROM Product WHERE product_id=?";
        XJdbc.executeUpdate(sql, productId);
    }
    public List<Product> selectAll() {
        String sql = "SELECT * FROM Product";
        return XQuery.getBeanList(Product.class, sql);
    }
    public Product selectById(int productId) {
        String sql = "SELECT * FROM Product WHERE product_id=?";
        return XQuery.getSingleBean(Product.class, sql, productId);
    }
} 