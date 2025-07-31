package com.DuAn1.volleyballshoes.app.dao;

import com.DuAn1.volleyballshoes.app.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderDAO {
    List<Order> findAll();
    List<Order> findAll(int page, int pageSize);
    Optional<Order> findById(Integer id);
    Optional<Order> findByCode(String code);
    Order save(Order order);
    void deleteById(Integer id);
    long count();
    List<Order> findByCustomerId(Integer customerId);
    List<Order> findByStaffId(Integer staffId);
    List<Order> search(String keyword);
}
