package com.DuAn1.volleyballshoes.app.dao.impl;

import com.DuAn1.volleyballshoes.app.dao.BrandDAO;
import com.DuAn1.volleyballshoes.app.entity.Brand;
import com.DuAn1.volleyballshoes.app.utils.XJdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class BrandDAOImpl implements BrandDAO {
    
    @Override
    public Brand create(Brand brand) {
        String sql = "INSERT INTO Brands (brand_name, brand_description, created_at) "
                  + "OUTPUT INSERTED.* "
                  + "VALUES (?, ?, ?)";
        
        brand.setCreatedAt(LocalDateTime.now());
        
        return XJdbc.queryForObject(sql, this::mapResultSetToBrand,
            brand.getBrandName(),
            brand.getBrandDescription(),
            brand.getCreatedAt()
        );
    }
    
    @Override
    public Brand update(Brand brand) {
        String sql = "UPDATE Brands SET brand_name = ?, brand_description = ?, "
                  + "updated_at = ? OUTPUT INSERTED.* WHERE id = ?";
        
        brand.setUpdatedAt(LocalDateTime.now());
        
        return XJdbc.queryForObject(sql, this::mapResultSetToBrand,
            brand.getBrandName(),
            brand.getBrandDescription(),
            brand.getUpdatedAt(),
            brand.getId()
        );
    }
    
    @Override
    public boolean deleteById(Integer id) {
        String sql = "DELETE FROM Brands WHERE id = ?";
        return XJdbc.executeUpdate(sql, id) > 0;
    }
    
    @Override
    public List<Brand> findAll() {
        String sql = "SELECT * FROM Brands ORDER BY id DESC";
        return XJdbc.query(sql, this::mapResultSetToBrand);
    }
    
    @Override
    public Brand findById(Integer id) {
        String sql = "SELECT * FROM Brands WHERE id = ?";
        return XJdbc.queryForObject(sql, this::mapResultSetToBrand, id);
    }
    
    @Override
    public Brand findByName(String name) {
        String sql = "SELECT * FROM Brands WHERE brand_name = ?";
        return XJdbc.queryForObject(sql, this::mapResultSetToBrand, name);
    }
    
    @Override
    public List<Brand> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return findAll();
        }
        
        String searchPattern = "%" + keyword.trim() + "%";
        String sql = "SELECT * FROM Brands WHERE brand_name LIKE ? OR brand_description LIKE ?";
        return XJdbc.query(sql, this::mapResultSetToBrand, searchPattern, searchPattern);
    }
    
    @Override
    public boolean existsByName(String name) {
        String sql = "SELECT COUNT(*) FROM Brands WHERE brand_name = ?";
        List<Long> counts = XJdbc.query(sql, rs -> rs.getLong(1), name);
        return !counts.isEmpty() && counts.get(0) > 0;
    }
    
    /**
     * Chuyển đổi ResultSet thành đối tượng Brand
     */
    private Brand mapResultSetToBrand(ResultSet rs) throws SQLException {
        if (rs == null || !rs.next()) {
            return null;
        }
        
        Brand brand = new Brand();
        brand.setId(rs.getInt("id"));
        brand.setBrandName(rs.getString("brand_name"));
        brand.setBrandDescription(rs.getString("brand_description"));
        
        // Xử lý giá trị NULL cho các trường datetime
        Object createdAt = rs.getObject("created_at");
        if (createdAt != null) {
            if (createdAt instanceof java.sql.Timestamp) {
                brand.setCreatedAt(((java.sql.Timestamp) createdAt).toLocalDateTime());
            } else if (createdAt instanceof LocalDateTime) {
                brand.setCreatedAt((LocalDateTime) createdAt);
            }
        }
        
        Object updatedAt = rs.getObject("updated_at");
        if (updatedAt != null) {
            if (updatedAt instanceof java.sql.Timestamp) {
                brand.setUpdatedAt(((java.sql.Timestamp) updatedAt).toLocalDateTime());
            } else if (updatedAt instanceof LocalDateTime) {
                brand.setUpdatedAt((LocalDateTime) updatedAt);
            }
        }
        
        return brand;
    }
}
