package com.DuAn1.volleyballshoes.app.dao.impl;

import com.DuAn1.volleyballshoes.app.dao.ProductVariantDAO;
import com.DuAn1.volleyballshoes.app.entity.ProductVariant;
import com.DuAn1.volleyballshoes.app.utils.XJdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductVariantDAOImpl implements ProductVariantDAO {

    private ProductVariant mapResultSetToProductVariant(ResultSet rs) throws SQLException {
        ProductVariant variant = new ProductVariant();
        variant.setVariantId(rs.getInt("variant_id"));
        variant.setProductId(rs.getInt("product_id"));
        variant.setSizeId(rs.getInt("size_id"));
        variant.setColorId(rs.getInt("color_id"));
        variant.setSoleId(rs.getInt("sole_id"));
        variant.setVariantSku(rs.getString("variant_sku"));
        variant.setVariantOrigPrice(rs.getBigDecimal("variant_orig_price"));
        variant.setVariantImgUrl(rs.getString("variant_img_url"));
        variant.setQuantity(rs.getInt("variant_quantity")); // Add this line to map the quantity field
        System.out.println("Mapped variant: " + variant.getVariantSku() + " with quantity: " + variant.getQuantity());
        return variant;
    }

    @Override
    public List<ProductVariant> findByProductId(int productId) {
        String sql = "SELECT * FROM ProductVariant WHERE product_id = ?";
        System.out.println("\n=== Executing SQL: " + sql + " with productId: " + productId + " ===");
        
        // Log all variants in the database for debugging
        try (var conn = XJdbc.openConnection();
             var stmt = conn.prepareStatement("SELECT * FROM ProductVariant");
             var rs = stmt.executeQuery()) {
            
            System.out.println("--- ALL VARIANTS IN DATABASE ---");
            int totalVariants = 0;
            int matchingProductId = 0;
            
            while (rs.next()) {
                totalVariants++;
                int currentProductId = rs.getInt("product_id");
                if (currentProductId == productId) {
                    matchingProductId++;
                }
                
                System.out.println(String.format("ID: %d, ProductID: %d, SKU: %-15s, Qty: %-4d, SizeID: %d, ColorID: %d, SoleID: %d, Price: %s",
                    rs.getInt("variant_id"),
                    currentProductId,
                    rs.getString("variant_sku"),
                    rs.getInt("variant_quantity"),
                    rs.getInt("size_id"),
                    rs.getInt("color_id"),
                    rs.getInt("sole_id"),
                    rs.getBigDecimal("variant_orig_price")
                ));
            }
            System.out.println("-------------------------------");
            System.out.println("Total variants in database: " + totalVariants);
            System.out.println("Variants matching product ID " + productId + ": " + matchingProductId);
            
        } catch (SQLException e) {
            System.err.println("Error querying all variants: " + e.getMessage());
            e.printStackTrace();
        }
        
        // Now get variants for the specific product with detailed logging
        System.out.println("\n=== Fetching variants for product ID: " + productId + " ===");
        List<ProductVariant> variants = new ArrayList<>();
        
        try (var conn = XJdbc.openConnection();
             var stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, productId);
            System.out.println("Executing query: " + stmt);
            
            try (var rs = stmt.executeQuery()) {
                while (rs.next()) {
                    try {
                        ProductVariant variant = mapResultSetToProductVariant(rs);
                        variants.add(variant);
                        System.out.println("Mapped variant: " + variant.getVariantSku() + 
                                       ", Qty: " + variant.getQuantity() +
                                       ", SizeID: " + variant.getSizeId() +
                                       ", ColorID: " + variant.getColorId() +
                                       ", SoleID: " + variant.getSoleId());
                    } catch (Exception e) {
                        System.err.println("Error mapping variant: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("=== Found " + variants.size() + " variants for product ID: " + productId + " ===");
        return variants;
    }

    @Override
    public ProductVariant create(ProductVariant entity) {
        String sql = "INSERT INTO ProductVariant (product_id, size_id, color_id, sole_id, variant_sku, variant_orig_price, variant_img_url,variant_quantity) "
                + "OUTPUT INSERTED.* "
                + "VALUES (?, ?, ?, ?, ?, ?, ?,?)";

        return XJdbc.queryForObject(sql, this::mapResultSetToProductVariant,
                entity.getProductId(),
                entity.getSizeId(),
                entity.getColorId(),
                entity.getSoleId(),
                entity.getVariantSku(),
                entity.getVariantOrigPrice(),
                entity.getVariantImgUrl(),
                entity.getQuantity()
        );
    }

    @Override
    public ProductVariant update(ProductVariant entity) {
        String sql = "UPDATE ProductVariant SET product_id = ?, size_id = ?, color_id = ?, "
                + "sole_id = ?, variant_sku = ?, variant_orig_price = ?, variant_img_url = ?, variant_quantity = ? "
                + "OUTPUT INSERTED.* WHERE variant_id = ?";

        return XJdbc.queryForObject(sql, this::mapResultSetToProductVariant,
                entity.getProductId(),
                entity.getSizeId(),
                entity.getColorId(),
                entity.getSoleId(),
                entity.getVariantSku(),
                entity.getVariantOrigPrice(),
                entity.getVariantImgUrl(),
                entity.getQuantity(),
                entity.getVariantId()
        );
    }

    @Override
    public boolean deleteById(Integer id) {
        String sql = "DELETE FROM ProductVariant WHERE variant_id = ?";
        return XJdbc.executeUpdate(sql, id) > 0;
    }

    @Override
    public List<ProductVariant> findAll() {
        String sql = "SELECT * FROM ProductVariant";
        return XJdbc.query(sql, this::mapResultSetToProductVariant);
    }

    @Override
    public List<ProductVariant> findWithPagination(int page, int pageSize, String filter) {
        int offset = (page - 1) * pageSize;
        String sql = "SELECT * FROM ProductVariant "
                + "WHERE variant_sku LIKE ? OR variant_img_url LIKE ? "
                + "ORDER BY variant_id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        String searchPattern = "%" + (filter != null ? filter : "") + "%";
        return XJdbc.query(sql, this::mapResultSetToProductVariant,
                searchPattern, searchPattern, offset, pageSize);
    }

    @Override
    public int count(String filter) {
//        String sql = "SELECT COUNT(*) FROM ProductVariant "
//                   + "WHERE variant_sku LIKE ? OR variant_img_url LIKE ?";
//        
//        String searchPattern = "%" + (filter != null ? filter : "") + "%";
//        Long count = XJdbc.getValue(sql, searchPattern, searchPattern);
//        return count != null ? count.intValue() : 0;
        return 0;
    }

    @Override
    public ProductVariant findById(Integer id) {
        String sql = "SELECT * FROM ProductVariant WHERE variant_id = ?";
        List<ProductVariant> list = XJdbc.query(sql, this::mapResultSetToProductVariant, id);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public boolean existsBySku(String sku) {
        String sql = "SELECT COUNT(*) FROM ProductVariant WHERE variant_sku = ?";
        Long count = XJdbc.getValue(sql, Long.class, sku);
        return count != null && count > 0;
    }
    
    @Override
    public Optional<ProductVariant> findBySku(String sku) {
        String sql = "SELECT * FROM ProductVariant WHERE variant_sku = ?";
        List<ProductVariant> list = XJdbc.query(sql, this::mapResultSetToProductVariant, sku);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }
    
    @Override
    public boolean reduceQuantity(int variantId, int quantity) throws IllegalStateException {
        // First check if there's enough quantity
        ProductVariant variant = this.findById(variantId);
        if (variant == null) {
            throw new IllegalStateException("Không tìm thấy sản phẩm với ID: " + variantId);
        }
        
        if (variant.getQuantity() < quantity) {
            return false;
        }
        
        // Update the quantity
        String sql = "UPDATE ProductVariant SET variant_quantity = variant_quantity - ? WHERE variant_id = ?";
        int updated = XJdbc.executeUpdate(sql, quantity, variantId);
        return updated > 0;
    }
}
