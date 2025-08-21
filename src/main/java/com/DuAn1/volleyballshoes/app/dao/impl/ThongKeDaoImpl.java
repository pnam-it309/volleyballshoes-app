/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DuAn1.volleyballshoes.app.dao.impl;

import com.DuAn1.volleyballshoes.app.dao.ThongKeDao;
import com.DuAn1.volleyballshoes.app.utils.XJdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nickh
 */
public class ThongKeDaoImpl implements ThongKeDao {

    @Override
    public List<Map<String, Object>> getDoanhThuTheoThang() {
        String sql = """
            SELECT 
                MONTH(order_created_at) AS Thang,
                SUM(order_final_amount) AS DoanhThu
            FROM [Order]
            WHERE order_status = N'Đã thanh toán'
            GROUP BY MONTH(order_created_at)
            ORDER BY Thang
        """;

        List<Map<String, Object>> list = new ArrayList<>();
        try {
            ResultSet rs = XJdbc.executeQuery(sql);
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("Thang", rs.getInt("Thang"));
                row.put("DoanhThu", rs.getDouble("DoanhThu"));
                list.add(row);
            }
            rs.getStatement().getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public double getTongDoanhThu() {
        String sql = "SELECT SUM(order_final_amount) FROM [Order] WHERE order_status = N'Đã thanh toán'";
        try {
            ResultSet rs = XJdbc.executeQuery(sql);
            if (rs.next()) {
                return rs.getDouble(1);
            }
            rs.getStatement().getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int getSoHoaDon() {
        String sql = "SELECT COUNT(*) FROM [Order] WHERE order_status = N'Đã thanh toán'";
        try {
            ResultSet rs = XJdbc.executeQuery(sql);
            if (rs.next()) {
                return rs.getInt(1);
            }
            rs.getStatement().getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public double layDoanhThu(int month, int year) {
        String sql = "SELECT ISNULL(SUM(order_final_amount), 0) FROM [Order] WHERE MONTH(order_created_at) = ? AND YEAR(order_created_at) = ? AND order_status = N'Đã thanh toán'";
        try {
            ResultSet rs = XJdbc.executeQuery(sql, month, year);
            if (rs.next()) {
                return rs.getDouble(1);
            }
            rs.getStatement().getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    @Override
    public int getSoHoaDonDaHuy() {
        String sql = "SELECT COUNT(*) FROM [Order] WHERE order_status = N'Đã hủy'";
        try {
            ResultSet rs = XJdbc.executeQuery(sql);
            if (rs.next()) {
                return rs.getInt(1);
            }
            rs.getStatement().getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    @Override
    public int getTongSoKhachHang() {
        String sql = "SELECT COUNT(DISTINCT customer_id) FROM [Order]";
        try {
            ResultSet rs = XJdbc.executeQuery(sql);
            if (rs.next()) {
                return rs.getInt(1);
            }
            rs.getStatement().getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    @Override
    public double getDoanhThuTheoKhoangThoiGian(Date fromDate, Date toDate) {
        String sql = """
            SELECT ISNULL(SUM(order_final_amount), 0) AS doanh_thu
            FROM [Order]
            WHERE order_created_at BETWEEN ? AND ?
              AND order_status = N'Đã thanh toán'
        """;
        try {
            java.sql.Date sqlFromDate = new java.sql.Date(fromDate.getTime());
            java.sql.Date sqlToDate = new java.sql.Date(toDate.getTime());
            ResultSet rs = XJdbc.executeQuery(sql, sqlFromDate, sqlToDate);
            if (rs.next()) {
                return rs.getDouble("doanh_thu");
            }
            rs.getStatement().getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    @Override
    public int getSoHoaDonTheoKhoangThoiGian(Date fromDate, Date toDate) {
        String sql = """
            SELECT COUNT(*) 
            FROM [Order]
            WHERE order_created_at BETWEEN ? AND ?
              AND order_status = N'Đã thanh toán'
        """;
        try {
            java.sql.Date sqlFromDate = new java.sql.Date(fromDate.getTime());
            java.sql.Date sqlToDate = new java.sql.Date(toDate.getTime());
            ResultSet rs = XJdbc.executeQuery(sql, sqlFromDate, sqlToDate);
            if (rs.next()) {
                return rs.getInt(1);
            }
            rs.getStatement().getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    @Override
    public int getSoHoaDonHuyTheoKhoangThoiGian(Date fromDate, Date toDate) {
        String sql = """
            SELECT COUNT(*) 
            FROM [Order]
            WHERE order_created_at BETWEEN ? AND ?
              AND order_status = N'Đã hủy'
        """;
        try {
            java.sql.Date sqlFromDate = new java.sql.Date(fromDate.getTime());
            java.sql.Date sqlToDate = new java.sql.Date(toDate.getTime());
            ResultSet rs = XJdbc.executeQuery(sql, sqlFromDate, sqlToDate);
            if (rs.next()) {
                return rs.getInt(1);
            }
            rs.getStatement().getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
