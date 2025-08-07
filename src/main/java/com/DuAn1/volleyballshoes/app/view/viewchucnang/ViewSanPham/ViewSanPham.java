package com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewSanPham;

import com.DuAn1.volleyballshoes.app.controller.*;
import com.DuAn1.volleyballshoes.app.dao.*;
import com.DuAn1.volleyballshoes.app.dao.impl.*;
import com.DuAn1.volleyballshoes.app.dto.request.*;
import com.DuAn1.volleyballshoes.app.dto.response.*;
import com.DuAn1.volleyballshoes.app.entity.*;
import com.DuAn1.volleyballshoes.app.utils.NotificationUtil;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ViewSanPham extends javax.swing.JPanel {

    private int currentPage = 1;
    private int pageSize = 10;
    private int totalPages = 1;
    private final ProductController productController;
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

    private void processQRCodeData(String data) {
        // TODO: Thực hiện xử lý dữ liệu QR code ở đây
        System.out.println("QR Data: " + data);
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
        setupTable();
        loadBrands();
        loadCategories();
        loadData();
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
                Object[] row = {
                    product.getProductCode(),
                    product.getProductName(),
                    product.getProductDescription(),
                    product.getBrandName(),
                    product.getCategoryName()
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
            phanTrang.setText("Trang " + (currentPage + 1) + " / " + totalPages);

            nhoNhat.setEnabled(currentPage > 0);
            nho.setEnabled(currentPage > 0);
            lon.setEnabled(currentPage < totalPages - 1);
            lonNhat.setEnabled(currentPage < totalPages - 1);
        } catch (Exception e) {
            phanTrang.setText("Trang 1 / 1");
        }
    }

    private void clearForm() {
        // Generate a new product code when clearing the form
        txtMaSP.setText("SP" + System.currentTimeMillis());
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
            List<Brand> brands = brandDAO.findAll();
            for (Brand brand : brands) {
                if (brand != null && brand.getBrandName() != null) {
                    ComboBoxItem item = new ComboBoxItem(brand.getBrandId(), brand.getBrandName());
                    cbo_brand.addItem(item.toString());
                    // Store the ComboBoxItem in client property for later retrieval
                    cbo_brand.putClientProperty(item.toString(), item);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách thương hiệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadCategories() {
        try {
            cbo_category.removeAllItems();
            List<com.DuAn1.volleyballshoes.app.entity.Category> categories = categoryDAO.findAll();
            for (com.DuAn1.volleyballshoes.app.entity.Category category : categories) {
                if (category != null && category.getCategoryName() != null) {
                    ComboBoxItem item = new ComboBoxItem(category.getCategoryId(), category.getCategoryName());
                    cbo_category.addItem(item.toString());
                    // Store the ComboBoxItem in client property for later retrieval
                    cbo_category.putClientProperty(item.toString(), item);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách danh mục: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadTable() {
        try {
            if (rb_category.isSelected()) {
                // Load danh sách danh mục
                com.DuAn1.volleyballshoes.app.dao.impl.CategoryDAOImpl dao
                        = new com.DuAn1.volleyballshoes.app.dao.impl.CategoryDAOImpl();
                List<com.DuAn1.volleyballshoes.app.entity.Category> list = dao.findAll();
                loadTableData(list, "category");
            } else if (rb_brand.isSelected()) {
                // Load danh sách thương hiệu
                com.DuAn1.volleyballshoes.app.dao.impl.BrandDAOImpl dao
                        = new com.DuAn1.volleyballshoes.app.dao.impl.BrandDAOImpl();
                List<com.DuAn1.volleyballshoes.app.entity.Brand> list = dao.findAll();
                loadTableData(list, "brand");
            } else if (rb_sole.isSelected()) {
                // Load danh sách loại đế
                com.DuAn1.volleyballshoes.app.dao.impl.SoleTypeDAOImpl dao
                        = new com.DuAn1.volleyballshoes.app.dao.impl.SoleTypeDAOImpl();
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

    private void loadProductVariants() {
        try {
            // Get paginated data from DAO
            List<ProductVariant> variants = productVariantDAO.findWithPagination(
                    currentPage,
                    pageSize,
                    "" // No filter for now
            );

            // Get total count for pagination
            int totalItems = productVariantDAO.count("");
            totalPages = (int) Math.ceil((double) totalItems / pageSize);

            // Update pagination label
            updatePaginationLabel();

            // Update table model with data
            updateTableModel(variants);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Lỗi khi tải dữ liệu biến thể sản phẩm: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
        }
    }

    private void updateTableModel(List<ProductVariant> variants) {
        DefaultTableModel model = (DefaultTableModel) tblSanPhamCon.getModel();
        model.setRowCount(0); // Clear existing data

        // Set column names if not already set
        if (model.getColumnCount() == 0) {
            model.setColumnIdentifiers(new String[]{
                "Mã SKU",
                "Kích thước",
                "Màu sắc",
                "Loại đế",
                "Giá bán",
                "Số lượng tồn"
            });
        }

        // Add data rows
        for (ProductVariant variant : variants) {
            model.addRow(new Object[]{
                variant.getVariantSku(),
                variant.getSizeId(), // Consider getting size name instead of ID
                variant.getColorId(), // Consider getting color name instead of ID
                variant.getSoleId(), // Consider getting sole type name instead of ID
                formatCurrency(variant.getVariantOrigPrice()),});
        }
    }

    /**
     * Format currency for display
     */
    private String formatCurrency(BigDecimal amount) {
        if (amount == null) {
            return "0 VNĐ";
        }
        return String.format("%,d VNĐ", amount.longValue());
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
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
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
        phanTrang = new javax.swing.JLabel();
        lon = new javax.swing.JButton();
        lonNhat = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cbo_brand = new javax.swing.JComboBox<>();
        cbo_category = new javax.swing.JComboBox<>();
        pnl_thuộc_tính = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtMa = new javax.swing.JTextField();
        txtTen = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        rb_color = new javax.swing.JRadioButton();
        rb_size = new javax.swing.JRadioButton();
        rb_brand = new javax.swing.JRadioButton();
        rb_category = new javax.swing.JRadioButton();
        rb_sole = new javax.swing.JRadioButton();
        btnThem1 = new javax.swing.JButton();
        btnsua1 = new javax.swing.JButton();
        btnXoa1 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblThuocTinh = new javax.swing.JTable();
        pbl_productvariant = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblSanPhamCon = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        cbbLTL = new javax.swing.JComboBox<>();
        jPanel10 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        cbbKieu = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        txtLGT = new javax.swing.JTextField();
        btnNho = new javax.swing.JButton();
        trang = new javax.swing.JLabel();
        btnLon = new javax.swing.JButton();
        btnNhoNhat = new javax.swing.JButton();
        btnLonNhat = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        btnQuetQR = new javax.swing.JButton();
        btnThem2 = new javax.swing.JButton();
        btnXuatFile = new javax.swing.JButton();
        btnLamMoi1 = new javax.swing.JButton();
        btnTaiQR = new javax.swing.JButton();
        cbAll = new javax.swing.JCheckBox();
        btn_dowload_template = new javax.swing.JButton();
        btn_import_file_excel = new javax.swing.JButton();

        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblSanPham);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 255));
        jLabel1.setText("Thông Tin Sản Phẩm");

        jLabel2.setText("Mã Sản Phẩm");

        jLabel3.setText("Tên Sản Phẩm");

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
                .addGap(35, 35, 35)
                .addComponent(btnThem)
                .addGap(18, 18, 18)
                .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnXoa)
                .addGap(49, 49, 49)
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
                .addContainerGap(50, Short.MAX_VALUE))
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

        phanTrang.setText("jLabel5");

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

        jLabel5.setText("Thương Hiệu");

        jLabel6.setText("Loại");

        cbo_brand.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbo_category.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(411, 411, 411)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addGap(494, 494, 494))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(txtMaSP, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)
                            .addComponent(txtTenSP))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(38, 38, 38)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbo_category, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbo_brand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(175, 175, 175)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(331, 331, 331)
                        .addComponent(nhoNhat)
                        .addGap(18, 18, 18)
                        .addComponent(nho)
                        .addGap(18, 18, 18)
                        .addComponent(phanTrang)
                        .addGap(18, 18, 18)
                        .addComponent(lon)
                        .addGap(18, 18, 18)
                        .addComponent(lonNhat))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 904, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(190, 190, 190)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel1)
                .addGap(70, 70, 70)
                .addComponent(jLabel4)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addGap(11, 11, 11)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbo_brand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(53, 53, 53)
                        .addComponent(jLabel3)
                        .addGap(29, 29, 29)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(cbo_category, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nhoNhat)
                    .addComponent(nho)
                    .addComponent(phanTrang)
                    .addComponent(lon)
                    .addComponent(lonNhat))
                .addGap(85, 85, 85))
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
            .addGap(0, 716, Short.MAX_VALUE)
            .addGroup(pnl_productLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnl_productLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("Sản Phẩm", pnl_product);

        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        jLabel7.setText("Mã Thuộc Tính");

        jLabel8.setText("Tên Thuộc Tính");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtMa)
                        .addComponent(txtTen, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
                        .addGroup(jPanel7Layout.createSequentialGroup()
                            .addGap(8, 8, 8)
                            .addComponent(jLabel7))))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtMa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(jLabel8)
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

        btnThem1.setBackground(new java.awt.Color(0, 51, 255));
        btnThem1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThem1.setForeground(new java.awt.Color(255, 255, 255));
        btnThem1.setText("Thêm");
        btnThem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThem1ActionPerformed(evt);
            }
        });

        btnsua1.setBackground(new java.awt.Color(0, 51, 255));
        btnsua1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnsua1.setForeground(new java.awt.Color(255, 255, 255));
        btnsua1.setText("Sửa");
        btnsua1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsua1ActionPerformed(evt);
            }
        });

        btnXoa1.setBackground(new java.awt.Color(0, 51, 255));
        btnXoa1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnXoa1.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa1.setText("Xóa");
        btnXoa1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoa1ActionPerformed(evt);
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
                        .addComponent(btnThem1)
                        .addGap(158, 158, 158)
                        .addComponent(btnsua1)
                        .addGap(121, 121, 121)
                        .addComponent(btnXoa1))
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
                    .addComponent(btnThem1)
                    .addComponent(btnsua1)
                    .addComponent(btnXoa1))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("thuộc tính", pnl_thuộc_tính);

        tblSanPhamCon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblSanPhamCon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamConMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblSanPhamCon);

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Lọc Sản Phẩm"));

        jPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        jLabel14.setText("Thể Loại");

        cbbLTL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbLTLActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(51, Short.MAX_VALUE)
                .addComponent(jLabel14)
                .addGap(35, 35, 35))
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbbLTL, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addGap(18, 18, 18)
                .addComponent(cbbLTL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(23, Short.MAX_VALUE))
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

        btnThem2.setBackground(new java.awt.Color(0, 102, 255));
        btnThem2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThem2.setForeground(new java.awt.Color(255, 255, 255));
        btnThem2.setText("Thêm");
        btnThem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThem2ActionPerformed(evt);
            }
        });

        btnXuatFile.setBackground(new java.awt.Color(0, 102, 255));
        btnXuatFile.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnXuatFile.setForeground(new java.awt.Color(255, 255, 255));
        btnXuatFile.setText("Xuất File");
        btnXuatFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatFileActionPerformed(evt);
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

        cbAll.setText("All");
        cbAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbAllActionPerformed(evt);
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

        javax.swing.GroupLayout pbl_productvariantLayout = new javax.swing.GroupLayout(pbl_productvariant);
        pbl_productvariant.setLayout(pbl_productvariantLayout);
        pbl_productvariantLayout.setHorizontalGroup(
            pbl_productvariantLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pbl_productvariantLayout.createSequentialGroup()
                .addGroup(pbl_productvariantLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pbl_productvariantLayout.createSequentialGroup()
                        .addGroup(pbl_productvariantLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pbl_productvariantLayout.createSequentialGroup()
                                .addGap(319, 319, 319)
                                .addComponent(btnNhoNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnNho)
                                .addGap(18, 18, 18)
                                .addComponent(trang)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnLon)
                                .addGap(18, 18, 18)
                                .addComponent(btnLonNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pbl_productvariantLayout.createSequentialGroup()
                                .addGap(369, 369, 369)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pbl_productvariantLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pbl_productvariantLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane4))))
                .addContainerGap())
            .addGroup(pbl_productvariantLayout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(btnXuatFile)
                .addGap(18, 18, 18)
                .addComponent(cbAll)
                .addGap(45, 45, 45)
                .addComponent(btn_dowload_template)
                .addGap(30, 30, 30)
                .addComponent(btn_import_file_excel)
                .addGap(75, 75, 75)
                .addComponent(btnThem2)
                .addGap(32, 32, 32)
                .addComponent(btnQuetQR)
                .addGap(46, 46, 46)
                .addComponent(btnTaiQR)
                .addGap(42, 42, 42)
                .addComponent(btnLamMoi1)
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
                    .addComponent(btnThem2)
                    .addComponent(btnXuatFile)
                    .addComponent(btnLamMoi1)
                    .addComponent(btnTaiQR)
                    .addComponent(cbAll)
                    .addComponent(btn_dowload_template)
                    .addComponent(btn_import_file_excel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addGroup(pbl_productvariantLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNho)
                    .addComponent(trang, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLon)
                    .addComponent(btnNhoNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLonNhat))
                .addContainerGap())
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

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
        selectedRow = tblSanPham.getSelectedRow();
        if (selectedRow != -1) {
            try {
                List<ProductResponse> products = productController.getProductsWithPagination(currentPage, pageSize);
                if (selectedRow < products.size()) {
                    ProductResponse product = products.get(selectedRow);
                    fillForm(product);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi chọn sản phẩm: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_tblSanPhamMouseClicked

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
            productController.createProduct(request);

            loadData();
            clearForm();

            NotificationUtil.showSuccess(this, "Thêm sản phẩm thành công!");
        } catch (Exception e) {
            NotificationUtil.showError(this, "Lỗi khi thêm sản phẩm: ");
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

    private void btnThem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThem1ActionPerformed
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
                loadTable();
                break;
            case "size":
                Size size = new Size();
                size.setSizeCode(code);
                size.setSizeValue(name);
                sizeDAO.create(size);
                JOptionPane.showMessageDialog(this, "Thêm kích cỡ thành công!");
                loadTable();
                break;
            case "brand":
                Brand brand = new Brand();
                brand.setBrandCode(code);
                brand.setBrandName(name);
                brandDAO.create(brand);
                JOptionPane.showMessageDialog(this, "Thêm thương hiệu thành công!");
                loadTable();
                break;
            case "category":
                Category category = new Category();
                category.setCategoryCode(code);
                category.setCategoryName(name);
                categoryDAO.create(category);
                JOptionPane.showMessageDialog(this, "Thêm danh mục thành công!");
                loadTable();
                break;
            case "soleType":
                SoleType soleType = new SoleType();
                soleType.setSoleCode(code);
                soleType.setSoleName(name);
                soleTypeDAO.create(soleType);
                JOptionPane.showMessageDialog(this, "Thêm loại đế thành công!");
                loadTable();
                break;
        }
    }//GEN-LAST:event_btnThem1ActionPerformed

    private void btnsua1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsua1ActionPerformed
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
    }//GEN-LAST:event_btnsua1ActionPerformed

    private void btnXoa1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoa1ActionPerformed
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
    }//GEN-LAST:event_btnXoa1ActionPerformed

    private void tblThuocTinhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblThuocTinhMouseClicked
        int row = tblThuocTinh.getSelectedRow();
        if (row != -1) {
            txtMa.setText(tblThuocTinh.getValueAt(row, 1).toString());
            txtTen.setText(tblThuocTinh.getValueAt(row, 2).toString());
        }
    }//GEN-LAST:event_tblThuocTinhMouseClicked

    private void tblSanPhamConMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamConMouseClicked
        int selectedRow = tblSanPhamCon.getSelectedRow();
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

    private void cbbLTLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbLTLActionPerformed
        currentPage = 1;

        // Lấy loại thuộc tính được chọn
        String selectedType = cbbLTL.getSelectedItem().toString();
        String filter = "";

        // Tạo bộ lọc dựa trên loại được chọn
        if (!"Tất cả".equals(selectedType)) {
            filter = "attribute_type = '" + selectedType + "'";
        }

        try {
            // Lấy dữ liệu phân trang với bộ lọc
            List<ProductVariant> variants = productVariantDAO.findWithPagination(
                    currentPage,
                    pageSize,
                    filter
            );

            // Cập nhật tổng số trang
            int totalItems = productVariantDAO.count(filter);
            totalPages = (int) Math.ceil((double) totalItems / pageSize);

            // Cập nhật bảng với dữ liệu mới
            updateTableModel(variants);

            // Cập nhật trạng thái nút phân trang
            updatePaginationButtons();

            // Cập nhật nhãn phân trang
            updatePaginationLabel();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi tải dữ liệu: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
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
            updateTableModel(variants);

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

    private void btnThem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThem2ActionPerformed
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
    }//GEN-LAST:event_btnThem2ActionPerformed

    private void btnXuatFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatFileActionPerformed
        // Tạo hộp thoại chọn nơi lưu file
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Lưu dữ liệu ra file");

        // Đặt tên file mặc định
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        fileChooser.setSelectedFile(new File("danh_sach_san_pham_" + timestamp + ".csv"));

        // Chỉ cho phép lưu file .csv
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files (*.csv)", "csv");
        fileChooser.setFileFilter(filter);

        // Hiển thị hộp thoại lưu file
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            // Đảm bảo file có đuôi .csv
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".csv")) {
                fileToSave = new File(filePath + ".csv");
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileToSave))) {
                // Lấy model của bảng
                DefaultTableModel model = (DefaultTableModel) tblSanPhamCon.getModel();

                // Ghi tiêu đề cột
                for (int i = 0; i < model.getColumnCount(); i++) {
                    writer.write(model.getColumnName(i));
                    if (i < model.getColumnCount() - 1) {
                        writer.write(",");
                    }
                }
                writer.newLine();

                // Ghi dữ liệu từng dòng
                for (int i = 0; i < model.getRowCount(); i++) {
                    for (int j = 0; j < model.getColumnCount(); j++) {
                        Object value = model.getValueAt(i, j);
                        // Đảm bảo dữ liệu có dấu phẩy được đặt trong dấu ngoặc kép
                        if (value != null) {
                            String strValue = value.toString();
                            if (strValue.contains(",") || strValue.contains("\"") || strValue.contains("\n")) {
                                // Escape dấu ngoặc kép và đặt trong dấu ngoặc kép
                                strValue = strValue.replace("\"", "\"\"");
                                strValue = "\"" + strValue + "\"";
                            }
                            writer.write(strValue);
                        }
                        if (j < model.getColumnCount() - 1) {
                            writer.write(",");
                        }
                    }
                    writer.newLine();
                }

                JOptionPane.showMessageDialog(this,
                        "Xuất dữ liệu thành công!\nĐường dẫn: " + fileToSave.getAbsolutePath(),
                        "Thành công",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (IOException e) {
                JOptionPane.showMessageDialog(this,
                        "Lỗi khi xuất dữ liệu: " + e.getMessage(),
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnXuatFileActionPerformed

    private void btnLamMoi1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoi1ActionPerformed
        // Reset all filter fields
        cbbLTL.setSelectedIndex(0);
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

    private void cbAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbAllActionPerformed
        boolean isSelected = cbAll.isSelected();
        DefaultTableModel model = (DefaultTableModel) tblSanPhamCon.getModel();

        // Lặp qua tất cả các dòng trong bảng
        for (int i = 0; i < model.getRowCount(); i++) {
            // Cập nhật cột checkbox (giả sử cột checkbox là cột đầu tiên)
            model.setValueAt(isSelected, i, 0);

            // Nếu bạn muốn cập nhật trạng thái selected của dòng
            // tblSanPhamCon.changeSelection(i, 0, false, false);
        }

        // Cập nhật giao diện
        tblSanPhamCon.repaint();
    }//GEN-LAST:event_cbAllActionPerformed

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
                    String[] headers = {"Mã SKU", "Tên biến thể", "ID Sản phẩm", "ID Màu sắc",
                        "ID Kích thước", "ID Loại đế", "Giá gốc", "Giá khuyến mãi",
                        "Số lượng tồn", "Trạng thái (1: Hoạt động, 0: Ngừng bán)"};

                    for (int i = 0; i < headers.length; i++) {
                        org.apache.poi.ss.usermodel.Cell cell = headerRow.createCell(i);
                        cell.setCellValue(headers[i]);
                        cell.setCellStyle(headerStyle);
                    }

                    // Tạo dòng hướng dẫn
                    org.apache.poi.ss.usermodel.Row instructionRow1 = sheet.createRow(1);
                    instructionRow1.createCell(0).setCellValue("VD: SP001");
                    instructionRow1.createCell(1).setCellValue("VD: Giày bóng chuyền đen 42");
                    instructionRow1.createCell(2).setCellValue("1");
                    instructionRow1.createCell(3).setCellValue("1");
                    instructionRow1.createCell(4).setCellValue("1");
                    instructionRow1.createCell(5).setCellValue("1");
                    instructionRow1.createCell(6).setCellValue("500000");
                    instructionRow1.createCell(7).setCellValue("450000");
                    instructionRow1.createCell(8).setCellValue("50");
                    instructionRow1.createCell(9).setCellValue("1");

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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnLamMoi1;
    private javax.swing.JButton btnLon;
    private javax.swing.JButton btnLonNhat;
    private javax.swing.JButton btnNho;
    private javax.swing.JButton btnNhoNhat;
    private javax.swing.JButton btnQuetQR;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnTaiQR;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnThem1;
    private javax.swing.JButton btnThem2;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXoa1;
    private javax.swing.JButton btnXuatFile;
    private javax.swing.JButton btn_dowload_template;
    private javax.swing.JButton btn_import_file_excel;
    private javax.swing.JButton btnsua1;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox cbAll;
    private javax.swing.JComboBox<String> cbbKieu;
    private javax.swing.JComboBox<String> cbbLTL;
    private javax.swing.JComboBox<String> cbo_brand;
    private javax.swing.JComboBox<String> cbo_category;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton lon;
    private javax.swing.JButton lonNhat;
    private javax.swing.JButton nho;
    private javax.swing.JButton nhoNhat;
    private javax.swing.JPanel pbl_productvariant;
    private javax.swing.JLabel phanTrang;
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
    private javax.swing.JTextArea txtaMoTa;
    // End of variables declaration//GEN-END:variables
}
