package com.DuAn1.volleyballshoes.app.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class StatisticRequest {
    private LocalDate startDate;
    private LocalDate endDate;
    private String type; // DAY, MONTH, YEAR, CUSTOM
    private Long categoryId; // Lọc theo danh mục (nếu cần)
    private Long staffId; // Lọc theo nhân viên (nếu cần)
}
