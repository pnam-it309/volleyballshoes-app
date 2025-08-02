package com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewHoaDon;


public class ViewHoaDon extends javax.swing.JPanel {


    public ViewHoaDon() {

        initComponents();

    }



    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnSearch = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        txtTuNgay = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        txtDenNgay = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        btnSearchNgay = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        CBtrangThai = new javax.swing.JComboBox<>();
        btnSearchGia = new javax.swing.JButton();
        btnXuatExcel = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        CBhinhthucTT = new javax.swing.JComboBox<>();
        btnRestart = new javax.swing.JButton();
        btnLoc = new javax.swing.JButton();
        btnXuatPDF = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblHDCT = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblLSHD = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtQR = new javax.swing.JTextField();

        setBackground(new java.awt.Color(255, 255, 255));

        btnSearch.setBackground(new java.awt.Color(0, 51, 255));
        btnSearch.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSearch.setForeground(new java.awt.Color(255, 255, 255));
        btnSearch.setText("Tìm Kiếm");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel5.setLayout(null);

        txtTuNgay.setDateFormatString("yyyy-MM-dd");
        jPanel5.add(txtTuNgay);
        txtTuNgay.setBounds(200, 20, 162, 29);

        jLabel2.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Blue"));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Từ");
        jPanel5.add(jLabel2);
        jLabel2.setBounds(170, 30, 30, 20);

        txtDenNgay.setDateFormatString("yyyy-MM-dd");
        jPanel5.add(txtDenNgay);
        txtDenNgay.setBounds(420, 20, 162, 29);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Đến");
        jPanel5.add(jLabel4);
        jLabel4.setBounds(370, 30, 40, 20);

        btnSearchNgay.setBackground(new java.awt.Color(0, 51, 255));
        btnSearchNgay.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSearchNgay.setForeground(new java.awt.Color(255, 255, 255));
        btnSearchNgay.setText("Tìm theo ngày");
        btnSearchNgay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchNgayActionPerformed(evt);
            }
        });
        jPanel5.add(btnSearchNgay);
        btnSearchNgay.setBounds(10, 20, 140, 30);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Trạng thái hoá đơn");
        jPanel5.add(jLabel3);
        jLabel3.setBounds(360, 90, 120, 16);

        CBtrangThai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CBtrangThaiActionPerformed(evt);
            }
        });
        jPanel5.add(CBtrangThai);
        CBtrangThai.setBounds(480, 80, 130, 30);

        btnSearchGia.setBackground(new java.awt.Color(0, 51, 255));
        btnSearchGia.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSearchGia.setForeground(new java.awt.Color(255, 255, 255));
        btnSearchGia.setText("Tìm Kiếm");
        btnSearchGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchGiaActionPerformed(evt);
            }
        });
        jPanel5.add(btnSearchGia);
        btnSearchGia.setBounds(10, 70, 140, 30);

        btnXuatExcel.setBackground(new java.awt.Color(0, 51, 255));
        btnXuatExcel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXuatExcel.setForeground(new java.awt.Color(255, 255, 255));
        btnXuatExcel.setText("Xuất Excel");
        btnXuatExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatExcelActionPerformed(evt);
            }
        });
        jPanel5.add(btnXuatExcel);
        btnXuatExcel.setBounds(600, 20, 100, 30);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Hình thức TT");
        jPanel5.add(jLabel5);
        jLabel5.setBounds(620, 90, 80, 10);

        CBhinhthucTT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CBhinhthucTTActionPerformed(evt);
            }
        });
        jPanel5.add(CBhinhthucTT);
        CBhinhthucTT.setBounds(700, 80, 130, 30);

        btnRestart.setBackground(new java.awt.Color(0, 51, 255));
        btnRestart.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnRestart.setForeground(new java.awt.Color(255, 255, 255));
        btnRestart.setText("Làm mới");
        btnRestart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRestartActionPerformed(evt);
            }
        });
        jPanel5.add(btnRestart);
        btnRestart.setBounds(900, 20, 80, 30);

        btnLoc.setBackground(new java.awt.Color(0, 51, 255));
        btnLoc.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnLoc.setForeground(new java.awt.Color(255, 255, 255));
        btnLoc.setText("Lọc");
        btnLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLocActionPerformed(evt);
            }
        });
        jPanel5.add(btnLoc);
        btnLoc.setBounds(810, 20, 80, 30);

        btnXuatPDF.setBackground(new java.awt.Color(51, 51, 255));
        btnXuatPDF.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXuatPDF.setForeground(new java.awt.Color(255, 255, 255));
        btnXuatPDF.setText("Xuất PDF");
        btnXuatPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatPDFActionPerformed(evt);
            }
        });
        jPanel5.add(btnXuatPDF);
        btnXuatPDF.setBounds(710, 20, 90, 30);

        tbl.setModel(new javax.swing.table.DefaultTableModel(
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
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl);

        tblHDCT.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tblHDCT.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tblHDCT);

        tblLSHD.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tblLSHD.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblLSHD);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Hoá đơn chi tiết");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Lịch sử hoá đơn");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setForeground(new java.awt.Color(0, 170, 158));

        jLabel7.setFont(new java.awt.Font("Source Sans Pro SemiBold", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 51, 255));
        jLabel7.setText(" QUẢN LÍ HOÁ ĐƠN");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel7)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txtQR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQRActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(297, 297, 297)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane1)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtQR, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 747, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane3)
                                .addGap(18, 18, 18)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtQR, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(65, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
  

        //TODO add your handling code here:
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnSearchNgayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchNgayActionPerformed

        // TODO add your handling code here:
    }//GEN-LAST:event_btnSearchNgayActionPerformed

    private void btnSearchGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchGiaActionPerformed

        // TODO add your handling code here:
    }//GEN-LAST:event_btnSearchGiaActionPerformed

    private void CBhinhthucTTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CBhinhthucTTActionPerformed

        // TODO add your handling code here:
    }//GEN-LAST:event_CBhinhthucTTActionPerformed

    private void btnRestartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRestartActionPerformed


        // TODO add your handling code here:
    }//GEN-LAST:event_btnRestartActionPerformed

    private void tblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMouseClicked

    }//GEN-LAST:event_tblMouseClicked

    private void btnXuatExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatExcelActionPerformed
      

// TODO add your handling code here:
    }//GEN-LAST:event_btnXuatExcelActionPerformed

    private void btnLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocActionPerformed

        // TODO add your handling code here:
    }//GEN-LAST:event_btnLocActionPerformed

    private void txtQRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQRActionPerformed

        // TODO add your handling code here:
    }//GEN-LAST:event_txtQRActionPerformed

    private void CBtrangThaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CBtrangThaiActionPerformed

    }//GEN-LAST:event_CBtrangThaiActionPerformed

    private void btnXuatPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatPDFActionPerformed
 
        // TODO add your handling code here:
    }//GEN-LAST:event_btnXuatPDFActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> CBhinhthucTT;
    private javax.swing.JComboBox<String> CBtrangThai;
    private javax.swing.JButton btnLoc;
    private javax.swing.JButton btnRestart;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnSearchGia;
    private javax.swing.JButton btnSearchNgay;
    private javax.swing.JButton btnXuatExcel;
    private javax.swing.JButton btnXuatPDF;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tbl;
    private javax.swing.JTable tblHDCT;
    private javax.swing.JTable tblLSHD;
    private com.toedter.calendar.JDateChooser txtDenNgay;
    private javax.swing.JTextField txtQR;
    private com.toedter.calendar.JDateChooser txtTuNgay;
    // End of variables declaration//GEN-END:variables
}
