package com.DuAn1.volleyballshoes.app.controller;

import com.DuAn1.volleyballshoes.app.dao.OrderDAO;
import com.DuAn1.volleyballshoes.app.dao.OrderDetailDAO;
import com.DuAn1.volleyballshoes.app.dto.response.BillResponse;
import com.DuAn1.volleyballshoes.app.dto.response.OrderDetailResponse;
import com.DuAn1.volleyballshoes.app.entity.Order;
import com.DuAn1.volleyballshoes.app.entity.OrderDetail;
import com.DuAn1.volleyballshoes.app.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class BillController {
    private final OrderDAO orderDAO;
    private final OrderDetailDAO orderDetailDAO;

    public List<BillResponse> getAllBills() {
        return orderDAO.findAll().stream()
                .map(this::mapToBillResponse)
                .collect(Collectors.toList());
    }

    public BillResponse getBillById(Long id) {
        Order order = orderDAO.findById(id)
                .orElseThrow(() -> new BusinessException("Không tìm thấy hóa đơn với ID: " + id));
        return mapToBillResponse(order);
    }

    public List<BillResponse> searchBills(String keyword, LocalDateTime fromDate, LocalDateTime toDate) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            return orderDAO.findByCustomerNameContainingIgnoreCase(keyword).stream()
                    .filter(order -> isDateInRange(order.getOrderDate(), fromDate, toDate))
                    .map(this::mapToBillResponse)
                    .collect(Collectors.toList());
        }
        return orderDAO.findByOrderDateBetween(fromDate, toDate).stream()
                .map(this::mapToBillResponse)
                .collect(Collectors.toList());
    }

    private boolean isDateInRange(LocalDateTime date, LocalDateTime fromDate, LocalDateTime toDate) {
        if (fromDate != null && date.isBefore(fromDate)) {
            return false;
        }
        if (toDate != null && date.isAfter(toDate)) {
            return false;
        }
        return true;
    }

    private BillResponse mapToBillResponse(Order order) {
        List<OrderDetail> orderDetails = orderDetailDAO.findByOrderId(order.getId());
        
        List<OrderDetailResponse> detailResponses = orderDetails.stream()
                .map(detail -> new OrderDetailResponse(
                        detail.getId(),
                        detail.getProductVariant().getProduct().getName(),
                        detail.getProductVariant().getColor().getName(),
                        detail.getProductVariant().getSize().getSize(),
                        detail.getQuantity(),
                        detail.getPrice(),
                        detail.getPrice().multiply(BigDecimal.valueOf(detail.getQuantity()))))
                .collect(Collectors.toList());

        return new BillResponse(
                order.getId(),
                order.getCustomer().getName(),
                order.getStaff().getName(),
                order.getOrderDate(),
                order.getTotalAmount(),
                order.getStatus(),
                detailResponses);
    }
}
