package com.DuAn1.volleyballshoes.app.dto.response;

import com.DuAn1.volleyballshoes.app.dao.BrandDAO;
import com.DuAn1.volleyballshoes.app.dao.CategoryDAO;
import com.DuAn1.volleyballshoes.app.dao.impl.BrandDAOImpl;
import com.DuAn1.volleyballshoes.app.dao.impl.CategoryDAOImpl;
import com.DuAn1.volleyballshoes.app.entity.Brand;
import com.DuAn1.volleyballshoes.app.entity.Category;
import com.DuAn1.volleyballshoes.app.entity.Product;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {
    
    private Integer productId;
    private String productCode;
    private String productName;
    private String productDescription;
    private Integer brandId;
    private String brandName;
    private Integer categoryId;
    private String categoryName;
    private LocalDateTime productCreateAt;
    private LocalDateTime productUpdatedAt;
    
    private static BrandDAO brandDAO = new BrandDAOImpl();
    private static CategoryDAO categoryDAO = new CategoryDAOImpl();
    
    public static ProductResponse fromEntity(Product product) {
        String brandName = "";
        String categoryName = "";
        
        try {
            // Get brand name
            Brand brand = brandDAO.findById(product.getBrandId());
            if (brand != null) {
                brandName = brand.getBrandName();
            }
            
            // Get category name
            Category category = categoryDAO.findById(product.getCategoryId());
            if (category != null) {
                categoryName = category.getCategoryName();
            }
        } catch (Exception e) {
            // If there's an error, use the ID as fallback
            brandName = "Brand " + product.getBrandId();
            categoryName = "Category " + product.getCategoryId();
        }
        
        return ProductResponse.builder()
            .productId(product.getProductId())
            .productCode(product.getProductCode())
            .productName(product.getProductName())
            .productDescription(product.getProductDescription())
            .brandId(product.getBrandId())
            .brandName(brandName)
            .categoryId(product.getCategoryId())
            .categoryName(categoryName)
            .productCreateAt(product.getProductCreateAt())
            .productUpdatedAt(product.getProductUpdatedAt())
            .build();
    }
}
