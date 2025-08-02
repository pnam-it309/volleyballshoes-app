/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DuAn1.volleyballshoes.app.utils;

import com.DuAn1.volleyballshoes.app.dto.response.OrderResponse;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.FileOutputStream;
import java.util.List;
import java.util.stream.Stream;

/**
 *
 * @author nickh
 */
public class PDFUtil {

    public static void exportOrdersToPDF(List<OrderResponse> orders, String filePath) {
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
                table.addCell(order.getOrderCode());
                table.addCell(order.getCustomerId().toString());
                table.addCell(order.getStaffId().toString());
                table.addCell(order.getFinalAmount().toString());
                table.addCell(order.getPaymentMethod());
                table.addCell(order.getStatus());
                table.addCell(order.getCreatedAt().toString());
            }

            document.add(table);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }
}
