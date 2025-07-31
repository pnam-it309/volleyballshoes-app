package com.DuAn1.volleyballshoes.app.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateRequest {
    @NotNull(message = "Mã khách hàng không được để trống")
    @Positive(message = "Mã khách hàng không hợp lệ")
    private Integer customerId;
    
    @NotNull(message = "Mã nhân viên không được để trống")
    @Positive(message = "Mã nhân viên không hợp lệ")
    private Integer staffId;
    
    @Valid
    private List<OrderItemRequest> items;
    
    @NotNull(message = "Phương thức thanh toán không được để trống")
    private String paymentMethod;
    
    private String note;
}
