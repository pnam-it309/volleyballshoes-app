package com.DuAn1.volleyballshoes.app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {
    private RevenueStats revenueStats;
    private OrderStats orderStats;
    private ProductStats productStats;
    private List<TopSellingProduct> topSellingProducts;
    private List<RecentOrder> recentOrders;
    private List<RevenueByCategory> revenueByCategory;
    private List<MonthlyRevenue> monthlyRevenue;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RevenueStats {
        private BigDecimal todayRevenue;
        private BigDecimal monthlyRevenue;
        private BigDecimal yearlyRevenue;
        private BigDecimal totalRevenue;
        private BigDecimal revenueChangePercent;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderStats {
        private long totalOrders;
        private long todayOrders;
        private long pendingOrders;
        private long completedOrders;
        private long cancelledOrders;
        private double orderChangePercent;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductStats {
        private long totalProducts;
        private long totalVariants;
        private long lowStockProducts;
        private long outOfStockProducts;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TopSellingProduct {
        private Long productId;
        private String productName;
        private String imageUrl;
        private long quantitySold;
        private BigDecimal revenue;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecentOrder {
        private Long orderId;
        private String orderCode;
        private String customerName;
        private BigDecimal totalAmount;
        private String status;
        private String orderDate;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RevenueByCategory {
        private Long categoryId;
        private String categoryName;
        private BigDecimal revenue;
        private double percentage;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MonthlyRevenue {
        private String month;
        private BigDecimal revenue;
        private long orderCount;
    }
}
