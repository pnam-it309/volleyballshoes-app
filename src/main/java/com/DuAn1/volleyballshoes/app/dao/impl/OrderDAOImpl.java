package com.DuAn1.volleyballshoes.app.dao.impl;

import com.DuAn1.volleyballshoes.app.dao.OrderDAO;
import com.DuAn1.volleyballshoes.app.entity.Order;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class OrderDAOImpl implements OrderDAO {
    private final List<Order> orders = new ArrayList<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    @Override
    public List<Order> findAll() {
        return new ArrayList<>(orders);
    }

    @Override
    public List<Order> findAll(int page, int pageSize) {
        int start = page * pageSize;
        int end = Math.min(start + pageSize, orders.size());
        
        if (start > end) {
            return new ArrayList<>();
        }
        
        return new ArrayList<>(orders.subList(start, end));
    }

    @Override
    public Optional<Order> findById(Integer id) {
        return orders.stream()
            .filter(o -> o.getOrderId().equals(id))
            .findFirst();
    }

    @Override
    public Optional<Order> findByCode(String code) {
        return orders.stream()
            .filter(o -> o.getOrderCode().equalsIgnoreCase(code))
            .findFirst();
    }

    @Override
    public Order save(Order order) {
        if (order.getOrderId() == null) {
            // Create new order
            order.setOrderId(idGenerator.getAndIncrement());
            order.setOrderCode("ORD" + String.format("%06d", order.getOrderId()));
            order.setOrderCreatedAt(LocalDateTime.now());
            orders.add(order);
            return order;
        } else {
            // Update existing order
            return orders.stream()
                .filter(o -> o.getOrderId().equals(order.getOrderId()))
                .findFirst()
                .map(o -> {
                    o.setCustomerId(order.getCustomerId());
                    o.setStaffId(order.getStaffId());
                    o.setOrderFinalAmount(order.getOrderFinalAmount());
                    o.setOrderPaymentMethod(order.getOrderPaymentMethod());
                    o.setOrderStatus(order.getOrderStatus());
                    return o;
                })
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + order.getOrderId()));
        }
    }

    @Override
    public void deleteById(Integer id) {
        orders.removeIf(o -> o.getOrderId().equals(id));
    }

    @Override
    public long count() {
        return orders.size();
    }

    @Override
    public List<Order> findByCustomerId(Integer customerId) {
        return orders.stream()
            .filter(o -> o.getCustomerId().equals(customerId))
            .collect(Collectors.toList());
    }

    @Override
    public List<Order> findByStaffId(Integer staffId) {
        return orders.stream()
            .filter(o -> o.getStaffId().equals(staffId))
            .collect(Collectors.toList());
    }

    @Override
    public List<Order> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return new ArrayList<>(orders);
        }
        
        String searchTerm = keyword.toLowerCase().trim();
        return orders.stream()
            .filter(o -> 
                (o.getOrderCode() != null && o.getOrderCode().toLowerCase().contains(searchTerm)) ||
                (o.getOrderStatus() != null && o.getOrderStatus().toLowerCase().contains(searchTerm)) ||
                (o.getOrderPaymentMethod() != null && o.getOrderPaymentMethod().toLowerCase().contains(searchTerm))
            )
            .collect(Collectors.toList());
    }
}
