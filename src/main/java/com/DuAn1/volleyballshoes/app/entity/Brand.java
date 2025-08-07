package com.DuAn1.volleyballshoes.app.entity;

import java.time.LocalDateTime;
import lombok.*;

/**
 * Entity class representing a brand of products.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Brand {

    private Integer id;

    private String brandName;

    private String brandCode;

    private String brandOrigin;

    private String brandDescription;

    // For backward compatibility
    public int getBrandId() {
        return id != null ? id : 0;
    }

    public void setBrandId(int brandId) {
        this.id = brandId;
    }

}
