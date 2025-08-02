package com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewSanPham;


public class ViewThuocTinh extends javax.swing.JPanel {

  

    public ViewThuocTinh() {
        initComponents();
  
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblThuocTinh = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtMa = new javax.swing.JTextField();
        txtTen = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        rbTacGia = new javax.swing.JRadioButton();
        rbNXB = new javax.swing.JRadioButton();
        rbLoaiGiay = new javax.swing.JRadioButton();
        rbLoaiBia = new javax.swing.JRadioButton();
        rbTheLoai = new javax.swing.JRadioButton();
        btnThem = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnsua = new javax.swing.JButton();

        tblThuocTinh.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "STT", "Mã Thuộc Tính", "Tên Thuộc Tính"
            }
        ));
        tblThuocTinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblThuocTinhMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblThuocTinh);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        jLabel1.setText("Mã Thuộc Tính");

        jLabel2.setText("Tên Thuộc Tính");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtMa)
                        .addComponent(txtTen, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(8, 8, 8)
                            .addComponent(jLabel1))))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtMa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 255));
        jLabel3.setText("Thông Tin Thuộc Tính");

        buttonGroup1.add(rbTacGia);
        rbTacGia.setText("Size");
        rbTacGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbTacGiaActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbNXB);
        rbNXB.setText("Đế giày");
        rbNXB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbNXBActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbLoaiGiay);
        rbLoaiGiay.setText("Loại");
        rbLoaiGiay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbLoaiGiayActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbLoaiBia);
        rbLoaiBia.setText("Thương hiệu");
        rbLoaiBia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbLoaiBiaActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbTheLoai);
        rbTheLoai.setText("Màu sắc");
        rbTheLoai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbTheLoaiActionPerformed(evt);
            }
        });

        btnThem.setBackground(new java.awt.Color(0, 51, 255));
        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(0, 51, 255));
        btnXoa.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnsua.setBackground(new java.awt.Color(0, 51, 255));
        btnsua.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnsua.setForeground(new java.awt.Color(255, 255, 255));
        btnsua.setText("Sửa");
        btnsua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsuaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 842, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(139, 139, 139)
                .addComponent(btnThem)
                .addGap(136, 136, 136)
                .addComponent(btnsua)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnXoa)
                .addGap(163, 163, 163))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(58, 58, 58)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rbTacGia)
                            .addComponent(rbLoaiGiay))
                        .addGap(61, 61, 61)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rbNXB)
                            .addComponent(rbLoaiBia))
                        .addGap(76, 76, 76)
                        .addComponent(rbTheLoai))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(285, 285, 285)
                        .addComponent(jLabel3)))
                .addContainerGap(168, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel3)
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rbTacGia)
                            .addComponent(rbNXB)
                            .addComponent(rbTheLoai))
                        .addGap(65, 65, 65)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rbLoaiGiay)
                            .addComponent(rbLoaiBia))))
                .addGap(88, 88, 88)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem)
                    .addComponent(btnXoa)
                    .addComponent(btnsua))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
        );
    }// </editor-fold>//GEN-END:initComponents

   

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
    String ma = txtMa.getText().trim();
    String ten = txtTen.getText().trim();
    if(ma.isEmpty() || ten.isEmpty()) {
        javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
        return;
    }
    try {
        if (rbTheLoai.isSelected()) {
            com.DuAn1.volleyballshoes.app.entity.Category entity = new com.DuAn1.volleyballshoes.app.entity.Category();
            entity.setCategoryCode(ma);
            entity.setCategoryName(ten);
            new com.DuAn1.volleyballshoes.app.dao.impl.CategoryDAOImpl().create(entity);
        } else if (rbTacGia.isSelected()) {
            com.DuAn1.volleyballshoes.app.entity.Brand entity = new com.DuAn1.volleyballshoes.app.entity.Brand();
            entity.setBrandCode(ma);
            entity.setBrandName(ten);
            new com.DuAn1.volleyballshoes.app.dao.impl.BrandDAOImpl().create(entity);
        } else if (rbLoaiBia.isSelected()) {
            com.DuAn1.volleyballshoes.app.entity.SoleType entity = new com.DuAn1.volleyballshoes.app.entity.SoleType();
            entity.setSoleCode(ma);
            entity.setSoleName(ten);
            new com.DuAn1.volleyballshoes.app.dao.impl.SoleTypeDAOImpl().create(entity);
        } else if (rbLoaiGiay.isSelected()) {
            com.DuAn1.volleyballshoes.app.entity.Size entity = new com.DuAn1.volleyballshoes.app.entity.Size();
            entity.setSizeCode(ma);
            entity.setSizeValue(ten);
            new com.DuAn1.volleyballshoes.app.dao.impl.SizeDAOImpl().create(entity);
        } else if (rbNXB.isSelected()) {
            com.DuAn1.volleyballshoes.app.entity.Promotion entity = new com.DuAn1.volleyballshoes.app.entity.Promotion();
            entity.setPromoCode(ma);
            entity.setPromoName(ten);
            new com.DuAn1.volleyballshoes.app.dao.impl.PromotionDAOImpl().create(entity);
        }
        javax.swing.JOptionPane.showMessageDialog(this, "Thêm thành công!");
        loadTable();
    } catch(Exception ex) {
        javax.swing.JOptionPane.showMessageDialog(this, "Lỗi thêm dữ liệu: " + ex.getMessage());
    }
}//GEN-LAST:event_btnThemActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
    int row = tblThuocTinh.getSelectedRow();
    if(row == -1) {
        javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần xóa!");
        return;
    }
    String ma = tblThuocTinh.getValueAt(row, 1).toString();
    try {
        if (rbTheLoai.isSelected()) {
            com.DuAn1.volleyballshoes.app.dao.impl.CategoryDAOImpl dao = new com.DuAn1.volleyballshoes.app.dao.impl.CategoryDAOImpl();
            com.DuAn1.volleyballshoes.app.entity.Category cat = dao.findByName(ma);
            dao.deleteById(cat.getCategoryId());
        } else if (rbTacGia.isSelected()) {
            com.DuAn1.volleyballshoes.app.dao.impl.BrandDAOImpl dao = new com.DuAn1.volleyballshoes.app.dao.impl.BrandDAOImpl();
            com.DuAn1.volleyballshoes.app.entity.Brand b = dao.findByCode(ma);
            dao.deleteById(b.getBrandId());
        } else if (rbLoaiBia.isSelected()) {
            com.DuAn1.volleyballshoes.app.dao.impl.SoleTypeDAOImpl dao = new com.DuAn1.volleyballshoes.app.dao.impl.SoleTypeDAOImpl();
            com.DuAn1.volleyballshoes.app.entity.SoleType st = dao.findByCode(ma);
            dao.deleteById(st.getSoleId());
        } else if (rbLoaiGiay.isSelected()) {
            com.DuAn1.volleyballshoes.app.dao.impl.SizeDAOImpl dao = new com.DuAn1.volleyballshoes.app.dao.impl.SizeDAOImpl();
            com.DuAn1.volleyballshoes.app.entity.Size sz = dao.findByCode(ma);
            dao.deleteById(sz.getSizeId());
        } else if (rbNXB.isSelected()) {
            com.DuAn1.volleyballshoes.app.dao.impl.PromotionDAOImpl dao = new com.DuAn1.volleyballshoes.app.dao.impl.PromotionDAOImpl();
            com.DuAn1.volleyballshoes.app.entity.Promotion pr = dao.findByCode(ma);
            dao.deleteById(pr.getPromotionId());
        }
        javax.swing.JOptionPane.showMessageDialog(this, "Xóa thành công!");
        loadTable();
    } catch(Exception ex) {
        javax.swing.JOptionPane.showMessageDialog(this, "Lỗi xóa dữ liệu: " + ex.getMessage());
    }
}//GEN-LAST:event_btnXoaActionPerformed

    private void rbTacGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbTacGiaActionPerformed
    loadTable();
}//GEN-LAST:event_rbTacGiaActionPerformed

    private void rbNXBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbNXBActionPerformed
    loadTable();
}//GEN-LAST:event_rbNXBActionPerformed

    private void rbTheLoaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbTheLoaiActionPerformed
    loadTable();
}//GEN-LAST:event_rbTheLoaiActionPerformed

    private void rbLoaiGiayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbLoaiGiayActionPerformed
    loadTable();
}//GEN-LAST:event_rbLoaiGiayActionPerformed

    private void rbLoaiBiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbLoaiBiaActionPerformed
    loadTable();
}//GEN-LAST:event_rbLoaiBiaActionPerformed

    private void tblThuocTinhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblThuocTinhMouseClicked
    int row = tblThuocTinh.getSelectedRow();
    if(row != -1) {
        txtMa.setText(tblThuocTinh.getValueAt(row, 1).toString());
        txtTen.setText(tblThuocTinh.getValueAt(row, 2).toString());
    }
}//GEN-LAST:event_tblThuocTinhMouseClicked

    private void btnsuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsuaActionPerformed
    int row = tblThuocTinh.getSelectedRow();
    if(row == -1) {
        javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần sửa!");
        return;
    }
    String ma = txtMa.getText().trim();
    String ten = txtTen.getText().trim();
    try {
        if (rbTheLoai.isSelected()) {
            com.DuAn1.volleyballshoes.app.dao.impl.CategoryDAOImpl dao = new com.DuAn1.volleyballshoes.app.dao.impl.CategoryDAOImpl();
            com.DuAn1.volleyballshoes.app.entity.Category cat = dao.findByName(ma);
            cat.setCategoryName(ten);
            dao.update(cat);
        } else if (rbTacGia.isSelected()) {
            com.DuAn1.volleyballshoes.app.dao.impl.BrandDAOImpl dao = new com.DuAn1.volleyballshoes.app.dao.impl.BrandDAOImpl();
            com.DuAn1.volleyballshoes.app.entity.Brand b = dao.findByCode(ma);
            b.setBrandName(ten);
            dao.update(b);
        } else if (rbLoaiBia.isSelected()) {
            com.DuAn1.volleyballshoes.app.dao.impl.SoleTypeDAOImpl dao = new com.DuAn1.volleyballshoes.app.dao.impl.SoleTypeDAOImpl();
            com.DuAn1.volleyballshoes.app.entity.SoleType st = dao.findByCode(ma);
            st.setSoleName(ten);
            dao.update(st);
        } else if (rbLoaiGiay.isSelected()) {
            com.DuAn1.volleyballshoes.app.dao.impl.SizeDAOImpl dao = new com.DuAn1.volleyballshoes.app.dao.impl.SizeDAOImpl();
            com.DuAn1.volleyballshoes.app.entity.Size sz = dao.findByCode(ma);
            sz.setSizeValue(ten);
            dao.update(sz);
        } else if (rbNXB.isSelected()) {
            com.DuAn1.volleyballshoes.app.dao.impl.PromotionDAOImpl dao = new com.DuAn1.volleyballshoes.app.dao.impl.PromotionDAOImpl();
            com.DuAn1.volleyballshoes.app.entity.Promotion pr = dao.findByCode(ma);
            pr.setPromoName(ten);
            dao.update(pr);
        }
        javax.swing.JOptionPane.showMessageDialog(this, "Sửa thành công!");
        loadTable();
    } catch(Exception ex) {
        javax.swing.JOptionPane.showMessageDialog(this, "Lỗi sửa dữ liệu: " + ex.getMessage());
    }
}//GEN-LAST:event_btnsuaActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnsua;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rbLoaiBia;
    private javax.swing.JRadioButton rbLoaiGiay;
    private javax.swing.JRadioButton rbNXB;
    private javax.swing.JRadioButton rbTacGia;
    private javax.swing.JRadioButton rbTheLoai;
    private javax.swing.JTable tblThuocTinh;
    private javax.swing.JTextField txtMa;
    private javax.swing.JTextField txtTen;
    // End of variables declaration//GEN-END:variables
}
