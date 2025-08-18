package com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewPhieuGiamGia;

import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.time.ZoneId;

import com.DuAn1.volleyballshoes.app.dao.PromotionDAO;
import com.DuAn1.volleyballshoes.app.dao.impl.PromotionDAOImpl;
import com.DuAn1.volleyballshoes.app.entity.Promotion;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ViewPhieuGiamGia extends javax.swing.JPanel {

    private final PromotionDAO promotionDAO = new PromotionDAOImpl();

    public ViewPhieuGiamGia() {

        initComponents();
        loadPromotionsToTable(); // Load data when form opens

    }

    private void clearForm() {
        txtMaPhieu.setText("");
        txtTenPhieu.setText("");
        txtPhanTramGiam.setText("");
        DCNgayTao.setDate(null);
        DCNgayKT.setDate(null);
    }

    private void loadPromotionsToTable() {
        DefaultTableModel model = (DefaultTableModel) tblPhieuGiamGia.getModel();
        model.setRowCount(0); // Clear existing data

        try {
            List<Promotion> promotions = promotionDAO.findAll();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

            int stt = 1; // Biến đếm số thứ tự
            for (Promotion p : promotions) {
                model.addRow(new Object[]{
                    stt++, // Thêm số thứ tự
                    p.getPromoCode(),
                    p.getPromoName(),
                    p.getPromoDiscountValue() + "%",
                    p.getPromoStartDate() != null
                    ? dateFormat.format(Timestamp.valueOf(p.getPromoStartDate())) : "",
                    p.getPromoEndDate() != null
                    ? dateFormat.format(Timestamp.valueOf(p.getPromoEndDate())) : "",});
            }

            // Auto-resize columns to fit content
            for (int i = 0; i < tblPhieuGiamGia.getColumnCount(); i++) {
                tblPhieuGiamGia.getColumnModel().getColumn(i).setPreferredWidth(150);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi tải dữ liệu phiếu giảm giá: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
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
            java.util.logging.Logger.getLogger(ViewPhieuGiamGia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewPhieuGiamGia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewPhieuGiamGia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewPhieuGiamGia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewPhieuGiamGia().setVisible(true);
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        buttonGroup5 = new javax.swing.ButtonGroup();
        buttonGroup6 = new javax.swing.ButtonGroup();
        buttonGroup7 = new javax.swing.ButtonGroup();
        buttonGroup8 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtMaPhieu = new javax.swing.JTextField();
        txtTenPhieu = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        DCNgayTao = new com.toedter.calendar.JDateChooser();
        DCNgayKT = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        txtPhanTramGiam = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        btnTimKiem = new javax.swing.JButton();
        txtHopNhap = new javax.swing.JTextField();
        searchDate = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPhieuGiamGia = new javax.swing.JTable();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 255));
        jLabel1.setText("Quản lý phiếu giảm giá");

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 13))); // NOI18N

        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("Mã phiếu giảm giá: ");

        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("Tên phiếu giảm giá: ");

        jLabel7.setForeground(new java.awt.Color(51, 51, 51));
        jLabel7.setText("Ngày bắt đầu: ");

        jLabel8.setForeground(new java.awt.Color(51, 51, 51));
        jLabel8.setText("Ngày kết thúc: ");

        DCNgayTao.setDateFormatString("yyyy-MM-dd");

        DCNgayKT.setDateFormatString("yyyy-MM-dd");

        jLabel5.setText("Phần trăm giảm: ");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPhanTramGiam, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtMaPhieu, javax.swing.GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE)
                            .addComponent(txtTenPhieu))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(DCNgayKT, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(DCNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(41, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(txtMaPhieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7))
                    .addComponent(DCNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(DCNgayKT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(txtTenPhieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8)))
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtPhanTramGiam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(89, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnTimKiem.setBackground(new java.awt.Color(0, 102, 255));
        btnTimKiem.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnTimKiem.setForeground(new java.awt.Color(255, 255, 255));
        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        searchDate.setBackground(new java.awt.Color(0, 102, 255));
        searchDate.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        searchDate.setForeground(new java.awt.Color(255, 255, 255));
        searchDate.setText("Tìm theo ngày");
        searchDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchDateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtHopNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchDate, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTimKiem)
                    .addComponent(txtHopNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnThem.setBackground(new java.awt.Color(0, 102, 255));
        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setText("Thêm ");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(0, 102, 255));
        btnSua.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSua.setForeground(new java.awt.Color(255, 255, 255));
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnClear.setBackground(new java.awt.Color(0, 102, 255));
        btnClear.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnClear.setForeground(new java.awt.Color(255, 255, 255));
        btnClear.setText("Làm mới");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(8, Short.MAX_VALUE)
                .addComponent(btnThem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSua)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnClear)
                .addGap(16, 16, 16))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        tblPhieuGiamGia.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã phiếu giảm giá", "Tên phiếu giảm giá", "Phần trăm giảm giá", "Ngày bắt đầu", "Ngày kết thúc"
            }
        ));
        tblPhieuGiamGia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPhieuGiamGiaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblPhieuGiamGia);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(478, 478, 478)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(349, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1011, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        try {
            String searchText = txtHopNhap.getText().trim();
            if (searchText.isEmpty()) {
                loadPromotionsToTable(); // Reload all promotions if search text is empty
                return;
            }
            
            // Get all promotions and filter by name or code
            List<Promotion> promotions = promotionDAO.findAll();
List<Promotion> filteredPromotions = promotions.stream()
                .filter(p -> p.getPromoName().toLowerCase().contains(searchText.toLowerCase()) ||
                            p.getPromoCode().toLowerCase().contains(searchText.toLowerCase()))
                .collect(java.util.stream.Collectors.toList());
            
            // Update table with search results
            DefaultTableModel model = (DefaultTableModel) tblPhieuGiamGia.getModel();
            model.setRowCount(0); // Clear existing data
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            
            for (int i = 0; i < filteredPromotions.size(); i++) {
                Promotion p = filteredPromotions.get(i);
                model.addRow(new Object[]{
                    i + 1, // STT
                    p.getPromoCode(),
                    p.getPromoName(),
                    p.getPromoDiscountValue() + "%",
                    dateFormat.format(p.getPromoStartDate()),
                    dateFormat.format(p.getPromoEndDate())
                });
            }
            
            // Show message if no results found
            if (filteredPromotions.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Không tìm thấy phiếu giảm giá phù hợp với: " + searchText, 
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi tìm kiếm phiếu giảm giá: " + e.getMessage(), 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void tblPhieuGiamGiaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPhieuGiamGiaMouseClicked
        try {
            int selectedRow = tblPhieuGiamGia.getSelectedRow();
            if (selectedRow >= 0) { // Ensure a valid row is selected
                // Get values from the selected row (skipping STT column which is index 0)
                String maPhieu = tblPhieuGiamGia.getValueAt(selectedRow, 1).toString(); // Mã phiếu giảm giá
                String tenPhieu = tblPhieuGiamGia.getValueAt(selectedRow, 2).toString(); // Tên phiếu giảm giá
                String phanTramGiam = tblPhieuGiamGia.getValueAt(selectedRow, 3).toString(); // Phần trăm giảm giá
                String ngayBatDau = tblPhieuGiamGia.getValueAt(selectedRow, 4).toString(); // Ngày bắt đầu
                String ngayKetThuc = tblPhieuGiamGia.getValueAt(selectedRow, 5).toString(); // Ngày kết thúc
                
                // Set values to input fields
                txtMaPhieu.setText(maPhieu);
                txtTenPhieu.setText(tenPhieu);
                txtPhanTramGiam.setText(phanTramGiam.replace("%", "")); // Remove % if exists
                
                // Parse and set dates
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date startDate = dateFormat.parse(ngayBatDau);
                    Date endDate = dateFormat.parse(ngayKetThuc);
                    DCNgayTao.setDate(startDate);
                    DCNgayKT.setDate(endDate);
                } catch (ParseException e) {
                    JOptionPane.showMessageDialog(this, 
                        "Lỗi định dạng ngày tháng: " + e.getMessage(), 
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
                
                // Enable/disable buttons as needed
                btnSua.setEnabled(true);
                btnThem.setEnabled(false);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi chọn phiếu giảm giá: " + e.getMessage(), 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_tblPhieuGiamGiaMouseClicked

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        try {
            // Validate required fields
            if (txtMaPhieu.getText().trim().isEmpty()
                    || txtTenPhieu.getText().trim().isEmpty()
                    || txtPhanTramGiam.getText().trim().isEmpty()
                    || DCNgayTao.getDate() == null
                    || DCNgayKT.getDate() == null) {

                JOptionPane.showMessageDialog(this,
                        "Vui lòng điền đầy đủ thông tin phiếu giảm giá!",
                        "Thông báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Validate discount value
            BigDecimal discountValue;
            try {
                discountValue = new BigDecimal(txtPhanTramGiam.getText().trim());
                if (discountValue.compareTo(BigDecimal.ZERO) <= 0 || discountValue.compareTo(new BigDecimal(100)) > 0) {
                    JOptionPane.showMessageDialog(this,
                            "Phần trăm giảm giá phải lớn hơn 0 và nhỏ hơn hoặc bằng 100!",
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                        "Phần trăm giảm giá không hợp lệ!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate date range
            Date startDate = DCNgayTao.getDate();
            Date endDate = DCNgayKT.getDate();

            if (endDate.before(startDate)) {
                JOptionPane.showMessageDialog(this,
                        "Ngày kết thúc phải sau ngày bắt đầu!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Check if promotion code already exists
            if (promotionDAO.findByCode(txtMaPhieu.getText().trim()) != null) {
                JOptionPane.showMessageDialog(this,
                        "Mã phiếu giảm giá đã tồn tại!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Create new promotion
            Promotion promotion = new Promotion();
            promotion.setPromoCode(txtMaPhieu.getText().trim());
            promotion.setPromoName(txtTenPhieu.getText().trim());
            promotion.setPromoDiscountValue(discountValue);

            // Convert Date to LocalDateTime
            LocalDateTime startDateTime = startDate.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            LocalDateTime endDateTime = endDate.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();

            promotion.setPromoStartDate(startDateTime);
            promotion.setPromoEndDate(endDateTime);

            // Save to database
            Promotion createdPromotion = promotionDAO.create(promotion);

            if (createdPromotion != null) {
                JOptionPane.showMessageDialog(this,
                        "Thêm phiếu giảm giá thành công!",
                        "Thành công",
                        JOptionPane.INFORMATION_MESSAGE);

                clearForm();
                loadPromotionsToTable(); // Refresh the table with updated data

            } else {
                JOptionPane.showMessageDialog(this,
                        "Có lỗi xảy ra khi thêm phiếu giảm giá!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Lỗi: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        try {
            // Validate input fields
            if (txtMaPhieu.getText().trim().isEmpty() ||
                txtTenPhieu.getText().trim().isEmpty() ||
                txtPhanTramGiam.getText().trim().isEmpty() ||
                DCNgayTao.getDate() == null ||
                DCNgayKT.getDate() == null) {
                JOptionPane.showMessageDialog(this, 
                    "Vui lòng điền đầy đủ thông tin phiếu giảm giá",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Validate discount value
            double discountValue;
            try {
                discountValue = Double.parseDouble(txtPhanTramGiam.getText().trim());
                if (discountValue <= 0 || discountValue > 100) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, 
                    "Phần trăm giảm giá phải là số từ 0.01 đến 100",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate date range
            if (DCNgayKT.getDate().before(DCNgayTao.getDate())) {
                JOptionPane.showMessageDialog(this, 
                    "Ngày kết thúc phải sau ngày bắt đầu",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Create updated promotion object
            Promotion updatedPromotion = new Promotion();
            updatedPromotion.setPromoCode(txtMaPhieu.getText().trim());
            updatedPromotion.setPromoName(txtTenPhieu.getText().trim());
            updatedPromotion.setPromoDiscountValue(BigDecimal.valueOf(discountValue));
            // Convert Date to LocalDateTime
            LocalDateTime startDate = DCNgayTao.getDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
            LocalDateTime endDate = DCNgayKT.getDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
                
            updatedPromotion.setPromoStartDate(startDate);
            updatedPromotion.setPromoEndDate(endDate);

            // Update in database
            Promotion updated = promotionDAO.update(updatedPromotion);
            boolean success = updated != null;
            
            if (success) {
                JOptionPane.showMessageDialog(this, 
                    "Cập nhật phiếu giảm giá thành công",
                    "Thành công", JOptionPane.INFORMATION_MESSAGE);
                
                // Refresh table and form
                loadPromotionsToTable();
                clearForm();
                
                // Reset button states
                btnThem.setEnabled(true);
                btnSua.setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Cập nhật phiếu giảm giá thất bại",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi cập nhật phiếu giảm giá: " + e.getMessage(),
                "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed

    }//GEN-LAST:event_btnClearActionPerformed

    private void searchDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchDateActionPerformed

    }//GEN-LAST:event_searchDateActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser DCNgayKT;
    private com.toedter.calendar.JDateChooser DCNgayTao;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.ButtonGroup buttonGroup5;
    private javax.swing.ButtonGroup buttonGroup6;
    private javax.swing.ButtonGroup buttonGroup7;
    private javax.swing.ButtonGroup buttonGroup8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton searchDate;
    private javax.swing.JTable tblPhieuGiamGia;
    private javax.swing.JTextField txtHopNhap;
    private javax.swing.JTextField txtMaPhieu;
    private javax.swing.JTextField txtPhanTramGiam;
    private javax.swing.JTextField txtTenPhieu;
    // End of variables declaration//GEN-END:variables
}
