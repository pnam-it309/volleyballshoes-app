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
        String sql = "INSERT INTO Categories (category_name, category_description, created_at) "
                  + "OUTPUT INSERTED.* "
                  + "VALUES (?, ?, ?)";
        
        category.setCreatedAt(LocalDateTime.now());
        
        return XJdbc.queryForObject(sql, this::mapResultSetToCategory,
            category.getCategoryName(),
            category.getCategoryDescription(),
            category.getCreatedAt()
        );
    }
    
    @Override
    public Category update(Category category) {
        String sql = "UPDATE Categories SET category_name = ?, category_description = ?, "
                  + "updated_at = ? OUTPUT INSERTED.* WHERE id = ?";
        
        category.setUpdatedAt(LocalDateTime.now());
        
        return XJdbc.queryForObject(sql, this::mapResultSetToCategory,
            category.getCategoryName(),
            category.getCategoryDescription(),
            category.getUpdatedAt(),
            category.getId()
        );
    }
    
    @Override
    public boolean deleteById(Integer id) {
        String sql = "DELETE FROM Categories WHERE id = ?";
        return XJdbc.executeUpdate(sql, id) > 0;
    }
    
    @Override
    public List<Category> findAll() {
        String sql = "SELECT * FROM Categories ORDER BY id DESC";
        return XJdbc.query(sql, this::mapResultSetToCategory);
    }
    
    @Override
    public Category findById(Integer id) {
        String sql = "SELECT * FROM Categories WHERE id = ?";
        return XJdbc.queryForObject(sql, this::mapResultSetToCategory, id);
    }
    
    @Override
    public Category findByName(String name) {
        String sql = "SELECT * FROM Categories WHERE category_name = ?";
        return XJdbc.queryForObject(sql, this::mapResultSetToCategory, name);
    }
    
    @Override
    public List<Category> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return findAll();
        }
        
        String searchPattern = "%" + keyword.trim() + "%";
        String sql = "SELECT * FROM Categories WHERE category_name LIKE ? OR category_description LIKE ?";
        return XJdbc.query(sql, this::mapResultSetToCategory, searchPattern, searchPattern);
    }
    
    @Override
    public boolean existsByName(String name) {
        String sql = "SELECT COUNT(*) FROM Categories WHERE category_name = ?";
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
        category.setId(rs.getInt("id"));
        category.setCategoryName(rs.getString("category_name"));
        category.setCategoryDescription(rs.getString("category_description"));
        
        // Xử lý giá trị NULL cho các trường datetime
        Object createdAt = rs.getObject("created_at");
        if (createdAt != null) {
            if (createdAt instanceof java.sql.Timestamp) {
                category.setCreatedAt(((java.sql.Timestamp) createdAt).toLocalDateTime());
            } else if (createdAt instanceof LocalDateTime) {
                category.setCreatedAt((LocalDateTime) createdAt);
            }
        }
        
        Object updatedAt = rs.getObject("updated_at");
        if (updatedAt != null) {
            if (updatedAt instanceof java.sql.Timestamp) {
                category.setUpdatedAt(((java.sql.Timestamp) updatedAt).toLocalDateTime());
            } else if (updatedAt instanceof LocalDateTime) {
                category.setUpdatedAt((LocalDateTime) updatedAt);
            }
        }
        
        return category;
    }
}
