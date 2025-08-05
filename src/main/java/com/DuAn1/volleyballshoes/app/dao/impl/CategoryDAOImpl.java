package com.DuAn1.volleyballshoes.app.dao.impl;

import com.DuAn1.volleyballshoes.app.dao.CategoryDAO;
import com.DuAn1.volleyballshoes.app.entity.Category;
import com.DuAn1.volleyballshoes.app.utils.XJdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class CategoryDAOImpl implements CategoryDAO {
    
    @Override
    public Category create(Category category) {
        String sql = "INSERT INTO Category (category_name, category_code) "
                  + "OUTPUT INSERTED.* "
                  + "VALUES (?, ?)";
        
        return XJdbc.queryForObject(sql, this::mapResultSetToCategory,
            category.getCategoryName(),
            category.getCategoryCode()
        );
    }
    
    @Override
    public Category update(Category category) {
        String sql = "UPDATE Category SET category_name = ?, category_code = ? "
                  + "OUTPUT INSERTED.* WHERE category_id = ?";
        
        return XJdbc.queryForObject(sql, this::mapResultSetToCategory,
            category.getCategoryName(),
            category.getCategoryCode(),
            category.getCategoryId()
        );
    }
    
    @Override
    public boolean deleteById(Integer id) {
        String sql = "DELETE FROM Category WHERE category_id = ?";
        return XJdbc.executeUpdate(sql, id) > 0;
    }
    
    @Override
    public List<Category> findAll() {
        String sql = "SELECT * FROM Category ORDER BY category_id DESC";
        return XJdbc.query(sql, this::mapResultSetToCategory);
    }
    
    @Override
    public Category findById(Integer id) {
        String sql = "SELECT * FROM Category WHERE category_id = ?";
        return XJdbc.queryForObject(sql, this::mapResultSetToCategory, id);
    }
    
    @Override
    public Category findByName(String name) {
        String sql = "SELECT * FROM Category WHERE category_name = ?";
        return XJdbc.queryForObject(sql, this::mapResultSetToCategory, name);
    }
    
    @Override
    public List<Category> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return findAll();
        }
        
        String searchPattern = "%" + keyword.trim() + "%";
        String sql = "SELECT * FROM Category WHERE category_name LIKE ? OR category_code LIKE ?";
        return XJdbc.query(sql, this::mapResultSetToCategory, searchPattern, searchPattern);
    }
    
    @Override
    public boolean existsByName(String name) {
        String sql = "SELECT COUNT(*) FROM Category WHERE category_name = ?";
        List<Long> counts = XJdbc.query(sql, rs -> rs.getLong(1), name);
        return !counts.isEmpty() && counts.get(0) > 0;
    }
    
    /**
     * Chuyển đổi ResultSet thành đối tượng Category
     */
    private Category mapResultSetToCategory(ResultSet rs) throws SQLException {
        if (rs == null || !rs.next()) {
            return null;
        }
        
        Category category = new Category();
        category.setCategoryId(rs.getInt("category_id"));
        category.setCategoryName(rs.getString("category_name"));

        
        return category;
    }
}
