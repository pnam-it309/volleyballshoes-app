package com.DuAn1.volleyballshoes.app.controller;

import com.DuAn1.volleyballshoes.app.dao.ThongKeDao;
import com.DuAn1.volleyballshoes.app.dao.impl.ThongKeDaoImpl;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

public class ThongKeController {
    private final ThongKeDao thongKeDao;
    private final NumberFormat currencyFormat;
    
    public ThongKeController() {
        this.thongKeDao = new ThongKeDaoImpl();
        this.currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
    }
    
    public String getTongDoanhThuFormatted() {
        return currencyFormat.format(thongKeDao.getTongDoanhThu());
    }
    
    public String getDoanhThuTheoKhoangThoiGianFormatted(Date fromDate, Date toDate) {
        return currencyFormat.format(getDoanhThuTheoKhoangThoiGian(fromDate, toDate));
    }
    
    public int getSoHoaDonDaThanhToan() {
        return thongKeDao.getSoHoaDon();
    }
    
    public int getSoHoaDonDaHuy() {
        return thongKeDao.getSoHoaDonDaHuy();
    }
    
    public int getTongSoKhachHang() {
        return thongKeDao.getTongSoKhachHang();
    }
    
    public double getDoanhThuTheoKhoangThoiGian(Date fromDate, Date toDate) {
        return thongKeDao.getDoanhThuTheoKhoangThoiGian(fromDate, toDate);
    }
    
    public int getSoHoaDonTheoKhoangThoiGian(Date fromDate, Date toDate) {
        return thongKeDao.getSoHoaDonTheoKhoangThoiGian(fromDate, toDate);
    }
    
    public int getSoHoaDonHuyTheoKhoangThoiGian(Date fromDate, Date toDate) {
        return thongKeDao.getSoHoaDonHuyTheoKhoangThoiGian(fromDate, toDate);
    }
    
    public double layDoanhThu(int month, int year) {
        return thongKeDao.layDoanhThu(month, year);
    }
    
    public String formatCurrency(double amount) {
        return currencyFormat.format(amount);
    }
}
