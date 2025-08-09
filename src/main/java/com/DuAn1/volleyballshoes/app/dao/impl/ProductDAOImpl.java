package com.DuAn1.volleyballshoes.app.dao.impl;

import com.DuAn1.volleyballshoes.app.dao.ProductDAO;
import com.DuAn1.volleyballshoes.app.entity.Product;
import com.DuAn1.volleyballshoes.app.utils.XJdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {

    @Override
    public Product create(Product product) {
        String sql = "INSERT INTO Product (product_code, product_name, product_description, brand_id, category_id) "
                + "OUTPUT INSERTED.product_id, INSERTED.product_create_at "
                + "VALUES (?, ?, ?, ?, ?)";

        try (ResultSet rs = XJdbc.executeQuery(sql,
                product.getProductCode(),
                product.getProductName(),
                product.getProductDescription(),
                product.getBrandId(),
                product.getCategoryId())) {
            if (rs.next()) {
                product.setProductId(rs.getInt("product_id"));
                Timestamp ts = rs.getTimestamp("product_create_at");
                product.setProductCreateAt(ts != null ? ts.toLocalDateTime() : null);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return product;
    }

    @Override
    public void update(Product product) {
        String sql = "UPDATE Product SET product_code = ?, product_name = ?, product_description = ?, "
                + "brand_id = ?, category_id = ?, product_updated_at = GETDATE() "
                + "WHERE product_id = ?";

        XJdbc.executeUpdate(sql,
                product.getProductCode(),
                product.getProductName(),
                product.getProductDescription(),
                product.getBrandId(),
                product.getCategoryId(),
                product.getProductId()
        );

        // Set the updated timestamp to current time
        product.setProductUpdatedAt(LocalDateTime.now());
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM Product WHERE product_id = ?";
        XJdbc.executeUpdate(sql, id);
    }

    @Override
    public List<Product> findAll() {
        String sql = "SELECT p.*, b.brand_name, c.category_name FROM Product p " +
                   "LEFT JOIN Brand b ON p.brand_id = b.brand_id " +
                   "LEFT JOIN Category c ON p.category_id = c.category_id";
        return XJdbc.query(sql, this::mapResultSetToProduct);
    }

    @Override
    public Product findById(Integer id) {
        String sql = "SELECT p.*, b.brand_name, c.category_name FROM Product p " +
                   "LEFT JOIN Brand b ON p.brand_id = b.brand_id " +
                   "LEFT JOIN Category c ON p.category_id = c.category_id " +
                   "WHERE p.product_id = ?";
        List<Product> list = XJdbc.query(sql, this::mapResultSetToProduct, id);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public Product findByCode(String productCode) {
        String sql = "SELECT p.*, b.brand_name, c.category_name FROM Product p " +
                   "LEFT JOIN Brand b ON p.brand_id = b.brand_id " +
                   "LEFT JOIN Category c ON p.category_id = c.category_id " +
                   "WHERE p.product_code = ?";
        List<Product> list = XJdbc.query(sql, this::mapResultSetToProduct, productCode);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<Product> findByName(String productName) {
        String sql = "SELECT p.*, b.brand_name, c.category_name FROM Product p " +
                   "LEFT JOIN Brand b ON p.brand_id = b.brand_id " +
                   "LEFT JOIN Category c ON p.category_id = c.category_id " +
                   "WHERE p.product_name LIKE ?";
        return XJdbc.query(sql, this::mapResultSetToProduct, "%" + productName + "%");
    }

    @Override
    public List<Product> findByBrandId(Integer brandId) {
        String sql = "SELECT p.*, b.brand_name, c.category_name FROM Product p " +
                   "LEFT JOIN Brand b ON p.brand_id = b.brand_id " +
                   "LEFT JOIN Category c ON p.category_id = c.category_id " +
                   "WHERE p.brand_id = ?";
        return XJdbc.query(sql, this::mapResultSetToProduct, brandId);
    }

    @Override
    public List<Product> findByCategoryId(Integer categoryId) {
        String sql = "SELECT p.*, b.brand_name, c.category_name FROM Product p " +
                   "LEFT JOIN Brand b ON p.brand_id = b.brand_id " +
                   "LEFT JOIN Category c ON p.category_id = c.category_id " +
                   "WHERE p.category_id = ?";
        return XJdbc.query(sql, this::mapResultSetToProduct, categoryId);
    }

    @Override
    public List<Product> findWithPagination(int offset, int limit) {
        String sql = "SELECT p.*, b.brand_name, c.category_name FROM Product p " +
                   "LEFT JOIN Brand b ON p.brand_id = b.brand_id " +
                   "LEFT JOIN Category c ON p.category_id = c.category_id " +
                   "ORDER BY p.product_id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        return XJdbc.query(sql, this::mapResultSetToProduct, offset, limit);
    }

    @Override
    public long countAll() {
        String sql = "SELECT COUNT(*) FROM Product";
        return XJdbc.getValue(sql, Long.class);
    }

    /**
     * Chuyển đổi ResultSet thành đối tượng Product
     *
     * @param rs ResultSet chứa dữ liệu
     * @return Đối tượng Product
     * @throws SQLException
     */
    private Product mapResultSetToProduct(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setProductId(rs.getInt("product_id"));
        product.setProductCode(rs.getString("product_code"));
        product.setProductName(rs.getString("product_name"));
        product.setProductDescription(rs.getString("product_description"));
        product.setBrandId(rs.getInt("brand_id"));
        product.setCategoryId(rs.getInt("category_id"));

        // Set brand name if available in the result set
        try {
            if (rs.getMetaData().getColumnCount() > 6) { // Check if we have additional columns
                String brandName = rs.getString("brand_name");
                String categoryName = rs.getString("category_name");
                
                // Use reflection to set the brand and category names if they exist in the Product class
                try {
                    java.lang.reflect.Field brandNameField = product.getClass().getDeclaredField("brandName");
                    brandNameField.setAccessible(true);
                    brandNameField.set(product, brandName);
                    
                    java.lang.reflect.Field categoryNameField = product.getClass().getDeclaredField("categoryName");
                    categoryNameField.setAccessible(true);
                    categoryNameField.set(product, categoryName);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    // Fields don't exist or can't be accessed, ignore
                }
            }
        } catch (SQLException e) {
            // Column doesn't exist in the result set, ignore
        }

        // Xử lý giá trị NULL cho các trường datetime
        java.sql.Timestamp createAt = rs.getTimestamp("product_create_at");
        if (createAt != null) {
            product.setProductCreateAt(createAt.toLocalDateTime());
        }

        java.sql.Timestamp updatedAt = rs.getTimestamp("product_updated_at");
        if (updatedAt != null) {
            product.setProductUpdatedAt(updatedAt.toLocalDateTime());
        }

        return product;
    }
}
