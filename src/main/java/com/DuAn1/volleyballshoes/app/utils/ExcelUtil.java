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
        // Xuất hóa đơn chi tiết theo mẫu yêu cầu
    public static void exportBillToExcel(com.DuAn1.volleyballshoes.app.dto.response.BillResponse bill, String filePath) {
        try (org.apache.poi.ss.usermodel.Workbook workbook = new org.apache.poi.xssf.usermodel.XSSFWorkbook()) {
            org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet("HoaDon");
            int rowIdx = 0;

            // 1. Thông tin cửa hàng
            org.apache.poi.ss.usermodel.Row shopRow = sheet.createRow(rowIdx++);
            shopRow.createCell(0).setCellValue("VSHOESAPP - Hệ thống bán giày bóng chuyền chính hãng");
            rowIdx++;

            // 2. Tiêu đề hóa đơn
            org.apache.poi.ss.usermodel.Row titleRow = sheet.createRow(rowIdx++);
            org.apache.poi.ss.usermodel.Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue("HÓA ĐƠN BÁN HÀNG");
            rowIdx++;

            // 3. Thông tin hóa đơn
            org.apache.poi.ss.usermodel.Row infoRow1 = sheet.createRow(rowIdx++);
            infoRow1.createCell(0).setCellValue("Mã hóa đơn:");
            infoRow1.createCell(1).setCellValue(bill.getId() != null ? bill.getId().toString() : "");
            infoRow1.createCell(3).setCellValue("Ngày lập:");
            infoRow1.createCell(4).setCellValue(bill.getOrderDate() != null ? bill.getOrderDate().toString() : "");

            org.apache.poi.ss.usermodel.Row infoRow2 = sheet.createRow(rowIdx++);
            infoRow2.createCell(0).setCellValue("Nhân viên bán hàng:");
            infoRow2.createCell(1).setCellValue(bill.getStaffName() != null ? bill.getStaffName() : "");
            infoRow2.createCell(3).setCellValue("Khách hàng:");
            infoRow2.createCell(4).setCellValue(bill.getCustomerName() != null ? bill.getCustomerName() : "");

            org.apache.poi.ss.usermodel.Row infoRow3 = sheet.createRow(rowIdx++);
            infoRow3.createCell(0).setCellValue("Trạng thái:");
            infoRow3.createCell(1).setCellValue(bill.getStatus() != null ? bill.getStatus() : "");

            rowIdx++;

            // 4. Bảng sản phẩm
            org.apache.poi.ss.usermodel.Row productHeader = sheet.createRow(rowIdx++);
            String[] headers = {"STT", "Tên SP", "Màu", "Size", "SL", "Đơn giá", "Thành tiền"};
            for (int i = 0; i < headers.length; i++) {
                productHeader.createCell(i).setCellValue(headers[i]);
            }
            int stt = 1;
            if (bill.getOrderDetails() != null) {
                for (com.DuAn1.volleyballshoes.app.dto.response.OrderDetailResponse detail : bill.getOrderDetails()) {
                    org.apache.poi.ss.usermodel.Row row = sheet.createRow(rowIdx++);
                    row.createCell(0).setCellValue(stt++);
                    row.createCell(1).setCellValue(detail.getProductName() != null ? detail.getProductName() : "");
                    row.createCell(2).setCellValue(detail.getColorName() != null ? detail.getColorName() : "");
                    row.createCell(3).setCellValue(detail.getSizeName() != null ? detail.getSizeName() : "");
                    row.createCell(4).setCellValue(detail.getQuantity());
                    row.createCell(5).setCellValue(detail.getUnitPrice() != null ? detail.getUnitPrice().doubleValue() : 0);
                    row.createCell(6).setCellValue(detail.getTotalPrice() != null ? detail.getTotalPrice().doubleValue() : 0);
                }
            }
            rowIdx++;

            // 5. Tổng kết hóa đơn
            org.apache.poi.ss.usermodel.Row sumRow = sheet.createRow(rowIdx++);
            sumRow.createCell(5).setCellValue("Tổng cộng:");
            sumRow.createCell(6).setCellValue(bill.getTotalAmount() != null ? bill.getTotalAmount().doubleValue() : 0);

            // 6. Lời cảm ơn
            rowIdx++;
            org.apache.poi.ss.usermodel.Row thankRow = sheet.createRow(rowIdx++);
            thankRow.createCell(0).setCellValue("Cảm ơn quý khách đã mua hàng tại VSHOESAPP! Đổi trả trong 7 ngày với sản phẩm còn nguyên tem mác.");

            // Auto-size columns
            for (int i = 0; i < 7; i++) {
                sheet.autoSizeColumn(i);
            }

            try (java.io.FileOutputStream fileOut = new java.io.FileOutputStream(filePath)) {
                workbook.write(fileOut);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
