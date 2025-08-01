package com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewHoaDon;

import com.DuAn1.volleyballshoes.app.controller.OrderController;
import com.DuAn1.volleyballshoes.app.dto.response.OrderDetailResponse;
import com.DuAn1.volleyballshoes.app.dto.response.OrderResponse;
import com.DuAn1.volleyballshoes.app.entity.Order;
import com.DuAn1.volleyballshoes.app.entity.OrderDetail;
import com.DuAn1.volleyballshoes.app.utils.ExcelUtil;
import com.DuAn1.volleyballshoes.app.utils.PDFUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;
import com.DuAn1.volleyballshoes.app.dto.response.OrderItemResponse;
import javax.swing.table.DefaultTableModel;

public class ViewHoaDon extends javax.swing.JPanel {

    // Chuyển đổi tạm thời từ OrderItemResponse sang OrderDetailResponse để hiển thị bảng chi tiết
    private List<OrderDetailResponse> convertToOrderDetailResponses(List<OrderItemResponse> items) {
        List<OrderDetailResponse> result = new ArrayList<>();
        if (items == null) {
            return result;
        }
        for (OrderItemResponse item : items) {
            OrderDetailResponse detail = new OrderDetailResponse();
            // Map các trường cơ bản nếu có
            detail.setQuantity(item.getQuantity());
            detail.setUnitPrice(item.getUnitPrice());
            // Các trường khác (productName, colorName, sizeName) để trống hoặc null
            result.add(detail);
        }
        return result;
    }

    private OrderController orderController = new OrderController();

    public ViewHoaDon() {
        initComponents();
        initData();
        loadAllOrders();
    }

    private void initData() {
        // Khởi tạo dữ liệu cho combo box trạng thái
        CBtrangThai.removeAllItems();
        CBtrangThai.addItem("Tất cả");
        CBtrangThai.addItem("Đã thanh toán");
        CBtrangThai.addItem("Chưa thanh toán");
        CBtrangThai.addItem("Đã huỷ");

        // Khởi tạo dữ liệu cho combo box hình thức thanh toán
        CBhinhthucTT.removeAllItems();
        CBhinhthucTT.addItem("Tất cả");
        CBhinhthucTT.addItem("Tiền mặt");
        CBhinhthucTT.addItem("Chuyển khoản");
        CBhinhthucTT.addItem("Ví điện tử");

        // Đặt ngày mặc định là hôm nay
        txtTuNgay.setDate(new Date());
        txtDenNgay.setDate(new Date());
    }

    private void loadAllOrders() {
        try {
            List<OrderResponse> orders = orderController.getAllOrders();
            loadDataTable(orders);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách hoá đơn: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadDataTable(List<OrderResponse> orders) {
        DefaultTableModel model = (DefaultTableModel) tbl.getModel();
        model.setRowCount(0);

        for (OrderResponse order : orders) {
            model.addRow(new Object[]{
                order.getOrderId(),
                order.getCustomerId(),
                order.getFinalAmount(),
                order.getStatus(),
                order.getPaymentMethod()
            });
        }
    }

    private void loadDetailsTable(List<OrderDetailResponse> details) {
        try {
            DefaultTableModel model = (DefaultTableModel) tblHDCT.getModel();
            model.setRowCount(0);

            if (details != null) {
                for (OrderDetailResponse detail : details) {
                    try {
                        // Lấy thông tin sản phẩm
                        String productName = detail.getProductName() != null ? detail.getProductName() : "";
                        String colorName = detail.getColorName() != null ? detail.getColorName() : "";
                        String sizeName = detail.getSizeName() != null ? detail.getSizeName() : "";
                        int quantity = detail.getQuantity();
                        java.math.BigDecimal unitPrice = detail.getUnitPrice() != null ? detail.getUnitPrice() : java.math.BigDecimal.ZERO;
                        java.math.BigDecimal totalPrice = detail.getTotalPrice() != null ? detail.getTotalPrice() : unitPrice.multiply(java.math.BigDecimal.valueOf(quantity));

                        model.addRow(new Object[]{
                            productName,
                            colorName,
                            sizeName,
                            quantity,
                            formatCurrency(unitPrice.doubleValue()),
                            formatCurrency(totalPrice.doubleValue())
                        });
                    } catch (Exception e) {
                        System.err.println("Error processing order detail: " + e.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải chi tiết hoá đơn: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String formatCurrency(double amount) {
        return String.format("%,.0f đ", amount);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnSearch = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        txtTuNgay = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        txtDenNgay = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        btnSearchNgay = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        CBtrangThai = new javax.swing.JComboBox<>();
        btnSearchGia = new javax.swing.JButton();
        btnXuatExcel = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        CBhinhthucTT = new javax.swing.JComboBox<>();
        btnRestart = new javax.swing.JButton();
        btnLoc = new javax.swing.JButton();
        btnXuatPDF = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblHDCT = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblLSHD = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtQR = new javax.swing.JTextField();

        setBackground(new java.awt.Color(255, 255, 255));

        btnSearch.setBackground(new java.awt.Color(0, 51, 255));
        btnSearch.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSearch.setForeground(new java.awt.Color(255, 255, 255));
        btnSearch.setText("Tìm Kiếm");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel5.setLayout(null);

        txtTuNgay.setDateFormatString("yyyy-MM-dd");
        jPanel5.add(txtTuNgay);
        txtTuNgay.setBounds(200, 20, 162, 29);

        jLabel2.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Blue"));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Từ");
        jPanel5.add(jLabel2);
        jLabel2.setBounds(170, 30, 30, 20);

        txtDenNgay.setDateFormatString("yyyy-MM-dd");
        jPanel5.add(txtDenNgay);
        txtDenNgay.setBounds(420, 20, 162, 29);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Đến");
        jPanel5.add(jLabel4);
        jLabel4.setBounds(370, 30, 40, 20);

        btnSearchNgay.setBackground(new java.awt.Color(0, 51, 255));
        btnSearchNgay.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSearchNgay.setForeground(new java.awt.Color(255, 255, 255));
        btnSearchNgay.setText("Tìm theo ngày");
        btnSearchNgay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchNgayActionPerformed(evt);
            }
        });
        jPanel5.add(btnSearchNgay);
        btnSearchNgay.setBounds(10, 20, 140, 30);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Trạng thái hoá đơn");
        jPanel5.add(jLabel3);
        jLabel3.setBounds(360, 90, 120, 16);

        CBtrangThai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CBtrangThaiActionPerformed(evt);
            }
        });
        jPanel5.add(CBtrangThai);
        CBtrangThai.setBounds(480, 80, 130, 30);

        btnSearchGia.setBackground(new java.awt.Color(0, 51, 255));
        btnSearchGia.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSearchGia.setForeground(new java.awt.Color(255, 255, 255));
        btnSearchGia.setText("Tìm Kiếm");
        btnSearchGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchGiaActionPerformed(evt);
            }
        });
        jPanel5.add(btnSearchGia);
        btnSearchGia.setBounds(10, 70, 140, 30);

        btnXuatExcel.setBackground(new java.awt.Color(0, 51, 255));
        btnXuatExcel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXuatExcel.setForeground(new java.awt.Color(255, 255, 255));
        btnXuatExcel.setText("Xuất Excel");
        btnXuatExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatExcelActionPerformed(evt);
            }
        });
        jPanel5.add(btnXuatExcel);
        btnXuatExcel.setBounds(600, 20, 100, 30);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Hình thức TT");
        jPanel5.add(jLabel5);
        jLabel5.setBounds(620, 90, 80, 10);

        CBhinhthucTT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CBhinhthucTTActionPerformed(evt);
            }
        });
        jPanel5.add(CBhinhthucTT);
        CBhinhthucTT.setBounds(700, 80, 130, 30);

        btnRestart.setBackground(new java.awt.Color(0, 51, 255));
        btnRestart.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnRestart.setForeground(new java.awt.Color(255, 255, 255));
        btnRestart.setText("Làm mới");
        btnRestart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRestartActionPerformed(evt);
            }
        });
        jPanel5.add(btnRestart);
        btnRestart.setBounds(900, 20, 80, 30);

        btnLoc.setBackground(new java.awt.Color(0, 51, 255));
        btnLoc.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnLoc.setForeground(new java.awt.Color(255, 255, 255));
        btnLoc.setText("Lọc");
        btnLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLocActionPerformed(evt);
            }
        });
        jPanel5.add(btnLoc);
        btnLoc.setBounds(810, 20, 80, 30);

        btnXuatPDF.setBackground(new java.awt.Color(51, 51, 255));
        btnXuatPDF.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXuatPDF.setForeground(new java.awt.Color(255, 255, 255));
        btnXuatPDF.setText("Xuất PDF");
        btnXuatPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatPDFActionPerformed(evt);
            }
        });
        jPanel5.add(btnXuatPDF);
        btnXuatPDF.setBounds(710, 20, 90, 30);

        tbl.setModel(new javax.swing.table.DefaultTableModel(
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
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl);

        tblHDCT.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tblHDCT.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tblHDCT);

        tblLSHD.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tblLSHD.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblLSHD);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Hoá đơn chi tiết");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Lịch sử hoá đơn");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setForeground(new java.awt.Color(0, 170, 158));

        jLabel7.setFont(new java.awt.Font("Source Sans Pro SemiBold", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 51, 255));
        jLabel7.setText(" QUẢN LÍ HOÁ ĐƠN");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel7)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txtQR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQRActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(297, 297, 297)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane1)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtQR, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 747, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane3)
                                .addGap(18, 18, 18)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtQR, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(65, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        String keyword = JOptionPane.showInputDialog("Nhập mã hoặc tên khách hàng:");
        List<OrderResponse> orders = orderController.searchOrders(keyword);
        loadDataTable(orders);
        //TODO add your handling code here:
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnSearchNgayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchNgayActionPerformed
        try {
            Date tuNgay = txtTuNgay.getDate();
            Date denNgay = txtDenNgay.getDate();

            if (tuNgay == null || denNgay == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn đầy đủ ngày bắt đầu và kết thúc",
                        "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Đảm bảo ngày kết thúc là cuối ngày
            Calendar cal = Calendar.getInstance();
            cal.setTime(denNgay);
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            denNgay = cal.getTime();

            List<OrderResponse> orders = orderController.findByCreatedDateBetween(tuNgay, denNgay);
            loadDataTable(orders);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm theo ngày: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSearchNgayActionPerformed

    private void btnSearchGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchGiaActionPerformed
        try {
            String minStr = JOptionPane.showInputDialog("Nhập giá thấp nhất:");
            if (minStr == null) {
                return;
            }
            double giaMin = Double.parseDouble(minStr);

            String maxStr = JOptionPane.showInputDialog("Nhập giá cao nhất:");
            if (maxStr == null) {
                return;
            }
            double giaMax = Double.parseDouble(maxStr);

            if (giaMin > giaMax) {
                JOptionPane.showMessageDialog(this, "Giá thấp nhất không thể lớn hơn giá cao nhất",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            List<OrderResponse> orders = orderController.findByTotalAmountBetween(giaMin, giaMax);
            loadDataTable(orders);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số hợp lệ",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm theo giá: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSearchGiaActionPerformed

    private void CBhinhthucTTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CBhinhthucTTActionPerformed
        try {
            // Get the selected payment method
            String selectedPaymentMethod = CBhinhthucTT.getSelectedItem().toString();

            // If "Tất cả" is selected, load all orders
            if ("Tất cả".equals(selectedPaymentMethod)) {
                loadAllOrders();
                return;
            }

            // Lấy tất cả đơn hàng
            List<OrderResponse> allOrders = orderController.getAllOrders();

            // Lọc đơn hàng theo hình thức thanh toán đã chọn
            List<OrderResponse> filteredOrders = allOrders.stream()
                    .filter(order -> selectedPaymentMethod.equals(order.getPaymentMethod()))
                    .collect(Collectors.toList());

            // If no orders found with the selected payment method
            if (filteredOrders.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Không tìm thấy hóa đơn nào với hình thức thanh toán: " + selectedPaymentMethod,
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
            }

            // Load the filtered orders into the table
            loadDataTable(filteredOrders);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi lọc hóa đơn theo hình thức thanh toán: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_CBhinhthucTTActionPerformed

    private void btnRestartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRestartActionPerformed
        loadAllOrders();
        txtTuNgay.setDate(null);
        txtDenNgay.setDate(null);
        CBtrangThai.setSelectedIndex(0);
        CBhinhthucTT.setSelectedIndex(0);
        txtQR.setText("");
        // TODO add your handling code here:
    }//GEN-LAST:event_btnRestartActionPerformed

    private void tblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMouseClicked
        try {
            int selectedRow = tbl.getSelectedRow();
            if (selectedRow < 0) {
                return;
            }

            String orderId = tbl.getValueAt(selectedRow, 0).toString();
            if (orderId == null || orderId.trim().isEmpty()) {
                return;
            }

            // Lấy chi tiết đơn hàng theo orderId
            OrderResponse order = orderController.getOrderById(Integer.parseInt(orderId));
            List<OrderItemResponse> itemResponses = order != null ? order.getItems() : new ArrayList<>();
            List<OrderDetailResponse> details = convertToOrderDetailResponses(itemResponses);
            loadDetailsTable(details);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải chi tiết đơn hàng: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_tblMouseClicked

    private void btnXuatExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatExcelActionPerformed
        List<OrderResponse> orders = orderController.getAllOrders();
        ExcelUtil.exportOrdersToExcel(orders, "orders.xlsx");
        JOptionPane.showMessageDialog(this, "Xuất Excel thành công!");
// TODO add your handling code here:
    }//GEN-LAST:event_btnXuatExcelActionPerformed

    private void btnLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocActionPerformed
        try {
            // Get the selected dates
            Date fromDate = txtTuNgay.getDate();
            Date toDate = txtDenNgay.getDate();

            // Validate date range
            if (fromDate == null || toDate == null) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng chọn đầy đủ ngày bắt đầu và ngày kết thúc",
                        "Cảnh báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Make sure fromDate is before or equal to toDate
            if (fromDate.after(toDate)) {
                JOptionPane.showMessageDialog(this,
                        "Ngày bắt đầu phải trước hoặc bằng ngày kết thúc",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Add 1 day to toDate to include the entire end day
            Calendar cal = Calendar.getInstance();
            cal.setTime(toDate);
            cal.add(Calendar.DATE, 1);
            Date endOfDay = cal.getTime();

            // Get all orders
            List<OrderResponse> allOrders = orderController.getAllOrders();

            // Filter orders by date range
            List<OrderResponse> filteredOrders = allOrders.stream()
                    .filter(order -> {
                        // Convert LocalDateTime to Date for comparison
                        Date orderDate = null;
                        if (order.getCreatedAt() != null) {
                            orderDate = java.sql.Timestamp.valueOf(order.getCreatedAt());
                        }
                        return orderDate != null
                                && !orderDate.before(fromDate)
                                && orderDate.before(endOfDay);
                    })
                    .collect(Collectors.toList());

            // If no orders found in the date range
            if (filteredOrders.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Không tìm thấy hóa đơn nào trong khoảng thời gian đã chọn",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
            }

            // Load the filtered orders into the table
            loadDataTable(filteredOrders); // filteredOrders là List<OrderResponse>

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi lọc hóa đơn theo ngày: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnLocActionPerformed

    private void txtQRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQRActionPerformed
        try {
            String qrCode = txtQR.getText().trim();

            if (qrCode.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng nhập mã QR hoặc mã hóa đơn",
                        "Cảnh báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Try to find order by QR code or order ID
            OrderResponse foundOrder = orderController.getOrderByQRCodeOrId(qrCode);

            if (foundOrder == null) {
                JOptionPane.showMessageDialog(this,
                        "Không tìm thấy hóa đơn với mã: " + qrCode,
                        "Không tìm thấy",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // If found, load the single order into the table
            List<OrderResponse> orders = new ArrayList<>();
            orders.add(foundOrder);
            loadDataTable(orders);

            // Select the row in the table
            selectOrderInTable(String.valueOf(foundOrder.getOrderId()));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi tìm kiếm hóa đơn: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_txtQRActionPerformed
    private void selectOrderInTable(String orderId) {
        DefaultTableModel model = (DefaultTableModel) tbl.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            String currentId = model.getValueAt(i, 0).toString(); // Assuming order ID is in first column
            if (currentId.equals(orderId)) {
                tbl.setRowSelectionInterval(i, i);
                tbl.scrollRectToVisible(tbl.getCellRect(i, 0, true));
                break;
            }
        }
    }
    private void CBtrangThaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CBtrangThaiActionPerformed
        try {
            String trangThai = (String) CBtrangThai.getSelectedItem();
            if (trangThai == null || "Tất cả".equals(trangThai)) {
                loadAllOrders();
                return;
            }

            List<OrderResponse> orders = orderController.findByStatus(trangThai); // Đảm bảo orderController có hàm này
            loadDataTable(orders);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi lọc theo trạng thái: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_CBtrangThaiActionPerformed

    private void btnXuatPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatPDFActionPerformed
        List<OrderResponse> orders = orderController.getAllOrders();
        PDFUtil.exportOrdersToPDF(orders, "orders.pdf");
        JOptionPane.showMessageDialog(this, "Xuất PDF thành công!");
        // TODO add your handling code here:
    }//GEN-LAST:event_btnXuatPDFActionPerformed

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
            java.util.logging.Logger.getLogger(ViewHoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewHoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewHoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewHoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewHoaDon().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> CBhinhthucTT;
    private javax.swing.JComboBox<String> CBtrangThai;
    private javax.swing.JButton btnLoc;
    private javax.swing.JButton btnRestart;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnSearchGia;
    private javax.swing.JButton btnSearchNgay;
    private javax.swing.JButton btnXuatExcel;
    private javax.swing.JButton btnXuatPDF;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tbl;
    private javax.swing.JTable tblHDCT;
    private javax.swing.JTable tblLSHD;
    private com.toedter.calendar.JDateChooser txtDenNgay;
    private javax.swing.JTextField txtQR;
    private com.toedter.calendar.JDateChooser txtTuNgay;
    // End of variables declaration//GEN-END:variables
}
