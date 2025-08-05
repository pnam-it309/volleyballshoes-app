package com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewSanPham;

import java.util.List;
import javax.swing.table.DefaultTableModel;

public class ViewThuocTinh extends javax.swing.JPanel {

    public ViewThuocTinh() {
        initComponents();

    }

    private void loadTable() {
        try {
            if (rb_color.isSelected()) {
                // Load danh sách danh mục
                com.DuAn1.volleyballshoes.app.dao.impl.CategoryDAOImpl dao
                        = new com.DuAn1.volleyballshoes.app.dao.impl.CategoryDAOImpl();
                List<com.DuAn1.volleyballshoes.app.entity.Category> list = dao.findAll();
                loadTableData(list, "category");
            } else if (rb_size.isSelected()) {
                // Load danh sách thương hiệu
                com.DuAn1.volleyballshoes.app.dao.impl.BrandDAOImpl dao
                        = new com.DuAn1.volleyballshoes.app.dao.impl.BrandDAOImpl();
                List<com.DuAn1.volleyballshoes.app.entity.Brand> list = dao.findAll();
                loadTableData(list, "brand");
            } else if (rb_brand.isSelected()) {
                // Load danh sách loại đế
                com.DuAn1.volleyballshoes.app.dao.impl.SoleTypeDAOImpl dao
                        = new com.DuAn1.volleyballshoes.app.dao.impl.SoleTypeDAOImpl();
                List<com.DuAn1.volleyballshoes.app.entity.SoleType> list = dao.findAll();
                loadTableData(list, "soleType");
            } else if (rb_category.isSelected()) {
                // Load danh sách kích thước
                com.DuAn1.volleyballshoes.app.dao.impl.SizeDAOImpl dao
                        = new com.DuAn1.volleyballshoes.app.dao.impl.SizeDAOImpl();
                List<com.DuAn1.volleyballshoes.app.entity.Size> list = dao.findAll();
                loadTableData(list, "size");
            } else if (rb_sole.isSelected()) {
                // Load danh sách khuyến mãi
                com.DuAn1.volleyballshoes.app.dao.impl.PromotionDAOImpl dao
                        = new com.DuAn1.volleyballshoes.app.dao.impl.PromotionDAOImpl();
                List<com.DuAn1.volleyballshoes.app.entity.Promotion> list = dao.findAll();
                loadTableData(list, "promotion");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Lỗi tải dữ liệu: " + ex.getMessage(),
                    "Lỗi",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void loadTableData(List<?> dataList, String type) {
        DefaultTableModel model = (DefaultTableModel) tblThuocTinh.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ

        for (Object item : dataList) {
            Object[] row = new Object[3];
            row[0] = model.getRowCount() + 1; // STT

            switch (type) {
                case "brand":
                    com.DuAn1.volleyballshoes.app.entity.Brand brand = (com.DuAn1.volleyballshoes.app.entity.Brand) item;
                    row[1] = brand.getBrandCode();
                    row[2] = brand.getBrandName();
                    break;
                case "promotion":
                    com.DuAn1.volleyballshoes.app.entity.Promotion promo = (com.DuAn1.volleyballshoes.app.entity.Promotion) item;
                    row[1] = promo.getPromoCode();
                    row[2] = promo.getPromoName();
                    break;
                case "category":
                    com.DuAn1.volleyballshoes.app.entity.Category cat = (com.DuAn1.volleyballshoes.app.entity.Category) item;
                    row[1] = cat.getCategoryCode();
                    row[2] = cat.getCategoryName();
                    break;
                case "size":
                    com.DuAn1.volleyballshoes.app.entity.Size size = (com.DuAn1.volleyballshoes.app.entity.Size) item;
                    row[1] = size.getSizeCode();
                    row[2] = size.getSizeValue();
                    break;
                case "soleType":
                    com.DuAn1.volleyballshoes.app.entity.SoleType soleType = (com.DuAn1.volleyballshoes.app.entity.SoleType) item;
                    row[1] = soleType.getSoleCode();
                    row[2] = soleType.getSoleName();
                    break;
            }

            model.addRow(row);
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
            java.util.logging.Logger.getLogger(ViewThuocTinh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewThuocTinh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewThuocTinh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewThuocTinh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewThuocTinh().setVisible(true);
            }
        });
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
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
        btnThem = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnsua = new javax.swing.JButton();
        rb_color = new javax.swing.JRadioButton();
        rb_size = new javax.swing.JRadioButton();
        rb_brand = new javax.swing.JRadioButton();
        rb_category = new javax.swing.JRadioButton();
        rb_sole = new javax.swing.JRadioButton();

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

        rb_color.setText("Màu");
        rb_color.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_colorActionPerformed(evt);
            }
        });

        rb_size.setText("Size");
        rb_size.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_sizeActionPerformed(evt);
            }
        });

        rb_brand.setText("Thương hiệu");
        rb_brand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_brandActionPerformed(evt);
            }
        });

        rb_category.setText("Loại");
        rb_category.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_categoryActionPerformed(evt);
            }
        });

        rb_sole.setText("Đế giày");
        rb_sole.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_soleActionPerformed(evt);
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
                        .addGap(123, 123, 123)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rb_color)
                            .addComponent(rb_category))
                        .addGap(56, 56, 56)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(rb_size)
                                .addGap(42, 42, 42)
                                .addComponent(rb_brand))
                            .addComponent(rb_sole)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(285, 285, 285)
                        .addComponent(jLabel3)))
                .addContainerGap(162, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rb_color)
                            .addComponent(rb_size)
                            .addComponent(rb_brand))
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rb_category)
                            .addComponent(rb_sole))))
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

    private void rb_colorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_colorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rb_colorActionPerformed

    private void rb_sizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_sizeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rb_sizeActionPerformed

    private void rb_brandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_brandActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rb_brandActionPerformed

    private void rb_categoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_categoryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rb_categoryActionPerformed

    private void rb_soleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_soleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rb_soleActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnThemActionPerformed
        String ma = txtMa.getText().trim();
        String ten = txtTen.getText().trim();
        if (ma.isEmpty() || ten.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }
        try {
            if (rb_color.isSelected()) {
                com.DuAn1.volleyballshoes.app.entity.Category entity = new com.DuAn1.volleyballshoes.app.entity.Category();
                entity.setCategoryCode(ma);
                entity.setCategoryName(ten);
                new com.DuAn1.volleyballshoes.app.dao.impl.CategoryDAOImpl().create(entity);
            } else if (rb_size.isSelected()) {
                com.DuAn1.volleyballshoes.app.entity.Brand entity = new com.DuAn1.volleyballshoes.app.entity.Brand();
                entity.setBrandCode(ma);
                entity.setBrandName(ten);
                new com.DuAn1.volleyballshoes.app.dao.impl.BrandDAOImpl().create(entity);
            } else if (rb_brand.isSelected()) {
                com.DuAn1.volleyballshoes.app.entity.SoleType entity = new com.DuAn1.volleyballshoes.app.entity.SoleType();
                entity.setSoleCode(ma);
                entity.setSoleName(ten);
                new com.DuAn1.volleyballshoes.app.dao.impl.SoleTypeDAOImpl().create(entity);
            } else if (rb_category.isSelected()) {
                com.DuAn1.volleyballshoes.app.entity.Size entity = new com.DuAn1.volleyballshoes.app.entity.Size();
                entity.setSizeCode(ma);
                entity.setSizeValue(ten);
                new com.DuAn1.volleyballshoes.app.dao.impl.SizeDAOImpl().create(entity);
            } else if (rb_sole.isSelected()) {
                com.DuAn1.volleyballshoes.app.entity.Promotion entity = new com.DuAn1.volleyballshoes.app.entity.Promotion();
                entity.setPromoCode(ma);
                entity.setPromoName(ten);
                new com.DuAn1.volleyballshoes.app.dao.impl.PromotionDAOImpl().create(entity);
            }
            javax.swing.JOptionPane.showMessageDialog(this, "Thêm thành công!");
            loadTable();
        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(this, "Lỗi thêm dữ liệu: " + ex.getMessage());
        }
    }// GEN-LAST:event_btnThemActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnXoaActionPerformed
        int row = tblThuocTinh.getSelectedRow();
        if (row == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần xóa!");
            return;
        }
        String ma = tblThuocTinh.getValueAt(row, 1).toString();
        try {
            if (rb_color.isSelected()) {
                com.DuAn1.volleyballshoes.app.dao.impl.CategoryDAOImpl dao = new com.DuAn1.volleyballshoes.app.dao.impl.CategoryDAOImpl();
                com.DuAn1.volleyballshoes.app.entity.Category cat = dao.findByName(ma);
                dao.deleteById(cat.getCategoryId());
            } else if (rb_size.isSelected()) {
                com.DuAn1.volleyballshoes.app.dao.impl.BrandDAOImpl dao = new com.DuAn1.volleyballshoes.app.dao.impl.BrandDAOImpl();
                com.DuAn1.volleyballshoes.app.entity.Brand b = dao.findByCode(ma);
                dao.deleteById(b.getBrandId());
            } else if (rb_brand.isSelected()) {
                com.DuAn1.volleyballshoes.app.dao.impl.SoleTypeDAOImpl dao = new com.DuAn1.volleyballshoes.app.dao.impl.SoleTypeDAOImpl();
                com.DuAn1.volleyballshoes.app.entity.SoleType st = dao.findByCode(ma);
                dao.deleteById(st.getSoleId());
            } else if (rb_category.isSelected()) {
                com.DuAn1.volleyballshoes.app.dao.impl.SizeDAOImpl dao = new com.DuAn1.volleyballshoes.app.dao.impl.SizeDAOImpl();
                com.DuAn1.volleyballshoes.app.entity.Size sz = dao.findByCode(ma);
                dao.deleteById(sz.getSizeId());
            } else if (rb_sole.isSelected()) {
                com.DuAn1.volleyballshoes.app.dao.impl.PromotionDAOImpl dao = new com.DuAn1.volleyballshoes.app.dao.impl.PromotionDAOImpl();
                com.DuAn1.volleyballshoes.app.entity.Promotion pr = dao.findByCode(ma);
                dao.deleteById(pr.getPromotionId());
            }
            javax.swing.JOptionPane.showMessageDialog(this, "Xóa thành công!");
            loadTable();
        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(this, "Lỗi xóa dữ liệu: " + ex.getMessage());
        }
    }// GEN-LAST:event_btnXoaActionPerformed

    private void rbTacGiaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_rbTacGiaActionPerformed
        try {
            com.DuAn1.volleyballshoes.app.dao.impl.BrandDAOImpl dao = new com.DuAn1.volleyballshoes.app.dao.impl.BrandDAOImpl();
            List<com.DuAn1.volleyballshoes.app.entity.Brand> list = dao.findAll();
            loadTableData(list, "brand");
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu thương hiệu: " + e.getMessage());
        }
    }// GEN-LAST:event_rbTacGiaActionPerformed

    private void rbNXBActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_rbNXBActionPerformed
        try {
            com.DuAn1.volleyballshoes.app.dao.impl.PromotionDAOImpl dao = new com.DuAn1.volleyballshoes.app.dao.impl.PromotionDAOImpl();
            List<com.DuAn1.volleyballshoes.app.entity.Promotion> list = dao.findAll();
            loadTableData(list, "promotion");
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu khuyến mãi: " + e.getMessage());
        }
    }// GEN-LAST:event_rbNXBActionPerformed

    private void rbTheLoaiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_rbTheLoaiActionPerformed
        try {
            com.DuAn1.volleyballshoes.app.dao.impl.CategoryDAOImpl dao = new com.DuAn1.volleyballshoes.app.dao.impl.CategoryDAOImpl();
            List<com.DuAn1.volleyballshoes.app.entity.Category> list = dao.findAll();
            loadTableData(list, "category");
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu danh mục: " + e.getMessage());
        }
    }// GEN-LAST:event_rbTheLoaiActionPerformed

    private void rbLoaiGiayActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_rbLoaiGiayActionPerformed
        try {
            com.DuAn1.volleyballshoes.app.dao.impl.SizeDAOImpl dao = new com.DuAn1.volleyballshoes.app.dao.impl.SizeDAOImpl();
            List<com.DuAn1.volleyballshoes.app.entity.Size> list = dao.findAll();
            loadTableData(list, "size");
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu kích thước: " + e.getMessage());
        }
    }// GEN-LAST:event_rbLoaiGiayActionPerformed

    private void rbLoaiBiaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_rbLoaiBiaActionPerformed
        try {
            com.DuAn1.volleyballshoes.app.dao.impl.SoleTypeDAOImpl dao = new com.DuAn1.volleyballshoes.app.dao.impl.SoleTypeDAOImpl();
            List<com.DuAn1.volleyballshoes.app.entity.SoleType> list = dao.findAll();
            loadTableData(list, "soleType");
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu loại đế: " + e.getMessage());
        }
    }// GEN-LAST:event_rbLoaiBiaActionPerformed

    private void tblThuocTinhMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_tblThuocTinhMouseClicked
        int row = tblThuocTinh.getSelectedRow();
        if (row != -1) {
            txtMa.setText(tblThuocTinh.getValueAt(row, 1).toString());
            txtTen.setText(tblThuocTinh.getValueAt(row, 2).toString());
        }
    }// GEN-LAST:event_tblThuocTinhMouseClicked

    private void btnsuaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnsuaActionPerformed
        int row = tblThuocTinh.getSelectedRow();
        if (row == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần sửa!");
            return;
        }
        String ma = txtMa.getText().trim();
        String ten = txtTen.getText().trim();
        try {
            if (rb_color.isSelected()) {
                com.DuAn1.volleyballshoes.app.dao.impl.CategoryDAOImpl dao = new com.DuAn1.volleyballshoes.app.dao.impl.CategoryDAOImpl();
                com.DuAn1.volleyballshoes.app.entity.Category cat = dao.findByName(ma);
                cat.setCategoryName(ten);
                dao.update(cat);
            } else if (rb_size.isSelected()) {
                com.DuAn1.volleyballshoes.app.dao.impl.BrandDAOImpl dao = new com.DuAn1.volleyballshoes.app.dao.impl.BrandDAOImpl();
                com.DuAn1.volleyballshoes.app.entity.Brand b = dao.findByCode(ma);
                b.setBrandName(ten);
                dao.update(b);
            } else if (rb_brand.isSelected()) {
                com.DuAn1.volleyballshoes.app.dao.impl.SoleTypeDAOImpl dao = new com.DuAn1.volleyballshoes.app.dao.impl.SoleTypeDAOImpl();
                com.DuAn1.volleyballshoes.app.entity.SoleType st = dao.findByCode(ma);
                st.setSoleName(ten);
                dao.update(st);
            } else if (rb_category.isSelected()) {
                com.DuAn1.volleyballshoes.app.dao.impl.SizeDAOImpl dao = new com.DuAn1.volleyballshoes.app.dao.impl.SizeDAOImpl();
                com.DuAn1.volleyballshoes.app.entity.Size sz = dao.findByCode(ma);
                sz.setSizeValue(ten);
                dao.update(sz);
            } else if (rb_sole.isSelected()) {
                com.DuAn1.volleyballshoes.app.dao.impl.PromotionDAOImpl dao = new com.DuAn1.volleyballshoes.app.dao.impl.PromotionDAOImpl();
                com.DuAn1.volleyballshoes.app.entity.Promotion pr = dao.findByCode(ma);
                pr.setPromoName(ten);
                dao.update(pr);
            }
            javax.swing.JOptionPane.showMessageDialog(this, "Sửa thành công!");
            loadTable();
        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(this, "Lỗi sửa dữ liệu: " + ex.getMessage());
        }
    }// GEN-LAST:event_btnsuaActionPerformed

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
    private javax.swing.JRadioButton rb_brand;
    private javax.swing.JRadioButton rb_category;
    private javax.swing.JRadioButton rb_color;
    private javax.swing.JRadioButton rb_size;
    private javax.swing.JRadioButton rb_sole;
    private javax.swing.JTable tblThuocTinh;
    private javax.swing.JTextField txtMa;
    private javax.swing.JTextField txtTen;
    // End of variables declaration//GEN-END:variables
}
