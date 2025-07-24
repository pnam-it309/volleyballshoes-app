/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.DuAn1.volleyballshoes.app.ui;

import com.DuAn1.volleyballshoes.app.controller.ProductController;
import com.DuAn1.volleyballshoes.app.dto.request.ProductRequest;
import com.DuAn1.volleyballshoes.app.entity.Brand;
import com.DuAn1.volleyballshoes.app.entity.Category;
import com.DuAn1.volleyballshoes.app.entity.SoleType;
import com.DuAn1.volleyballshoes.app.entity.Size;
import com.DuAn1.volleyballshoes.app.entity.Color;
import com.DuAn1.volleyballshoes.app.utils.XQuery;
import java.util.List;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import com.DuAn1.volleyballshoes.app.repository.ProductVariantRepository;
import com.DuAn1.volleyballshoes.app.entity.ProductVariant;
import com.DuAn1.volleyballshoes.app.repository.ProductRepository;
import com.DuAn1.volleyballshoes.app.entity.Product;
import com.DuAn1.volleyballshoes.app.utils.ExcelUtil;
import com.DuAn1.volleyballshoes.app.utils.NotificationUtil;

/**
 *
 * @author nickh
 */
public class ProductManagement extends javax.swing.JPanel {

    private javax.swing.JButton btn_exportExcel;
    private javax.swing.JButton btn_importExcel;
    private javax.swing.JButton btn_pickImage;
    private String selectedImagePath = null;
    private javax.swing.JLabel lbl_imagePreview;

    private final ProductController productController = new ProductController();
    private Integer selectedProductId = null;
    private List<Brand> brandList = new ArrayList<>();
    private List<Category> categoryList = new ArrayList<>();
    private List<SoleType> soleTypeList = new ArrayList<>();
    private List<Size> sizeList = new ArrayList<>();
    private List<Color> colorList = new ArrayList<>();
    private final ProductVariantRepository productVariantRepo = new ProductVariantRepository();
    private final ProductRepository productRepo = new ProductRepository();
    private Integer selectedVariantId = null;

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTextField2 = new javax.swing.JTextField();
        btn_search = new javax.swing.JButton();
        btn_dowload_excel = new javax.swing.JButton();
        btn_import = new javax.swing.JButton();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        btn_create = new javax.swing.JButton();
        btn_update = new javax.swing.JButton();
        btn_delete = new javax.swing.JButton();
        btn_add_image = new javax.swing.JButton();

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Hình ảnh"));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 168, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 206, Short.MAX_VALUE)
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Thông tin sản phẩm"));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable1);

        btn_search.setText("Tìm kiếm");

        btn_dowload_excel.setText("Tải mẫu Excel");
        btn_dowload_excel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dowload_excelActionPerformed(evt);
            }
        });

        btn_import.setText("Thêm từ Excel");
        btn_import.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_importActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 746, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addComponent(btn_search)
                        .addGap(151, 151, 151)
                        .addComponent(btn_dowload_excel)
                        .addGap(45, 45, 45)
                        .addComponent(btn_import)))
                .addContainerGap(175, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_search)
                    .addComponent(btn_dowload_excel)
                    .addComponent(btn_import))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Sản phẩm"));

        jLabel1.setText("Tên sản phẩm");

        jLabel7.setText("Giá");

        jLabel8.setText("Mã Sản phẩm");

        jLabel2.setText("Thương hiệu");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel3.setText("Loại");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel4.setText("Đế giày");

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel5.setText("size");

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel6.setText("Màu");

        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel9.setText("Mô tả");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel1))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel8)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(37, 37, 37)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(45, 45, 45)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(jLabel1))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel7)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(96, Short.MAX_VALUE))
        );

        jSplitPane1.setLeftComponent(jPanel1);

        btn_create.setText("thêm");
        btn_create.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_createActionPerformed(evt);
            }
        });

        btn_update.setText("sửa");
        btn_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateActionPerformed(evt);
            }
        });

        btn_delete.setText("xóa");
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });

        btn_add_image.setText("Thêm ảnh");
        btn_add_image.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_add_imageActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_create)
                    .addComponent(btn_update)
                    .addComponent(btn_delete)
                    .addComponent(btn_add_image))
                .addContainerGap(93, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(btn_create)
                .addGap(18, 18, 18)
                .addComponent(btn_update)
                .addGap(18, 18, 18)
                .addComponent(btn_delete)
                .addGap(18, 18, 18)
                .addComponent(btn_add_image)
                .addContainerGap(201, Short.MAX_VALUE))
        );

        jSplitPane1.setRightComponent(jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(277, 277, 277)
                        .addComponent(jSplitPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(43, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSplitPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(117, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_createActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_createActionPerformed
        create();
    }//GEN-LAST:event_btn_createActionPerformed

    private void btn_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateActionPerformed
        Update();
    }//GEN-LAST:event_btn_updateActionPerformed

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        Delete();
    }//GEN-LAST:event_btn_deleteActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        fillFromTable();
    }//GEN-LAST:event_jTable1MouseClicked

    private void btn_dowload_excelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dowload_excelActionPerformed
        exportExcelTemplate();
    }//GEN-LAST:event_btn_dowload_excelActionPerformed

    private void btn_importActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_importActionPerformed
        importExcelProducts();
    }//GEN-LAST:event_btn_importActionPerformed

    private void btn_add_imageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_add_imageActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_add_imageActionPerformed

    public ProductManagement() {
        initComponents();
        // Thêm nút tải mẫu và import Excel vào giao diện
        btn_exportExcel = new javax.swing.JButton("Tải mẫu Excel");
        btn_importExcel = new javax.swing.JButton("Thêm từ Excel");
        btn_exportExcel.addActionListener(e -> exportExcelTemplate());
        btn_importExcel.addActionListener(e -> importExcelProducts());
        jPanel1.add(btn_exportExcel);
        jPanel1.add(btn_importExcel);
        loadBrandCombo();
        loadCategoryCombo();
        loadSoleTypeCombo();
        loadSizeCombo();
        loadColorCombo();
        loadTable();
    }

    // Xử lý tải mẫu Excel
    private void exportExcelTemplate() {
        javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
        chooser.setDialogTitle("Chọn nơi lưu file mẫu Excel");
        // Đặt tên mặc định cho file
        chooser.setSelectedFile(new java.io.File("thong_tin_san_pham.xlsx"));
        int result = chooser.showSaveDialog(this);
        if (result == javax.swing.JFileChooser.APPROVE_OPTION) {
            java.io.File file = chooser.getSelectedFile();
            String path = file.getAbsolutePath();
            // Đảm bảo có đuôi .xlsx
            if (!path.toLowerCase().endsWith(".xlsx")) {
                path += ".xlsx";
            }
            try {
                com.DuAn1.volleyballshoes.app.utils.ExcelUtil.exportProductTemplate(path);
                NotificationUtil.showSuccess(this, "Đã tạo file mẫu Excel thành công!");
            } catch (Exception ex) {
                NotificationUtil.showError(this, "Lỗi khi tạo file mẫu Excel!", ex);
            }
        }
    }

    // Xử lý import sản phẩm từ Excel
    private void importExcelProducts() {
        javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
        chooser.setDialogTitle("Chọn file Excel để import");
        int result = chooser.showOpenDialog(this);
        if (result == javax.swing.JFileChooser.APPROVE_OPTION) {
            java.io.File file = chooser.getSelectedFile();
            try {
                java.util.List<java.util.Map<String, String>> rows = (java.util.List<java.util.Map<String, String>>) ExcelUtil.importProductExcel(file.getAbsolutePath());
                int count = 0;
                for (java.util.Map<String, String> row : rows) {
                    // Nếu trường là số, coi là ID, tự map sang tên
                    String productName = row.getOrDefault("Tên SP", "");
                    String productDesc = row.getOrDefault("Mô tả", "");
                    String sku = row.getOrDefault("Mã Sản Phẩm", "");
                    double price = Double.parseDouble(row.getOrDefault("Giá", "0"));

                    String categoryRaw = row.getOrDefault("Loại", "");
                    String soleRaw = row.getOrDefault("Đế giày", "");
                    String sizeRaw = row.getOrDefault("Size", "");
                    String colorRaw = row.getOrDefault("Màu", "");
                    String brandRaw = row.getOrDefault("Thương hiệu", "");

                    int brandId, categoryId, soleId, sizeId, colorId;
                    String brandName, categoryName, soleName, sizeValue, colorName;

                    // Nếu nhập số, map sang tên
                    if (brandRaw.matches("\\d+")) {
                        brandId = Integer.parseInt(brandRaw);
                        brandName = brandList.stream().filter(b -> b.brand_id == brandId).map(b -> b.brand_name).findFirst().orElse("");
                    } else {
                        brandName = brandRaw;
                        brandId = brandList.stream().filter(b -> b.brand_name.equals(brandName)).map(b -> b.brand_id).findFirst().orElse(-1);
                    }
                    if (categoryRaw.matches("\\d+")) {
                        categoryId = Integer.parseInt(categoryRaw);
                        categoryName = categoryList.stream().filter(c -> c.category_id == categoryId).map(c -> c.category_name).findFirst().orElse("");
                    } else {
                        categoryName = categoryRaw;
                        categoryId = categoryList.stream().filter(c -> c.category_name.equals(categoryName)).map(c -> c.category_id).findFirst().orElse(-1);
                    }
                    if (soleRaw.matches("\\d+")) {
                        soleId = Integer.parseInt(soleRaw);
                        soleName = soleTypeList.stream().filter(s -> s.sole_id == soleId).map(s -> s.sole_name).findFirst().orElse("");
                    } else {
                        soleName = soleRaw;
                        soleId = soleTypeList.stream().filter(s -> s.sole_name.equals(soleName)).map(s -> s.sole_id).findFirst().orElse(-1);
                    }
                    if (sizeRaw.matches("\\d+")) {
                        sizeId = Integer.parseInt(sizeRaw);
                        sizeValue = sizeList.stream().filter(s -> s.size_id == sizeId).map(s -> s.size_value).findFirst().orElse("");
                    } else {
                        sizeValue = sizeRaw;
                        sizeId = sizeList.stream().filter(s -> s.size_value.equals(sizeValue)).map(s -> s.size_id).findFirst().orElse(-1);
                    }
                    if (colorRaw.matches("\\d+")) {
                        colorId = Integer.parseInt(colorRaw);
                        colorName = colorList.stream().filter(c -> c.color_id == colorId).map(c -> c.color_name).findFirst().orElse("");
                    } else {
                        colorName = colorRaw;
                        colorId = colorList.stream().filter(c -> c.color_name.equals(colorName)).map(c -> c.color_id).findFirst().orElse(-1);
                    }

                    Product product = Product.builder()
                            .brand_id(brandId)
                            .category_id(categoryId)
                            .product_name(productName)
                            .product_desc(productDesc)
                            .product_create_at(java.time.LocalDateTime.now())
                            .product_updated_at(java.time.LocalDateTime.now())
                            .build();
                    productRepo.insert(product);
                    Product lastProduct = productRepo.selectAll().stream()
                            .filter(p -> p.product_name.equals(productName) && p.product_desc.equals(productDesc))
                            .reduce((first, second) -> second).orElse(null);
                    int productId = lastProduct != null ? lastProduct.product_id : -1;

                    ProductVariant variant = ProductVariant.builder()
                            .product_id(productId)
                            .size_id(sizeId)
                            .color_id(colorId)
                            .sole_id(soleId)
                            .variant_sku(sku)
                            .variant_orig_price(price)
                            .variant_img_url("")
                            .build();
                    productVariantRepo.insert(variant);
                    count++;
                }
                loadTable();
                NotificationUtil.showSuccess(this, "Đã import thành công " + count + " sản phẩm từ Excel!");
            } catch (Exception ex) {
                NotificationUtil.showError(this, "Lỗi khi import sản phẩm từ Excel!", ex);
            }
        }
    }

    private void loadBrandCombo() {
        jComboBox1.removeAllItems();
        brandList = XQuery.getBeanList(Brand.class, "SELECT * FROM Brand");
        for (Brand b : brandList) {
            jComboBox1.addItem(b.brand_name);
        }
    }

    private void loadCategoryCombo() {
        jComboBox2.removeAllItems();
        categoryList = XQuery.getBeanList(Category.class, "SELECT * FROM Category");
        for (Category c : categoryList) {
            jComboBox2.addItem(c.category_name);
        }
    }

    private void loadSoleTypeCombo() {
        jComboBox3.removeAllItems();
        soleTypeList = XQuery.getBeanList(SoleType.class, "SELECT * FROM SoleType");
        for (SoleType s : soleTypeList) {
            jComboBox3.addItem(s.sole_name);
        }
    }

    private void loadSizeCombo() {
        jComboBox4.removeAllItems();
        sizeList = XQuery.getBeanList(Size.class, "SELECT * FROM Size");
        for (Size s : sizeList) {
            jComboBox4.addItem(s.size_value);
        }
    }

    private void loadColorCombo() {
        jComboBox5.removeAllItems();
        colorList = XQuery.getBeanList(Color.class, "SELECT * FROM Color");
        for (Color c : colorList) {
            jComboBox5.addItem(c.color_name);
        }
    }

    // Khi lấy dữ liệu từ form để lưu xuống DB, map từ tên sang ID
    private int getSelectedBrandId() {
        int idx = jComboBox1.getSelectedIndex();
        return idx >= 0 ? brandList.get(idx).brand_id : -1;
    }

    private int getSelectedCategoryId() {
        int idx = jComboBox2.getSelectedIndex();
        return idx >= 0 ? categoryList.get(idx).category_id : -1;
    }

    private int getSelectedSoleTypeId() {
        int idx = jComboBox3.getSelectedIndex();
        return idx >= 0 ? soleTypeList.get(idx).sole_id : -1;
    }

    private int getSelectedSizeId() {
        int idx = jComboBox4.getSelectedIndex();
        return idx >= 0 ? sizeList.get(idx).size_id : -1;
    }

    private int getSelectedColorId() {
        int idx = jComboBox5.getSelectedIndex();
        return idx >= 0 ? colorList.get(idx).color_id : -1;
    }

    private void loadTable() {
        List<ProductVariant> variantList = XQuery.getBeanList(ProductVariant.class, "SELECT * FROM ProductVariant");
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setColumnIdentifiers(new Object[]{
            "Tên SP", "Mã Sản Phẩm", "Mô tả", "Giá", "Loại", "Đế giày", "Size", "Màu", "Thương hiệu"
        });
        model.setRowCount(0);
        for (ProductVariant v : variantList) {
            Product p = productRepo.selectById(v.product_id);
            String productName = p != null ? p.product_name : "";
            String productDesc = p != null ? p.product_desc : "";
            String brandName = brandList.stream().filter(b -> b.brand_id == (p != null ? p.brand_id : -1)).map(b -> b.brand_name).findFirst().orElse("");
            String categoryName = categoryList.stream().filter(c -> c.category_id == (p != null ? p.category_id : -1)).map(c -> c.category_name).findFirst().orElse("");
            String sizeValue = sizeList.stream().filter(s -> s.size_id == v.size_id).map(s -> s.size_value).findFirst().orElse("");
            String colorName = colorList.stream().filter(c -> c.color_id == v.color_id).map(c -> c.color_name).findFirst().orElse("");
            String img = v.variant_img_url;
            double price = v.variant_orig_price;
            String sku = v.variant_sku;
            model.addRow(new Object[]{
                productName, // Tên SP
                sku, // Mã Sản Phẩm
                productDesc, // Mô tả
                price, // Giá
                categoryName, // Loại
                soleTypeList.stream().filter(s -> s.sole_id == v.sole_id).map(s -> s.sole_name).findFirst().orElse(""), // Đế giày
                sizeValue, // Size
                colorName, // Màu
                brandName // Thương hiệu
            });
        }
    }

    private void create() {
        try {
            // 1. Thêm Product
            ProductRequest req = getForm();
            Product product = Product.builder()
                    .brand_id(req.brand_id)
                    .category_id(req.category_id)
                    .product_name(req.product_name)
                    .product_desc(req.product_desc)
                    .product_create_at(java.time.LocalDateTime.now())
                    .product_updated_at(java.time.LocalDateTime.now())
                    .build();
            productRepo.insert(product);

            // Lấy productId vừa thêm
            Product lastProduct = productRepo.selectAll().stream()
                    .filter(p -> p.product_name.equals(product.product_name)
                    && p.product_desc.equals(product.product_desc))
                    .reduce((first, second) -> second).orElse(null);
            int productId = lastProduct != null ? lastProduct.product_id : -1;

            // 2. Thêm ProductVariant
            int sizeId = getSelectedSizeId();
            int colorId = getSelectedColorId();
            int soleId = getSelectedSoleTypeId();
            String sku = jTextField4.getText();
            double price = Double.parseDouble(jTextField3.getText());
            String img = "";

            ProductVariant variant = ProductVariant.builder()
                    .product_id(productId)
                    .size_id(sizeId)
                    .color_id(colorId)
                    .sole_id(soleId)
                    .variant_sku(sku)
                    .variant_orig_price(price)
                    .variant_img_url(img)
                    .build();
            productVariantRepo.insert(variant);

            loadTable();
            clearForm();
            NotificationUtil.showSuccess(this, "Thêm sản phẩm thành công!");
        } catch (Exception ex) {
            ex.printStackTrace();
            NotificationUtil.showError(this, "Thêm sản phẩm thất bại!", ex);
        }
    }

    private void Update() {
        if (selectedVariantId == null) {
            NotificationUtil.showInfo(this, "Vui lòng chọn sản phẩm để sửa!");
            return;
        }
        try {
            double price = Double.parseDouble(jTextField3.getText());
            int sizeId = getSelectedSizeId();
            int colorId = getSelectedColorId();
            int soleId = getSelectedSoleTypeId();
            String img = "";
            String sku = jTextField4.getText();

            List<ProductVariant> allVariants = productVariantRepo.selectAll();
            ProductVariant variant = allVariants.stream()
                    .filter(v -> v.variant_id == selectedVariantId)
                    .findFirst().orElse(null);

            if (variant != null) {
                variant.size_id = sizeId;
                variant.color_id = colorId;
                variant.sole_id = soleId;
                variant.variant_orig_price = price;
                variant.variant_img_url = img;
                variant.variant_sku = sku;
                productVariantRepo.update(variant);
                loadTable();
                clearForm();
                NotificationUtil.showSuccess(this, "Cập nhật sản phẩm thành công!");
            } else {
                NotificationUtil.showError(this, "Không tìm thấy sản phẩm để sửa!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            NotificationUtil.showError(this, "Cập nhật sản phẩm thất bại!", ex);
        }
    }

    private void Delete() {
        if (selectedVariantId == null) {
            NotificationUtil.showInfo(this, "Vui lòng chọn sản phẩm để xóa!");
            return;
        }

        // Sử dụng NotificationUtil.showDeleteConfirm thay vì JOptionPane
        if (NotificationUtil.showDeleteConfirm(this, "sản phẩm này")) {
            try {
                productVariantRepo.delete(selectedVariantId);
                loadTable();
                clearForm();
                NotificationUtil.showSuccess(this, "Xóa sản phẩm thành công!");
            } catch (Exception ex) {
                ex.printStackTrace();
                NotificationUtil.showError(this, "Xóa sản phẩm thất bại!", ex);
            }
        }
    }

    private ProductRequest getForm() {
        int brandId = getSelectedBrandId();
        int categoryId = getSelectedCategoryId();
        String productName = jTextField1.getText();
        String productDesc = jTextArea1.getText();
        return new ProductRequest(brandId, categoryId, productName, productDesc);
    }

    private void fillFromTable() {
        int row = jTable1.getSelectedRow();
        if (row >= 0) {
            ProductVariant v = getVariantFromRow(row);
            selectedVariantId = v != null ? v.variant_id : null;
            jTextField1.setText(jTable1.getValueAt(row, 0).toString()); // Tên SP
            jTextField4.setText(jTable1.getValueAt(row, 1).toString()); // Mã Sản Phẩm
            jTextArea1.setText(jTable1.getValueAt(row, 2).toString()); // Mô tả
            jTextField3.setText(jTable1.getValueAt(row, 3).toString()); // Giá
            jComboBox2.setSelectedItem(jTable1.getValueAt(row, 4).toString()); // Loại
            jComboBox3.setSelectedItem(jTable1.getValueAt(row, 5).toString()); // Đế giày
            jComboBox4.setSelectedItem(jTable1.getValueAt(row, 6).toString()); // Size
            jComboBox5.setSelectedItem(jTable1.getValueAt(row, 7).toString()); // Màu
            jComboBox1.setSelectedItem(jTable1.getValueAt(row, 8).toString()); // Thương hiệu
        } else {
            selectedVariantId = null;
        }
    }

    private ProductVariant getVariantFromRow(int row) {
        String productName = jTable1.getValueAt(row, 0).toString();
        String sizeValue = jTable1.getValueAt(row, 6).toString();
        String colorName = jTable1.getValueAt(row, 7).toString();

        int productId = productRepo.selectAll().stream()
                .filter(p -> p.product_name.equals(productName))
                .map(p -> p.product_id).findFirst().orElse(-1);
        int sizeId = sizeList.stream().filter(s -> s.size_value.equals(sizeValue)).map(s -> s.size_id).findFirst().orElse(-1);
        int colorId = colorList.stream().filter(c -> c.color_name.equals(colorName)).map(c -> c.color_id).findFirst().orElse(-1);

        return productVariantRepo.findByProductId(productId).stream()
                .filter(v -> v.size_id == sizeId && v.color_id == colorId)
                .findFirst().orElse(null);
    }

    private void clearForm() {
        jTextField1.setText("");
        jTextField3.setText("");
        jTextField4.setText("");
        jTextArea1.setText("");
        if (jComboBox1.getItemCount() > 0) {
            jComboBox1.setSelectedIndex(0);
        }
        if (jComboBox2.getItemCount() > 0) {
            jComboBox2.setSelectedIndex(0);
        }
        if (jComboBox3.getItemCount() > 0) {
            jComboBox3.setSelectedIndex(0);
        }
        if (jComboBox4.getItemCount() > 0) {
            jComboBox4.setSelectedIndex(0);
        }
        if (jComboBox5.getItemCount() > 0) {
            jComboBox5.setSelectedIndex(0);
        }
        selectedProductId = null;
        selectedVariantId = null;
    }

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(ProductManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ProductManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ProductManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ProductManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ProductManagement().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_add_image;
    private javax.swing.JButton btn_create;
    private javax.swing.JButton btn_delete;
    private javax.swing.JButton btn_dowload_excel;
    private javax.swing.JButton btn_import;
    private javax.swing.JButton btn_search;
    private javax.swing.JButton btn_update;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables

    // Hàm chọn ảnh và hiển thị preview lên lbl_imagePreview
    private void pickAndPreviewImage() {
        String path = com.DuAn1.volleyballshoes.app.utils.ImageUtil.pickImage(this);
        if (path != null) {
            selectedImagePath = path;
            javax.swing.ImageIcon icon = com.DuAn1.volleyballshoes.app.utils.ImageUtil.readAndResize(path, 120, 120);
            lbl_imagePreview.setIcon(icon);
        }
    }

}
