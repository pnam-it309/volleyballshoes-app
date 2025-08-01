package com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewNhanVien;

import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.stream.Collectors;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ViewNhanVien extends javax.swing.JPanel {

 
    public ViewNhanVien() {
        initComponents();
 
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
        btn_T├¼m = new javax.swing.JButton();
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
        jLabel1.setText("Quß║ún L├╜ Nh├ón Vi├¬n");

        jLabel2.setBackground(new java.awt.Color(255, 51, 51));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 204, 255));
        jLabel2.setText("Thiß║┐t Lß║¡p Th├┤ng Tin Nh├ón Vi├¬n");

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel44.setText("S─ÉT");

        jLabel46.setText("Hß╗ì v├á T├¬n:");

        jLabel49.setText("M├ú:");

        txt_ma3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_ma2ActionPerformed(evt);
            }
        });

        jLabel50.setText("Chß╗⌐c Vß╗Ñ");

        jLabel51.setText("Email:");

        jLabel52.setText("Ng├áy Bß║»t ─Éß║ºu");

        jLabel53.setText("Trß║íng Th├íi");

        buttonGroup3.add(rdo_danglamviec3);
        rdo_danglamviec3.setText("─Éang l├ám viß╗çc");

        buttonGroup3.add(rdo_nghiviec3);
        rdo_nghiviec3.setText("Nghß╗ë Viß╗çc");

        jLabel55.setText("Mß║¡t Khß║⌐u");

        date_ngaybatdau3.setDateFormatString("yyyy-MM-dd");

        btn_Them1.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        btn_Them1.setText("Th├¬m");
        btn_Them1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ThemActionPerformed(evt);
            }
        });

        btn_Sua1.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        btn_Sua1.setText("Sß╗¡a");
        btn_Sua1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SuaActionPerformed(evt);
            }
        });

        btn_Xoa1.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        btn_Xoa1.setText("X├│a");
        btn_Xoa1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_XoaActionPerformed(evt);
            }
        });

        buttonGroup2.add(rdo_QuanLy1);
        rdo_QuanLy1.setText("Quß║ún L├╜ ");

        buttonGroup2.add(rdo_NhanVien1);
        rdo_NhanVien1.setText("Nh├ón Vi├¬n");

        btn_LamMoi.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        btn_LamMoi.setText("L├ám Mß╗¢i");
        btn_LamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_LamMoibtn_ThemActionPerformed(evt);
            }
        });

        btn_XuatExcel.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        btn_XuatExcel.setText("Xuß║Ñt Excel");
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

        btn_T├¼m.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        btn_T├¼m.setText("T├¼m");
        btn_T├¼m.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_T├¼mbtn_ThemActionPerformed(evt);
            }
        });

        btn_Loc.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        btn_Loc.setText("Lß╗ìc");
        btn_Loc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Locbtn_ThemActionPerformed(evt);
            }
        });

        cbx_TrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "─Éang L├ám Viß╗çc", "Nghß╗ë Viß╗çc" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_T├¼m)
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
                    .addComponent(btn_T├¼m)
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
            txtMaNhanVien.setText(model.getValueAt(selectedRow, 0).toString());
            txtHoTen.setText(model.getValueAt(selectedRow, 1).toString());
            txtEmail.setText(model.getValueAt(selectedRow, 2).toString());
            txtSoDienThoai.setText(model.getValueAt(selectedRow, 3).toString());
            
            // Handle gender selection
            String gioiTinh = model.getValueAt(selectedRow, 4).toString();
            if (gioiTinh.equals("Nam")) {
                rdoNam.setSelected(true);
            } else if (gioiTinh.equals("Nß╗»")) {
                rdoNu.setSelected(true);
            } else {
                rdoKhac.setSelected(true);
            }
            
            // Handle date of birth
            try {
                Date ngaySinh = (Date) model.getValueAt(selectedRow, 5);
                if (ngaySinh != null) {
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    txtNgaySinh.setText(dateFormat.format(ngaySinh));
                }
            } catch (Exception e) {
                txtNgaySinh.setText("");
            }
            
            // Handle address
            txtDiaChi.setText(model.getValueAt(selectedRow, 6) != null ? 
                model.getValueAt(selectedRow, 6).toString() : "");
                
            // Handle role
            String vaiTro = model.getValueAt(selectedRow, 7).toString();
            cboChucVu.setSelectedItem(vaiTro);
            
            // Handle status
            String trangThai = model.getValueAt(selectedRow, 8).toString();
            chkTrangThai.setSelected(trangThai.equals("─Éang l├ám viß╗çc"));
            
            // Enable/disable buttons
            btnSua.setEnabled(true);
            btnXoa.setEnabled(true);
            btnThem.setEnabled(false);
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this,
            "Lß╗ùi khi chß╗ìn nh├ón vi├¬n: " + e.getMessage(),
            "Lß╗ùi",
            JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
    }//GEN-LAST:event_tbl_bang1MouseClicked

    private void btn_T├¼mbtn_ThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_T├¼mbtn_ThemActionPerformed
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
                "Kh├┤ng t├¼m thß║Ñy nh├ón vi├¬n n├áo ph├╣ hß╗úp vß╗¢i tß╗½ kh├│a: " + searchTerm,
                "Kh├┤ng c├│ kß║┐t quß║ú",
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
                emp.isActive() ? "─Éang l├ám viß╗çc" : "─É├ú nghß╗ë viß╗çc"
            });
        }
        
        // Clear the search field
        txt_Tim.setText("");
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this,
            "Lß╗ùi khi t├¼m kiß║┐m nh├ón vi├¬n: " + e.getMessage(),
            "Lß╗ùi",
            JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
    }//GEN-LAST:event_btn_T├¼mbtn_ThemActionPerformed

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
                    String empStatus = emp.isActive() ? "─Éang l├ám viß╗çc" : "─É├ú nghß╗ë viß╗çc";
                    return selectedStatus.equals("Tß║Ñt cß║ú") || empStatus.equals(selectedStatus);
                })
                .collect(Collectors.toList());
            
            // If no results found
            if (filteredEmployees.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Kh├┤ng c├│ nh├ón vi├¬n n├áo ß╗ƒ trß║íng th├íi: " + selectedStatus,
                    "Kh├┤ng c├│ kß║┐t quß║ú",
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
                    emp.isActive() ? "─Éang l├ám viß╗çc" : "─É├ú nghß╗ë viß╗çc"
                });
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Lß╗ùi khi lß╗ìc nh├ón vi├¬n: " + e.getMessage(),
                "Lß╗ùi",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_btn_Locbtn_ThemActionPerformed

    private void btn_XuatExcelbtn_ThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_XuatExcelbtn_ThemActionPerformed
  JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("L╞░u file Excel");
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
            XSSFSheet sheet = workbook.createSheet("Danh s├ích nh├ón vi├¬n");
            
            // Create header row
            String[] headers = {
                "M├ú NV", "Hß╗ì t├¬n", "Email", "Sß╗æ ─æiß╗çn thoß║íi", 
                "Giß╗¢i t├¡nh", "Ng├áy sinh", "─Éß╗ïa chß╗ë", "Vai tr├▓", "Trß║íng th├íi"
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
                    "Xuß║Ñt dß╗» liß╗çu th├ánh c├┤ng!\n─É├ú l╞░u tß║íi: " + filePath,
                    "Th├ánh c├┤ng",
                    JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Lß╗ùi khi xuß║Ñt file Excel: " + e.getMessage(),
                "Lß╗ùi",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    }//GEN-LAST:event_btn_XuatExcelbtn_ThemActionPerformed

    private void btn_LamMoibtn_ThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_LamMoibtn_ThemActionPerformed
        try {
            // Reset all input fields
            txtMaNhanVien.setText("");
            txtHoTen.setText("");
            txtEmail.setText("");
            txtSoDienThoai.setText("");
            txtNgaySinh.setDate(null);
            txtDiaChi.setText("");
            
            // Reset radio buttons
            buttonGroup1.clearSelection();
            
            // Reset combo box
            cboChucVu.setSelectedIndex(0);
            
            // Reset check box
            chkTrangThai.setSelected(true);
            
            // Reset search field
            txt_Tim.setText("");
            
            // Reset status filter
            cbx_TrangThai.setSelectedIndex(0);
            
            // Reload all employees
            loadEmployeeData();
            
            // Enable/disable buttons
            btnThem.setEnabled(true);
            btnSua.setEnabled(false);
            btnXoa.setEnabled(false);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Lß╗ùi khi l├ám mß╗¢i dß╗» liß╗çu: " + e.getMessage(),
                "Lß╗ùi",
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
                    "Vui l├▓ng chß╗ìn nh├ón vi├¬n cß║ºn x├│a",
                    "Cß║únh b├ío",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Get employee code from the selected row
            String employeeCode = tbl_bang1.getValueAt(selectedRow, 0).toString();
            String employeeName = tbl_bang1.getValueAt(selectedRow, 1).toString();
            
            // Confirm deletion
            int confirm = JOptionPane.showConfirmDialog(this,
                "Bß║ín c├│ chß║»c chß║»n muß╗æn x├│a nh├ón vi├¬n: " + employeeName + " (M├ú: " + employeeCode + ")?",
                "X├íc nhß║¡n x├│a",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
            
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    // Call service to delete employee
                    boolean isDeleted = employeeService.deleteEmployee(employeeCode);
                    
                    if (isDeleted) {
                        JOptionPane.showMessageDialog(this,
                            "X├│a nh├ón vi├¬n th├ánh c├┤ng!",
                            "Th├ánh c├┤ng",
                            JOptionPane.INFORMATION_MESSAGE);
                        
                        // Refresh the table
                        loadEmployeeData();
                        
                        // Reset form
                        btn_LamMoibtn_ThemActionPerformed(null);
                    } else {
                        JOptionPane.showMessageDialog(this,
                            "Kh├┤ng thß╗â x├│a nh├ón vi├¬n. Vui l├▓ng thß╗¡ lß║íi!",
                            "Lß╗ùi",
                            JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this,
                        "Lß╗ùi khi x├│a nh├ón vi├¬n: " + ex.getMessage(),
                        "Lß╗ùi",
                        JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Lß╗ùi: " + e.getMessage(),
                "Lß╗ùi",
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
                    "Vui l├▓ng ─æiß╗ün ─æß║ºy ─æß╗º th├┤ng tin bß║»t buß╗Öc (*)",
                    "Cß║únh b├ío",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
    
            // Validate email format
            if (!txtEmail.getText().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                JOptionPane.showMessageDialog(this,
                    "Email kh├┤ng ─æ├║ng ─æß╗ïnh dß║íng!",
                    "Lß╗ùi",
                    JOptionPane.ERROR_MESSAGE);
                txtEmail.requestFocus();
                return;
            }
    
            // Validate phone number format
            if (!txtSoDienThoai.getText().matches("(0[3|5|7|8|9])+([0-9]{8})\\b")) {
                JOptionPane.showMessageDialog(this,
                    "Sß╗æ ─æiß╗çn thoß║íi kh├┤ng ─æ├║ng ─æß╗ïnh dß║íng!",
                    "Lß╗ùi",
                    JOptionPane.ERROR_MESSAGE);
                txtSoDienThoai.requestFocus();
                return;
            }
    
            // Get gender
            String gender = "";
            if (rdoNam.isSelected()) {
                gender = "Nam";
            } else if (rdoNu.isSelected()) {
                gender = "Nß╗»";
            } else {
                gender = "Kh├íc";
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
                "Bß║ín c├│ chß║»c chß║»n muß╗æn cß║¡p nhß║¡t th├┤ng tin nh├ón vi├¬n n├áy?",
                "X├íc nhß║¡n cß║¡p nhß║¡t",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
    
            if (confirm == JOptionPane.YES_OPTION) {
                // Call service to update employee
                boolean isUpdated = employeeService.updateEmployee(employee);
                
                if (isUpdated) {
                    JOptionPane.showMessageDialog(this,
                        "Cß║¡p nhß║¡t th├┤ng tin nh├ón vi├¬n th├ánh c├┤ng!",
                        "Th├ánh c├┤ng",
                        JOptionPane.INFORMATION_MESSAGE);
                    
                    // Refresh the table
                    loadEmployeeData();
                    
                    // Reset form
                    btn_LamMoibtn_ThemActionPerformed(null);
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Cß║¡p nhß║¡t th├┤ng tin thß║Ñt bß║íi!",
                        "Lß╗ùi",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Lß╗ùi khi cß║¡p nhß║¡t nh├ón vi├¬n: " + e.getMessage(),
                "Lß╗ùi",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_btn_SuaActionPerformed

    private void btn_ThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ThemActionPerformed
        try {
            // Validate required fields
            if (txtHoTen.getText().trim().isEmpty() ||
                txtEmail.getText().trim().isEmpty() ||
                txtSoDienThoai.getText().trim().isEmpty() ||
                txtNgaySinh.getDate() == null) {
                
                JOptionPane.showMessageDialog(this,
                    "Vui l├▓ng ─æiß╗ün ─æß║ºy ─æß╗º th├┤ng tin bß║»t buß╗Öc (*)",
                    "Cß║únh b├ío",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
    
            // Validate email format
            if (!txtEmail.getText().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                JOptionPane.showMessageDialog(this,
                    "Email kh├┤ng ─æ├║ng ─æß╗ïnh dß║íng!",
                    "Lß╗ùi",
                    JOptionPane.ERROR_MESSAGE);
                txtEmail.requestFocus();
                return;
            }
    
            // Validate phone number format
            if (!txtSoDienThoai.getText().matches("(0[3|5|7|8|9])+([0-9]{8})\\b")) {
                JOptionPane.showMessageDialog(this,
                    "Sß╗æ ─æiß╗çn thoß║íi kh├┤ng ─æ├║ng ─æß╗ïnh dß║íng!",
                    "Lß╗ùi",
                    JOptionPane.ERROR_MESSAGE);
                txtSoDienThoai.requestFocus();
                return;
            }
    
            // Get gender
            String gender = "";
            if (rdoNam.isSelected()) {
                gender = "Nam";
            } else if (rdoNu.isSelected()) {
                gender = "Nß╗»";
            } else {
                gender = "Kh├íc";
            }
    
            // Generate employee code (example: NV + timestamp)
            String employeeCode = "NV" + System.currentTimeMillis();
    
            // Create employee object
            Employee employee = new Employee();
            employee.setEmployeeCode(employeeCode);
            employee.setFullName(txtHoTen.getText().trim());
            employee.setEmail(txtEmail.getText().trim());
            employee.setPhoneNumber(txtSoDienThoai.getText().trim());
            employee.setGender(gender);
            employee.setDateOfBirth(new java.sql.Date(txtNgaySinh.getDate().getTime()));
            employee.setAddress(txtDiaChi.getText().trim());
            employee.setRole(cboChucVu.getSelectedItem().toString());
            employee.setActive(chkTrangThai.isSelected());
    
            // Confirm add
            int confirm = JOptionPane.showConfirmDialog(this,
                "Bß║ín c├│ chß║»c chß║»n muß╗æn th├¬m nh├ón vi├¬n mß╗¢i?",
                "X├íc nhß║¡n th├¬m mß╗¢i",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
    
            if (confirm == JOptionPane.YES_OPTION) {
                // Call service to add employee
                boolean isAdded = employeeService.addEmployee(employee);
                
                if (isAdded) {
                    JOptionPane.showMessageDialog(this,
                        "Th├¬m nh├ón vi├¬n mß╗¢i th├ánh c├┤ng!",
                        "Th├ánh c├┤ng",
                        JOptionPane.INFORMATION_MESSAGE);
                    
                    // Refresh the table
                    loadEmployeeData();
                    
                    // Reset form
                    btn_LamMoibtn_ThemActionPerformed(null);
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Th├¬m nh├ón vi├¬n thß║Ñt bß║íi!",
                        "Lß╗ùi",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Lß╗ùi khi th├¬m nh├ón vi├¬n: " + e.getMessage(),
                "Lß╗ùi",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_btn_ThemActionPerformed

    private void txt_ma2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_ma2ActionPerformed

        try {
            String employeeCode = txt_ma2.getText().trim();
            
            if (employeeCode.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Vui l├▓ng nhß║¡p m├ú nh├ón vi├¬n cß║ºn t├¼m",
                    "Cß║únh b├ío",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Search for employee by code
            Employee employee = employeeService.getEmployeeByCode(employeeCode);
            
            if (employee == null) {
                JOptionPane.showMessageDialog(this,
                    "Kh├┤ng t├¼m thß║Ñy nh├ón vi├¬n vß╗¢i m├ú: " + employeeCode,
                    "Kh├┤ng t├¼m thß║Ñy",
                    JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            // Populate form fields with employee data
            txtMaNhanVien.setText(employee.getEmployeeCode());
            txtHoTen.setText(employee.getFullName());
            txtEmail.setText(employee.getEmail());
            txtSoDienThoai.setText(employee.getPhoneNumber());
            
            // Set gender radio button
            if ("Nam".equals(employee.getGender())) {
                rdoNam.setSelected(true);
            } else if ("Nß╗»".equals(employee.getGender())) {
                rdoNu.setSelected(true);
            } else {
                rdoKhac.setSelected(true);
            }
            
            // Set date of birth
            if (employee.getDateOfBirth() != null) {
                txtNgaySinh.setDate(new Date(employee.getDateOfBirth().getTime()));
            }
            
            txtDiaChi.setText(employee.getAddress());
            
            // Set role in combo box
            cboChucVu.setSelectedItem(employee.getRole());
            
            // Set status
            chkTrangThai.setSelected(employee.isActive());
            
            // Enable/disable buttons
            btnThem.setEnabled(false);
            btnSua.setEnabled(true);
            btnXoa.setEnabled(true);
            
            // Focus on the next field
            txtHoTen.requestFocus();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Lß╗ùi khi t├¼m kiß║┐m nh├ón vi├¬n: " + e.getMessage(),
                "Lß╗ùi",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_txt_ma2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_LamMoi;
    private javax.swing.JButton btn_Loc;
    private javax.swing.JButton btn_Sua1;
    private javax.swing.JButton btn_Them1;
    private javax.swing.JButton btn_T├¼m;
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
