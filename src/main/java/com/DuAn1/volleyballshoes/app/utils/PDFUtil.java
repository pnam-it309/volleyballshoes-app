/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DuAn1.volleyballshoes.app.utils;

import com.DuAn1.volleyballshoes.app.dto.response.OrderDetailResponse;
import com.DuAn1.volleyballshoes.app.dto.response.OrderResponse;
import com.DuAn1.volleyballshoes.app.dto.response.OrderWithDetailsResponse;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

public class PDFUtil {
    
    private static final Font TITLE_FONT = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
    private static final Font HEADER_FONT = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
    private static final Font NORMAL_FONT = new Font(Font.FontFamily.HELVETICA, 10);
    private static final Font BOLD_FONT = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
    private static final NumberFormat CURRENCY_FORMAT = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public static void exportOrderToPDF(OrderWithDetailsResponse orderWithDetails, String filePath) throws Exception {
        Document document = new Document(PageSize.A4);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();
        
        try {
            OrderResponse order = orderWithDetails.getOrder();
            // Add header
            addHeader(document, order);
            
            // Add customer info
            addCustomerInfo(document, order);
            
            // Add order items
            addOrderItems(document, orderWithDetails.getOrderDetails());
            
            // Add totals - pass the full orderWithDetails to access both order and details
            addOrderTotals(document, orderWithDetails);
            
            // Add footer
            addFooter(document);
            
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            document.close();
        }
    }
    
    private static void addHeader(Document document, OrderResponse order) throws DocumentException {
        // Logo and shop info
        PdfPTable headerTable = new PdfPTable(2);
        headerTable.setWidthPercentage(100);
        headerTable.setWidths(new float[]{1, 2});
        
        // Left cell - Logo (placeholder)
        PdfPCell logoCell = new PdfPCell(new Phrase("LOGO", new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD)));
        logoCell.setBorder(Rectangle.NO_BORDER);
        logoCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable.addCell(logoCell);
        
        // Right cell - Shop info
        PdfPCell infoCell = new PdfPCell();
        infoCell.setBorder(Rectangle.NO_BORDER);
        infoCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        
        Paragraph shopName = new Paragraph("CỬA HÀNG GIÀY BÓNG CHUYỀN", TITLE_FONT);
        shopName.setAlignment(Element.ALIGN_RIGHT);
        
        Paragraph address = new Paragraph("123 Đường ABC, Quận XYZ, TP.HCM", NORMAL_FONT);
        address.setAlignment(Element.ALIGN_RIGHT);
        
        Paragraph phone = new Paragraph("ĐT: 0909 123 456", NORMAL_FONT);
        phone.setAlignment(Element.ALIGN_RIGHT);
        
        infoCell.addElement(shopName);
        infoCell.addElement(address);
        infoCell.addElement(phone);
        headerTable.addCell(infoCell);
        
        document.add(headerTable);
        
        // Title
        Paragraph title = new Paragraph("HÓA ĐƠN BÁN HÀNG", TITLE_FONT);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);
        
        // Order info
        PdfPTable infoTable = new PdfPTable(2);
        infoTable.setWidthPercentage(100);
        infoTable.setWidths(new float[]{1, 3});
        
        addInfoRow(infoTable, "Số hóa đơn:", order.getOrderCode());
        addInfoRow(infoTable, "Ngày tạo:", DATE_FORMAT.format(order.getCreatedAt()));
        addInfoRow(infoTable, "Nhân viên:", order.getStaffName() != null ? order.getStaffName() : "N/A");
        addInfoRow(infoTable, "Phương thức thanh toán:", order.getPaymentMethod());
        addInfoRow(infoTable, "Trạng thái:", order.getStatus());
        
        document.add(infoTable);
        document.add(new Paragraph("\n"));
    }
    
    private static void addCustomerInfo(Document document, OrderResponse order) throws DocumentException {
        PdfPTable customerTable = new PdfPTable(2);
        customerTable.setWidthPercentage(100);
        customerTable.setWidths(new float[]{1, 3});
        
        PdfPCell customerHeader = new PdfPCell(new Phrase("THÔNG TIN KHÁCH HÀNG", BOLD_FONT));
        customerHeader.setColspan(2);
        customerHeader.setBackgroundColor(new BaseColor(220, 220, 220));
        customerTable.addCell(customerHeader);
        
        addInfoRow(customerTable, "Tên khách hàng:", order.getCustomerName() != null ? order.getCustomerName() : "Khách lẻ");
        
        document.add(customerTable);
        document.add(new Paragraph("\n"));
    }
    
    private static void addOrderItems(Document document, List<OrderDetailResponse> items) throws DocumentException {
        if (items == null || items.isEmpty()) {
            document.add(new Paragraph("Không có sản phẩm nào trong đơn hàng."));
            return;
        }

        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{0.5f, 4f, 1.5f, 1f, 1.5f, 2f});
        
        // Table header
        String[] headers = {"STT", "Tên sản phẩm (Màu sắc, Kích cỡ)", "Đơn giá", "SL", "Giảm giá", "Thành tiền"};
        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header, BOLD_FONT));
            cell.setBackgroundColor(new BaseColor(220, 220, 220));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(5);
            table.addCell(cell);
        }
        
        // Table rows
        int rowNum = 1;
        for (OrderDetailResponse item : items) {
            // STT
            table.addCell(createCell(String.valueOf(rowNum++), Element.ALIGN_CENTER));
            
            // Product name with color and size
            String productInfo = item.getProductName();
            if (item.getColorName() != null || item.getSizeName() != null) {
                productInfo += "\n";
                if (item.getColorName() != null) {
                    productInfo += "Màu: " + item.getColorName();
                }
                if (item.getSizeName() != null) {
                    if (item.getColorName() != null) productInfo += ", ";
                    productInfo += "Size: " + item.getSizeName();
                }
            }
            table.addCell(createCell(productInfo));
            
            // Unit price
            table.addCell(createCell(formatCurrency(item.getUnitPrice()), Element.ALIGN_RIGHT));
            
            // Quantity
            table.addCell(createCell(String.valueOf(item.getQuantity()), Element.ALIGN_CENTER));
            
            // Discount
            String discountText = item.getDiscountPercent() != null && item.getDiscountPercent().compareTo(BigDecimal.ZERO) > 0 
                ? String.format("%.0f%%", item.getDiscountPercent()) 
                : "0%";
            table.addCell(createCell(discountText, Element.ALIGN_CENTER));
            
            // Total price
            BigDecimal totalPrice = item.getTotalPrice() != null 
                ? item.getTotalPrice() 
                : item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            table.addCell(createCell(formatCurrency(totalPrice), Element.ALIGN_RIGHT));
        }
        
        document.add(table);
        document.add(Chunk.NEWLINE);
    }
    
    private static void addOrderTotals(Document document, OrderWithDetailsResponse orderWithDetails) throws DocumentException {
        // Create a table for the order totals
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{70, 30});
        
        OrderResponse order = orderWithDetails.getOrder();
        List<OrderDetailResponse> orderDetails = orderWithDetails.getOrderDetails();
        
        // Calculate subtotal from order items
        BigDecimal subtotal = BigDecimal.ZERO;
        BigDecimal totalDiscount = BigDecimal.ZERO;
        
        if (orderDetails != null) {
            for (OrderDetailResponse detail : orderDetails) {
                // Calculate item total without discount
                BigDecimal itemTotal = detail.getUnitPrice().multiply(BigDecimal.valueOf(detail.getQuantity()));
                subtotal = subtotal.add(itemTotal);
                
                // Calculate discount if applicable
                if (detail.getDiscountPercent() != null && detail.getDiscountPercent().compareTo(BigDecimal.ZERO) > 0) {
                    BigDecimal itemDiscount = detail.getUnitPrice()
                            .multiply(detail.getDiscountPercent())
                            .divide(new BigDecimal(100))
                            .multiply(new BigDecimal(detail.getQuantity()));
                    totalDiscount = totalDiscount.add(itemDiscount);
                }
            }
        }
        
        // Add subtotal row
        addTotalRow(table, "Tổng tiền hàng:", subtotal);
        
        // Add discount row if applicable
        if (totalDiscount.compareTo(BigDecimal.ZERO) > 0) {
            addTotalRow(table, "Giảm giá:", totalDiscount.negate());
        }
        
        // Calculate final total
        BigDecimal finalTotal = subtotal.subtract(totalDiscount);
        
        // Add total row with top border
        PdfPCell totalLabelCell = new PdfPCell(new Phrase("THÀNH TIỀN:", BOLD_FONT));
        totalLabelCell.setBorder(Rectangle.NO_BORDER);
        totalLabelCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        totalLabelCell.setPadding(5);
        table.addCell(totalLabelCell);
        
        PdfPCell totalValueCell = new PdfPCell(new Phrase(formatCurrency(finalTotal), BOLD_FONT));
        totalValueCell.setBorder(Rectangle.TOP);
        totalValueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        totalValueCell.setPadding(5);
        table.addCell(totalValueCell);
        
        // Add payment method if available
        if (order.getPaymentMethod() != null && !order.getPaymentMethod().isEmpty()) {
            PdfPCell paymentLabelCell = new PdfPCell(new Phrase("Phương thức thanh toán:", BOLD_FONT));
            paymentLabelCell.setBorder(Rectangle.NO_BORDER);
            paymentLabelCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            paymentLabelCell.setPadding(5);
            table.addCell(paymentLabelCell);
            
            PdfPCell paymentValueCell = new PdfPCell(new Phrase(order.getPaymentMethod(), NORMAL_FONT));
            paymentValueCell.setBorder(Rectangle.NO_BORDER);
            paymentValueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            paymentValueCell.setPadding(5);
            table.addCell(paymentValueCell);
        }
        
        document.add(table);
    }
    
    private static void addFooter(Document document) throws DocumentException {
        Paragraph thanks = new Paragraph("Cảm ơn quý khách đã mua hàng!", new Font(Font.FontFamily.HELVETICA, 12, Font.ITALIC));
        thanks.setAlignment(Element.ALIGN_CENTER);
        thanks.setSpacingBefore(30);
        document.add(thanks);
        
        Paragraph signature = new Paragraph("Người lập hóa đơn", NORMAL_FONT);
        signature.setAlignment(Element.ALIGN_RIGHT);
        signature.setSpacingBefore(50);
        document.add(signature);
    }
    
    // Helper methods
    private static void addInfoRow(PdfPTable table, String label, String value) {
        table.addCell(createCell(label, Element.ALIGN_LEFT, BOLD_FONT));
        table.addCell(createCell(value, Element.ALIGN_LEFT));
    }
    
    private static void addTotalRow(PdfPTable table, String label, java.math.BigDecimal amount) {
        table.addCell(createCell(label, Element.ALIGN_RIGHT, NORMAL_FONT));
        table.addCell(createCell(formatCurrency(amount), Element.ALIGN_RIGHT, NORMAL_FONT));
    }
    
    private static PdfPCell createCell(String content) {
        return createCell(content, Element.ALIGN_LEFT, NORMAL_FONT);
    }
    
    private static PdfPCell createCell(String content, int alignment) {
        return createCell(content, alignment, NORMAL_FONT);
    }
    
    private static PdfPCell createCell(String content, int alignment, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(alignment);
        cell.setPadding(5);
        return cell;
    }
    
    private static String formatCurrency(java.math.BigDecimal amount) {
        return CURRENCY_FORMAT.format(amount);
    }
    
    // Keep the old method for backward compatibility
    public static void exportOrdersToPDF(List<OrderResponse> orders, String filePath) {
        // Implementation remains the same as before
        Document document = new Document(PageSize.A4.rotate());
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
            Paragraph title = new Paragraph("Danh Sách Hóa Đơn", font);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(7);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{2, 2, 2, 2, 2, 2, 3});

            Stream.of("Mã Hóa Đơn", "Mã Khách", "Mã NV", "Tổng Tiền", "Thanh Toán", "Trạng Thái", "Ngày Tạo")
                    .forEach(headerTitle -> {
                        PdfPCell header = new PdfPCell();
                        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        header.setPhrase(new Phrase(headerTitle));
                        table.addCell(header);
                    });

            for (OrderResponse order : orders) {
                table.addCell(order.getOrderCode() != null ? order.getOrderCode() : "");
                table.addCell(order.getCustomerId() != null ? order.getCustomerId().toString() : "N/A");
                table.addCell(order.getStaffId() != null ? order.getStaffId().toString() : "N/A");
                table.addCell(order.getFinalAmount() != null ? order.getFinalAmount().toString() : "0.00");
                table.addCell(order.getPaymentMethod() != null ? order.getPaymentMethod() : "");
                table.addCell(order.getStatus() != null ? order.getStatus() : "");
                table.addCell(order.getCreatedAt() != null ? order.getCreatedAt().toString() : "");
            }

            document.add(table);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }
}
