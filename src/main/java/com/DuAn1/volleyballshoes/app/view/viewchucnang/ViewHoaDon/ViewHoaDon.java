package com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewHoaDon;

import com.DuAn1.volleyballshoes.app.controller.BillController;
import com.DuAn1.volleyballshoes.app.controller.OrderController;
import com.DuAn1.volleyballshoes.app.dto.response.OrderDetailResponse;
import com.DuAn1.volleyballshoes.app.dto.response.OrderResponse;
import com.DuAn1.volleyballshoes.app.dto.response.OrderWithDetailsResponse;
import com.DuAn1.volleyballshoes.app.entity.Order;
import com.DuAn1.volleyballshoes.app.entity.OrderDetail;
import com.DuAn1.volleyballshoes.app.utils.ExcelUtil;
import com.DuAn1.volleyballshoes.app.utils.PDFUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import com.DuAn1.volleyballshoes.app.dto.response.*;
import java.io.File;
import java.text.SimpleDateFormat;

public class ViewHoaDon extends javax.swing.JPanel {

    // Utility method to safely set font with fallback
    private static Font getSafeFont(String fontName, int style, int size) {
        try {
            return new Font(fontName, style, size);
        } catch (Exception e) {
            // Fallback to default font if specified font fails to load
            return new Font(Font.SANS_SERIF, style, size);
        }
    }

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
        try {
            // Set system look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            // Set default font for all components
            Font defaultFont = getSafeFont("Segoe UI", Font.PLAIN, 12);
            Enumeration<Object> keys = UIManager.getDefaults().keys();
            while (keys.hasMoreElements()) {
                Object key = keys.nextElement();
                if (key.toString().toLowerCase().contains(".font")) {
                    try {
                        UIManager.put(key, defaultFont);
                    } catch (Exception e) {
                        // Ignore any font setting errors
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("=== Initializing ViewHoaDon ===");
        initComponents();

        // Initialize the table model with correct column names and types
        String[] orderColumns = {"STT", "Khách hàng", "Tổng tiền", "Trạng thái", "Hình thức TT", "Mã đơn hàng", "Ngày tạo"};
        DefaultTableModel orderModel = new DefaultTableModel(orderColumns, 0) {
            Class[] types = new Class[]{
                Integer.class, // STT
                String.class, // Khách hàng
                String.class, // Tổng tiền (đã định dạng)
                String.class, // Trạng thái
                String.class, // Hình thức TT
                String.class, // Mã đơn hàng
                String.class // Ngày tạo
            };

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };

        // Set the model and configure table properties
        tblhoadon.setModel(orderModel);
        tblhoadon.setAutoCreateRowSorter(true);
        tblhoadon.setFillsViewportHeight(true);
        tblhoadon.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblhoadon.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tblhoadon.getTableHeader().setReorderingAllowed(false);

        // Set preferred column widths
        int[] columnWidths = {50, 200, 150, 100, 120, 150, 150}; // STT, Khách hàng, Tổng tiền, Trạng thái, Hình thức TT, Mã đơn hàng, Ngày tạo
        for (int i = 0; i < columnWidths.length && i < orderModel.getColumnCount(); i++) {
            tblhoadon.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
        }

        // Set preferred size
        tblhoadon.setPreferredScrollableViewportSize(new Dimension(1000, 400));

        // Initialize data and load orders
        initData();
        loadAllOrders();

        String[] detailColumnsVN = {"STT", "Sản phẩm", "Màu sắc", "Kích thước", "Số lượng", "Đơn giá", "Giảm giá", "Tiền giảm", "Thành tiền"};
        DefaultTableModel detailModel = new DefaultTableModel(detailColumnsVN, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0 || columnIndex == 4) {
                    return Integer.class; // STT, Số lượng
                }
                if (columnIndex >= 5) {
                    return String.class; // Các cột tiền
                }
                return Object.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa các ô
            }
        };
        tblHDCT.setModel(detailModel);

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

        // Đặt ngày mặc định là hôm nay
        txtTuNgay.setDate(new Date());
        txtDenNgay.setDate(new Date());
    }

    private void loadAllOrders() {
        try {
            System.out.println("Fetching all orders from controller...");
            List<OrderResponse> orders = orderController.getAllOrders();
            System.out.println("Retrieved " + (orders != null ? orders.size() : 0) + " orders from controller");
            if (orders != null && !orders.isEmpty()) {
                System.out.println("First order details - ID: " + orders.get(0).getOrderId()
                        + ", Code: " + orders.get(0).getOrderCode()
                        + ", Customer: " + orders.get(0).getCustomerName());
            }
            loadDataTable(orders);
        } catch (Exception e) {
            System.err.println("Error in loadAllOrders: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách hoá đơn: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadDataTable(List<OrderResponse> orders) {
        if (orders == null) {
            System.err.println("Error: Orders list is null");
            return;
        }

        for (int i = 0; i < tblhoadon.getColumnCount(); i++) {
            System.out.println("  Column " + i + ": "
                    + tblhoadon.getColumnName(i)
                    + " (" + tblhoadon.getColumnClass(i).getSimpleName() + ")");
        }

        // Print current table data
        System.out.println("\nCurrent table data:");
        DefaultTableModel currentModel = (DefaultTableModel) tblhoadon.getModel();
        for (int row = 0; row < Math.min(5, currentModel.getRowCount()); row++) {
            System.out.print("Row " + row + ": ");
            for (int col = 0; col < currentModel.getColumnCount(); col++) {
                System.out.print(currentModel.getValueAt(row, col) + " | ");
            }
            System.out.println();
        }

        System.out.println("Loading " + orders.size() + " orders into table");
        DefaultTableModel model = (DefaultTableModel) tblhoadon.getModel();

        // Clear existing data
        model.setRowCount(0);

        if (orders.isEmpty()) {
            System.out.println("No orders found");
            JOptionPane.showMessageDialog(this, "Không tìm thấy đơn hàng nào", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int stt = 1;
        // Process each order
        for (OrderResponse order : orders) {
            try {
                System.out.println("Processing order: " + order.getOrderCode()
                        + ", Customer: " + order.getCustomerName()
                        + ", Amount: " + order.getFinalAmount());

                // Handle null customer name
                String customerName = order.getCustomerName();
                if (customerName == null || customerName.trim().isEmpty()) {
                    customerName = "Khách lẻ";
                } else {
                    try {
                        customerName = new String(customerName.getBytes("ISO-8859-1"), "UTF-8");
                    } catch (Exception e) {
                        System.err.println("Error converting customer name: " + e.getMessage());
                    }
                }

                // Format date
                String orderDate = "";
                if (order.getCreatedAt() != null) {
                    orderDate = order.getCreatedAt().toString();
                }

                // Format amount
                double amount = order.getFinalAmount() != null
                        ? order.getFinalAmount().doubleValue() : 0.0;

                // Add row to table model - ensure column order matches the model
                Object[] rowData = {
                    stt++, // STT (Integer)
                    customerName, // Khách hàng (String)
                    String.format("%,.0f đ", amount), // Tổng tiền (String)
                    order.getStatus() != null ? order.getStatus() : "", // Trạng thái (String)
                    order.getPaymentMethod() != null ? order.getPaymentMethod() : "", // Hình thức TT (String)
                    order.getOrderCode() != null ? order.getOrderCode() : "", // Mã đơn hàng (String)
                    orderDate // Ngày tạo (String)
                };
                model.addRow(rowData);

            } catch (Exception e) {
                System.err.println("Error processing order " + (order != null ? order.getOrderCode() : "null") + ": " + e.getMessage());
                e.printStackTrace();
            }
        }

        // Notify the table that the model has changed
        model.fireTableDataChanged();
        System.out.println("Finished loading " + orders.size() + " orders into table");

        // Force UI update
        tblhoadon.revalidate();
        tblhoadon.repaint();

        // Ensure table is visible and has a reasonable size
        if (tblhoadon.getPreferredSize().width < 100) {
            System.out.println("Adjusting table preferred size...");
            tblhoadon.setPreferredScrollableViewportSize(new Dimension(1000, 400));
        }

        // Force update of the UI
        tblhoadon.revalidate();
        tblhoadon.repaint();

        // Debug: Print scroll pane info
        if (tblhoadon.getParent() instanceof JViewport) {
            JViewport viewport = (JViewport) tblhoadon.getParent();
            System.out.println("Viewport size: " + viewport.getSize());
            System.out.println("Viewport view size: " + viewport.getViewSize());
            System.out.println("Viewport view position: " + viewport.getViewPosition());
        }
    }

    private void loadDetailsTable(List<OrderDetailResponse> details) {
        try {
            DefaultTableModel model = (DefaultTableModel) tblHDCT.getModel();
            model.setRowCount(0);

            if (details != null) {
                int stt = 1;
                for (OrderDetailResponse detail : details) {
                    try {
                        String productName = detail.getProductName() != null ? detail.getProductName() : "";
                        String colorName = detail.getColorName() != null ? detail.getColorName() : "";
                        String sizeName = detail.getSizeName() != null ? detail.getSizeName() : "";
                        int quantity = detail.getQuantity();
                        java.math.BigDecimal unitPrice = detail.getUnitPrice() != null ? detail.getUnitPrice() : java.math.BigDecimal.ZERO;
                        java.math.BigDecimal discountPercent = detail.getDiscountPercent() != null
                                ? detail.getDiscountPercent() : java.math.BigDecimal.ZERO;
                        java.math.BigDecimal discount = discountPercent.compareTo(java.math.BigDecimal.ZERO) > 0
                                ? unitPrice.multiply(discountPercent).divide(java.math.BigDecimal.valueOf(100))
                                : java.math.BigDecimal.ZERO;
                        java.math.BigDecimal totalPrice = detail.getTotalPrice() != null
                                ? detail.getTotalPrice()
                                : unitPrice.multiply(java.math.BigDecimal.valueOf(quantity)).subtract(discount);

                        model.addRow(new Object[]{
                            stt++, // STT
                            productName,
                            colorName,
                            sizeName,
                            quantity,
                            formatCurrency(unitPrice.doubleValue()),
                            discountPercent.compareTo(java.math.BigDecimal.ZERO) > 0
                            ? discountPercent.setScale(2, java.math.RoundingMode.HALF_UP) + "%" : "0%",
                            formatCurrency(discount.doubleValue()),
                            formatCurrency(totalPrice.doubleValue())
                        });
                    } catch (Exception e) {
                        System.err.println("Error processing order detail: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải chi tiết hoá đơn: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
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
        btnXuatPDF1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblhoadon = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblHDCT = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
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

        btnXuatPDF1.setBackground(new java.awt.Color(51, 51, 255));
        btnXuatPDF1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXuatPDF1.setForeground(new java.awt.Color(255, 255, 255));
        btnXuatPDF1.setText("Xuất PDF");
        btnXuatPDF1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatPDF1ActionPerformed(evt);
            }
        });
        jPanel5.add(btnXuatPDF1);
        btnXuatPDF1.setBounds(710, 20, 90, 30);

        tblhoadon.setModel(new javax.swing.table.DefaultTableModel(
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
        tblhoadon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblhoadonMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblhoadon);

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

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Hoá đơn chi tiết");

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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtQR, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(297, 297, 297)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1090, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1082, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 1082, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 62, Short.MAX_VALUE))
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
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void tblhoadonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblhoadonMouseClicked
        try {
            int selectedRow = tblhoadon.getSelectedRow();
            if (selectedRow < 0) {
                return;
            }

            String orderId = tblhoadon.getValueAt(selectedRow, 0).toString();
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
    }//GEN-LAST:event_tblhoadonMouseClicked

    private void btnXuatExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatExcelActionPerformed
        try {
            // Create a file chooser
            javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
            fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
            fileChooser.setSelectedFile(new java.io.File("DanhSachHoaDon_"
                    + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".xlsx"));

            int userSelection = fileChooser.showSaveDialog(this);
            if (userSelection == javax.swing.JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                if (!filePath.toLowerCase().endsWith(".xlsx")) {
                    filePath += ".xlsx";
                }

                // Get all orders with details
                List<OrderWithDetailsResponse> allOrders = new ArrayList<>();
                for (int i = 0; i < tblhoadon.getRowCount(); i++) {
                    String orderCode = tblhoadon.getValueAt(i, 5).toString(); // Column 5 is the order code
                    try {
                        OrderWithDetailsResponse orderWithDetails = orderController.getOrderWithDetails(orderCode);
                        if (orderWithDetails != null) {
                            allOrders.add(orderWithDetails);
                        }
                    } catch (Exception e) {
                        System.err.println("Error getting details for order " + orderCode + ": " + e.getMessage());
                    }
                }

                if (allOrders.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Không có dữ liệu hóa đơn để xuất!",
                            "Cảnh báo",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Export all orders to Excel
                ExcelUtil.exportAllOrdersToExcel(allOrders, filePath);

                JOptionPane.showMessageDialog(this,
                        "Xuất danh sách hóa đơn thành công!\nFile đã được lưu tại: " + filePath,
                        "Thành công",
                        JOptionPane.INFORMATION_MESSAGE);

                // Optionally open the Excel file after export
                if (Desktop.isDesktopSupported()) {
                    try {
                        File file = new File(filePath);
                        Desktop.getDesktop().open(file);
                    } catch (Exception ex) {
                        // Ignore if opening fails
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi xuất file Excel: " + ex.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
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
        DefaultTableModel model = (DefaultTableModel) tblhoadon.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            String currentId = model.getValueAt(i, 0).toString(); // Assuming order ID is in first column
            if (currentId.equals(orderId)) {
                tblhoadon.setRowSelectionInterval(i, i);
                tblhoadon.scrollRectToVisible(tblhoadon.getCellRect(i, 0, true));
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

    private void btnXuatPDF1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatPDF1ActionPerformed
        // TODO add your handling code here:
        int selectedRow = tblhoadon.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một hóa đơn để xuất PDF!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Get the order code from column 5 (index 5) which is the "Mã đơn hàng" column
        String orderCode = tblhoadon.getValueAt(selectedRow, 5).toString();
        if (orderCode == null || orderCode.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy mã đơn hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        OrderWithDetailsResponse orderWithDetails = orderController.getOrderWithDetails(orderCode);
        if (orderWithDetails == null || orderWithDetails.getOrder() == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin đơn hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create a file chooser
        javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file PDF");
        fileChooser.setSelectedFile(new java.io.File("HoaDon_" + orderCode + ".pdf"));

        // Set file filter for PDF files
        javax.swing.filechooser.FileNameExtensionFilter filter = new javax.swing.filechooser.FileNameExtensionFilter("PDF Files", "pdf");
        fileChooser.setFileFilter(filter);

        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == javax.swing.JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".pdf")) {
                filePath += ".pdf";
            }

            try {
                // Export the order with details to PDF
                PDFUtil.exportOrderToPDF(orderWithDetails, filePath);
                JOptionPane.showMessageDialog(this,
                        "Xuất hóa đơn thành công!\nFile đã được lưu tại: " + filePath,
                        "Thành công",
                        JOptionPane.INFORMATION_MESSAGE);

                // Optionally open the PDF after export
                if (Desktop.isDesktopSupported()) {
                    try {
                        File file = new File(filePath);
                        if (file.exists()) {
                            Desktop.getDesktop().open(file);
                        }
                    } catch (Exception ex) {
                        // Ignore if we can't open the file
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this,
                        "Lỗi khi xuất file PDF: " + e.getMessage(),
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnXuatPDF1ActionPerformed

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
    private javax.swing.JButton btnXuatPDF1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tblHDCT;
    private javax.swing.JTable tblhoadon;
    private com.toedter.calendar.JDateChooser txtDenNgay;
    private javax.swing.JTextField txtQR;
    private com.toedter.calendar.JDateChooser txtTuNgay;
    // End of variables declaration//GEN-END:variables
}
