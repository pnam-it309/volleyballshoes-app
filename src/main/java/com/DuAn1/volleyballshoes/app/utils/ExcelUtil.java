package com.DuAn1.volleyballshoes.app.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class ExcelUtil {
    // Tạo file Excel mẫu cho sản phẩm
    public static void exportProductTemplate(String filePath) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("SanPham");
        Row header = sheet.createRow(0);
        String[] columns = {
            "Tên SP", "Mã Sản Phẩm", "Mô tả", "Giá", "Loại", "Đế giày", "Size", "Màu", "Thương hiệu"
        };
        for (int i = 0; i < columns.length; i++) {
            Cell cell = header.createCell(i);
            cell.setCellValue(columns[i]);
        }
        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }
        try (FileOutputStream fos = new FileOutputStream(new File(filePath))) {
            workbook.write(fos);
        }
        workbook.close();
    }
    /**
     * Đọc file Excel và trả về danh sách các dòng dữ liệu dạng Map<String, String>
     * Mỗi map là một sản phẩm, key là tên cột trong file mẫu
     */
    public static List<Map<String, String>> importProductExcel(String filePath) throws Exception {
        List<Map<String, String>> rows = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            List<String> headers = new ArrayList<>();
            if (rowIterator.hasNext()) {
                Row headerRow = rowIterator.next();
                for (Cell cell : headerRow) {
                    headers.add(cell.getStringCellValue().trim());
                }
            }
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Map<String, String> map = new LinkedHashMap<>();
                boolean isEmpty = true;
                for (int i = 0; i < headers.size(); i++) {
                    Cell cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    String value = "";
                    switch (cell.getCellType()) {
                        case STRING:
                            value = cell.getStringCellValue().trim();
                            break;
                        case NUMERIC:
                            if (DateUtil.isCellDateFormatted(cell)) {
                                value = cell.getDateCellValue().toString();
                            } else {
                                value = String.valueOf(cell.getNumericCellValue());
                            }
                            break;
                        case BOOLEAN:
                            value = String.valueOf(cell.getBooleanCellValue());
                            break;
                        case FORMULA:
                            try {
                                value = cell.getStringCellValue();
                            } catch (Exception e) {
                                value = String.valueOf(cell.getNumericCellValue());
                            }
                            break;
                        default:
                            value = "";
                    }
                    if (!value.isEmpty()) isEmpty = false;
                    map.put(headers.get(i), value);
                }
                if (!isEmpty) {
                    rows.add(map);
                }
            }
        }
        return rows;
    }
}
