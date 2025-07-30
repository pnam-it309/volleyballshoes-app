package com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewSanPham;



public class ViewSanPham extends javax.swing.JPanel {

 
   

    public ViewSanPham() {
        initComponents();

    }

   

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtMaSP = new javax.swing.JTextField();
        txtTenSP = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtaMoTa = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();
        nhoNhat = new javax.swing.JButton();
        nho = new javax.swing.JButton();
        phanTrang = new javax.swing.JLabel();
        lon = new javax.swing.JButton();
        lonNhat = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        rbTatCa = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();

        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã Sản Phẩm", "Tên Sản Phẩm", "Mô Tả", "Trạng Thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblSanPham);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 255));
        jLabel1.setText("Thông Tin Sản Phẩm");

        jLabel2.setText("Mã Sản Phẩm");

        jLabel3.setText("Tên Sản Phẩm");

        jLabel4.setText("Mô Tả");

        txtaMoTa.setColumns(20);
        txtaMoTa.setRows(5);
        jScrollPane1.setViewportView(txtaMoTa);

        btnThem.setBackground(new java.awt.Color(0, 102, 255));
        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(0, 102, 255));
        btnSua.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSua.setForeground(new java.awt.Color(255, 255, 255));
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(0, 102, 255));
        btnXoa.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnLamMoi.setBackground(new java.awt.Color(0, 102, 255));
        btnLamMoi.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnLamMoi.setForeground(new java.awt.Color(255, 255, 255));
        btnLamMoi.setText("Làm mới");
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnThem)
                    .addComponent(btnXoa))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnLamMoi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(21, 21, 21))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem)
                    .addComponent(btnSua))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnXoa)
                    .addComponent(btnLamMoi))
                .addGap(41, 41, 41))
        );

        nhoNhat.setText("<<");
        nhoNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nhoNhatActionPerformed(evt);
            }
        });

        nho.setText("<");
        nho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nhoActionPerformed(evt);
            }
        });

        phanTrang.setText("jLabel5");

        lon.setText(">");
        lon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lonActionPerformed(evt);
            }
        });

        lonNhat.setText(">>");
        lonNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lonNhatActionPerformed(evt);
            }
        });

        jLabel5.setText("Trạng Thái");

        buttonGroup1.add(rbTatCa);
        rbTatCa.setText("tất cả");
        rbTatCa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbTatCaActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("còn kinh doanh");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setText("ngừng kinh doang");
        jRadioButton3.setToolTipText("");
        jRadioButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel2)
                        .addComponent(txtTenSP, javax.swing.GroupLayout.DEFAULT_SIZE, 393, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addComponent(txtMaSP))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(rbTatCa)
                                .addGap(18, 18, 18)
                                .addComponent(jRadioButton2)
                                .addGap(18, 18, 18)
                                .addComponent(jRadioButton3))
                            .addComponent(jLabel5))
                        .addGap(96, 96, 96)
                        .addComponent(jLabel1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 270, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(131, 131, 131))
            .addGroup(layout.createSequentialGroup()
                .addGap(331, 331, 331)
                .addComponent(nhoNhat)
                .addGap(18, 18, 18)
                .addComponent(nho)
                .addGap(18, 18, 18)
                .addComponent(phanTrang)
                .addGap(18, 18, 18)
                .addComponent(lon)
                .addGap(18, 18, 18)
                .addComponent(lonNhat)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel1)
                .addGap(70, 70, 70)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rbTatCa)
                            .addComponent(jRadioButton2)
                            .addComponent(jRadioButton3))
                        .addGap(27, 27, 27))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nhoNhat)
                    .addComponent(nho)
                    .addComponent(phanTrang)
                    .addComponent(lon)
                    .addComponent(lonNhat))
                .addGap(85, 85, 85))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
      
        
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
    
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
 
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
    
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void nhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nhoActionPerformed
      
    }//GEN-LAST:event_nhoActionPerformed

    private void nhoNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nhoNhatActionPerformed
     
    }//GEN-LAST:event_nhoNhatActionPerformed

    private void lonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lonActionPerformed
     
    }//GEN-LAST:event_lonActionPerformed

    private void lonNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lonNhatActionPerformed
       
    }//GEN-LAST:event_lonNhatActionPerformed

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
    
    }//GEN-LAST:event_tblSanPhamMouseClicked

    private void jRadioButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton3ActionPerformed
      
    }//GEN-LAST:event_jRadioButton3ActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed

    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void rbTatCaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbTatCaActionPerformed
     
    }//GEN-LAST:event_rbTatCaActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton lon;
    private javax.swing.JButton lonNhat;
    private javax.swing.JButton nho;
    private javax.swing.JButton nhoNhat;
    private javax.swing.JLabel phanTrang;
    private javax.swing.JRadioButton rbTatCa;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTextField txtMaSP;
    private javax.swing.JTextField txtTenSP;
    private javax.swing.JTextArea txtaMoTa;
    // End of variables declaration//GEN-END:variables
}
