package com.DuAn1.volleyballshoes.app.utils;

import com.DuAn1.volleyballshoes.app.dto.response.OrderDetailResponse;
import com.DuAn1.volleyballshoes.app.dto.response.OrderResponse;
import com.DuAn1.volleyballshoes.app.dto.response.OrderWithDetailsResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExcelUtil {

    public static void exportAllOrdersToExcel(List<OrderWithDetailsResponse> orders, String filePath) {
        try (Workbook workbook = new XSSFWorkbook()) {
            // Create styles
            CellStyle headerStyle = createHeaderStyle(workbook);
            CellStyle titleStyle = createTitleStyle(workbook);
            CellStyle currencyStyle = createCurrencyStyle(workbook);
            CellStyle dateStyle = createDateStyle(workbook);
            
            // Create main sheet
            Sheet sheet = workbook.createSheet("Danh sách hóa đơn");
            
            // Set column widths
            sheet.setColumnWidth(0, 15 * 256);   // Mã HĐ
            sheet.setColumnWidth(1, 25 * 256);   // Khách hàng
            sheet.setColumnWidth(2, 20 * 256);   // Nhân viên
            sheet.setColumnWidth(3, 15 * 256);   // Tổng tiền
            sheet.setColumnWidth(4, 20 * 256);   // Thanh toán
            sheet.setColumnWidth(5, 15 * 256);   // Trạng thái
            sheet.setColumnWidth(6, 20 * 256);   // Ngày tạo
            
            // Title row
            Row titleRow = sheet.createRow(0);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue("DANH SÁCH HÓA ĐƠN");
            titleCell.setCellStyle(titleStyle);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));
            
            // Header row
            Row headerRow = sheet.createRow(1);
            String[] headers = {"Mã HĐ", "Khách hàng", "Nhân viên", "Tổng tiền", "Thanh toán", "Trạng thái", "Ngày tạo"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }
            
            // Data rows
            int rowIdx = 2;
            for (OrderWithDetailsResponse orderWithDetails : orders) {
                OrderResponse order = orderWithDetails.getOrder();
                if (order == null) continue;
                
                Row row = sheet.createRow(rowIdx++);
                
                // Order code
                row.createCell(0).setCellValue(order.getOrderCode());
                
                // Customer name (or ID if name not available)
                String customerInfo = order.getCustomerName() != null ? 
                    order.getCustomerName() : 
                    (order.getCustomerId() != null ? "KH" + order.getCustomerId() : "Khách lẻ");
                row.createCell(1).setCellValue(customerInfo);
                
                // Staff name (or ID if name not available)
                String staffInfo = order.getStaffName() != null ? 
                    order.getStaffName() : 
                    (order.getStaffId() != null ? "NV" + order.getStaffId() : "N/A");
                row.createCell(2).setCellValue(staffInfo);
                
                // Total amount
                if (order.getFinalAmount() != null) {
                    Cell amountCell = row.createCell(3);
                    amountCell.setCellValue(order.getFinalAmount().doubleValue());
                    amountCell.setCellStyle(currencyStyle);
                }
                
                // Payment method
                row.createCell(4).setCellValue(order.getPaymentMethod() != null ? order.getPaymentMethod() : "");
                
                // Status
                row.createCell(5).setCellValue(order.getStatus() != null ? order.getStatus() : "");
                
                // Created at
                if (order.getCreatedAt() != null) {
                    Cell dateCell = row.createCell(6);
                    dateCell.setCellValue(order.getCreatedAt().toString());
                    dateCell.setCellStyle(dateStyle);
                }
                
                // Add order details as hidden rows
                if (orderWithDetails.getOrderDetails() != null && !orderWithDetails.getOrderDetails().isEmpty()) {
                    // Add details header
                    Row detailHeaderRow = sheet.createRow(rowIdx++);
                    Cell detailHeaderCell = detailHeaderRow.createCell(0);
                    detailHeaderCell.setCellValue("Chi tiết đơn hàng:");
                    detailHeaderCell.setCellStyle(headerStyle);
                    sheet.addMergedRegion(new CellRangeAddress(rowIdx-1, rowIdx-1, 0, 6));
                    
                    // Add detail headers
                    Row detailSubHeaderRow = sheet.createRow(rowIdx++);
                    String[] detailHeaders = {"STT", "Sản phẩm", "Màu sắc", "Kích cỡ", "Số lượng", "Đơn giá", "Thành tiền"};
                    for (int i = 0; i < detailHeaders.length; i++) {
                        Cell cell = detailSubHeaderRow.createCell(i);
                        cell.setCellValue(detailHeaders[i]);
                        cell.setCellStyle(headerStyle);
                    }
                    
                    // Add detail rows
                    int detailNum = 1;
                    for (OrderDetailResponse detail : orderWithDetails.getOrderDetails()) {
                        Row detailRow = sheet.createRow(rowIdx++);
                        
                        // STT
                        detailRow.createCell(0).setCellValue(detailNum++);
                        
                        // Product name
                        detailRow.createCell(1).setCellValue(detail.getProductName() != null ? detail.getProductName() : "");
                        
                        // Color
                        detailRow.createCell(2).setCellValue(detail.getColorName() != null ? detail.getColorName() : "");
                        
                        // Size
                        detailRow.createCell(3).setCellValue(detail.getSizeName() != null ? detail.getSizeName() : "");
                        
                        // Quantity
                        detailRow.createCell(4).setCellValue(detail.getQuantity());
                        
                        // Unit price
                        if (detail.getUnitPrice() != null) {
                            Cell priceCell = detailRow.createCell(5);
                            priceCell.setCellValue(detail.getUnitPrice().doubleValue());
                            priceCell.setCellStyle(currencyStyle);
                        }
                        
                        // Total price
                        if (detail.getTotalPrice() != null) {
                            Cell totalCell = detailRow.createCell(6);
                            totalCell.setCellValue(detail.getTotalPrice().doubleValue());
                            totalCell.setCellStyle(currencyStyle);
                        } else if (detail.getUnitPrice() != null) {
                            Cell totalCell = detailRow.createCell(6);
                            double total = detail.getUnitPrice().doubleValue() * detail.getQuantity();
                            totalCell.setCellValue(total);
                            totalCell.setCellStyle(currencyStyle);
                        }
                    }
                    
                    // Add empty row after details
                    rowIdx++;
                }
            }
            
            // Auto-size all columns
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }
            
            // Write the output to a file
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi xuất file Excel: " + e.getMessage(), e);
        }
    }
    
    private static CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 11);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        return style;
    }
    
    private static CellStyle createTitleStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 14);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }
    
    private static CellStyle createCurrencyStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setDataFormat(workbook.createDataFormat().getFormat("#,##0"));
        return style;
    }
    
    private static CellStyle createDateStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setDataFormat(workbook.createDataFormat().getFormat("dd/MM/yyyy HH:mm"));
        return style;
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
