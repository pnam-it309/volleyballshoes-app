package com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewBanHang;

import com.DuAn1.volleyballshoes.app.entity.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.math.BigDecimal;

public class ViewBanHang extends javax.swing.JPanel {

    private List<Order> orderList;
    private List<Product> productList;
    private List<Customer> customerList;
    private List<OrderDetail> cartItems;
    private DefaultTableModel orderTableModel;
    private DefaultTableModel productTableModel;
    private DefaultTableModel cartTableModel;
    private Order currentOrder;
    private BigDecimal totalAmount = BigDecimal.ZERO;
    
    public ViewBanHang() {
        initComponents();
        initializeData();
        setupTables();
        loadData();
    }
    
    private void initializeData() {
        orderList = new ArrayList<>();
        productList = new ArrayList<>();
        customerList = new ArrayList<>();
        cartItems = new ArrayList<>();
        
        // Sample data
        productList.add(Product.builder()
            .productId(1)
            .productName("Nike Air Max")
            .productCode("SP001")
            .build());
        
        customerList.add(Customer.builder()
            .customerId(1)
            .customerFullName("Nguyễn Văn A")
            .customerCode("KH001")
            .customerPhone("0123456789")
            .build());
    }
    
    private void setupTables() {
        // Setup order table
        String[] orderColumns = {"Mã HĐ", "Ngày tạo", "Khách hàng", "Tổng tiền", "Trạng thái"};
        orderTableModel = new DefaultTableModel(orderColumns, 0);
        tblHoaDon.setModel(orderTableModel);
        
        // Setup product table
        String[] productColumns = {"Mã SP", "Tên sản phẩm", "Giá", "Tồn kho"};
        productTableModel = new DefaultTableModel(productColumns, 0);
        tblSanPham.setModel(productTableModel);
        
        // Setup cart table
        String[] cartColumns = {"Sản phẩm", "Số lượng", "Đơn giá", "Thành tiền"};
        cartTableModel = new DefaultTableModel(cartColumns, 0);
        tblGioHang.setModel(cartTableModel);
    }
    
    private void loadData() {
        loadOrders();
        loadProducts();
        updateOrderInfo();
    }
    
    private void loadOrders() {
        orderTableModel.setRowCount(0);
        for (Order order : orderList) {
            Object[] row = {
                "HD" + String.format("%03d", order.getOrderId()),
                order.getOrderCreateAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                "Khách hàng", // Replace with actual customer name
                "0 VNĐ", // Replace with actual total
                "Đang xử lý"
            };
            orderTableModel.addRow(row);
        }
    }
    
    private void loadProducts() {
        productTableModel.setRowCount(0);
        for (Product product : productList) {
            Object[] row = {
                product.getProductCode(),
                product.getProductName(),
                "100,000 VNĐ", // Replace with actual price
                "50" // Replace with actual stock
            };
            productTableModel.addRow(row);
        }
    }
    
    private void updateOrderInfo() {
        if (currentOrder != null) {
            lbMaHD.setText("HD" + String.format("%03d", currentOrder.getOrderId()));
            lbNgayTao.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
            lbMaNV.setText("NV001"); // Replace with actual staff ID
        }
        lbTongTien.setText(formatCurrency(totalAmount));
        lbTong.setText(formatCurrency(totalAmount));
    }
    
    private String formatCurrency(BigDecimal amount) {
        return String.format("%,.0f VNĐ", amount.doubleValue());
    }
    
    private void addToCart(Product product, int quantity) {
        // Check if product already exists in cart
        for (OrderDetail item : cartItems) {
            if (item.getProductId() == product.getProductId()) {
                item.setQuantity(item.getQuantity() + quantity);
                updateCartDisplay();
                return;
            }
        }
        
        // Add new item to cart
        OrderDetail newItem = OrderDetail.builder()
            .productId(product.getProductId())
            .quantity(quantity)
            .unitPrice(BigDecimal.valueOf(100000)) // Sample price
            .build();
        
        cartItems.add(newItem);
        updateCartDisplay();
    }
    
    private void removeFromCart(int index) {
        if (index >= 0 && index < cartItems.size()) {
            cartItems.remove(index);
            updateCartDisplay();
        }
    }
    
    private void updateCartDisplay() {
        cartTableModel.setRowCount(0);
        totalAmount = BigDecimal.ZERO;
        
        for (OrderDetail item : cartItems) {
            Product product = findProductById(item.getProductId());
            BigDecimal itemTotal = item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            totalAmount = totalAmount.add(itemTotal);
            
            Object[] row = {
                product != null ? product.getProductName() : "Unknown",
                item.getQuantity(),
                formatCurrency(item.getUnitPrice()),
                formatCurrency(itemTotal)
            };
            cartTableModel.addRow(row);
        }
        
        updateOrderInfo();
    }
    
    private Product findProductById(int productId) {
        return productList.stream()
            .filter(p -> p.getProductId() == productId)
            .findFirst()
            .orElse(null);
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblGioHang = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        tab = new javax.swing.JTabbedPane();
        jPanel9 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtMaKhachhang = new javax.swing.JTextField();
        txtTenKhach = new javax.swing.JTextField();
        btn_Them4 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
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

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(null);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 255));
        jLabel1.setText("BÁN HÀNG");
        add(jLabel1);
        jLabel1.setBounds(560, 10, 98, 25);

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));

        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã HD", "Ngày Tạo", "Nhân Vien", "Tổng Sản Phảm", "Trạng Thái"
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(btn_Them3)
                .addGap(28, 28, 28)
                .addComponent(btnTheHoaDon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_Them5)
                .addGap(18, 18, 18)
                .addComponent(btn_Them1)
                .addGap(17, 17, 17))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 819, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addGap(27, 27, 27)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        add(jPanel2);
        jPanel2.setBounds(10, 50, 831, 210);

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        jPanel4.setForeground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(null);

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

        jPanel4.add(jScrollPane1);
        jScrollPane1.setBounds(7, 72, 817, 155);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel4.add(jComboBox1);
        jComboBox1.setBounds(566, 18, 120, 31);

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel4.add(jComboBox2);
        jComboBox2.setBounds(291, 18, 120, 31);

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel4.add(jComboBox3);
        jComboBox3.setBounds(704, 18, 120, 31);

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel4.add(jComboBox4);
        jComboBox4.setBounds(429, 18, 119, 31);

        add(jPanel4);
        jPanel4.setBounds(6, 479, 0, 0);

        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        jPanel5.setForeground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(null);

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

        jPanel5.add(jPanel1);
        jPanel1.setBounds(10, 20, 210, 100);

        add(jPanel5);
        jPanel5.setBounds(855, 77, 0, 636);

        jLabel2.setText("Sản phẩm");
        add(jLabel2);
        jLabel2.setBounds(10, 470, 60, 16);

        jLabel3.setText("Giỏ Hàng");
        add(jLabel3);
        jLabel3.setBounds(10, 280, 60, 16);

        jLabel4.setText("Hóa đơn");
        add(jLabel4);
        jLabel4.setBounds(10, 30, 60, 20);

        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã SPCT", "Tên SP", "NXB", "Tác Giả", "Thể Loại", "Số Lượng", "Giá"
            }
        ));
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tblSanPham);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 810, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(9, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(jPanel7);
        jPanel7.setBounds(10, 490, 830, 230);

        tblGioHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã SPCT", "Tên SP", "Loại Bìa", "Loại Giấy", "Kích Thước", "Giá Bán", "Số Lượng", "Thành Tiền"
            }
        ));
        tblGioHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblGioHangMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblGioHang);

        add(jScrollPane2);
        jScrollPane2.setBounds(10, 300, 830, 152);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        jLabel6.setText("Thông tin khách hàng");

        txtMaKhachhang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaKhachhangActionPerformed(evt);
            }
        });

        txtTenKhach.setText("Khách Bán Lẻ");

        btn_Them4.setBackground(new java.awt.Color(0, 102, 255));
        btn_Them4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_Them4.setForeground(new java.awt.Color(255, 255, 255));
        btn_Them4.setText("Chọn");
        btn_Them4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Them4btn_ThemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6)
                    .addComponent(txtTenKhach, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
                    .addComponent(txtMaKhachhang))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_Them4, javax.swing.GroupLayout.PREFERRED_SIZE, 63, Short.MAX_VALUE)
                .addContainerGap())
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(txtTenKhach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

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

        cbbHinhThucThanhToan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "  ", "Tiền Mặt", "Chuyển Khoản", "Cả 2", " " }));
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

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel16))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(lbNgayTao, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                                            .addComponent(lbMaHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(lbMaNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(lbTenKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(lbTongTien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtTienKhachCK, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtTienKhachDua, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addGap(24, 24, 24)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbTong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addGap(46, 46, 46)
                                .addComponent(lbTienThua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addComponent(jLabel13)
                                        .addGap(18, 18, 18)
                                        .addComponent(cbbPhieuGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addComponent(jLabel14)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(cbbHinhThucThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(1, 1, 1)))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(lbGiamGiaTot, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(lbMaHD))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(lbNgayTao))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(lbMaNV))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(lbTenKhachHang))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(lbTongTien))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbbPhieuGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbGiamGiaTot)
                .addGap(17, 17, 17)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(cbbHinhThucThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtTienKhachDua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtTienKhachCK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(lbTong))
                        .addGap(27, 27, 27)
                        .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbTienThua, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        tab.addTab("Tại Quầy", jPanel9);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(853, Short.MAX_VALUE)
                .addComponent(tab, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tab, javax.swing.GroupLayout.PREFERRED_SIZE, 715, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(49, Short.MAX_VALUE))
        );

        add(jPanel3);
        jPanel3.setBounds(0, 0, 1120, 770);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_Them1btn_ThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Them1btn_ThemActionPerformed
        // Create new order
        try {
            currentOrder = Order.builder()
                .orderId(orderList.size() + 1)
                .orderCreateAt(LocalDateTime.now())
                .build();
            
            orderList.add(currentOrder);
            cartItems.clear();
            totalAmount = BigDecimal.ZERO;
            
            loadOrders();
            updateOrderInfo();
            cartTableModel.setRowCount(0);
            
            JOptionPane.showMessageDialog(this, "Tạo hóa đơn mới thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tạo hóa đơn: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_Them1btn_ThemActionPerformed

    private void btnTheHoaDonbtn_ThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTheHoaDonbtn_ThemActionPerformed
        // Print invoice
        if (currentOrder == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn để in!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            // Here you would integrate with JasperReports to print the invoice
            JOptionPane.showMessageDialog(this, "Đang in hóa đơn...", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi in hóa đơn: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnTheHoaDonbtn_ThemActionPerformed

    private void btn_Them3btn_ThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Them3btn_ThemActionPerformed
        // Delete selected order
        int selectedRow = tblHoaDon.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn cần xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Bạn có chắc chắn muốn xóa hóa đơn này?", 
            "Xác nhận xóa", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                orderList.remove(selectedRow);
                loadOrders();
                JOptionPane.showMessageDialog(this, "Xóa hóa đơn thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa hóa đơn: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btn_Them3btn_ThemActionPerformed


    private void btn_Them5btn_ThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Them5btn_ThemActionPerformed
        // Refresh data
        try {
            loadData();
            JOptionPane.showMessageDialog(this, "Đã làm mới dữ liệu!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi làm mới dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_Them5btn_ThemActionPerformed

    private void tblHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMouseClicked
        int selectedRow = tblHoaDon.getSelectedRow();
        if (selectedRow != -1 && selectedRow < orderList.size()) {
            currentOrder = orderList.get(selectedRow);
            updateOrderInfo();
            // Load cart items for selected order
            // This would typically load from database
        }
    }//GEN-LAST:event_tblHoaDonMouseClicked

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
        // Add product to cart on double click
        if (evt.getClickCount() == 2) {
            int selectedRow = tblSanPham.getSelectedRow();
            if (selectedRow != -1 && selectedRow < productList.size()) {
                Product selectedProduct = productList.get(selectedRow);
                addToCart(selectedProduct, 1);
            }
        }
    }//GEN-LAST:event_tblSanPhamMouseClicked

    private void tblGioHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblGioHangMouseClicked
        // Remove item from cart on right click
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
            int selectedRow = tblGioHang.getSelectedRow();
            if (selectedRow != -1) {
                int confirm = JOptionPane.showConfirmDialog(this, 
                    "Bạn có muốn xóa sản phẩm này khỏi giỏ hàng?", 
                    "Xác nhận", 
                    JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    removeFromCart(selectedRow);
                }
            }
        }
    }//GEN-LAST:event_tblGioHangMouseClicked

    private void txtTimKiemSPCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtTimKiemSPCaretUpdate
      
    }//GEN-LAST:event_txtTimKiemSPCaretUpdate

    private void tblHoaDonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tblHoaDonMouseEntered

    private void btnThanhToanbtn_ThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanbtn_ThemActionPerformed

    }//GEN-LAST:event_btnThanhToanbtn_ThemActionPerformed

    private void txtTienKhachCKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTienKhachCKActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTienKhachCKActionPerformed

    private void txtTienKhachCKCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtTienKhachCKCaretUpdate
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTienKhachCKCaretUpdate

    private void txtTienKhachDuaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTienKhachDuaKeyReleased

    }//GEN-LAST:event_txtTienKhachDuaKeyReleased

    private void txtTienKhachDuaCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtTienKhachDuaCaretUpdate

    }//GEN-LAST:event_txtTienKhachDuaCaretUpdate

    private void cbbHinhThucThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbHinhThucThanhToanActionPerformed

    }//GEN-LAST:event_cbbHinhThucThanhToanActionPerformed

    private void cbbPhieuGiamGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbPhieuGiamGiaActionPerformed

    }//GEN-LAST:event_cbbPhieuGiamGiaActionPerformed

    private void btn_Them4btn_ThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Them4btn_ThemActionPerformed
        // Add new customer
        String customerCode = txtMaKhachhang.getText().trim();
        String customerName = txtTenKhach.getText().trim();
        
        if (customerCode.isEmpty() || customerName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin khách hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            Customer newCustomer = Customer.builder()
                .customerId(customerList.size() + 1)
                .customerCode(customerCode)
                .customerFullName(customerName)
                .build();
            
            customerList.add(newCustomer);
            txtMaKhachhang.setText("");
            txtTenKhach.setText("");
            
            JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm khách hàng: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_Them4btn_ThemActionPerformed

    private void txtMaKhachhangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaKhachhangActionPerformed
        // Search customer by code
        String customerCode = txtMaKhachhang.getText().trim();
        if (!customerCode.isEmpty()) {
            Customer customer = customerList.stream()
                .filter(c -> c.getCustomerCode().equals(customerCode))
                .findFirst()
                .orElse(null);
            
            if (customer != null) {
                txtTenKhach.setText(customer.getCustomerFullName());
                lbTenKhachHang.setText(customer.getCustomerFullName());
            } else {
                txtTenKhach.setText("");
                lbTenKhachHang.setText("Khách lẻ");
            }
        }
    }//GEN-LAST:event_txtMaKhachhangActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JButton btnTheHoaDon;
    private javax.swing.JButton btn_Them1;
    private javax.swing.JButton btn_Them3;
    private javax.swing.JButton btn_Them4;
    private javax.swing.JButton btn_Them5;
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
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
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
    private javax.swing.JTabbedPane tab;
    private javax.swing.JTable tblGioHang;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTextField txtMaKhachhang;
    private javax.swing.JTextField txtTenKhach;
    private javax.swing.JTextField txtTienKhachCK;
    private javax.swing.JTextField txtTienKhachDua;
    // End of variables declaration//GEN-END:variables
}
