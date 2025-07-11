package com.DuAn1.volleyballshoes.app.entity;

import java.time.LocalDateTime;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Brand {
    private Integer brandId;
    private String name;
    private String logoUrl;
    private LocalDateTime createdAt;
}
