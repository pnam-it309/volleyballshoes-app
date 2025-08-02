package com.DuAn1.volleyballshoes.app.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCreateRequest {
    
    private String productCode;
    private String productName;
    private String productDescription;
    private Integer brandId;
    private Integer categoryId;
    
    public boolean isValid() {
        return productCode != null && !productCode.trim().isEmpty() &&
               productName != null && !productName.trim().isEmpty() &&
               brandId != null && brandId > 0 &&
               categoryId != null && categoryId > 0;
    }
}
