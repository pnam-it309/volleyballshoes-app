package com.DuAn1.volleyballshoes.app.dto.response;

import com.DuAn1.volleyballshoes.app.dto.response.SimpleProductResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariantResponse {
    private Long id;
    private String sku;
    private String barcode;
    private BigDecimal price;
    private BigDecimal originalPrice;
    private BigDecimal cost;
    private Integer quantity;
    private String unit;
    private String imageUrl;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    private SimpleProductResponse product;
    private ColorResponse color;
    private SizeResponse size;
    private SoleTypeResponse soleType;



}
