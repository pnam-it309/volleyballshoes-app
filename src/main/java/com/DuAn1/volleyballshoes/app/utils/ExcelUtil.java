package com.DuAn1.volleyballshoes.app.utils;

import com.DuAn1.volleyballshoes.app.entity.Product;
import com.DuAn1.volleyballshoes.app.entity.ProductVariant;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

/**
 * ExcelUtil – Import/Export Excel cho Product & ProductVariant
 *
 * File Excel (template) gồm các cột theo đúng thứ tự dưới đây:
 *   0  - product_name           (String)
 *   1  - product_desc           (String)
 *   2  - brand_id               (int)
 *   3  - category_id            (int)
 *   4  - variant_sku            (String, alphanumeric)
 *   5  - size_id                (int)
 *   6  - color_id               (int)
 *   7  - sole_id                (int)
 *   8  - variant_orig_price     (double)
 *   9  - variant_img_url        (String)
 *
 * Timestamps (product_create_at, product_updated_at) sẽ được set = LocalDateTime.now()
 * khi map sang entity – KHÔNG lấy trong Excel.
 */
public class ExcelUtil {

    /* =========================================================
     * ============= 1) HEADER TEMPLATE (khớp entity) ==========
     * ========================================================= */
    public static final String[] EXPORT_TEMPLATE_HEADERS = {
            "product_name",
            "product_desc",
            "brand_id",
            "category_id",
            "variant_sku",
            "size_id",
            "color_id",
            "sole_id",
            "variant_orig_price",
            "variant_img_url"
    };

    /* =========================================================
     * ============= 2) DTO một dòng import =====================
     * ========================================================= */
    public static class ProductExcelRow {
        // Product fields
        public String productName;
        public String productDesc;
        public int brandId;
        public int categoryId;

        // Variant fields
        public String variantSku;
        public int sizeId;
        public int colorId;
        public int soleId;
        public double variantOrigPrice;
        public String variantImgUrl;

        // Dùng để group các variants theo 1 Product (tên + brand_id + category_id)
        public int productHashKey() {
            return Objects.hash(productName == null ? "" : productName.trim(), brandId, categoryId);
        }
    }

    /* =========================================================
     * ============= 3) KẾT QUẢ MAP RA 2 LIST ==================
     * ========================================================= */
    public static class ImportBundle {
        public final List<Product> products;
        public final List<ProductVariant> variants;

        public ImportBundle(List<Product> products, List<ProductVariant> variants) {
            this.products = products;
            this.variants = variants;
        }
    }

    /* =========================================================
     * ============= 4) HÀM PUBLIC – BẠN ĐANG BỊ MISSING =======
     * ========================================================= */

    /**
     * Hàm mà ProductManagement.java đang gọi (theo compile log):
     *  - Đọc file Excel
     *  - Trả về danh sách các dòng (DTO) để bạn đổ thẳng lên JTable.
     */
    public List<ProductExcelRow> importProductExcel(String filePath) throws IOException {
        return readExcelToRows(filePath);
    }

    /**
     * Xuất template đúng các trường (không có thời gian) – bạn nói là đã đủ
     * nên mình giữ nguyên, không thêm field nào khác.
     */
    public void exportProductTemplate(File outFile) throws IOException {
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("template");

        // Header
        Row header = sheet.createRow(0);
        for (int i = 0; i < EXPORT_TEMPLATE_HEADERS.length; i++) {
            Cell c = header.createCell(i, CellType.STRING);
            c.setCellValue(EXPORT_TEMPLATE_HEADERS[i]);
            sheet.autoSizeColumn(i);
        }

        try (FileOutputStream fos = new FileOutputStream(outFile)) {
            wb.write(fos);
        }
        wb.close();
    }

    /**
     * Trả về DefaultTableModel để bạn set trực tiếp cho JTable (nếu muốn).
     */
    public DefaultTableModel toTableModel(List<ProductExcelRow> rows) {
        DefaultTableModel model = new DefaultTableModel(EXPORT_TEMPLATE_HEADERS, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        for (ProductExcelRow r : rows) {
            model.addRow(new Object[]{
                    r.productName,
                    r.productDesc,
                    r.brandId,
                    r.categoryId,
                    r.variantSku,
                    r.sizeId,
                    r.colorId,
                    r.soleId,
                    r.variantOrigPrice,
                    r.variantImgUrl
            });
        }
        return model;
    }

    /**
     * Map danh sách ProductExcelRow (đã đọc từ Excel) -> 2 list entity:
     *  - Product (unique theo productHashKey)
     *  - ProductVariant (nhiều dòng theo từng product)
     *
     * Bạn có thể dùng bundle này để insert DB:
     *   1) Insert Product trước -> lấy product_id
     *   2) Gắn product_id vào ProductVariant -> insert variant
     */
    public ImportBundle toImportBundle(List<ProductExcelRow> rows) {
        Map<Integer, Product> productMap = new LinkedHashMap<>();
        List<ProductVariant> variants = new ArrayList<>();

        for (ProductExcelRow r : rows) {
            int key = r.productHashKey();

            // Tạo Product nếu chưa có
            Product p = productMap.get(key);
            if (p == null) {
                p = new Product();
                // ====== Map đúng tên field từ Product.java của bạn ======
                // TODO: nếu tên field/getter/setter trong Product khác, sửa lại phần này
                p.setBrand_id(r.brandId);
                p.setCategory_id(r.categoryId);
                p.setProduct_name(r.productName);
                p.setProduct_desc(r.productDesc);
                p.setProduct_create_at(LocalDateTime.now());
                p.setProduct_updated_at(LocalDateTime.now());
                productMap.put(key, p);
            }

            // Tạo ProductVariant
            ProductVariant v = new ProductVariant();
            // ====== Map đúng tên field từ ProductVariant.java của bạn ======
            // TODO: nếu tên field/getter/setter trong ProductVariant khác, sửa lại phần này
            v.setVariant_sku(r.variantSku);
            v.setSize_id(r.sizeId);
            v.setColor_id(r.colorId);
            v.setSole_id(r.soleId);
            v.setVariant_orig_price(r.variantOrigPrice);
            v.setVariant_img_url(r.variantImgUrl);

            // product_id sẽ được set sau khi bạn insert Product và lấy ra id.
            // Ở đây mình chưa có id, nên tạm thời để 0 hoặc bỏ qua.
            v.setProduct_id(0);

            variants.add(v);
        }

        return new ImportBundle(new ArrayList<>(productMap.values()), variants);
    }

    /* =========================================================
     * ============= 5) HÀM ĐỌC EXCEL CỐT LÕI ==================
     * ========================================================= */
    private List<ProductExcelRow> readExcelToRows(String filePath) throws IOException {
        List<ProductExcelRow> rows = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            if (sheet == null) return rows;

            int rowIndex = 0;
            for (Row row : sheet) {
                if (rowIndex == 0) { // bỏ header
                    rowIndex++;
                    continue;
                }

                ProductExcelRow r = new ProductExcelRow();
                r.productName        = getCellStringValue(row, 0);
                r.productDesc        = getCellStringValue(row, 1);
                r.brandId            = (int) parseDoubleSafe(getCellStringValue(row, 2)); // Excel có thể là text
                r.categoryId         = (int) parseDoubleSafe(getCellStringValue(row, 3));

                r.variantSku         = getCellStringValue(row, 4);
                r.sizeId             = (int) parseDoubleSafe(getCellStringValue(row, 5));
                r.colorId            = (int) parseDoubleSafe(getCellStringValue(row, 6));
                r.soleId             = (int) parseDoubleSafe(getCellStringValue(row, 7));
                r.variantOrigPrice   = parseDoubleSafe(getCellStringValue(row, 8));
                r.variantImgUrl      = getCellStringValue(row, 9);

                rows.add(r);
                rowIndex++;
            }
        }
        return rows;
    }

    /* =========================================================
     * ============= 6) HELPERS – SAFE PARSER ==================
     * ========================================================= */
    public static int parseIntSafe(String value) {
        try {
            if (value == null) return 0;
            return Integer.parseInt(value.trim());
        } catch (Exception e) {
            return 0;
        }
    }

    public static double parseDoubleSafe(String value) {
        try {
            if (value == null || value.trim().isEmpty()) return 0d;
            return Double.parseDouble(value.trim());
        } catch (Exception e) {
            return 0d;
        }
    }

    private String getCellStringValue(Row row, int colIdx) {
        Cell cell = row.getCell(colIdx);
        if (cell == null) return "";
        DataFormatter fmt = new DataFormatter();
        return fmt.formatCellValue(cell).trim();
    }
}
