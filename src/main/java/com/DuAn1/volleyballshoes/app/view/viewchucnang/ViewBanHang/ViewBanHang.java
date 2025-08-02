package com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewBanHang;

import com.DuAn1.volleyballshoes.app.controller.CustomerController;
import com.DuAn1.volleyballshoes.app.controller.OrderController;
import com.DuAn1.volleyballshoes.app.controller.ProductController;

import java.awt.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.print.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class ViewBanHang extends javax.swing.JPanel {

    private final CustomerController customerController = new CustomerController();
    private final OrderController orderController = new OrderController();
    private final ProductController productController = new ProductController();
    // Table models
    private DefaultTableModel modelHoaDon;
    private DefaultTableModel modelSanPham;
    private DefaultTableModel modelGioHang;

    // Initialize UI components
    private void initializeComponents() {
        // Initialize buttons with proper text and mnemonics
        btnThanhToan = new JButton("Thanh toán");
        btnThanhToan.setMnemonic('T');
        
        btnTheHoaDon = new JButton("Tạo mới hóa đơn");
        btnTheHoaDon.setMnemonic('T');
        
        btn_Them1 = new JButton("Làm mới");
        btn_Them1.setMnemonic('L');
        
        btn_Them3 = new JButton("Xem");
        btn_Them3.setMnemonic('X');
        
        btn_Them4 = new JButton("In");
        btn_Them4.setMnemonic('I');
        
        btn_Them5 = new JButton("Hủy");
        btn_Them5.setMnemonic('H');
        
        btnTimKiem = new JButton("Tìm");
        btnTimKiem.setMnemonic('T');
        
        // Initialize text fields with proper preferred sizes
        txtTimKiem = new JTextField(20);
        txtTimKiem.setPreferredSize(new Dimension(200, 30));
        
        txtMaKhachhang = new JTextField(15);
        txtMaKhachhang.setPreferredSize(new Dimension(150, 30));
        
        txtTenKhach = new JTextField(15);
        txtTenKhach.setPreferredSize(new Dimension(150, 30));
        
        txtTienKhachDua = new JTextField(15);
        txtTienKhachDua.setPreferredSize(new Dimension(150, 30));
        
        txtTienKhachCK = new JTextField(15);
        txtTienKhachCK.setPreferredSize(new Dimension(150, 30));
        
        // Initialize labels with proper alignment
        jLabel1 = new JLabel("BÁN HÀNG", SwingConstants.CENTER);
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 24));
        jLabel1.setForeground(new Color(0, 102, 204));
        
        // Initialize price labels with right alignment
        lbTongTien = createPriceLabel("0 đ");
        lbGiamGiaTot = createPriceLabel("0 đ");
        lbTong = createPriceLabel("0 đ");
        lbTienThua = createPriceLabel("0 đ");
        
        // Initialize combo boxes with proper renderers
        cbbHinhThucThanhToan = new JComboBox<>(new String[]{"Tiền mặt", "Chuyển khoản"});
        cbbHinhThucThanhToan.setPreferredSize(new Dimension(150, 30));
        
        cbbPhieuGiamGia = new JComboBox<>(new String[]{"Không áp dụng"});
        cbbPhieuGiamGia.setPreferredSize(new Dimension(150, 30));
        
        // Initialize tables with proper settings
        tblHoaDon = createTable();
        tblSanPham = createTable();
        tblGioHang = createTable();
        
        // Set up table selection modes
        tblHoaDon.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblSanPham.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblGioHang.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Initialize scroll panes with proper settings
        jScrollPane1 = new JScrollPane();
        jScrollPane2 = new JScrollPane();
        jScrollPane3 = new JScrollPane(tblHoaDon);
        jScrollPane3.setPreferredSize(new Dimension(800, 200));
        
        jScrollPane5 = new JScrollPane(tblSanPham);
        jScrollPane5.setPreferredSize(new Dimension(800, 250));
        
        // Initialize panels with proper layouts and borders
        jPanel1 = createPanel("Thông tin hóa đơn");
        jPanel2 = createPanel("Danh sách hóa đơn");
        jPanel3 = createPanel("Chi tiết hóa đơn");
        jPanel4 = createPanel("Tìm kiếm sản phẩm");
        jPanel5 = createPanel("Thông tin khách hàng");
        jPanel6 = createPanel("Thanh toán");
        jPanel7 = createPanel("Sản phẩm");
        jPanel8 = createPanel("Giỏ hàng");
        jPanel9 = createPanel("Thống kê");
    }
    
    private JLabel createPriceLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.RIGHT);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(Color.RED);
        label.setPreferredSize(new Dimension(150, 25));
        return label;
    }
    
    private JTable createTable() {
        JTable table = new JTable();
        table.setRowHeight(30);
        table.setShowGrid(true);
        table.setGridColor(Color.LIGHT_GRAY);
        table.setSelectionBackground(new Color(220, 240, 255));
        table.setSelectionForeground(Color.BLACK);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setFillsViewportHeight(true);
        return table;
    }
    
    private JPanel createPanel(String title) {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
            title,
            javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
            javax.swing.border.TitledBorder.DEFAULT_POSITION,
            new Font("Segoe UI", Font.BOLD, 12),
            new Color(0, 102, 204)
        ));
        panel.setBackground(Color.WHITE);
        return panel;
    }
    
    public ViewBanHang() {
        initializeComponents();
        initTables();
        setupLayout();
        
        // Set up button actions
        setupButtonActions();
    }
    
    private void configureButton(JButton button, String colorHex) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        button.setBackground(Color.decode(colorHex));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(darkenColor(Color.decode(colorHex), 0.1f));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.decode(colorHex));
            }
        });
    }
    
    private Color darkenColor(Color color, float factor) {
        return new Color(
            Math.max((int)(color.getRed() * (1 - factor)), 0),
            Math.max((int)(color.getGreen() * (1 - factor)), 0),
            Math.max((int)(color.getBlue() * (1 - factor)), 0),
            color.getAlpha()
        );
    }
    
    private void setupButtonActions() {
        btnTheHoaDon.addActionListener(e -> btnTheHoaDonbtn_ThemActionPerformed(e));
        btn_Them1.addActionListener(e -> btn_Them1btn_ThemActionPerformed(e));
        btnThanhToan.addActionListener(e -> {
            // Handle payment
            JOptionPane.showMessageDialog(this, "Chức năng thanh toán đang được phát triển");
        });
    }
    
    private void setupLayout() {
        // Set layout manager for the main panel
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(Color.WHITE);
        
        // Configure button styles
        configureButton(btnThanhToan, "#2196F3");
        configureButton(btnTheHoaDon, "#4CAF50");
        configureButton(btn_Them1, "#607D8B");
        configureButton(btn_Them3, "#FFC107");
        configureButton(btn_Them4, "#9C27B0");
        configureButton(btn_Them5, "#F44336");
        configureButton(btnTimKiem, "#9E9E9E");
        
        // Set up table models
        modelHoaDon = new DefaultTableModel(
            new Object[][]{},
            new String[]{"Mã HĐ", "Ngày tạo", "Khách hàng", "Tổng tiền", "Trạng thái"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        modelSanPham = new DefaultTableModel(
            new Object[][]{},
            new String[]{"Mã SP", "Tên sản phẩm", "Giá bán", "Số lượng", "Thao tác"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; // Only action column is editable
            }
        };
        
        modelGioHang = new DefaultTableModel(
            new Object[][]{},
            new String[]{"STT", "Tên sản phẩm", "Số lượng", "Đơn giá", "Thành tiền", "Xóa"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2 || column == 5; // Only quantity and delete are editable
            }
        };
        
        // Apply models to tables
        tblHoaDon.setModel(modelHoaDon);
        tblSanPham.setModel(modelSanPham);
        tblGioHang.setModel(modelGioHang);
        
        // Main container with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(Color.WHITE);
        
        // 1. Top Panel - Title
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.add(jLabel1, BorderLayout.CENTER);
        
        // 2. Center Panel - Main content with invoice list and cart
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(Color.WHITE);
        
        // Invoice list panel
        JPanel invoicePanel = new JPanel(new BorderLayout());
        invoicePanel.setBackground(Color.WHITE);
        
        // Invoice buttons panel
        JPanel invoiceButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        invoiceButtonPanel.setBackground(Color.WHITE);
        invoiceButtonPanel.add(btnTheHoaDon);
        invoiceButtonPanel.add(btn_Them3);
        invoiceButtonPanel.add(btn_Them4);
        invoiceButtonPanel.add(btn_Them5);
        invoiceButtonPanel.add(btn_Them1);
        
        invoicePanel.add(invoiceButtonPanel, BorderLayout.NORTH);
        invoicePanel.add(jScrollPane3, BorderLayout.CENTER);
        
        // Cart panel
        JPanel cartPanel = new JPanel(new BorderLayout());
        cartPanel.setBackground(Color.WHITE);
        cartPanel.setBorder(BorderFactory.createTitledBorder("Giỏ hàng"));
        cartPanel.add(new JScrollPane(tblGioHang), BorderLayout.CENTER);
        
        // Add to center panel
        centerPanel.add(invoicePanel, BorderLayout.CENTER);
        centerPanel.add(cartPanel, BorderLayout.SOUTH);
        
        // 3. Right Panel - Customer info and payment
        JPanel rightPanel = new JPanel(new BorderLayout(10, 10));
        rightPanel.setPreferredSize(new Dimension(350, getHeight()));
        rightPanel.setBackground(Color.WHITE);
        
        // Customer info panel
        JPanel customerPanel = new JPanel(new BorderLayout());
        customerPanel.setBorder(BorderFactory.createTitledBorder("Thông tin khách hàng"));
        
        JPanel customerInfoPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        customerInfoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        customerInfoPanel.add(new JLabel("Mã KH:"));
        customerInfoPanel.add(txtMaKhachhang);
        customerInfoPanel.add(new JLabel("Tên KH:"));
        customerInfoPanel.add(txtTenKhach);
        
        customerPanel.add(customerInfoPanel, BorderLayout.CENTER);
        
        // Payment panel
        JPanel paymentPanel = new JPanel(new BorderLayout());
        paymentPanel.setBorder(BorderFactory.createTitledBorder("Thanh toán"));
        
        JPanel paymentInfoPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        paymentInfoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        paymentInfoPanel.add(new JLabel("Tổng tiền:"));
        paymentInfoPanel.add(lbTongTien);
        paymentInfoPanel.add(new JLabel("Giảm giá:"));
        paymentInfoPanel.add(lbGiamGiaTot);
        paymentInfoPanel.add(new JLabel("Thành tiền:"));
        paymentInfoPanel.add(lbTong);
        paymentInfoPanel.add(new JLabel("Hình thức:"));
        paymentInfoPanel.add(cbbHinhThucThanhToan);
        paymentInfoPanel.add(new JLabel("Khách đưa:"));
        paymentInfoPanel.add(txtTienKhachDua);
        paymentInfoPanel.add(new JLabel("Tiền thừa:"));
        paymentInfoPanel.add(lbTienThua);
        
        paymentPanel.add(paymentInfoPanel, BorderLayout.CENTER);
        
        // Add payment button at the bottom
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(btnThanhToan);
        paymentPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add components to right panel
        rightPanel.add(customerPanel, BorderLayout.NORTH);
        rightPanel.add(paymentPanel, BorderLayout.CENTER);
        
        // 4. Bottom Panel - Product list
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.WHITE);
        
        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.add(new JLabel("Tìm kiếm:"));
        searchPanel.add(txtTimKiem);
        searchPanel.add(btnTimKiem);
        searchPanel.add(new JLabel("   ")); // Spacer
        searchPanel.add(new JLabel("Mã giảm giá:"));
        searchPanel.add(cbbPhieuGiamGia);
        
        // Product table panel
        JPanel productPanel = new JPanel(new BorderLayout());
        productPanel.setBackground(Color.WHITE);
        productPanel.add(searchPanel, BorderLayout.NORTH);
        productPanel.add(jScrollPane5, BorderLayout.CENTER);
        
        bottomPanel.add(productPanel, BorderLayout.CENTER);
        
        // Add all panels to main panel
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(rightPanel, BorderLayout.EAST);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        // Add main panel to the view
        add(mainPanel, BorderLayout.CENTER);
        
        // Add some sample data for testing
        addSampleData();
    }
    
    private void addSampleData() {
        // Add sample invoice data
        modelHoaDon.addRow(new Object[]{"HD001", "01/01/2023", "Khách lẻ", "1,500,000 đ", "Đã thanh toán"});
        modelHoaDon.addRow(new Object[]{"HD002", "02/01/2023", "Nguyễn Văn A", "2,300,000 đ", "Chờ thanh toán"});
        
        // Add sample product data
        modelSanPham.addRow(new Object[]{"SP001", "Giày bóng chuyền Asics", "1,200,000 đ", 10, "Thêm"});
        modelSanPham.addRow(new Object[]{"SP002", "Giày bóng chuyền Mizuno", "1,500,000 đ", 8, "Thêm"});
        modelSanPham.addRow(new Object[]{"SP003", "Giày bóng chuyền Adidas", "1,800,000 đ", 5, "Thêm"});
        
        // Add sample cart items
        modelGioHang.addRow(new Object[]{1, "Giày bóng chuyền Asics", 2, "1,200,000 đ", "2,400,000 đ", "Xóa"});
        modelGioHang.addRow(new Object[]{2, "Giày bóng chuyền Mizuno", 1, "1,500,000 đ", "1,500,000 đ", "Xóa"});
        
        // Update totals
        updateTotals();
    }
    
    private void updateTotals() {
        double total = 0;
        for (int i = 0; i < modelGioHang.getRowCount(); i++) {
            String priceStr = modelGioHang.getValueAt(i, 4).toString();
            double price = Double.parseDouble(priceStr.replace(" đ", "").replace(",", ""));
            total += price;
        }
        
        lbTongTien.setText(String.format("%,.0f đ", total));
        lbTong.setText(String.format("%,.0f đ", total));
    }
        
        // Create panels for different sections
    private void createPanels() {
        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        JPanel bottomPanel = new JPanel(new BorderLayout());
        
        // Add title to top panel
        JPanel titlePanel = new JPanel();
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titlePanel.add(jLabel1);
        topPanel.add(titlePanel, BorderLayout.NORTH);
        
        // Configure and add invoice panel to center
        jPanel2.setBorder(BorderFactory.createTitledBorder("Hóa đơn"));
        jPanel2.setLayout(new BorderLayout());
        centerPanel.add(jPanel2, BorderLayout.NORTH);
        
        // Configure cart panel
        JPanel cartPanel = new JPanel(new BorderLayout());
        cartPanel.setBorder(BorderFactory.createTitledBorder("Giỏ hàng"));
        cartPanel.add(new JScrollPane(tblGioHang), BorderLayout.CENTER);
        centerPanel.add(cartPanel, BorderLayout.CENTER);
        
        // Configure product panel
        JPanel productPanel = new JPanel(new BorderLayout());
        productPanel.setBorder(BorderFactory.createTitledBorder("Sản phẩm"));
        productPanel.add(new JScrollPane(tblSanPham), BorderLayout.CENTER);
        
        // Add search panel above products
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Tìm kiếm:"));
        searchPanel.add(txtTimKiem);
        searchPanel.add(btnTimKiem);
        productPanel.add(searchPanel, BorderLayout.NORTH);
        
        bottomPanel.add(productPanel, BorderLayout.CENTER);
        
        // Configure right panel with customer info and payment
        JPanel rightPanel = new JPanel(new BorderLayout(5, 10));
        rightPanel.setPreferredSize(new Dimension(300, getHeight()));
        
        // Customer info panel
        JPanel customerPanel = new JPanel(new BorderLayout());
        customerPanel.setBorder(BorderFactory.createTitledBorder("Thông tin khách hàng"));
        
        JPanel customerInfoPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        customerInfoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        customerInfoPanel.add(new JLabel("Mã KH:"));
        customerInfoPanel.add(txtMaKhachhang);
        customerInfoPanel.add(new JLabel("Tên KH:"));
        customerInfoPanel.add(txtTenKhach);
        
        customerPanel.add(customerInfoPanel, BorderLayout.CENTER);
        
        // Payment panel
        JPanel paymentPanel = new JPanel(new BorderLayout());
        paymentPanel.setBorder(BorderFactory.createTitledBorder("Thanh toán"));
        
        JPanel paymentInfoPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        paymentInfoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        paymentInfoPanel.add(new JLabel("Tổng tiền:"));
        paymentInfoPanel.add(lbTongTien);
        paymentInfoPanel.add(new JLabel("Giảm giá:"));
        paymentInfoPanel.add(lbGiamGiaTot);
        paymentInfoPanel.add(new JLabel("Thành tiền:"));
        paymentInfoPanel.add(lbTong);
        paymentInfoPanel.add(new JLabel("Khách đưa:"));
        paymentInfoPanel.add(txtTienKhachDua);
        paymentInfoPanel.add(new JLabel("Tiền thừa:"));
        paymentInfoPanel.add(lbTienThua);
        
        paymentPanel.add(paymentInfoPanel, BorderLayout.CENTER);
        
        // Add payment button at the bottom
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(btnThanhToan);
        paymentPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add components to right panel
        rightPanel.add(customerPanel, BorderLayout.NORTH);
        rightPanel.add(paymentPanel, BorderLayout.CENTER);
        
        // Add all panels to main layout
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        add(rightPanel, BorderLayout.EAST);
        
        // Set preferred sizes for better initial layout
        jPanel2.setPreferredSize(new Dimension(800, 200));
        cartPanel.setPreferredSize(new Dimension(800, 200));
        productPanel.setPreferredSize(new Dimension(800, 250));
    }

    private void initTables() {
        // Initialize table models with column headers
        modelHoaDon = new DefaultTableModel(new String[]{"Mã HD", "Ngày tạo", "Nhân viên", "Khách hàng", "Tổng tiền", "Trạng thái"}, 0);
        modelSanPham = new DefaultTableModel(new String[]{"Mã SP", "Tên sản phẩm", "Đơn giá", "Số lượng", "Thành tiền"}, 0);
        modelGioHang = new DefaultTableModel(new String[]{"STT", "Mã SP", "Tên sản phẩm", "Đơn giá", "Số lượng", "Thành tiền"}, 0);
        
        // Set models to tables
        tblHoaDon.setModel(modelHoaDon);
        tblSanPham.setModel(modelSanPham);
        tblGioHang.setModel(modelGioHang);

        // Thiết lập tên cột cho bảng sản phẩm
        String[] sanPhamColumns = {"STT", "Mã SP", "Tên SP", "Size", "Màu sắc", "Đơn giá", "Số lượng"};
        for (int i = 0; i < sanPhamColumns.length; i++) {
            modelSanPham.setColumnIdentifiers(sanPhamColumns);
        }

        // Thiết lập tên cột cho bảng giỏ hàng
        String[] gioHangColumns = {"STT", "Mã SP", "Tên SP", "Size", "Màu sắc", "Đơn giá", "Số lượng", "Thành tiền"};
        for (int i = 0; i < gioHangColumns.length; i++) {
            modelGioHang.setColumnIdentifiers(gioHangColumns);
        }

        // Thiết lập tên cột cho bảng hóa đơn
        String[] hoaDonColumns = {"STT", "Mã HD", "Ngày Tạo", "Nhân Viên", "Tổng tiền", "Trạng thái"};
        for (int i = 0; i < hoaDonColumns.length; i++) {
            modelHoaDon.setColumnIdentifiers(hoaDonColumns);
        }
    }

    private void updateTongTien() {
        double tongTien = 0;
        for (int i = 0; i < modelGioHang.getRowCount(); i++) {
            double thanhTien = Double.parseDouble(modelGioHang.getValueAt(i, 7).toString());
            tongTien += thanhTien;
        }

        lbTongTien.setText(String.format("%,.0f", tongTien));
        lbTong.setText(String.format("%,.0f", tongTien));

        // Cập nhật tiền thừa nếu có
        try {
            double tienKhachDua = Double.parseDouble(txtTienKhachDua.getText().replaceAll("[^\\d.]", ""));
            double tienThua = tienKhachDua - tongTien;
            if (tienThua >= 0) {
                lbTienThua.setText(String.format("%,.0f", tienThua));
            }
        } catch (NumberFormatException e) {
            // Bỏ qua nếu chưa nhập tiền khách đưa
        }
    }

    private void updateSTT() {
        for (int i = 0; i < modelGioHang.getRowCount(); i++) {
            modelGioHang.setValueAt(i + 1, i, 0);
        }
    }

    /**
     * Creates and returns a panel with order information
     */
    private JPanel createOrderInfoPanel(String orderId, String orderDate, String staffName,
            String totalAmount, String status) {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Thông tin hóa đơn"));
        panel.setBorder(BorderFactory.createCompoundBorder(
                panel.getBorder(),
                BorderFactory.createEmptyBorder(10, 10, 10, 10))
        );

        // Add order details
        addLabelValuePair(panel, "Mã hóa đơn:", orderId);
        addLabelValuePair(panel, "Ngày tạo:", orderDate);
        addLabelValuePair(panel, "Nhân viên:", staffName);
        addLabelValuePair(panel, "Tổng tiền:", totalAmount);
        addLabelValuePair(panel, "Trạng thái:", status);

        return panel;
    }

    /**
     * Helper method to add a label-value pair to a panel
     */
    private void addLabelValuePair(JPanel panel, String label, String value) {
        JLabel lbl = new JLabel(label);
        lbl.setFont(lbl.getFont().deriveFont(Font.BOLD));
        panel.add(lbl);
        panel.add(new JLabel(value));
    }

    /**
     * Creates and returns a table with order items
     */
    private JTable createOrderItemsTable(String orderId) {
        // Create table model with columns
        String[] columns = {"STT", "Mã SP", "Tên sản phẩm", "Số lượng", "Đơn giá", "Thành tiền"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };

        // Create table with model
        JTable table = new JTable(model);
        table.setAutoCreateRowSorter(true);
        table.setFillsViewportHeight(true);

        try {
            // TODO: Replace with actual service call to get order items
            // List<OrderItem> items = orderService.getOrderItems(orderId);
            // for (OrderItem item : items) {
            //     model.addRow(new Object[]{
            //         model.getRowCount() + 1,
            //         item.getProductCode(),
            //         item.getProductName(),
            //         item.getQuantity(),
            //         formatCurrency(item.getUnitPrice()),
            //         formatCurrency(item.getTotalPrice())
            //     });
            // }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi tải chi tiết đơn hàng: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }

        // Set column widths
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);  // STT
        columnModel.getColumn(1).setPreferredWidth(100); // Mã SP
        columnModel.getColumn(2).setPreferredWidth(200); // Tên SP
        columnModel.getColumn(3).setPreferredWidth(80);  // Số lượng
        columnModel.getColumn(4).setPreferredWidth(100); // Đơn giá
        columnModel.getColumn(5).setPreferredWidth(120); // Thành tiền

        return table;
    }

    /**
     * Prints the specified order
     */
    private void printOrder(String orderId) {
        try {
            // TODO: Implement order printing logic
            // Order order = orderService.getOrderById(orderId);
            // if (order != null) {
            //     PrinterJob job = PrinterJob.getPrinterJob();
            //     job.setPrintable(new OrderPrinter(order));
            //     if (job.printDialog()) {
            //         job.print();
            //     }
            // }
            JOptionPane.showMessageDialog(this,
                    "In hóa đơn " + orderId + " thành công!",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi in hóa đơn: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void calculateTotal() {
        try {
            // Lấy tổng tiền từ label đã định dạng
            BigDecimal total = new BigDecimal(lbTongTien.getText().replaceAll("[^\\d]", ""));
            
            // Khởi tạo giảm giá mặc định là 0
            BigDecimal discount = BigDecimal.ZERO;
            
            // Tính tổng tiền cuối cùng
            BigDecimal finalTotal = total.subtract(discount);

            // Đảm bảo tổng tiền không âm
            if (finalTotal.compareTo(BigDecimal.ZERO) < 0) {
                finalTotal = BigDecimal.ZERO;
            }

            // Cập nhật giao diện - sử dụng lbTongTien để hiển thị tổng tiền
            lbTongTien.setText(String.format("%,.0f VNĐ", finalTotal.doubleValue()));
            lbTong.setText(String.format("%,.0f VNĐ", finalTotal.doubleValue()));
        } catch (Exception e) {
            // Ghi log lỗi và hiển thị thông báo cho người dùng
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Có lỗi xảy ra khi tính toán tổng tiền: " + e.getMessage(), 
                "Lỗi tính toán", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Adds a product to the cart
     */
    private void addToCart(String productId, String productName, BigDecimal price, int quantity) {
        DefaultTableModel model = (DefaultTableModel) tblGioHang.getModel();
        
        // Check if product already exists in cart
        for (int i = 0; i < model.getRowCount(); i++) {
            if (model.getValueAt(i, 0).equals(productId)) {
                // Update quantity if product exists
                int newQty = Integer.parseInt(model.getValueAt(i, 3).toString()) + quantity;
                model.setValueAt(newQty, i, 3);
                updateTotal();
                return;
            }
        }
        
        // Add new product to cart
        model.addRow(new Object[]{
            productId,
            productName,
            price,
            quantity,
            price.multiply(BigDecimal.valueOf(quantity))
        });
        
        updateTotal();
    }
    
    /**
     * Updates the total amount in the cart
     */
    private void updateTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (int i = 0; i < tblGioHang.getRowCount(); i++) {
            BigDecimal price = new BigDecimal(tblGioHang.getValueAt(i, 2).toString());
            int quantity = Integer.parseInt(tblGioHang.getValueAt(i, 3).toString());
            total = total.add(price.multiply(BigDecimal.valueOf(quantity)));
        }
        lbTongTien.setText(String.format("%,.0f VNĐ", total.doubleValue()));
        lbTong.setText(String.format("%,.0f VNĐ", total.doubleValue()));
    }
    
    /**
     * Shows a popup menu when right-clicking on cart items
     */
    private void showCartPopupMenu(int x, int y) {
        JPopupMenu popup = new JPopupMenu();
        JMenuItem removeItem = new JMenuItem("Xóa khỏi giỏ hàng");
        
        removeItem.addActionListener(e -> {
            int row = tblGioHang.getSelectedRow();
            if (row >= 0) {
                ((DefaultTableModel) tblGioHang.getModel()).removeRow(row);
                updateTotal();
            }
        });
        
        popup.add(removeItem);
        popup.show(tblGioHang, x, y);
    }
    
    /**
     * Clears the order form
     */
    private void clearForm() {
        try {
            // Clear cart
            if (tblGioHang != null) {
                DefaultTableModel model = (DefaultTableModel) tblGioHang.getModel();
                model.setRowCount(0);
            }
            
            // Reset payment method
            if (cbbHinhThucThanhToan != null) {
                cbbHinhThucThanhToan.setSelectedIndex(0);
            }
            
            // Reset totals - Chỉ sử dụng các thành phần đã xác nhận tồn tại
            if (lbTongTien != null) {
                lbTongTien.setText("0 VNĐ");
            }
            if (lbTong != null) {
                lbTong.setText("0 VNĐ");
            }
            if (lbTienThua != null) {
                lbTienThua.setText("0 VNĐ");
            }
            
            // Clear customer info - Chỉ sử dụng các thành phần đã xác nhận tồn tại
            if (txtTienKhachDua != null) {
                txtTienKhachDua.setText("");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi xóa dữ liệu form: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // UI Components - Single source of truth for all UI component declarations
    private JButton btnThanhToan;
    private JButton btnTheHoaDon;
    private JButton btnTimKiem;
    private JButton btn_Them1;
    private JButton btn_Them3;
    private JButton btn_Them4;
    private JButton btn_Them5;
    private JComboBox<String> cbbHinhThucThanhToan;
    private JComboBox<String> cbbPhieuGiamGia;
    private JLabel jLabel1;
    private JLabel lbMaHD;
    private JLabel lbNgayTao;
    private JLabel lbNhanVien;
    private JLabel lbKhachHang;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JPanel jPanel5;
    private JPanel jPanel6;
    private JPanel jPanel7;
    private JPanel jPanel8;
    private JPanel jPanel9;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JScrollPane jScrollPane3;
    private JScrollPane jScrollPane5;
    private JLabel lbGiamGiaTot;
    private JLabel lbTienThua;
    private JLabel lbTong;
    private JLabel lbTongTien;
    private JTable tblGioHang;
    private JTable tblHoaDon;
    private JTable tblSanPham;
    private JTextField txtMaKhachhang;
    private JTextField txtTenKhach;
    private JTextField txtTienKhachCK;
    private JTextField txtTienKhachDua;
    private JTextField txtTimKiem;
    
    // Table models - Already declared above

    private void btn_Them1btn_ThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Them1btn_ThemActionPerformed

        modelGioHang.setRowCount(0);

        // Cập nhật thông tin hóa đơn mới
        lbMaHD.setText("HD" + System.currentTimeMillis());
        lbNgayTao.setText(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        lbTongTien.setText("0");
        lbTong.setText("0");
        lbTienThua.setText("0");
        txtTenKhach.setText("Khách Bán Lẻ");
        txtMaKhachhang.setText("");
    }//GEN-LAST:event_btn_Them1btn_ThemActionPerformed

    private void btnTheHoaDonbtn_ThemActionPerformed(java.awt.event.ActionEvent evt) {                                                     
        // 1. Validate cart is not empty
        if (modelGioHang == null || modelGioHang.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng thêm sản phẩm vào giỏ hàng trước khi lưu hóa đơn",
                    "Giỏ hàng trống",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 2. Get customer info
        String customerCode = txtMaKhachhang.getText().trim();
        if (customerCode.isEmpty()) {
            customerCode = "KH000000"; // Default customer code if not provided
        }

        // 3. Get payment method
        String paymentMethod = (String) cbbHinhThucThanhToan.getSelectedItem();
        if (paymentMethod == null) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn hình thức thanh toán",
                    "Thiếu thông tin",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 4. Calculate total amount
        // Lấy giá trị từ các label và loại bỏ các ký tự không phải số
        String totalText = lbTongTien.getText().trim();
        String discountText = lbGiamGiaTot.getText().trim();

        if (totalText.isEmpty() || totalText.equals("0")) {
            JOptionPane.showMessageDialog(this,
                    "Giỏ hàng đang trống. Vui lòng thêm sản phẩm vào giỏ hàng trước khi thanh toán.",
                    "Giỏ hàng trống",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Xử lý chuỗi tiền tệ
            String totalStr = totalText.replaceAll("[^\\d]", "");
            String discountStr = discountText.replaceAll("[^\\d]", "");

            // Nếu sau khi xử lý mà rỗng thì gán = "0"
            totalStr = totalStr.isEmpty() ? "0" : totalStr;
            discountStr = discountStr.isEmpty() ? "0" : discountStr;

            BigDecimal totalAmount = new BigDecimal(totalStr);
            BigDecimal discount = new BigDecimal(discountStr);
            BigDecimal finalAmount = totalAmount.subtract(discount);

            // TODO: Thêm logic xử lý thanh toán ở đây
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Lỗi khi tạo hóa đơn: " + ex.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    } // Kết thúc phương thức btnTheHoaDonbtn_ThemActionPerformed

    /**
     * Formats a BigDecimal value as currency string
     *
     * @param amount The amount to format
     * @return Formatted currency string
     */
    private String formatCurrency(BigDecimal amount) {
        if (amount == null) {
            return "0 VNĐ";
        }
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getNumberInstance(Locale.US);
        formatter.applyPattern("#,##0");
        return formatter.format(amount) + " VNĐ";
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
            java.util.logging.Logger.getLogger(ViewThemKhachHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewThemKhachHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewThemKhachHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewThemKhachHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
    // UI Components - This duplicate block should be removed
    // End of variables declaration//GEN-END:variables
}
