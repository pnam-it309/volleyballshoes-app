package com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewBanHang;

import com.DuAn1.volleyballshoes.app.controller.OrderController;
import com.DuAn1.volleyballshoes.app.dao.CustomerDAO;
import com.DuAn1.volleyballshoes.app.dao.ProductVariantDAO;
import com.DuAn1.volleyballshoes.app.dao.StaffDAO;
import com.DuAn1.volleyballshoes.app.dto.request.OrderCreateRequest;
import com.DuAn1.volleyballshoes.app.dto.request.OrderItemRequest;
import com.DuAn1.volleyballshoes.app.dto.response.OrderResponse;
import com.DuAn1.volleyballshoes.app.entity.Customer;
import com.DuAn1.volleyballshoes.app.entity.ProductVariant;
import com.DuAn1.volleyballshoes.app.entity.Staff;
import com.DuAn1.volleyballshoes.app.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BanHangPanel extends JPanel {
    // Controllers & DAOs
    private final OrderController orderController;
    private final CustomerDAO customerDAO;
    private final StaffDAO staffDAO;
    private final ProductVariantDAO productVariantDAO;
    
    // UI Components
    private JTable tblHoaDon;
    private JTable tblSanPham;
    private JTable tblGioHang;
    private JLabel lbMaHD;
    private JLabel lbNgayTao;
    private JLabel lbMaNV;
    private JLabel lbTenKhachHang;
    private JLabel lbTongTien;
    private JLabel lbTienThua;
    private JLabel lbTong;
    private JButton btnThanhToan;
    private JButton btnTaoMoiHoaDon;
    private JButton btnLamMoi;
    private JTextField txtMaKhachHang;
    private JTextField txtTenKhach;
    private JComboBox<String> cbbHinhThucThanhToan;
    private JComboBox<String> cbbPhieuGiamGia;
    private JTextField txtTienKhachDua;
    
    // Data models
    private DefaultTableModel orderTableModel;
    private DefaultTableModel productTableModel;
    private DefaultTableModel cartTableModel;
    
    // Business data
    private List<OrderItemRequest> cartItems = new ArrayList<>();
    private BigDecimal totalAmount = BigDecimal.ZERO;
    private Staff currentStaff;
    private Customer currentCustomer;
    
    public void init() {
        initializeComponents();
        setupUI();
        loadInitialData();
    }
    
    private void initializeComponents() {
        // Initialize tables
        orderTableModel = new DefaultTableModel(new String[]{"Mã HĐ", "Ngày tạo", "Khách hàng", "Tổng tiền", "Trạng thái"}, 0);
        productTableModel = new DefaultTableModel(new String[]{"Mã SP", "Tên sản phẩm", "Giá", "Tồn kho"}, 0);
        cartTableModel = new DefaultTableModel(new String[]{"Sản phẩm", "Số lượng", "Đơn giá", "Thành tiền"}, 0);
        
        tblHoaDon = new JTable(orderTableModel);
        tblSanPham = new JTable(productTableModel);
        tblGioHang = new JTable(cartTableModel);
        
        // Initialize buttons
        btnThanhToan = new JButton("Thanh toán");
        btnTaoMoiHoaDon = new JButton("Tạo mới hóa đơn");
        btnLamMoi = new JButton("Làm mới");
        
        // Initialize labels
        lbMaHD = new JLabel("");
        lbNgayTao = new JLabel("");
        lbMaNV = new JLabel("");
        lbTenKhachHang = new JLabel("");
        lbTongTien = new JLabel("0 VNĐ");
        lbTienThua = new JLabel("0 VNĐ");
        lbTong = new JLabel("0 VNĐ");
        
        // Initialize text fields
        txtMaKhachHang = new JTextField(15);
        txtTenKhach = new JTextField(20);
        txtTienKhachDua = new JTextField(15);
        
        // Initialize combo boxes
        cbbHinhThucThanhToan = new JComboBox<>(new String[]{"Tiền mặt", "Chuyển khoản", "Thẻ tín dụng"});
        cbbPhieuGiamGia = new JComboBox<>(new String[]{"Không áp dụng", "Giảm 10%", "Giảm 20%"});
    }
    
    private void setupUI() {
        setLayout(new BorderLayout());
        
        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Top panel - Title
        JLabel titleLabel = new JLabel("BÁN HÀNG");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Center panel - Split pane for left and right
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(600);
        
        // Left panel - Order list and product list
        JPanel leftPanel = new JPanel(new BorderLayout(5, 5));
        
        // Order list panel
        JPanel orderListPanel = new JPanel(new BorderLayout());
        orderListPanel.setBorder(BorderFactory.createTitledBorder("Danh sách hóa đơn"));
        orderListPanel.add(new JScrollPane(tblHoaDon), BorderLayout.CENTER);
        
        // Button panel for order actions
        JPanel orderButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        orderButtonPanel.add(btnTaoMoiHoaDon);
        orderButtonPanel.add(btnLamMoi);
        orderListPanel.add(orderButtonPanel, BorderLayout.SOUTH);
        
        // Product list panel
        JPanel productListPanel = new JPanel(new BorderLayout());
        productListPanel.setBorder(BorderFactory.createTitledBorder("Danh sách sản phẩm"));
        productListPanel.add(new JScrollPane(tblSanPham), BorderLayout.CENTER);
        
        // Add to left panel with split pane
        JSplitPane leftSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, orderListPanel, productListPanel);
        leftSplitPane.setDividerLocation(200);
        leftPanel.add(leftSplitPane, BorderLayout.CENTER);
        
        // Right panel - Order details and cart
        JPanel rightPanel = new JPanel(new BorderLayout(5, 5));
        
        // Order info panel
        JPanel orderInfoPanel = new JPanel(new GridBagLayout());
        orderInfoPanel.setBorder(BorderFactory.createTitledBorder("Thông tin hóa đơn"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Add components to order info panel
        addFormField(orderInfoPanel, gbc, 0, "Mã HĐ:", lbMaHD);
        addFormField(orderInfoPanel, gbc, 1, "Ngày tạo:", lbNgayTao);
        addFormField(orderInfoPanel, gbc, 2, "Mã NV:", lbMaNV);
        addFormField(orderInfoPanel, gbc, 3, "Mã KH:", txtMaKhachHang);
        addFormField(orderInfoPanel, gbc, 4, "Tên KH:", txtTenKhach);
        addFormField(orderInfoPanel, gbc, 5, "Hình thức TT:", cbbHinhThucThanhToan);
        addFormField(orderInfoPanel, gbc, 6, "Phiếu GG:", cbbPhieuGiamGia);
        addFormField(orderInfoPanel, gbc, 7, "Tiền khách đưa:", txtTienKhachDua);
        addFormField(orderInfoPanel, gbc, 8, "Tiền thừa:", lbTienThua);
        addFormField(orderInfoPanel, gbc, 9, "Tổng tiền:", lbTong);
        
        // Cart panel
        JPanel cartPanel = new JPanel(new BorderLayout());
        cartPanel.setBorder(BorderFactory.createTitledBorder("Giỏ hàng"));
        cartPanel.add(new JScrollPane(tblGioHang), BorderLayout.CENTER);
        
        // Button panel for cart actions
        JPanel cartButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        cartButtonPanel.add(btnThanhToan);
        cartPanel.add(cartButtonPanel, BorderLayout.SOUTH);
        
        // Add to right panel with split pane
        JSplitPane rightSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, orderInfoPanel, cartPanel);
        rightSplitPane.setDividerLocation(300);
        rightPanel.add(rightSplitPane, BorderLayout.CENTER);
        
        // Add to main split pane
        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(rightPanel);
        
        // Add to main panel
        mainPanel.add(splitPane, BorderLayout.CENTER);
        
        // Add to this panel
        add(mainPanel, BorderLayout.CENTER);
        
        // Setup event listeners
        setupEventListeners();
    }
    
    private void addFormField(JPanel panel, GridBagConstraints gbc, int y, String label, JComponent component) {
        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(new JLabel(label), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panel.add(component, gbc);
        
        // Reset fill and weight for next row
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
    }
    
    private void setupEventListeners() {
        // Create new order button
        btnTaoMoiHoaDon.addActionListener(e -> createNewOrder());
        
        // Refresh button
        btnLamMoi.addActionListener(e -> refreshData());
        
        // Checkout button
        btnThanhToan.addActionListener(e -> processCheckout());
        
        // Customer ID field - search customer when focus is lost
        txtMaKhachHang.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                searchCustomer();
            }
        });
        
        // Product table selection
        tblSanPham.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tblSanPham.getSelectedRow();
                if (selectedRow >= 0) {
                    addProductToCart(selectedRow);
                }
            }
        });
        
        // Cart table selection - for removing items
        tblGioHang.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && e.getFirstIndex() >= 0) {
                int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Bạn có chắc chắn muốn xóa sản phẩm này khỏi giỏ hàng?",
                    "Xác nhận xóa",
                    JOptionPane.YES_NO_OPTION
                );
                
                if (choice == JOptionPane.YES_OPTION) {
                    removeFromCart(tblGioHang.getSelectedRow());
                }
            }
        });
        
        // Payment amount field - calculate change
        txtTienKhachDua.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { calculateChange(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { calculateChange(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { calculateChange(); }
        });
    }
    
    private void loadInitialData() {
        // Load current staff (in a real app, this would be the logged-in user)
        currentStaff = staffDAO.findById(1).orElse(null); // Default to staff with ID 1 for demo
        
        if (currentStaff != null) {
            lbMaNV.setText(currentStaff.getStaffCode());
        }
        
        // Load orders
        loadOrders();
        
        // Load products
        loadProducts();
        
        // Initialize new order
        createNewOrder();
    }
    
    private void loadOrders() {
        // Clear existing data
        orderTableModel.setRowCount(0);
        
        try {
            // Get orders from controller with pagination
            List<OrderResponse> orders = orderController.getAllOrders(0, 10); // First page, 10 items per page
            
            // Add to table
            for (OrderResponse order : orders) {
                orderTableModel.addRow(new Object[]{
                    order.getOrderCode(),
                    order.getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                    order.getCustomerName(),
                    String.format("%,.0f VNĐ", order.getFinalAmount().doubleValue()),
                    order.getStatus()
                });
            }
        } catch (Exception e) {
            showError("Lỗi tải danh sách hóa đơn: " + e.getMessage());
        }
    }
    
    private void loadProducts() {
        // Clear existing data
        productTableModel.setRowCount(0);
        
        try {
            // Get all product variants
            List<ProductVariant> variants = productVariantDAO.findAll();
            
            // Add to table
            for (ProductVariant variant : variants) {
                // In a real app, you would get the product name from the product entity
                String productName = "Sản phẩm " + variant.getVariantId();
                productTableModel.addRow(new Object[]{
                    variant.getVariantCode(),
                    productName,
                    String.format("%,.0f VNĐ", variant.getPrice().doubleValue()),
                    variant.getQuantityInStock()
                });
            }
        } catch (Exception e) {
            showError("Lỗi tải danh sách sản phẩm: " + e.getMessage());
        }
    }
    
    private void createNewOrder() {
        // Reset cart
        cartItems.clear();
        cartTableModel.setRowCount(0);
        
        // Reset customer info
        currentCustomer = null;
        txtMaKhachHang.setText("");
        txtTenKhach.setText("");
        
        // Reset payment info
        txtTienKhachDua.setText("");
        lbTienThua.setText("0 VNĐ");
        lbTongTien.setText("0 VNĐ");
        lbTong.setText("0 VNĐ");
        
        // Generate new order code (temporary, will be replaced by server)
        lbMaHD.setText("TẠO MỚI");
        lbNgayTao.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        
        // Reset total amount
        totalAmount = BigDecimal.ZERO;
        
        // Set focus to customer ID field
        txtMaKhachHang.requestFocus();
    }
    
    private void searchCustomer() {
        String customerIdOrCode = txtMaKhachHang.getText().trim();
        if (customerIdOrCode.isEmpty()) {
            return;
        }
        
        try {
            // Try to find by ID
            try {
                int customerId = Integer.parseInt(customerIdOrCode);
                currentCustomer = customerDAO.findById(customerId).orElse(null);
            } catch (NumberFormatException e) {
                // If not a number, try to find by code
                currentCustomer = customerDAO.findByCode(customerIdOrCode).orElse(null);
            }
            
            if (currentCustomer != null) {
                txtTenKhach.setText(currentCustomer.getFullName());
            } else {
                txtTenKhach.setText("Không tìm thấy khách hàng");
            }
        } catch (Exception e) {
            showError("Lỗi tìm kiếm khách hàng: " + e.getMessage());
        }
    }
    
    private void addProductToCart(int productRow) {
        try {
            // Get product variant from table
            String variantCode = (String) productTableModel.getValueAt(productRow, 0);
            ProductVariant variant = productVariantDAO.findByCode(variantCode)
                .orElseThrow(() -> new BusinessException("Không tìm thấy sản phẩm"));
            
            // Check if already in cart
            for (OrderItemRequest item : cartItems) {
                if (item.getVariantId().equals(variant.getVariantId())) {
                    // Update quantity
                    item.setQuantity(item.getQuantity() + 1);
                    updateCartDisplay();
                    return;
                }
            }
            
            // Add new item to cart
            OrderItemRequest newItem = new OrderItemRequest();
            newItem.setVariantId(variant.getVariantId());
            newItem.setQuantity(1);
            newItem.setUnitPrice(variant.getPrice());
            
            cartItems.add(newItem);
            updateCartDisplay();
            
        } catch (Exception e) {
            showError("Lỗi thêm sản phẩm vào giỏ: " + e.getMessage());
        }
    }
    
    private void removeFromCart(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < cartItems.size()) {
            cartItems.remove(rowIndex);
            updateCartDisplay();
        }
    }
    
    private void updateCartDisplay() {
        // Clear existing data
        cartTableModel.setRowCount(0);
        
        // Reset total amount
        totalAmount = BigDecimal.ZERO;
        
        // Add items to cart table
        for (OrderItemRequest item : cartItems) {
            try {
                ProductVariant variant = productVariantDAO.findById(item.getVariantId())
                    .orElseThrow(() -> new BusinessException("Không tìm thấy sản phẩm"));
                
                BigDecimal itemTotal = item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
                totalAmount = totalAmount.add(itemTotal);
                
                cartTableModel.addRow(new Object[]{
                    variant.getVariantName(),
                    item.getQuantity(),
                    String.format("%,.0f VNĐ", item.getUnitPrice().doubleValue()),
                    String.format("%,.0f VNĐ", itemTotal.doubleValue())
                });
                
            } catch (Exception e) {
                showError("Lỗi cập nhật giỏ hàng: " + e.getMessage());
            }
        }
        
        // Update total labels
        lbTongTien.setText(String.format("%,.0f VNĐ", totalAmount.doubleValue()));
        lbTong.setText(String.format("%,.0f VNĐ", totalAmount.doubleValue()));
        
        // Recalculate change if payment amount is entered
        calculateChange();
    }
    
    private void calculateChange() {
        try {
            String amountPaidStr = txtTienKhachDua.getText().trim();
            if (amountPaidStr.isEmpty()) {
                lbTienThua.setText("0 VNĐ");
                return;
            }
            
            BigDecimal amountPaid = new BigDecimal(amountPaidStr.replaceAll("[^\\d.]", ""));
            BigDecimal change = amountPaid.subtract(totalAmount);
            
            if (change.compareTo(BigDecimal.ZERO) >= 0) {
                lbTienThua.setText(String.format("%,.0f VNĐ", change.doubleValue()));
            } else {
                lbTienThua.setText("Không đủ tiền");
            }
            
        } catch (NumberFormatException e) {
            // Ignore invalid number format
            lbTienThua.setText("Số tiền không hợp lệ");
        }
    }
    
    private void processCheckout() {
        // Validate customer
        if (currentCustomer == null) {
            showError("Vui lòng chọn khách hàng");
            txtMaKhachHang.requestFocus();
            return;
        }
        
        // Validate cart not empty
        if (cartItems.isEmpty()) {
            showError("Giỏ hàng trống");
            return;
        }
        
        // Validate payment amount
        try {
            String amountPaidStr = txtTienKhachDua.getText().trim();
            BigDecimal amountPaid = new BigDecimal(amountPaidStr.replaceAll("[^\\d.]", ""));
            
            if (amountPaid.compareTo(totalAmount) < 0) {
                showError("Số tiền thanh toán không đủ");
                return;
            }
            
        } catch (NumberFormatException e) {
            showError("Số tiền thanh toán không hợp lệ");
            return;
        }
        
        try {
            // Create order request
            OrderCreateRequest request = new OrderCreateRequest();
            request.setCustomerId(currentCustomer.getCustomerId());
            request.setStaffId(currentStaff.getStaffId());
            request.setPaymentMethod((String) cbbHinhThucThanhToan.getSelectedItem());
            request.setItems(cartItems);
            
            // Call controller to create order
            OrderResponse orderResponse = orderController.createOrder(request);
            
            // Show success message
            showInfo("Tạo đơn hàng thành công. Mã đơn hàng: " + orderResponse.getOrderCode());
            
            // Refresh data
            refreshData();
            
        } catch (BusinessException e) {
            showError("Lỗi tạo đơn hàng: " + e.getMessage());
        } catch (Exception e) {
            showError("Lỗi hệ thống: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void refreshData() {
        loadOrders();
        loadProducts();
        createNewOrder();
    }
    
    // Utility methods
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
    
    private void showInfo(String message) {
        JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }
}
