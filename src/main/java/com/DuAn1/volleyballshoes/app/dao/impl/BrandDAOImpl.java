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
        String sql = "INSERT INTO Brand (brand_name, brand_code, brand_origin) "
                  + "OUTPUT INSERTED.* "
                  + "VALUES (?, ?, ?)";
        
        return XJdbc.queryForObject(sql, this::mapResultSetToBrand,
            brand.getBrandName(),
            brand.getBrandCode(),
            brand.getBrandOrigin()
        );
    }
    
    @Override
    public Brand update(Brand brand) {
        String sql = "UPDATE Brand SET brand_name = ?, brand_code = ?, brand_origin = ? "
                  + "OUTPUT INSERTED.* WHERE brand_id = ?";
        
        return XJdbc.queryForObject(sql, this::mapResultSetToBrand,
            brand.getBrandName(),
            brand.getBrandCode(),
            brand.getBrandOrigin(),
            brand.getBrandId()
        );
    }
    
    @Override
    public boolean deleteById(Integer id) {
        String sql = "DELETE FROM Brand WHERE brand_id = ?";
        return XJdbc.executeUpdate(sql, id) > 0;
    }
    
    @Override
    public Brand findByCode(String code) {
        String sql = "SELECT * FROM Brand WHERE brand_code = ?";
        return XJdbc.queryForObject(sql, this::mapResultSetToBrand, code);
    }
    
    @Override
    public List<Brand> findAll() {
        String sql = "SELECT * FROM Brand ORDER BY brand_id DESC";
        return XJdbc.query(sql, this::mapResultSetToBrand);
    }
    
    @Override
    public Brand findById(Integer id) {
        String sql = "SELECT * FROM Brand WHERE brand_id = ?";
        return XJdbc.queryForObject(sql, this::mapResultSetToBrand, id);
    }
    
    @Override
    public Brand findByName(String name) {
        String sql = "SELECT * FROM Brand WHERE brand_name = ?";
        return XJdbc.queryForObject(sql, this::mapResultSetToBrand, name);
    }
    
    @Override
    public List<Brand> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return findAll();
        }
        
        String searchPattern = "%" + keyword.trim() + "%";
        String sql = "SELECT * FROM Brand WHERE brand_name LIKE ? OR brand_origin LIKE ?";
        return XJdbc.query(sql, this::mapResultSetToBrand, searchPattern, searchPattern);
    }
    
    @Override
    public boolean existsByName(String name) {
        String sql = "SELECT COUNT(*) FROM Brand WHERE brand_name = ?";
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
        brand.setBrandId(rs.getInt("brand_id"));
        brand.setBrandName(rs.getString("brand_name"));
        brand.setBrandCode(rs.getString("brand_code"));
        brand.setBrandOrigin(rs.getString("brand_origin"));
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
