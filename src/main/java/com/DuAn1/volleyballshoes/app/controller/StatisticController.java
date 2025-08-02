package com.DuAn1.volleyballshoes.app.controller;

import com.DuAn1.volleyballshoes.app.dao.OrderDAO;
import com.DuAn1.volleyballshoes.app.dao.CustomerDAO;
import com.DuAn1.volleyballshoes.app.dao.impl.OrderDAOImpl;
import com.DuAn1.volleyballshoes.app.dao.impl.CustomerDAOImpl;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

public class StatisticController {

    private OrderDAO orderDAO = new OrderDAOImpl();
    private CustomerDAO customerDAO = new CustomerDAOImpl();

    public BigDecimal getTotalRevenue() {
        return orderDAO.getTotalRevenue();
    }

    public int getTotalOrders() {
        return orderDAO.getTotalOrders();
    }

    public int getCanceledOrders() {
        return orderDAO.getCanceledOrders();
    }

    public int getNewCustomersCount() {
        return customerDAO.getNewCustomersCount();
    }

    public BigDecimal getRevenueByDateRange(Date fromDate, Date toDate) {
        return orderDAO.getRevenueByDateRange(fromDate, toDate);
    }

    public Object[][] getRevenueDataByYear(int year) {
        return orderDAO.getRevenueDataByYear(year);
    }

    public Object[][] getCanceledOrderDataByYear(int year) {
        return orderDAO.getCanceledOrderDataByYear(year);
    }
}
