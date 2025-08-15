package com.DuAn1.volleyballshoes.app.dao;

import com.DuAn1.volleyballshoes.app.entity.Order;
import com.DuAn1.volleyballshoes.app.entity.OrderDetail;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
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

    Order update(Order order);

    BigDecimal getTotalRevenue();

    int getTotalOrders();

    int getCanceledOrders();

    BigDecimal getRevenueByDateRange(Date fromDate, Date toDate);

    int getNewCustomersCount();

    Object[][] getRevenueDataByYear(int year);

    Object[][] getCanceledOrderDataByYear(int year);

    List<Order> findByCreatedDateBetween(Date from, Date to);

    List<Order> findByTotalAmountBetween(double min, double max);

    List<Order> findByStatus(String status);

    String generateOrderCode();

    int getStaffIdByCode(String staffCode);

    Order getOrderByCode(String orderCode);

    Order processPayment(Order order, Map<Integer, Integer> orderDetails) throws Exception;
}
