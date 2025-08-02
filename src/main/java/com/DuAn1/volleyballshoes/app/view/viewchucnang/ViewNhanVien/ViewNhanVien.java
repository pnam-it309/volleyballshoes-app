package com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewNhanVien;

import com.DuAn1.volleyballshoes.app.entity.Staff;
import com.DuAn1.volleyballshoes.app.dao.StaffDAO;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class ViewNhanVien extends javax.swing.JPanel {

    private StaffDAO staffDAO = new StaffDAO();
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    public ViewNhanVien() {
        initComponents();
        loadStaffData();
    }
    
    private void loadStaffData() {
        try {
            // Lấy danh sách nhân viên từ DAO
            List<Staff> staffList = staffDAO.findAll();
            
            // Tạo model cho bảng
            DefaultTableModel model = (DefaultTableModel) tbl_bang1.getModel();
            model.setRowCount(0); // Xóa dữ liệu cũ
            
            // Thêm dữ liệu vào bảng
            for (Staff staff : staffList) {
                Object[] row = new Object[]{
                    staff.getCode(),
                    staff.getName(),
                    staff.getGender(),
                    staff.getPhone(),
                    staff.getEmail(),
                    staff.getRole().equals("QUANLY") ? "Quản lý" : "Nhân viên",
                    staff.getStatus() == 1 ? "Đang làm việc" : "Đã nghỉ việc"
                };
                model.addRow(row);
            }
            
            // Căn chỉnh cột
            tbl_bang1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Lỗi khi tải dữ liệu nhân viên: " + e.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_bang1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        txt_sdt3 = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        txt_hoten3 = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        txt_ma3 = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        txt_email3 = new javax.swing.JTextField();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        rdo_danglamviec3 = new javax.swing.JRadioButton();
        rdo_nghiviec3 = new javax.swing.JRadioButton();
        jLabel55 = new javax.swing.JLabel();
        txt_matkhau3 = new javax.swing.JTextField();
        date_ngaybatdau3 = new com.toedter.calendar.JDateChooser();
        btn_Them1 = new javax.swing.JButton();
        btn_Sua1 = new javax.swing.JButton();
        btn_Xoa1 = new javax.swing.JButton();
        rdo_QuanLy1 = new javax.swing.JRadioButton();
        rdo_NhanVien1 = new javax.swing.JRadioButton();
        btn_LamMoi = new javax.swing.JButton();
        btn_XuatExcel = new javax.swing.JButton();
        btn_Tìm = new javax.swing.JButton();
        txt_Tim = new javax.swing.JTextField();
        btn_Loc = new javax.swing.JButton();
        cbx_TrangThai = new javax.swing.JComboBox<>();

        tbl_bang1.setModel(new javax.swing.table.DefaultTableModel(
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
        tbl_bang1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_bang1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_bang1);

        jLabel1.setBackground(new java.awt.Color(255, 51, 51));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 153, 255));
        jLabel1.setText("Quản Lý Nhân Viên");

        jLabel2.setBackground(new java.awt.Color(255, 51, 51));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 204, 255));
        jLabel2.setText("Thiết Lập Thông Tin Nhân Viên");

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel44.setText("SĐT");

        jLabel46.setText("Họ và Tên:");

        jLabel49.setText("Mã:");

        txt_ma3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_ma2ActionPerformed(evt);
            }
        });

        jLabel50.setText("Chức Vụ");

        jLabel51.setText("Email:");

        jLabel52.setText("Ngày Bắt Đầu");

        jLabel53.setText("Trạng Thái");

        buttonGroup3.add(rdo_danglamviec3);
        rdo_danglamviec3.setText("Đang làm việc");

        buttonGroup3.add(rdo_nghiviec3);
        rdo_nghiviec3.setText("Nghỉ Việc");

        jLabel55.setText("Mật Khẩu");

        date_ngaybatdau3.setDateFormatString("yyyy-MM-dd");

        btn_Them1.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        btn_Them1.setText("Thêm");
        btn_Them1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ThemActionPerformed(evt);
            }
        });

        btn_Sua1.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        btn_Sua1.setText("Sửa");
        btn_Sua1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SuaActionPerformed(evt);
            }
        });

        btn_Xoa1.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        btn_Xoa1.setText("Xóa");
        btn_Xoa1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_XoaActionPerformed(evt);
            }
        });

        buttonGroup2.add(rdo_QuanLy1);
        rdo_QuanLy1.setText("Quản Lý ");

        buttonGroup2.add(rdo_NhanVien1);
        rdo_NhanVien1.setText("Nhân Viên");

        btn_LamMoi.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        btn_LamMoi.setText("Làm Mới");
        btn_LamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_LamMoibtn_ThemActionPerformed(evt);
            }
        });

        btn_XuatExcel.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        btn_XuatExcel.setText("Xuất Excel");
        btn_XuatExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_XuatExcelbtn_ThemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(jLabel44)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txt_hoten3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE)
                            .addComponent(txt_sdt3, javax.swing.GroupLayout.Alignment.LEADING)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(jLabel55)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_matkhau3, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                            .addGap(106, 106, 106)
                            .addComponent(jLabel52)
                            .addGap(551, 551, 551))
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(122, 122, 122)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addComponent(jLabel53)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(rdo_nghiviec3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(rdo_danglamviec3)
                                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txt_email3, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(124, 124, 124))))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addGap(146, 146, 146)
                                        .addComponent(jLabel51))
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addGap(184, 184, 184)
                                        .addComponent(date_ngaybatdau3, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(92, 92, 92))))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(75, 75, 75)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel50)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(btn_Them1)
                                .addGap(18, 18, 18))
                            .addComponent(jLabel49))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(rdo_QuanLy1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rdo_NhanVien1))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(btn_Sua1)
                                .addGap(27, 27, 27)
                                .addComponent(btn_Xoa1)
                                .addGap(18, 18, 18)
                                .addComponent(btn_LamMoi)
                                .addGap(18, 18, 18)
                                .addComponent(btn_XuatExcel))
                            .addComponent(txt_ma3, javax.swing.GroupLayout.PREFERRED_SIZE, 422, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel46)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel46)
                            .addComponent(txt_hoten3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_ma3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel49))
                        .addGap(4, 4, 4)))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdo_QuanLy1)
                    .addComponent(jLabel50)
                    .addComponent(rdo_NhanVien1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44)
                    .addComponent(txt_sdt3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel51)
                    .addComponent(txt_email3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rdo_nghiviec3)
                            .addComponent(jLabel53)
                            .addComponent(rdo_danglamviec3)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(99, 99, 99)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel55)
                            .addComponent(txt_matkhau3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(50, 50, 50)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(date_ngaybatdau3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel52))
                .addGap(33, 33, 33)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_Them1)
                    .addComponent(btn_Sua1)
                    .addComponent(btn_Xoa1)
                    .addComponent(btn_LamMoi)
                    .addComponent(btn_XuatExcel))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        btn_Tìm.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        btn_Tìm.setText("Tìm");
        btn_Tìm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Tìmbtn_ThemActionPerformed(evt);
            }
        });

        btn_Loc.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        btn_Loc.setText("Lọc");
        btn_Loc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Locbtn_ThemActionPerformed(evt);
            }
        });

        cbx_TrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Đang Làm Việc", "Nghỉ Việc" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_Tìm)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_Tim, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                .addGap(348, 348, 348)
                .addComponent(cbx_TrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(128, 128, 128)
                .addComponent(btn_Loc)
                .addGap(58, 58, 58))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addContainerGap(923, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(435, 435, 435))
            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 1100, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_Tìm)
                    .addComponent(txt_Tim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_Loc)
                    .addComponent(cbx_TrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tbl_bang1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_bang1MouseClicked
 try {
        // Get the selected row index
        int selectedRow = tbl_bang1.getSelectedRow();
        
        // If a valid row is selected
        if (selectedRow >= 0) {
            // Get the table model
            DefaultTableModel model = (DefaultTableModel) tbl_bang1.getModel();
            
            // Populate form fields with selected row data
            txt_ma3.setText(model.getValueAt(selectedRow, 0).toString());
            txt_hoten3.setText(model.getValueAt(selectedRow, 1).toString());
            txt_email3.setText(model.getValueAt(selectedRow, 2).toString());
            txt_sdt3.setText(model.getValueAt(selectedRow, 3).toString());
            
            // Handle role selection
            String vaiTro = model.getValueAt(selectedRow, 7).toString();
            if (vaiTro.equals("Quản lý")) {
                rdo_QuanLy1.setSelected(true);
            } else {
                rdo_NhanVien1.setSelected(true);
            }
            
            // Handle status
            String trangThai = model.getValueAt(selectedRow, 8).toString();
            if (trangThai.equals("Đang làm việc")) {
                rdo_danglamviec3.setSelected(true);
            } else {
                rdo_nghiviec3.setSelected(true);
            }
            
            // Set password field (if available in the model)
            if (model.getColumnCount() > 9) {
                Object matKhauObj = model.getValueAt(selectedRow, 9);
                if (matKhauObj != null) {
                    txt_matkhau3.setText(matKhauObj.toString());
                }
            }
            
                // Xử lý địa chỉ (nếu có)
            // txtDiaChi.setText(model.getValueAt(selectedRow, 6) != null ? 
            //     model.getValueAt(selectedRow, 6).toString() : "");
            
            // Xử lý vai trò (sử dụng radio button thay vì combobox)
            String vaiTro = model.getValueAt(selectedRow, 7).toString();
            if (vaiTro.equalsIgnoreCase("Quản lý")) {
                rdo_QuanLy1.setSelected(true);
            } else {
                rdo_NhanVien1.setSelected(true);
            }
            
            // Xử lý trạng thái (sử dụng radio button thay vì checkbox)
            String trangThai = model.getValueAt(selectedRow, 8).toString();
            if (trangThai.equals("Đang làm việc")) {
                rdo_danglamviec3.setSelected(true);
            } else {
                rdo_nghiviec3.setSelected(true);
            }
            
            // Kích hoạt/vô hiệu hóa các nút chức năng
            btn_Sua1.setEnabled(true);
            btn_Xoa1.setEnabled(true);
            btn_Them1.setEnabled(false);
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this,
            "Lỗi khi chọn nhân viên: " + e.getMessage(),
            "Lỗi",
            JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
    }//GEN-LAST:event_tbl_bang1MouseClicked

    private void btn_Tìmbtn_ThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Tìmbtn_ThemActionPerformed
   try {
        // Get the search term and trim any whitespace
        String searchTerm = txt_Tim.getText().trim();
        
        if (searchTerm.isEmpty()) {
            // If search term is empty, reload all employees
            loadEmployeeData();
            return;
        }
        
        // Get the current table model
        DefaultTableModel model = (DefaultTableModel) tbl_bang1.getModel();
        model.setRowCount(0); // Clear existing data
        
        // Get all employees from the database
        List<Employee> employees = employeeService.getAllEmployees();
        
        // Filter employees based on search term (case-insensitive)
        List<Employee> filteredEmployees = employees.stream()
            .filter(emp -> 
                (emp.getEmployeeCode() != null && emp.getEmployeeCode().toLowerCase().contains(searchTerm.toLowerCase())) ||
                (emp.getFullName() != null && emp.getFullName().toLowerCase().contains(searchTerm.toLowerCase())) ||
                (emp.getEmail() != null && emp.getEmail().toLowerCase().contains(searchTerm.toLowerCase())) ||
                (emp.getPhoneNumber() != null && emp.getPhoneNumber().contains(searchTerm)) ||
                (emp.getAddress() != null && emp.getAddress().toLowerCase().contains(searchTerm.toLowerCase())))
            .collect(Collectors.toList());
        
        // If no results found
        if (filteredEmployees.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Không tìm thấy nhân viên nào phù hợp với từ khóa: " + searchTerm,
                "Không có kết quả",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        // Add filtered employees to the table
        for (Employee emp : filteredEmployees) {
            model.addRow(new Object[]{
                emp.getEmployeeCode(),
                emp.getFullName(),
                emp.getEmail(),
                emp.getPhoneNumber(),
                emp.getGender(),
                emp.getDateOfBirth(),
                emp.getAddress(),
                emp.getRole(),
                emp.isActive() ? "Đang làm việc" : "Đã nghỉ việc"
            });
        }
        
        // Clear the search field
        txt_Tim.setText("");
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this,
            "Lỗi khi tìm kiếm nhân viên: " + e.getMessage(),
            "Lỗi",
            JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
    }//GEN-LAST:event_btn_Tìmbtn_ThemActionPerformed

    private void btn_Locbtn_ThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Locbtn_ThemActionPerformed
        try {
            // Get the selected status from the combo box
            String selectedStatus = cbx_TrangThai.getSelectedItem().toString();
            
            // Get the table model
            DefaultTableModel model = (DefaultTableModel) tbl_bang1.getModel();
            model.setRowCount(0); // Clear existing data
            
            // Get all employees from the database
            List<Employee> employees = employeeService.getAllEmployees();
            
            // Filter employees based on selected status
            List<Employee> filteredEmployees = employees.stream()
                .filter(emp -> {
                    String empStatus = emp.isActive() ? "Đang làm việc" : "Đã nghỉ việc";
                    return selectedStatus.equals("Tất cả") || empStatus.equals(selectedStatus);
                })
                .collect(Collectors.toList());
            
            // If no results found
            if (filteredEmployees.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Không có nhân viên nào ở trạng thái: " + selectedStatus,
                    "Không có kết quả",
                    JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            // Add filtered employees to the table
            for (Employee emp : filteredEmployees) {
                model.addRow(new Object[]{
                    emp.getEmployeeCode(),
                    emp.getFullName(),
                    emp.getEmail(),
                    emp.getPhoneNumber(),
                    emp.getGender(),
                    emp.getDateOfBirth(),
                    emp.getAddress(),
                    emp.getRole(),
                    emp.isActive() ? "Đang làm việc" : "Đã nghỉ việc"
                });
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Lỗi khi lọc nhân viên: " + e.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_btn_Locbtn_ThemActionPerformed

    private void btn_XuatExcelbtn_ThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_XuatExcelbtn_ThemActionPerformed
  JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Lưu file Excel");
    fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Files", "xlsx"));
    fileChooser.setSelectedFile(new File("DanhSachNhanVien.xlsx"));
    
    int userSelection = fileChooser.showSaveDialog(this);
    
    if (userSelection == JFileChooser.APPROVE_OPTION) {
        File fileToSave = fileChooser.getSelectedFile();
        // Ensure the file has .xlsx extension
        String filePath = fileToSave.getAbsolutePath();
        if (!filePath.endsWith(".xlsx")) {
            filePath += ".xlsx";
        }
        
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            // Create a new Excel sheet
            XSSFSheet sheet = workbook.createSheet("Danh sách nhân viên");
            
            // Create header row
            String[] headers = {
                "Mã NV", "Họ tên", "Email", "Số điện thoại", 
                "Giới tính", "Ngày sinh", "Địa chỉ", "Vai trò", "Trạng thái"
            };
            
            // Create header style
            XSSFCellStyle headerStyle = workbook.createCellStyle();
            XSSFFont font = workbook.createFont();
            font.setBold(true);
            headerStyle.setFont(font);
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            
            // Create header row
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }
            
            // Get data from table
            DefaultTableModel model = (DefaultTableModel) tbl_bang1.getModel();
            
            // Fill data
            for (int i = 0; i < model.getRowCount(); i++) {
                Row row = sheet.createRow(i + 1);
                for (int j = 0; j < model.getColumnCount(); j++) {
                    Object value = model.getValueAt(i, j);
                    Cell cell = row.createCell(j);
                    if (value != null) {
                        cell.setCellValue(value.toString());
                    }
                }
            }
            
            // Auto-size columns
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }
            
            // Write the output to a file
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
                JOptionPane.showMessageDialog(this,
                    "Xuất dữ liệu thành công!\nĐã lưu tại: " + filePath,
                    "Thành công",
                    JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Lỗi khi xuất file Excel: " + e.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    }//GEN-LAST:event_btn_XuatExcelbtn_ThemActionPerformed

    private void btn_LamMoibtn_ThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_LamMoibtn_ThemActionPerformed
        try {
            // Đặt lại tất cả các trường nhập liệu
            txt_ma3.setText("");
            txt_hoten3.setText("");
            txt_email3.setText("");
            txt_sdt3.setText("");
            date_ngaybatdau3.setDate(null);
            
            // Đặt lại các nút radio giới tính
            buttonGroup1.clearSelection();
            
            // Đặt lại các nút radio vai trò
            buttonGroup2.clearSelection();
            
            // Đặt lại các nút radio trạng thái
            buttonGroup3.clearSelection();
            
            // Đặt lại trường tìm kiếm
            txt_Tim.setText("");
            
            // Đặt lại bộ lọc trạng thái
            cbx_TrangThai.setSelectedIndex(0);
            
            // Kích hoạt/vô hiệu hóa các nút
            btn_Them1.setEnabled(true);
            btn_Sua1.setEnabled(false);
            btn_Xoa1.setEnabled(false);
            
            // Làm mới dữ liệu bảng (nếu cần)
            // loadEmployeeData(); // Bỏ comment nếu có phương thức này
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Lỗi khi làm mới dữ liệu: " + e.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_btn_LamMoibtn_ThemActionPerformed

    private void btn_XoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_XoaActionPerformed
        try {
            // Get the selected row
            int selectedRow = tbl_bang1.getSelectedRow();
            
            // If no row is selected
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn nhân viên cần xóa",
                    "Cảnh báo",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Get employee code from the selected row
            String employeeCode = tbl_bang1.getValueAt(selectedRow, 0).toString();
            String employeeName = tbl_bang1.getValueAt(selectedRow, 1).toString();
            
            // Confirm deletion
            int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa nhân viên: " + employeeName + " (Mã: " + employeeCode + ")?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
            
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    // Gọi DAO/Controller để xóa nhân viên
                    boolean isDeleted = staffDAO.delete(employeeCode);
                    
                    if (isDeleted) {
                        JOptionPane.showMessageDialog(this,
                            "Xóa nhân viên thành công!",
                            "Thành công",
                            JOptionPane.INFORMATION_MESSAGE);
                        
                        // Làm mới dữ liệu bảng
                        loadStaffData();
                        
                        // Đặt lại form
                        btn_LamMoibtn_ThemActionPerformed(null);
                    } else {
                        JOptionPane.showMessageDialog(this,
                            "Không thể xóa nhân viên. Vui lòng thử lại!",
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this,
                        "Lỗi khi xóa nhân viên: " + ex.getMessage(),
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Lỗi: " + e.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_btn_XoaActionPerformed

    private void btn_SuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SuaActionPerformed
        try {
            // Validate required fields
            if (txtMaNhanVien.getText().trim().isEmpty() || 
                txtHoTen.getText().trim().isEmpty() ||
                txtEmail.getText().trim().isEmpty() ||
                txtSoDienThoai.getText().trim().isEmpty() ||
                txtNgaySinh.getDate() == null) {
                
                JOptionPane.showMessageDialog(this,
                    "Vui lòng điền đầy đủ thông tin bắt buộc (*)",
                    "Cảnh báo",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
    
            // Validate email format
            if (!txtEmail.getText().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                JOptionPane.showMessageDialog(this,
                    "Email không đúng định dạng!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
                txtEmail.requestFocus();
                return;
            }
    
            // Validate phone number format
            if (!txtSoDienThoai.getText().matches("(0[3|5|7|8|9])+([0-9]{8})\\b")) {
                JOptionPane.showMessageDialog(this,
                    "Số điện thoại không đúng định dạng!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
                txtSoDienThoai.requestFocus();
                return;
            }
    
            
    
            // Create employee object
            Employee employee = new Employee();
            employee.setEmployeeCode(txtMaNhanVien.getText().trim());
            employee.setFullName(txtHoTen.getText().trim());
            employee.setEmail(txtEmail.getText().trim());
            employee.setPhoneNumber(txtSoDienThoai.getText().trim());
            employee.setGender(gender);
            employee.setDateOfBirth(new java.sql.Date(txtNgaySinh.getDate().getTime()));
            employee.setAddress(txtDiaChi.getText().trim());
            employee.setRole(cboChucVu.getSelectedItem().toString());
            employee.setActive(chkTrangThai.isSelected());
    
            // Confirm update
            int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn cập nhật thông tin nhân viên này?",
                "Xác nhận cập nhật",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
    
            if (confirm == JOptionPane.YES_OPTION) {
                // Call service to update employee
                boolean isUpdated = employeeService.updateEmployee(employee);
                
                if (isUpdated) {
                    JOptionPane.showMessageDialog(this,
                        "Cập nhật thông tin nhân viên thành công!",
                        "Thành công",
                        JOptionPane.INFORMATION_MESSAGE);
                    
                    // Refresh the table
                    loadEmployeeData();
                    
                    // Reset form
                    btn_LamMoibtn_ThemActionPerformed(null);
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Cập nhật thông tin thất bại!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Lỗi khi cập nhật nhân viên: " + e.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_btn_SuaActionPerformed

    private void btn_ThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ThemActionPerformed
        try {
            // Kiểm tra các trường bắt buộc
            if (txt_ma3.getText().trim().isEmpty() || 
                txt_hoten3.getText().trim().isEmpty() ||
                txt_email3.getText().trim().isEmpty() ||
                txt_sdt3.getText().trim().isEmpty() ||
                date_ngaybatdau3.getDate() == null) {
                
                JOptionPane.showMessageDialog(this,
                    "Vui lòng điền đầy đủ thông tin bắt buộc (*)",
                    "Cảnh báo",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
    
            // Kiểm tra định dạng email
            if (!txt_email3.getText().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                JOptionPane.showMessageDialog(this,
                    "Email không đúng định dạng!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
                txt_email3.requestFocus();
                return;
            }
    
            // Kiểm tra định dạng số điện thoại
            if (!txt_sdt3.getText().matches("(0[3|5|7|8|9])+([0-9]{8})\\b")) {
                JOptionPane.showMessageDialog(this,
                    "Số điện thoại không đúng định dạng!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
                txt_sdt3.requestFocus();
                return;
            }
    
            // Kiểm tra mã nhân viên đã tồn tại chưa
            if (staffDAO.existsByCode(txt_ma3.getText().trim())) {
                JOptionPane.showMessageDialog(this,
                    "Mã nhân viên đã tồn tại!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
                txt_ma3.requestFocus();
                return;
            }
    
            // Lấy giới tính từ radio button
            String gender = "Nam"; // Mặc định là Nam vì không có radio button chọn giới tính trong giao diện hiện tại
            
            // Lấy vai trò từ radio button
            String role = rdo_QuanLy1.isSelected() ? "QUANLY" : "NHANVIEN";
            
            // Lấy trạng thái từ radio button
            boolean isActive = rdo_danglamviec3.isSelected();
    
            // Tạo đối tượng Staff
            Staff staff = new Staff();
            staff.setCode(txt_ma3.getText().trim());
            staff.setName(txt_hoten3.getText().trim());
            staff.setEmail(txt_email3.getText().trim());
            staff.setPhone(txt_sdt3.getText().trim());
            staff.setGender(gender);
            staff.setBirthDate(new java.sql.Date(date_ngaybatdau3.getDate().getTime()));
            staff.setPassword(passwordEncoder.encode("123456")); // Mật khẩu mặc định
            staff.setRole(role);
            staff.setStatus(isActive ? 1 : 0);
            staff.setCreatedAt(new java.sql.Date(System.currentTimeMillis()));
            staff.setUpdatedAt(new java.sql.Date(System.currentTimeMillis()));
    
            // Xác nhận thêm
            int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn thêm nhân viên này?",
                "Xác nhận thêm",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
    
            if (confirm == JOptionPane.YES_OPTION) {
                // Gọi DAO/Controller để thêm nhân viên
                Staff savedStaff = staffDAO.create(staff);
                
                if (savedStaff != null) {
                    JOptionPane.showMessageDialog(this,
                        "Thêm nhân viên thành công! Mật khẩu mặc định là: 123456",
                        "Thành công",
                        JOptionPane.INFORMATION_MESSAGE);
                    
                    // Làm mới dữ liệu bảng
                    loadStaffData();
                    
                    // Đặt lại form
                    btn_LamMoibtn_ThemActionPerformed(null);
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Không thể thêm nhân viên. Vui lòng thử lại!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Lỗi khi thêm nhân viên: " + e.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_btn_ThemActionPerformed

    private void txt_ma2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_ma2ActionPerformed

        try {
            String employeeCode = txt_ma2.getText().trim();
            
            if (employeeCode.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Vui lòng nhập mã nhân viên cần tìm",
                    "Cảnh báo",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Search for employee by code
            Employee employee = employeeService.getEmployeeByCode(employeeCode);
            
            if (employee == null) {
                JOptionPane.showMessageDialog(this,
                    "Không tìm thấy nhân viên với mã: " + employeeCode,
                    "Không tìm thấy",
                    JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            // Populate form fields with employee data
            txt_ma3.setText(employee.getEmployeeCode() != null ? employee.getEmployeeCode() : "");
            txt_hoten3.setText(employee.getFullName() != null ? employee.getFullName() : "");
            txt_email3.setText(employee.getEmail() != null ? employee.getEmail() : "");
            txt_sdt3.setText(employee.getPhoneNumber() != null ? employee.getPhoneNumber() : "");
            
 
            
            // Set date of birth - using the correct date field from the form
            if (employee.getDateOfBirth() != null) {
                try {
                    // Convert java.sql.Date to java.util.Date if needed
                    java.util.Date utilDate = new java.util.Date(employee.getDateOfBirth().getTime());
                    date_ngaybatdau3.setDate(utilDate);
                } catch (Exception ex) {
                    System.err.println("Error setting date: " + ex.getMessage());
                }
            }
            
            // Set address if the field exists
            // txtDiaChi.setText(employee.getAddress() != null ? employee.getAddress() : "");
            
            // Set role in radio buttons
            String role = employee.getRole();
            if (role != null) {
                if (role.equalsIgnoreCase("Quản lý")) {
                    rdo_QuanLy1.setSelected(true);
                } else {
                    rdo_NhanVien1.setSelected(true);
                }
            }
            
            // Set status in radio buttons
            if (employee.isActive()) {
                rdo_danglamviec3.setSelected(true);
            } else {
                rdo_nghiviec3.setSelected(true);
            }
            
            // Enable/disable buttons
            btn_Them1.setEnabled(false);
            btn_Sua1.setEnabled(true);
            btn_Xoa1.setEnabled(true);
            
            // Focus on the next field
            txt_hoten3.requestFocus();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Lỗi khi tìm kiếm nhân viên: " + e.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_txt_ma2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_LamMoi;
    private javax.swing.JButton btn_Loc;
    private javax.swing.JButton btn_Sua1;
    private javax.swing.JButton btn_Them1;
    private javax.swing.JButton btn_Tìm;
    private javax.swing.JButton btn_Xoa1;
    private javax.swing.JButton btn_XuatExcel;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JComboBox<String> cbx_TrangThai;
    private com.toedter.calendar.JDateChooser date_ngaybatdau3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rdo_NhanVien1;
    private javax.swing.JRadioButton rdo_QuanLy1;
    private javax.swing.JRadioButton rdo_danglamviec3;
    private javax.swing.JRadioButton rdo_nghiviec3;
    private javax.swing.JTable tbl_bang1;
    private javax.swing.JTextField txt_Tim;
    private javax.swing.JTextField txt_email3;
    private javax.swing.JTextField txt_hoten3;
    private javax.swing.JTextField txt_ma3;
    private javax.swing.JTextField txt_matkhau3;
    private javax.swing.JTextField txt_sdt3;
    // End of variables declaration//GEN-END:variables
}
