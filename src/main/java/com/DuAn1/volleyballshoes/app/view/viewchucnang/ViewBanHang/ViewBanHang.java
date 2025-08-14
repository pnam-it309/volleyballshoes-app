package com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewBanHang;

import com.DuAn1.volleyballshoes.app.dao.*;
import com.DuAn1.volleyballshoes.app.dao.impl.*;
import com.DuAn1.volleyballshoes.app.entity.*;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.DuAn1.volleyballshoes.app.util.SessionManager;
import com.DuAn1.volleyballshoes.app.utils.XJdbc;
import java.math.RoundingMode;
import java.sql.*;

public class ViewBanHang extends javax.swing.JPanel {

    // Static reference to the active instance
    private static ViewBanHang activeInstance;

    // Method to get the active instance
    public static ViewBanHang getActiveInstance() {
        return activeInstance;
    }

    // Method to update the active instance
    private static void setActiveInstance(ViewBanHang instance) {
        activeInstance = instance;
    }

    private final CustomerDAO customerDAO = new CustomerDAOImpl();
    private final OrderDAO orderDAO = new OrderDAOImpl();
    private final OrderDetailDAO orderDetailDAO = new OrderDetailDAOImpl(); // Chỉ giữ lại 1 khai báo duy nhất
    private final ProductVariantDAO productVariantDAO = new ProductVariantDAOImpl();
    private final ProductDAO productDAO = new ProductDAOImpl();
    private final SizeDAO sizeDAO = new SizeDAOImpl();
    private final ColorDAO colorDAO = new ColorDAOImpl();
    private final SoleTypeDAO soleTypeDAO = new SoleTypeDAOImpl();
    private final PromotionDAO promotionDAO = new PromotionDAOImpl();
    private String orderCode; // Biến lưu mã đơn hàng

    private void initCartTable() {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Only allow editing quantity and discount columns
                return column == 3 || column == 5;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 0: // STT
                        return Integer.class;
                    case 3: // Số lượng
                        return Integer.class;
                    case 4: // Đơn giá
                    case 5: // Phần trăm giảm
                    case 6: // Thành tiền
                        return BigDecimal.class;
                    default:
                        return String.class;
                }
            }
        };

        // Set column names for cart table
        String[] columnNames = {"STT", "Mã SP", "Tên sản phẩm", "Số lượng", "Đơn giá", "Giảm giá (%)", "Thành tiền"};
        model.setColumnIdentifiers(columnNames);
        tblGioHang.setModel(model);

        // Add table model listener to update totals when quantity or discount changes
        model.addTableModelListener(e -> {
            if (e.getType() == javax.swing.event.TableModelEvent.UPDATE && (e.getColumn() == 3 || e.getColumn() == 5)) {
                updateRowTotal(e.getFirstRow());
                updateCartSummary();
            }
        });
    }

    // Update total for a specific row
    private void updateRowTotal(int row) {
        DefaultTableModel model = (DefaultTableModel) tblGioHang.getModel();
        try {
            int quantity = (int) model.getValueAt(row, 3);
            BigDecimal price = (BigDecimal) model.getValueAt(row, 4);
            BigDecimal discountPercent = (BigDecimal) model.getValueAt(row, 5);

            // Validate discount percentage (0-100)
            if (discountPercent.compareTo(BigDecimal.ZERO) < 0) {
                discountPercent = BigDecimal.ZERO;
                model.setValueAt(discountPercent, row, 5);
            } else if (discountPercent.compareTo(new BigDecimal(100)) > 0) {
                discountPercent = new BigDecimal(100);
                model.setValueAt(discountPercent, row, 5);
            }

            // Calculate total with discount
            BigDecimal subtotal = price.multiply(BigDecimal.valueOf(quantity));
            BigDecimal discountAmount = subtotal.multiply(discountPercent).divide(new BigDecimal(100));
            BigDecimal total = subtotal.subtract(discountAmount);

            model.setValueAt(total, row, 6);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng và phần trăm giảm giá hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Update cart summary (total, discount, etc.)
    private void updateCartSummary() {
        if (tblGioHang == null || tblGioHang.getModel() == null) {
            return; // Exit if table is not initialized
        }
        
        DefaultTableModel model = (DefaultTableModel) tblGioHang.getModel();
        BigDecimal subtotal = BigDecimal.ZERO;
        BigDecimal totalDiscount = BigDecimal.ZERO;
        BigDecimal promotionDiscount = BigDecimal.ZERO;

        // Calculate subtotal and total discount from items
        for (int i = 0; i < model.getRowCount(); i++) {
            try {
                Object rowTotalObj = model.getValueAt(i, 6);
                Object rowPriceObj = model.getValueAt(i, 4);
                Object quantityObj = model.getValueAt(i, 3);
                
                if (rowTotalObj == null || rowPriceObj == null || quantityObj == null) {
                    continue; // Skip invalid rows
                }
                
                BigDecimal rowTotal = (BigDecimal) rowTotalObj;
                BigDecimal rowPrice = (BigDecimal) rowPriceObj;
                int quantity = ((Number) quantityObj).intValue();
                
                BigDecimal rowSubtotal = rowPrice.multiply(BigDecimal.valueOf(quantity));
                subtotal = subtotal.add(rowSubtotal);
                totalDiscount = totalDiscount.add(rowSubtotal.subtract(rowTotal));
            } catch (Exception e) {
                // Skip any rows that cause errors
                continue;
            }
        }

        // Apply promotion discount if selected
        int selectedPromoIndex = cbbPhieuGiamGia.getSelectedIndex();
        if (selectedPromoIndex > 0) { // If a promotion is selected (not "Không áp dụng")
            try {
                List<Promotion> activePromotions = promotionDAO.findActivePromotions();
                if (selectedPromoIndex - 1 < activePromotions.size()) {
                    Promotion selectedPromo = activePromotions.get(selectedPromoIndex - 1);
                    // Calculate promotion discount as a percentage of subtotal
                    promotionDiscount = subtotal.multiply(selectedPromo.getPromoDiscountValue())
                                             .divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, 
                    "Lỗi khi áp dụng khuyến mãi: " + e.getMessage(), 
                    "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }

        // Update summary labels
        lbTongTien.setText(formatCurrency(subtotal));
        lbGiamGiaTot.setText(formatCurrency(totalDiscount.add(promotionDiscount)));

        // Calculate and update total after all discounts
        BigDecimal total = subtotal.subtract(totalDiscount).subtract(promotionDiscount);
        lbTong.setText(formatCurrency(total));
    }

    // Format currency for display
    private String formatCurrency(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) == 0) {
            return ""; // Return empty string for zero or null amounts
        }
        return String.format("%,d đ", amount.longValue());
    }

    private void loadActivePromotions() {
        try {
            // Temporarily remove action listener to prevent triggering updates during initialization
            java.awt.event.ActionListener[] actionListeners = cbbPhieuGiamGia.getActionListeners();
            for (java.awt.event.ActionListener listener : actionListeners) {
                cbbPhieuGiamGia.removeActionListener(listener);
            }
            
            // Clear existing items
            cbbPhieuGiamGia.removeAllItems();
            
            // Add default "Không áp dụng" option
            cbbPhieuGiamGia.addItem("Không áp dụng");
            
            // Get active promotions
            List<Promotion> activePromotions = promotionDAO.findActivePromotions();
            
            // Add each promotion to the combobox
            for (Promotion promotion : activePromotions) {
                cbbPhieuGiamGia.addItem(String.format("%s - Giảm %s%%", 
                    promotion.getPromoName(), 
                    promotion.getPromoDiscountValue()));
            }
            
            // Set default selection without triggering events
            cbbPhieuGiamGia.setSelectedIndex(0);
            
            // Restore action listeners
            for (java.awt.event.ActionListener listener : actionListeners) {
                cbbPhieuGiamGia.addActionListener(listener);
            }
            
        } catch (Exception e) {
            // Don't show error dialog during initialization to avoid popups
            if (this.isVisible()) {
                JOptionPane.showMessageDialog(this, 
                    "Lỗi khi tải danh sách khuyến mãi: " + e.getMessage(), 
                    "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
            }
            e.printStackTrace();
        }
    }
    
    public ViewBanHang() {
        initComponents();
        // Set this as the active instance
        setActiveInstance(this);
        
        // Load active promotions
        loadActivePromotions();

        // Clear the empty rows from tblHoaDon
        DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }

        loadProductVariants();
        initCartTable();

        // Hide invoice information by default
        // Hide value labels but keep static labels always visible
        lbMaHD.setVisible(false); // Hide the invoice code value
        jLabel8.setVisible(true); // Keep the "Mã Hóa Đơn" label always visible
        lbMaNV.setVisible(false);  // Hide the staff code value
        jLabel14.setVisible(true); // Keep the "Nhân Viên" label always visible
        lbNgayTao.setVisible(false); // Hide the creation date value
        jLabel15.setVisible(true);   // Keep the "Ngày Tạo" label always visible
    }

    public void refreshProductVariants() {
        loadProductVariants();
    }

    private void loadProductVariants() {
        try {
            // Get the table model
            DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
            model.setRowCount(0); // Clear existing data

            // Set column names for product variants
            String[] columnNames = {"Mã SP", "Tên SP", "Size", "Màu sắc", "Đế giày", "Số lượng", "Giá", "Hình ảnh"};
            model.setColumnIdentifiers(columnNames);

            // Fetch all variants using the class field DAO
            List<ProductVariant> variants = productVariantDAO.findAll();

            // Create caches to avoid repeated database queries
            Map<Integer, Product> productCache = new HashMap<>();
            Map<Integer, Size> sizeCache = new HashMap<>();
            Map<Integer, Color> colorCache = new HashMap<>();
            Map<Integer, SoleType> soleTypeCache = new HashMap<>();

            // Process each variant
            for (ProductVariant variant : variants) {
                // Get or fetch product
                Product product = productCache.computeIfAbsent(
                        variant.getProductId(),
                        id -> productDAO.findById(id)
                );

                // Get or fetch size
                Size size = sizeCache.computeIfAbsent(
                        variant.getSizeId(),
                        id -> sizeDAO.findById(id)
                );

                // Get or fetch color
                Color color = colorCache.computeIfAbsent(
                        variant.getColorId(),
                        id -> colorDAO.findById(id)
                );

                // Get or fetch sole type
                SoleType soleType = soleTypeCache.computeIfAbsent(
                        variant.getSoleId(),
                        id -> soleTypeDAO.findById(id)
                );

                // Add row with actual names
                model.addRow(new Object[]{
                    variant.getVariantSku(),
                    product != null ? product.getProductName() : "N/A",
                    size != null ? size.getSizeValue() : "N/A",
                    color != null ? color.getColorName() : "N/A",
                    soleType != null ? soleType.getSoleName() : "N/A",
                    variant.getQuantity(),
                    formatCurrency(variant.getVariantOrigPrice()),
                    variant.getVariantImgUrl()
                });
            }

            // Resize columns to fit content
            for (int i = 0; i < tblSanPham.getColumnCount(); i++) {
                tblSanPham.getColumnModel().getColumn(i).setPreferredWidth(100);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi tải danh sách sản phẩm: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void getMaQR(String ma) {
        try {
            // Clean and normalize the QR code content
            String variantSku = ma.trim();
            System.out.println("[DEBUG] Raw QR code input: " + variantSku);

            // Extract just the code part if it's in format "SKU: code"
            if (variantSku.startsWith("SKU:")) {
                variantSku = variantSku.substring(4).trim();
            }

            System.out.println("[DEBUG] Searching for product variant with SKU: " + variantSku);

            // Create a final copy of variantSku for use in lambda
            final String searchSku = variantSku.trim();

            // Find the product variant by SKU
            ProductVariant variant = productVariantDAO.findAll().stream()
                    .filter(v -> v.getVariantSku() != null && v.getVariantSku().trim().equalsIgnoreCase(searchSku))
                    .findFirst()
                    .orElse(null);

            if (variant != null) {
                System.out.println("[DEBUG] Found product variant: " + variant.getVariantSku());

                // Find the product for this variant
                Product product = productDAO.findById(variant.getProductId());
                if (product != null) {
                    System.out.println("[DEBUG] Found product: " + product.getProductName());

                    // Find the product in the table
                    DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
                    for (int i = 0; i < model.getRowCount(); i++) {
                        String productId = model.getValueAt(i, 0).toString().trim();
                        if (productId.equals(String.valueOf(product.getProductId()))) {
                            System.out.println("[DEBUG] Found product in table at row: " + i);
                            handleFoundProduct(model, i);
                            return;
                        }
                    }

                    // If we get here, product was found but not in the table
                    JOptionPane.showMessageDialog(this,
                            "Sản phẩm không có sẵn trong danh sách hiển thị.",
                            "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    System.err.println("[ERROR] Product not found for variant: " + variant.getVariantSku());
                    JOptionPane.showMessageDialog(this,
                            "Không tìm thấy thông tin sản phẩm.",
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                System.err.println("[ERROR] Product variant not found with SKU: " + variantSku);
                JOptionPane.showMessageDialog(this,
                        "Không tìm thấy sản phẩm với mã: " + variantSku,
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            // Error handling is already done above
            // No need for additional checks here
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Có lỗi xảy ra khi xử lý mã QR: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleFoundProduct(DefaultTableModel model, int rowIndex) {
        try {
            // Get product details
            String sku = model.getValueAt(rowIndex, 0).toString();
            String productName = model.getValueAt(rowIndex, 1).toString();
            BigDecimal price = new BigDecimal(model.getValueAt(rowIndex, 6).toString());

            // Prompt for quantity
            String quantityStr = JOptionPane.showInputDialog(this,
                    "Nhập số lượng cho sản phẩm: " + productName + " (Mã: " + sku + ")",
                    "Nhập số lượng",
                    JOptionPane.QUESTION_MESSAGE);

            if (quantityStr == null || quantityStr.trim().isEmpty()) {
                return; // User cancelled
            }

            try {
                int quantity = Integer.parseInt(quantityStr.trim());
                if (quantity <= 0) {
                    JOptionPane.showMessageDialog(this,
                            "Số lượng phải lớn hơn 0",
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Add to cart
                DefaultTableModel cartModel = (DefaultTableModel) tblGioHang.getModel();
                boolean productExists = false;

                // Check if product already exists in cart
                for (int j = 0; j < cartModel.getRowCount(); j++) {
                    String cartSku = cartModel.getValueAt(j, 1).toString();
                    if (cartSku.equals(sku)) {
                        // Update existing product quantity
                        int currentQty = Integer.parseInt(cartModel.getValueAt(j, 3).toString());
                        cartModel.setValueAt(currentQty + quantity, j, 3);
                        updateRowTotal(j);
                        productExists = true;
                        break;
                    }
                }

                // If product doesn't exist in cart, add new row
                if (!productExists) {
                    BigDecimal lineTotal = price.multiply(new BigDecimal(quantity));
                    Object[] rowData = {
                        "", // Empty for checkbox
                        sku,
                        productName,
                        quantity,
                        price,
                        0, // discount percentage
                        lineTotal // total
                    };
                    cartModel.addRow(rowData);
                }

                // Update cart summary
                updateCartSummary();

                // Select and scroll to the product in the table
                tblSanPham.setRowSelectionInterval(rowIndex, rowIndex);
                tblSanPham.scrollRectToVisible(tblSanPham.getCellRect(rowIndex, 0, true));

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng nhập số lượng hợp lệ (số nguyên dương)",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this,
                        "Có lỗi xảy ra khi thêm sản phẩm vào giỏ hàng: " + e.getMessage(),
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Có lỗi xảy ra khi xử lý sản phẩm: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        btn_Them1 = new javax.swing.JButton();
        btnTheHoaDon = new javax.swing.JButton();
        btn_Them3 = new javax.swing.JButton();
        btn_Them5 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jComboBox3 = new javax.swing.JComboBox<>();
        jComboBox4 = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtMaKhachhang = new javax.swing.JTextField();
        btn_Them4 = new javax.swing.JButton();
        btn_khachvanglai = new javax.swing.JButton();
        pnl_thongtinhoadon = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        lbMaHD = new javax.swing.JLabel();
        lbNgayTao = new javax.swing.JLabel();
        lbMaNV = new javax.swing.JLabel();
        lbTenKhachHang = new javax.swing.JLabel();
        lbTongTien = new javax.swing.JLabel();
        cbbPhieuGiamGia = new javax.swing.JComboBox<>();
        cbbHinhThucThanhToan = new javax.swing.JComboBox<>();
        txtTienKhachDua = new javax.swing.JTextField();
        txtTienKhachCK = new javax.swing.JTextField();
        lbTienThua = new javax.swing.JLabel();
        lbTong = new javax.swing.JLabel();
        btnThanhToan = new javax.swing.JButton();
        lbGiamGiaTot = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        btn_chon_san_pham = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblGioHang = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 255));
        jLabel1.setText("BÁN HÀNG");

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));

        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã hóa đơn", "Mã nhân viên", "Trạng thái", "Ngày tạo"
            }
        ));
        tblHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tblHoaDonMouseEntered(evt);
            }
        });
        jScrollPane3.setViewportView(tblHoaDon);

        btn_Them1.setBackground(new java.awt.Color(0, 102, 255));
        btn_Them1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_Them1.setForeground(new java.awt.Color(255, 255, 255));
        btn_Them1.setText("Làm Mới");
        btn_Them1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Them1btn_ThemActionPerformed(evt);
            }
        });

        btnTheHoaDon.setBackground(new java.awt.Color(0, 102, 255));
        btnTheHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnTheHoaDon.setForeground(new java.awt.Color(255, 255, 255));
        btnTheHoaDon.setText("Tạo Mới Hóa Đơn");
        btnTheHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTheHoaDonbtn_ThemActionPerformed(evt);
            }
        });

        btn_Them3.setBackground(new java.awt.Color(0, 102, 255));
        btn_Them3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_Them3.setForeground(new java.awt.Color(255, 255, 255));
        btn_Them3.setText("Quét Mã QR");
        btn_Them3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Them3btn_ThemActionPerformed(evt);
            }
        });

        btn_Them5.setBackground(new java.awt.Color(0, 102, 255));
        btn_Them5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_Them5.setForeground(new java.awt.Color(255, 255, 255));
        btn_Them5.setText("hủy HD");
        btn_Them5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Them5btn_ThemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(btn_Them3)
                .addGap(28, 28, 28)
                .addComponent(btnTheHoaDon)
                .addGap(119, 119, 119)
                .addComponent(btn_Them5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_Them1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 758, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_Them3)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnTheHoaDon)
                        .addComponent(btn_Them1)
                        .addComponent(btn_Them5, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        jPanel4.setForeground(new java.awt.Color(255, 255, 255));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(290, 290, 290)
                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 817, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        jPanel5.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 210, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel3.setText("Giỏ Hàng");

        jLabel4.setText("Hóa đơn");

        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        jLabel6.setText("Thông tin khách hàng");

        txtMaKhachhang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaKhachhangActionPerformed(evt);
            }
        });

        btn_Them4.setBackground(new java.awt.Color(0, 102, 255));
        btn_Them4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_Them4.setForeground(new java.awt.Color(255, 255, 255));
        btn_Them4.setText("Chọn");
        btn_Them4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Them4btn_ThemActionPerformed(evt);
            }
        });

        btn_khachvanglai.setForeground(new java.awt.Color(51, 102, 255));
        btn_khachvanglai.setText("Khách vãng lai");
        btn_khachvanglai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_khachvanglaiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_khachvanglai)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel6)
                        .addComponent(txtMaKhachhang, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_Them4, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMaKhachhang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_Them4))
                .addGap(18, 18, 18)
                .addComponent(btn_khachvanglai)
                .addContainerGap(10, Short.MAX_VALUE))
        );

        pnl_thongtinhoadon.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        jLabel7.setText("Thông tin hóa đơn");

        jLabel8.setText("Mã Hòa đơn :");

        jLabel9.setText("Ngày Tạo :");

        jLabel10.setText("Mã Nhân Viên :");

        jLabel11.setText("Tên Khách Hàng :");

        jLabel12.setText("Tổng tiền :");

        jLabel13.setText("Phiếu giảm giá :");

        jLabel14.setText("Hình Thức TT :");

        jLabel15.setText("Tiền Khách đưa :");

        jLabel16.setText("Tiền Khách CK :");

        jLabel17.setText("Tiền Thừa :");

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 51, 51));
        jLabel18.setText("Tổng :");

        lbNgayTao.setToolTipText("");

        cbbPhieuGiamGia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));
        cbbPhieuGiamGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbPhieuGiamGiaActionPerformed(evt);
            }
        });

        cbbHinhThucThanhToan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tiền mặt", "Chuyển khoản", " ", " ", " " }));
        cbbHinhThucThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbHinhThucThanhToanActionPerformed(evt);
            }
        });

        txtTienKhachDua.setText("0");
        txtTienKhachDua.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtTienKhachDuaCaretUpdate(evt);
            }
        });
        txtTienKhachDua.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTienKhachDuaKeyReleased(evt);
            }
        });

        txtTienKhachCK.setText("0");
        txtTienKhachCK.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtTienKhachCKCaretUpdate(evt);
            }
        });
        txtTienKhachCK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTienKhachCKActionPerformed(evt);
            }
        });

        lbTong.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbTong.setForeground(new java.awt.Color(255, 0, 51));

        btnThanhToan.setBackground(new java.awt.Color(0, 102, 255));
        btnThanhToan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThanhToan.setForeground(new java.awt.Color(255, 255, 255));
        btnThanhToan.setText("thanh toán");
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanbtn_ThemActionPerformed(evt);
            }
        });

        lbGiamGiaTot.setFont(new java.awt.Font("Segoe UI", 2, 10)); // NOI18N
        lbGiamGiaTot.setForeground(new java.awt.Color(255, 0, 51));

        javax.swing.GroupLayout pnl_thongtinhoadonLayout = new javax.swing.GroupLayout(pnl_thongtinhoadon);
        pnl_thongtinhoadon.setLayout(pnl_thongtinhoadonLayout);
        pnl_thongtinhoadonLayout.setHorizontalGroup(
            pnl_thongtinhoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_thongtinhoadonLayout.createSequentialGroup()
                .addGroup(pnl_thongtinhoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_thongtinhoadonLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnl_thongtinhoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl_thongtinhoadonLayout.createSequentialGroup()
                                .addGroup(pnl_thongtinhoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel16)
                                    .addComponent(jLabel10))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(pnl_thongtinhoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lbNgayTao, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                                    .addComponent(lbMaHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lbMaNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lbTenKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lbTongTien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(pnl_thongtinhoadonLayout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addGap(24, 24, 24)
                                .addComponent(lbTong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(pnl_thongtinhoadonLayout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addGap(67, 67, 67)
                                .addComponent(lbTienThua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(pnl_thongtinhoadonLayout.createSequentialGroup()
                                .addGroup(pnl_thongtinhoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnl_thongtinhoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(pnl_thongtinhoadonLayout.createSequentialGroup()
                                            .addComponent(jLabel14)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(cbbHinhThucThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(1, 1, 1))
                                        .addGroup(pnl_thongtinhoadonLayout.createSequentialGroup()
                                            .addComponent(jLabel13)
                                            .addGap(18, 18, 18)
                                            .addComponent(cbbPhieuGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(pnl_thongtinhoadonLayout.createSequentialGroup()
                                        .addComponent(jLabel15)
                                        .addGap(18, 18, 18)
                                        .addGroup(pnl_thongtinhoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtTienKhachCK, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtTienKhachDua, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(pnl_thongtinhoadonLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(lbGiamGiaTot, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(pnl_thongtinhoadonLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnl_thongtinhoadonLayout.setVerticalGroup(
            pnl_thongtinhoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_thongtinhoadonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addGroup(pnl_thongtinhoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(lbMaHD))
                .addGap(18, 18, 18)
                .addGroup(pnl_thongtinhoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(lbNgayTao))
                .addGap(18, 18, 18)
                .addGroup(pnl_thongtinhoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(lbMaNV))
                .addGap(18, 18, 18)
                .addGroup(pnl_thongtinhoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(lbTenKhachHang))
                .addGap(18, 18, 18)
                .addGroup(pnl_thongtinhoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(lbTongTien))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_thongtinhoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbbPhieuGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbGiamGiaTot)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_thongtinhoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(cbbHinhThucThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnl_thongtinhoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtTienKhachDua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnl_thongtinhoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtTienKhachCK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnl_thongtinhoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_thongtinhoadonLayout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(18, 18, 18)
                        .addGroup(pnl_thongtinhoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(lbTong)))
                    .addComponent(lbTienThua, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

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
        jScrollPane5.setViewportView(tblSanPham);

        btn_chon_san_pham.setBackground(new java.awt.Color(0, 102, 255));
        btn_chon_san_pham.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_chon_san_pham.setForeground(new java.awt.Color(255, 255, 255));
        btn_chon_san_pham.setText("Chọn");
        btn_chon_san_pham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_chon_san_phambtn_ThemActionPerformed(evt);
            }
        });

        jLabel2.setText("Sản phẩm");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(469, 469, 469)
                        .addComponent(btn_chon_san_pham)
                        .addGap(0, 146, Short.MAX_VALUE))
                    .addComponent(jScrollPane5))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_chon_san_pham)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        tblGioHang.setModel(new javax.swing.table.DefaultTableModel(
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
        tblGioHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblGioHangMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblGioHang);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(58, 58, 58)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(6, 6, 6)
                                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(707, 707, 707))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 761, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnl_thongtinhoadon, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(272, 272, 272))
            .addGroup(layout.createSequentialGroup()
                .addGap(537, 537, 537)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(485, 485, 485)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addGap(11, 11, 11)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pnl_thongtinhoadon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(196, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_Them1btn_ThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Them1btn_ThemActionPerformed

    }//GEN-LAST:event_btn_Them1btn_ThemActionPerformed

    // Field to store temporary customer
    private Customer currentTemporaryCustomer = null;
    
    // Khai báo callback interface
    public interface CustomerSelectionCallback {
        void onCustomerSelected(Customer customer);
    }
    
    // Khởi tạo callback
    private final CustomerSelectionCallback customerSelectionCallback = new CustomerSelectionCallback() {
        @Override
        public void onCustomerSelected(Customer customer) {
            setCustomerToForm(customer);
        }
    };

    private void btnTheHoaDonbtn_ThemActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            // Get customer code from form
            String customerCode = txtMaKhachhang.getText().trim();
            Customer customer = null;

            // Check if we have a temporary customer from the guest button
            if (currentTemporaryCustomer != null && currentTemporaryCustomer.getCustomerId() != null) {
                customer = currentTemporaryCustomer;
                System.out.println("[DEBUG] Using currentTemporaryCustomer: " + customer.getCustomerCode());
            }
            // If no temporary customer, try to find by code
            else if (!customerCode.isEmpty()) {
                customer = customerDAO.findByCode(customerCode);
                if (customer == null) {
                    JOptionPane.showMessageDialog(this,
                            "Không tìm thấy thông tin khách hàng. Vui lòng chọn lại khách hàng.",
                            "Không tìm thấy khách hàng",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                System.out.println("[DEBUG] Sử dụng khách hàng từ mã: " + customerCode);
            } else {
                // No customer selected - show error
                JOptionPane.showMessageDialog(this,
                        "Vui lòng chọn khách hàng hoặc nhấn 'Khách vãng lai' để tạo mới.",
                        "Chưa chọn khách hàng",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Get staff info from session
            Staff staff = SessionManager.getInstance().getCurrentStaff();
            if (staff == null) {
                // If no staff in session, try to get a default staff (fallback)
                StaffDAO staffDAO = new StaffDAOImpl();
                staff = staffDAO.findByStaffCode("NV01");

                if (staff == null) {
                    // If still no staff, get the first active staff
                    List<Staff> staffList = staffDAO.findAll();
                    if (staffList != null && !staffList.isEmpty()) {
                        staff = staffList.get(0);
                    } else {
                        throw new Exception("Không tìm thấy thông tin nhân viên. Vui lòng đăng nhập lại.");
                    }
                }

                // Log the fallback for debugging
                System.out.println("Using fallback staff: " + staff.getStaffCode());
            }

            // Generate a new order code (HD + timestamp)
            String orderCode = "HD" + System.currentTimeMillis();

            // Get current date/time
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            String formattedDate = now.format(formatter);

            // Create new order object with all required fields
            Order newOrder = new Order();
            newOrder.setStaffId(staff.getStaffId());
            newOrder.setOrderCode(orderCode);
            newOrder.setOrderStatus("Chưa thanh toán");
            newOrder.setOrderCreatedAt(now);
            // Set default values for required fields
            newOrder.setOrderFinalAmount(BigDecimal.ZERO); // Default to 0
            newOrder.setOrderPaymentMethod("Tiền mặt"); // Default payment method

            // Ensure we have a valid customer for the order
            if (customer != null && customer.getCustomerId() != null) {
                // 1. Use the selected customer if available
                newOrder.setCustomerId(customer.getCustomerId());
                System.out.println("[DEBUG] Using selected customer ID: " + customer.getCustomerId());
            } else {

                
                // 2. Try to find the default guest customer (CUS0000)
                Customer guestCustomer = customerDAO.findByCode("CUS0000");
                if (guestCustomer != null) {
                    newOrder.setCustomerId(guestCustomer.getCustomerId());
                    System.out.println("[DEBUG] Using guest customer ID: " + guestCustomer.getCustomerId());
                    
                    // Update the customer display
                    setCustomerToForm(guestCustomer);
                } else {
                    // If no guest customer exists, show error
                    JOptionPane.showMessageDialog(this,
                            "Không tìm thấy tài khoản khách vãng lai mặc định (CUS0000).\nVui lòng liên hệ quản trị viên.",
                            "Lỗi khách hàng",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Save order to database
            try {
                OrderDAO orderDAO = new OrderDAOImpl();
                System.out.println("[DEBUG] Saving order with data: " + newOrder);

                // Save the order
                Order savedOrder = orderDAO.save(newOrder);

                if (savedOrder == null || savedOrder.getOrderId() <= 0) {
                    throw new Exception("Không thể lưu hóa đơn vào cơ sở dữ liệu: Invalid order ID");
                }
                System.out.println("[DEBUG] Order saved successfully with ID: " + savedOrder.getOrderId());

                // Save order details from cart
                DefaultTableModel cartModel = (DefaultTableModel) tblGioHang.getModel();
                List<OrderDetail> orderDetails = new ArrayList<>();

                for (int i = 0; i < cartModel.getRowCount(); i++) {
                    try {
                        String variantSku = (String) cartModel.getValueAt(i, 1);
                        int quantity = (int) cartModel.getValueAt(i, 3);
                        BigDecimal price = (BigDecimal) cartModel.getValueAt(i, 4);
                        BigDecimal discountPercent = (BigDecimal) cartModel.getValueAt(i, 5);

                        // Find variant by SKU
                        // First try to find by SKU directly
                        ProductVariant variant = null;
                        try {
                            // If variantSku is numeric, try to find by ID
                            int variantId = Integer.parseInt(variantSku);
                            variant = productVariantDAO.findById(variantId);
                        } catch (NumberFormatException e) {
                            // If not numeric, try to find by SKU
                            List<ProductVariant> variants = productVariantDAO.findAll();
                            for (ProductVariant v : variants) {
                                if (variantSku.equals(v.getVariantSku())) {
                                    variant = v;
                                    break;
                                }
                            }
                        }

                        if (variant == null) {
                            System.err.println("Variant not found for SKU/ID: " + variantSku);
                            continue;
                        }

                        // Create order detail
                        OrderDetail detail = new OrderDetail();
                        detail.setOrderId(savedOrder.getOrderId());
                        detail.setVariantId(variant.getVariantId());
                        detail.setDetailQuantity(quantity);
                        detail.setDetailUnitPrice(price);
                        detail.setDetailDiscountPercent(discountPercent);

                        orderDetails.add(detail);

                    } catch (Exception e) {
                        System.err.println("Error processing cart row " + i + ": " + e.getMessage());
                        e.printStackTrace();
                    }
                }

                // Save all order details
                if (!orderDetails.isEmpty()) {
                    orderDetailDAO.saveAll(orderDetails);
                    System.out.println("Saved " + orderDetails.size() + " order details for order ID: " + savedOrder.getOrderId());
                } else {
                    System.out.println("No order details to save for order ID: " + savedOrder.getOrderId());
                }
            } catch (Exception e) {
                System.err.println("[ERROR] Error saving order: " + e.getMessage());
                e.printStackTrace();
                throw e;
            }

            // Get or initialize the table model
            DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();

            // Initialize columns if table is empty
            if (model.getColumnCount() == 0) {
                String[] columnNames = {"Mã hóa đơn", "Mã nhân viên", "Trạng thái", "Ngày tạo"};
                for (String columnName : columnNames) {
                    model.addColumn(columnName);
                }
            }

            // Add the new order to the table
            Object[] rowData = new Object[]{
                orderCode, // Mã hóa đơn
                staff.getStaffCode(), // Mã nhân viên
                "Chưa thanh toán", // Start as unpaid
                formattedDate // Ngày tạo
            };

            model.addRow(rowData);

            // Auto-resize columns
            for (int i = 0; i < tblHoaDon.getColumnCount(); i++) {
                tblHoaDon.getColumnModel().getColumn(i).setPreferredWidth(150);
            }

            // Select the newly created order
            tblHoaDon.setRowSelectionInterval(model.getRowCount() - 1, model.getRowCount() - 1);

            // Show and update the invoice information
            // Static labels (jLabel8, jLabel14, jLabel15) remain always visible
            // Only update the value labels
            lbMaHD.setVisible(true);
            lbMaHD.setText(orderCode);

            // Update staff code
            lbMaNV.setVisible(true);
            lbMaNV.setText(staff.getStaffCode());

            // Show and update creation date
            lbNgayTao.setVisible(true);
            jLabel15.setVisible(true);
            lbNgayTao.setText(formattedDate);

            // Show success message
            JOptionPane.showMessageDialog(this,
                    "Đã tạo hóa đơn mới: " + orderCode,
                    "Thành công",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi tạo hóa đơn mới: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnTheHoaDonbtn_ThemActionPerformed

    private void btn_Them3btn_ThemActionPerformed(java.awt.event.ActionEvent evt) {
        QuetQRBanHang banHang = new QuetQRBanHang(this);
        banHang.setVisible(true);
    }
    
    private void btn_Them5btn_ThemActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO: Add your handling code here
    }

    private void tblHoaDonMouseClicked(java.awt.event.MouseEvent evt) {
        // Lấy model của bảng giỏ hàng
        DefaultTableModel cartModel = (DefaultTableModel) tblGioHang.getModel();
        
        try {
            // Lấy dòng được chọn
            int selectedRow = tblHoaDon.getSelectedRow();
            if (selectedRow == -1) {
                return;
            }

            // Lấy mã hóa đơn
            Object orderCodeObj = tblHoaDon.getValueAt(selectedRow, 0);
            if (orderCodeObj == null) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy mã hóa đơn", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String orderCode = orderCodeObj.toString();

            // Cập nhật thông tin hóa đơn
            lbMaHD.setVisible(true);
            lbMaHD.setText(orderCode);
            
            // Lấy thông tin nhân viên và ngày tạo
            String staffCode = tblHoaDon.getValueAt(selectedRow, 1) != null ? 
                             tblHoaDon.getValueAt(selectedRow, 1).toString() : "N/A";
            String createDate = tblHoaDon.getValueAt(selectedRow, 3) != null ? 
                              tblHoaDon.getValueAt(selectedRow, 3).toString() : "N/A";
            
            lbMaNV.setText(staffCode);
            lbMaNV.setVisible(true);
            lbNgayTao.setText(createDate);
            lbNgayTao.setVisible(true);

            // Xóa dữ liệu cũ trong giỏ hàng
            cartModel.setRowCount(0);

            // Lấy thông tin đơn hàng từ database
            Optional<Order> orderOpt = orderDAO.findByCode(orderCode);
            if (!orderOpt.isPresent()) {
                throw new Exception("Không tìm thấy thông tin đơn hàng");
            }

            Order order = orderOpt.get();
            
            // Lấy danh sách sản phẩm trong đơn hàng
            List<OrderDetail> orderDetails = orderDetailDAO.findByOrderId(order.getOrderId());
            
            // Thêm sản phẩm vào giỏ hàng
            for (OrderDetail detail : orderDetails) {
                if (detail == null) continue;
                
                ProductVariant variant = productVariantDAO.findById(detail.getVariantId());
                if (variant == null) continue;
                
                Product product = productDAO.findById(variant.getProductId());
                if (product == null) continue;
                
                // Lấy thông tin màu sắc và kích thước
                Color color = colorDAO.findById(variant.getColorId());
                Size size = sizeDAO.findById(variant.getSizeId());
                
                // Tạo tên sản phẩm đầy đủ
                String productName = product.getProductName();
                if (color != null) productName += " - " + color.getColorName();
                if (size != null) productName += " - " + size.getSizeCode();
                
                // Tính toán thành tiền
                int quantity = detail.getDetailQuantity();
                BigDecimal price = detail.getDetailUnitPrice();
                BigDecimal discount = detail.getDetailDiscountPercent() != null ? 
                                   detail.getDetailDiscountPercent() : BigDecimal.ZERO;
                BigDecimal total = price.multiply(BigDecimal.valueOf(quantity))
                                     .multiply(BigDecimal.ONE.subtract(discount.divide(new BigDecimal(100))));
                
                // Thêm vào giỏ hàng
                cartModel.addRow(new Object[]{
                    cartModel.getRowCount() + 1,  // STT
                    variant.getVariantSku(),      // Mã SP
                    productName,                  // Tên SP + Màu + Size
                    quantity,                     // Số lượng
                    price,                        // Đơn giá
                    discount,                     // Giảm giá (%)
                    total,                        // Thành tiền
                    variant.getVariantId()        // ID biến thể để thao tác sau này
                });
            }
            
            // Cập nhật tổng tiền
            updateCartSummary();
            
            // Làm mới giao diện
            tblGioHang.revalidate();
            tblGioHang.repaint();
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi tải chi tiết hóa đơn: " + e.getMessage(), 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_tblHoaDonMouseClicked

    private void tblHoaDonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tblHoaDonMouseEntered

    private void tblGioHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblGioHangMouseClicked

    }//GEN-LAST:event_tblGioHangMouseClicked

    private void btn_chon_san_phambtn_ThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_chon_san_phambtn_ThemActionPerformed
        // TODO add your handling code here:
        int selectedRow = tblSanPham.getSelectedRow();

        // Check if a product is selected
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần thêm vào giỏ hàng", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Get product variant details from the selected row
            String sku = tblSanPham.getValueAt(selectedRow, 0).toString();
            String productName = tblSanPham.getValueAt(selectedRow, 1).toString();
            BigDecimal price = new BigDecimal(tblSanPham.getValueAt(selectedRow, 6).toString().replaceAll("[^\\d.]", ""));
            int availableQty = Integer.parseInt(tblSanPham.getValueAt(selectedRow, 5).toString());

            // Show quantity input dialog
            String qtyStr = JOptionPane.showInputDialog(this,
                    "Nhập số lượng cho sản phẩm: " + productName + "\nSố lượng có sẵn: " + availableQty,
                    "Nhập số lượng",
                    JOptionPane.PLAIN_MESSAGE);

            // Check if user cancelled the input
            if (qtyStr == null) {
                return;
            }

            // Validate quantity input
            int quantity;
            try {
                quantity = Integer.parseInt(qtyStr);
                if (quantity <= 0) {
                    throw new NumberFormatException();
                }
                if (quantity > availableQty) {
                    JOptionPane.showMessageDialog(this, "Số lượng vượt quá số lượng tồn kho", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Calculate line total (price * quantity)
            BigDecimal lineTotal = price.multiply(BigDecimal.valueOf(quantity));

            // Get cart table model
            DefaultTableModel cartModel = (DefaultTableModel) tblGioHang.getModel();

            // Check if product already exists in cart
            int existingRow = -1;
            for (int i = 0; i < cartModel.getRowCount(); i++) {
                if (cartModel.getValueAt(i, 1).equals(sku)) { // Check by SKU
                    existingRow = i;
                    break;
                }
            }

            if (existingRow >= 0) {
                // Update existing item quantity and total
                int currentQty = (int) cartModel.getValueAt(existingRow, 3);
                int newQty = currentQty + quantity;
                if (newQty > availableQty) {
                    JOptionPane.showMessageDialog(this, "Tổng số lượng vượt quá số lượng tồn kho", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                BigDecimal currentTotal = (BigDecimal) cartModel.getValueAt(existingRow, 6);
                BigDecimal newTotal = currentTotal.add(lineTotal);

                cartModel.setValueAt(newQty, existingRow, 3);
                cartModel.setValueAt(price, existingRow, 4);
                cartModel.setValueAt(BigDecimal.ZERO, existingRow, 5); // Reset discount to 0
                cartModel.setValueAt(newTotal, existingRow, 6);
            } else {
                // Add new item to cart
                Object[] rowData = {
                    cartModel.getRowCount() + 1, // STT
                    sku, // Mã SP
                    productName, // Tên sản phẩm
                    quantity, // Số lượng
                    price, // Đơn giá
                    BigDecimal.ZERO, // Phần trăm giảm (mặc định 0)
                    lineTotal // Thành tiền
                };
                cartModel.addRow(rowData);
            }

            // Update cart summary and refresh UI
            updateCartSummary();

            // Force UI refresh
            tblGioHang.revalidate();
            tblGioHang.repaint();

            System.out.println("Cart updated with " + cartModel.getRowCount() + " items");

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi thêm sản phẩm vào giỏ hàng", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_chon_san_phambtn_ThemActionPerformed

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked

    }//GEN-LAST:event_tblSanPhamMouseClicked

    // Callback implementation for customer selection
    private final ViewThemKhachHang.ViewBanHangCallback customerSelectionCallback = new ViewThemKhachHang.ViewBanHangCallback() {
        @Override
        public void onCustomerSelected(String tenKH, String sdt, String email) {
            // Update the customer information in the form
            txtMaKhachhang.setText(sdt); // Using phone number as customer ID
            lbTenKhachHang.setText(tenKH);

            // Enable the create invoice button if it was disabled
            btnTheHoaDon.setEnabled(true);
        }
    };

    private void btn_Them4btn_ThemActionPerformed(java.awt.event.ActionEvent evt) {
        java.awt.EventQueue.invokeLater(() -> {
            ViewThemKhachHang viewThemKhachHang = new ViewThemKhachHang();
            viewThemKhachHang.setViewBanHangCallback(customerSelectionCallback);
            viewThemKhachHang.setLocationRelativeTo(null);
            viewThemKhachHang.setVisible(true);
        });
    }

    private void txtMaKhachhangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaKhachhangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaKhachhangActionPerformed

    private void btnThanhToanbtn_ThemActionPerformed(java.awt.event.ActionEvent evt) {
        Connection conn = null;
        try {
            // Check if cart is empty
            if (tblGioHang.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this,
                        "Giỏ hàng đang trống. Vui lòng thêm sản phẩm vào giỏ hàng trước khi thanh toán.",
                        "Giỏ hàng trống",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Khai báo cartModel và tính totalAmount dùng cho toàn bộ hàm
            DefaultTableModel cartModel = (DefaultTableModel) tblGioHang.getModel();
            BigDecimal totalAmount = BigDecimal.ZERO;
            for (int i = 0; i < cartModel.getRowCount(); i++) {
                BigDecimal rowTotal = (BigDecimal) cartModel.getValueAt(i, 6);
                totalAmount = totalAmount.add(rowTotal);
            }

            // Check if customer is selected
            if (txtMaKhachhang.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng chọn khách hàng trước khi thanh toán.",
                        "Chưa chọn khách hàng",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Get customer ID from the current temporary customer or find by code
            int customerId;
            if (currentTemporaryCustomer != null && currentTemporaryCustomer.getCustomerId() != null) {
                customerId = currentTemporaryCustomer.getCustomerId();
                System.out.println("[DEBUG] Using customer ID from currentTemporaryCustomer: " + customerId);
            } else {
                String customerCode = txtMaKhachhang.getText().trim();
                Customer customer = customerDAO.findByCode(customerCode);
                if (customer == null) {
                    JOptionPane.showMessageDialog(this, 
                        "Không tìm thấy thông tin khách hàng", 
                        "Lỗi", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                customerId = customer.getCustomerId();
                System.out.println("[DEBUG] Found customer by code " + customerCode + ", ID: " + customerId);
            }

            // Get payment method
            String paymentMethod = cbbHinhThucThanhToan.getSelectedItem() != null
                    ? cbbHinhThucThanhToan.getSelectedItem().toString()
                    : "Tiền mặt";

            // Kiểm tra số tiền thanh toán
            BigDecimal paidAmount = BigDecimal.ZERO;
            if (paymentMethod.equals("Tiền mặt")) {
                try {
                    String tienKhachDuaText = txtTienKhachDua.getText().trim().replaceAll("\\.|,\\s*", "");
                    if (tienKhachDuaText.isEmpty()) {
                        JOptionPane.showMessageDialog(this,
                            "Vui lòng nhập số tiền khách đưa",
                            "Thiếu thông tin",
                            JOptionPane.WARNING_MESSAGE);
                        txtTienKhachDua.requestFocus();
                        return;
                    }
                    paidAmount = new BigDecimal(tienKhachDuaText);
                    if (paidAmount.compareTo(totalAmount) < 0) {
                        JOptionPane.showMessageDialog(this,
                            "Số tiền thanh toán không đủ. Cần thêm: " + formatCurrency(totalAmount.subtract(paidAmount)),
                            "Số tiền không đủ",
                            JOptionPane.WARNING_MESSAGE);
                        txtTienKhachDua.requestFocus();
                        return;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this,
                        "Số tiền không hợp lệ. Vui lòng nhập lại",
                        "Lỗi định dạng",
                        JOptionPane.ERROR_MESSAGE);
                    txtTienKhachDua.requestFocus();
                    return;
                }
            } else if (paymentMethod.equals("Chuyển khoản")) {
                try {
                    String tienChuyenKhoanText = txtTienKhachCK.getText().trim().replaceAll("\\.|,\\s*", "");
                    if (tienChuyenKhoanText.isEmpty()) {
                        JOptionPane.showMessageDialog(this,
                            "Vui lòng nhập số tiền chuyển khoản",
                            "Thiếu thông tin",
                            JOptionPane.WARNING_MESSAGE);
                        txtTienKhachCK.requestFocus();
                        return;
                    }
                    paidAmount = new BigDecimal(tienChuyenKhoanText);
                    if (paidAmount.compareTo(totalAmount) < 0) {
                        JOptionPane.showMessageDialog(this,
                            "Số tiền chuyển khoản không đủ. Cần thêm: " + formatCurrency(totalAmount.subtract(paidAmount)),
                            "Số tiền không đủ",
                            JOptionPane.WARNING_MESSAGE);
                        txtTienKhachCK.requestFocus();
                        return;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this,
                        "Số tiền không hợp lệ. Vui lòng nhập lại",
                        "Lỗi định dạng",
                        JOptionPane.ERROR_MESSAGE);
                    txtTienKhachCK.requestFocus();
                    return;
                }
            }

            // Xác nhận thanh toán
            String confirmMessage = "Xác nhận thanh toán đơn hàng với tổng tiền: " + formatCurrency(totalAmount) + "\n"
                + "Hình thức thanh toán: " + paymentMethod + "\n";
            
            if (paymentMethod.equals("Tiền mặt") && paidAmount.compareTo(totalAmount) > 0) {
                confirmMessage += "Tiền thừa: " + formatCurrency(paidAmount.subtract(totalAmount)) + "\n";
            }
            
            confirmMessage += "Bạn có chắc chắn muốn thanh toán?";
            
            int confirm = JOptionPane.showConfirmDialog(this,
                confirmMessage,
                "Xác nhận thanh toán",
                JOptionPane.YES_NO_OPTION);

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            // Tạo mã đơn hàng mới
            String orderCode = generateOrderCode();
            
            // Tạo đối tượng đơn hàng
            Order order = Order.builder()
                .orderCode(orderCode)
                .orderStatus("Chưa thanh toán")
                .orderCreatedAt(java.time.LocalDateTime.now())
                .customerId(customerId)
                .orderFinalAmount(totalAmount)
                .orderPaymentMethod(paymentMethod)
                .build();
            
            // Lấy thông tin nhân viên
            Staff staff = SessionManager.getInstance().getCurrentStaff();
            if (staff != null) {
                order.setStaffId(staff.getStaffId());
            } else {
                StaffDAO staffDAO = new StaffDAOImpl();
                staff = staffDAO.findByStaffCode("NV01");
                if (staff != null) {
                    order.setStaffId(staff.getStaffId());
                } else {
                    List<Staff> staffList = staffDAO.findAll();
                    if (!staffList.isEmpty()) {
                        order.setStaffId(staffList.get(0).getStaffId());
                    } else {
                        JOptionPane.showMessageDialog(this,
                            "Không tìm thấy thông tin nhân viên. Vui lòng đăng nhập lại.",
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            }

            // Bắt đầu transaction
            conn = XJdbc.openConnection();
            conn.setAutoCommit(false);
            
            try {
                // Lưu đơn hàng
                Order savedOrder = orderDAO.save(order);
                if (savedOrder == null) {
                    throw new Exception("Không thể tạo đơn hàng. Vui lòng thử lại.");
                }

                // Lưu chi tiết đơn hàng và cập nhật tồn kho
                for (int i = 0; i < cartModel.getRowCount(); i++) {
                    String sku = cartModel.getValueAt(i, 1).toString();
                    int quantity = Integer.parseInt(cartModel.getValueAt(i, 3).toString());
                    BigDecimal price = (BigDecimal) cartModel.getValueAt(i, 4);
                    
                    // Lấy thông tin sản phẩm
                    Optional<ProductVariant> variantOpt = productVariantDAO.findBySku(sku);
                    if (!variantOpt.isPresent()) {
                        throw new Exception("Không tìm thấy thông tin sản phẩm: " + sku);
                    }
                    ProductVariant variant = variantOpt.get();
                    
                    // Kiểm tra tồn kho
                    if (variant.getQuantity() < quantity) {
                        throw new Exception("Không đủ hàng tồn kho cho sản phẩm: " + sku);
                    }
                    
                    // Tạo chi tiết đơn hàng
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setOrderId(savedOrder.getOrderId());
                    orderDetail.setVariantId(variant.getVariantId());
                    orderDetail.setDetailQuantity(quantity);
                    orderDetail.setDetailUnitPrice(price);
                    orderDetail.setDetailDiscountPercent(BigDecimal.ZERO);
                    orderDetail.setDetailTotal(price.multiply(BigDecimal.valueOf(quantity)));
                    
                    // Lưu chi tiết đơn hàng
                    orderDetailDAO.save(orderDetail);
                    
                    // Cập nhật số lượng tồn kho
                    variant.setQuantity(variant.getQuantity() - quantity);
                    productVariantDAO.update(variant);
                }
                
                // Cập nhật trạng thái đơn hàng thành đã thanh toán
                savedOrder.setOrderStatus("Đã thanh toán");
                orderDAO.update(savedOrder);
                
                // Commit transaction
                conn.commit();
                
                // Thông báo thành công
                JOptionPane.showMessageDialog(this,
                    "Thanh toán thành công! Mã đơn hàng: " + orderCode,
                    "Thành công",
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Xóa giỏ hàng
                cartModel.setRowCount(0);
                updateCartSummary();
                
            } catch (Exception e) {
                if (conn != null) {
                    try {
                        conn.rollback();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
                throw e;
            } finally {
                if (conn != null) {
                    try {
                        conn.setAutoCommit(true);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Có lỗi xảy ra: " + e.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
        } finally {
            if (conn != null) {
                try {
                    if (!conn.isClosed()) {
                        conn.close();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        }

    private void txtTienKhachCKCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtTienKhachCKCaretUpdate
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTienKhachCKCaretUpdate

    private void txtTienKhachCKActionPerformed(java.awt.event.ActionEvent evt) {
        // Tự động chuyển focus sang nút thanh toán khi nhấn Enter
        btnThanhToan.requestFocusInWindow();
    }

    // Phương thức tạo mã đơn hàng tự động
    private String generateOrderCode() {
        // Tạo mã đơn hàng dựa trên thời gian hiện tại (đảm bảo độc nhất)
        return "HD" + System.currentTimeMillis();
    }

    private void txtTienKhachDuaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTienKhachDuaKeyReleased

    }//GEN-LAST:event_txtTienKhachDuaKeyReleased

    private void txtTienKhachDuaCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtTienKhachDuaCaretUpdate

    }//GEN-LAST:event_txtTienKhachDuaCaretUpdate

    private void cbbHinhThucThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbHinhThucThanhToanActionPerformed

    }//GEN-LAST:event_cbbHinhThucThanhToanActionPerformed

    private void btn_khachvanglaiActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            System.out.println("[DEBUG] === Starting guest customer process ===");
            
            // First try to find any existing guest customer
            List<Customer> allCustomers = customerDAO.findAll();
            Customer guestCustomer = null;
            
            // Look for any existing guest customer by name
            for (Customer c : allCustomers) {
                if (c.getCustomerFullName() != null && 
                    c.getCustomerFullName().startsWith("Khách vãng lai")) {
                    guestCustomer = c;
                    System.out.println("[DEBUG] Found existing guest customer: " + c.getCustomerCode());
                    break;
                }
            }
            
            // If no existing guest customer, create a new one
            if (guestCustomer == null) {
                System.out.println("[DEBUG] No existing guest customer found, creating new one");
                String guestCode = "KH" + System.currentTimeMillis();
                
                // Create new customer object
                guestCustomer = new Customer();
                guestCustomer.setCustomerCode(guestCode);
                guestCustomer.setCustomerFullName("Khách vãng lai (" + guestCode + ")");
                guestCustomer.setCustomerPhone("0000000000");
                guestCustomer.setCustomerEmail(guestCode + "@guest.com");
                
                System.out.println("[DEBUG] Attempting to create new guest customer: " + guestCode);
                
                // Try to save to database
                Customer createdCustomer = customerDAO.create(guestCustomer);
                
                if (createdCustomer != null && createdCustomer.getCustomerId() != null) {
                    guestCustomer = createdCustomer;
                    System.out.println("[DEBUG] Successfully created guest customer with ID: " + 
                                     guestCustomer.getCustomerId());
                } else {
                    // If creation failed, try one more time with a different code
                    guestCode = "KH" + (System.currentTimeMillis() + 1);
                    guestCustomer.setCustomerCode(guestCode);
                    guestCustomer.setCustomerFullName("Khách vãng lai (" + guestCode + ")");
                    guestCustomer.setCustomerEmail(guestCode + "@guest.com");
                    
                    System.out.println("[DEBUG] Retrying with new code: " + guestCode);
                    createdCustomer = customerDAO.create(guestCustomer);
                    
                    if (createdCustomer != null && createdCustomer.getCustomerId() != null) {
                        guestCustomer = createdCustomer;
                        System.out.println("[DEBUG] Successfully created guest customer on second attempt: " + 
                                         guestCustomer.getCustomerId());
                    } else {
                        throw new Exception("Không thể tạo tài khoản khách vãng lai. Vui lòng thử lại.");
                    }
                }
            }
            
            // Update the form with the guest customer
            if (guestCustomer != null && guestCustomer.getCustomerId() != null) {
                setCustomerToForm(guestCustomer);
                currentTemporaryCustomer = guestCustomer;
                System.out.println("[DEBUG] Using guest customer: " + guestCustomer.getCustomerCode() + 
                                 " (ID: " + guestCustomer.getCustomerId() + ")");
            } else {
                throw new Exception("Thông tin khách hàng không hợp lệ");
            }
            
        } catch (Exception e) {
            System.err.println("[ERROR] Failed to handle guest customer: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Không thể tạo tài khoản khách vãng lai. Vui lòng thử lại.\n" + e.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
                
            // Clear any invalid customer references
            currentTemporaryCustomer = null;
            txtMaKhachhang.setText("");
        }
    }

    private void cbbPhieuGiamGiaActionPerformed(java.awt.event.ActionEvent evt) {
        // Get the selected promotion
        int selectedIndex = cbbPhieuGiamGia.getSelectedIndex();
        if (selectedIndex <= 0) {
            // "Không áp dụng" is selected or no selection
            updateCartSummary();
            return;
        }
        
        try {
            // Get all active promotions
            List<Promotion> activePromotions = promotionDAO.findActivePromotions();
            if (selectedIndex - 1 < activePromotions.size()) {
                // Update the cart summary with the selected promotion
                updateCartSummary();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi áp dụng khuyến mãi: " + e.getMessage(), 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }                                               

    private void btn_khachvanglai1ActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            System.out.println("[DEBUG] === Starting guest customer search ===");
            Customer guestCustomer = null;
            
            if (guestCustomer != null) {
                System.out.println("[DEBUG] Found guest customer by code CUS0000");
            } else {
                System.out.println("[DEBUG] No customer found with code CUS0000, searching by name...");
                // If not found by code, search by name
                List<Customer> allCustomers = customerDAO.findAll();
                System.out.println("[DEBUG] Total customers in database: " + allCustomers.size());
                
                // Search for guest customer by name
                for (Customer c : allCustomers) {
                    System.out.println("[DEBUG] Checking customer: " + c.getCustomerCode() + " - " + c.getCustomerFullName());
                    if (c.getCustomerFullName() != null && 
                        c.getCustomerFullName().contains("Khách vãng lai")) {
                        guestCustomer = c;
                        System.out.println("[DEBUG] Found guest customer by name: " + c.getCustomerCode());
                        break;
                    }
                }
            }
            
            // If not found, create a new guest customer
            if (guestCustomer == null) {
                System.out.println("[DEBUG] No guest customer found, creating a new one");
                guestCustomer = new Customer();
                String newCode = "CUS" + System.currentTimeMillis();
                guestCustomer.setCustomerCode(newCode);
                guestCustomer.setCustomerFullName("Khách vãng lai (" + newCode + ")");
                guestCustomer.setCustomerPhone("0000000000");
                guestCustomer.setCustomerEmail("guest@example.com");
                
                try {
                    customerDAO.create(guestCustomer);
                    System.out.println("[DEBUG] Created new guest customer: " + newCode);
                } catch (Exception e) {
                    System.err.println("[ERROR] Failed to create guest customer: " + e.getMessage());
                    throw new Exception("Không thể tạo tài khoản khách vãng lai. Vui lòng thử lại.");
                }
            }

            // Update the form with the customer
            setCustomerToForm(guestCustomer);
            System.out.println("[DEBUG] Using existing guest customer: " + guestCustomer.getCustomerCode());

        } catch (Exception e) {
            System.err.println("[DEBUG] Lỗi khi xử lý khách vãng lai: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Đã xảy ra lỗi khi tải thông tin khách vãng lai. Vui lòng thử lại hoặc liên hệ quản trị viên.\n"
                    + "Lỗi: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }// GEN-LAST:event_btn_khachvanglai1ActionPerformed

    private void setCustomerToForm(Customer customer) {
        if (customer != null) {
            // Set customer code to the text field
            txtMaKhachhang.setText(customer.getCustomerCode() != null ? customer.getCustomerCode() : "");

            // Set customer name to the label
            lbTenKhachHang.setText(customer.getCustomerFullName() != null ? customer.getCustomerFullName() : "");

            // If this is a temporary customer, store it in the field
            if (customer.getCustomerCode() != null && customer.getCustomerCode().startsWith("TEMP_")) {
                currentTemporaryCustomer = customer;
                System.out.println("[DEBUG] Đã lưu khách hàng tạm thời: " + customer.getCustomerCode());
            } else {
                currentTemporaryCustomer = null;
            }

            // Enable the create invoice button
            btnTheHoaDon.setEnabled(true);
        } else {
            // Clear the form if customer is null
            txtMaKhachhang.setText("");
            lbTenKhachHang.setText("");
            currentTemporaryCustomer = null;
            System.out.println("[DEBUG] Đã xóa thông tin khách hàng");
        }
    }
    // Helper method to generate random customer code

    private String generateRandomCustomerCode() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String nums = "0123456789";
        Random random = new Random();

        // Format: CUS + 3 random letters + 3 random numbers
        StringBuilder sb = new StringBuilder("CUS");

        // Add 3 random letters
        for (int i = 0; i < 3; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }

        // Add 3 random numbers
        for (int i = 0; i < 3; i++) {
            sb.append(nums.charAt(random.nextInt(nums.length())));
        }

        return sb.toString();
    }

    /**
     * Updates the status of an order in the tblHoaDon table
     *
     * @param orderCode The order code to update
     * @param newStatus The new status to set (e.g., "Đã thanh toán" or "Chưa
     * thanh toán")
     */
    private void updateOrderStatusInTable(String orderCode, String newStatus) {
        DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();

        // Find the row with the matching order code
        for (int i = 0; i < model.getRowCount(); i++) {
            Object orderCodeObj = model.getValueAt(i, 0);
            if (orderCodeObj != null && orderCodeObj.toString().equals(orderCode)) {
                // Update the status column (index 2)
                model.setValueAt(newStatus, i, 2);

                // Force the table to repaint
                tblHoaDon.repaint();

                System.out.println("[DEBUG] Updated order " + orderCode + " status to: " + newStatus);
                return;
            }
        }

        System.out.println("[WARNING] Order not found in table: " + orderCode);
    }

    /**
     * Loads or refreshes the orders in the tblHoaDon table
     */
    private void loadOrders() {
        try {
            DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
            model.setRowCount(0); // Clear existing rows

            // Get all orders
            OrderDAO orderDAO = new OrderDAOImpl();
            List<Order> orders = orderDAO.findAll();

            // Sort orders by creation date (newest first)
            orders.sort((o1, o2) -> o2.getOrderCreatedAt().compareTo(o1.getOrderCreatedAt()));

            // Add orders to table
            for (Order order : orders) {
                // Format the date
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                String formattedDate = order.getOrderCreatedAt().format(formatter);

                // Get staff code
                String staffCode = "";
                StaffDAO staffDAO = new StaffDAOImpl();
                Staff staff = staffDAO.findById(order.getStaffId());
                if (staff != null) {
                    staffCode = staff.getStaffCode();
                }

                // Add row to table
                model.addRow(new Object[]{
                    order.getOrderCode(),
                    staffCode,
                    order.getOrderStatus(),
                    formattedDate
                });
            }

            // Auto-resize columns
            for (int i = 0; i < tblHoaDon.getColumnCount(); i++) {
                tblHoaDon.getColumnModel().getColumn(i).setPreferredWidth(150);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi tải danh sách đơn hàng: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
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
            java.util.logging.Logger.getLogger(ViewBanHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewBanHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewBanHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewBanHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewBanHang().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JButton btnTheHoaDon;
    private javax.swing.JButton btn_Them1;
    private javax.swing.JButton btn_Them3;
    private javax.swing.JButton btn_Them4;
    private javax.swing.JButton btn_Them5;
    private javax.swing.JButton btn_chon_san_pham;
    private javax.swing.JButton btn_khachvanglai;
    private javax.swing.JComboBox<String> cbbHinhThucThanhToan;
    private javax.swing.JComboBox<String> cbbPhieuGiamGia;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lbGiamGiaTot;
    private javax.swing.JLabel lbMaHD;
    private javax.swing.JLabel lbMaNV;
    private javax.swing.JLabel lbNgayTao;
    private javax.swing.JLabel lbTenKhachHang;
    private javax.swing.JLabel lbTienThua;
    private javax.swing.JLabel lbTong;
    private javax.swing.JLabel lbTongTien;
    private javax.swing.JPanel pnl_thongtinhoadon;
    private javax.swing.JTable tblGioHang;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTextField txtMaKhachhang;
    private javax.swing.JTextField txtTienKhachCK;
    private javax.swing.JTextField txtTienKhachDua;
    // End of variables declaration//GEN-END:variables
}
