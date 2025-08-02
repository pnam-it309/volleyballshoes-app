package com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewBanHang;

import com.DuAn1.volleyballshoes.app.controller.CustomerController;
import com.DuAn1.volleyballshoes.app.controller.OrderController;
import com.DuAn1.volleyballshoes.app.controller.ProductController;
import com.DuAn1.volleyballshoes.app.dto.response.OrderItemResponse;
import com.DuAn1.volleyballshoes.app.dto.response.OrderResponse;
import com.DuAn1.volleyballshoes.app.entity.Customer;
import com.DuAn1.volleyballshoes.app.entity.Product;
import com.DuAn1.volleyballshoes.app.entity.ProductVariant;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class ViewBanHang extends javax.swing.JPanel {

    private final OrderController orderController = new OrderController();
    private final CustomerController customerController = new CustomerController();
    private DefaultTableModel modelHoaDon;
    private DefaultTableModel modelSanPham;
    private DefaultTableModel modelGioHang;

    public ViewBanHang() {
        initComponents();
    }

    private void initTables() {
        // Khởi tạo model cho bảng hóa đơn
        modelHoaDon = (DefaultTableModel) tblHoaDon.getModel();
        modelSanPham = (DefaultTableModel) tblSanPham.getModel();
        modelGioHang = (DefaultTableModel) tblGioHang.getModel();

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
    private void calculateTotal() {
        try {
            BigDecimal total = new BigDecimal(txtTongTien.getText().replaceAll("[^\\d]", ""));
            BigDecimal discount = new BigDecimal(txtGiamGia.getText().replaceAll("[^\\d]", ""));
            BigDecimal finalTotal = total.subtract(discount);
            
            if (finalTotal.compareTo(BigDecimal.ZERO) < 0) {
                finalTotal = BigDecimal.ZERO;
            }
            
            txtThanhTien.setText(String.format("%,d", finalTotal.longValue()));
        } catch (Exception e) {
            // Handle any calculation errors
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
                                        .addComponent(btnThanhToan)
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
                            .addComponent(lbTong)))
                    .addComponent(lbTienThua, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void btnTheHoaDonbtn_ThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTheHoaDonbtn_ThemActionPerformed

        try {
            // 1. Validate cart is not empty
            if (modelGioHang.getRowCount() == 0) {
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
            BigDecimal totalAmount = new BigDecimal(txtTongTien.getText().replaceAll("[^\\d]", ""));
            BigDecimal discount = new BigDecimal(txtGiamGia.getText().replaceAll("[^\\d]", ""));
            BigDecimal finalAmount = totalAmount.subtract(discount);
    
            // 5. Create order request
            OrderRequest orderRequest = new OrderRequest();
            orderRequest.setCustomerCode(customerCode);
            orderRequest.setStaffCode("NV001"); // Get from session or login
            orderRequest.setPaymentMethod(paymentMethod);
            orderRequest.setTotalAmount(finalAmount);
            orderRequest.setNote(txtGhiChu.getText().trim());
    
            // 6. Add order items
            for (int i = 0; i < modelGioHang.getRowCount(); i++) {
                String productCode = modelGioHang.getValueAt(i, 1).toString(); // Assuming column 1 is product code
                int quantity = Integer.parseInt(modelGioHang.getValueAt(i, 3).toString()); // Assuming column 3 is quantity
                orderRequest.addOrderItem(productCode, quantity);
            }
    
            // 7. Save order
            OrderResponse response = orderController.createOrder(orderRequest);
            
            if (response != null && response.isSuccess()) {
                // 8. Show success message
                JOptionPane.showMessageDialog(this,
                    "Lưu hóa đơn thành công!\nMã hóa đơn: " + response.getOrderCode(),
                    "Thành công",
                    JOptionPane.INFORMATION_MESSAGE);
    
                // 9. Print invoice if needed
                if (JOptionPane.showConfirmDialog(this,
                    "Bạn có muốn in hóa đơn không?",
                    "In hóa đơn",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    printInvoice(response);
                }
    
                // 10. Reset form for new order
                resetOrderForm();
            } else {
                throw new Exception("Có lỗi xảy ra khi lưu hóa đơn");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Lỗi: " + e.getMessage(),
                "Lỗi lưu hóa đơn",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnTheHoaDonbtn_ThemActionPerformed

    private void btn_Them3btn_ThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Them3btn_ThemActionPerformed
        // TODO add your handling code here:
        QuetQRBanHang qrForm = new QuetQRBanHang();
        qrForm.setQRCodeScannedListener(new QuetQRBanHang.QRCodeScannedListener() {
            @Override
            public void onQRCodeScanned(String maSP) {
                if (maSP != null && !maSP.isEmpty()) {
                    // Tìm sản phẩm trong bảng sản phẩm theo mã SP
                    for (int i = 0; i < tblSanPham.getRowCount(); i++) {
                        if (tblSanPham.getValueAt(i, 1).toString().equals(maSP)) {
                            // Mô phỏng click vào sản phẩm để thêm vào giỏ hàng
                            tblSanPham.setRowSelectionInterval(i, i);
                            tblSanPhamMouseClicked(null);
                            break;
                        }
                    }
                }
            }
        });
        qrForm.setVisible(true);
    }//GEN-LAST:event_btn_Them3btn_ThemActionPerformed


    private void btn_Them5btn_ThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Them5btn_ThemActionPerformed
        // Tạo dialog thêm khách hàng mới
        javax.swing.JDialog dialog = new javax.swing.JDialog();
        dialog.setTitle("Thêm khách hàng mới");
        dialog.setModal(true);
        dialog.setSize(400, 350);
        dialog.setLocationRelativeTo(this);

        // Tạo các thành phần giao diện
        JPanel panel = new JPanel();
        panel.setLayout(new java.awt.GridLayout(0, 2, 10, 10));
        panel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Tạo các trường nhập liệu
        JTextField txtMaKH = new JTextField();
        JTextField txtTenKH = new JTextField();
        JTextField txtSDT = new JTextField();
        JTextField txtEmail = new JTextField();
        JTextField txtDiaChi = new JTextField();

        // Thêm các thành phần vào panel
        panel.add(new JLabel("Mã khách hàng:"));
        panel.add(txtMaKH);
        panel.add(new JLabel("Tên khách hàng:"));
        panel.add(txtTenKH);
        panel.add(new JLabel("Số điện thoại:"));
        panel.add(txtSDT);
        panel.add(new JLabel("Email:"));
        panel.add(txtEmail);
        panel.add(new JLabel("Địa chỉ:"));
        panel.add(txtDiaChi);

        // Nút lưu
        JButton btnLuu = new JButton("Lưu");
        btnLuu.addActionListener(e -> {
            // Kiểm tra các trường bắt buộc
            if (txtMaKH.getText().trim().isEmpty() || txtTenKH.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(dialog,
                        "Vui lòng nhập đầy đủ thông tin bắt buộc (Mã KH, Tên KH)",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Ở đây bạn cần thêm code để lưu khách hàng vào CSDL
            // Ví dụ: 
            // Customer newCustomer = new Customer();
            // newCustomer.setCode(txtMaKH.getText().trim());
            // newCustomer.setName(txtTenKH.getText().trim());
            // newCustomer.setPhone(txtSDT.getText().trim());
            // newCustomer.setEmail(txtEmail.getText().trim());
            // newCustomer.setAddress(txtDiaChi.getText().trim());
            // customerService.add(newCustomer);
            // Cập nhật giao diện
            txtMaKhachhang.setText(txtMaKH.getText().trim());
            lbTenKhachHang.setText(txtTenKH.getText().trim());

            JOptionPane.showMessageDialog(dialog,
                    "Thêm khách hàng thành công!",
                    "Thành công", JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
        });

        // Nút hủy
        JButton btnHuy = new JButton("Hủy");
        btnHuy.addActionListener(e -> dialog.dispose());

        // Panel chứa các nút
        JPanel buttonPanel = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));
        buttonPanel.add(btnHuy);
        buttonPanel.add(btnLuu);

        // Thêm các panel vào dialog
        dialog.setLayout(new java.awt.BorderLayout());
        dialog.add(panel, java.awt.BorderLayout.CENTER);
        dialog.add(buttonPanel, java.awt.BorderLayout.SOUTH);

        // Hiển thị dialog
        dialog.setVisible(true);
    }//GEN-LAST:event_btn_Them5btn_ThemActionPerformed

    private void tblHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMouseClicked
        int selectedRow = tblHoaDon.getSelectedRow();
        if (selectedRow >= 0) {
            try {
                // Lấy thông tin hóa đơn được chọn
                String maHD = modelHoaDon.getValueAt(selectedRow, 1).toString();
                String ngayTao = modelHoaDon.getValueAt(selectedRow, 2).toString();
                String nhanVien = modelHoaDon.getValueAt(selectedRow, 3).toString();
                String tongTien = modelHoaDon.getValueAt(selectedRow, 4).toString();
                String trangThai = modelHoaDon.getValueAt(selectedRow, 5).toString();

                // Hiển thị thông tin chi tiết hóa đơn
                JDialog dialog = new JDialog();
                dialog.setTitle("Chi tiết hóa đơn: " + maHD);
                dialog.setSize(800, 600);
                dialog.setLocationRelativeTo(this);
                dialog.setModal(true);

                // Tạo bảng chi tiết hóa đơn
                String[] columnNames = {"STT", "Mã SP", "Tên sản phẩm", "Số lượng", "Đơn giá", "Thành tiền"};
                DefaultTableModel detailModel = new DefaultTableModel(columnNames, 0);
                JTable detailTable = new JTable(detailModel);
                JScrollPane scrollPane = new JScrollPane(detailTable);

                // TODO: Lấy chi tiết hóa đơn từ CSDL dựa vào maHD
                // Ví dụ:
                // List<ChiTietHoaDon> chiTietList = hoaDonService.getChiTietHoaDon(maHD);
                // for (ChiTietHoaDon ct : chiTietList) {
                //     Object[] row = {
                //         detailModel.getRowCount() + 1,
                //         ct.getMaSP(),
                //         ct.getTenSP(),
                //         ct.getSoLuong(),
                //         ct.getDonGia(),
                //         ct.getThanhTien()
                //     };
                //     detailModel.addRow(row);
                // }
                // Tạo panel thông tin chung
                JPanel infoPanel = new JPanel(new GridLayout(0, 2, 10, 5));
                infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                infoPanel.add(new JLabel("Mã hóa đơn:"));
                infoPanel.add(new JLabel(maHD));
                infoPanel.add(new JLabel("Ngày tạo:"));
                infoPanel.add(new JLabel(ngayTao));
                infoPanel.add(new JLabel("Nhân viên:"));
                infoPanel.add(new JLabel(nhanVien));
                infoPanel.add(new JLabel("Tổng tiền:"));
                infoPanel.add(new JLabel(tongTien));
                infoPanel.add(new JLabel("Trạng thái:"));
                infoPanel.add(new JLabel(trangThai));

                // Tạo nút đóng
                JButton btnDong = new JButton("Đóng");
                btnDong.addActionListener(e -> dialog.dispose());

                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                buttonPanel.add(btnDong);

                // Thêm các thành phần vào dialog
                dialog.setLayout(new BorderLayout());
                dialog.add(infoPanel, BorderLayout.NORTH);
                dialog.add(scrollPane, BorderLayout.CENTER);
                dialog.add(buttonPanel, BorderLayout.SOUTH);

                dialog.setVisible(true);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Lỗi khi tải chi tiết hóa đơn: " + ex.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_tblHoaDonMouseClicked

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
        int selectedRow = tblSanPham.getSelectedRow();
        if (selectedRow >= 0) {
            // Lấy thông tin sản phẩm từ bảng
            String maSP = modelSanPham.getValueAt(selectedRow, 1).toString();
            String tenSP = modelSanPham.getValueAt(selectedRow, 2).toString();
            String size = modelSanPham.getValueAt(selectedRow, 3).toString();
            String mauSac = modelSanPham.getValueAt(selectedRow, 4).toString();
            double donGia = Double.parseDouble(modelSanPham.getValueAt(selectedRow, 5).toString());

            // Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
            boolean found = false;
            for (int i = 0; i < modelGioHang.getRowCount(); i++) {
                if (modelGioHang.getValueAt(i, 1).equals(maSP)
                        && modelGioHang.getValueAt(i, 3).equals(size)) {
                    // Tăng số lượng nếu đã có
                    int soLuong = Integer.parseInt(modelGioHang.getValueAt(i, 6).toString()) + 1;
                    modelGioHang.setValueAt(soLuong, i, 6);
                    double thanhTien = soLuong * donGia;
                    modelGioHang.setValueAt(thanhTien, i, 7);
                    found = true;
                    break;
                }
            }

            // Nếu chưa có thì thêm mới
            if (!found) {
                Object[] row = {
                    modelGioHang.getRowCount() + 1,
                    maSP,
                    tenSP,
                    size,
                    mauSac,
                    donGia,
                    1, // Số lượng mặc định là 1
                    donGia // Thành tiền ban đầu bằng đơn giá
                };
                modelGioHang.addRow(row);
            }

            // Cập nhật tổng tiền
            updateTongTien();
        }
    }//GEN-LAST:event_tblSanPhamMouseClicked

    private void tblGioHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblGioHangMouseClicked
        if (SwingUtilities.isRightMouseButton(evt)) {
            int row = tblGioHang.rowAtPoint(evt.getPoint());
            if (row >= 0) {
                // Hiển thị menu ngữ cảnh
                JPopupMenu popupMenu = new JPopupMenu();
                JMenuItem menuItemXoa = new JMenuItem("Xóa");
                JMenuItem menuItemGiamSoLuong = new JMenuItem("Giảm số lượng");
                JMenuItem menuItemTangSoLuong = new JMenuItem("Tăng số lượng");

                menuItemXoa.addActionListener(e -> {
                    modelGioHang.removeRow(row);
                    updateTongTien();
                    updateSTT();
                });

                menuItemGiamSoLuong.addActionListener(e -> {
                    int soLuong = Integer.parseInt(modelGioHang.getValueAt(row, 6).toString());
                    if (soLuong > 1) {
                        double donGia = Double.parseDouble(modelGioHang.getValueAt(row, 5).toString());
                        modelGioHang.setValueAt(soLuong - 1, row, 6);
                        modelGioHang.setValueAt((soLuong - 1) * donGia, row, 7);
                        updateTongTien();
                    }
                });

                menuItemTangSoLuong.addActionListener(e -> {
                    int soLuong = Integer.parseInt(modelGioHang.getValueAt(row, 6).toString());
                    double donGia = Double.parseDouble(modelGioHang.getValueAt(row, 5).toString());
                    modelGioHang.setValueAt(soLuong + 1, row, 6);
                    modelGioHang.setValueAt((soLuong + 1) * donGia, row, 7);
                    updateTongTien();
                });

                popupMenu.add(menuItemTangSoLuong);
                popupMenu.add(menuItemGiamSoLuong);
                popupMenu.add(menuItemXoa);
                popupMenu.show(tblGioHang, evt.getX(), evt.getY());
            }
        }

    }//GEN-LAST:event_tblGioHangMouseClicked

    private final ProductController productController = new ProductController();

    private void txtTimKiemSPCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtTimKiemSPCaretUpdate
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                String searchText = txtTimKiemSP.getText().trim();

                // Clear the table if search text is empty
                if (searchText.isEmpty()) {
                    modelSanPham.setRowCount(0);
                    return null;
                }

                try {
                    // Search products by name or code
                    List<Product> products = productController.searchProducts(searchText);

                    // Update the table on the EDT
                    SwingUtilities.invokeLater(() -> {
                        modelSanPham.setRowCount(0); // Clear existing data

                        // Add matching products to the table
                        int stt = 1;
                        for (Product product : products) {
                            // Get product variants for display
                            List<ProductVariant> variants = productController.getProductVariants(product.getId());

                            if (variants != null && !variants.isEmpty()) {
                                for (ProductVariant variant : variants) {
                                    Object[] row = {
                                        stt++,
                                        product.getProductCode(),
                                        product.getProductName(),
                                        variant.getSize(),
                                        variant.getColor(),
                                        variant.getPrice(),
                                        variant.getStockQuantity() > 0 ? variant.getStockQuantity() : "Hết hàng"
                                    };
                                    modelSanPham.addRow(row);
                                }
                            } else {
                                // If no variants, just show the product
                                Object[] row = {
                                    stt++,
                                    product.getProductCode(),
                                    product.getProductName(),
                                    "N/A",
                                    "N/A",
                                    "N/A",
                                    "N/A"
                                };
                                modelSanPham.addRow(row);
                            }
                        }
                    });

                } catch (Exception ex) {
                    // Handle any errors on the EDT
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(
                                ViewBanHang.this,
                                "Lỗi khi tìm kiếm sản phẩm: " + ex.getMessage(),
                                "Lỗi",
                                JOptionPane.ERROR_MESSAGE
                        );
                        ex.printStackTrace();
                    });
                }

                return null;
            }
        };

        // Execute the worker
        worker.execute();
    }//GEN-LAST:event_txtTimKiemSPCaretUpdate

    private void tblHoaDonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMouseEntered
        // TODO add your handling code here:
         int row = tblHoaDon.rowAtPoint(evt.getPoint());
    if (row >= 0) {
        try {
            // Get order ID from the selected row
            String orderId = modelHoaDon.getValueAt(row, 1).toString();
            String status = modelHoaDon.getValueAt(row, 5).toString(); // Assuming status is in column 5
            
            // Get additional order details (you might need to fetch this from your data source)
            String orderDate = modelHoaDon.getValueAt(row, 2).toString();
            String staffName = modelHoaDon.getValueAt(row, 3).toString();
            String totalAmount = modelHoaDon.getValueAt(row, 4).toString();
            
            // Create HTML-formatted tooltip
            String tooltip = String.format(
                "<html><b>Mã HĐ:</b> %s<br>" +
                "<b>Ngày tạo:</b> %s<br>" +
                "<b>Nhân viên:</b> %s<br>" +
                "<b>Tổng tiền:</b> %s<br>" +
                "<b>Trạng thái:</b> %s</html>",
                orderId, orderDate, staffName, totalAmount, status
            );
            
            // Set the tooltip
            tblHoaDon.setToolTipText(tooltip);
        } catch (Exception ex) {
            // If there's an error, show a generic tooltip
            tblHoaDon.setToolTipText("Không thể tải thông tin chi tiết hóa đơn");
        }
    } else {
        // If not hovering over a row, clear the tooltip
        tblHoaDon.setToolTipText(null);
    }
    }//GEN-LAST:event_tblHoaDonMouseEntered

    private void btnThanhToanbtn_ThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanbtn_ThemActionPerformed
        try {
            // 1. Validate cart is not empty
            if (modelGioHang.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, 
                    "Vui lòng thêm sản phẩm vào giỏ hàng trước khi thanh toán", 
                    "Giỏ hàng trống", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
    
            // 2. Get customer info
            String customerCode = txtMaKH.getText().trim();
            if (customerCode.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Vui lòng nhập mã khách hàng hoặc tạo khách hàng mới", 
                    "Thiếu thông tin", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
    
            // 3. Get payment method
            String paymentMethod = (String) cboHinhThucThanhToan.getSelectedItem();
            if (paymentMethod == null) {
                JOptionPane.showMessageDialog(this, 
                    "Vui lòng chọn hình thức thanh toán", 
                    "Thiếu thông tin", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
    
            // 4. Get payment amount and calculate change
            BigDecimal totalAmount = new BigDecimal(txtTongTien.getText().replaceAll("[^\\d]", ""));
            BigDecimal paidAmount = new BigDecimal(0);
            
            if ("Tiền mặt".equals(paymentMethod)) {
                try {
                    paidAmount = new BigDecimal(txtTienKhachDua.getText().replaceAll("[^\\d]", ""));
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, 
                        "Số tiền khách đưa không hợp lệ", 
                        "Lỗi", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
    
                if (paidAmount.compareTo(totalAmount) < 0) {
                    JOptionPane.showMessageDialog(this, 
                        "Số tiền thanh toán không được nhỏ hơn tổng tiền", 
                        "Lỗi thanh toán", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
    
            // 5. Create order
            OrderRequest orderRequest = new OrderRequest();
            orderRequest.setCustomerCode(customerCode);
            orderRequest.setStaffCode("NV001"); // Get from session or login
            orderRequest.setPaymentMethod(paymentMethod);
            orderRequest.setTotalAmount(totalAmount);
            
            // Add order items
            for (int i = 0; i < modelGioHang.getRowCount(); i++) {
                String productCode = modelGioHang.getValueAt(i, 0).toString();
                int quantity = Integer.parseInt(modelGioHang.getValueAt(i, 3).toString());
                orderRequest.addOrderItem(productCode, quantity);
            }
    
            // 6. Process order
            OrderResponse response = orderController.createOrder(orderRequest);
            
            if (response != null && response.isSuccess()) {
                // 7. Show success message and print invoice
                JOptionPane.showMessageDialog(this, 
                    "Thanh toán thành công!\nMã hóa đơn: " + response.getOrderCode(), 
                    "Thành công", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                // 8. Print invoice
                printInvoice(response.getOrderCode(), response.getCustomerName(), 
                            response.getOrderDate(), response.getTotalAmount(), 
                            response.getPaymentMethod(), response.getOrderItems());
                
                // 9. Clear cart and refresh
                clearCart();
                loadOrders();
            } else {
                throw new Exception("Có lỗi xảy ra khi tạo đơn hàng");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi: " + e.getMessage(), 
                "Lỗi thanh toán", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnThanhToanbtn_ThemActionPerformed

    private void txtTienKhachCKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTienKhachCKActionPerformed
        // TODO add your handling code here:
        try {
            // Only process if credit payment is selected
            if ("Chuyển khoản".equals(cboHinhThucThanhToan.getSelectedItem())) {
                // Get and validate the credit amount
                String amountText = txtTienKhachCK.getText().replaceAll("[^\\d]", "");
                if (amountText.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                        "Vui lòng nhập số tiền thanh toán",
                        "Thiếu thông tin",
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }
    
                BigDecimal creditAmount = new BigDecimal(amountText);
                BigDecimal totalAmount = new BigDecimal(txtTongTien.getText().replaceAll("[^\\d]", ""));
    
                // Validate credit amount
                if (creditAmount.compareTo(totalAmount) < 0) {
                    int confirm = JOptionPane.showConfirmDialog(this,
                        "Số tiền thanh toán nhỏ hơn tổng tiền. Bạn có chắc muốn tiếp tục?",
                        "Xác nhận thanh toán",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);
                    
                    if (confirm != JOptionPane.YES_OPTION) {
                        txtTienKhachCK.requestFocus();
                        return;
                    }
                }
    
                // Update UI to show payment details
                txtTienThanhToanCK.setText(String.format("%,d", creditAmount.longValue()));
                
                // Calculate and display change (if any)
                if (creditAmount.compareTo(totalAmount) > 0) {
                    BigDecimal change = creditAmount.subtract(totalAmount);
                    txtTienThuaCK.setText(String.format("%,d", change.longValue()));
                } else {
                    txtTienThuaCK.setText("0");
                }
    
                // Enable/disable payment button based on validation
                btnThanhToanbtn_Them.setEnabled(true);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "Số tiền không hợp lệ. Vui lòng nhập lại.",
                "Lỗi nhập liệu",
                JOptionPane.ERROR_MESSAGE);
            txtTienKhachCK.requestFocus();
        }
    }//GEN-LAST:event_txtTienKhachCKActionPerformed

    private void txtTienKhachCKCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtTienKhachCKCaretUpdate
        // TODO add your handling code here:
        try {
            String text = txtTienKhachCK.getText().replaceAll("[^\\d]", "");
            if (!text.isEmpty()) {
                long amount = Long.parseLong(text);
                NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
                String formatted = nf.format(amount) + " VNĐ";
                txtTienKhachCK.setText(formatted);

                // Position cursor at the end
                txtTienKhachCK.setCaretPosition(formatted.length() - 4);
            }
        } catch (NumberFormatException ex) {
            // Ignore invalid input
        }
    }//GEN-LAST:event_txtTienKhachCKCaretUpdate

    private void txtTienKhachDuaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTienKhachDuaKeyReleased
        try {
            String input = txtTienKhachDua.getText().replaceAll("[^\\d]", "");
            if (!input.isEmpty()) {
                double tienKhachDua = Double.parseDouble(input);
                double tongTien = Double.parseDouble(lbTong.getText().replaceAll("[^\\d.]", ""));
                double tienThua = tienKhachDua - tongTien;
                lbTienThua.setText(String.format("%,.0f", Math.max(0, tienThua)));
            }
        } catch (NumberFormatException e) {
            // Bỏ qua nếu nhập không đúng định dạng
        }
    }//GEN-LAST:event_txtTienKhachDuaKeyReleased

    private void txtTienKhachDuaCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtTienKhachDuaCaretUpdate
        try {
            // Get current text and remove all non-digit characters
            String text = txtTienKhachDua.getText().replaceAll("[^\\d]", "");
            
            if (!text.isEmpty()) {
                // Format the number with thousand separators
                long amount = Long.parseLong(text);
                String formatted = String.format("%,d", amount);
                
                // Update the text field with formatted number
                SwingUtilities.invokeLater(() -> {
                    txtTienKhachDua.setText(formatted);
                    
                    // Move cursor to the end
                    txtTienKhachDua.setCaretPosition(formatted.length());
                    
                    // Calculate and update change
                    try {
                        double tienKhachDua = Double.parseDouble(text);
                        double tongTien = Double.parseDouble(lbTong.getText().replaceAll("[^\\d.]", ""));
                        double tienThua = tienKhachDua - tongTien;
                        lbTienThua.setText(String.format("%,.0f", Math.max(0, tienThua)));
                    } catch (NumberFormatException e) {
                        // Ignore format errors during typing
                    }
                });
            } else {
                // Clear change field if input is empty
                lbTienThua.setText("0");
            }
        } catch (Exception e) {
            // Ignore any parsing errors during typing
        }
    }//GEN-LAST:event_txtTienKhachDuaCaretUpdate

    private void cbbHinhThucThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbHinhThucThanhToanActionPerformed
        String selectedMethod = cbbHinhThucThanhToan.getSelectedItem().toString();
    
        // Reset all payment panels
        pnThanhToanTienMat.setVisible(false);
        pnThanhToanChuyenKhoan.setVisible(false);
        pnThanhToanQuetMa.setVisible(false);
        
        // Enable/disable payment button based on selection
        btnThanhToanbtn_Them.setEnabled(false);
        
        // Show the appropriate panel based on selection
        switch (selectedMethod) {
            case "Tiền mặt":
                pnThanhToanTienMat.setVisible(true);
                txtTienKhachDua.requestFocus();
                break;
            case "Chuyển khoản":
                pnThanhToanChuyenKhoan.setVisible(true);
                txtTienKhachCK.requestFocus();
                break;
            case "Quét mã":
                pnThanhToanQuetMa.setVisible(true);
                // Additional QR code payment setup can go here
                break;
        }
        
        // Clear previous payment inputs
        txtTienKhachDua.setText("");
        txtTienKhachCK.setText("");
        lbTienThua.setText("0");
        txtTienThanhToanCK.setText("0");
        txtTienThuaCK.setText("0");
        
        // Update UI
        pnThanhToanTienMat.revalidate();
        pnThanhToanChuyenKhoan.revalidate();
        pnThanhToanQuetMa.revalidate();
        pnThanhToanTienMat.repaint();
        pnThanhToanChuyenKhoan.repaint();
        pnThanhToanQuetMa.repaint();
    }//GEN-LAST:event_cbbHinhThucThanhToanActionPerformed

    private void cbbPhieuGiamGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbPhieuGiamGiaActionPerformed
        try {
            // Get selected voucher
            String selectedVoucher = (String) cbbPhieuGiamGia.getSelectedItem();
            if (selectedVoucher == null || selectedVoucher.isEmpty() || "Không áp dụng".equals(selectedVoucher)) {
                // Reset discount if no voucher is selected
                txtGiamGia.setText("0");
                calculateTotal();
                return;
            }
    
            // Get the original total before any discount
            BigDecimal total = new BigDecimal(txtTongTien.getText().replaceAll("[^\\d]", ""));
            BigDecimal discountAmount = BigDecimal.ZERO;
    
            // In a real application, you would look up the voucher details from your database
            // For now, we'll use some example logic
            if (selectedVoucher.contains("10%")) {
                // 10% discount
                discountAmount = total.multiply(new BigDecimal("0.1"));
            } else if (selectedVoucher.contains("20%")) {
                // 20% discount
                discountAmount = total.multiply(new BigDecimal("0.2"));
            } else if (selectedVoucher.contains("50k")) {
                // Flat 50,000 VND discount
                discountAmount = new BigDecimal("50000");
                // Ensure discount doesn't exceed total
                if (discountAmount.compareTo(total) > 0) {
                    discountAmount = total;
                }
            }
    
            // Update the discount field
            txtGiamGia.setText(String.format("%,d", discountAmount.longValue()));
    
            // Recalculate the total with discount
            calculateTotal();
    
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Có lỗi khi áp dụng khuyến mãi: " + e.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
            // Reset to no discount on error
            cbbPhieuGiamGia.setSelectedIndex(0);
        }
    }//GEN-LAST:event_cbbPhieuGiamGiaActionPerformed

    private void btn_Them4btn_ThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Them4btn_ThemActionPerformed
        ViewThemKhachHang view = new ViewThemKhachHang();
        view.setViewBanHangCallback((tenKH, sdt, email) -> {
            txtTenKhach.setText(tenKH);
            txtMaKhachhang.setText(sdt); // Sử dụng số điện thoại làm mã khách hàng tạm thời
        });
        view.setVisible(true);
    }//GEN-LAST:event_btn_Them4btn_ThemActionPerformed

    private void txtMaKhachhangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaKhachhangActionPerformed
        // TODO add your handling code here:
          String customerCode = txtMaKhachhang.getText().trim();
    if (customerCode.isEmpty()) {
        return;
    }

    try {
        // Search for customer by code
        Customer customer = customerController.findByCode(customerCode);
        if (customer != null) {
            // Customer found, update UI
            txtTenKhach.setText(customer.getFullName());
            txtSDT.setText(customer.getPhoneNumber() != null ? customer.getPhoneNumber() : "");
            txtEmail.setText(customer.getEmail() != null ? customer.getEmail() : "");
            
            // If customer has a loyalty program, update UI accordingly
            if (customer.getLoyaltyPoints() != null && customer.getLoyaltyPoints() > 0) {
                // Show loyalty points or apply any member benefits
                // This is a placeholder - implement according to your loyalty program
                txtDiemTichLuy.setText(String.valueOf(customer.getLoyaltyPoints()));
            }
            
            // Focus on the next field
            txtGhiChu.requestFocus();
        } else {
            // Customer not found
            int option = JOptionPane.showConfirmDialog(
                this,
                "Không tìm thấy khách hàng với mã: " + customerCode + 
                "\nBạn có muốn tạo mới khách hàng này không?",
                "Không tìm thấy khách hàng",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );
            
            if (option == JOptionPane.YES_OPTION) {
                // Open customer creation dialog
                ViewThemKhachHang view = new ViewThemKhachHang();
                view.setCustomerCode(customerCode);
                view.setViewBanHangCallback((tenKH, sdt, email) -> {
                    txtTenKhach.setText(tenKH);
                    txtMaKhachhang.setText(customerCode);
                    if (sdt != null) txtSDT.setText(sdt);
                    if (email != null) txtEmail.setText(email);
                });
                view.setVisible(true);
            } else {
                // Clear the field if customer not found and not creating new one
                txtMaKhachhang.setText("");
                txtTenKhach.setText("");
                txtSDT.setText("");
                txtEmail.setText("");
            }
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(
            this,
            "Lỗi khi tìm kiếm khách hàng: " + e.getMessage(),
            "Lỗi",
            JOptionPane.ERROR_MESSAGE
        );
        e.printStackTrace();
    }
    }//GEN-LAST:event_txtMaKhachhangActionPerformed
    private void printInvoice(OrderResponse order) {
        try {
            // Create a printable invoice panel
            JPanel invoicePanel = new JPanel(new BorderLayout(10, 10));
            invoicePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            // Header
            JPanel headerPanel = new JPanel();
            headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));

            JLabel titleLabel = new JLabel("HÓA ĐƠN BÁN HÀNG");
            titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
            titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel shopName = new JLabel("CỬA HÀNG GIÀY BÓNG CHUYỀN");
            shopName.setFont(new Font("Arial", Font.BOLD, 14));
            shopName.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel address = new JLabel("Địa chỉ: 123 Đường ABC, Quận 1, TP.HCM");
            address.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel phone = new JLabel("Điện thoại: 0123 456 789");
            phone.setAlignmentX(Component.CENTER_ALIGNMENT);

            headerPanel.add(titleLabel);
            headerPanel.add(Box.createVerticalStrut(10));
            headerPanel.add(shopName);
            headerPanel.add(address);
            headerPanel.add(phone);

            // Order info
            JPanel infoPanel = new JPanel(new GridLayout(0, 2, 5, 5));
            infoPanel.setBorder(BorderFactory.createTitledBorder("Thông tin đơn hàng"));

            infoPanel.add(new JLabel("Mã đơn hàng:"));
            infoPanel.add(new JLabel(order.getOrderCode()));

            infoPanel.add(new JLabel("Ngày tạo:"));
            infoPanel.add(new JLabel(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(order.getOrderDate())));

            infoPanel.add(new JLabel("Khách hàng:"));
            infoPanel.add(new JLabel(order.getCustomerName()));

            infoPanel.add(new JLabel("Nhân viên:"));
            infoPanel.add(new JLabel(order.getStaffName()));

            // Order items
            String[] columns = {"STT", "Tên SP", "Đơn giá", "SL", "Thành tiền"};
            DefaultTableModel model = new DefaultTableModel(columns, 0);

            double total = 0;
            for (int i = 0; i < order.getItems().size(); i++) {
                OrderItemResponse item = order.getItems().get(i);
                double subtotal = item.getUnitPrice().doubleValue() * item.getQuantity();
                total += subtotal;

                model.addRow(new Object[]{
                    i + 1,
                    item.getProductName(),
                    formatCurrency(item.getUnitPrice()),
                    item.getQuantity(),
                    formatCurrency(BigDecimal.valueOf(subtotal))
                });
            }

            JTable itemsTable = new JTable(model);
            itemsTable.setFillsViewportHeight(true);

            // Total
            JPanel totalPanel = new JPanel(new BorderLayout());
            totalPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

            JLabel totalLabel = new JLabel("Tổng cộng: " + formatCurrency(BigDecimal.valueOf(total)));
            totalLabel.setFont(new Font("Arial", Font.BOLD, 14));
            totalLabel.setHorizontalAlignment(JLabel.RIGHT);
            totalPanel.add(totalLabel, BorderLayout.EAST);

            // Footer
            JLabel thankYou = new JLabel("Cảm ơn quý khách! Hẹn gặp lại!");
            thankYou.setFont(new Font("Arial", Font.ITALIC, 12));
            thankYou.setHorizontalAlignment(JLabel.CENTER);

            // Assemble the invoice
            invoicePanel.add(headerPanel, BorderLayout.NORTH);
            invoicePanel.add(infoPanel, BorderLayout.CENTER);
            invoicePanel.add(new JScrollPane(itemsTable), BorderLayout.CENTER);
            invoicePanel.add(totalPanel, BorderLayout.SOUTH);
            invoicePanel.add(thankYou, BorderLayout.SOUTH);

            // Show print dialog
            PrinterJob job = PrinterJob.getPrinterJob();
            job.setJobName("Hoa don " + order.getOrderCode());

            if (job.printDialog()) {
                try {
                    job.print();
                } catch (PrinterException ex) {
                    JOptionPane.showMessageDialog(this,
                            "Lỗi khi in hóa đơn: " + ex.getMessage(),
                            "Lỗi in ấn",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi tạo hóa đơn: " + ex.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

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
