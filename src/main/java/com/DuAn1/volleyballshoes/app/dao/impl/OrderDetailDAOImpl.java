package com.DuAn1.volleyballshoes.app.dao.impl;

import com.DuAn1.volleyballshoes.app.dao.OrderDetailDAO;
import com.DuAn1.volleyballshoes.app.entity.OrderDetail;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class OrderDetailDAOImpl implements OrderDetailDAO {
    private final List<OrderDetail> orderDetails = new ArrayList<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    @Override
    public List<OrderDetail> findByOrderId(Integer orderId) {
        return orderDetails.stream()
            .filter(od -> od.getOrderId().equals(orderId))
            .collect(Collectors.toList());
    }

    @Override
    public Optional<OrderDetail> findById(Integer id) {
        return orderDetails.stream()
            .filter(od -> od.getOrderDetailId().equals(id))
            .findFirst();
    }

    @Override
    public OrderDetail save(OrderDetail orderDetail) {
        if (orderDetail.getOrderDetailId() == null) {
            // Create new
            orderDetail.setOrderDetailId(idGenerator.getAndIncrement());
            orderDetails.add(orderDetail);
            return orderDetail;
        } else {
            // Update existing
            return orderDetails.stream()
                .filter(od -> od.getOrderDetailId().equals(orderDetail.getOrderDetailId()))
                .findFirst()
                .map(od -> {
                    od.setOrderId(orderDetail.getOrderId());
                    od.setVariantId(orderDetail.getVariantId());
                    od.setDetailQuantity(orderDetail.getDetailQuantity());
                    od.setDetailUnitPrice(orderDetail.getDetailUnitPrice());
                    return od;
                })
                .orElseThrow(() -> new RuntimeException("Order detail not found with id: " + orderDetail.getOrderDetailId()));
        }
    }

    @Override
    public void deleteById(Integer id) {
        orderDetails.removeIf(od -> od.getOrderDetailId().equals(id));
    }

    @Override
    public void deleteByOrderId(Integer orderId) {
        orderDetails.removeIf(od -> od.getOrderId().equals(orderId));
    }

    @Override
    public List<OrderDetail> saveAll(List<OrderDetail> orderDetailList) {
        return orderDetailList.stream()
            .map(this::save)
            .collect(Collectors.toList());
    }
}
