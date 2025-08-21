/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DuAn1.volleyballshoes.app.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nickh
 */
public interface ThongKeDao {

    /**
     * Lấy doanh thu theo từng tháng.
     *
     * @return Danh sách map gồm: key = "Thang", key = "DoanhThu"
     */
    List<Map<String, Object>> getDoanhThuTheoThang();

    /**
     * Lấy tổng doanh thu toàn bộ.
     *
     * @return Doanh thu
     */
    double getTongDoanhThu();

    /**
     * Lấy số lượng hóa đơn đã thanh toán.
     *
     * @return Số lượng hóa đơn
     */
    int getSoHoaDon();

    double layDoanhThu(int month, int year);

    int getSoHoaDonDaHuy();

    int getTongSoKhachHang();

    double getDoanhThuTheoKhoangThoiGian(Date fromDate, Date toDate);

    int getSoHoaDonTheoKhoangThoiGian(Date fromDate, Date toDate);

    int getSoHoaDonHuyTheoKhoangThoiGian(Date fromDate, Date toDate);
}
