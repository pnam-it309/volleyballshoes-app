package com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewSanPham;

import com.DuAn1.volleyballshoes.app.controller.*;
import com.DuAn1.volleyballshoes.app.dao.*;
import com.DuAn1.volleyballshoes.app.dao.impl.*;
import com.DuAn1.volleyballshoes.app.dto.request.*;
import com.DuAn1.volleyballshoes.app.dto.response.*;
import com.DuAn1.volleyballshoes.app.entity.*;
import com.DuAn1.volleyballshoes.app.utils.NotificationUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

public final class ViewSanPham extends javax.swing.JPanel {

    private int currentPage = 1;
    private int pageSize = 10;
    private int totalPages = 1;
    private final ProductController productController;
    private final Map<Integer, String> brandIdToName = new HashMap<>();
    private final Map<Integer, String> categoryIdToName = new HashMap<>();
    private final BrandDAO brandDAO = new BrandDAOImpl();
    private final CategoryDAO categoryDAO = new CategoryDAOImpl();
    private DefaultTableModel tableModel;
    private int selectedRow = -1;
    private ProductResponse selectedProduct;
    private final ProductVariantDAO productVariantDAO = new ProductVariantDAOImpl();
    ColorDAO colorDAO = new ColorDAOImpl();
    SoleTypeDAO soleTypeDAO = new SoleTypeDAOImpl();
    SizeDAO sizeDAO = new SizeDAOImpl();
    private String type = "color";
    private long lastClickTime = 0;
    private long lastEditClickTime = 0;
    private long lastDeleteClickTime = 0;
    private static final int DOUBLE_CLICK_DELAY = 300;
    private static ViewSanPham activeInstance;

    private void processQRCodeData(String data) {
        // TODO: Thực hiện xử lý dữ liệu QR code ở đây
        // Process QR data
    }

    private String getCellValueAsString(org.apache.poi.ss.usermodel.Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return "";
            default:
                return "";
        }
    }

    public ViewSanPham() {
        this.productController = new ProductController();
        initComponents();
        loadProductVariantSKUs();
        setupTable();
        loadBrands();
        loadCategories();
        loadData();
        loadProductVariants();
        // Initialize product variants table
        setupProductVariantTable();
        // Load product variants for the first product if available
        if (tblSanPham.getRowCount() > 0) {
            tblSanPham.setRowSelectionInterval(0, 0);
            loadSelectedProductVariants();
        }
        activeInstance = this;
    }

    public static ViewSanPham getActiveInstance() {
        return activeInstance;
    }

    private void setupTable() {
        String[] columns = {"Mã SP", "Tên Sản Phẩm", "Mô Tả", "Thương Hiệu", "Danh Mục"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblSanPham.setModel(tableModel);
    }

    private void loadData() {
        try {
            tableModel.setRowCount(0);
            List<ProductResponse> products = productController.getProductsWithPagination(currentPage, pageSize);

            for (ProductResponse product : products) {
                String brandName = brandIdToName.getOrDefault(product.getBrandId(), String.valueOf(product.getBrandId()));
                String categoryName = categoryIdToName.getOrDefault(product.getCategoryId(), String.valueOf(product.getCategoryId()));
                Object[] row = {
                    product.getProductCode(),
                    product.getProductName(),
                    product.getProductDescription(),
                    brandName,
                    categoryName
                };
                tableModel.addRow(row);
            }

            updatePaginationInfo();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updatePaginationInfo() {
        try {
            int totalPages = productController.getTotalPages(pageSize);
            jlabel5.setText("Trang " + (currentPage + 1) + " / " + totalPages);

            nhoNhat.setEnabled(currentPage > 0);
            nho.setEnabled(currentPage > 0);
            lon.setEnabled(currentPage < totalPages - 1);
            lonNhat.setEnabled(currentPage < totalPages - 1);
        } catch (Exception e) {
            jlabel5.setText("Trang 1 / 1");
        }
    }

    private void clearForm() {
        txtMaSP.setText("");
        txtTenSP.setText("");
        txtaMoTa.setText("");
        if (cbo_brand.getItemCount() > 0) {
            cbo_brand.setSelectedIndex(0);
        }
        if (cbo_category.getItemCount() > 0) {
            cbo_category.setSelectedIndex(0);
        }
        selectedRow = -1;
        selectedProduct = null;
    }

    private void fillForm(ProductResponse product) {
        if (product == null) {
            return;
        }
        txtMaSP.setText(product.getProductCode());
        txtTenSP.setText(product.getProductName());
        txtaMoTa.setText(product.getProductDescription());

        // Set selected brand
        if (product.getBrandId() != null) {
            setSelectedComboItem(cbo_brand, product.getBrandId());
        }

        // Set selected category
        if (product.getCategoryId() != null) {
            setSelectedComboItem(cbo_category, product.getCategoryId());
        }
        selectedProduct = product;
    }

    // Load tất cả mã SKU của product variant vào combobox
    private void loadProductVariantSKUs() {
        javax.swing.DefaultComboBoxModel<String> skuModel = new javax.swing.DefaultComboBoxModel<>();
        try {
            List<ProductVariant> variants = productVariantDAO.findAll();
            for (ProductVariant variant : variants) {
                String sku = variant.getVariantSku();
                if (sku != null && !sku.trim().isEmpty()) {
                    skuModel.addElement(sku);
                }
            }
            if (skuModel.getSize() == 0) {
                skuModel.addElement("Không có SKU");
            }
            cbo_product_variant_sku.setModel(skuModel);
        } catch (Exception e) {
            skuModel.removeAllElements();
            skuModel.addElement("Lỗi tải SKU");
            cbo_product_variant_sku.setModel(skuModel);
            JOptionPane.showMessageDialog(this, "Lỗi khi tải mã SKU: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Helper method to select an item in a combo box by ID
    @SuppressWarnings("unchecked")
    private void setSelectedComboItem(JComboBox<String> comboBox, Integer itemId) {
        if (itemId == null) {
            comboBox.setSelectedIndex(-1);
            return;
        }
        for (int i = 0; i < comboBox.getItemCount(); i++) {
            String itemText = comboBox.getItemAt(i);
            ComboBoxItem item = (ComboBoxItem) comboBox.getClientProperty(itemText);
            if (item != null && item.getId().equals(itemId)) {
                comboBox.setSelectedIndex(i);
                break;
            }
        }
    }

    private ProductCreateRequest getCreateRequest() {
        ProductCreateRequest request = new ProductCreateRequest();
        request.setProductCode(txtMaSP.getText().trim());
        request.setProductName(txtTenSP.getText().trim());
        request.setProductDescription(txtaMoTa.getText().trim());
        if (cbo_brand.getSelectedItem() != null) {
            String selectedBrand = cbo_brand.getSelectedItem().toString();
            ComboBoxItem brandItem = (ComboBoxItem) cbo_brand.getClientProperty(selectedBrand);
            if (brandItem != null) {
                request.setBrandId(brandItem.getId());
            }
        }
        if (cbo_category.getSelectedItem() != null) {
            String selectedCategory = cbo_category.getSelectedItem().toString();
            ComboBoxItem categoryItem = (ComboBoxItem) cbo_category.getClientProperty(selectedCategory);
            if (categoryItem != null) {
                request.setCategoryId(categoryItem.getId());
            }
        }
        return request;
    }

    private ProductUpdateRequest getUpdateRequest() {
        if (selectedProduct == null) {
            return null;
        }
        ProductUpdateRequest request = new ProductUpdateRequest();
        request.setProductId(selectedProduct.getProductId());
        request.setProductCode(txtMaSP.getText().trim());
        request.setProductName(txtTenSP.getText().trim());
        request.setProductDescription(txtaMoTa.getText().trim());
        if (cbo_brand.getSelectedItem() != null) {
            String selectedBrand = cbo_brand.getSelectedItem().toString();
            ComboBoxItem brandItem = (ComboBoxItem) cbo_brand.getClientProperty(selectedBrand);
            if (brandItem != null) {
                request.setBrandId(brandItem.getId());
            }
        }
        if (cbo_category.getSelectedItem() != null) {
            String selectedCategory = cbo_category.getSelectedItem().toString();
            ComboBoxItem categoryItem = (ComboBoxItem) cbo_category.getClientProperty(selectedCategory);
            if (categoryItem != null) {
                request.setCategoryId(categoryItem.getId());
            }
        }
        return request;
    }

    private boolean validateForm() {
        if (txtMaSP.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã sản phẩm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (txtTenSP.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên sản phẩm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (cbo_brand.getSelectedIndex() < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn thương hiệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (cbo_category.getSelectedIndex() < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn danh mục!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void loadBrands() {
        try {
            cbo_brand.removeAllItems();
            brandIdToName.clear();
            List<Brand> brands = brandDAO.findAll();
            for (Brand brand : brands) {
                if (brand != null && brand.getBrandName() != null) {
                    ComboBoxItem item = new ComboBoxItem(brand.getBrandId(), brand.getBrandName());
                    cbo_brand.addItem(item.toString());
                    cbo_brand.putClientProperty(item.toString(), item);
                    brandIdToName.put(brand.getBrandId(), brand.getBrandName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi tải biến thể sản phẩm: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadCategories() {
        try {
            cbo_category.removeAllItems();
            categoryIdToName.clear();
            List<Category> categorys = categoryDAO.findAll();
            for (Category category : categorys) {
                if (category != null && category.getCategoryName() != null) {
                    ComboBoxItem item = new ComboBoxItem(category.getCategoryId(), category.getCategoryName());
                    cbo_category.addItem(item.toString());
                    cbo_category.putClientProperty(item.toString(), item);
                    categoryIdToName.put(category.getCategoryId(), category.getCategoryName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi tải biến thể sản phẩm: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateVariantTableModel(List<ProductVariant> variants) {
        System.out.println("\n=== updateVariantTableModel called ===");
        System.out.println("Input variants list is " + (variants == null ? "null" : "not null"));
        System.out.println("Number of variants to process: " + (variants != null ? variants.size() : 0));

        // Log the actual variants list content
        if (variants != null && !variants.isEmpty()) {
            System.out.println("\n--- Variants List Content ---");
            for (int i = 0; i < variants.size(); i++) {
                ProductVariant v = variants.get(i);
                System.out.println(String.format("[%d] ID: %d, SKU: %s, Qty: %d, Price: %s, SizeID: %d, ColorID: %d, SoleID: %d",
                        i, v.getVariantId(), v.getVariantSku(), v.getVariantquantity(),
                        v.getVariantOrigPrice(), v.getSizeId(), v.getColorId(), v.getSoleId()));
            }
            System.out.println("----------------------------");
        }

        // Get the table model
        DefaultTableModel model = (DefaultTableModel) tblSanPhamCon.getModel();
        System.out.println("\n--- Table Model Info ---");
        System.out.println("Current row count: " + model.getRowCount());
        System.out.println("Column count: " + model.getColumnCount());

        // Log column names
        System.out.println("Column names:");
        for (int i = 0; i < model.getColumnCount(); i++) {
            System.out.println("  " + i + ": " + model.getColumnName(i));
        }

        // Clear existing data
        System.out.println("\nClearing table model...");
        model.setRowCount(0);
        System.out.println("Table model cleared. New row count: " + model.getRowCount());

        if (variants == null || variants.isEmpty()) {
            System.out.println("No variants to display. Exiting updateVariantTableModel.");
            return;
        }

        // Initialize DAOs for lookups
        SizeDAO sizeDAO = new SizeDAOImpl();
        ColorDAO colorDAO = new ColorDAOImpl();
        SoleTypeDAO soleTypeDAO = new SoleTypeDAOImpl();

        System.out.println("\n--- Processing Variants ---");
        int rowCount = 0;

        for (ProductVariant variant : variants) {
            try {
                rowCount++;
                System.out.println("\nProcessing variant " + rowCount + " of " + variants.size());
                System.out.println("Variant ID: " + variant.getVariantId());
                System.out.println("Variant SKU: " + variant.getVariantSku());
                System.out.println("Quantity from variant object: " + variant.getVariantquantity());
                System.out.println("Price from variant object: " + variant.getVariantOrigPrice());

                // Look up related data with null checks
                Size size = variant.getSizeId() > 0 ? sizeDAO.findById(variant.getSizeId()) : null;
                Color color = variant.getColorId() > 0 ? colorDAO.findById(variant.getColorId()) : null;
                SoleType soleType = variant.getSoleId() > 0 ? soleTypeDAO.findById(variant.getSoleId()) : null;

                // Debug information
                System.out.println("Looked up data - Size: " + (size != null ? size.getSizeCode() : "null")
                        + ", Color: " + (color != null ? color.getColorHexCode() : "null")
                        + ", Sole: " + (soleType != null ? soleType.getSoleCode() : "null"));

                // Prepare row data with product name, color and sole type in the first column
                String productName = selectedProduct != null ? selectedProduct.getProductName() : "";
                String colorName = color != null ? color.getColorHexCode() : "";
                String soleTypeName = soleType != null ? soleType.getSoleCode() : "";
                String displayName = String.format("%s - %s - %s",
                        productName,
                        colorName,
                        soleTypeName);

                Object[] rowData = new Object[]{
                    displayName,
                    variant.getVariantSku(),
                    size != null ? size.getSizeCode() : "N/A",
                    color != null ? color.getColorHexCode() : "N/A",
                    soleType != null ? soleType.getSoleCode() : "N/A",
                    formatCurrency(variant.getVariantOrigPrice()),
                    variant.getVariantquantity()
                };

                // Debug: Print the row data before adding
                System.out.println("Adding row with data: " + Arrays.toString(rowData));

                // Add row to model
                model.addRow(rowData);

                // Verify the row was added correctly
                int lastRow = model.getRowCount() - 1;
                Object addedQuantity = model.getValueAt(lastRow, 5); // Quantity is at index 5
                System.out.println("Added row " + lastRow + ". Quantity in table: " + addedQuantity);

            } catch (Exception e) {
                System.err.println("ERROR processing variant " + (variant != null ? variant.getVariantSku() : "null") + ":");
                e.printStackTrace();

                // Log the specific error
                System.err.println("Error details: " + e.getMessage());
                if (e.getCause() != null) {
                    System.err.println("Caused by: " + e.getCause().getMessage());
                }
            }
        }

        System.out.println("\n=== updateVariantTableModel completed ===");
        System.out.println("Total rows added to table: " + model.getRowCount());
        System.out.println("Table model column count: " + model.getColumnCount());
    }

    private void setupProductVariantTable() {

        String[] columns = {
            "Tên Sản Phẩm",
            "Mã SKU",
            "Kích thước",
            "Màu sắc",
            "Loại đế",
            "Giá bán",
            "Số lượng tồn"
        };

        // Set up the table model with the columns
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };

        tblSanPhamCon.setModel(model);

        // Set up column widths and make sure text doesn't get truncated
        tblSanPhamCon.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // Set preferred widths for columns
        TableColumnModel columnModel = tblSanPhamCon.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(300); // Wider column for product name
        columnModel.getColumn(1).setPreferredWidth(100); // SKU
        columnModel.getColumn(2).setPreferredWidth(100);  // Size
        columnModel.getColumn(3).setPreferredWidth(100); // Color
        columnModel.getColumn(4).setPreferredWidth(100); // Sole type
        columnModel.getColumn(5).setPreferredWidth(210); // Price
        columnModel.getColumn(6).setPreferredWidth(100);  // Quantity

        // Enable text wrapping for the first column
        tblSanPhamCon.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel renderer = (JLabel) super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                if (column == 0) { // Only for the product name column
                    renderer.setToolTipText(value != null ? value.toString() : "");
                    // Set the row height to fit wrapped text
                    table.setRowHeight(row, Math.max(20,
                            getPreferredSize().height + 5));
                }
                return renderer;
            }
        });

        // Log the column names
        System.out.println("Table columns (UTF-8):");
        for (int i = 0; i < columns.length; i++) {
            System.out.println("  " + i + ": " + columns[i]);
        }

        tblSanPhamCon.setModel(model);
    }

    private void loadSelectedProductVariants() {
        if (selectedProduct == null) {
            System.out.println("No product selected. Cannot load variants.");
            return;
        }

        try {
            System.out.println("\n=== Loading variants for product ===");
            System.out.println("Product ID: " + selectedProduct.getProductId());
            System.out.println("Product Name: " + selectedProduct.getProductName());
            System.out.println("Product Code: " + selectedProduct.getProductCode());

            // Load variants for the selected product
            System.out.println("Fetching variants from DAO...");
            List<ProductVariant> variants = productVariantDAO.findByProductId(selectedProduct.getProductId());

            System.out.println("DAO returned " + (variants != null ? variants.size() : 0) + " variants");

            // Debug: Print all variant details
            if (variants != null && !variants.isEmpty()) {
                System.out.println("\n=== Variant Details from DAO ===");
                for (ProductVariant v : variants) {
                    System.out.println(String.format("Variant ID: %d, SKU: %s, Qty: %d, Price: %s, SizeID: %d, ColorID: %d, SoleID: %d",
                            v.getVariantId(),
                            v.getVariantSku(),
                            v.getVariantquantity(),
                            v.getVariantOrigPrice(),
                            v.getSizeId(),
                            v.getColorId(),
                            v.getSoleId()
                    ));
                }
            } else {
                System.out.println("No variants found for this product in the database");
            }

            // Update the table model with the variants
            System.out.println("\nUpdating table model with variants...");
            updateVariantTableModel(variants);

            // Verify the table model was updated correctly
            System.out.println("Table model update complete. Rows in table: " + tblSanPhamCon.getModel().getRowCount());

            // Print the actual table contents for verification
            if (tblSanPhamCon.getModel().getRowCount() > 0) {
                System.out.println("\n=== Contents of Variant Table ===");
                DefaultTableModel model = (DefaultTableModel) tblSanPhamCon.getModel();
                for (int i = 0; i < model.getRowCount(); i++) {
                    System.out.println(String.format("Row %d: %s, %s, %s, %s, %s, %s",
                            i,
                            model.getValueAt(i, 0), // SKU
                            model.getValueAt(i, 1), // Size
                            model.getValueAt(i, 2), // Color
                            model.getValueAt(i, 3), // Sole
                            model.getValueAt(i, 4), // Price
                            model.getValueAt(i, 5) // Quantity
                    ));
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading product variants:");
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi tải biến thể sản phẩm: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshComboboxes(String type) {
        try {
            // Refresh current form's combobox if it's the same type
            if (type.equals("brand") && cbo_brand != null) {
                BrandDAOImpl brandDAO = new BrandDAOImpl();
                List<Brand> brands = brandDAO.findAll();
                cbo_brand.removeAllItems();
                cbo_brand.addItem("Chọn");
                for (Brand brand : brands) {
                    cbo_brand.addItem(brand.getBrandId() + ":" + brand.getBrandName());
                }
            } else if (type.equals("category") && cbo_category != null) {
                CategoryDAOImpl categoryDAO = new CategoryDAOImpl();
                List<Category> categories = categoryDAO.findAll();
                cbo_category.removeAllItems();
                cbo_category.addItem("Chọn");
                for (Category category : categories) {
                    cbo_category.addItem(category.getCategoryId() + ":" + category.getCategoryName());
                }
            }

            // Also refresh any open ViewThemSanPhamm dialogs
            for (java.awt.Window window : java.awt.Window.getWindows()) {
                if (window instanceof javax.swing.JDialog) {
                    javax.swing.JDialog dialog = (javax.swing.JDialog) window;
                    java.awt.Component[] components = dialog.getContentPane().getComponents();
                    for (java.awt.Component comp : components) {
                        if (comp instanceof ViewThemSanPhamm) {
                            ViewThemSanPhamm themSanPhamPanel = (ViewThemSanPhamm) comp;
                            themSanPhamPanel.loadAllData();
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi làm mới dữ liệu: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadTable() {
        try {
            if (rb_category.isSelected()) {
                // Load danh sách danh mục
                CategoryDAOImpl dao
                        = new CategoryDAOImpl();
                List<Category> list = dao.findAll();
                loadTableData(list, "category");
            } else if (rb_brand.isSelected()) {
                // Load danh sách thương hiệu
                BrandDAOImpl dao
                        = new BrandDAOImpl();
                List<Brand> list = dao.findAll();
                loadTableData(list, "brand");
            } else if (rb_sole.isSelected()) {
                // Load danh sách loại đế
                SoleTypeDAOImpl dao
                        = new SoleTypeDAOImpl();
                List<com.DuAn1.volleyballshoes.app.entity.SoleType> list = dao.findAll();
                loadTableData(list, "soleType");
            } else if (rb_size.isSelected()) {
                // Load danh sách kích thước
                com.DuAn1.volleyballshoes.app.dao.impl.SizeDAOImpl dao
                        = new com.DuAn1.volleyballshoes.app.dao.impl.SizeDAOImpl();
                List<com.DuAn1.volleyballshoes.app.entity.Size> list = dao.findAll();
                loadTableData(list, "size");
            } else if (rb_color.isSelected()) {
                // Load danh sách màu sắc
                com.DuAn1.volleyballshoes.app.dao.impl.ColorDAOImpl dao
                        = new com.DuAn1.volleyballshoes.app.dao.impl.ColorDAOImpl();
                List<Color> list = dao.findAll();
                loadTableData(list, "color");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Lỗi tải dữ liệu: " + ex.getMessage(),
                    "Lỗi",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public void loadProductVariants() {
        System.out.println("\n=== loadProductVariants() called ===");
        System.out.println("Selected Product: " + (selectedProduct != null ? 
            "ID: " + selectedProduct.getProductId() + ", Name: " + selectedProduct.getProductName() : "null"));
            
        if (selectedProduct != null) {
            try {
                // Debug: Print product ID being queried
                System.out.println("Querying variants for product ID: " + selectedProduct.getProductId());
                
                // Get variants from DAO
                List<ProductVariant> variants = productVariantDAO.findByProductId(selectedProduct.getProductId());
                
                // Debug: Print number of variants found
                System.out.println("Number of variants found: " + (variants != null ? variants.size() : "null"));
                
                if (variants != null && !variants.isEmpty()) {
                    System.out.println("First variant SKU: " + variants.get(0).getVariantSku());
                }
                
                // Update the table model
                updateVariantTableModel(variants);
                
                // Debug: Verify table model was updated
                System.out.println("Table model row count after update: " + tblSanPhamCon.getModel().getRowCount());
                
            } catch (Exception e) {
                System.err.println("Error in loadProductVariants():");
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, 
                    "Lỗi khi tải biến thể sản phẩm: " + e.getMessage(),
                    "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } else {
            System.out.println("No product selected, clearing variant table");
            DefaultTableModel model = (DefaultTableModel) tblSanPhamCon.getModel();
            model.setRowCount(0);
        }
    }

    private void loadTableData(List<?> dataList, String type) {
        DefaultTableModel model = (DefaultTableModel) tblThuocTinh.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ

        for (Object item : dataList) {
            Object[] row = new Object[3];
            row[0] = model.getRowCount() + 1; // STT

            switch (type) {
                case "brand":
                    com.DuAn1.volleyballshoes.app.entity.Brand brand = (com.DuAn1.volleyballshoes.app.entity.Brand) item;
                    row[1] = brand.getBrandCode();
                    row[2] = brand.getBrandName();
                    break;
                case "color":
                    com.DuAn1.volleyballshoes.app.entity.Color color = (com.DuAn1.volleyballshoes.app.entity.Color) item;
                    row[1] = color.getColorHexCode();
                    row[2] = color.getColorName();
                    break;
                case "category":
                    com.DuAn1.volleyballshoes.app.entity.Category cat = (com.DuAn1.volleyballshoes.app.entity.Category) item;
                    row[1] = cat.getCategoryCode();
                    row[2] = cat.getCategoryName();
                    break;
                case "size":
                    com.DuAn1.volleyballshoes.app.entity.Size size = (com.DuAn1.volleyballshoes.app.entity.Size) item;
                    row[1] = size.getSizeCode();
                    row[2] = size.getSizeValue();
                    break;
                case "soleType":
                    com.DuAn1.volleyballshoes.app.entity.SoleType soleType = (com.DuAn1.volleyballshoes.app.entity.SoleType) item;
                    row[1] = soleType.getSoleCode();
                    row[2] = soleType.getSoleName();
                    break;
            }
            model.addRow(row);
        }
    }

    private void generateQRCodeForRow(int rowIndex) {
        try {
            String sku = tblSanPhamCon.getValueAt(rowIndex, 1).toString(); // Assuming SKU is in column 1
            String productName = tblSanPhamCon.getValueAt(rowIndex, 0).toString();

            // Create QR code content with product info
            String qrContent = String.format("SKU: %s\nProduct: %s", sku, productName);

            // Create and show QR code dialog
            JDialog qrDialog = new JDialog();
            qrDialog.setTitle("QR Code - " + productName);

            // Generate QR code
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(qrContent, BarcodeFormat.QR_CODE, 300, 300);

            // Convert to BufferedImage
            BufferedImage qrImage = new BufferedImage(300, 300, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < 300; x++) {
                for (int y = 0; y < 300; y++) {
                    qrImage.setRGB(x, y, bitMatrix.get(x, y) ? 0x000000 : 0xFFFFFF);
                }
            }

            // Display QR code
            JLabel qrLabel = new JLabel(new ImageIcon(qrImage));
            qrDialog.add(qrLabel);
            qrDialog.pack();
            qrDialog.setLocationRelativeTo(this);
            qrDialog.setVisible(true);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi tạo mã QR: " + ex.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleEditAction() {
        int selectedRow = tblSanPhamCon.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần sửa");
            return;
        }

        // Tạo JDialog mới
        JDialog dialog = new JDialog();
        dialog.setTitle("Sửa Sản phẩm");

        // Thêm panel vào dialog
        ViewThemSanPhamm themSanPhamPanel = new ViewThemSanPhamm();
        dialog.add(themSanPhamPanel);

        // Đặt kích thước dialog
        dialog.pack();

        // Căn giữa màn hình
        dialog.setLocationRelativeTo(null);

        // Hiển thị dialog
        dialog.setModal(true);
        dialog.setVisible(true);
    }

    private void handleDeleteAction() {
        int selectedRow = tblSanPhamCon.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần xóa");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa sản phẩm này?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            // TODO: Implement delete logic here
            // Get product ID from the selected row and delete it
            JOptionPane.showMessageDialog(this, "Đã xóa sản phẩm thành công!");
        }
    }

    private String formatCurrency(BigDecimal amount) {
        if (amount == null) {
            return "0 đ";
        }
        return String.format("%,d đ", amount.longValue());
    }

    /**
     * Update pagination label
     */
    private void updatePaginationLabel() {
        trang.setText(String.format("Trang %d/%d", currentPage, totalPages));
    }

    private void updatePaginationButtons() {
        btnNhoNhat.setEnabled(currentPage > 1);
        btnNho.setEnabled(currentPage > 1);
        btnLon.setEnabled(currentPage < totalPages);
        btnLonNhat.setEnabled(currentPage < totalPages);
    }

    public static void main(String args[]) {
        // Set console encoding to UTF-8
        System.setProperty("file.encoding", "UTF-8");
        try {
            java.lang.reflect.Field charset = java.nio.charset.Charset.class.getDeclaredField("defaultCharset");
            charset.setAccessible(true);
            charset.set(null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ViewSanPham.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewSanPham.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewSanPham.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewSanPham.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewSanPham().setVisible(true);
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        pnl_product = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lbl_product_id = new javax.swing.JLabel();
        lbl_product_name = new javax.swing.JLabel();
        txtMaSP = new javax.swing.JTextField();
        txtTenSP = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtaMoTa = new javax.swing.JTextArea();
        jPanel5 = new javax.swing.JPanel();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        nhoNhat = new javax.swing.JButton();
        nho = new javax.swing.JButton();
        jlabel5 = new javax.swing.JLabel();
        lon = new javax.swing.JButton();
        lonNhat = new javax.swing.JButton();
        lbl_brand = new javax.swing.JLabel();
        lbl_category = new javax.swing.JLabel();
        cbo_brand = new javax.swing.JComboBox<>();
        cbo_category = new javax.swing.JComboBox<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        pnl_thuộc_tính = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        lbl_ma = new javax.swing.JLabel();
        lbl_ten = new javax.swing.JLabel();
        txtMa = new javax.swing.JTextField();
        txtTen = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        rb_color = new javax.swing.JRadioButton();
        rb_size = new javax.swing.JRadioButton();
        rb_brand = new javax.swing.JRadioButton();
        rb_category = new javax.swing.JRadioButton();
        rb_sole = new javax.swing.JRadioButton();
        btnThemThuocTinh = new javax.swing.JButton();
        btnsuathuoctinh = new javax.swing.JButton();
        btnXoaThuocTinh = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblThuocTinh = new javax.swing.JTable();
        pbl_productvariant = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        lbl_sku = new javax.swing.JLabel();
        cbo_product_variant_sku = new javax.swing.JComboBox<>();
        jPanel10 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        cbbKieu = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        txtLGT = new javax.swing.JTextField();
        btn_search = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        txt_productname = new javax.swing.JTextField();
        btnNho = new javax.swing.JButton();
        trang = new javax.swing.JLabel();
        btnLon = new javax.swing.JButton();
        btnNhoNhat = new javax.swing.JButton();
        btnLonNhat = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        btnQuetQR = new javax.swing.JButton();
        btnThembienthe = new javax.swing.JButton();
        btnLamMoi1 = new javax.swing.JButton();
        btnTaiQR = new javax.swing.JButton();
        btn_dowload_template = new javax.swing.JButton();
        btn_import_file_excel = new javax.swing.JButton();
        btnSua_productvariant = new javax.swing.JButton();
        btnXoa_productvariant = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblSanPhamCon = new javax.swing.JTable();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 255));
        jLabel1.setText("Thông Tin Sản Phẩm");

        lbl_product_id.setText("Mã Sản Phẩm");

        lbl_product_name.setText("Tên Sản Phẩm");

        jLabel4.setText("Mô Tả");

        txtaMoTa.setColumns(20);
        txtaMoTa.setRows(5);
        jScrollPane1.setViewportView(txtaMoTa);

        btnSua.setBackground(new java.awt.Color(0, 102, 255));
        btnSua.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSua.setForeground(new java.awt.Color(255, 255, 255));
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(0, 102, 255));
        btnXoa.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnLamMoi.setBackground(new java.awt.Color(0, 102, 255));
        btnLamMoi.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnLamMoi.setForeground(new java.awt.Color(255, 255, 255));
        btnLamMoi.setText("Làm mới");
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        btnThem.setBackground(new java.awt.Color(0, 102, 255));
        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnThem)
                .addGap(47, 47, 47)
                .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(btnXoa)
                .addGap(27, 27, 27)
                .addComponent(btnLamMoi)
                .addGap(32, 32, 32))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSua)
                    .addComponent(btnXoa)
                    .addComponent(btnLamMoi)
                    .addComponent(btnThem))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        nhoNhat.setText("<<");
        nhoNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nhoNhatActionPerformed(evt);
            }
        });

        nho.setText("<");
        nho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nhoActionPerformed(evt);
            }
        });

        jlabel5.setText("jLabel5");

        lon.setText(">");
        lon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lonActionPerformed(evt);
            }
        });

        lonNhat.setText(">>");
        lonNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lonNhatActionPerformed(evt);
            }
        });

        lbl_brand.setText("Thương Hiệu");

        lbl_category.setText("Danh mục");

        cbo_brand.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbo_category.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblSanPham);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbl_product_id)
                                    .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(26, 26, 26)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbl_brand)
                                    .addComponent(cbo_brand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbl_product_name)
                                    .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(40, 40, 40)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(cbo_category, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(102, 102, 102))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(lbl_category, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(116, 116, 116)))
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4)))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(438, 438, 438)
                        .addComponent(jLabel1))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(328, 328, 328)
                        .addComponent(nhoNhat)
                        .addGap(18, 18, 18)
                        .addComponent(nho)
                        .addGap(18, 18, 18)
                        .addComponent(jlabel5)
                        .addGap(18, 18, 18)
                        .addComponent(lon)
                        .addGap(29, 29, 29)
                        .addComponent(lonNhat))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 904, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(236, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_brand)
                    .addComponent(jLabel4)
                    .addComponent(lbl_product_id))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbo_brand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(39, 39, 39)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_product_name)
                            .addComponent(lbl_category))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbo_category, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nhoNhat)
                    .addComponent(nho)
                    .addComponent(jlabel5)
                    .addComponent(lon)
                    .addComponent(lonNhat))
                .addContainerGap(155, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnl_productLayout = new javax.swing.GroupLayout(pnl_product);
        pnl_product.setLayout(pnl_productLayout);
        pnl_productLayout.setHorizontalGroup(
            pnl_productLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1146, Short.MAX_VALUE)
            .addGroup(pnl_productLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnl_productLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        pnl_productLayout.setVerticalGroup(
            pnl_productLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 719, Short.MAX_VALUE)
            .addGroup(pnl_productLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnl_productLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("Sản Phẩm", pnl_product);

        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        lbl_ma.setText("Mã ");

        lbl_ten.setText("Tên");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_ten)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtMa)
                        .addComponent(txtTen, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
                        .addGroup(jPanel7Layout.createSequentialGroup()
                            .addGap(8, 8, 8)
                            .addComponent(lbl_ma))))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(lbl_ma)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtMa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(lbl_ten)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 255));
        jLabel9.setText("Thông Tin Thuộc Tính");

        buttonGroup1.add(rb_color);
        rb_color.setText("Màu");
        rb_color.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_colorActionPerformed(evt);
            }
        });

        buttonGroup1.add(rb_size);
        rb_size.setText("Size");
        rb_size.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_sizeActionPerformed(evt);
            }
        });

        buttonGroup1.add(rb_brand);
        rb_brand.setText("Thương hiệu");
        rb_brand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_brandActionPerformed(evt);
            }
        });

        buttonGroup1.add(rb_category);
        rb_category.setText("Loại");
        rb_category.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_categoryActionPerformed(evt);
            }
        });

        buttonGroup1.add(rb_sole);
        rb_sole.setText("Đế giày");
        rb_sole.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_soleActionPerformed(evt);
            }
        });

        btnThemThuocTinh.setBackground(new java.awt.Color(0, 51, 255));
        btnThemThuocTinh.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThemThuocTinh.setForeground(new java.awt.Color(255, 255, 255));
        btnThemThuocTinh.setText("Thêm");
        btnThemThuocTinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemThuocTinhActionPerformed(evt);
            }
        });

        btnsuathuoctinh.setBackground(new java.awt.Color(0, 51, 255));
        btnsuathuoctinh.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnsuathuoctinh.setForeground(new java.awt.Color(255, 255, 255));
        btnsuathuoctinh.setText("Sửa");
        btnsuathuoctinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsuathuoctinhActionPerformed(evt);
            }
        });

        btnXoaThuocTinh.setBackground(new java.awt.Color(0, 51, 255));
        btnXoaThuocTinh.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnXoaThuocTinh.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaThuocTinh.setText("Xóa");
        btnXoaThuocTinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaThuocTinhActionPerformed(evt);
            }
        });

        tblThuocTinh.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "STT", "Mã thuộc tính", "Tên thuộc tính"
            }
        ));
        tblThuocTinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblThuocTinhMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblThuocTinh);

        javax.swing.GroupLayout pnl_thuộc_tínhLayout = new javax.swing.GroupLayout(pnl_thuộc_tính);
        pnl_thuộc_tính.setLayout(pnl_thuộc_tínhLayout);
        pnl_thuộc_tínhLayout.setHorizontalGroup(
            pnl_thuộc_tínhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_thuộc_tínhLayout.createSequentialGroup()
                .addGroup(pnl_thuộc_tínhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_thuộc_tínhLayout.createSequentialGroup()
                        .addGap(120, 120, 120)
                        .addComponent(btnThemThuocTinh)
                        .addGap(158, 158, 158)
                        .addComponent(btnsuathuoctinh)
                        .addGap(121, 121, 121)
                        .addComponent(btnXoaThuocTinh))
                    .addGroup(pnl_thuộc_tínhLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(123, 123, 123)
                        .addGroup(pnl_thuộc_tínhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rb_color)
                            .addComponent(rb_category))
                        .addGap(56, 56, 56)
                        .addGroup(pnl_thuộc_tínhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rb_sole)
                            .addGroup(pnl_thuộc_tínhLayout.createSequentialGroup()
                                .addComponent(rb_size)
                                .addGap(42, 42, 42)
                                .addComponent(rb_brand))))
                    .addGroup(pnl_thuộc_tínhLayout.createSequentialGroup()
                        .addGap(285, 285, 285)
                        .addComponent(jLabel9))
                    .addGroup(pnl_thuộc_tínhLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 793, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(164, Short.MAX_VALUE))
        );
        pnl_thuộc_tínhLayout.setVerticalGroup(
            pnl_thuộc_tínhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_thuộc_tínhLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel9)
                .addGroup(pnl_thuộc_tínhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_thuộc_tínhLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_thuộc_tínhLayout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(pnl_thuộc_tínhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rb_color)
                            .addComponent(rb_size)
                            .addComponent(rb_brand))
                        .addGap(29, 29, 29)
                        .addGroup(pnl_thuộc_tínhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rb_category)
                            .addComponent(rb_sole))))
                .addGap(81, 81, 81)
                .addGroup(pnl_thuộc_tínhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemThuocTinh)
                    .addComponent(btnsuathuoctinh)
                    .addComponent(btnXoaThuocTinh))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("thuộc tính", pnl_thuộc_tính);

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Lọc Sản Phẩm"));

        jPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        lbl_sku.setText("Ma San pham");

        cbo_product_variant_sku.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbo_product_variant_sku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo_product_variant_skuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(65, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbo_product_variant_sku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_sku))
                .addGap(35, 35, 35))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_sku)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbo_product_variant_sku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        jLabel17.setText("Giá");

        cbbKieu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { ">", "<", ">=", "<=", "=", " " }));

        jLabel19.setText("Giá Tiền:");

        txtLGT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLGTActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel17)
                .addGap(91, 91, 91))
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbbKieu, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtLGT, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbbKieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19)
                    .addComponent(txtLGT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        btn_search.setText("Tìm kiếm");
        btn_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_searchActionPerformed(evt);
            }
        });

        jPanel11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        jLabel15.setText("Tên");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap(36, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGap(88, 88, 88))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addComponent(txt_productname, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30))))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addGap(18, 18, 18)
                .addComponent(txt_productname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btn_search)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(23, Short.MAX_VALUE))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(btn_search)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnNho.setText("<");
        btnNho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhoActionPerformed(evt);
            }
        });

        trang.setText("jLabel13");

        btnLon.setText(">");
        btnLon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLonActionPerformed(evt);
            }
        });

        btnNhoNhat.setText("<<");
        btnNhoNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhoNhatActionPerformed(evt);
            }
        });

        btnLonNhat.setText(">>");
        btnLonNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLonNhatActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 255));
        jLabel10.setText("Thông tin sản phảm chi tiết");

        btnQuetQR.setBackground(new java.awt.Color(0, 102, 255));
        btnQuetQR.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnQuetQR.setForeground(new java.awt.Color(255, 255, 255));
        btnQuetQR.setText("Quét QR");
        btnQuetQR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuetQRActionPerformed(evt);
            }
        });

        btnThembienthe.setBackground(new java.awt.Color(0, 102, 255));
        btnThembienthe.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThembienthe.setForeground(new java.awt.Color(255, 255, 255));
        btnThembienthe.setText("Thêm");
        btnThembienthe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThembientheActionPerformed(evt);
            }
        });

        btnLamMoi1.setBackground(new java.awt.Color(0, 102, 255));
        btnLamMoi1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnLamMoi1.setForeground(new java.awt.Color(255, 255, 255));
        btnLamMoi1.setText("Làm mới");
        btnLamMoi1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoi1ActionPerformed(evt);
            }
        });

        btnTaiQR.setBackground(new java.awt.Color(0, 102, 255));
        btnTaiQR.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnTaiQR.setForeground(new java.awt.Color(255, 255, 255));
        btnTaiQR.setText("Tải QR");
        btnTaiQR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaiQRActionPerformed(evt);
            }
        });

        btn_dowload_template.setBackground(new java.awt.Color(0, 102, 255));
        btn_dowload_template.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_dowload_template.setForeground(new java.awt.Color(255, 255, 255));
        btn_dowload_template.setText("Tải File Excel");
        btn_dowload_template.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dowload_templateActionPerformed(evt);
            }
        });

        btn_import_file_excel.setBackground(new java.awt.Color(0, 102, 255));
        btn_import_file_excel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_import_file_excel.setForeground(new java.awt.Color(255, 255, 255));
        btn_import_file_excel.setText("Thêm bằng file excel");
        btn_import_file_excel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_import_file_excelActionPerformed(evt);
            }
        });

        btnSua_productvariant.setBackground(new java.awt.Color(0, 102, 255));
        btnSua_productvariant.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSua_productvariant.setForeground(new java.awt.Color(255, 255, 255));
        btnSua_productvariant.setText("Sửa");
        btnSua_productvariant.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSua_productvariantActionPerformed(evt);
            }
        });

        btnXoa_productvariant.setBackground(new java.awt.Color(0, 102, 255));
        btnXoa_productvariant.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnXoa_productvariant.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa_productvariant.setText("Xóa");
        btnXoa_productvariant.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoa_productvariantActionPerformed(evt);
            }
        });

        tblSanPhamCon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblSanPhamCon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamConMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tblSanPhamCon);

        javax.swing.GroupLayout pbl_productvariantLayout = new javax.swing.GroupLayout(pbl_productvariant);
        pbl_productvariant.setLayout(pbl_productvariantLayout);
        pbl_productvariantLayout.setHorizontalGroup(
            pbl_productvariantLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pbl_productvariantLayout.createSequentialGroup()
                .addGroup(pbl_productvariantLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pbl_productvariantLayout.createSequentialGroup()
                        .addGroup(pbl_productvariantLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pbl_productvariantLayout.createSequentialGroup()
                                .addGap(369, 369, 369)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pbl_productvariantLayout.createSequentialGroup()
                                .addGap(298, 298, 298)
                                .addComponent(btnNhoNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnNho)
                                .addGap(18, 18, 18)
                                .addComponent(trang)
                                .addGap(27, 27, 27)
                                .addComponent(btnLon)
                                .addGap(18, 18, 18)
                                .addComponent(btnLonNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pbl_productvariantLayout.createSequentialGroup()
                                .addGap(47, 47, 47)
                                .addComponent(btn_dowload_template)
                                .addGap(31, 31, 31)
                                .addComponent(btn_import_file_excel)
                                .addGap(64, 64, 64)
                                .addComponent(btnThembienthe)
                                .addGap(18, 18, 18)
                                .addComponent(btnSua_productvariant)
                                .addGap(26, 26, 26)
                                .addComponent(btnXoa_productvariant)
                                .addGap(57, 57, 57)
                                .addComponent(btnQuetQR)
                                .addGap(46, 46, 46)
                                .addComponent(btnTaiQR)
                                .addGap(42, 42, 42)
                                .addComponent(btnLamMoi1)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pbl_productvariantLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(pbl_productvariantLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 965, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pbl_productvariantLayout.setVerticalGroup(
            pbl_productvariantLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pbl_productvariantLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel10)
                .addGap(31, 31, 31)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pbl_productvariantLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnQuetQR)
                    .addComponent(btnThembienthe)
                    .addComponent(btnLamMoi1)
                    .addComponent(btnTaiQR)
                    .addComponent(btn_dowload_template)
                    .addComponent(btn_import_file_excel)
                    .addComponent(btnSua_productvariant)
                    .addComponent(btnXoa_productvariant))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addGroup(pbl_productvariantLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNhoNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNho)
                    .addComponent(trang, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLon)
                    .addComponent(btnLonNhat))
                .addGap(197, 197, 197))
        );

        jTabbedPane1.addTab("Biến thể sản phẩm", pbl_productvariant);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 975, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
    }// </editor-fold>//GEN-END:initComponents


    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        if (selectedProduct == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!validateForm()) {
            return;
        }

        try {
            ProductUpdateRequest request = getUpdateRequest();
            productController.updateProduct(request);

            loadData();
            clearForm();

            JOptionPane.showMessageDialog(this, "Cập nhật sản phẩm thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật sản phẩm: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        if (selectedProduct == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa sản phẩm này?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                productController.deleteProduct(selectedProduct.getProductId());
                loadData();
                clearForm();

                JOptionPane.showMessageDialog(this, "Xóa sản phẩm thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa sản phẩm: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        clearForm();
        currentPage = 0;
        loadData();
        JOptionPane.showMessageDialog(this, "Đã làm mới dữ liệu!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        if (!validateForm()) {
            return;
        }

        try {
            ProductCreateRequest request = getCreateRequest();
            System.out.println("[DEBUG] ProductCreateRequest: " + request);
            productController.createProduct(request);

            loadData();
            clearForm();

            NotificationUtil.showSuccess(this, "Thêm sản phẩm thành công!");
        } catch (Exception e) {
            NotificationUtil.showError(this, "Lỗi khi thêm sản phẩm: " + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void nhoNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nhoNhatActionPerformed
        currentPage = 0;
        loadData();
    }//GEN-LAST:event_nhoNhatActionPerformed

    private void nhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nhoActionPerformed
        if (currentPage > 0) {
            currentPage--;
            loadData();
        }
    }//GEN-LAST:event_nhoActionPerformed

    private void lonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lonActionPerformed
        try {
            int totalPages = productController.getTotalPages(pageSize);
            if (currentPage < totalPages - 1) {
                currentPage++;
                loadData();
            }
        } catch (Exception e) {
            // Handle error silently for pagination
        }
    }//GEN-LAST:event_lonActionPerformed

    private void lonNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lonNhatActionPerformed
        try {
            int totalPages = productController.getTotalPages(pageSize);
            currentPage = Math.max(0, totalPages - 1);
            loadData();
        } catch (Exception e) {
            // Handle error silently for pagination
        }
    }//GEN-LAST:event_lonNhatActionPerformed

    private void rb_colorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_colorActionPerformed
        type = "color";
        loadTable();
    }//GEN-LAST:event_rb_colorActionPerformed

    private void rb_sizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_sizeActionPerformed
        type = "size";
        loadTable();
    }//GEN-LAST:event_rb_sizeActionPerformed

    private void rb_brandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_brandActionPerformed
        type = "brand";
        loadTable();
    }//GEN-LAST:event_rb_brandActionPerformed

    private void rb_categoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_categoryActionPerformed
        type = "category";
        loadTable();
    }//GEN-LAST:event_rb_categoryActionPerformed

    private void rb_soleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_soleActionPerformed
        type = "soleType";
        loadTable();
    }//GEN-LAST:event_rb_soleActionPerformed

    private void btnThemThuocTinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemThuocTinhActionPerformed
        String code = txtMa.getText().trim();
        String name = txtTen.getText().trim();
        if (code.isEmpty() || name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ mã và tên!");
            return;
        }
        switch (type) {
            case "color":
                Color color = new Color();
                color.setColorHexCode(code);
                color.setColorName(name);
                colorDAO.create(color);
                JOptionPane.showMessageDialog(this, "Thêm màu sắc thành công!");
                txtMa.setText("");
                txtTen.setText("");
                loadTable();
                refreshComboboxes("color");
                break;
            case "size":
                Size size = new Size();
                size.setSizeCode(code);
                size.setSizeValue(name);
                sizeDAO.create(size);
                JOptionPane.showMessageDialog(this, "Thêm kích cỡ thành công!");
                txtMa.setText("");
                txtTen.setText("");
                loadTable();
                refreshComboboxes("size");
                break;
            case "brand":
                Brand brand = new Brand();
                brand.setBrandCode(code);
                brand.setBrandName(name);
                brandDAO.create(brand);
                JOptionPane.showMessageDialog(this, "Thêm thương hiệu thành công!");
                txtMa.setText("");
                txtTen.setText("");
                loadTable();
                refreshComboboxes("brand");
                break;
            case "category":
                Category category = new Category();
                category.setCategoryCode(code);
                category.setCategoryName(name);
                categoryDAO.create(category);
                JOptionPane.showMessageDialog(this, "Thêm danh mục thành công!");
                txtMa.setText("");
                txtTen.setText("");
                loadTable();
                refreshComboboxes("category");
                break;
            case "soleType":
                SoleType soleType = new SoleType();
                soleType.setSoleCode(code);
                soleType.setSoleName(name);
                soleTypeDAO.create(soleType);
                JOptionPane.showMessageDialog(this, "Thêm loại đế thành công!");
                txtMa.setText("");
                txtTen.setText("");
                loadTable();
                refreshComboboxes("soleType");
                break;
        }
    }//GEN-LAST:event_btnThemThuocTinhActionPerformed

    private void btnsuathuoctinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsuathuoctinhActionPerformed
        int row = tblThuocTinh.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng để sửa!");
            return;
        }
        String code = txtMa.getText().trim();
        String name = txtTen.getText().trim();
        switch (type) {
            case "color":
                Color color = colorDAO.findByCode(code);
                if (color == null) {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy!");
                    return;
                }
                color.setColorName(name);
                colorDAO.update(color);
                JOptionPane.showMessageDialog(this, "Sửa màu sắc thành công!");
                loadTable();
                break;
            case "size":
                Size size = sizeDAO.findByCode(code);
                if (size == null) {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy!");
                    return;
                }
                size.setSizeValue(name);
                sizeDAO.update(size);
                JOptionPane.showMessageDialog(this, "Sửa kích cỡ thành công!");
                loadTable();
                break;
            case "brand":
                Brand brand = brandDAO.findByCode(code);
                if (brand == null) {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy!");
                    return;
                }
                brand.setBrandName(name);
                brandDAO.update(brand);
                JOptionPane.showMessageDialog(this, "Sửa thương hiệu thành công!");
                loadTable();
                break;
            case "category":
                Category category = categoryDAO.findByCode(code);
                if (category == null) {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy!");
                    return;
                }
                category.setCategoryName(name);
                categoryDAO.update(category);
                JOptionPane.showMessageDialog(this, "Sửa danh mục thành công!");
                loadTable();
                break;
            case "soleType":
                SoleType soleType = soleTypeDAO.findByCode(code);
                if (soleType == null) {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy!");
                    return;
                }
                soleType.setSoleName(name);
                soleTypeDAO.update(soleType);
                JOptionPane.showMessageDialog(this, "Sửa loại đế thành công!");
                loadTable();
                break;
        }
    }//GEN-LAST:event_btnsuathuoctinhActionPerformed

    private void btnXoaThuocTinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaThuocTinhActionPerformed
        int row = tblThuocTinh.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng để xoá!");
            return;
        }
        String code = txtMa.getText().trim();
        switch (type) {
            case "color":
                colorDAO.deleteByCode(code);
                JOptionPane.showMessageDialog(this, "Xoá màu sắc thành công!");
                loadTable();
                break;
            case "size":
                sizeDAO.deleteByCode(code);
                JOptionPane.showMessageDialog(this, "Xoá kích cỡ thành công!");
                loadTable();
                break;
            case "brand":
                brandDAO.deleteByCode(code);
                JOptionPane.showMessageDialog(this, "Xoá thương hiệu thành công!");
                loadTable();
                break;
            case "category":
                categoryDAO.deleteByCode(code);
                JOptionPane.showMessageDialog(this, "Xoá danh mục thành công!");
                loadTable();
                break;
            case "soleType":
                soleTypeDAO.deleteByCode(code);
                JOptionPane.showMessageDialog(this, "Xoá loại đế thành công!");
                loadTable();
                break;
        }
    }//GEN-LAST:event_btnXoaThuocTinhActionPerformed

    private void tblThuocTinhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblThuocTinhMouseClicked
        int row = tblThuocTinh.getSelectedRow();
        if (row != -1) {
            txtMa.setText(tblThuocTinh.getValueAt(row, 1).toString());
            txtTen.setText(tblThuocTinh.getValueAt(row, 2).toString());
        }
    }//GEN-LAST:event_tblThuocTinhMouseClicked

    private void cbbLTLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbLTLActionPerformed
//        currentPage = 1;
//
//        // Lấy loại thuộc tính được chọn
//        String selectedType = cbbLTL.getSelectedItem().toString();
//        String filter = "";
//
//        // Tạo bộ lọc dựa trên loại được chọn
//        if (!"Tất cả".equals(selectedType)) {
//            filter = "attribute_type = '" + selectedType + "'";
//        }
//
//        try {
//            // Lấy dữ liệu phân trang với bộ lọc
//            List<ProductVariant> variants = productVariantDAO.findWithPagination(
//                    currentPage,
//                    pageSize,
//                    filter
//            );
//
//            // Cập nhật tổng số trang
//            int totalItems = productVariantDAO.count(filter);
//            totalPages = (int) Math.ceil((double) totalItems / pageSize);
//
//            // Cập nhật bảng với dữ liệu mới
//            updateTableModel(variants);
//
//            // Cập nhật trạng thái nút phân trang
//            updatePaginationButtons();
//
//            // Cập nhật nhãn phân trang
//            updatePaginationLabel();
//
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(this,
//                    "Lỗi khi tải dữ liệu: " + e.getMessage(),
//                    "Lỗi",
//                    JOptionPane.ERROR_MESSAGE);
//            e.printStackTrace();
//        }
    }//GEN-LAST:event_cbbLTLActionPerformed

    private void txtLGTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLGTActionPerformed
        currentPage = 1;

        // Lấy từ khóa tìm kiếm
        String keyword = txtLGT.getText().trim();
        String filter = "";

        // Tạo bộ lọc tìm kiếm nếu có từ khóa
        if (!keyword.isEmpty()) {
            filter = String.format("variant_sku LIKE '%%%s%%' OR variant_name LIKE '%%%s%%'",
                    keyword, keyword);
        }

        try {
            // Lấy dữ liệu phân trang với bộ lọc tìm kiếm
            List<ProductVariant> variants = productVariantDAO.findWithPagination(
                    currentPage,
                    pageSize,
                    filter
            );

            // Cập nhật tổng số trang
            int totalItems = productVariantDAO.count(filter);
            totalPages = (int) Math.ceil((double) totalItems / pageSize);

            // Cập nhật bảng với kết quả tìm kiếm
            updateVariantTableModel(variants);

            // Cập nhật trạng thái nút phân trang
            updatePaginationButtons();

            // Cập nhật nhãn phân trang
            updatePaginationLabel();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi tìm kiếm: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_txtLGTActionPerformed

    private void btnNhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhoActionPerformed

        if (currentPage > 1) {
            currentPage--;
            loadProductVariants();
        }
    }//GEN-LAST:event_btnNhoActionPerformed

    private void btnLonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLonActionPerformed
        if (currentPage < totalPages) {
            currentPage++;
            loadProductVariants();
        }
    }//GEN-LAST:event_btnLonActionPerformed

    private void btnNhoNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhoNhatActionPerformed
        if (currentPage != 1) {
            currentPage = 1;
            loadProductVariants();
        }
    }//GEN-LAST:event_btnNhoNhatActionPerformed

    private void btnLonNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLonNhatActionPerformed
        if (currentPage != totalPages) {
            currentPage = totalPages;
            loadProductVariants();
        }
    }//GEN-LAST:event_btnLonNhatActionPerformed

    private void btnQuetQRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuetQRActionPerformed
        // Tạo và hiển thị cửa sổ quét QR code
        QuetQRSanPham qrScanner = new QuetQRSanPham();
        qrScanner.setVisible(true);

        // Căn giữa màn hình
        qrScanner.setLocationRelativeTo(null);
    }//GEN-LAST:event_btnQuetQRActionPerformed

    private void btnThembientheActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThembientheActionPerformed
        // Tạo JDialog mới
        JDialog dialog = new JDialog();
        dialog.setTitle("Thêm sản phẩm mới");

        // Thêm panel vào dialog
        ViewThemSanPhamm themSanPhamPanel = new ViewThemSanPhamm();
        dialog.add(themSanPhamPanel);

        // Đặt kích thước dialog
        dialog.pack();

        // Căn giữa màn hình
        dialog.setLocationRelativeTo(null);

        // Hiển thị dialog
        dialog.setModal(true);
        dialog.setVisible(true);
    }//GEN-LAST:event_btnThembientheActionPerformed

    private void btnLamMoi1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoi1ActionPerformed
        // Reset all filter fields
//        cbbLTL.setSelectedIndex(0);
        cbbKieu.setSelectedIndex(0);
        txtLGT.setText("");

        // Reset pagination to first page
        currentPage = 1;

        // Reload product variants
        loadProductVariants();

        // Clear any selection in the table
        tblSanPhamCon.clearSelection();

        JOptionPane.showMessageDialog(this,
                "Đã làm mới dữ liệu thành công!",
                "Thành công",
                JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnLamMoi1ActionPerformed

    private void btnTaiQRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaiQRActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn ảnh QR code");

        // Chỉ cho phép chọn file ảnh
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Image files", "jpg", "jpeg", "png", "gif", "bmp");
        fileChooser.setFileFilter(filter);

        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                // Đọc ảnh QR code
                BufferedImage image = ImageIO.read(selectedFile);

                // Tạo đối tượng BinaryBitmap từ ảnh
                LuminanceSource source = new BufferedImageLuminanceSource(image);
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

                // Giải mã QR code
                Result result = new MultiFormatReader().decode(bitmap);
                String qrText = result.getText();

                // Xử lý dữ liệu QR code
                processQRCodeData(qrText);

            } catch (IOException e) {
                JOptionPane.showMessageDialog(this,
                        "Lỗi khi đọc file ảnh: " + e.getMessage(),
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            } catch (NotFoundException e) {
                JOptionPane.showMessageDialog(this,
                        "Không tìm thấy mã QR trong ảnh",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Lỗi khi đọc mã QR: " + e.getMessage(),
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnTaiQRActionPerformed

    private void btn_dowload_templateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dowload_templateActionPerformed
        // Tạo hộp thoại chọn nơi lưu file
        javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file mẫu");
        fileChooser.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == javax.swing.JFileChooser.APPROVE_OPTION) {
            try {
                // Tạo đường dẫn đầy đủ đến file mẫu
                File folder = fileChooser.getSelectedFile();
                String templatePath = folder.getAbsolutePath() + File.separator + "Mau_Import_SanPham.xlsx";

                // Tạo workbook mới
                try (org.apache.poi.xssf.usermodel.XSSFWorkbook workbook = new org.apache.poi.xssf.usermodel.XSSFWorkbook()) {
                    // Tạo sheet
                    org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet("Mau_Import");

                    // Tạo style cho header
                    org.apache.poi.ss.usermodel.CellStyle headerStyle = workbook.createCellStyle();
                    org.apache.poi.ss.usermodel.Font font = workbook.createFont();
                    font.setBold(true);
                    headerStyle.setFont(font);

                    // Tạo header row
                    org.apache.poi.ss.usermodel.Row headerRow = sheet.createRow(0);
                    String[] headers = {
                        // Product
                        "Mã sản phẩm (product_code)",
                        "Tên sản phẩm (product_name)",
                        "ID thương hiệu (brand_id, nhập số)",
                        "ID danh mục (category_id, nhập số)",
                        // ProductVariant
                        "Mã SKU (variant_sku)",
                        "ID Kích thước (size_id, nhập số)",
                        "ID Màu sắc (color_id, nhập số)",
                        "ID Loại đế (sole_id, nhập số)",
                        "Giá gốc (variant_orig_price)",
                        "Số lượng tồn (variant_quantity)"
                    };

                    for (int i = 0; i < headers.length; i++) {
                        org.apache.poi.ss.usermodel.Cell cell = headerRow.createCell(i);
                        cell.setCellValue(headers[i]);
                        cell.setCellStyle(headerStyle);
                    }

                    // Tạo dòng hướng dẫn
                    org.apache.poi.ss.usermodel.Row instructionRow1 = sheet.createRow(1);
                    // Product mẫu
                    instructionRow1.createCell(0).setCellValue("SP001"); // product_code
                    instructionRow1.createCell(1).setCellValue("Giày bóng chuyền Mizuno"); // product_name
                    instructionRow1.createCell(2).setCellValue("1"); // brand_id (nhập số, sẽ map sang tên)
                    instructionRow1.createCell(3).setCellValue("2"); // category_id (nhập số, sẽ map sang tên)
                    // ProductVariant mẫu
                    instructionRow1.createCell(4).setCellValue("SP001-BLACK-42"); // variant_sku
                    instructionRow1.createCell(5).setCellValue("3"); // size_id (nhập số, sẽ map sang tên)
                    instructionRow1.createCell(6).setCellValue("5"); // color_id (nhập số, sẽ map sang tên)
                    instructionRow1.createCell(7).setCellValue("2"); // sole_id (nhập số, sẽ map sang tên)
                    instructionRow1.createCell(8).setCellValue("650000"); // variant_orig_price
                    instructionRow1.createCell(9).setCellValue("100"); // variant_quantity

                    // Tự động điều chỉnh độ rộng cột
                    for (int i = 0; i < headers.length; i++) {
                        sheet.autoSizeColumn(i);
                    }

                    // Lưu file
                    try (java.io.FileOutputStream outputStream = new java.io.FileOutputStream(templatePath)) {
                        workbook.write(outputStream);
                    }

                    // Thông báo thành công
                    JOptionPane.showMessageDialog(this,
                            "Đã tạo file mẫu thành công tại: " + templatePath,
                            "Thành công",
                            JOptionPane.INFORMATION_MESSAGE);
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Lỗi khi tạo file mẫu: " + e.getMessage(),
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_btn_dowload_templateActionPerformed

    private void btn_import_file_excelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_import_file_excelActionPerformed
        // Tạo hộp thoại chọn file
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn file Excel cần import");

        // Chỉ cho phép chọn file Excel
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Excel Files", "xls", "xlsx");
        fileChooser.setFileFilter(filter);

        int returnValue = fileChooser.showOpenDialog(this);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            // Xác nhận trước khi import
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Bạn có chắc chắn muốn import dữ liệu từ file: " + selectedFile.getName() + "?",
                    "Xác nhận Import",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                try (FileInputStream fis = new FileInputStream(selectedFile); XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

                    // Lấy sheet đầu tiên
                    Sheet sheet = workbook.getSheetAt(0);

                    // Đếm số dòng dữ liệu (bỏ qua dòng tiêu đề)
                    int rowCount = sheet.getPhysicalNumberOfRows() - 1;

                    if (rowCount <= 0) {
                        JOptionPane.showMessageDialog(this,
                                "File Excel không có dữ liệu để import!",
                                "Cảnh báo",
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    int successCount = 0;
                    int failCount = 0;
                    StringBuilder errorMessages = new StringBuilder();

                    // Bắt đầu import từ dòng thứ 2 (index 1) vì dòng 1 là tiêu đề
                    for (int i = 1; i <= rowCount; i++) {
                        Row row = sheet.getRow(i);

                        try {
                            // Bỏ qua dòng trống
                            if (row == null) {
                                continue;
                            }

                            // Đọc dữ liệu từ các ô
                            String sku = getCellValueAsString(row.getCell(0));
                            String variantName = getCellValueAsString(row.getCell(1));
                            int productId = (int) Double.parseDouble(getCellValueAsString(row.getCell(2)));
                            int colorId = (int) Double.parseDouble(getCellValueAsString(row.getCell(3)));
                            int sizeId = (int) Double.parseDouble(getCellValueAsString(row.getCell(4)));
                            int soleId = (int) Double.parseDouble(getCellValueAsString(row.getCell(5)));
                            double originalPrice = Double.parseDouble(getCellValueAsString(row.getCell(6)));

                            // Tạo đối tượng ProductVariant
                            ProductVariant variant = new ProductVariant();

                            // Thiết lập các thuộc tính từ dữ liệu Excel
                            variant.setVariantSku(sku);
                            variant.setProductId(productId);
                            variant.setColorId(colorId);
                            variant.setSizeId(sizeId);
                            variant.setSoleId(soleId);
                            variant.setVariantOrigPrice(BigDecimal.valueOf(originalPrice));

                            // Lưu vào CSDL
                            // Lưu ý: Hiện tại DAO chưa hỗ trợ tìm kiếm theo SKU, nên tạm thời luôn tạo mới
                            // Có thể cần bổ sung phương thức findBySku vào ProductVariantDAO nếu cần
                            productVariantDAO.create(variant);

                            successCount++;

                        } catch (Exception e) {
                            failCount++;
                            errorMessages.append("Lỗi dòng ").append(i + 1).append(": ").append(e.getMessage()).append("\n");
                            e.printStackTrace();
                        }
                    }

                    // Hiển thị kết quả import
                    StringBuilder resultMessage = new StringBuilder();
                    resultMessage.append("Kết quả import:\n");
                    resultMessage.append("- Thành công: ").append(successCount).append(" dòng\n");
                    resultMessage.append("- Thất bại: ").append(failCount).append(" dòng\n");

                    if (failCount > 0) {
                        resultMessage.append("\nChi tiết lỗi:\n").append(errorMessages.toString());
                    }

                    JOptionPane.showMessageDialog(
                            this,
                            resultMessage.toString(),
                            "Kết quả Import",
                            failCount > 0 ? JOptionPane.WARNING_MESSAGE : JOptionPane.INFORMATION_MESSAGE
                    );

                    // Làm mới dữ liệu sau khi import
                    loadProductVariants();

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Lỗi khi đọc file Excel: " + e.getMessage(),
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE
                    );
                    e.printStackTrace();
                }
            }
        }
    }//GEN-LAST:event_btn_import_file_excelActionPerformed

    private void btnSua_productvariantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSua_productvariantActionPerformed
        int selectedRow = tblSanPhamCon.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một hàng để sửa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get the SKU from the second column (index 1)
        String sku = (String) tblSanPhamCon.getValueAt(selectedRow, 1);

        // Get ProductVariant by SKU - returns Optional<ProductVariant>
        ProductVariantDAO variantDAO = new ProductVariantDAOImpl();
        ProductVariant variantOpt = variantDAO.findBySku(sku);

        JDialog dialog = new JDialog();
        dialog.setTitle("Sửa Sản phẩm");
        ViewThemSanPhamm themSanPhamPanel = new ViewThemSanPhamm();
        themSanPhamPanel.loadVariantForEdit(variantOpt);
        dialog.add(themSanPhamPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setModal(true);
        dialog.setVisible(true);

        loadProductVariants();
    }//GEN-LAST:event_btnSua_productvariantActionPerformed

    private void btnXoa_productvariantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoa_productvariantActionPerformed
        // TODO add your handling code here:
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastDeleteClickTime < DOUBLE_CLICK_DELAY) {
            // Double click - generate QR code
            int selectedRow = tblSanPhamCon.getSelectedRow();
            if (selectedRow != -1) {
                generateQRCodeForRow(selectedRow);
            }
        } else {
            // Single click - delete
            handleDeleteAction();
        }
        lastDeleteClickTime = currentTime;
    }//GEN-LAST:event_btnXoa_productvariantActionPerformed

    private void btn_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_searchActionPerformed
        try {
            String sku = (String) cbo_product_variant_sku.getSelectedItem();
            String priceText = txtLGT.getText().trim();
            String productName = txt_productname.getText().trim();
            java.math.BigDecimal price = null;
            if (!priceText.isEmpty()) {
                try {
                    price = new java.math.BigDecimal(priceText);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Giá không hợp lệ! Vui lòng nhập số.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            // Nếu sku là null hoặc "Tất cả", bỏ lọc
            if (sku != null && (sku.equalsIgnoreCase("Tất cả") || sku.trim().isEmpty())) {
                sku = null;
            }
            if (productName.isEmpty()) {
                productName = null;
            }
            List<ProductVariant> variants = productVariantDAO.searchVariants(sku, productName, price);
            updateVariantTableModel(variants);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm biến thể: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
}//GEN-LAST:event_btn_searchActionPerformed

    private void cbo_product_variant_skuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo_product_variant_skuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbo_product_variant_skuActionPerformed

    private void tblSanPhamConMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamConMouseClicked
        // TODO add your handling code here:
        int selectedRow = tblSanPhamCon.getSelectedRow();
        if (evt.getClickCount() == 3) {
            int clickedRow = tblSanPhamCon.rowAtPoint(evt.getPoint());
            if (clickedRow != -1) {
                generateQRCodeForRow(clickedRow);
                return;
            }
        }
        if (selectedRow >= 0) {
            try {
                // Lấy dữ liệu từ hàng được chọn
                String sku = tblSanPhamCon.getValueAt(selectedRow, 0).toString();
                String size = tblSanPhamCon.getValueAt(selectedRow, 1).toString();
                String color = tblSanPhamCon.getValueAt(selectedRow, 2).toString();
                String soleType = tblSanPhamCon.getValueAt(selectedRow, 3).toString();
                String price = tblSanPhamCon.getValueAt(selectedRow, 4).toString();
                String quantity = tblSanPhamCon.getValueAt(selectedRow, 5).toString();

                // Ghi log thông tin sản phẩm được chọn
                System.out.println("Đã chọn sản phẩm:");
                System.out.println("SKU: " + sku);
                System.out.println("Kích thước: " + size);
                System.out.println("Màu sắc: " + color);
                System.out.println("Loại đế: " + soleType);
                System.out.println("Giá: " + price);
                System.out.println("Số lượng: " + quantity);

                // Tạo mã QR cho sản phẩm
                String qrData = "SKU: " + sku + "\n"
                        + "Kích thước: " + size + "\n"
                        + "Màu sắc: " + color + "\n"
                        + "Loại đế: " + soleType + "\n"
                        + "Giá: " + price + " VNĐ" + "\n"
                        + "Số lượng tồn: " + quantity;

                try {
                    // Hiển thị thông báo thay vì tạo mã QR (vì chưa có thư viện)
                    javax.swing.JOptionPane.showMessageDialog(this,
                            "Thông tin sản phẩm đã được chọn.\n"
                            + "Dữ liệu QR sẽ được tạo sau khi tích hợp thư viện.\n\n"
                            + qrData,
                            "Thông tin sản phẩm",
                            javax.swing.JOptionPane.INFORMATION_MESSAGE);

                    // Ghi log dữ liệu QR
                    System.out.println("Dữ liệu QR sẽ được tạo:" + qrData);

                    // Lưu ý: Để tạo mã QR thực tế, cần thêm thư viện như ZXing
                    // Ví dụ:
                    // String filePath = System.getProperty("java.io.tmpdir") + "qr_code_" + sku + ".png";
                    // generateQRCode(qrData, 200, 200, filePath);
                    // showQRCode(filePath);
                } catch (Exception e) {
                    // Log lỗi nếu có
                    System.err.println("Lỗi khi xử lý sự kiện chọn sản phẩm: " + e.getMessage());
                    e.printStackTrace();

                    // Hiển thị thông báo lỗi cho người dùng
                    JOptionPane.showMessageDialog(
                            this,
                            "Có lỗi xảy ra khi xử lý sản phẩm: " + e.getMessage(),
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE
                    );
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Lỗi khi đọc dữ liệu từ bảng: " + e.getMessage(),
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_tblSanPhamConMouseClicked

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
        // TODO add your handling code here:
        System.out.println("\n=== tblSanPhamMouseClicked triggered ===");
        selectedRow = tblSanPham.getSelectedRow();
        System.out.println("Selected row index: " + selectedRow);

        if (selectedRow != -1) {
            try {
                System.out.println("\n=== Processing row selection ===");

                // Get the product code directly from the selected row in the table
                String productCode = (String) tblSanPham.getValueAt(selectedRow, 0);
                System.out.println("Selected product code from table: " + productCode);

                if (productCode == null || productCode.trim().isEmpty()) {
                    System.out.println("ERROR: Product code is null or empty");
                    return;
                }

                // Log the selected row data for debugging
                System.out.println("\n--- Selected Row Data ---");
                int colCount = tblSanPham.getColumnCount();
                for (int i = 0; i < colCount; i++) {
                    System.out.println(tblSanPham.getColumnName(i) + ": " + tblSanPham.getValueAt(selectedRow, i));
                }

                // Find the product by code to get its ID
                System.out.println("\nLooking up product with code: " + productCode);
                selectedProduct = productController.getProductByCode(productCode);

                if (selectedProduct != null) {
                    System.out.println("\n--- Found Product Details ---");
                    System.out.println("Product ID: " + selectedProduct.getProductId());
                    System.out.println("Name: " + selectedProduct.getProductName());
                    System.out.println("Code: " + selectedProduct.getProductCode());

                    // Fill the form with product details
                    System.out.println("Filling product form...");
                    fillForm(selectedProduct);

                    // Load variants for the selected product
                    System.out.println("Loading product variants...");
                    loadSelectedProductVariants();
                } else {
                    System.out.println("ERROR: No product found with code: " + productCode);
                    // Clear the variant table if no product is selected
                    DefaultTableModel model = (DefaultTableModel) tblSanPhamCon.getModel();
                    System.out.println("Clearing variant table (no product found)");
                    model.setRowCount(0);
                }
            } catch (Exception e) {
                System.err.println("Error in tblSanPhamMouseClicked: " + e.getMessage());
                e.printStackTrace();
                JOptionPane.showMessageDialog(this,
                        "Lỗi khi tải thông tin sản phẩm: " + e.getMessage(),
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            System.out.println("No row selected or invalid selection");
        }
    }//GEN-LAST:event_tblSanPhamMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnLamMoi1;
    private javax.swing.JButton btnLon;
    private javax.swing.JButton btnLonNhat;
    private javax.swing.JButton btnNho;
    private javax.swing.JButton btnNhoNhat;
    private javax.swing.JButton btnQuetQR;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnSua_productvariant;
    private javax.swing.JButton btnTaiQR;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnThemThuocTinh;
    private javax.swing.JButton btnThembienthe;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXoaThuocTinh;
    private javax.swing.JButton btnXoa_productvariant;
    private javax.swing.JButton btn_dowload_template;
    private javax.swing.JButton btn_import_file_excel;
    private javax.swing.JButton btn_search;
    private javax.swing.JButton btnsuathuoctinh;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbbKieu;
    private javax.swing.JComboBox<String> cbo_brand;
    private javax.swing.JComboBox<String> cbo_category;
    private javax.swing.JComboBox<String> cbo_product_variant_sku;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel jlabel5;
    private javax.swing.JLabel lbl_brand;
    private javax.swing.JLabel lbl_category;
    private javax.swing.JLabel lbl_ma;
    private javax.swing.JLabel lbl_product_id;
    private javax.swing.JLabel lbl_product_name;
    private javax.swing.JLabel lbl_sku;
    private javax.swing.JLabel lbl_ten;
    private javax.swing.JButton lon;
    private javax.swing.JButton lonNhat;
    private javax.swing.JButton nho;
    private javax.swing.JButton nhoNhat;
    private javax.swing.JPanel pbl_productvariant;
    private javax.swing.JPanel pnl_product;
    private javax.swing.JPanel pnl_thuộc_tính;
    private javax.swing.JRadioButton rb_brand;
    private javax.swing.JRadioButton rb_category;
    private javax.swing.JRadioButton rb_color;
    private javax.swing.JRadioButton rb_size;
    private javax.swing.JRadioButton rb_sole;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTable tblSanPhamCon;
    private javax.swing.JTable tblThuocTinh;
    private javax.swing.JLabel trang;
    private javax.swing.JTextField txtLGT;
    private javax.swing.JTextField txtMa;
    private javax.swing.JTextField txtMaSP;
    private javax.swing.JTextField txtTen;
    private javax.swing.JTextField txtTenSP;
    private javax.swing.JTextField txt_productname;
    private javax.swing.JTextArea txtaMoTa;
    // End of variables declaration//GEN-END:variables
}
