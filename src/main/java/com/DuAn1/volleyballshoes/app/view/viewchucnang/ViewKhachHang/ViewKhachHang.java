package com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewKhachHang;

import com.DuAn1.volleyballshoes.app.controller.CustomerController;
import com.DuAn1.volleyballshoes.app.dto.request.CustomerCreateRequest;
import com.DuAn1.volleyballshoes.app.entity.Customer;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ViewKhachHang extends javax.swing.JPanel {

    public ViewKhachHang() {
        initComponents();
        initTable();
        loadCustomerData();
    }

    private final CustomerController customerController = new CustomerController();
    private DefaultTableModel customerTableModel;
    private DefaultTableModel orderHistoryTableModel;

    private void initTable() {
        // Khởi tạo model cho bảng khách hàng
        String[] customerColumns = {"Mã KH", "Tên KH", "Số ĐT", "Email"};
        customerTableModel = new DefaultTableModel(customerColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa trực tiếp trên bảng
            }
        };
        tbl_bang.setModel(customerTableModel);

        // Khởi tạo model cho bảng lịch sử đơn hàng
        String[] orderColumns = {"Mã ĐH", "Ngày tạo", "Tổng tiền", "Trạng thái"};
        orderHistoryTableModel = new DefaultTableModel(orderColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tbl_bangls.setModel(orderHistoryTableModel);
    }

    private void loadCustomerData() {
        try {
            customerTableModel.setRowCount(0); // Xóa dữ liệu cũ
            List<Customer> customers = customerController.getAllCustomers();
    
            for (Customer customer : customers) {
                customerTableModel.addRow(new Object[]{
                    customer.getCustomerCode(),    // Mã KH
                    customer.getCustomerUsername(), // Tên KH
                    customer.getCustomerPhone(),   // SĐT
                    customer.getCustomerEmail()    // Email
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi tải dữ liệu khách hàng: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        tbl_them = new javax.swing.JButton();
        tbl_sửa = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txt_ma = new javax.swing.JTextField();
        txt_ten = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txt_sdt = new javax.swing.JTextField();
        txt_email = new javax.swing.JTextField();
        tbl_lammoi = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txt_nhạptim = new javax.swing.JTextField();
        tbl_timkiem = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_bang = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_bangls = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 255));
        jLabel1.setText("Quản Lý Khách Hàng");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông Tin Khách Hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 18))); // NOI18N
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        tbl_them.setBackground(new java.awt.Color(0, 51, 255));
        tbl_them.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tbl_them.setForeground(new java.awt.Color(255, 255, 255));
        tbl_them.setText("Thêm");
        tbl_them.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbl_themActionPerformed(evt);
            }
        });

        tbl_sửa.setBackground(new java.awt.Color(0, 51, 255));
        tbl_sửa.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tbl_sửa.setForeground(new java.awt.Color(255, 255, 255));
        tbl_sửa.setText("Cập Nhật");
        tbl_sửa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbl_sửaActionPerformed(evt);
            }
        });

        jLabel3.setText("Mã Khách Hàng");

        txt_ten.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_tenActionPerformed(evt);
            }
        });

        jLabel4.setText("Tên Khách Hàng");

        jLabel7.setText("Email");

        jLabel9.setText("Số Điện Thoại");

        tbl_lammoi.setBackground(new java.awt.Color(0, 51, 255));
        tbl_lammoi.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tbl_lammoi.setForeground(new java.awt.Color(255, 255, 255));
        tbl_lammoi.setText("Làm Mới");
        tbl_lammoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbl_lammoiActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Tìm Kiếm"));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));

        jLabel2.setText("Nhập Tên  Cần Tìm");

        tbl_timkiem.setBackground(new java.awt.Color(0, 51, 255));
        tbl_timkiem.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tbl_timkiem.setForeground(new java.awt.Color(255, 255, 255));
        tbl_timkiem.setText("Tìm Kiếm");
        tbl_timkiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbl_timkiemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(txt_nhạptim, javax.swing.GroupLayout.PREFERRED_SIZE, 549, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(tbl_timkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(114, 114, 114))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_nhạptim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tbl_timkiem))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jButton1.setBackground(new java.awt.Color(0, 51, 255));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Xuất Danh Sách");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(tbl_them)
                                .addGap(71, 71, 71)
                                .addComponent(tbl_sửa)
                                .addGap(74, 74, 74)
                                .addComponent(tbl_lammoi)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel4))
                                    .addGap(72, 72, 72)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(txt_ma)
                                        .addComponent(txt_ten, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE))
                                    .addGap(139, 139, 139)
                                    .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(58, 58, 58))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel9)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txt_sdt, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jLabel7))
                                    .addGap(40, 40, 40)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 833, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56)
                        .addComponent(jButton1)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_ma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9)
                        .addComponent(txt_sdt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(53, 53, 53)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel7)
                            .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_ten, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tbl_them)
                    .addComponent(tbl_sửa)
                    .addComponent(tbl_lammoi))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(27, 27, 27))))
        );

        tbl_bang.setModel(new javax.swing.table.DefaultTableModel(
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
        tbl_bang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_bangMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_bang);

        tbl_bangls.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tbl_bangls);

        jLabel10.setText("Lịch Sử Mua Hàng");

        jLabel11.setText("Thông tin khách Hàng ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 709, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(76, 76, 76))
            .addGroup(layout.createSequentialGroup()
                .addGap(370, 370, 370)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(351, 351, 351))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel1)
                .addGap(35, 35, 35)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(105, 105, 105))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // Export customer list to Excel

    }//GEN-LAST:event_jButton1ActionPerformed

    private void tbl_themActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbl_themActionPerformed
        try {
            // Kiểm tra dữ liệu đầu vào
            if (txt_ma.getText().trim().isEmpty()
                    || txt_ten.getText().trim().isEmpty()
                    || txt_sdt.getText().trim().isEmpty()
                    || txt_email.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng điền đầy đủ thông tin khách hàng!",
                        "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Tạo đối tượng yêu cầu tạo khách hàng mới
            CustomerCreateRequest request = new CustomerCreateRequest();
            request.setCustomerCode(txt_ma.getText().trim());
            request.setCustomerFullName(txt_ten.getText().trim());
            request.setCustomerSdt(txt_sdt.getText().trim());
            request.setCustomerEmail(txt_email.getText().trim());

            // Gọi controller để thêm khách hàng
            customerController.createCustomer(request);

            // Làm mới dữ liệu
            loadCustomerData();
            clearFields();

            JOptionPane.showMessageDialog(this,
                    "Thêm khách hàng thành công!",
                    "Thành công", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi thêm khách hàng: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_tbl_themActionPerformed

    private void tbl_sửaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbl_sửaActionPerformed
        try {
            int selectedRow = tbl_bang.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng chọn khách hàng cần cập nhật!",
                        "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Lấy mã khách hàng từ dòng được chọn
            String customerCode = tbl_bang.getValueAt(selectedRow, 0).toString();

            // Cập nhật thông tin khách hàng
            Customer updatedCustomer = new Customer();
            updatedCustomer.setCustomerCode(customerCode);
            updatedCustomer.setCustomerUsername(txt_ten.getText().trim());
            updatedCustomer.setCustomerPhone(txt_sdt.getText().trim());
            updatedCustomer.setCustomerEmail(txt_email.getText().trim());

            // Gọi controller để cập nhật
            customerController.updateCustomer(updatedCustomer);

            // Làm mới dữ liệu
            loadCustomerData();
            clearFields();

            JOptionPane.showMessageDialog(this,
                    "Cập nhật thông tin khách hàng thành công!",
                    "Thành công", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi cập nhật khách hàng: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_tbl_sửaActionPerformed

    private void tbl_timkiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbl_timkiemActionPerformed
        String keyword = txt_nhạptim.getText().trim();
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng nhập từ khóa tìm kiếm!",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            customerTableModel.setRowCount(0); // Xóa dữ liệu cũ
            List<Customer> searchResults = customerController.searchCustomers(keyword);

            if (searchResults.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Không tìm thấy khách hàng phù hợp!",
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            for (Customer customer : searchResults) {
                customerTableModel.addRow(new Object[]{
                    customer.getCustomerCode(),
                    customer.getCustomerUsername(),
                    customer.getCustomerPhone(),
                    customer.getCustomerEmail()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi tìm kiếm: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_tbl_timkiemActionPerformed

    private void tbl_lammoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbl_lammoiActionPerformed
        clearFields();
        loadCustomerData();
    }//GEN-LAST:event_tbl_lammoiActionPerformed
    private void clearFields() {
        txt_ma.setText("");
        txt_ten.setText("");
        txt_sdt.setText("");
        txt_email.setText("");
        txt_nhạptim.setText("");
        tbl_bang.clearSelection();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tbl_bang;
    private javax.swing.JTable tbl_bangls;
    private javax.swing.JButton tbl_lammoi;
    private javax.swing.JButton tbl_sửa;
    private javax.swing.JButton tbl_them;
    private javax.swing.JButton tbl_timkiem;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_ma;
    private javax.swing.JTextField txt_nhạptim;
    private javax.swing.JTextField txt_sdt;
    private javax.swing.JTextField txt_ten;
    // End of variables declaration//GEN-END:variables

}
