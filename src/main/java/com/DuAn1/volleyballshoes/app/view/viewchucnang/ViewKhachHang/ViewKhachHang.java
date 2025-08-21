package com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewKhachHang;

import com.DuAn1.volleyballshoes.app.controller.CustomerController;
import com.DuAn1.volleyballshoes.app.dto.request.CustomerCreateRequest;
import com.DuAn1.volleyballshoes.app.entity.Customer;
import com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewBanHang.ViewThemKhachHang;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ViewKhachHang extends javax.swing.JPanel {

    private Integer selectedCustomerId = null;

    public ViewKhachHang() {
        // Initialize table model first
        String[] customerColumns = {"Mã KH", "Tên KH", "Số ĐT", "Email"};
        customerTableModel = new DefaultTableModel(customerColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa trực tiếp trên bảng
            }
        };
        
        initComponents();
        initTable();
        loadCustomerData();
    }

    private final CustomerController customerController = new CustomerController();
    private DefaultTableModel customerTableModel;
    private DefaultTableModel orderHistoryTableModel;

    private void initTable() {
        // Use the already initialized customerTableModel
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
        System.out.println("\n=== Bắt đầu tải dữ liệu khách hàng ===");
        
        try {
            // Clear existing data
            if (customerTableModel == null) {
                System.err.println("Lỗi: customerTableModel chưa được khởi tạo!");
                return;
            }
            customerTableModel.setRowCount(0);
            
            // Get customers from controller
            System.out.println("Đang gọi getAllCustomers() từ controller...");
            List<Customer> customers = customerController.getAllCustomers();
            
            if (customers == null) {
                System.err.println("Lỗi: Danh sách khách hàng trả về là null");
                JOptionPane.showMessageDialog(this, 
                    "Lỗi kết nối cơ sở dữ liệu. Vui lòng kiểm tra log để biết thêm chi tiết.", 
                    "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            System.out.println("Đã nhận được " + customers.size() + " khách hàng từ cơ sở dữ liệu");
            
            if (customers.isEmpty()) {
                System.out.println("Cảnh báo: Không tìm thấy khách hàng nào trong cơ sở dữ liệu");
                JOptionPane.showMessageDialog(this, 
                    "Không tìm thấy dữ liệu khách hàng.", 
                    "Thông báo", 
                    JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            // Add customers to table
            int addedCount = 0;
            for (Customer customer : customers) {
                try {
                    if (customer != null) {
                        System.out.println(String.format("Thêm khách hàng [%s] %s - %s", 
                            customer.getCustomerCode(), 
                            customer.getCustomerFullName(),
                            customer.getCustomerPhone()));
                            
                        Object[] row = new Object[]{
                            customer.getCustomerCode() != null ? customer.getCustomerCode() : "",
                            customer.getCustomerFullName() != null ? customer.getCustomerFullName() : "",
                            customer.getCustomerPhone() != null ? customer.getCustomerPhone() : "",
                            customer.getCustomerEmail() != null ? customer.getCustomerEmail() : ""
                        };
                        customerTableModel.addRow(row);
                        addedCount++;
                    } else {
                        System.err.println("Cảnh báo: Phát hiện đối tượng Customer null trong danh sách");
                    }
                } catch (Exception e) {
                    System.err.println("Lỗi khi thêm khách hàng vào bảng: " + e.getMessage());
                    e.printStackTrace();
                }
            }
            
            System.out.println("Đã thêm thành công " + addedCount + "/" + customers.size() + " khách hàng vào bảng");
            
        } catch (Exception e) {
            System.err.println("Lỗi trong quá trình tải dữ liệu khách hàng: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Lỗi khi tải dữ liệu khách hàng: " + e.getMessage(),
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
        } finally {
            System.out.println("=== Kết thúc tải dữ liệu khách hàng ===\n");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        tbl_Them = new javax.swing.JButton();
        tbl_Xoa = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txt_ma = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txt_sdt = new javax.swing.JTextField();
        txt_email = new javax.swing.JTextField();
        tbl_lamMoi = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txt_nhapTim = new javax.swing.JTextField();
        tbl_timkiem = new javax.swing.JButton();
        txt_ten = new javax.swing.JTextField();
        tbl_capNhat = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_bangls = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_bang = new javax.swing.JTable();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 255));
        jLabel1.setText("Quản Lý Khách Hàng");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông Tin Khách Hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 18))); // NOI18N
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        tbl_Them.setBackground(new java.awt.Color(0, 51, 255));
        tbl_Them.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tbl_Them.setForeground(new java.awt.Color(255, 255, 255));
        tbl_Them.setText("Thêm");
        tbl_Them.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbl_ThemActionPerformed(evt);
            }
        });

        tbl_Xoa.setBackground(new java.awt.Color(0, 51, 255));
        tbl_Xoa.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tbl_Xoa.setForeground(new java.awt.Color(255, 255, 255));
        tbl_Xoa.setText("Xóa");
        tbl_Xoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbl_XoaActionPerformed(evt);
            }
        });

        jLabel3.setText("Mã Khách Hàng");

        jLabel4.setText("Tên Khách Hàng");

        jLabel7.setText("Email");

        jLabel9.setText("Số Điện Thoại");

        tbl_lamMoi.setBackground(new java.awt.Color(0, 51, 255));
        tbl_lamMoi.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tbl_lamMoi.setForeground(new java.awt.Color(255, 255, 255));
        tbl_lamMoi.setText("Làm Mới");
        tbl_lamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbl_lamMoiActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Tìm Kiếm"));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));

        jLabel2.setText("Nhập Tên  Cần Tìm");

        txt_nhapTim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nhapTimActionPerformed(evt);
            }
        });

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
                .addComponent(txt_nhapTim, javax.swing.GroupLayout.PREFERRED_SIZE, 549, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(txt_nhapTim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tbl_timkiem))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        txt_ten.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_tenActionPerformed(evt);
            }
        });

        tbl_capNhat.setBackground(new java.awt.Color(0, 51, 255));
        tbl_capNhat.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tbl_capNhat.setForeground(new java.awt.Color(255, 255, 255));
        tbl_capNhat.setText("Cập Nhật");
        tbl_capNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbl_capNhatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(tbl_Them))
                .addGap(72, 72, 72)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(tbl_capNhat)
                        .addGap(60, 60, 60)
                        .addComponent(tbl_lamMoi)
                        .addGap(39, 39, 39)
                        .addComponent(tbl_Xoa))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_ten, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                            .addComponent(txt_ma))
                        .addGap(39, 39, 39)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel7))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_sdt, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 833, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 69, Short.MAX_VALUE))
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
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tbl_Them)
                    .addComponent(tbl_lamMoi)
                    .addComponent(tbl_capNhat)
                    .addComponent(tbl_Xoa))
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

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
        jScrollPane3.setViewportView(tbl_bang);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(370, 370, 370)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane3)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1))
                        .addContainerGap(37, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel1)
                .addGap(35, 35, 35)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(126, 126, 126))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tbl_ThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbl_ThemActionPerformed
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
            DefaultTableModel model = (DefaultTableModel) tbl_bang.getModel();
            Object ten = null;
            Object email = null;
            Object sdt = null;
            model.addRow(new Object[]{ten, sdt, email});

            JOptionPane.showMessageDialog(this,
                    "Thêm khách hàng thành công!",
                    "Thành công", JOptionPane.INFORMATION_MESSAGE);
            lamMoiForm();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi thêm khách hàng: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_tbl_ThemActionPerformed

    private void tbl_XoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbl_XoaActionPerformed
        int row = tbl_bang.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng cần xóa!");
            return;
        }

        String code = tbl_bang.getValueAt(row, 0).toString();

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn xóa khách hàng này? Hành động không thể hoàn tác!",
                "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            Customer customer = customerController.getCustomerByCode(code);
            if (customer != null) {
                customerController.deleteCustomer(customer.getCustomerId());
                JOptionPane.showMessageDialog(this, "Xóa thành công!");
                loadCustomerData();
                clearFields();
                selectedCustomerId = null;
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi xóa: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_tbl_XoaActionPerformed

    private void tbl_timkiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbl_timkiemActionPerformed
        String keyword = txt_nhapTim.getText().trim();
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
                    customer.getCustomerFullName(),
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

    private void tbl_lamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbl_lamMoiActionPerformed
        clearFields();
        loadCustomerData();
        lamMoiForm();
    }//GEN-LAST:event_tbl_lamMoiActionPerformed

    private void txt_tenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_tenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_tenActionPerformed

    private void tbl_bangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_bangMouseClicked
        // TODO add your handling code here:
        int selectedRow = tbl_bang.getSelectedRow();
        if (selectedRow >= 0) {
            try {
                String customerCode = tbl_bang.getValueAt(selectedRow, 0).toString();
                Customer customer = customerController.getCustomerByCode(customerCode);
                if (customer != null) {
                    txt_ma.setText(customer.getCustomerCode() != null ? customer.getCustomerCode() : "");
                    txt_ten.setText(customer.getCustomerFullName() != null ? customer.getCustomerFullName() : "");
                    txt_sdt.setText(customer.getCustomerPhone() != null ? customer.getCustomerPhone() : "");
                    txt_email.setText(customer.getCustomerEmail() != null ? customer.getCustomerEmail() : "");
                    selectedCustomerId = customer.getCustomerId();
                    loadOrderHistory(customer.getCustomerId());
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Lỗi khi tải thông tin khách hàng: " + e.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_tbl_bangMouseClicked

    private void tbl_capNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbl_capNhatActionPerformed
        // TODO add your handling code here:
        if (selectedCustomerId == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng cần cập nhật!");
            return;
        }

        String code = txt_ma.getText().trim();
        String name = txt_ten.getText().trim();
        String phone = txt_sdt.getText().trim();
        String email = txt_email.getText().trim();

        if (code.isEmpty() || name.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin bắt buộc (mã, tên, số ĐT)!");
            return;
        }

        // Validate phone là số (không chứa ký tự đặc biệt như ')
        if (!phone.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại phải là số nguyên!");
            return;
        }

        // Optional: validate email format
        // if (!email.isEmpty() && !email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
        //     JOptionPane.showMessageDialog(this, "Email không hợp lệ!");
        //     return;
        // }
        Customer customer = new Customer();
        customer.setCustomerId(selectedCustomerId);
        customer.setCustomerCode(code);
        customer.setCustomerFullName(name);
        customer.setCustomerPhone(phone);
        customer.setCustomerEmail(email);

        try {
            customerController.updateCustomer(customer);
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
            loadCustomerData();
            clearFields();
            selectedCustomerId = null;
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Lỗi", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi cập nhật: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_tbl_capNhatActionPerformed

    private void txt_nhapTimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nhapTimActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nhapTimActionPerformed
    /**
     * Loads order history for a specific customer
     *
     * @param customerId The ID of the customer to load order history for
     */
    private void loadOrderHistory(Integer customerId) {
        try {
            // Clear existing data
            orderHistoryTableModel.setRowCount(0);

            // Get orders for this customer (you'll need to implement this in your OrderController)
            // List<Order> orders = orderController.getOrdersByCustomerId(customerId);
            // For now, we'll just show a message
            if (customerId != null) {
                // This is a placeholder - replace with actual order data
                // for (Order order : orders) {
                //     orderHistoryTableModel.addRow(new Object[]{
                //         order.getOrderCode(),
                //         order.getOrderDate(),
                //         String.format("%,.0f VNĐ", order.getTotalAmount()),
                //         order.getStatus()
                //     });
                // }

                // For now, just show a message
                if (orderHistoryTableModel.getRowCount() == 0) {
                    orderHistoryTableModel.addRow(new Object[]{
                        "Không có đơn hàng nào", "", "", ""
                    });
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi tải lịch sử đơn hàng: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        txt_ma.setText("");
        txt_ten.setText("");
        txt_sdt.setText("");
        txt_email.setText("");
        txt_nhapTim.setText("");
        tbl_bang.clearSelection();
        orderHistoryTableModel.setRowCount(0);
        selectedCustomerId = null;
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
            java.util.logging.Logger.getLogger(ViewKhachHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewKhachHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewKhachHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewKhachHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewKhachHang().setVisible(true);
            }
        });
    }

    private void lamMoiForm() {
        txt_ma.setText("");
        txt_ten.setText("");
        txt_sdt.setText("");
        txt_nhapTim.setText("");
        tbl_bang.clearSelection();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
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
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton tbl_Them;
    private javax.swing.JButton tbl_Xoa;
    private javax.swing.JTable tbl_bang;
    private javax.swing.JTable tbl_bangls;
    private javax.swing.JButton tbl_capNhat;
    private javax.swing.JButton tbl_lamMoi;
    private javax.swing.JButton tbl_timkiem;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_ma;
    private javax.swing.JTextField txt_nhapTim;
    private javax.swing.JTextField txt_sdt;
    private javax.swing.JTextField txt_ten;
    // End of variables declaration//GEN-END:variables

}
