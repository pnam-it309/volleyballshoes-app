package com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewSanPham;

import com.DuAn1.volleyballshoes.app.entity.ProductVariant;
import com.DuAn1.volleyballshoes.app.dao.ProductVariantDAO;
import com.DuAn1.volleyballshoes.app.dao.impl.ProductVariantDAOImpl;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.multi.qrcode.QRCodeMultiReader;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.DuAn1.volleyballshoes.app.dao.ProductVariantDAO;
import com.DuAn1.volleyballshoes.app.dao.impl.ProductVariantDAOImpl;
import com.DuAn1.volleyballshoes.app.entity.ProductVariant;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.math.BigDecimal;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import javax.swing.JDialog;

public class ViewSanPhamChiTiet extends javax.swing.JPanel {

    // Pagination variables
    private int currentPage = 1;
    private int pageSize = 10;
    private int totalPages = 1;

    // DAO
    private final ProductVariantDAO productVariantDAO = new ProductVariantDAOImpl();

    public ViewSanPhamChiTiet() {
        initComponents();

    }

    private void loadProductVariants() {
        try {
            // Get paginated data from DAO
            List<ProductVariant> variants = productVariantDAO.findWithPagination(
                    currentPage,
                    pageSize,
                    "" // No filter for now
            );

            // Get total count for pagination
            int totalItems = productVariantDAO.count("");
            totalPages = (int) Math.ceil((double) totalItems / pageSize);

            // Update pagination label
            updatePaginationLabel();

            // Update table model with data
            updateTableModel(variants);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Lỗi khi tải dữ liệu biến thể sản phẩm: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
        }
    }

    private void updateTableModel(List<ProductVariant> variants) {
        DefaultTableModel model = (DefaultTableModel) tblSanPhamCon.getModel();
        model.setRowCount(0); // Clear existing data

        // Set column names if not already set
        if (model.getColumnCount() == 0) {
            model.setColumnIdentifiers(new String[]{
                "Mã SKU",
                "Kích thước",
                "Màu sắc",
                "Loại đế",
                "Giá bán",
                "Số lượng tồn"
            });
        }

        // Add data rows
        for (ProductVariant variant : variants) {
            model.addRow(new Object[]{
                variant.getVariantSku(),
                variant.getSizeId(), // Consider getting size name instead of ID
                variant.getColorId(), // Consider getting color name instead of ID
                variant.getSoleId(), // Consider getting sole type name instead of ID
                formatCurrency(variant.getVariantOrigPrice()),});
        }
    }

    /**
     * Format currency for display
     */
    private String formatCurrency(BigDecimal amount) {
        if (amount == null) {
            return "0 VNĐ";
        }
        return String.format("%,d VNĐ", amount.longValue());
    }

    /**
     * Update pagination label
     */
    private void updatePaginationLabel() {
        trang.setText(String.format("Trang %d/%d", currentPage, totalPages));
    }

    private void updatePaginationButtons() {
        btnNhoNhat.setEnabled(currentPage > 1);
        btnNho.setEnabled(currentPage > 1);
        btnLon.setEnabled(currentPage < totalPages);
        btnLonNhat.setEnabled(currentPage < totalPages);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblSanPhamCon = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        cbbLTL = new javax.swing.JComboBox<>();
        jPanel10 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        cbbKieu = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        txtLGT = new javax.swing.JTextField();
        btnNho = new javax.swing.JButton();
        trang = new javax.swing.JLabel();
        btnLon = new javax.swing.JButton();
        btnNhoNhat = new javax.swing.JButton();
        btnLonNhat = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnQuetQR = new javax.swing.JButton();
        btnThem1 = new javax.swing.JButton();
        btnXuatFile = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();
        btnTaiQR = new javax.swing.JButton();
        cbAll = new javax.swing.JCheckBox();
        btn_dowload_template = new javax.swing.JButton();
        btn_import_file_excel = new javax.swing.JButton();

        tblSanPhamCon.setModel(new javax.swing.table.DefaultTableModel(
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
        tblSanPhamCon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamConMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblSanPhamCon);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Lọc Sản Phẩm"));

        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        jLabel14.setText("Thể Loại");

        cbbLTL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbLTLActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(51, Short.MAX_VALUE)
                .addComponent(jLabel14)
                .addGap(35, 35, 35))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbbLTL, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addGap(18, 18, 18)
                .addComponent(cbbLTL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        jLabel17.setText("Giá");

        cbbKieu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { ">", "<", ">=", "<=", "=", " " }));

        jLabel19.setText("Giá Tiền:");

        txtLGT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLGTActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel17)
                .addGap(91, 91, 91))
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbbKieu, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtLGT, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbbKieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19)
                    .addComponent(txtLGT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        btnNho.setText("<");
        btnNho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhoActionPerformed(evt);
            }
        });

        trang.setText("jLabel13");

        btnLon.setText(">");
        btnLon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLonActionPerformed(evt);
            }
        });

        btnNhoNhat.setText("<<");
        btnNhoNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhoNhatActionPerformed(evt);
            }
        });

        btnLonNhat.setText(">>");
        btnLonNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLonNhatActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 255));
        jLabel1.setText("Thông tin sản phảm chi tiết");

        btnQuetQR.setBackground(new java.awt.Color(0, 102, 255));
        btnQuetQR.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnQuetQR.setForeground(new java.awt.Color(255, 255, 255));
        btnQuetQR.setText("Quét QR");
        btnQuetQR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuetQRActionPerformed(evt);
            }
        });

        btnThem1.setBackground(new java.awt.Color(0, 102, 255));
        btnThem1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThem1.setForeground(new java.awt.Color(255, 255, 255));
        btnThem1.setText("Thêm");
        btnThem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThem1ActionPerformed(evt);
            }
        });

        btnXuatFile.setBackground(new java.awt.Color(0, 102, 255));
        btnXuatFile.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnXuatFile.setForeground(new java.awt.Color(255, 255, 255));
        btnXuatFile.setText("Xuất File");
        btnXuatFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatFileActionPerformed(evt);
            }
        });

        btnLamMoi.setBackground(new java.awt.Color(0, 102, 255));
        btnLamMoi.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnLamMoi.setForeground(new java.awt.Color(255, 255, 255));
        btnLamMoi.setText("Làm mới");
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        btnTaiQR.setBackground(new java.awt.Color(0, 102, 255));
        btnTaiQR.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnTaiQR.setForeground(new java.awt.Color(255, 255, 255));
        btnTaiQR.setText("Tải QR");
        btnTaiQR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaiQRActionPerformed(evt);
            }
        });

        cbAll.setText("All");
        cbAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbAllActionPerformed(evt);
            }
        });

        btn_dowload_template.setBackground(new java.awt.Color(0, 102, 255));
        btn_dowload_template.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_dowload_template.setForeground(new java.awt.Color(255, 255, 255));
        btn_dowload_template.setText("Tải File Excel");
        btn_dowload_template.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dowload_templateActionPerformed(evt);
            }
        });

        btn_import_file_excel.setBackground(new java.awt.Color(0, 102, 255));
        btn_import_file_excel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_import_file_excel.setForeground(new java.awt.Color(255, 255, 255));
        btn_import_file_excel.setText("Thêm bằng file excel");
        btn_import_file_excel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_import_file_excelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(319, 319, 319)
                                .addComponent(btnNhoNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnNho)
                                .addGap(18, 18, 18)
                                .addComponent(trang)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnLon)
                                .addGap(18, 18, 18)
                                .addComponent(btnLonNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(369, 369, 369)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1))))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(btnXuatFile)
                .addGap(18, 18, 18)
                .addComponent(cbAll)
                .addGap(45, 45, 45)
                .addComponent(btn_dowload_template)
                .addGap(30, 30, 30)
                .addComponent(btn_import_file_excel)
                .addGap(75, 75, 75)
                .addComponent(btnThem1)
                .addGap(32, 32, 32)
                .addComponent(btnQuetQR)
                .addGap(46, 46, 46)
                .addComponent(btnTaiQR)
                .addGap(42, 42, 42)
                .addComponent(btnLamMoi)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel1)
                .addGap(31, 31, 31)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnQuetQR)
                    .addComponent(btnThem1)
                    .addComponent(btnXuatFile)
                    .addComponent(btnLamMoi)
                    .addComponent(btnTaiQR)
                    .addComponent(cbAll)
                    .addComponent(btn_dowload_template)
                    .addComponent(btn_import_file_excel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNho)
                    .addComponent(trang, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLon)
                    .addComponent(btnNhoNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLonNhat))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblSanPhamConMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamConMouseClicked
        int selectedRow = tblSanPhamCon.getSelectedRow();
        if (selectedRow >= 0) {
            try {
                // Lấy dữ liệu từ hàng được chọn
                String sku = tblSanPhamCon.getValueAt(selectedRow, 0).toString();
                String size = tblSanPhamCon.getValueAt(selectedRow, 1).toString();
                String color = tblSanPhamCon.getValueAt(selectedRow, 2).toString();
                String soleType = tblSanPhamCon.getValueAt(selectedRow, 3).toString();
                String price = tblSanPhamCon.getValueAt(selectedRow, 4).toString();
                String quantity = tblSanPhamCon.getValueAt(selectedRow, 5).toString();

                // Ghi log thông tin sản phẩm được chọn
                System.out.println("Đã chọn sản phẩm:");
                System.out.println("SKU: " + sku);
                System.out.println("Kích thước: " + size);
                System.out.println("Màu sắc: " + color);
                System.out.println("Loại đế: " + soleType);
                System.out.println("Giá: " + price);
                System.out.println("Số lượng: " + quantity);

                // Tạo mã QR cho sản phẩm
                String qrData = "SKU: " + sku + "\n"
                        + "Kích thước: " + size + "\n"
                        + "Màu sắc: " + color + "\n"
                        + "Loại đế: " + soleType + "\n"
                        + "Giá: " + price + " VNĐ" + "\n"
                        + "Số lượng tồn: " + quantity;

                try {
                    // Hiển thị thông báo thay vì tạo mã QR (vì chưa có thư viện)
                    javax.swing.JOptionPane.showMessageDialog(this,
                            "Thông tin sản phẩm đã được chọn.\n"
                            + "Dữ liệu QR sẽ được tạo sau khi tích hợp thư viện.\n\n"
                            + qrData,
                            "Thông tin sản phẩm",
                            javax.swing.JOptionPane.INFORMATION_MESSAGE);

                    // Ghi log dữ liệu QR
                    System.out.println("Dữ liệu QR sẽ được tạo:" + qrData);

                    // Lưu ý: Để tạo mã QR thực tế, cần thêm thư viện như ZXing
                    // Ví dụ: 
                    // String filePath = System.getProperty("java.io.tmpdir") + "qr_code_" + sku + ".png";
                    // generateQRCode(qrData, 200, 200, filePath);
                    // showQRCode(filePath);
                } catch (Exception e) {
                    // Log lỗi nếu có
                    System.err.println("Lỗi khi xử lý sự kiện chọn sản phẩm: " + e.getMessage());
                    e.printStackTrace();

                    // Hiển thị thông báo lỗi cho người dùng
                    JOptionPane.showMessageDialog(
                            this,
                            "Có lỗi xảy ra khi xử lý sản phẩm: " + e.getMessage(),
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE
                    );
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Lỗi khi đọc dữ liệu từ bảng: " + e.getMessage(),
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_tblSanPhamConMouseClicked

    private void btnNhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhoActionPerformed

        if (currentPage > 1) {
            currentPage--;
            loadProductVariants();
        }
    }//GEN-LAST:event_btnNhoActionPerformed

    private void btnLonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLonActionPerformed
        if (currentPage < totalPages) {
            currentPage++;
            loadProductVariants();
        }
    }//GEN-LAST:event_btnLonActionPerformed

    private void btnNhoNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhoNhatActionPerformed
        if (currentPage != 1) {
            currentPage = 1;
            loadProductVariants();
        }
    }//GEN-LAST:event_btnNhoNhatActionPerformed

    private void btnLonNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLonNhatActionPerformed
        if (currentPage != totalPages) {
            currentPage = totalPages;
            loadProductVariants();
        }
    }//GEN-LAST:event_btnLonNhatActionPerformed

    private void btnQuetQRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuetQRActionPerformed
        // Tạo và hiển thị cửa sổ quét QR code
        QuetQRSanPham qrScanner = new QuetQRSanPham();
        qrScanner.setVisible(true);

        // Căn giữa màn hình
        qrScanner.setLocationRelativeTo(null);
    }//GEN-LAST:event_btnQuetQRActionPerformed

    private void btnThem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThem1ActionPerformed
        // Tạo JDialog mới
        JDialog dialog = new JDialog();
        dialog.setTitle("Thêm sản phẩm mới");
        
        // Thêm panel vào dialog
        ViewThemSanPhamm themSanPhamPanel = new ViewThemSanPhamm();
        dialog.add(themSanPhamPanel);
        
        // Đặt kích thước dialog
        dialog.pack();
        
        // Căn giữa màn hình
        dialog.setLocationRelativeTo(null);
        
        // Hiển thị dialog
        dialog.setModal(true);
        dialog.setVisible(true);
    }//GEN-LAST:event_btnThem1ActionPerformed

    private void btnXuatFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatFileActionPerformed
        // Tạo hộp thoại chọn nơi lưu file
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Lưu dữ liệu ra file");

        // Đặt tên file mặc định
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        fileChooser.setSelectedFile(new File("danh_sach_san_pham_" + timestamp + ".csv"));

        // Chỉ cho phép lưu file .csv
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files (*.csv)", "csv");
        fileChooser.setFileFilter(filter);

        // Hiển thị hộp thoại lưu file
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            // Đảm bảo file có đuôi .csv
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".csv")) {
                fileToSave = new File(filePath + ".csv");
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileToSave))) {
                // Lấy model của bảng
                DefaultTableModel model = (DefaultTableModel) tblSanPhamCon.getModel();

                // Ghi tiêu đề cột
                for (int i = 0; i < model.getColumnCount(); i++) {
                    writer.write(model.getColumnName(i));
                    if (i < model.getColumnCount() - 1) {
                        writer.write(",");
                    }
                }
                writer.newLine();

                // Ghi dữ liệu từng dòng
                for (int i = 0; i < model.getRowCount(); i++) {
                    for (int j = 0; j < model.getColumnCount(); j++) {
                        Object value = model.getValueAt(i, j);
                        // Đảm bảo dữ liệu có dấu phẩy được đặt trong dấu ngoặc kép
                        if (value != null) {
                            String strValue = value.toString();
                            if (strValue.contains(",") || strValue.contains("\"") || strValue.contains("\n")) {
                                // Escape dấu ngoặc kép và đặt trong dấu ngoặc kép
                                strValue = strValue.replace("\"", "\"\"");
                                strValue = "\"" + strValue + "\"";
                            }
                            writer.write(strValue);
                        }
                        if (j < model.getColumnCount() - 1) {
                            writer.write(",");
                        }
                    }
                    writer.newLine();
                }

                JOptionPane.showMessageDialog(this,
                        "Xuất dữ liệu thành công!\nĐường dẫn: " + fileToSave.getAbsolutePath(),
                        "Thành công",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (IOException e) {
                JOptionPane.showMessageDialog(this,
                        "Lỗi khi xuất dữ liệu: " + e.getMessage(),
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnXuatFileActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        // Reset all filter fields
        cbbLTL.setSelectedIndex(0);
        cbbKieu.setSelectedIndex(0);
        txtLGT.setText("");

        // Reset pagination to first page
        currentPage = 1;

        // Reload product variants
        loadProductVariants();

        // Clear any selection in the table
        tblSanPhamCon.clearSelection();

        JOptionPane.showMessageDialog(this,
                "Đã làm mới dữ liệu thành công!",
                "Thành công",
                JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void btnTaiQRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaiQRActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn ảnh QR code");

        // Chỉ cho phép chọn file ảnh
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Image files", "jpg", "jpeg", "png", "gif", "bmp");
        fileChooser.setFileFilter(filter);

        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                // Đọc ảnh QR code
                BufferedImage image = ImageIO.read(selectedFile);

                // Tạo đối tượng BinaryBitmap từ ảnh
                LuminanceSource source = new BufferedImageLuminanceSource(image);
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

                // Giải mã QR code
                Result result = new MultiFormatReader().decode(bitmap);
                String qrText = result.getText();

                // Xử lý dữ liệu QR code
                processQRCodeData(qrText);

            } catch (IOException e) {
                JOptionPane.showMessageDialog(this,
                        "Lỗi khi đọc file ảnh: " + e.getMessage(),
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            } catch (NotFoundException e) {
                JOptionPane.showMessageDialog(this,
                        "Không tìm thấy mã QR trong ảnh",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Lỗi khi đọc mã QR: " + e.getMessage(),
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnTaiQRActionPerformed

    /**
     * Xử lý dữ liệu QR code đã đọc được
     *
     * @param qrData Dữ liệu từ QR code
     */
    private void processQRCodeData(String qrData) {
        try {
            // Phân tích dữ liệu QR code
            // Định dạng dự kiến: "SKU: SKU123\nKích thước: 40\nMàu sắc: Đen\n..."
            Map<String, String> qrMap = new HashMap<>();
            String[] lines = qrData.split("\\n");

            for (String line : lines) {
                String[] parts = line.split(": ", 2);
                if (parts.length == 2) {
                    qrMap.put(parts[0].trim(), parts[1].trim());
                }
            }

            // Lấy SKU từ dữ liệu QR code
            String sku = qrMap.get("SKU");
            if (sku != null && !sku.isEmpty()) {
                // Tìm sản phẩm trong bảng hiện tại
                boolean found = selectProductInTable(sku);

                if (!found) {
                    // Nếu không tìm thấy trong bảng hiện tại, thử tải lại dữ liệu với bộ lọc
                    currentPage = 1;
                    List<ProductVariant> variants = productVariantDAO.findWithPagination(
                            currentPage,
                            pageSize,
                            "variant_sku LIKE '%" + sku + "%'"
                    );

                    if (!variants.isEmpty()) {
                        // Cập nhật bảng với kết quả tìm kiếm
                        updateTableModel(variants);
                        // Chọn sản phẩm đầu tiên trong kết quả
                        selectProductInTable(sku);
                    } else {
                        JOptionPane.showMessageDialog(this,
                                "Không tìm thấy sản phẩm với SKU: " + sku,
                                "Thông báo",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "Không tìm thấy thông tin SKU trong mã QR",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi xử lý dữ liệu QR code: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /**
     * Hiển thị thông tin sản phẩm lên form
     *
     * @param variant Thông tin sản phẩm
     */
    private void showProductVariantInfo(ProductVariant variant) {
        // TODO: Cập nhật các trường thông tin sản phẩm lên form
        // Ví dụ:
        // txtMaSP.setText(variant.getSku());
        // txtTenSP.setText(variant.getProductName());
        // ...
    }

    /**
     * Tìm và chọn sản phẩm trong bảng dựa trên SKU
     *
     * @param sku Mã SKU cần tìm
     * @return true nếu tìm thấy và chọn được sản phẩm, false nếu không tìm thấy
     */
    private boolean selectProductInTable(String sku) {
        DefaultTableModel model = (DefaultTableModel) tblSanPhamCon.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            String rowSku = model.getValueAt(i, 0).toString(); // Cột 0 là cột SKU
            if (rowSku.equals(sku)) {
                // Chọn hàng tương ứng
                tblSanPhamCon.setRowSelectionInterval(i, i);
                // Cuộn đến hàng được chọn
                tblSanPhamCon.scrollRectToVisible(tblSanPhamCon.getCellRect(i, 0, true));
                return true; // Đã tìm thấy và chọn sản phẩm
            }
        }
        return false; // Không tìm thấy sản phẩm
    }

    private void cbbLTLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbLTLActionPerformed
        currentPage = 1;

        // Lấy loại thuộc tính được chọn
        String selectedType = cbbLTL.getSelectedItem().toString();
        String filter = "";

        // Tạo bộ lọc dựa trên loại được chọn
        if (!"Tất cả".equals(selectedType)) {
            filter = "attribute_type = '" + selectedType + "'";
        }

        try {
            // Lấy dữ liệu phân trang với bộ lọc
            List<ProductVariant> variants = productVariantDAO.findWithPagination(
                    currentPage,
                    pageSize,
                    filter
            );

            // Cập nhật tổng số trang
            int totalItems = productVariantDAO.count(filter);
            totalPages = (int) Math.ceil((double) totalItems / pageSize);

            // Cập nhật bảng với dữ liệu mới
            updateTableModel(variants);

            // Cập nhật trạng thái nút phân trang
            updatePaginationButtons();

            // Cập nhật nhãn phân trang
            updatePaginationLabel();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi tải dữ liệu: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

    }//GEN-LAST:event_cbbLTLActionPerformed

    private void cbAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbAllActionPerformed
        boolean isSelected = cbAll.isSelected();
        DefaultTableModel model = (DefaultTableModel) tblSanPhamCon.getModel();

        // Lặp qua tất cả các dòng trong bảng
        for (int i = 0; i < model.getRowCount(); i++) {
            // Cập nhật cột checkbox (giả sử cột checkbox là cột đầu tiên)
            model.setValueAt(isSelected, i, 0);

            // Nếu bạn muốn cập nhật trạng thái selected của dòng
            // tblSanPhamCon.changeSelection(i, 0, false, false);
        }

        // Cập nhật giao diện
        tblSanPhamCon.repaint();
    }//GEN-LAST:event_cbAllActionPerformed

    private void txtLGTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLGTActionPerformed
        currentPage = 1;

        // Lấy từ khóa tìm kiếm
        String keyword = txtLGT.getText().trim();
        String filter = "";

        // Tạo bộ lọc tìm kiếm nếu có từ khóa
        if (!keyword.isEmpty()) {
            filter = String.format("variant_sku LIKE '%%%s%%' OR variant_name LIKE '%%%s%%'",
                    keyword, keyword);
        }

        try {
            // Lấy dữ liệu phân trang với bộ lọc tìm kiếm
            List<ProductVariant> variants = productVariantDAO.findWithPagination(
                    currentPage,
                    pageSize,
                    filter
            );

            // Cập nhật tổng số trang
            int totalItems = productVariantDAO.count(filter);
            totalPages = (int) Math.ceil((double) totalItems / pageSize);

            // Cập nhật bảng với kết quả tìm kiếm
            updateTableModel(variants);

            // Cập nhật trạng thái nút phân trang
            updatePaginationButtons();

            // Cập nhật nhãn phân trang
            updatePaginationLabel();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi tìm kiếm: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_txtLGTActionPerformed

    private void txtTimKiemCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtTimKiemCaretUpdate
        try {
            // Lấy từ khóa tìm kiếm từ sự kiện (nếu có) hoặc từ trường tìm kiếm
            String keyword = "";
            if (evt != null && evt.getSource() instanceof javax.swing.JTextField) {
                javax.swing.JTextField searchField = (javax.swing.JTextField) evt.getSource();
                keyword = searchField.getText().trim();
            }

            String filter = "";

            // Tạo bộ lọc tìm kiếm nếu có từ khóa
            if (!keyword.isEmpty()) {
                filter = String.format("variant_sku LIKE '%%%s%%' OR variant_name LIKE '%%%s%%'",
                        keyword, keyword);
            }

            // Reset về trang đầu tiên khi tìm kiếm
            currentPage = 1;

            // Lấy dữ liệu phân trang với bộ lọc tìm kiếm
            List<ProductVariant> variants = productVariantDAO.findWithPagination(
                    currentPage,
                    pageSize,
                    filter
            );

            // Cập nhật tổng số trang
            int totalItems = productVariantDAO.count(filter);
            totalPages = (int) Math.ceil((double) totalItems / pageSize);

            // Cập nhật bảng với kết quả tìm kiếm
            updateTableModel(variants);

            // Cập nhật trạng thái nút phân trang
            updatePaginationButtons();

            // Cập nhật nhãn phân trang
            updatePaginationLabel();

        } catch (Exception e) {
            // Không hiển thị thông báo lỗi để tránh làm phiền người dùng khi đang gõ
            e.printStackTrace();
        }
    }//GEN-LAST:event_txtTimKiemCaretUpdate

    private void btn_dowload_templateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dowload_templateActionPerformed
        // Tạo hộp thoại chọn nơi lưu file
        javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file mẫu");
        fileChooser.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == javax.swing.JFileChooser.APPROVE_OPTION) {
            try {
                // Tạo đường dẫn đầy đủ đến file mẫu
                File folder = fileChooser.getSelectedFile();
                String templatePath = folder.getAbsolutePath() + File.separator + "Mau_Import_SanPham.xlsx";

                // Tạo workbook mới
                try (org.apache.poi.xssf.usermodel.XSSFWorkbook workbook = new org.apache.poi.xssf.usermodel.XSSFWorkbook()) {
                    // Tạo sheet
                    org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet("Mau_Import");

                    // Tạo style cho header
                    org.apache.poi.ss.usermodel.CellStyle headerStyle = workbook.createCellStyle();
                    org.apache.poi.ss.usermodel.Font font = workbook.createFont();
                    font.setBold(true);
                    headerStyle.setFont(font);

                    // Tạo header row
                    org.apache.poi.ss.usermodel.Row headerRow = sheet.createRow(0);
                    String[] headers = {"Mã SKU", "Tên biến thể", "ID Sản phẩm", "ID Màu sắc",
                        "ID Kích thước", "ID Loại đế", "Giá gốc", "Giá khuyến mãi",
                        "Số lượng tồn", "Trạng thái (1: Hoạt động, 0: Ngừng bán)"};

                    for (int i = 0; i < headers.length; i++) {
                        org.apache.poi.ss.usermodel.Cell cell = headerRow.createCell(i);
                        cell.setCellValue(headers[i]);
                        cell.setCellStyle(headerStyle);
                    }

                    // Tạo dòng hướng dẫn
                    org.apache.poi.ss.usermodel.Row instructionRow1 = sheet.createRow(1);
                    instructionRow1.createCell(0).setCellValue("VD: SP001");
                    instructionRow1.createCell(1).setCellValue("VD: Giày bóng chuyền đen 42");
                    instructionRow1.createCell(2).setCellValue("1");
                    instructionRow1.createCell(3).setCellValue("1");
                    instructionRow1.createCell(4).setCellValue("1");
                    instructionRow1.createCell(5).setCellValue("1");
                    instructionRow1.createCell(6).setCellValue("500000");
                    instructionRow1.createCell(7).setCellValue("450000");
                    instructionRow1.createCell(8).setCellValue("50");
                    instructionRow1.createCell(9).setCellValue("1");

                    // Tự động điều chỉnh độ rộng cột
                    for (int i = 0; i < headers.length; i++) {
                        sheet.autoSizeColumn(i);
                    }

                    // Lưu file
                    try (java.io.FileOutputStream outputStream = new java.io.FileOutputStream(templatePath)) {
                        workbook.write(outputStream);
                    }

                    // Thông báo thành công
                    JOptionPane.showMessageDialog(this,
                            "Đã tạo file mẫu thành công tại: " + templatePath,
                            "Thành công",
                            JOptionPane.INFORMATION_MESSAGE);
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Lỗi khi tạo file mẫu: " + e.getMessage(),
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_btn_dowload_templateActionPerformed

    private void btn_import_file_excelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_import_file_excelActionPerformed
        // Tạo hộp thoại chọn file
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn file Excel cần import");

        // Chỉ cho phép chọn file Excel
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Excel Files", "xls", "xlsx");
        fileChooser.setFileFilter(filter);

        int returnValue = fileChooser.showOpenDialog(this);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            // Xác nhận trước khi import
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Bạn có chắc chắn muốn import dữ liệu từ file: " + selectedFile.getName() + "?",
                    "Xác nhận Import",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                try (FileInputStream fis = new FileInputStream(selectedFile); XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

                    // Lấy sheet đầu tiên
                    Sheet sheet = workbook.getSheetAt(0);

                    // Đếm số dòng dữ liệu (bỏ qua dòng tiêu đề)
                    int rowCount = sheet.getPhysicalNumberOfRows() - 1;

                    if (rowCount <= 0) {
                        JOptionPane.showMessageDialog(this,
                                "File Excel không có dữ liệu để import!",
                                "Cảnh báo",
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    int successCount = 0;
                    int failCount = 0;
                    StringBuilder errorMessages = new StringBuilder();

                    // Bắt đầu import từ dòng thứ 2 (index 1) vì dòng 1 là tiêu đề
                    for (int i = 1; i <= rowCount; i++) {
                        Row row = sheet.getRow(i);

                        try {
                            // Bỏ qua dòng trống
                            if (row == null) {
                                continue;
                            }

                            // Đọc dữ liệu từ các ô
                            String sku = getCellValueAsString(row.getCell(0));
                            String variantName = getCellValueAsString(row.getCell(1));
                            int productId = (int) Double.parseDouble(getCellValueAsString(row.getCell(2)));
                            int colorId = (int) Double.parseDouble(getCellValueAsString(row.getCell(3)));
                            int sizeId = (int) Double.parseDouble(getCellValueAsString(row.getCell(4)));
                            int soleId = (int) Double.parseDouble(getCellValueAsString(row.getCell(5)));
                            double originalPrice = Double.parseDouble(getCellValueAsString(row.getCell(6)));

                            // Tạo đối tượng ProductVariant
                            ProductVariant variant = new ProductVariant();

                            // Thiết lập các thuộc tính từ dữ liệu Excel
                            variant.setVariantSku(sku);
                            variant.setProductId(productId);
                            variant.setColorId(colorId);
                            variant.setSizeId(sizeId);
                            variant.setSoleId(soleId);
                            variant.setVariantOrigPrice(BigDecimal.valueOf(originalPrice));

                            // Lưu vào CSDL
                            // Lưu ý: Hiện tại DAO chưa hỗ trợ tìm kiếm theo SKU, nên tạm thời luôn tạo mới
                            // Có thể cần bổ sung phương thức findBySku vào ProductVariantDAO nếu cần
                            productVariantDAO.create(variant);

                            successCount++;

                        } catch (Exception e) {
                            failCount++;
                            errorMessages.append("Lỗi dòng ").append(i + 1).append(": ").append(e.getMessage()).append("\n");
                            e.printStackTrace();
                        }
                    }

                    // Hiển thị kết quả import
                    StringBuilder resultMessage = new StringBuilder();
                    resultMessage.append("Kết quả import:\n");
                    resultMessage.append("- Thành công: ").append(successCount).append(" dòng\n");
                    resultMessage.append("- Thất bại: ").append(failCount).append(" dòng\n");

                    if (failCount > 0) {
                        resultMessage.append("\nChi tiết lỗi:\n").append(errorMessages.toString());
                    }

                    JOptionPane.showMessageDialog(
                            this,
                            resultMessage.toString(),
                            "Kết quả Import",
                            failCount > 0 ? JOptionPane.WARNING_MESSAGE : JOptionPane.INFORMATION_MESSAGE
                    );

                    // Làm mới dữ liệu sau khi import
                    loadProductVariants();

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Lỗi khi đọc file Excel: " + e.getMessage(),
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE
                    );
                    e.printStackTrace();
                }
            }
        }
    }//GEN-LAST:event_btn_import_file_excelActionPerformed

    // Hàm hỗ trợ đọc giá trị ô Excel
    private String getCellValueAsString(org.apache.poi.ss.usermodel.Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    // Xử lý số nguyên
                    double value = cell.getNumericCellValue();
                    if (value == (long) value) {
                        return String.format("%d", (long) value);
                    } else {
                        return String.format("%s", value);
                    }
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
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
            java.util.logging.Logger.getLogger(ViewSanPhamChiTiet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewSanPhamChiTiet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewSanPhamChiTiet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewSanPhamChiTiet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewSanPhamChiTiet().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnLon;
    private javax.swing.JButton btnLonNhat;
    private javax.swing.JButton btnNho;
    private javax.swing.JButton btnNhoNhat;
    private javax.swing.JButton btnQuetQR;
    private javax.swing.JButton btnTaiQR;
    private javax.swing.JButton btnThem1;
    private javax.swing.JButton btnXuatFile;
    private javax.swing.JButton btn_dowload_template;
    private javax.swing.JButton btn_import_file_excel;
    private javax.swing.JCheckBox cbAll;
    private javax.swing.JComboBox<String> cbbKieu;
    private javax.swing.JComboBox<String> cbbLTL;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblSanPhamCon;
    private javax.swing.JLabel trang;
    private javax.swing.JTextField txtLGT;
    // End of variables declaration//GEN-END:variables

}
