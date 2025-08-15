package com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewBanHang;

import com.DuAn1.volleyballshoes.app.controller.OrderController;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.DuAn1.volleyballshoes.app.dao.*;
import com.DuAn1.volleyballshoes.app.dao.impl.*;
import com.DuAn1.volleyballshoes.app.dto.request.OrderCreateRequest;
import com.DuAn1.volleyballshoes.app.dto.request.OrderItemRequest;
import com.DuAn1.volleyballshoes.app.dto.response.OrderResponse;
import com.DuAn1.volleyballshoes.app.entity.*;
import com.DuAn1.volleyballshoes.app.utils.SessionManager;
import com.DuAn1.volleyballshoes.app.utils.XJdbc;
import java.util.stream.Collectors;

public class ViewBanHang extends javax.swing.JPanel {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private static final String CURRENCY_FORMAT = ",d đ";
    private ViewThemKhachHang.ViewBanHangCallback customerSelectionCallback;
    private static ViewBanHang activeInstance;

    public static ViewBanHang getActiveInstance() {
        return activeInstance;
    }

    private static void setActiveInstance(ViewBanHang instance) {
        activeInstance = instance;
    }

    private final CustomerDAO customerDAO = new CustomerDAOImpl();
    private final OrderDAO orderDAO = new OrderDAOImpl();
    private final OrderDetailDAO orderDetailDAO = new OrderDetailDAOImpl();
    private final ProductVariantDAO productVariantDAO = new ProductVariantDAOImpl();
    private final ProductDAO productDAO = new ProductDAOImpl();
    private final SizeDAO sizeDAO = new SizeDAOImpl();
    private final ColorDAO colorDAO = new ColorDAOImpl();
    private final SoleTypeDAO soleTypeDAO = new SoleTypeDAOImpl();
    private final PromotionDAO promotionDAO = new PromotionDAOImpl();

    private String orderCode;
    private Customer currentTemporaryCustomer;

    private String generateOrderCode() {
        return "HD" + System.currentTimeMillis();
    }

    private void initCartTable() {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Only allow editing quantity and discount columns
                return column == 3 || column == 5;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return switch (columnIndex) {
                    case 0 ->
                        Integer.class;    // STT
                    case 3 ->
                        Integer.class;    // Số lượng
                    case 4, 5, 6 ->
                        BigDecimal.class; // Đơn giá, Giảm giá (%), Thành tiền
                    default ->
                        String.class;    // Other columns
                };
            }
        };

        // Set column names for cart table
        String[] columnNames = {"STT", "Mã SP", "Tên sản phẩm", "Số lượng", "Đơn giá", "Giảm giá (%)", "Thành tiền"};
        model.setColumnIdentifiers(columnNames);
        tblGioHang.setModel(model);

        // Add table model listener to update totals when quantity or discount changes
        model.addTableModelListener(e -> {
            if (e.getType() == javax.swing.event.TableModelEvent.UPDATE
                    && (e.getColumn() == 3 || e.getColumn() == 5)) {
                updateRowTotal(e.getFirstRow());
                updateCartSummary();
            }
        });
    }

    /**
     * Updates the total for a specific row in the cart
     *
     * @param row The row index to update
     */
    private void updateRowTotal(int row) {
        DefaultTableModel model = (DefaultTableModel) tblGioHang.getModel();
        try {
            int quantity = (int) model.getValueAt(row, 3);
            BigDecimal price = (BigDecimal) model.getValueAt(row, 4);
            BigDecimal discountPercent = (BigDecimal) model.getValueAt(row, 5);

            // Validate discount percentage (0-100)
            discountPercent = discountPercent.max(BigDecimal.ZERO).min(new BigDecimal(100));
            if (!discountPercent.equals(model.getValueAt(row, 5))) {
                model.setValueAt(discountPercent, row, 5);
            }

            // Calculate total with discount
            BigDecimal subtotal = price.multiply(BigDecimal.valueOf(quantity));
            BigDecimal discountAmount = subtotal.multiply(discountPercent)
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            BigDecimal total = subtotal.subtract(discountAmount);

            model.setValueAt(total, row, 6);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng nhập số lượng và phần trăm giảm giá hợp lệ",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            // Revert to previous valid value
            model.fireTableDataChanged();
        }
    }

    /**
     * Updates the cart summary including subtotal, discounts, and grand total
     */
    private void updateCartSummary() {
        if (tblGioHang == null || tblGioHang.getModel() == null) {
            return;
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
                    continue;
                }

                BigDecimal rowTotal = (BigDecimal) rowTotalObj;
                BigDecimal rowPrice = (BigDecimal) rowPriceObj;
                int quantity = ((Number) quantityObj).intValue();

                BigDecimal rowSubtotal = rowPrice.multiply(BigDecimal.valueOf(quantity));
                subtotal = subtotal.add(rowSubtotal);
                totalDiscount = totalDiscount.add(rowSubtotal.subtract(rowTotal));
            } catch (Exception e) {
                // Log and continue with next row
                System.err.println("Error calculating row total: " + e.getMessage());
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
                            .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Lỗi khi áp dụng khuyến mãi: " + e.getMessage(),
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }

        // Update summary labels with formatted values
        lbTongTien.setText(formatCurrency(subtotal));
        lbGiamGiaTot.setText(formatCurrency(totalDiscount.add(promotionDiscount)));

        // Calculate and update total after all discounts
        BigDecimal total = subtotal.subtract(totalDiscount).subtract(promotionDiscount);
        lbTong.setText(formatCurrency(total));
    }

    /**
     * Formats a BigDecimal amount as a currency string
     *
     * @param amount The amount to format
     * @return Formatted currency string or empty string for null/zero amount
     */
    private String formatCurrency(BigDecimal amount) {
        return (amount == null || amount.compareTo(BigDecimal.ZERO) == 0)
                ? ""
                : String.format("%,d đ", amount.longValue());
    }

    //endregion
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
        setActiveInstance(this);
        loadActivePromotions();
        DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }
        loadProductVariants();
        initCartTable();
        lbMaHD.setVisible(false);
        lbl_odder_code.setVisible(true);
        lbMaNV.setVisible(false);
        lbl_payment_method.setVisible(true);
        lbNgayTao.setVisible(false);
        lbltienkhachdua.setVisible(true);
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

    //region Product Management
    /**
     * Refreshes the product variants list in the UI
     */
    public void refreshProductVariants() {
        loadProductVariants();
    }

    /**
     * Loads product variants into the products table with related information
     */
    private void loadProductVariants() {
        try {
            DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
            model.setRowCount(0); // Clear existing data

            // Set column names for product variants
            String[] columnNames = {"Mã SP", "Tên SP", "Size", "Màu sắc", "Đế giày", "Số lượng", "Giá", "Hình ảnh"};
            model.setColumnIdentifiers(columnNames);

            // Fetch all variants with error handling
            List<ProductVariant> variants = productVariantDAO.findAll();

            // Initialize caches for related entities
            Map<Integer, Product> productCache = new HashMap<>();
            Map<Integer, Size> sizeCache = new HashMap<>();
            Map<Integer, Color> colorCache = new HashMap<>();
            Map<Integer, SoleType> soleTypeCache = new HashMap<>();

            // Process each variant
            for (ProductVariant variant : variants) {
                try {
                    // Get or fetch related entities with null checks
                    Product product = getOrFetchProduct(variant.getProductId(), productCache);
                    Size size = getOrFetchSize(variant.getSizeId(), sizeCache);
                    Color color = getOrFetchColor(variant.getColorId(), colorCache);
                    SoleType soleType = getOrFetchSoleType(variant.getSoleId(), soleTypeCache);

                    // Add row with actual names
                    model.addRow(createProductRow(variant, product, size, color, soleType));
                } catch (Exception e) {
                    System.err.println("Error loading variant " + variant.getVariantSku() + ": " + e.getMessage());
                }
            }

            // Auto-resize columns to fit content
            autoResizeTableColumns(tblSanPham);

        } catch (Exception e) {
            handleException("Lỗi khi tải danh sách sản phẩm", e);
        }
    }

    /**
     * Gets a product from cache or fetches it from the database
     */
    private Product getOrFetchProduct(int productId, Map<Integer, Product> cache) {
        return cache.computeIfAbsent(productId, id -> {
            try {
                return productDAO.findById(id);
            } catch (Exception e) {
                System.err.println("Error fetching product " + id + ": " + e.getMessage());
                return null;
            }
        });
    }

    /**
     * Gets a size from cache or fetches it from the database
     */
    private Size getOrFetchSize(int sizeId, Map<Integer, Size> cache) {
        return cache.computeIfAbsent(sizeId, id -> {
            try {
                return sizeDAO.findById(id);
            } catch (Exception e) {
                System.err.println("Error fetching size " + id + ": " + e.getMessage());
                return null;
            }
        });
    }

    /**
     * Gets a color from cache or fetches it from the database
     */
    private Color getOrFetchColor(int colorId, Map<Integer, Color> cache) {
        return cache.computeIfAbsent(colorId, id -> {
            try {
                return colorDAO.findById(id);
            } catch (Exception e) {
                System.err.println("Error fetching color " + id + ": " + e.getMessage());
                return null;
            }
        });
    }

    /**
     * Gets a sole type from cache or fetches it from the database
     */
    private SoleType getOrFetchSoleType(int soleId, Map<Integer, SoleType> cache) {
        return cache.computeIfAbsent(soleId, id -> {
            try {
                return soleTypeDAO.findById(id);
            } catch (Exception e) {
                System.err.println("Error fetching sole type " + id + ": " + e.getMessage());
                return null;
            }
        });
    }

    /**
     * Creates a table row for a product variant with related information
     */
    private Object[] createProductRow(ProductVariant variant, Product product,
            Size size, Color color, SoleType soleType) {
        return new Object[]{
            variant.getVariantSku(),
            product != null ? product.getProductName() : "N/A",
            size != null ? size.getSizeValue() : "N/A",
            color != null ? color.getColorName() : "N/A",
            soleType != null ? soleType.getSoleName() : "N/A",
            variant.getVariantquantity(),
            formatCurrency(variant.getVariantOrigPrice()),
            variant.getVariantImgUrl()
        };
    }

    /**
     * Auto-resizes table columns to fit content
     */
    private void autoResizeTableColumns(JTable table) {
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(100);
        }
    }

    /**
     * Handles exceptions by showing an error dialog
     */
    private void handleException(String message, Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this,
                message + ": " + e.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Finds a product variant by its SKU
     */
    private ProductVariant findProductVariantBySku(String sku) {
        return productVariantDAO.findAll().stream()
                .filter(v -> v.getVariantSku() != null && v.getVariantSku().trim().equalsIgnoreCase(sku))
                .findFirst()
                .orElse(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btn_Them1 = new javax.swing.JButton();
        btn_QuetQR = new javax.swing.JButton();
        btnTheHoaDon = new javax.swing.JButton();
        btn_HuyHD = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
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
        btn_khachvanglai = new javax.swing.JButton();
        btn_Chon = new javax.swing.JButton();
        pnl_thongtinhoadon = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        lbl_odder_code = new javax.swing.JLabel();
        lbl_oder_create_at = new javax.swing.JLabel();
        lbl_staff_code = new javax.swing.JLabel();
        lbl_customer_name = new javax.swing.JLabel();
        lbl_tongtien = new javax.swing.JLabel();
        lbl_promotion = new javax.swing.JLabel();
        lbl_payment_method = new javax.swing.JLabel();
        lbltienkhachdua = new javax.swing.JLabel();
        lbl_tienkhachck = new javax.swing.JLabel();
        lbl_tienthua = new javax.swing.JLabel();
        lbl_tong = new javax.swing.JLabel();
        lbMaHD = new javax.swing.JLabel();
        lbNgayTao = new javax.swing.JLabel();
        lbMaNV = new javax.swing.JLabel();
        lbTenKhachHang = new javax.swing.JLabel();
        lbTongTien = new javax.swing.JLabel();
        cbbHinhThucThanhToan = new javax.swing.JComboBox<>();
        txtTienKhachDua = new javax.swing.JTextField();
        lbTienThua = new javax.swing.JLabel();
        lbTong = new javax.swing.JLabel();
        lbGiamGiaTot = new javax.swing.JLabel();
        btnThanhToan = new javax.swing.JButton();
        txtTienKhachCK = new javax.swing.JTextField();
        cbbPhieuGiamGia = new javax.swing.JComboBox<>();
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

        btn_Them1.setBackground(new java.awt.Color(0, 102, 255));
        btn_Them1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_Them1.setForeground(new java.awt.Color(255, 255, 255));
        btn_Them1.setText("Làm Mới");
        btn_Them1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Them1btn_ThemActionPerformed(evt);
            }
        });

        btn_QuetQR.setBackground(new java.awt.Color(0, 102, 255));
        btn_QuetQR.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_QuetQR.setForeground(new java.awt.Color(255, 255, 255));
        btn_QuetQR.setText("Quét Mã QR");
        btn_QuetQR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_QuetQRbtn_ThemActionPerformed(evt);
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

        btn_HuyHD.setBackground(new java.awt.Color(0, 102, 255));
        btn_HuyHD.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_HuyHD.setForeground(new java.awt.Color(255, 255, 255));
        btn_HuyHD.setText("hủy HD");
        btn_HuyHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_HuyHDbtn_ThemActionPerformed(evt);
            }
        });

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
        jScrollPane4.setViewportView(tblHoaDon);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(btn_QuetQR)
                .addGap(27, 27, 27)
                .addComponent(btnTheHoaDon)
                .addGap(104, 104, 104)
                .addComponent(btn_HuyHD)
                .addGap(18, 18, 18)
                .addComponent(btn_Them1)
                .addContainerGap(184, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 758, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_Them1)
                    .addComponent(btn_QuetQR)
                    .addComponent(btnTheHoaDon)
                    .addComponent(btn_HuyHD, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE))
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

        btn_khachvanglai.setForeground(new java.awt.Color(51, 102, 255));
        btn_khachvanglai.setText("Khách vãng lai");
        btn_khachvanglai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_khachvanglaiActionPerformed(evt);
            }
        });

        btn_Chon.setBackground(new java.awt.Color(0, 102, 255));
        btn_Chon.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_Chon.setForeground(new java.awt.Color(255, 255, 255));
        btn_Chon.setText("Chọn");
        btn_Chon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Chonbtn_ThemActionPerformed(evt);
            }
        });

        pnl_thongtinhoadon.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        jLabel7.setText("Thông tin hóa đơn");

        lbl_odder_code.setText("Mã Hòa đơn :");

        lbl_oder_create_at.setText("Ngày Tạo :");

        lbl_staff_code.setText("Mã Nhân Viên :");

        lbl_customer_name.setText("Tên Khách Hàng :");

        lbl_tongtien.setText("Tổng tiền :");

        lbl_promotion.setText("Phiếu giảm giá :");

        lbl_payment_method.setText("Hình Thức TT :");

        lbltienkhachdua.setText("Tiền Khách đưa :");

        lbl_tienkhachck.setText("Tiền Khách CK :");

        lbl_tienthua.setText("Tiền Thừa :");

        lbl_tong.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_tong.setForeground(new java.awt.Color(255, 51, 51));
        lbl_tong.setText("Tổng :");

        lbNgayTao.setToolTipText("");

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

        lbTong.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbTong.setForeground(new java.awt.Color(255, 0, 51));

        lbGiamGiaTot.setFont(new java.awt.Font("Segoe UI", 2, 10)); // NOI18N
        lbGiamGiaTot.setForeground(new java.awt.Color(255, 0, 51));

        btnThanhToan.setBackground(new java.awt.Color(0, 102, 255));
        btnThanhToan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThanhToan.setForeground(new java.awt.Color(255, 255, 255));
        btnThanhToan.setText("thanh toán");
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanbtn_ThemActionPerformed(evt);
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

        cbbPhieuGiamGia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));
        cbbPhieuGiamGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbPhieuGiamGiaActionPerformed(evt);
            }
        });

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
                                    .addComponent(lbl_odder_code)
                                    .addComponent(lbl_oder_create_at)
                                    .addComponent(jLabel7)
                                    .addComponent(lbl_customer_name)
                                    .addComponent(lbl_tongtien)
                                    .addComponent(lbl_tienkhachck)
                                    .addComponent(lbl_staff_code))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(pnl_thongtinhoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lbNgayTao, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                                    .addComponent(lbMaHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lbMaNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lbTenKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lbTongTien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(pnl_thongtinhoadonLayout.createSequentialGroup()
                                .addComponent(lbl_tong)
                                .addGap(24, 24, 24)
                                .addComponent(lbTong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(pnl_thongtinhoadonLayout.createSequentialGroup()
                                .addComponent(lbl_tienthua)
                                .addGap(67, 67, 67)
                                .addComponent(lbTienThua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(pnl_thongtinhoadonLayout.createSequentialGroup()
                                .addGroup(pnl_thongtinhoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnl_thongtinhoadonLayout.createSequentialGroup()
                                        .addComponent(lbl_payment_method)
                                        .addGap(27, 27, 27)
                                        .addComponent(cbbHinhThucThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pnl_thongtinhoadonLayout.createSequentialGroup()
                                        .addComponent(lbl_promotion)
                                        .addGap(18, 18, 18)
                                        .addComponent(cbbPhieuGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pnl_thongtinhoadonLayout.createSequentialGroup()
                                        .addComponent(lbltienkhachdua)
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
                .addGap(51, 51, 51)
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
                    .addComponent(lbl_odder_code)
                    .addComponent(lbMaHD))
                .addGap(18, 18, 18)
                .addGroup(pnl_thongtinhoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_oder_create_at)
                    .addComponent(lbNgayTao))
                .addGap(18, 18, 18)
                .addGroup(pnl_thongtinhoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_staff_code)
                    .addComponent(lbMaNV))
                .addGap(18, 18, 18)
                .addGroup(pnl_thongtinhoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_customer_name)
                    .addComponent(lbTenKhachHang))
                .addGap(18, 18, 18)
                .addGroup(pnl_thongtinhoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_tongtien)
                    .addComponent(lbTongTien))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_thongtinhoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_promotion)
                    .addComponent(cbbPhieuGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addComponent(lbGiamGiaTot)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_thongtinhoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_payment_method)
                    .addComponent(cbbHinhThucThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnl_thongtinhoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbltienkhachdua)
                    .addComponent(txtTienKhachDua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnl_thongtinhoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_tienkhachck)
                    .addComponent(txtTienKhachCK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnl_thongtinhoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_thongtinhoadonLayout.createSequentialGroup()
                        .addComponent(lbl_tienthua)
                        .addGap(18, 18, 18)
                        .addGroup(pnl_thongtinhoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_tong)
                            .addComponent(lbTong)))
                    .addComponent(lbTienThua, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(txtMaKhachhang, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_Chon, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addComponent(btn_khachvanglai)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(pnl_thongtinhoadon, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMaKhachhang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_Chon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_khachvanglai)
                .addGap(18, 18, 18)
                .addComponent(pnl_thongtinhoadon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                        .addGap(708, 708, 708))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 761, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(266, 266, 266))
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
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(495, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_Them1btn_ThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Them1btn_ThemActionPerformed

    }//GEN-LAST:event_btn_Them1btn_ThemActionPerformed


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


    private void txtMaKhachhangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaKhachhangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaKhachhangActionPerformed


    private void txtTienKhachDuaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTienKhachDuaKeyReleased

    }//GEN-LAST:event_txtTienKhachDuaKeyReleased

    private void txtTienKhachDuaCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtTienKhachDuaCaretUpdate

    }//GEN-LAST:event_txtTienKhachDuaCaretUpdate

    private void cbbHinhThucThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbHinhThucThanhToanActionPerformed

    }//GEN-LAST:event_cbbHinhThucThanhToanActionPerformed

    private void btnThanhToanbtn_ThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanbtn_ThemActionPerformed
        // TODO add your handling code here:
        try {
            if (orderCode == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng tạo hóa đơn trước khi thanh toán", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            DefaultTableModel cartModel = (DefaultTableModel) tblGioHang.getModel();
            if (cartModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Giỏ hàng trống", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            SessionManager session = SessionManager.getInstance();
            Integer customerId = currentTemporaryCustomer != null ? currentTemporaryCustomer.getCustomerId() : null;
            Integer staffId = session.getStaffId();
            String paymentMethod = (String) cbbHinhThucThanhToan.getSelectedItem();

            // Lấy danh sách items từ giỏ hàng
            List<OrderItemRequest> items = new ArrayList<>();
            for (int i = 0; i < cartModel.getRowCount(); i++) {
                String sku = (String) cartModel.getValueAt(i, 1); // Mã SP
                ProductVariant variant = productVariantDAO.findBySku(sku);
                if (variant == null) {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm: " + sku, "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if ((int) cartModel.getValueAt(i, 3) > variant.getVariantquantity()) {
                    JOptionPane.showMessageDialog(this, "Sản phẩm " + sku + " không đủ tồn kho", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                OrderItemRequest item = OrderItemRequest.builder()
                        .variantId(variant.getVariantId())
                        .quantity((int) cartModel.getValueAt(i, 3))
                        .unitPrice((BigDecimal) cartModel.getValueAt(i, 4))
                        .build();
                items.add(item);
            }

            // Tính tổng tiền từ lbTong
            String totalText = lbTong.getText().replaceAll("[^0-9]", "");
            BigDecimal finalAmount = new BigDecimal(totalText);

            // Cập nhật Order
            OrderController controller = new OrderController();
            OrderResponse currentOrder = controller.getOrderByQRCodeOrId(orderCode);
            if (currentOrder == null) {
                JOptionPane.showMessageDialog(this, "Hóa đơn không tồn tại", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Order updatedOrder = Order.builder()
                    .orderId(currentOrder.getOrderId())
                    .customerId(customerId)
                    .staffId(staffId)
                    .orderFinalAmount(finalAmount)
                    .orderPaymentMethod(paymentMethod)
                    .orderStatus("COMPLETED")
                    .orderCode(orderCode)
                    .orderCreatedAt(currentOrder.getCreatedAt())
                    .build();

            // Lưu chi tiết hóa đơn và giảm tồn kho
            OrderDAOImpl orderDAO = new OrderDAOImpl();
            updatedOrder = orderDAO.processPayment(updatedOrder, items.stream()
                    .collect(Collectors.toMap(OrderItemRequest::getVariantId, OrderItemRequest::getQuantity)));

            // Cập nhật giao diện
            cartModel.setRowCount(0);
            updateCartSummary();
            loadOrders();

            orderCode = null;
            lbMaHD.setText("");
            lbMaHD.setVisible(false);
            lbl_odder_code.setVisible(true);
            lbMaNV.setText("");
            lbMaNV.setVisible(false);
            lbl_payment_method.setVisible(true);
            lbNgayTao.setText("");
            lbNgayTao.setVisible(false);
            lbltienkhachdua.setVisible(true);
            lbTenKhachHang.setText("");
            txtMaKhachhang.setText("");
            currentTemporaryCustomer = null;

            JOptionPane.showMessageDialog(this, "Thanh toán thành công cho hóa đơn: " + orderCode, "Thành công", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi thanh toán: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnThanhToanbtn_ThemActionPerformed

    private void btn_khachvanglaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_khachvanglaiActionPerformed
        // TODO add your handling code here:
        currentTemporaryCustomer = null;
        lbTenKhachHang.setText("Khách vãng lai");
        txtMaKhachhang.setText("");
    }//GEN-LAST:event_btn_khachvanglaiActionPerformed

    private void txtTienKhachCKCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtTienKhachCKCaretUpdate
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTienKhachCKCaretUpdate

    private void txtTienKhachCKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTienKhachCKActionPerformed
        // TODO add your handling code here:
        btnThanhToan.requestFocusInWindow();
    }//GEN-LAST:event_txtTienKhachCKActionPerformed

    private void btn_Chonbtn_ThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Chonbtn_ThemActionPerformed
        // TODO add your handling code here:
        ViewThemKhachHang hang = new ViewThemKhachHang();
        hang.setVisible(true);
    }//GEN-LAST:event_btn_Chonbtn_ThemActionPerformed

    private void btn_QuetQRbtn_ThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_QuetQRbtn_ThemActionPerformed
        // TODO add your handling code here:
        QuetQRBanHang banHang = new QuetQRBanHang(this);
        banHang.setVisible(true);
    }//GEN-LAST:event_btn_QuetQRbtn_ThemActionPerformed

    private void btnTheHoaDonbtn_ThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTheHoaDonbtn_ThemActionPerformed
        // TODO add your handling code here:
        try {
            SessionManager session = SessionManager.getInstance();
            if (!session.isLoggedIn()) {
                JOptionPane.showMessageDialog(this, "Vui lòng đăng nhập để tạo hóa đơn", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Integer customerId = currentTemporaryCustomer != null ? currentTemporaryCustomer.getCustomerId() : null;
            Integer staffId = session.getStaffId();
            String staffCode = session.getStaffCode();

            OrderCreateRequest request = OrderCreateRequest.builder()
                    .customerId(customerId)
                    .staffId(staffId)
                    .paymentMethod("CASH")
                    .items(new ArrayList<>())
                    .build();

            OrderController controller = new OrderController();
            OrderResponse newOrder = controller.createOrder(request);

            if (newOrder != null) {
                orderCode = newOrder.getOrderCode();
                lbMaHD.setText(orderCode);
                lbMaHD.setVisible(true);
                lbl_odder_code.setVisible(false);
                lbMaNV.setText(staffCode);
                lbMaNV.setVisible(true);
                lbl_payment_method.setVisible(false);
                lbNgayTao.setText(DATE_FORMATTER.format(newOrder.getCreatedAt()));
                lbNgayTao.setVisible(true);
                lbltienkhachdua.setVisible(false);

                DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
                Object[] row = {newOrder.getOrderId(), newOrder.getOrderCode(), DATE_FORMATTER.format(newOrder.getCreatedAt()), "PENDING"};
                model.addRow(row);

                JOptionPane.showMessageDialog(this, "Tạo hóa đơn mới thành công: " + orderCode, "Thành công", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Không thể tạo hóa đơn", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tạo hóa đơn: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnTheHoaDonbtn_ThemActionPerformed

    private void btn_HuyHDbtn_ThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_HuyHDbtn_ThemActionPerformed
        // TODO add your handling code here:
        try {
            if (orderCode == null) {
                JOptionPane.showMessageDialog(this, "Không có hóa đơn để hủy", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            OrderController controller = new OrderController();
            OrderResponse order = controller.getOrderByQRCodeOrId(orderCode);
            if (order != null && controller.cancelOrder(order.getOrderId())) {
                // Xóa giỏ hàng
                DefaultTableModel cartModel = (DefaultTableModel) tblGioHang.getModel();
                cartModel.setRowCount(0);
                updateCartSummary();

                // Reset giao diện
                orderCode = null;
                lbMaHD.setText("");
                lbMaHD.setVisible(false);
                lbl_odder_code.setVisible(true);
                lbMaNV.setText("");
                lbMaNV.setVisible(false);
                lbl_payment_method.setVisible(true);
                lbNgayTao.setText("");
                lbNgayTao.setVisible(false);
                lbltienkhachdua.setVisible(true);

                // Tải lại bảng hóa đơn
                loadOrders();

                JOptionPane.showMessageDialog(this, "Hủy hóa đơn thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi hủy hóa đơn: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_btn_HuyHDbtn_ThemActionPerformed

    private void cbbPhieuGiamGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbPhieuGiamGiaActionPerformed
        // TODO add your handling code here:
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
    }//GEN-LAST:event_cbbPhieuGiamGiaActionPerformed

    private void tblHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_tblHoaDonMouseClicked

    private void tblHoaDonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tblHoaDonMouseEntered

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
            java.util.logging.Logger.getLogger(ViewBanHang.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewBanHang.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewBanHang.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewBanHang.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
    private javax.swing.JButton btn_Chon;
    private javax.swing.JButton btn_HuyHD;
    private javax.swing.JButton btn_QuetQR;
    private javax.swing.JButton btn_Them1;
    private javax.swing.JButton btn_chon_san_pham;
    private javax.swing.JButton btn_khachvanglai;
    private javax.swing.JComboBox<String> cbbHinhThucThanhToan;
    private javax.swing.JComboBox<String> cbbPhieuGiamGia;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
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
    private javax.swing.JLabel lbl_customer_name;
    private javax.swing.JLabel lbl_odder_code;
    private javax.swing.JLabel lbl_oder_create_at;
    private javax.swing.JLabel lbl_payment_method;
    private javax.swing.JLabel lbl_promotion;
    private javax.swing.JLabel lbl_staff_code;
    private javax.swing.JLabel lbl_tienkhachck;
    private javax.swing.JLabel lbl_tienthua;
    private javax.swing.JLabel lbl_tong;
    private javax.swing.JLabel lbl_tongtien;
    private javax.swing.JLabel lbltienkhachdua;
    private javax.swing.JPanel pnl_thongtinhoadon;
    private javax.swing.JTable tblGioHang;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTextField txtMaKhachhang;
    private javax.swing.JTextField txtTienKhachCK;
    private javax.swing.JTextField txtTienKhachDua;
    // End of variables declaration//GEN-END:variables

    void getMaQR(String qrText) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
