package com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewSanPham;

import com.DuAn1.volleyballshoes.app.dao.BrandDAO;
import com.DuAn1.volleyballshoes.app.dao.CategoryDAO;
import com.DuAn1.volleyballshoes.app.dao.ColorDAO;
import com.DuAn1.volleyballshoes.app.dao.SizeDAO;
import com.DuAn1.volleyballshoes.app.dao.SoleTypeDAO;
import com.DuAn1.volleyballshoes.app.dao.impl.BrandDAOImpl;
import com.DuAn1.volleyballshoes.app.dao.impl.CategoryDAOImpl;
import com.DuAn1.volleyballshoes.app.dao.impl.ColorDAOImpl;
import com.DuAn1.volleyballshoes.app.dao.impl.SizeDAOImpl;
import com.DuAn1.volleyballshoes.app.dao.impl.SoleTypeDAOImpl;
import com.DuAn1.volleyballshoes.app.entity.*;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

public class ViewThemSanPhamm extends javax.swing.JPanel {
    
 
    private final BrandDAO brandDAO;
    private final CategoryDAO categoryDAO;
    private final SoleTypeDAO soleTypeDAO;
    private final SizeDAO sizeDAO;
    private final ColorDAO colorDAO;
    
    public ViewThemSanPhamm() {
// Initialize DAOs
        this.brandDAO = new BrandDAOImpl();
        this.categoryDAO = new CategoryDAOImpl();
        this.soleTypeDAO = new SoleTypeDAOImpl();
        this.sizeDAO = new SizeDAOImpl();
        this.colorDAO = new ColorDAOImpl();
        
        initComponents();
        loadAllData();
    }
    
    private void loadAllData() {
        loadDataToComboBox(brandDAO.findAll(), cbo_brand, "brandName"); // Brand
        loadDataToComboBox(categoryDAO.findAll(), cbo_category, "categoryName"); // Category
        loadDataToComboBox(soleTypeDAO.findAll(), cbo_sole, "soleName"); // Sole Type
        loadDataToComboBox(sizeDAO.findAll(), cbo_size, "sizeValue"); // Size
        loadDataToComboBox(colorDAO.findAll(), cbo_color, "colorName"); // Color
    }
    
    private <T> void loadDataToComboBox(List<T> items, javax.swing.JComboBox<String> comboBox, String propertyName) {
        try {
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
            
            for (T item : items) {
                try {
                    // Use reflection to get the property value
                    String methodName = "get" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
                    String value = (String) item.getClass().getMethod(methodName).invoke(item);
                    model.addElement(value);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            
            comboBox.setModel(model);
            
            if (model.getSize() > 0) {
                comboBox.setSelectedIndex(0);
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi tải dữ liệu: " + ex.getMessage(), 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    // Method kept for backward compatibility
    private void loadBrandsToComboBox() {
        loadDataToComboBox(brandDAO.findAll(), cbo_brand, "brandName");
    }

        private void clearForm() {
        // Xóa nội dung các trường nhập liệu
        txt_sku_product_variant.setText("");
        txt_name.setText("");
        txt_ori_price.setText("");
        jTextArea1.setText("");
        
        // Đặt lại các combobox về giá trị mặc định (nếu cần)
        if (cbo_brand.getItemCount() > 0) cbo_brand.setSelectedIndex(0);
        if (cbo_category.getItemCount() > 0) cbo_category.setSelectedIndex(0);
        if (cbo_sole.getItemCount() > 0) cbo_sole.setSelectedIndex(0);
        if (cbo_color.getItemCount() > 0) cbo_color.setSelectedIndex(0);
        if (cbo_size.getItemCount() > 0) cbo_size.setSelectedIndex(0);
        
        // Focus về trường đầu tiên
        txt_sku_product_variant.requestFocus();
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
            java.util.logging.Logger.getLogger(ViewThemSanPhamm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewThemSanPhamm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewThemSanPhamm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewThemSanPhamm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewThemSanPhamm().setVisible(true);
            }
        });
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnThem7 = new javax.swing.JButton();
        pane = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        hinhAnh = new javax.swing.JLabel();
        btnLH = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_ori_price = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cbo_brand = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        cbo_sole = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        cbo_color = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        cbo_category = new javax.swing.JComboBox<>();
        cbo_size = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        txt_sku_product_variant = new javax.swing.JTextField();
        txt_name = new javax.swing.JTextField();
        btnThoat = new javax.swing.JButton();
        btnThem1 = new javax.swing.JButton();

        btnThem7.setText("+");
        btnThem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThem7ActionPerformed(evt);
            }
        });

        pane.setBorder(javax.swing.BorderFactory.createTitledBorder("Thông Tin Sản Phẩm"));

        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(hinhAnh, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(hinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(7, Short.MAX_VALUE))
        );

        btnLH.setBackground(new java.awt.Color(0, 51, 255));
        btnLH.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLH.setForeground(new java.awt.Color(255, 255, 255));
        btnLH.setText("Lấy Ảnh");
        btnLH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLHActionPerformed(evt);
            }
        });

        jLabel1.setText("Mã Sản phẩm");

        jLabel2.setText("Giá");

        jLabel3.setText("Thương hiệu");

        cbo_brand.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel4.setText("Đế giày");

        cbo_sole.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel5.setText("Màu sắc");

        cbo_color.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel6.setText("Tên");

        jLabel7.setText("Loại");

        jLabel8.setText("Size");

        jLabel9.setText("Mô tả");

        cbo_category.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbo_size.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout paneLayout = new javax.swing.GroupLayout(pane);
        pane.setLayout(paneLayout);
        paneLayout.setHorizontalGroup(
            paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel9))
                .addGap(61, 61, 61)
                .addGroup(paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(paneLayout.createSequentialGroup()
                            .addComponent(cbo_category, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paneLayout.createSequentialGroup()
                            .addComponent(txt_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5))
                        .addGroup(paneLayout.createSequentialGroup()
                            .addGroup(paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txt_ori_price, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txt_sku_product_variant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(185, 185, 185)
                            .addGroup(paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel3)
                                .addComponent(jLabel4)))))
                .addGap(39, 39, 39)
                .addGroup(paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbo_brand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbo_sole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(paneLayout.createSequentialGroup()
                        .addGroup(paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbo_color, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbo_size, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(296, 296, 296)
                        .addGroup(paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnLH, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        paneLayout.setVerticalGroup(
            paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(cbo_brand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_sku_product_variant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(paneLayout.createSequentialGroup()
                        .addGroup(paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(paneLayout.createSequentialGroup()
                                .addGap(66, 66, 66)
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(paneLayout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addGroup(paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(txt_ori_price, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4)
                                    .addComponent(cbo_sole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(32, 32, 32)
                                .addGroup(paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(cbo_color, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6)
                                    .addComponent(txt_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(34, 34, 34)
                                .addGroup(paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel7)
                                    .addComponent(cbo_category, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbo_size, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(38, 38, 38)
                                .addComponent(jLabel9)))
                        .addGap(38, 38, 38)
                        .addComponent(btnLH))
                    .addGroup(paneLayout.createSequentialGroup()
                        .addGap(199, 199, 199)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(77, Short.MAX_VALUE))
        );

        btnThoat.setBackground(new java.awt.Color(0, 51, 255));
        btnThoat.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThoat.setForeground(new java.awt.Color(255, 255, 255));
        btnThoat.setText("Thoát");
        btnThoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThoatActionPerformed(evt);
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnThoat)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 848, Short.MAX_VALUE)
                .addComponent(btnThem1)
                .addGap(24, 24, 24))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(pane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem1)
                    .addComponent(btnThoat))
                .addGap(23, 23, 23))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoatActionPerformed
    // Đóng cửa sổ hiện tại
    java.awt.Window window = javax.swing.SwingUtilities.getWindowAncestor(this);
    if (window != null) {
        window.dispose();
    }
    }//GEN-LAST:event_btnThoatActionPerformed

    
    private void btnThem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThem1ActionPerformed
    // Lấy dữ liệu từ các trường nhập liệu
        String maSanPham = txt_sku_product_variant.getText().trim();
        String tenSanPham = txt_name.getText().trim();
        String moTa = jTextArea1.getText().trim();
        String gia = txt_ori_price.getText().trim();
        
        // Lấy dữ liệu từ các combobox
        String thuongHieu = (String) cbo_brand.getSelectedItem();
        String danhMuc = (String) cbo_category.getSelectedItem();
        String deGiay = (String) cbo_sole.getSelectedItem();
        String mauSac = (String) cbo_color.getSelectedItem();
        String kichThuoc = (String) cbo_size.getSelectedItem();

        // Kiểm tra dữ liệu đầu vào
        if (maSanPham.isEmpty() || tenSanPham.isEmpty() || moTa.isEmpty() || gia.isEmpty() || 
            thuongHieu == null || danhMuc == null || deGiay == null || mauSac == null || kichThuoc == null) {
            javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin sản phẩm!", "Cảnh báo", javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            double giaSanPham = Double.parseDouble(gia);
            // TODO: Thực hiện thêm mới sản phẩm vào database hoặc danh sách
            // Ví dụ: 
            // Product product = new Product(maSanPham, tenSanPham, moTa, giaSanPham, ...);
            // productService.addProduct(product);
            
            javax.swing.JOptionPane.showMessageDialog(this, "Thêm sản phẩm thành công!", "Thành công", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            
            // Làm mới form sau khi thêm thành công
            clearForm();
            
        } catch (NumberFormatException ex) {
            javax.swing.JOptionPane.showMessageDialog(this, "Giá sản phẩm phải là số!", "Lỗi nhập liệu", javax.swing.JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(this, "Lỗi khi thêm sản phẩm: " + ex.getMessage(), "Lỗi", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnThem1ActionPerformed

    private void btnThem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThem7ActionPerformed
    // Thêm nhanh sản phẩm con vào jComboBox1
    String ten = javax.swing.JOptionPane.showInputDialog(this, "Nhập tên sản phẩm con mới:");
    if (ten != null && !ten.trim().isEmpty()) {
        for (int i = 0; i < cbo_brand.getItemCount(); i++) {
            if (cbo_brand.getItemAt(i).equalsIgnoreCase(ten.trim())) {
                javax.swing.JOptionPane.showMessageDialog(this, "Tên sản phẩm con đã tồn tại!", "Cảnh báo", javax.swing.JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
        cbo_brand.addItem(ten.trim());
        javax.swing.JOptionPane.showMessageDialog(this, "Đã thêm sản phẩm con mới!", "Thành công", javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    }//GEN-LAST:event_btnThem7ActionPerformed

    private void btnLHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLHActionPerformed
    // Hiển thị thông tin liên hệ hỗ trợ
    javax.swing.JOptionPane.showMessageDialog(this, "Liên hệ hỗ trợ: 1900-xxxx hoặc email: support@volleyballshoes.com", "Liên hệ", javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnLHActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLH;
    private javax.swing.JButton btnThem1;
    private javax.swing.JButton btnThem7;
    private javax.swing.JButton btnThoat;
    private javax.swing.JComboBox<String> cbo_brand;
    private javax.swing.JComboBox<String> cbo_category;
    private javax.swing.JComboBox<String> cbo_color;
    private javax.swing.JComboBox<String> cbo_size;
    private javax.swing.JComboBox<String> cbo_sole;
    private javax.swing.JLabel hinhAnh;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JPanel pane;
    private javax.swing.JTextField txt_name;
    private javax.swing.JTextField txt_ori_price;
    private javax.swing.JTextField txt_sku_product_variant;
    // End of variables declaration//GEN-END:variables
}
