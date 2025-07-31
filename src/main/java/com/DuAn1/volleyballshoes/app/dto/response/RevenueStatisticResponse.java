package com.DuAn1.volleyballshoes.app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RevenueStatisticResponse {
    private LocalDate date;
    private long orderCount;
    private BigDecimal totalRevenue;
    private long productSold;
    private String label; // Ngày/tháng/năm tùy theo loại thống kê
}
