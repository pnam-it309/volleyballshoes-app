package com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewThongKe;

import com.DuAn1.volleyballshoes.app.controller.StatisticController;
import java.math.BigDecimal;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ViewThongKe extends javax.swing.JPanel {

    public ViewThongKe() {

        initComponents();

    }

    /**
     * Cập nhật dữ liệu cho các bảng thống kê dựa trên khoảng thời gian được chọn
     * 
     * @param tuNgay  Ngày bắt đầu lọc dữ liệu
     * @param denNgay Ngày kết thúc lọc dữ liệu
     */
    private void updateTableData(Date tuNgay, Date denNgay) {
        try {
            // Lấy năm từ ngày bắt đầu
            java.util.Calendar cal = java.util.Calendar.getInstance();
            cal.setTime(tuNgay);
            int year = cal.get(java.util.Calendar.YEAR);

            // Lấy dữ liệu thống kê
            StatisticController statisticController = new StatisticController();

            // 1. Cập nhật bảng doanh thu
            Object[][] revenueData = statisticController.getRevenueDataByYear(year);
            DefaultTableModel revenueModel = (DefaultTableModel) tblDoanhThu.getModel();
            revenueModel.setRowCount(0); // Xóa dữ liệu cũ

            // Thêm dữ liệu mới vào bảng doanh thu
            for (Object[] row : revenueData) {
                revenueModel.addRow(new Object[] {
                        row[0], // Tháng
                        String.format("%,.0f VNĐ", Double.parseDouble(row[1].toString())) // Định dạng tiền tệ
                });
            }

            // 2. Cập nhật bảng đơn hủy
            Object[][] canceledData = statisticController.getCanceledOrderDataByYear(year);
            DefaultTableModel canceledModel = (DefaultTableModel) tbDonHuy.getModel();
            canceledModel.setRowCount(0); // Xóa dữ liệu cũ

            // Thêm dữ liệu mới vào bảng đơn hủy
            for (Object[] row : canceledData) {
                canceledModel.addRow(new Object[] {
                        row[0], // Tháng
                        row[1] // Số đơn hủy
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    this,
                    "Lỗi khi cập nhật dữ liệu bảng: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabelTong = new javax.swing.JLabel();
        jLableDoanhThu = new javax.swing.JLabel();
        lbSoluong = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabelSoHoaDon = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        lbDonHuy = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        lbKhachHang = new javax.swing.JLabel();
        lbKhachHang1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        cbbLocNam = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        DCTuNgay = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        DCDenNgay = new com.toedter.calendar.JDateChooser();
        btnTim = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        pnDoanhTHu = new javax.swing.JTabbedPane();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDoanhThu = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbDonHuy = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        cbbLocNam1 = new javax.swing.JComboBox<>();
        btnBieuDo = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 255));
        jLabel1.setText("Thống kê quản lý bán sách");

        jPanel5.setBackground(new java.awt.Color(0, 102, 255));
        jPanel5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel5MouseClicked(evt);
            }
        });

        jLabelTong.setBackground(new java.awt.Color(255, 255, 255));
        jLabelTong.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelTong.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTong.setText("Doanh thu");

        jLableDoanhThu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLableDoanhThu.setForeground(new java.awt.Color(255, 255, 255));

        lbSoluong.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbSoluong.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLableDoanhThu, javax.swing.GroupLayout.PREFERRED_SIZE, 220,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel5Layout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(lbSoluong, javax.swing.GroupLayout.Alignment.LEADING,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabelTong, javax.swing.GroupLayout.Alignment.LEADING,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addContainerGap(29, Short.MAX_VALUE)));
        jPanel5Layout.setVerticalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(jLabelTong)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13,
                                        Short.MAX_VALUE)
                                .addComponent(lbSoluong, javax.swing.GroupLayout.PREFERRED_SIZE, 22,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLableDoanhThu, javax.swing.GroupLayout.PREFERRED_SIZE, 41,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)));

        jPanel1.setBackground(new java.awt.Color(255, 51, 51));
        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1MouseClicked(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Số hóa đơn đã thanh toán");

        jLabelSoHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabelSoHoaDon.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabelSoHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap(25, Short.MAX_VALUE)));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabelSoHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 42,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(19, 19, 19)));

        jPanel2.setBackground(new java.awt.Color(0, 204, 102));
        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel2MouseClicked(evt);
            }
        });

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Số hóa đơn hủy");

        lbDonHuy.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbDonHuy.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel4)
                                        .addComponent(lbDonHuy, javax.swing.GroupLayout.PREFERRED_SIZE, 219,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(36, Short.MAX_VALUE)));
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lbDonHuy, javax.swing.GroupLayout.PREFERRED_SIZE, 41,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(16, 16, 16)));

        jPanel3.setBackground(new java.awt.Color(255, 204, 0));
        jPanel3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel3MouseClicked(evt);
            }
        });

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Số khách hàng");

        lbKhachHang.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbKhachHang.setForeground(new java.awt.Color(255, 255, 255));

        lbKhachHang1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbKhachHang1.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel5)
                                        .addComponent(lbKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 219,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(36, Short.MAX_VALUE))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(16, 16, 16)
                                        .addComponent(lbKhachHang1, javax.swing.GroupLayout.PREFERRED_SIZE, 219,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap(26, Short.MAX_VALUE))));
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lbKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 41,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                        jPanel3Layout.createSequentialGroup()
                                                .addContainerGap(90, Short.MAX_VALUE)
                                                .addComponent(lbKhachHang1, javax.swing.GroupLayout.PREFERRED_SIZE, 41,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(8, 8, 8))));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        cbbLocNam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "2024", "2023" }));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(cbbLocNam, javax.swing.GroupLayout.PREFERRED_SIZE, 140,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(24, Short.MAX_VALUE)));
        jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(cbbLocNam, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(32, Short.MAX_VALUE)));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Lọc theo năm");

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel8.setText("Từ");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel9.setText("Đến");

        btnTim.setBackground(new java.awt.Color(0, 102, 255));
        btnTim.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTim.setForeground(new java.awt.Color(255, 255, 255));
        btnTim.setText("Tìm");
        btnTim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 33,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(DCTuNgay, javax.swing.GroupLayout.PREFERRED_SIZE, 170,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(39, 39, 39)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(DCDenNgay, javax.swing.GroupLayout.PREFERRED_SIZE, 170,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(btnTim, javax.swing.GroupLayout.PREFERRED_SIZE, 52,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(30, Short.MAX_VALUE)));
        jPanel6Layout.setVerticalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(btnTim)
                                        .addGroup(jPanel6Layout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(DCDenNgay, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGroup(jPanel6Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING,
                                                                false)
                                                        .addComponent(DCTuNgay, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                Short.MAX_VALUE))))
                                .addGap(28, 28, 28)));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("Lọc theo khoảng thời gian");

        jSeparator1.setBackground(new java.awt.Color(255, 0, 0));
        jSeparator1.setForeground(new java.awt.Color(255, 0, 0));

        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblDoanhThu.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                        { null, null, null, null, null },
                        { null, null, null, null, null },
                        { null, null, null, null, null },
                        { null, null, null, null, null }
                },
                new String[] {
                        "STT", "Mã hóa đơn", "Ngày tạo hóa đơn", "Số lượng", "Tổng tiền"
                }));
        jScrollPane1.setViewportView(tblDoanhThu);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
                jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel8Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1103, Short.MAX_VALUE)
                                .addContainerGap()));
        jPanel8Layout.setVerticalGroup(
                jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 371,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(27, Short.MAX_VALUE)));

        pnDoanhTHu.addTab("Doanh thu theo thời gian", jPanel8);

        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tbDonHuy.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                        { null, null, null, null, null },
                        { null, null, null, null, null },
                        { null, null, null, null, null },
                        { null, null, null, null, null }
                },
                new String[] {
                        "STT", "Mã hóa đơn", "Ngày tạo hóa đơn", "Số lượng", "Tổng tiền"
                }));
        jScrollPane2.setViewportView(tbDonHuy);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
                jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel7Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1103, Short.MAX_VALUE)
                                .addContainerGap()));
        jPanel7Layout.setVerticalGroup(
                jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        pnDoanhTHu.addTab("Hóa đơn hủy", jPanel7);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        cbbLocNam1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2024", "2023" }));

        btnBieuDo.setText("Biểu đồ");
        btnBieuDo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBieuDoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
                jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(cbbLocNam1, 0, 95, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(btnBieuDo)
                                .addContainerGap()));
        jPanel10Layout.setVerticalGroup(
                jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(cbbLocNam1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnBieuDo))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(428, 428, 428)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 237,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(pnDoanhTHu, javax.swing.GroupLayout.PREFERRED_SIZE, 0,
                                                        Short.MAX_VALUE)
                                                .addContainerGap())
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout
                                                .createSequentialGroup()
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jSeparator1)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(0, 0, Short.MAX_VALUE)
                                                                .addGroup(layout.createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.TRAILING,
                                                                        false)
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addGroup(layout.createParallelGroup(
                                                                                        javax.swing.GroupLayout.Alignment.LEADING)
                                                                                        .addComponent(jLabel6,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                79,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                        .addComponent(jPanel4,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                .addPreferredGap(
                                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                        Short.MAX_VALUE)
                                                                                .addGroup(layout.createParallelGroup(
                                                                                        javax.swing.GroupLayout.Alignment.LEADING)
                                                                                        .addComponent(jLabel7)
                                                                                        .addComponent(jPanel6,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                .addGap(43, 43, 43)
                                                                                .addComponent(jPanel10,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addComponent(jPanel5,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addGap(20, 20, 20)
                                                                                .addComponent(jPanel1,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addGap(18, 18, 18)
                                                                                .addComponent(jPanel2,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addPreferredGap(
                                                                                        javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                .addComponent(jPanel3,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                                .addGap(20, 20, 20)))));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(24, 24, 24)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(37, 37, 37)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel6)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(37, 37, 37))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel7)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING,
                                                                false)
                                                        .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(pnDoanhTHu, javax.swing.GroupLayout.PREFERRED_SIZE, 464,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap()));
    }// </editor-fold>//GEN-END:initComponents

    private void btnTimActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnTimActionPerformed
        try {
            // Lấy ngày bắt đầu và kết thúc từ JDateChooser
            Date tuNgay = DCTuNgay.getDate();
            Date denNgay = DCDenNgay.getDate();

            // Kiểm tra đã chọn đủ ngày chưa
            if (tuNgay == null || denNgay == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn đầy đủ ngày bắt đầu và kết thúc", "Thông báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Kiểm tra ngày bắt đầu phải trước hoặc bằng ngày kết thúc
            if (tuNgay.after(denNgay)) {
                JOptionPane.showMessageDialog(this, "Ngày bắt đầu phải trước hoặc bằng ngày kết thúc", "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Lấy instance của StatisticController
            StatisticController statisticController = new StatisticController();

            // Lấy dữ liệu thống kê trong khoảng thời gian
            BigDecimal doanhThu = statisticController.getRevenueByDateRange(tuNgay, denNgay);
            int soDonHang = statisticController.getTotalOrders();
            int soDonHuy = statisticController.getCanceledOrders();
            int soKhachHangMoi = statisticController.getNewCustomersCount();

            // Hiển thị dữ liệu lên giao diện
            jLableDoanhThu.setText(String.format("%,.0f VNĐ", doanhThu != null ? doanhThu.doubleValue() : 0));
            jLabelSoHoaDon.setText(String.valueOf(soDonHang));
            lbDonHuy.setText(String.valueOf(soDonHuy));
            lbKhachHang.setText(String.valueOf(soKhachHangMoi));

            // Cập nhật dữ liệu bảng
            updateTableData(tuNgay, denNgay);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Có lỗi xảy ra: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }// GEN-LAST:event_btnTimActionPerformed

    private void jPanel5MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jPanel5MouseClicked

    }// GEN-LAST:event_jPanel5MouseClicked

    private void btnBieuDoActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnBieuDoActionPerformed

    }// GEN-LAST:event_btnBieuDoActionPerformed

    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jPanel1MouseClicked

    }// GEN-LAST:event_jPanel1MouseClicked

    private void jPanel2MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jPanel2MouseClicked

    }// GEN-LAST:event_jPanel2MouseClicked

    private void jPanel3MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jPanel3MouseClicked

    }// GEN-LAST:event_jPanel3MouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser DCDenNgay;
    private com.toedter.calendar.JDateChooser DCTuNgay;
    private javax.swing.JButton btnBieuDo;
    private javax.swing.JButton btnTim;
    private javax.swing.JComboBox<String> cbbLocNam;
    private javax.swing.JComboBox<String> cbbLocNam1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelSoHoaDon;
    private javax.swing.JLabel jLabelTong;
    private javax.swing.JLabel jLableDoanhThu;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lbDonHuy;
    private javax.swing.JLabel lbKhachHang;
    private javax.swing.JLabel lbKhachHang1;
    private javax.swing.JLabel lbSoluong;
    private javax.swing.JTabbedPane pnDoanhTHu;
    private javax.swing.JTable tbDonHuy;
    private javax.swing.JTable tblDoanhThu;
    // End of variables declaration//GEN-END:variables

}
