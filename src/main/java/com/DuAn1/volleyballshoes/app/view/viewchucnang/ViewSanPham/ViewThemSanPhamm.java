package com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewSanPham;

import com.DuAn1.volleyballshoes.app.dao.BrandDAO;
import com.DuAn1.volleyballshoes.app.dao.CategoryDAO;
import com.DuAn1.volleyballshoes.app.dao.ColorDAO;
import com.DuAn1.volleyballshoes.app.dao.ProductDAO;
import com.DuAn1.volleyballshoes.app.dao.ProductVariantDAO;
import com.DuAn1.volleyballshoes.app.dao.SizeDAO;
import com.DuAn1.volleyballshoes.app.dao.SoleTypeDAO;
import com.DuAn1.volleyballshoes.app.dao.impl.BrandDAOImpl;
import com.DuAn1.volleyballshoes.app.dao.impl.CategoryDAOImpl;
import com.DuAn1.volleyballshoes.app.dao.impl.ColorDAOImpl;
import com.DuAn1.volleyballshoes.app.dao.impl.ProductDAOImpl;
import com.DuAn1.volleyballshoes.app.dao.impl.ProductVariantDAOImpl;
import com.DuAn1.volleyballshoes.app.dao.impl.SizeDAOImpl;
import com.DuAn1.volleyballshoes.app.dao.impl.SoleTypeDAOImpl;
import com.DuAn1.volleyballshoes.app.entity.*;
import java.math.BigDecimal;
import java.util.List;
import java.lang.reflect.Method;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class ViewThemSanPhamm extends javax.swing.JPanel {

    private final BrandDAO brandDAO;
    private final CategoryDAO categoryDAO;
    private final SoleTypeDAO soleTypeDAO;
    private final SizeDAO sizeDAO;
    private final ColorDAO colorDAO;
    private final ProductDAO productDAO;

    public ViewThemSanPhamm() {
// Initialize DAOs
        this.brandDAO = new BrandDAOImpl();
        this.categoryDAO = new CategoryDAOImpl();
        this.soleTypeDAO = new SoleTypeDAOImpl();
        this.sizeDAO = new SizeDAOImpl();
        this.colorDAO = new ColorDAOImpl();
        this.productDAO = new ProductDAOImpl();

        initComponents();
        loadAllData();
    }

    private void loadAllData() {
        try {
            // Load Sole Types
            List<SoleType> soleTypes = soleTypeDAO.findAll();
            loadDataToComboBox(cbo_sole, soleTypes, "soleId", "soleName");

            // Load Sizes
            List<Size> sizes = sizeDAO.findAll();
            loadDataToComboBox(cbo_size, sizes, "sizeId", "sizeValue");

            // Load Colors
            List<Color> colors = colorDAO.findAll();
            loadDataToComboBox(cbo_color, colors, "colorId", "colorName");

            // Load Product Codes
            List<Product> products = productDAO.findAll();
            loadDataToComboBox(cbo_product_code, products, "productId", "productCode");

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi tải dữ liệu: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private int getSelectedIdFromComboBox(javax.swing.JComboBox<String> comboBox) {
        String selectedValue = (String) comboBox.getSelectedItem();
        if (selectedValue == null) {
            return -1;
        }

        // Lấy model của combobox
        DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) comboBox.getModel();

        // Tìm index của item được chọn
        int selectedIndex = model.getIndexOf(selectedValue);
        if (selectedIndex == -1) {
            return -1;
        }

        // Lấy đối tượng gốc từ model (nếu có lưu ID trong model)
        // Ở đây giả sử chúng ta lưu ID trong model dưới dạng "ID:value"
        String item = model.getElementAt(selectedIndex);
        try {
            // Nếu item có dạng "ID:value" thì tách lấy ID
            if (item.contains(":")) {
                return Integer.parseInt(item.split(":")[0]);
            }
            // Nếu không thì trả về index + 1 (giả sử ID bắt đầu từ 1)
            return selectedIndex + 1;
        } catch (Exception e) {
            return selectedIndex + 1;
        }
    }

    private <T> void loadDataToComboBox(JComboBox<String> comboBox, List<T> items, String idFieldName, String displayFieldName) {
        comboBox.removeAllItems();
        comboBox.addItem("Chọn");
        
        if (items == null || items.isEmpty()) {
            return;
        }
        
        try {
            // Get the class of the first item to determine the type
            Class<?> itemClass = items.get(0).getClass();
            String className = itemClass.getSimpleName();
            
            // Determine the correct getter methods based on the entity type
            String idGetter;
            String displayGetter;
            
            // Special handling for different entity types
            switch (className) {
                case "Product":
                    idGetter = "getProductId";
                    displayGetter = "getProductCode"; // Always use productCode for display
                    break;
                case "SoleType":
                    idGetter = "getSoleId";
                    displayGetter = "getSoleName";
                    break;
                case "Size":
                    idGetter = "getSizeId";
                    displayGetter = "getSizeValue";
                    break;
                case "Color":
                    idGetter = "getColorId";
                    displayGetter = "getColorName";
                    break;
                default:
                    // Fallback to standard naming convention if entity type is not explicitly handled
                    idGetter = "get" + idFieldName.substring(0, 1).toUpperCase() + idFieldName.substring(1);
                    displayGetter = "get" + displayFieldName.substring(0, 1).toUpperCase() + displayFieldName.substring(1);
            }
            
            // Get the methods using reflection
            Method idMethod = itemClass.getMethod(idGetter);
            Method displayMethod = itemClass.getMethod(displayGetter);
            
            // Add items to the combo box
            for (T item : items) {
                Object id = idMethod.invoke(item);
                Object displayValue = displayMethod.invoke(item);
                
                if (id != null && displayValue != null) {
                    // Store as "ID:displayValue" for later extraction
                    comboBox.addItem(id + ":" + displayValue.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }            
    }

    private void clearForm() {
        // Xóa nội dung các trường nhập liệu
        txt_sku_product_variant.setText("");
        txt_ori_price.setText("");
        jTextField1.setText(""); // Clear quantity field

        // Đặt lại các combobox về giá trị mặc định (chọn mục đầu tiên)
        if (cbo_product_code.getItemCount() > 0) {
            cbo_product_code.setSelectedIndex(0);
        }
        if (cbo_sole.getItemCount() > 0) {
            cbo_sole.setSelectedIndex(0);
        }
        if (cbo_color.getItemCount() > 0) {
            cbo_color.setSelectedIndex(0);
        }
        if (cbo_size.getItemCount() > 0) {
            cbo_size.setSelectedIndex(0);
        }

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
        jLabel4 = new javax.swing.JLabel();
        cbo_sole = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        cbo_color = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        cbo_size = new javax.swing.JComboBox<>();
        txt_sku_product_variant = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        cbo_product_code = new javax.swing.JComboBox<>();
        btnThoat = new javax.swing.JButton();
        btnThem1 = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(0, 13, Short.MAX_VALUE)
                .addComponent(hinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        jLabel1.setText("Mã Biến thế Sản phẩm");

        jLabel2.setText("Giá");

        jLabel4.setText("Đế giày");

        cbo_sole.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel5.setText("Màu sắc");

        cbo_color.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel8.setText("Size");

        cbo_size.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel10.setText("Số lượng");

        jLabel11.setText("Mã Sản phẩm");

        cbo_product_code.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout paneLayout = new javax.swing.GroupLayout(pane);
        pane.setLayout(paneLayout);
        paneLayout.setHorizontalGroup(
            paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnLH, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(paneLayout.createSequentialGroup()
                        .addGroup(paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)
                            .addComponent(jLabel10))
                        .addGap(61, 61, 61)
                        .addGroup(paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paneLayout.createSequentialGroup()
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel5))
                            .addGroup(paneLayout.createSequentialGroup()
                                .addGroup(paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_ori_price, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_sku_product_variant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(paneLayout.createSequentialGroup()
                                        .addGap(212, 212, 212)
                                        .addComponent(jLabel4))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paneLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel11)
                                        .addGap(11, 11, 11)))))
                        .addGap(39, 39, 39)
                        .addGroup(paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbo_color, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbo_size, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(paneLayout.createSequentialGroup()
                                .addGroup(paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbo_sole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbo_product_code, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(34, 34, 34)
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(64, Short.MAX_VALUE))
        );
        paneLayout.setVerticalGroup(
            paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneLayout.createSequentialGroup()
                .addGroup(paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(paneLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txt_sku_product_variant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)
                            .addComponent(cbo_product_code, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(paneLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(paneLayout.createSequentialGroup()
                                .addGroup(paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(txt_ori_price, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4)
                                    .addComponent(cbo_sole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(32, 32, 32)
                                .addGroup(paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(cbo_color, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(34, 34, 34)
                                .addGroup(paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel8)
                                    .addComponent(cbo_size, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLH)))
                .addContainerGap(214, Short.MAX_VALUE))
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

        btnSua.setBackground(new java.awt.Color(0, 51, 255));
        btnSua.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSua.setForeground(new java.awt.Color(255, 255, 255));
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnThoat)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnThem1)
                .addGap(18, 18, 18)
                .addComponent(btnSua)
                .addGap(226, 226, 226))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem1)
                    .addComponent(btnThoat)
                    .addComponent(btnSua))
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
        try {
            // Lấy dữ liệu từ form
            String sku = txt_sku_product_variant.getText().trim();
            String priceText = txt_ori_price.getText().trim();
            String quantityText = jTextField1.getText().trim();

            // Validate dữ liệu bắt buộc
            if (sku.isEmpty() || priceText.isEmpty() || quantityText.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng điền đầy đủ thông tin bắt buộc!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate giá và số lượng
            double price;
            int quantity;
            try {
                price = Double.parseDouble(priceText);
                quantity = Integer.parseInt(quantityText);
                if (price <= 0 || quantity < 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                        "Giá phải là số dương và số lượng phải là số không âm!",
                        "Lỗi nhập liệu",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Lấy ID sản phẩm từ combobox
            int productId = getSelectedIdFromComboBox(cbo_product_code);
            if (productId == -1) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng chọn mã sản phẩm!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Lấy các ID từ combobox
            int sizeId = getSelectedIdFromComboBox(cbo_size);
            int colorId = getSelectedIdFromComboBox(cbo_color);
            int soleId = getSelectedIdFromComboBox(cbo_sole);

            if (sizeId == -1 || colorId == -1 || soleId == -1) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng chọn đầy đủ thông tin kích thước, màu sắc và đế giày!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Lấy đối tượng từ database
            Product product = productDAO.findById(productId);
            Size size = sizeDAO.findById(sizeId);
            Color color = colorDAO.findById(colorId);
            SoleType sole = soleTypeDAO.findById(soleId);

            if (product == null || size == null || color == null || sole == null) {
                JOptionPane.showMessageDialog(this,
                        "Không tìm thấy thông tin sản phẩm hoặc thông số kỹ thuật!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Tạo đối tượng ProductVariant
            ProductVariant variant = ProductVariant.builder()
                    .productId(product.getProductId())
                    .sizeId(size.getSizeId())
                    .colorId(color.getColorId())
                    .soleId(sole.getSoleId())
                    .variantSku(sku)
                    .variantOrigPrice(BigDecimal.valueOf(price))
                    .quantity(quantity)
                    .variantImgUrl("") // Có thể thêm URL ảnh sau
                    .build();

            // Lưu vào database
            ProductVariantDAO variantDAO = new ProductVariantDAOImpl();
            ProductVariant createdVariant = variantDAO.create(variant);

            if (createdVariant != null) {
                JOptionPane.showMessageDialog(this,
                        "Thêm biến thể sản phẩm thành công!",
                        "Thành công",
                        JOptionPane.INFORMATION_MESSAGE);
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Có lỗi xảy ra khi thêm biến thể sản phẩm!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Lỗi: " + e.getMessage(),
                    "Lỗi hệ thống",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnThem1ActionPerformed

    private void btnThem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThem7ActionPerformed
        // Thêm nhanh sản phẩm con vào jComboBox1

    }//GEN-LAST:event_btnThem7ActionPerformed

    private void btnLHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLHActionPerformed
        // Hiển thị thông tin liên hệ hỗ trợ
        javax.swing.JOptionPane.showMessageDialog(this, "Liên hệ hỗ trợ: 1900-xxxx hoặc email: support@volleyballshoes.com", "Liên hệ", javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnLHActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSuaActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLH;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem1;
    private javax.swing.JButton btnThem7;
    private javax.swing.JButton btnThoat;
    private javax.swing.JComboBox<String> cbo_color;
    private javax.swing.JComboBox<String> cbo_product_code;
    private javax.swing.JComboBox<String> cbo_size;
    private javax.swing.JComboBox<String> cbo_sole;
    private javax.swing.JLabel hinhAnh;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JPanel pane;
    private javax.swing.JTextField txt_ori_price;
    private javax.swing.JTextField txt_sku_product_variant;
    // End of variables declaration//GEN-END:variables
}
