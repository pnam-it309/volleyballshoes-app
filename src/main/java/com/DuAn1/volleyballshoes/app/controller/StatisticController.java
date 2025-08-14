package com.DuAn1.volleyballshoes.app.controller;

import com.DuAn1.volleyballshoes.app.dao.ThongKeDao;

import com.DuAn1.volleyballshoes.app.dao.impl.ThongKeDaoImpl;
import java.util.List;
import java.util.Map;

public class StatisticController {

    private final ThongKeDao thongKeDao;

    public StatisticController(ThongKeDao thongKeDao) {
        this.thongKeDao = thongKeDao;
    }

    // Lấy doanh thu theo từng tháng
    public List<Map<String, Object>> getDoanhThuTheoThang() {
        return thongKeDao.getDoanhThuTheoThang();
    }

    // Lấy tổng doanh thu
    public double getTongDoanhThu() {
        return thongKeDao.getTongDoanhThu();
    }

    // Lấy số hóa đơn
    public int getSoHoaDon() {
        return thongKeDao.getSoHoaDon();
    }
}
