package com.DuAn1.volleyballshoes.app.entity;

import java.time.LocalDateTime;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {
    private Integer categoryId;
    private String name;
    private Integer parentId; 
    private LocalDateTime createdAt;
}

