package com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewSanPham;

import com.DuAn1.volleyballshoes.app.dao.*;
import com.DuAn1.volleyballshoes.app.dao.impl.*;
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
        loadDataToComboBox(brandDAO.findAll(), jComboBox1, "brandName"); // Brand
        loadDataToComboBox(categoryDAO.findAll(), jComboBox2, "categoryName"); // Category
        loadDataToComboBox(soleTypeDAO.findAll(), jComboBox3, "soleName"); // Sole Type
        loadDataToComboBox(sizeDAO.findAll(), jComboBox4, "sizeValue"); // Size
        loadDataToComboBox(colorDAO.findAll(), jComboBox5, "colorName"); // Color
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
        loadDataToComboBox(brandDAO.findAll(), jComboBox1, "brandName");
    }
    }

    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnThem7 = new javax.swing.JButton();
        pane = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        hinhAnh = new javax.swing.JLabel();
        btnLH = new javax.swing.JButton();
        btnThemNhanhTL = new javax.swing.JButton();
        btnThemLB = new javax.swing.JButton();
        btnThemKichThuoc = new javax.swing.JButton();
        btnThem5 = new javax.swing.JButton();
        btnThemNhanhTacGia = new javax.swing.JButton();
        btnThemLG = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox<>();
        jComboBox6 = new javax.swing.JComboBox<>();
        jComboBox7 = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
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

        btnThemNhanhTL.setText("+");
        btnThemNhanhTL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemNhanhTLActionPerformed(evt);
            }
        });

        btnThemLB.setText("+");
        btnThemLB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemLBActionPerformed(evt);
            }
        });

        btnThemKichThuoc.setText("+");
        btnThemKichThuoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemKichThuocActionPerformed(evt);
            }
        });

        btnThem5.setText("+");
        btnThem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemNhanhNXB(evt);
            }
        });

        btnThemNhanhTacGia.setText("+");
        btnThemNhanhTacGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemNhanhTacGiaActionPerformed(evt);
            }
        });

        btnThemLG.setText("+");
        btnThemLG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemLGActionPerformed(evt);
            }
        });

        jLabel1.setText("jLabel1");

        jLabel2.setText("jLabel2");

        jTextField2.setText("jTextField2");

        jLabel3.setText("jLabel3");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel4.setText("jLabel4");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel5.setText("jLabel5");

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel6.setText("jLabel6");

        jLabel7.setText("jLabel7");

        jLabel8.setText("jLabel8");

        jLabel9.setText("jLabel9");

        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBox7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout paneLayout = new javax.swing.GroupLayout(pane);
        pane.setLayout(paneLayout);
        paneLayout.setHorizontalGroup(
            paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneLayout.createSequentialGroup()
                .addGroup(paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(paneLayout.createSequentialGroup()
                        .addGap(639, 639, 639)
                        .addComponent(btnThem5)
                        .addGap(319, 319, 319)
                        .addComponent(btnThemLG))
                    .addGroup(paneLayout.createSequentialGroup()
                        .addGap(639, 639, 639)
                        .addComponent(btnThemNhanhTL))
                    .addGroup(paneLayout.createSequentialGroup()
                        .addGap(825, 825, 825)
                        .addComponent(btnLH, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(paneLayout.createSequentialGroup()
                        .addGap(639, 639, 639)
                        .addComponent(btnThemLB))
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
                                    .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel8))
                                .addGroup(paneLayout.createSequentialGroup()
                                    .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel5))
                                .addGroup(paneLayout.createSequentialGroup()
                                    .addGroup(paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(184, 184, 184)
                                    .addGroup(paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel4)))))
                        .addGap(39, 39, 39)
                        .addGroup(paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(paneLayout.createSequentialGroup()
                                .addGroup(paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(paneLayout.createSequentialGroup()
                                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(120, 120, 120)
                                        .addComponent(btnThemNhanhTacGia))
                                    .addGroup(paneLayout.createSequentialGroup()
                                        .addGap(192, 192, 192)
                                        .addComponent(btnThemKichThuoc))
                                    .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(153, 153, 153)
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        paneLayout.setVerticalGroup(
            paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem5)
                    .addComponent(btnThemLG))
                .addGroup(paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(paneLayout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(paneLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addGroup(paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnThemNhanhTacGia)
                            .addComponent(jLabel5)
                            .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addGroup(paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jLabel7)
                            .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(paneLayout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(btnThemKichThuoc))
                            .addGroup(paneLayout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addComponent(jLabel9)))
                        .addGap(45, 45, 45)
                        .addComponent(btnThemNhanhTL))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paneLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(11, 11, 11)
                .addComponent(btnLH)
                .addGap(13, 13, 13)
                .addComponent(btnThemLB)
                .addContainerGap(39, Short.MAX_VALUE))
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
    String ma = jTextField2.getText().trim();
    String ten = (String) jComboBox1.getSelectedItem();
    String thuongHieu = (String) jComboBox2.getSelectedItem();
    String mauSac = (String) jComboBox3.getSelectedItem();
    String kichThuoc = (String) jComboBox4.getSelectedItem();
    String gia = ""; // Chưa có trường nhập giá, cần bổ sung JTextField cho giá nếu muốn nhập giá!

    // Kiểm tra dữ liệu đầu vào
    if (ma.isEmpty() || ten.isEmpty() || thuongHieu.isEmpty() || mauSac.isEmpty() || kichThuoc.isEmpty() || gia.isEmpty()) {
        javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin sản phẩm!", "Cảnh báo", javax.swing.JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    try {
        double giaSanPham = Double.parseDouble(gia);
        // TODO: Thực hiện thêm mới sản phẩm vào database hoặc danh sách
        // Ví dụ: gọi ProductController.addProduct(...)
        javax.swing.JOptionPane.showMessageDialog(this, "Thêm sản phẩm thành công!", "Thành công", javax.swing.JOptionPane.INFORMATION_MESSAGE);
        // TODO: Làm mới form hoặc đóng cửa sổ nếu cần
    } catch (NumberFormatException ex) {
        javax.swing.JOptionPane.showMessageDialog(this, "Giá sản phẩm không hợp lệ!", "Lỗi nhập liệu", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_btnThem1ActionPerformed

    private void btnThem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThem7ActionPerformed
    // Thêm nhanh sản phẩm con vào jComboBox1
    String ten = javax.swing.JOptionPane.showInputDialog(this, "Nhập tên sản phẩm con mới:");
    if (ten != null && !ten.trim().isEmpty()) {
        for (int i = 0; i < jComboBox1.getItemCount(); i++) {
            if (jComboBox1.getItemAt(i).equalsIgnoreCase(ten.trim())) {
                javax.swing.JOptionPane.showMessageDialog(this, "Tên sản phẩm con đã tồn tại!", "Cảnh báo", javax.swing.JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
        jComboBox1.addItem(ten.trim());
        javax.swing.JOptionPane.showMessageDialog(this, "Đã thêm sản phẩm con mới!", "Thành công", javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    }//GEN-LAST:event_btnThem7ActionPerformed

    private void btnThemLGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemLGActionPerformed
    // Thêm nhanh loại giày vào jComboBox2
    String ten = javax.swing.JOptionPane.showInputDialog(this, "Nhập tên loại giày mới:");
    if (ten != null && !ten.trim().isEmpty()) {
        for (int i = 0; i < jComboBox2.getItemCount(); i++) {
            if (jComboBox2.getItemAt(i).equalsIgnoreCase(ten.trim())) {
                javax.swing.JOptionPane.showMessageDialog(this, "Loại giày đã tồn tại!", "Cảnh báo", javax.swing.JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
        jComboBox2.addItem(ten.trim());
        javax.swing.JOptionPane.showMessageDialog(this, "Đã thêm loại giày mới!", "Thành công", javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    }//GEN-LAST:event_btnThemLGActionPerformed

    private void btnThemNhanhTacGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemNhanhTacGiaActionPerformed
    // Thêm nhanh tác giả vào jComboBox3
    String ten = javax.swing.JOptionPane.showInputDialog(this, "Nhập tên tác giả mới:");
    if (ten != null && !ten.trim().isEmpty()) {
        for (int i = 0; i < jComboBox3.getItemCount(); i++) {
            if (jComboBox3.getItemAt(i).equalsIgnoreCase(ten.trim())) {
                javax.swing.JOptionPane.showMessageDialog(this, "Tác giả đã tồn tại!", "Cảnh báo", javax.swing.JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
        jComboBox3.addItem(ten.trim());
        javax.swing.JOptionPane.showMessageDialog(this, "Đã thêm tác giả mới!", "Thành công", javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    }//GEN-LAST:event_btnThemNhanhTacGiaActionPerformed

    private void btnThemNhanhNXB(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemNhanhNXB
    // Thêm nhanh NXB vào jComboBox4
    String ten = javax.swing.JOptionPane.showInputDialog(this, "Nhập tên NXB mới:");
    if (ten != null && !ten.trim().isEmpty()) {
        for (int i = 0; i < jComboBox4.getItemCount(); i++) {
            if (jComboBox4.getItemAt(i).equalsIgnoreCase(ten.trim())) {
                javax.swing.JOptionPane.showMessageDialog(this, "Nhà xuất bản đã tồn tại!", "Cảnh báo", javax.swing.JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
        jComboBox4.addItem(ten.trim());
        javax.swing.JOptionPane.showMessageDialog(this, "Đã thêm NXB mới!", "Thành công", javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    }//GEN-LAST:event_btnThemNhanhNXB

    private void btnThemKichThuocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemKichThuocActionPerformed
    // Thêm nhanh kích thước vào jComboBox5
    String ten = javax.swing.JOptionPane.showInputDialog(this, "Nhập kích thước mới:");
    if (ten != null && !ten.trim().isEmpty()) {
        for (int i = 0; i < jComboBox5.getItemCount(); i++) {
            if (jComboBox5.getItemAt(i).equalsIgnoreCase(ten.trim())) {
                javax.swing.JOptionPane.showMessageDialog(this, "Kích thước đã tồn tại!", "Cảnh báo", javax.swing.JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
        jComboBox5.addItem(ten.trim());
        javax.swing.JOptionPane.showMessageDialog(this, "Đã thêm kích thước mới!", "Thành công", javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    }//GEN-LAST:event_btnThemKichThuocActionPerformed

    private void btnThemLBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemLBActionPerformed
    // Thêm nhanh label/phụ trợ vào jComboBox1 (hoặc thay bằng combobox phù hợp nếu có)
    String ten = javax.swing.JOptionPane.showInputDialog(this, "Nhập label mới:");
    if (ten != null && !ten.trim().isEmpty()) {
        for (int i = 0; i < jComboBox1.getItemCount(); i++) {
            if (jComboBox1.getItemAt(i).equalsIgnoreCase(ten.trim())) {
                javax.swing.JOptionPane.showMessageDialog(this, "Label đã tồn tại!", "Cảnh báo", javax.swing.JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
        jComboBox1.addItem(ten.trim());
        javax.swing.JOptionPane.showMessageDialog(this, "Đã thêm label mới!", "Thành công", javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    }//GEN-LAST:event_btnThemLBActionPerformed

    private void btnThemNhanhTLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemNhanhTLActionPerformed
    // Thêm nhanh thể loại vào jComboBox2 (hoặc thay bằng combobox phù hợp nếu có)
    String ten = javax.swing.JOptionPane.showInputDialog(this, "Nhập thể loại mới:");
    if (ten != null && !ten.trim().isEmpty()) {
        for (int i = 0; i < jComboBox2.getItemCount(); i++) {
            if (jComboBox2.getItemAt(i).equalsIgnoreCase(ten.trim())) {
                javax.swing.JOptionPane.showMessageDialog(this, "Thể loại đã tồn tại!", "Cảnh báo", javax.swing.JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
        jComboBox2.addItem(ten.trim());
        javax.swing.JOptionPane.showMessageDialog(this, "Đã thêm thể loại mới!", "Thành công", javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    }//GEN-LAST:event_btnThemNhanhTLActionPerformed

    private void btnLHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLHActionPerformed
    // Hiển thị thông tin liên hệ hỗ trợ
    javax.swing.JOptionPane.showMessageDialog(this, "Liên hệ hỗ trợ: 1900-xxxx hoặc email: support@volleyballshoes.com", "Liên hệ", javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnLHActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLH;
    private javax.swing.JButton btnThem1;
    private javax.swing.JButton btnThem5;
    private javax.swing.JButton btnThem7;
    private javax.swing.JButton btnThemKichThuoc;
    private javax.swing.JButton btnThemLB;
    private javax.swing.JButton btnThemLG;
    private javax.swing.JButton btnThemNhanhTL;
    private javax.swing.JButton btnThemNhanhTacGia;
    private javax.swing.JButton btnThoat;
    private javax.swing.JLabel hinhAnh;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JComboBox<String> jComboBox6;
    private javax.swing.JComboBox<String> jComboBox7;
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
    private javax.swing.JTextField jTextField2;
    private javax.swing.JPanel pane;
    // End of variables declaration//GEN-END:variables
}
