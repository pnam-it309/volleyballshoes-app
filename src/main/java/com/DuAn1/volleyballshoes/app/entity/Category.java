package com.DuAn1.volleyballshoes.app.entity;

import lombok.*;

/**
 * Entity class representing a product category.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {
    private Integer id;
    private String categoryName;
    private String categoryCode;
    
    // For backward compatibility
    public int getCategoryId() {
        return id != null ? id : 0;
    }
    
    public void setCategoryId(int categoryId) {
        this.id = categoryId;
    }
}

