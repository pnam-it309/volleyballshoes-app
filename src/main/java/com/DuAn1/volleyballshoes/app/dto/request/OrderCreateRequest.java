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

    private Integer customerId;
    

    private Integer staffId;
    
    private List<OrderItemRequest> items;
    
    private String paymentMethod;
    
    private String note;
}
