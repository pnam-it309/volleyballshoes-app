package com.DuAn1.volleyballshoes.app.utils;

import com.DuAn1.volleyballshoes.app.dto.response.OrderResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.util.List;

public class ExcelUtil {

    public static void exportOrdersToExcel(List<OrderResponse> orders, String filePath) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Orders");
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Mã Hóa Đơn");
            header.createCell(1).setCellValue("Mã Khách Hàng");
            header.createCell(2).setCellValue("Mã Nhân Viên");
            header.createCell(3).setCellValue("Tổng Tiền");
            header.createCell(4).setCellValue("Thanh Toán");
            header.createCell(5).setCellValue("Trạng Thái");
            header.createCell(6).setCellValue("Ngày Tạo");

            int rowIdx = 1;
            for (OrderResponse order : orders) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(order.getOrderCode());
                row.createCell(1).setCellValue(order.getCustomerId());
                row.createCell(2).setCellValue(order.getStaffId());
                row.createCell(3).setCellValue(order.getFinalAmount().doubleValue());
                row.createCell(4).setCellValue(order.getPaymentMethod());
                row.createCell(5).setCellValue(order.getStatus());
                row.createCell(6).setCellValue(order.getCreatedAt().toString());
            }

            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
