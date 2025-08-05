package com.DuAn1.volleyballshoes.app.dao.impl;

import com.DuAn1.volleyballshoes.app.dao.ProductDAO;
import com.DuAn1.volleyballshoes.app.entity.Product;
import com.DuAn1.volleyballshoes.app.utils.XJdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {
    
    @Override
    public Product create(Product product) {
        String sql = "INSERT INTO Product (product_code, product_name, product_desc, brand_id, category_id, product_create_at) "
                  + "VALUES (?, ?, ?, ?, ?, ?)";
        
        product.setProductCreateAt(LocalDateTime.now());
        
        XJdbc.executeUpdate(sql, 
            product.getProductName(),
            product.getProductDescription(),
            product.getBrandId(),
            product.getCategoryId(),
            product.getProductCreateAt()
        );
        
        // Lấy ID vừa tạo
        sql = "SELECT IDENT_CURRENT('Products') as id";
        Integer id = XJdbc.getValue(sql, Integer.class);
        product.setProductId(id);
        
        return product;
    }
    
    @Override
    public void update(Product product) {
        String sql = "UPDATE Product SET product_code = ?, product_name = ?, product_desc = ?, "
                  + "brand_id = ?, category_id = ?, product_updated_at = ? "
                  + "WHERE product_id = ?";
        
        product.setProductUpdatedAt(LocalDateTime.now());
        
        XJdbc.executeUpdate(sql, 
            product.getProductName(),
            product.getProductDescription(),
            product.getBrandId(),
            product.getCategoryId(),
            product.getProductUpdatedAt(),
            product.getProductId()
        );
    }
    
    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM Product WHERE product_id = ?";
        XJdbc.executeUpdate(sql, id);
    }
    
    @Override
    public List<Product> findAll() {
        String sql = "SELECT * FROM Product";
        return XJdbc.query(sql, this::mapResultSetToProduct);
    }
    
    @Override
    public Product findById(Integer id) {
        String sql = "SELECT * FROM Product WHERE product_id = ?";
        List<Product> list = XJdbc.query(sql, this::mapResultSetToProduct, id);
        return list.isEmpty() ? null : list.get(0);
    }
    
    @Override
    public Product findByCode(String productCode) {
        String sql = "SELECT * FROM Product WHERE product_code = ?";
        List<Product> list = XJdbc.query(sql, this::mapResultSetToProduct, productCode);
        return list.isEmpty() ? null : list.get(0);
    }
    
    @Override
    public List<Product> findByName(String productName) {
        String sql = "SELECT * FROM Product WHERE product_name LIKE ?";
        return XJdbc.query(sql, this::mapResultSetToProduct, "%" + productName + "%");
    }
    
    @Override
    public List<Product> findByBrandId(Integer brandId) {
        String sql = "SELECT * FROM Product WHERE brand_id = ?";
        return XJdbc.query(sql, this::mapResultSetToProduct, brandId);
    }
    
    @Override
    public List<Product> findByCategoryId(Integer categoryId) {
        String sql = "SELECT * FROM Product WHERE category_id = ?";
        return XJdbc.query(sql, this::mapResultSetToProduct, categoryId);
    }
    
    @Override
    public List<Product> findWithPagination(int offset, int limit) {
        String sql = "SELECT * FROM Product ORDER BY product_id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        return XJdbc.query(sql, this::mapResultSetToProduct, offset, limit);
    }
    
    @Override
    public long countAll() {
        String sql = "SELECT COUNT(*) FROM Product";
        return XJdbc.getValue(sql, Long.class);
    }
    
    /**
     * Chuyển đổi ResultSet thành đối tượng Product
     * @param rs ResultSet chứa dữ liệu
     * @return Đối tượng Product
     * @throws SQLException 
     */
    private Product mapResultSetToProduct(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setProductId(rs.getInt("product_id"));
        product.setProductName(rs.getString("product_name"));
        product.setProductDescription(rs.getString("product_desc"));
        product.setBrandId(rs.getInt("brand_id"));
        product.setCategoryId(rs.getInt("category_id"));
        
        // Xử lý giá trị NULL cho các trường datetime
        Object createAt = rs.getObject("product_create_at");
        if (createAt != null) {
            product.setProductCreateAt((java.time.LocalDateTime) createAt);
        }
        
        Object updatedAt = rs.getObject("product_updated_at");
        if (updatedAt != null) {
            product.setProductUpdatedAt((java.time.LocalDateTime) updatedAt);
        }
        
        return product;
    }
}
