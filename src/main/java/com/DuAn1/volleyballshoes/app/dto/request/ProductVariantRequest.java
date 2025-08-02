package com.DuAn1.volleyballshoes.app.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariantRequest {
    private Long id;
    
    private String sku;
    
    private String barcode;
    

    private BigDecimal price;
    
    private BigDecimal originalPrice;
    
    private BigDecimal cost;
    
    private Integer quantity;
    
    private String unit;
    
    private String imageUrl;
    
    private Long productId;
    
    private Long colorId;
    
    private Long sizeId;
    
    private Long soleTypeId;
    
    private String description;
}
