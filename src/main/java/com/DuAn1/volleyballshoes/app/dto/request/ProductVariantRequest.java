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
    
    @NotBlank(message = "Mã SKU không được để trống")
    private String sku;
    
    @NotBlank(message = "Mã vạch không được để trống")
    private String barcode;
    
    @NotNull(message = "Giá bán không được để trống")
    @DecimalMin(value = "0.0", message = "Giá bán phải lớn hơn hoặc bằng 0")
    private BigDecimal price;
    
    @NotNull(message = "Giá gốc không được để trống")
    @DecimalMin(value = "0.0", message = "Giá gốc phải lớn hơn hoặc bằng 0")
    private BigDecimal originalPrice;
    
    @NotNull(message = "Chi phí không được để trống")
    @DecimalMin(value = "0.0", message = "Chi phí phải lớn hơn hoặc bằng 0")
    private BigDecimal cost;
    
    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 0, message = "Số lượng không được âm")
    private Integer quantity;
    
    @NotBlank(message = "Đơn vị tính không được để trống")
    private String unit;
    
    private String imageUrl;
    
    @NotNull(message = "ID sản phẩm không được để trống")
    private Long productId;
    
    @NotNull(message = "ID màu sắc không được để trống")
    private Long colorId;
    
    @NotNull(message = "ID kích thước không được để trống")
    private Long sizeId;
    
    private Long soleTypeId;
    
    private String description;
}
