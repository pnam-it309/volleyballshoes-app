package com.DuAn1.volleyballshoes.app.dto.request;

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
public class OrderRequest {
    private Long customerId;
    private Long staffId;
    private BigDecimal finalAmount;
    private String paymentMethod;
    private String status;
    private String orderCode;
    private List<OrderDetailRequest> orderDetails;
}
