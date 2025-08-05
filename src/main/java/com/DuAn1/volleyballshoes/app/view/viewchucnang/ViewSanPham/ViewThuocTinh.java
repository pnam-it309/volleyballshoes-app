package com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewSanPham;

import com.DuAn1.volleyballshoes.app.dao.*;
import com.DuAn1.volleyballshoes.app.dao.impl.*;
import com.DuAn1.volleyballshoes.app.entity.*;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ViewThuocTinh extends javax.swing.JPanel {

    ColorDAO colorDAO = new ColorDAOImpl();
    SoleTypeDAO soleTypeDAO = new SoleTypeDAOImpl();
    SizeDAO sizeDAO = new SizeDAOImpl();
    BrandDAO brandDAO = new BrandDAOImpl();
    CategoryDAO categoryDAO = new CategoryDAOImpl();
    private String type = "color";


    public ViewThuocTinh() {
        initComponents();

    }

    private void loadTable() {
        try {
            if (rb_category.isSelected()) {
                // Load danh sách danh mục
                com.DuAn1.volleyballshoes.app.dao.impl.CategoryDAOImpl dao
                        = new com.DuAn1.volleyballshoes.app.dao.impl.CategoryDAOImpl();
                List<com.DuAn1.volleyballshoes.app.entity.Category> list = dao.findAll();
                loadTableData(list, "category");
            } else if (rb_brand.isSelected()) {
                // Load danh sách thương hiệu
                com.DuAn1.volleyballshoes.app.dao.impl.BrandDAOImpl dao
                        = new com.DuAn1.volleyballshoes.app.dao.impl.BrandDAOImpl();
                List<com.DuAn1.volleyballshoes.app.entity.Brand> list = dao.findAll();
                loadTableData(list, "brand");
            } else if (rb_sole.isSelected()) {
                // Load danh sách loại đế
                com.DuAn1.volleyballshoes.app.dao.impl.SoleTypeDAOImpl dao
                        = new com.DuAn1.volleyballshoes.app.dao.impl.SoleTypeDAOImpl();
                List<com.DuAn1.volleyballshoes.app.entity.SoleType> list = dao.findAll();
                loadTableData(list, "soleType");
            } else if (rb_size.isSelected()) {
                // Load danh sách kích thước
                com.DuAn1.volleyballshoes.app.dao.impl.SizeDAOImpl dao
                        = new com.DuAn1.volleyballshoes.app.dao.impl.SizeDAOImpl();
                List<com.DuAn1.volleyballshoes.app.entity.Size> list = dao.findAll();
                loadTableData(list, "size");
            } else if (rb_color.isSelected()) {
                // Load danh sách màu sắc
                com.DuAn1.volleyballshoes.app.dao.impl.ColorDAOImpl dao
                        = new com.DuAn1.volleyballshoes.app.dao.impl.ColorDAOImpl();
                List<Color> list = dao.findAll();
                loadTableData(list, "color");
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
                case "color":
                    com.DuAn1.volleyballshoes.app.entity.Color color = (com.DuAn1.volleyballshoes.app.entity.Color) item;
                    row[1] = color.getColorHexCode();
                    row[2] = color.getColorName();
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
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtMa = new javax.swing.JTextField();
        txtTen = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        rb_color = new javax.swing.JRadioButton();
        rb_size = new javax.swing.JRadioButton();
        rb_brand = new javax.swing.JRadioButton();
        rb_category = new javax.swing.JRadioButton();
        rb_sole = new javax.swing.JRadioButton();
        btnThem1 = new javax.swing.JButton();
        btnsua1 = new javax.swing.JButton();
        btnXoa1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblThuocTinh = new javax.swing.JTable();

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

        btnThem1.setBackground(new java.awt.Color(0, 51, 255));
        btnThem1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThem1.setForeground(new java.awt.Color(255, 255, 255));
        btnThem1.setText("Thêm");
        btnThem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThem1ActionPerformed(evt);
            }
        });

        btnsua1.setBackground(new java.awt.Color(0, 51, 255));
        btnsua1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnsua1.setForeground(new java.awt.Color(255, 255, 255));
        btnsua1.setText("Sửa");
        btnsua1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsua1ActionPerformed(evt);
            }
        });

        btnXoa1.setBackground(new java.awt.Color(0, 51, 255));
        btnXoa1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnXoa1.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa1.setText("Xóa");
        btnXoa1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoa1ActionPerformed(evt);
            }
        });

        tblThuocTinh.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "STT", "Mã thuộc tính", "Tên thuộc tính"
            }
        ));
        tblThuocTinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblThuocTinhMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblThuocTinh);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(120, 120, 120)
                .addComponent(btnThem1)
                .addGap(158, 158, 158)
                .addComponent(btnsua1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnXoa1)
                .addGap(154, 154, 154))
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
                            .addComponent(rb_sole)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(rb_size)
                                .addGap(42, 42, 42)
                                .addComponent(rb_brand))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(285, 285, 285)
                        .addComponent(jLabel3))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 793, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(31, Short.MAX_VALUE))
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
                    .addComponent(btnThem1)
                    .addComponent(btnsua1)
                    .addComponent(btnXoa1))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void rb_colorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_colorActionPerformed
        type = "color";
    }//GEN-LAST:event_rb_colorActionPerformed

    private void rb_sizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_sizeActionPerformed
        type = "size";
    }//GEN-LAST:event_rb_sizeActionPerformed

    private void rb_brandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_brandActionPerformed
        type = "brand";
    }//GEN-LAST:event_rb_brandActionPerformed

    private void rb_categoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_categoryActionPerformed
        type = "category";
    }//GEN-LAST:event_rb_categoryActionPerformed

    private void rb_soleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_soleActionPerformed
        type = "soleType";

    }//GEN-LAST:event_rb_soleActionPerformed

    private void btnThem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThem1ActionPerformed
        String code = txtMa.getText().trim();
        String name = txtTen.getText().trim();
        if (code.isEmpty() || name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ mã và tên!");
            return;
        }
        switch (type) {
            case "color":
                Color color = new Color();
                color.setColorHexCode(code);
                color.setColorName(name);
                colorDAO.create(color);
                JOptionPane.showMessageDialog(this, "Thêm màu sắc thành công!");
                loadTable();
                break;
            case "size":
                Size size = new Size();
                size.setSizeCode(code);
                size.setSizeValue(name);
                sizeDAO.create(size);
                JOptionPane.showMessageDialog(this, "Thêm kích cỡ thành công!");
                loadTable();
                break;
            case "brand":
                Brand brand = new Brand();
                brand.setBrandCode(code);
                brand.setBrandName(name);
                brandDAO.create(brand);
                JOptionPane.showMessageDialog(this, "Thêm thương hiệu thành công!");
                loadTable();
                break;
            case "category":
                Category category = new Category();
                category.setCategoryCode(code);
                category.setCategoryName(name);
                categoryDAO.create(category);
                JOptionPane.showMessageDialog(this, "Thêm danh mục thành công!");
                loadTable();
                break;
            case "soleType":
                SoleType soleType = new SoleType();
                soleType.setSoleCode(code);
                soleType.setSoleName(name);
                soleTypeDAO.create(soleType);
                JOptionPane.showMessageDialog(this, "Thêm loại đế thành công!");
                loadTable();
                break;
        }
    }//GEN-LAST:event_btnThem1ActionPerformed

    private void btnsua1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsua1ActionPerformed
        int row = tblThuocTinh.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng để sửa!");
            return;
        }
        String code = txtMa.getText().trim();
        String name = txtTen.getText().trim();
        switch (type) {
            case "color":
                Color color = colorDAO.findByCode(code);
                if (color == null) {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy!");
                    return;
                }
                color.setColorName(name);
                colorDAO.update(color);
                JOptionPane.showMessageDialog(this, "Sửa màu sắc thành công!");
                loadTable();
                break;
            case "size":
                Size size = sizeDAO.findByCode(code);
                if (size == null) {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy!");
                    return;
                }
                size.setSizeValue(name);
                sizeDAO.update(size);
                JOptionPane.showMessageDialog(this, "Sửa kích cỡ thành công!");
                loadTable();
                break;
            case "brand":
                Brand brand = brandDAO.findByCode(code);
                if (brand == null) {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy!");
                    return;
                }
                brand.setBrandName(name);
                brandDAO.update(brand);
                JOptionPane.showMessageDialog(this, "Sửa thương hiệu thành công!");
                loadTable();
                break;
            case "category":
                Category category = categoryDAO.findByCode(code);
                if (category == null) {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy!");
                    return;
                }
                category.setCategoryName(name);
                categoryDAO.update(category);
                JOptionPane.showMessageDialog(this, "Sửa danh mục thành công!");
                loadTable();
                break;
            case "soleType":
                SoleType soleType = soleTypeDAO.findByCode(code);
                if (soleType == null) {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy!");
                    return;
                }
                soleType.setSoleName(name);
                soleTypeDAO.update(soleType);
                JOptionPane.showMessageDialog(this, "Sửa loại đế thành công!");
                loadTable();
                break;
        }
    }//GEN-LAST:event_btnsua1ActionPerformed

    private void btnXoa1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoa1ActionPerformed
        int row = tblThuocTinh.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng để xoá!");
            return;
        }
        String code = txtMa.getText().trim();
        switch (type) {
            case "color":
                colorDAO.deleteByCode(code);
                JOptionPane.showMessageDialog(this, "Xoá màu sắc thành công!");
                loadTable();
                break;
            case "size":
                sizeDAO.deleteByCode(code);
                JOptionPane.showMessageDialog(this, "Xoá kích cỡ thành công!");
                loadTable();
                break;
            case "brand":
                brandDAO.deleteByCode(code);
                JOptionPane.showMessageDialog(this, "Xoá thương hiệu thành công!");
                loadTable();
                break;
            case "category":
                categoryDAO.deleteByCode(code);
                JOptionPane.showMessageDialog(this, "Xoá danh mục thành công!");
                loadTable();
                break;
            case "soleType":
                soleTypeDAO.deleteByCode(code);
                JOptionPane.showMessageDialog(this, "Xoá loại đế thành công!");
                loadTable();
                break;
        }
    }//GEN-LAST:event_btnXoa1ActionPerformed

    private void tblThuocTinhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblThuocTinhMouseClicked
        int row = tblThuocTinh.getSelectedRow();
        if (row != -1) {
            txtMa.setText(tblThuocTinh.getValueAt(row, 1).toString());
            txtTen.setText(tblThuocTinh.getValueAt(row, 2).toString());
        }
    }//GEN-LAST:event_tblThuocTinhMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnThem1;
    private javax.swing.JButton btnXoa1;
    private javax.swing.JButton btnsua1;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
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
