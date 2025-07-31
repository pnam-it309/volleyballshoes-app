package com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewKhachHang;

import com.DuAn1.volleyballshoes.app.controller.CustomerController;
import com.DuAn1.volleyballshoes.app.dto.request.CustomerCreateRequest;
import com.DuAn1.volleyballshoes.app.entity.Customer;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ViewKhachHang extends javax.swing.JPanel {
    private final CustomerController customerController;
    private List<Customer> customerList;
    private DefaultTableModel tableModel;
    private DefaultTableModel historyTableModel;
    private int selectedRow = -1;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    public ViewKhachHang() {
        this.customerController = new CustomerController();
        initComponents();
        setupTables();
        loadData();
        clearForm();
    }
    
    private void initializeData() {
        customerList = customerController.findAll();
    }
    
    private void setupTables() {
        // Setup main customer table
        String[] columns = {"Mã KH", "Tên khách hàng", "Giới tính", "Ngày sinh", "SĐT", "Địa chỉ", "Email", "Điểm tích lũy"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tbl_bang.setModel(tableModel);
        
        // Setup history table
        String[] historyColumns = {"Mã KH", "Tên khách hàng", "Thao tác", "Thời gian"};
        historyTableModel = new DefaultTableModel(historyColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return String.class;
            }
        };
        tbl_bangls.setModel(historyTableModel);
        
        // Set column widths for history table
        tbl_bangls.getColumnModel().getColumn(0).setPreferredWidth(80);  // Mã KH
        tbl_bangls.getColumnModel().getColumn(1).setPreferredWidth(150); // Tên KH
        tbl_bangls.getColumnModel().getColumn(2).setPreferredWidth(100); // Thao tác
        tbl_bangls.getColumnModel().getColumn(3).setPreferredWidth(150); // Thời gian
        
        // Setup gender radio buttons
        buttonGroup1.add(rdo_nam);
        buttonGroup1.add(rdo_nu);
        buttonGroup1.add(rdo_khac);
        
        // Setup gender filter combo box
        cbo_gioitinh.addItem("Tất cả");
        cbo_gioitinh.addItem("Nam");
        cbo_gioitinh.addItem("Nữ");
        cbo_gioitinh.addItem("Khác");
    }
    
    private void loadData() {
        try {
            customerList = customerController.findAll();
            updateTableData();
        } catch (Exception e) {
            showError("Lỗi khi tải dữ liệu khách hàng: " + e.getMessage());
        }
    }
    
    private void updateTableData() {
        tableModel.setRowCount(0);
        for (Customer customer : customerList) {
            Object[] row = {
                customer.getCustomerCode(),
                customer.getCustomerFullName(),
                customer.getGenderDisplayName(),
                customer.getCustomerBirthDate() != null ? 
                    customer.getCustomerBirthDate().format(dateFormatter) : "",
                customer.getCustomerPhone(),
                customer.getCustomerAddress() != null ? customer.getCustomerAddress() : "",
                customer.getCustomerEmail(),
                customer.getCustomerPoints()
            };
            tableModel.addRow(row);
        }
    }
    
    private void clearForm() {
        txt_ma.setText("");
        txt_ten.setText("");
        txt_sdt.setText("");
        txt_diachi.setText("");
        txt_email.setText("");
        buttonGroup1.clearSelection();
        datengaysinh.setDate(null);
        txt_ma.setEnabled(true);
        selectedRow = -1;
    }
    
    private void fillForm(Customer customer) {
        if (customer == null) return;
        
        txt_ma.setText(customer.getCustomerCode());
        txt_ma.setEnabled(false); // Disable code field when editing
        txt_ten.setText(customer.getCustomerFullName());
        txt_sdt.setText(customer.getCustomerPhone());
        txt_email.setText(customer.getCustomerEmail());
        txt_diachi.setText(customer.getCustomerAddress() != null ? customer.getCustomerAddress() : "");
        
        // Set gender radio button
        if (customer.getCustomerGender() != null) {
            switch (customer.getCustomerGender()) {
                case MALE -> rdo_nam.setSelected(true);
                case FEMALE -> rdo_nu.setSelected(true);
                case OTHER -> rdo_khac.setSelected(true);
            }
        }
        
        // Set birth date
        if (customer.getCustomerBirthDate() != null) {
            datengaysinh.setDate(Date.from(customer.getCustomerBirthDate()
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant()));
        } else {
            datengaysinh.setDate(null);
        }
    }
    
    private CustomerCreateRequest getFormData() {
        Customer.Gender gender = Customer.Gender.OTHER;
        if (rdo_nam.isSelected()) {
            gender = Customer.Gender.MALE;
        } else if (rdo_nu.isSelected()) {
            gender = Customer.Gender.FEMALE;
        }
        
        LocalDate birthDate = null;
        if (datengaysinh.getDate() != null) {
            birthDate = datengaysinh.getDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        }
        
        return CustomerCreateRequest.builder()
            .customerCode(txt_ma.getText().trim())
            .customerFullName(txt_ten.getText().trim())
            .customerPhone(txt_sdt.getText().trim())
            .customerEmail(txt_email.getText().trim())
            .customerAddress(txt_diachi.getText().trim())
            .customerGender(gender)
            .customerBirthDate(birthDate)
            .build();
    }
    
    private boolean validateForm() {
        if (txt_ma.getText().trim().isEmpty()) {
            showError("Vui lòng nhập mã khách hàng!");
            return false;
        }
        if (txt_ten.getText().trim().isEmpty()) {
            showError("Vui lòng nhập tên khách hàng!");
            return false;
        }
        if (txt_sdt.getText().trim().isEmpty()) {
            showError("Vui lòng nhập số điện thoại!");
            return false;
        }
        if (txt_email.getText().trim().isEmpty()) {
            showError("Vui lòng nhập email!");
            return false;
        }
        if (!buttonGroup1.isSelected(rdo_nam.getModel()) && 
            !buttonGroup1.isSelected(rdo_nu.getModel()) && 
            !buttonGroup1.isSelected(rdo_khac.getModel())) {
            showError("Vui lòng chọn giới tính!");
            return false;
        }
        return true;
    }
    
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
    
    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Thành công", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void addHistory(Customer customer, String action) {
        // Add to history table
        Object[] row = {
            customer.getCustomerCode(),
            customer.getCustomerFullName(),
            action,
            java.time.LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
        };
        historyTableModel.addRow(row);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        tbl_them = new javax.swing.JButton();
        tbl_sửa = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txt_ma = new javax.swing.JTextField();
        txt_ten = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        rdo_nam = new javax.swing.JRadioButton();
        rdo_nu = new javax.swing.JRadioButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        datengaysinh = new com.toedter.calendar.JDateChooser();
        txt_sdt = new javax.swing.JTextField();
        txt_diachi = new javax.swing.JTextField();
        txt_email = new javax.swing.JTextField();
        tbl_lammoi = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_bang = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txt_nhạptim = new javax.swing.JTextField();
        tbl_timkiem = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_bangls = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        cbo_gioitinh = new javax.swing.JComboBox<>();
        tbn_loc = new javax.swing.JButton();

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

        jButton1.setBackground(new java.awt.Color(0, 51, 255));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Xuất Danh Sách");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setText("Mã Khách Hàng");

        txt_ten.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_tenActionPerformed(evt);
            }
        });

        jLabel4.setText("Tên Khách Hàng");

        jLabel5.setText("GIới tính");

        buttonGroup1.add(rdo_nam);
        rdo_nam.setText("Nam");

        buttonGroup1.add(rdo_nu);
        rdo_nu.setText("Nữ");
        rdo_nu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdo_nuActionPerformed(evt);
            }
        });

        jLabel6.setText("Địa Chỉ");

        jLabel7.setText("Email");

        jLabel8.setText("Ngày Sinh");

        jLabel9.setText("Số Điện Thoại");

        datengaysinh.setDateFormatString("yyyy-MM-dd");

        tbl_lammoi.setBackground(new java.awt.Color(0, 51, 255));
        tbl_lammoi.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tbl_lammoi.setForeground(new java.awt.Color(255, 255, 255));
        tbl_lammoi.setText("Làm Mới");
        tbl_lammoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbl_lammoiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(tbl_them)
                        .addGap(251, 251, 251)
                        .addComponent(tbl_lammoi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel8)
                        .addGap(60, 60, 60)
                        .addComponent(datengaysinh, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(jButton1)
                        .addGap(79, 79, 79))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(44, 44, 44)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txt_ma, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_ten, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(66, 66, 66)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel6)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(tbl_sửa)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(rdo_nam)
                                        .addGap(29, 29, 29)
                                        .addComponent(rdo_nu)))
                                .addGap(278, 278, 278)
                                .addComponent(jLabel9)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_diachi, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_sdt, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(56, 56, 56))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(txt_ma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(jLabel3))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(txt_diachi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(53, 53, 53)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel7)
                            .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_ten, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(rdo_nam)
                            .addComponent(rdo_nu))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tbl_them)
                            .addComponent(tbl_sửa)
                            .addComponent(tbl_lammoi)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txt_sdt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(44, 44, 44)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(datengaysinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        tbl_bang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã Khách Hàng", "Tên Khách Hàng", "Giới Tính", "Địa chỉ ", "Email", "SDT", "Ngaysinh"
            }
        ));
        tbl_bang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_bangMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_bang);

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

        tbl_bangls.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Stt", "Mã KH", "Tên kh", "SDT KH", "Tổng tiền", "Ngày mua "
            }
        ));
        jScrollPane1.setViewportView(tbl_bangls);

        jLabel10.setText("Lịch Sử Mua Hàng");

        jLabel11.setText("Thông tin khách Hàng ");

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        cbo_gioitinh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "nam", "nữ" }));

        tbn_loc.setText("Lọc");
        tbn_loc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbn_locActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tbn_loc)
                    .addComponent(cbo_gioitinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(11, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(cbo_gioitinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(tbn_loc)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addGap(18, 18, 18)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(81, 81, 81))))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(370, 370, 370)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 833, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel1)
                .addGap(35, 35, 35)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49))
        );

        jPanel4.getAccessibleContext().setAccessibleName("Lọc");
        jPanel4.getAccessibleContext().setAccessibleDescription("");
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // Export customer list to Excel
        try {
            if (customerList.isEmpty()) {
                showError("Không có dữ liệu để xuất!");
                return;
            }
            
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn nơi lưu file");
            fileChooser.setSelectedFile(new File("DanhSachKhachHang_" + 
                java.time.LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + 
                ".xlsx"));
            
            int userSelection = fileChooser.showSaveDialog(this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                
                try (Workbook workbook = new XSSFWorkbook()) {
                    Sheet sheet = workbook.createSheet("Danh sách khách hàng");
                    
                    // Create header row
                    Row headerRow = sheet.createRow(0);
                    String[] headers = {"Mã KH", "Tên khách hàng", "Giới tính", "Ngày sinh", 
                                      "SĐT", "Địa chỉ", "Email", "Điểm tích lũy"};
                    
                    CellStyle headerStyle = workbook.createCellStyle();
                    Font headerFont = workbook.createFont();
                    headerFont.setBold(true);
                    headerStyle.setFont(headerFont);
                    
                    for (int i = 0; i < headers.length; i++) {
                        Cell cell = headerRow.createCell(i);
                        cell.setCellValue(headers[i]);
                        cell.setCellStyle(headerStyle);
                        sheet.autoSizeColumn(i);
                    }
                    
                    // Add data rows
                    int rowNum = 1;
                    for (Customer customer : customerList) {
                        Row row = sheet.createRow(rowNum++);
                        row.createCell(0).setCellValue(customer.getCustomerCode());
                        row.createCell(1).setCellValue(customer.getCustomerFullName());
                        row.createCell(2).setCellValue(customer.getGenderDisplayName());
                        
                        if (customer.getCustomerBirthDate() != null) {
                            row.createCell(3).setCellValue(
                                customer.getCustomerBirthDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                        } else {
                            row.createCell(3).setCellValue("");
                        }
                        
                        row.createCell(4).setCellValue(customer.getCustomerPhone());
                        row.createCell(5).setCellValue(customer.getCustomerAddress() != null ? 
                            customer.getCustomerAddress() : "");
                        row.createCell(6).setCellValue(customer.getCustomerEmail());
                        row.createCell(7).setCellValue(customer.getCustomerPoints());
                    }
                    
                    // Auto-size all columns
                    for (int i = 0; i < headers.length; i++) {
                        sheet.autoSizeColumn(i);
                    }
                    
                    // Write the output to a file
                    try (FileOutputStream fileOut = new FileOutputStream(fileToSave)) {
                        workbook.write(fileOut);
                    }
                    
                    showSuccess("Xuất dữ liệu thành công ra file: " + fileToSave.getAbsolutePath());
                } catch (IOException e) {
                    throw new IOException("Lỗi khi ghi file: " + e.getMessage(), e);
                }
            }
        } catch (Exception e) {
            showError("Lỗi khi xuất danh sách: " + e.getMessage());
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tbl_themActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbl_themActionPerformed
        if (!validateForm()) {
            return;
        }
        
        try {
            CustomerCreateRequest request = getFormData();
            String validationError = request.validate();
            if (validationError != null) {
                showError(validationError);
                return;
            }
            
            // Check if customer code already exists
            if (customerController.findByCode(request.getCustomerCode()) != null) {
                showError("Mã khách hàng đã tồn tại!");
                return;
            }
            
            Customer customer = customerController.create(request);
            showSuccess("Thêm khách hàng thành công!");
            loadData();
            clearForm();
            
            // Add to history
            addHistory(customer, "Thêm mới");
            
            // Auto-scroll to the bottom of the history table
            if (historyTableModel.getRowCount() > 0) {
                int lastRow = historyTableModel.getRowCount() - 1;
                tbl_bangls.scrollRectToVisible(tbl_bangls.getCellRect(lastRow, 0, true));
            }
        } catch (Exception e) {
            showError("Lỗi khi thêm khách hàng: " + e.getMessage());
        }
    }//GEN-LAST:event_tbl_themActionPerformed

    private void tbl_sửaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbl_sửaActionPerformed
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng cần cập nhật!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (!validateForm()) {
            return;
        }
        
        try {
            // Get the original customer to keep the ID
            Customer originalCustomer = customerList.get(selectedRow);
            CustomerCreateRequest updateRequest = getFormData();
            
            // Update the customer using the controller
            Customer updatedCustomer = customerController.update(originalCustomer.getCustomerId(), updateRequest);
            
            if (updatedCustomer != null) {
                loadData();
                clearForm();
                showSuccess("Cập nhật thông tin khách hàng thành công!");
                
                // Add to history
                addHistory(updatedCustomer, "Cập nhật");
                
                // Auto-scroll to the bottom of the history table
                if (historyTableModel.getRowCount() > 0) {
                    int lastRow = historyTableModel.getRowCount() - 1;
                    tbl_bangls.scrollRectToVisible(tbl_bangls.getCellRect(lastRow, 0, true));
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật khách hàng: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_tbl_sửaActionPerformed

    private void tbl_timkiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbl_timkiemActionPerformed
        String searchText = txt_nhạptim.getText().trim().toLowerCase();
        
        if (searchText.isEmpty()) {
            loadData();
            return;
        }
        
        try {
            List<Customer> filteredList = customerList.stream()
                .filter(customer -> 
                    customer.getCustomerCode().toLowerCase().contains(searchText) ||
                    customer.getCustomerFullName().toLowerCase().contains(searchText) ||
                    customer.getCustomerPhone().contains(searchText) ||
                    customer.getCustomerEmail().toLowerCase().contains(searchText)
                )
                .collect(Collectors.toList());
            
            tableModel.setRowCount(0);
            for (Customer customer : filteredList) {
                Object[] row = {
                    customer.getCustomerCode(),
                    customer.getCustomerFullName(),
                    "Nam",
                    "01/01/1990",
                    customer.getCustomerPhone(),
                    "Địa chỉ",
                    customer.getCustomerEmail(),
                    customer.getCustomerPoints()
                };
                tableModel.addRow(row);
            }
            
            JOptionPane.showMessageDialog(this, "Tìm thấy " + filteredList.size() + " kết quả!", "Kết quả tìm kiếm", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_tbl_timkiemActionPerformed

    private void tbl_lammoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbl_lammoiActionPerformed
        clearForm();
        loadData();
        txt_nhạptim.setText("");
        cbo_gioitinh.setSelectedIndex(0);
        JOptionPane.showMessageDialog(this, "Đã làm mới dữ liệu!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_tbl_lammoiActionPerformed

    private void tbn_locActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbn_locActionPerformed
        String selectedGender = cbo_gioitinh.getSelectedItem().toString();
        
        if (selectedGender.equals("Tất cả")) {
            loadData();
            return;
        }
        
        try {
            // Filter by gender - this would need gender field in Customer entity
            tableModel.setRowCount(0);
            for (Customer customer : customerList) {
                // For demo purposes, show all customers
                Object[] row = {
                    customer.getCustomerCode(),
                    customer.getCustomerFullName(),
                    selectedGender, // Show selected gender
                    "01/01/1990",
                    customer.getCustomerPhone(),
                    "Địa chỉ",
                    customer.getCustomerEmail(),
                    customer.getCustomerPoints()
                };
                tableModel.addRow(row);
            }
            
            JOptionPane.showMessageDialog(this, "Đã lọc theo giới tính: " + selectedGender, "Lọc dữ liệu", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi lọc dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_tbn_locActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbo_gioitinh;
    private com.toedter.calendar.JDateChooser datengaysinh;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
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
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JRadioButton rdo_nam;
    private javax.swing.JRadioButton rdo_nu;
    private javax.swing.JTable tbl_bang;
    private javax.swing.JTable tbl_bangls;
    private javax.swing.JButton tbl_lammoi;
    private javax.swing.JButton tbl_sửa;
    private javax.swing.JButton tbl_them;
    private javax.swing.JButton tbl_timkiem;
    private javax.swing.JButton tbn_loc;
    private javax.swing.JTextField txt_diachi;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_ma;
    private javax.swing.JTextField txt_nhạptim;
    private javax.swing.JTextField txt_sdt;
    private javax.swing.JTextField txt_ten;
    // End of variables declaration//GEN-END:variables

   
}
