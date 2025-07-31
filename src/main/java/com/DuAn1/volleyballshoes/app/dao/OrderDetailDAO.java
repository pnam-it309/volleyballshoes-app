package com.DuAn1.volleyballshoes.app.dao;

import com.DuAn1.volleyballshoes.app.entity.OrderDetail;

import java.util.List;
import java.util.Optional;

public interface OrderDetailDAO {
    List<OrderDetail> findByOrderId(Integer orderId);
    Optional<OrderDetail> findById(Integer id);
    OrderDetail save(OrderDetail orderDetail);
    void deleteById(Integer id);
    void deleteByOrderId(Integer orderId);
    List<OrderDetail> saveAll(List<OrderDetail> orderDetails);
}
