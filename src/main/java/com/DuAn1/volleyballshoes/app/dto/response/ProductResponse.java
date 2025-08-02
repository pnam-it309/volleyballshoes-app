package com.DuAn1.volleyballshoes.app.dto.response;

import com.DuAn1.volleyballshoes.app.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

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
    
    public static ProductResponse fromEntity(Product product) {
        return ProductResponse.builder()
            .productId(product.getProductId())
            .productCode(product.getProductCode())
            .productName(product.getProductName())
            .productDescription(product.getProductDescription())
            .brandId(product.getBrandId())
            .brandName("Brand " + product.getBrandId()) // Would be populated from Brand entity
            .categoryId(product.getCategoryId())
            .categoryName("Category " + product.getCategoryId()) // Would be populated from Category entity
            .productCreateAt(product.getProductCreateAt())
            .productUpdatedAt(product.getProductUpdatedAt())
            .build();
    }
}
