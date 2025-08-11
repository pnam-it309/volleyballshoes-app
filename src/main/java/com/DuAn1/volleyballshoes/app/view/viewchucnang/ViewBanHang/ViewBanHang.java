package com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewBanHang;

import com.DuAn1.volleyballshoes.app.dao.*;
import com.DuAn1.volleyballshoes.app.dao.impl.*;
import com.DuAn1.volleyballshoes.app.entity.*;
import com.DuAn1.volleyballshoes.app.entity.Staff;
import com.DuAn1.volleyballshoes.app.dao.StaffDAO;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.DuAn1.volleyballshoes.app.util.SessionManager;
import com.DuAn1.volleyballshoes.app.utils.XJdbc;
import java.sql.*;

public class ViewBanHang extends javax.swing.JPanel {

    private final CustomerDAO customerDAO = new CustomerDAOImpl();

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
        DefaultTableModel model = (DefaultTableModel) tblGioHang.getModel();
        BigDecimal subtotal = BigDecimal.ZERO;
        BigDecimal totalDiscount = BigDecimal.ZERO;

        // Calculate subtotal and total discount
        for (int i = 0; i < model.getRowCount(); i++) {
            BigDecimal rowTotal = (BigDecimal) model.getValueAt(i, 6);
            BigDecimal rowPrice = (BigDecimal) model.getValueAt(i, 4);
            int quantity = (int) model.getValueAt(i, 3);
            BigDecimal rowSubtotal = rowPrice.multiply(BigDecimal.valueOf(quantity));

            subtotal = subtotal.add(rowSubtotal);
            totalDiscount = totalDiscount.add(rowSubtotal.subtract(rowTotal));
        }

        // Update summary labels
        lbTongTien.setText(formatCurrency(subtotal));
        lbGiamGiaTot.setText(formatCurrency(totalDiscount));

        // Calculate and update total after discount
        BigDecimal total = subtotal.subtract(totalDiscount);
        lbTong.setText(formatCurrency(total));
    }

    // Format currency for display
    private String formatCurrency(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) == 0) {
            return ""; // Return empty string for zero or null amounts
        }
        return String.format("%,d đ", amount.longValue());
    }

    public ViewBanHang() {
        initComponents();
        loadProductVariants();
        initCartTable();
    }

    private void loadProductVariants() {
        try {
            // Get the table model
            DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
            model.setRowCount(0); // Clear existing data

            // Set column names for product variants
            String[] columnNames = {"Mã SP", "Tên SP", "Size", "Màu sắc", "Đế giày", "Số lượng", "Giá", "Hình ảnh"};
            model.setColumnIdentifiers(columnNames);

            // Initialize DAOs
            ProductVariantDAO productVariantDAO = new ProductVariantDAOImpl();
            ProductDAO productDAO = new ProductDAOImpl();
            SizeDAO sizeDAO = new SizeDAOImpl();
            ColorDAO colorDAO = new ColorDAOImpl();
            SoleTypeDAO soleTypeDAO = new SoleTypeDAOImpl();

            // Fetch all required data
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
        jLabel5 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
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
                "Title 1", "Title 2", "Title 3", "Title 4"
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
                .addComponent(btn_Them4, javax.swing.GroupLayout.PREFERRED_SIZE, 60, Short.MAX_VALUE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_khachvanglai)
                .addContainerGap(13, Short.MAX_VALUE))
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

        jLabel5.setText("jLabel5");

        jLabel19.setText("jLabel19");

        jLabel20.setText("jLabel20");

        jLabel21.setText("jLabel21");

        jLabel22.setText("jLabel22");

        jLabel23.setText("jLabel23");

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
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel5))
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel19))
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel11)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addComponent(jLabel12)
                                        .addGap(22, 22, 22)
                                        .addComponent(jLabel22))
                                    .addComponent(jLabel16)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel20)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lbNgayTao, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                                    .addComponent(lbMaHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lbMaNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lbTenKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lbTongTien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addGap(24, 24, 24)
                                .addComponent(lbTong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel23)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbTienThua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanel6Layout.createSequentialGroup()
                                            .addComponent(jLabel14)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(cbbHinhThucThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(1, 1, 1))
                                        .addGroup(jPanel6Layout.createSequentialGroup()
                                            .addComponent(jLabel13)
                                            .addGap(18, 18, 18)
                                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel21)
                                                .addComponent(cbbPhieuGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addComponent(jLabel15)
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtTienKhachCK, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtTienKhachDua, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(lbGiamGiaTot, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(lbMaHD)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(lbNgayTao)
                    .addComponent(jLabel19))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(lbMaNV)
                    .addComponent(jLabel20))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(lbTenKhachHang)
                    .addComponent(jLabel21))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(lbTongTien)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbbPhieuGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbGiamGiaTot)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(jLabel23))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(272, 272, 272))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void btnTheHoaDonbtn_ThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTheHoaDonbtn_ThemActionPerformed
        try {
            // Get customer code from form
            String customerCode = txtMaKhachhang.getText().trim();
            if (customerCode.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng chọn khách hàng hoặc chọn 'Khách vãng lai' trước khi tạo đơn hàng.",
                        "Chưa chọn khách hàng",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Find customer by code
            Customer customer = customerDAO.findByCode(customerCode);
            if (customer == null) {
                JOptionPane.showMessageDialog(this,
                        "Không tìm thấy thông tin khách hàng. Vui lòng chọn lại khách hàng.",
                        "Không tìm thấy khách hàng",
                        JOptionPane.ERROR_MESSAGE);
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

            // Create new order object
            Order newOrder = Order.builder()
                    .staffId(staff.getStaffId())
                    .orderCode(orderCode)
                    .orderStatus("Chưa thanh toán")
                    .orderCreatedAt(now)
                    .build();

            // Save order to database
            OrderDAO orderDAO = new OrderDAOImpl();
            Order savedOrder = orderDAO.save(newOrder);

            if (savedOrder == null || savedOrder.getOrderId() <= 0) {
                throw new Exception("Không thể lưu hóa đơn vào cơ sở dữ liệu");
            }

            // Clear and setup table if needed
            DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
            model.setRowCount(0);

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
                "Chưa thanh toán", // Trạng thái
                formattedDate // Ngày tạo
            };

            model.addRow(rowData);

            // Auto-resize columns
            for (int i = 0; i < tblHoaDon.getColumnCount(); i++) {
                tblHoaDon.getColumnModel().getColumn(i).setPreferredWidth(150);
            }

            // Select the newly created order
            tblHoaDon.setRowSelectionInterval(model.getRowCount() - 1, model.getRowCount() - 1);

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

    private void btn_Them3btn_ThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Them3btn_ThemActionPerformed

        QuetQRBanHang banHang = new QuetQRBanHang(this);
        banHang.setVisible(true);
    }//GEN-LAST:event_btn_Them3btn_ThemActionPerformed


    private void btn_Them5btn_ThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Them5btn_ThemActionPerformed

    }//GEN-LAST:event_btn_Them5btn_ThemActionPerformed

    private void tblHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMouseClicked

    }//GEN-LAST:event_tblHoaDonMouseClicked

    private void txtTimKiemSPCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtTimKiemSPCaretUpdate

    }//GEN-LAST:event_txtTimKiemSPCaretUpdate

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

            // Update cart summary
            updateCartSummary();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi thêm sản phẩm vào giỏ hàng", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_chon_san_phambtn_ThemActionPerformed

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked

    }//GEN-LAST:event_tblSanPhamMouseClicked

    private void btnThanhToanbtn_ThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanbtn_ThemActionPerformed
        // Validate cart is not empty
        if (tblGioHang.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this,
                    "Giỏ hàng đang trống. Vui lòng thêm sản phẩm vào giỏ hàng trước khi thanh toán.",
                    "Giỏ hàng trống",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {

            // Get payment method (default to "Tiền mặt" if not selected)
            String paymentMethod = "Tiền mặt";
            if (cbbHinhThucThanhToan.getSelectedItem() != null) {
                paymentMethod = cbbHinhThucThanhToan.getSelectedItem().toString();
            }

            // Calculate total amount
            BigDecimal totalAmount = BigDecimal.ZERO;
            DefaultTableModel cartModel = (DefaultTableModel) tblGioHang.getModel();
            for (int i = 0; i < cartModel.getRowCount(); i++) {
                BigDecimal rowTotal = (BigDecimal) cartModel.getValueAt(i, 6); // Column 6 is Thành tiền
                totalAmount = totalAmount.add(rowTotal);
            }

            // Confirm payment
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Xác nhận thanh toán đơn hàng với tổng tiền: " + formatCurrency(totalAmount) + "\n"
                    + "Hình thức thanh toán: " + paymentMethod + "\n"
                    + "Bạn có chắc chắn muốn thanh toán?",
                    "Xác nhận thanh toán",
                    JOptionPane.YES_NO_OPTION);

            if (confirm != JOptionPane.YES_OPTION) {
                return; // User cancelled
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

            // Get customer ID from form
            String customerCode = txtMaKhachhang.getText().trim();
            if (customerCode.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng chọn khách hàng hoặc chọn 'Khách vãng lai' trước khi tạo đơn hàng.",
                        "Chưa chọn khách hàng",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Find customer by code
            Customer customer = customerDAO.findByCode(customerCode);
            if (customer == null) {
                JOptionPane.showMessageDialog(this,
                        "Không tìm thấy thông tin khách hàng. Vui lòng chọn lại khách hàng.",
                        "Không tìm thấy khách hàng",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Create order
            Order order = new Order();
            order.setCustomerId(customer.getCustomerId());
            order.setStaffId(staff.getStaffId()); // Use the actual staff ID from database
            order.setOrderFinalAmount(totalAmount);
            order.setOrderPaymentMethod(paymentMethod);
            order.setOrderStatus("completed");
            order.setOrderCreatedAt(LocalDateTime.now());

            // Save order
            OrderDAO orderDAO = new OrderDAOImpl();
            Order savedOrder = orderDAO.save(order);

            if (savedOrder == null) {
                throw new Exception("Không thể tạo đơn hàng. Vui lòng thử lại.");
            }

            // Save order details and update inventory
            ProductVariantDAO productVariantDAO = new ProductVariantDAOImpl();
            OrderDetailDAO orderDetailDAO = new OrderDetailDAOImpl();

            for (int i = 0; i < cartModel.getRowCount(); i++) {
                String sku = cartModel.getValueAt(i, 1).toString();
                int quantity = (int) cartModel.getValueAt(i, 3);
                BigDecimal price = (BigDecimal) cartModel.getValueAt(i, 4);
                BigDecimal discountPercent = (BigDecimal) cartModel.getValueAt(i, 5);
                BigDecimal total = (BigDecimal) cartModel.getValueAt(i, 6);

                // Get product variant by SKU
                ProductVariant variant = null;
                List<ProductVariant> variants = productVariantDAO.findAll();
                for (ProductVariant v : variants) {
                    if (v.getVariantSku().equals(sku)) {
                        variant = v;
                        break;
                    }
                }

                if (variant == null) {
                    throw new Exception("Không tìm thấy sản phẩm với mã: " + sku);
                }

                // Check stock
                if (variant.getQuantity() < quantity) {
                    throw new Exception("Số lượng sản phẩm " + sku + " trong kho không đủ. Chỉ còn " + variant.getQuantity() + " sản phẩm.");
                }

                // Update inventory
                variant.setQuantity(variant.getQuantity() - quantity);
                productVariantDAO.update(variant);

                // Create order detail
                OrderDetail detail = new OrderDetail();
                detail.setOrderId(savedOrder.getOrderId());
                detail.setVariantId(variant.getVariantId());
                detail.setDetailQuantity(quantity);
                detail.setDetailUnitPrice(price);
                detail.setDetailDiscountPercent(discountPercent);
                detail.setDetailTotal(total);

                orderDetailDAO.save(detail);
            }

            // Clear cart
            cartModel.setRowCount(0);
            updateCartSummary();

            // Show success message
            JOptionPane.showMessageDialog(this,
                    "Thanh toán thành công!\n"
                    + "Mã đơn hàng: " + savedOrder.getOrderCode() + "\n"
                    + "Tổng tiền: " + formatCurrency(totalAmount),
                    "Thành công",
                    JOptionPane.INFORMATION_MESSAGE);

            // Refresh orders list
            // Refresh orders table if needed
            // loadOrders(); // Commented out as it's not implemented yet
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi xử lý thanh toán: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
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
        java.awt.EventQueue.invokeLater(() -> {
            ViewThemKhachHang viewThemKhachHang = new ViewThemKhachHang();
            viewThemKhachHang.setLocationRelativeTo(null);
            viewThemKhachHang.setVisible(true);
        });
    }//GEN-LAST:event_btn_Them4btn_ThemActionPerformed

    private void txtMaKhachhangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaKhachhangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaKhachhangActionPerformed

    private void btn_khachvanglaiActionPerformed(java.awt.event.ActionEvent evt) {
        final String GUEST_CODE = "CUS0000";
        final String GUEST_NAME = "Khách vãng lai";

        try {
            // DEBUG 1: kiểm tra kết nối đang dùng DB nào
            try (Connection conn = XJdbc.openConnection()) {
                String dbName = XJdbc.getValue("SELECT DB_NAME()", String.class);
                String schemaName = XJdbc.getValue("SELECT SCHEMA_NAME()", String.class);
                System.out.println("[DEBUG] Đang kết nối DB: " + dbName + ", Schema: " + schemaName);
            } catch (Exception e) {
                System.out.println("[DEBUG] Lỗi khi kiểm tra DB/schema: " + e.getMessage());
            }

            // DEBUG 2: kiểm tra giá trị mã khách hàng truyền vào
            System.out.println("[DEBUG] GUEST_CODE = '" + GUEST_CODE + "', length = " + GUEST_CODE.length());

            Customer guestCustomer = null;

            // DEBUG 3: thử tìm trong DB
            try {
                guestCustomer = customerDAO.findByCode(GUEST_CODE);
                System.out.println("[DEBUG] Kết quả tìm lần 1: " + (guestCustomer != null ? "Tìm thấy" : "Không tìm thấy"));
            } catch (Exception e) {
                System.out.println("[DEBUG] Lỗi khi tìm lần 1: " + e.getMessage());
            }

            // Nếu không tìm thấy thì thử tạo mới
            if (guestCustomer == null) {
                guestCustomer = new Customer();
                guestCustomer.setCustomerCode(GUEST_CODE);
                guestCustomer.setCustomerFullName(GUEST_NAME);
                guestCustomer.setCustomerEmail("");
                guestCustomer.setCustomerPhone("");

                try {
                    guestCustomer = customerDAO.create(guestCustomer);
                    System.out.println("[DEBUG] Đã tạo mới khách hàng: " + guestCustomer.getCustomerCode());
                } catch (Exception createEx) {
                    System.out.println("[DEBUG] Lỗi khi tạo mới: " + createEx.getMessage());

                    // Nếu lỗi trùng khóa
                    if (createEx.getMessage() != null && createEx.getMessage().toLowerCase().contains("duplicate key")) {
                        System.out.println("[DEBUG] Phát hiện trùng khóa → tìm lại khách hàng");
                        try {
                            guestCustomer = customerDAO.findByCode(GUEST_CODE);
                            System.out.println("[DEBUG] Kết quả tìm lại: " + (guestCustomer != null ? "Tìm thấy" : "Không tìm thấy"));
                        } catch (Exception findEx) {
                            System.out.println("[DEBUG] Lỗi khi tìm lại: " + findEx.getMessage());
                        }
                    }
                }
            }

            // Cập nhật form
            if (guestCustomer != null) {
                System.out.println("[DEBUG] Sử dụng khách hàng: " + guestCustomer.getCustomerCode());
                setCustomerToForm(guestCustomer);
            } else {
                System.out.println("[DEBUG] Không tìm được → dùng khách hàng tạm thời");
                guestCustomer = new Customer();
                guestCustomer.setCustomerCode(GUEST_CODE);
                guestCustomer.setCustomerFullName(GUEST_NAME);
                setCustomerToForm(guestCustomer);
            }
        } catch (Exception e) {
            System.err.println("[DEBUG] Lỗi nghiêm trọng: " + e.getMessage());
            e.printStackTrace();
        }

    }

    // Helper method to set customer information to the form
    private void setCustomerToForm(Customer customer) {
        if (customer != null) {
            txtMaKhachhang.setText(customer.getCustomerCode());
            lbTenKhachHang.setText(customer.getCustomerFullName());
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
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
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
    private javax.swing.JTable tblGioHang;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTextField txtMaKhachhang;
    private javax.swing.JTextField txtTienKhachCK;
    private javax.swing.JTextField txtTienKhachDua;
    // End of variables declaration//GEN-END:variables
}
