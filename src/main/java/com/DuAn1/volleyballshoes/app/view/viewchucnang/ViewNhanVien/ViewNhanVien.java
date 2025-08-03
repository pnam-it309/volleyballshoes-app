package com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewNhanVien;
import com.DuAn1.volleyballshoes.app.entity.Staff;
import com.DuAn1.volleyballshoes.app.dao.StaffDAO;
import com.DuAn1.volleyballshoes.app.dao.impl.StaffDAOImpl;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ViewNhanVien extends javax.swing.JPanel {

    private StaffDAO staffDAO = new StaffDAOImpl();

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

            // Đặt tên cột cho bảng (thêm cột mật khẩu)
            String[] columnNames = {"Mã NV", "Họ tên", "SĐT", "Email", "Mật khẩu", "Vai trò", "Trạng thái"};
            model.setColumnIdentifiers(columnNames);

            for (Staff staff : staffList) {
                Object[] row = new Object[] {
                        staff.getStaffCode(), // Mã nhân viên
                        staff.getStaffFullName(), // Tên nhân viên
                        staff.getStaffSdt(), // Số điện thoại
                        staff.getStaffEmail(), // Email
                        staff.getStaffPassword() != null ? "***" : "Chưa đặt", // Mật khẩu (ẩn)
                        staff.getStaffRole() == 1 ? "Quản lý" : "Nhân viên", // Vai trò
                        "Đang làm việc" // Trạng thái mặc định (có thể thêm field này vào entity sau)
                };
                model.addRow(row);
            }

            // Căn chỉnh cột
            tbl_bang1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
            
            // Reset form buttons
            btn_Them1.setEnabled(true);
            btn_Sua1.setEnabled(false);
            btn_Xoa1.setEnabled(false);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi tải dữ liệu nhân viên: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
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
        jLabel53 = new javax.swing.JLabel();
        rdo_danglamviec3 = new javax.swing.JRadioButton();
        rdo_nghiviec3 = new javax.swing.JRadioButton();
        jLabel55 = new javax.swing.JLabel();
        txt_matkhau3 = new javax.swing.JTextField();
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
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Họ Và Tên", "Mã", "Sđt", "Email", "Mật Khẩu", "Chức Vụ", "Trạng Thái", "Ngày Bắt Đầu"
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

        txt_sdt3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_sdt3ActionPerformed(evt);
            }
        });

        jLabel46.setText("Họ và Tên:");

        jLabel49.setText("Mã:");

        txt_ma3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_ma2ActionPerformed(evt);
            }
        });

        jLabel50.setText("Chức Vụ");

        jLabel51.setText("Email:");

        txt_email3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_email3ActionPerformed(evt);
            }
        });

        jLabel53.setText("Trạng Thái");

        buttonGroup3.add(rdo_danglamviec3);
        rdo_danglamviec3.setText("Đang làm việc");

        buttonGroup3.add(rdo_nghiviec3);
        rdo_nghiviec3.setText("Nghỉ Việc");

        jLabel55.setText("Mật Khẩu");

        txt_matkhau3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_matkhau3ActionPerformed(evt);
            }
        });

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
                        .addComponent(jLabel55)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_matkhau3, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel46)
                                    .addComponent(jLabel44))
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addGap(2, 2, 2)
                                        .addComponent(txt_hoten3, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txt_sdt3, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(btn_Them1)
                                .addGap(18, 18, 18)
                                .addComponent(btn_Sua1)
                                .addGap(18, 18, 18)
                                .addComponent(btn_Xoa1)
                                .addGap(18, 18, 18)
                                .addComponent(btn_LamMoi)
                                .addGap(18, 18, 18)
                                .addComponent(btn_XuatExcel)))))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(125, 125, 125)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel49)
                            .addComponent(jLabel50)
                            .addComponent(jLabel51))
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(rdo_QuanLy1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rdo_NhanVien1))
                            .addComponent(txt_ma3, javax.swing.GroupLayout.PREFERRED_SIZE, 422, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_email3, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(127, 127, 127)
                        .addComponent(jLabel53)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rdo_nghiviec3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rdo_danglamviec3)))
                .addContainerGap(53, Short.MAX_VALUE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44)
                    .addComponent(txt_sdt3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel51)
                    .addComponent(txt_email3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel55)
                    .addComponent(txt_matkhau3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel53)
                    .addComponent(rdo_nghiviec3)
                    .addComponent(rdo_danglamviec3)
                    .addComponent(btn_Them1)
                    .addComponent(btn_Sua1)
                    .addComponent(btn_Xoa1)
                    .addComponent(btn_LamMoi)
                    .addComponent(btn_XuatExcel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btn_Tìm.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        btn_Tìm.setText("Tìm");
        btn_Tìm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Tìmbtn_ThemActionPerformed(evt);
            }
        });

        txt_Tim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_TimActionPerformed(evt);
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
                .addComponent(jLabel2)
                .addContainerGap(943, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(435, 435, 435))
            .addComponent(jScrollPane1)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_Tìm)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_Tim, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                .addGap(348, 348, 348)
                .addComponent(cbx_TrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(128, 128, 128)
                .addComponent(btn_Loc)
                .addGap(58, 58, 58))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_Tìm)
                    .addComponent(txt_Tim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_Loc)
                    .addComponent(cbx_TrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txt_sdt3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_sdt3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_sdt3ActionPerformed

    private void txt_matkhau3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_matkhau3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_matkhau3ActionPerformed

    private void txt_email3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_email3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_email3ActionPerformed

    private void txt_TimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_TimActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_TimActionPerformed

    private void tbl_bang1MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_tbl_bang1MouseClicked
        try {
            // Get the selected row index
            int selectedRow = tbl_bang1.getSelectedRow();

            // If a valid row is selected
            if (selectedRow >= 0) {
                // Get the table model
                DefaultTableModel model = (DefaultTableModel) tbl_bang1.getModel();

                // Populate form fields with selected row data
                // Kiểm tra số cột có sẵn trước khi truy cập
                int columnCount = model.getColumnCount();
                
                // Cột 0: Mã NV, Cột 1: Họ tên, Cột 2: SĐT, Cột 3: Email, Cột 4: Mật khẩu
                if (columnCount > 0) {
                    Object value = model.getValueAt(selectedRow, 0);
                    txt_ma3.setText(value != null ? value.toString() : "");
                }
                if (columnCount > 1) {
                    Object value = model.getValueAt(selectedRow, 1);
                    txt_hoten3.setText(value != null ? value.toString() : "");
                }
                if (columnCount > 2) {
                    Object value = model.getValueAt(selectedRow, 2);
                    txt_sdt3.setText(value != null ? value.toString() : "");
                }
                if (columnCount > 3) {
                    Object value = model.getValueAt(selectedRow, 3);
                    txt_email3.setText(value != null ? value.toString() : "");
                }

                // Lấy mật khẩu thực từ database thay vì hiển thị "***"
                String staffCode = txt_ma3.getText().trim();
                if (!staffCode.isEmpty()) {
                    Staff selectedStaff = staffDAO.findAll().stream()
                        .filter(s -> staffCode.equals(s.getStaffCode()))
                        .findFirst()
                        .orElse(null);
                    if (selectedStaff != null && selectedStaff.getStaffPassword() != null) {
                        txt_matkhau3.setText(selectedStaff.getStaffPassword());
                    } else {
                        txt_matkhau3.setText("");
                    }
                }

                // Handle role selection (cột 5) - nếu có
                if (columnCount > 5) {
                    Object roleValue = model.getValueAt(selectedRow, 5);
                    String vaiTro = roleValue != null ? roleValue.toString() : "";
                    if (vaiTro.equals("Quản lý")) {
                        rdo_QuanLy1.setSelected(true);
                    } else {
                        rdo_NhanVien1.setSelected(true);
                    }
                } else {
                    // Mặc định chọn nhân viên nếu không có thông tin vai trò
                    rdo_NhanVien1.setSelected(true);
                }

                // Handle status (cột 6) - nếu có
                if (columnCount > 6) {
                    Object statusValue = model.getValueAt(selectedRow, 6);
                    String trangThai = statusValue != null ? statusValue.toString() : "";
                    if (trangThai.equals("Đang làm việc")) {
                        rdo_danglamviec3.setSelected(true);
                    } else {
                        rdo_nghiviec3.setSelected(true);
                    }
                } else {
                    // Mặc định chọn đang làm việc nếu không có thông tin trạng thái
                    rdo_danglamviec3.setSelected(true);
                }

                // Kích hoạt/vô hiệu hóa các nút chức năng
                btn_Sua1.setEnabled(true);
                btn_Xoa1.setEnabled(true);
                btn_Them1.setEnabled(false);
                
                // Disable mã nhân viên khi chỉnh sửa
                txt_ma3.setEnabled(false);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi chọn nhân viên: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }// GEN-LAST:event_tbl_bang1MouseClicked

    private void txt_ma2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txt_ma2ActionPerformed

        try {
            String employeeCode = txt_ma3.getText().trim();

            if (employeeCode.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng nhập mã nhân viên cần tìm",
                        "Cảnh báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Tìm kiếm nhân viên theo mã
            Staff staff = staffDAO.findAll().stream()
                .filter(s -> employeeCode.equalsIgnoreCase(s.getStaffCode()))
                .findFirst()
                .orElse(null);

            if (staff == null) {
                JOptionPane.showMessageDialog(this,
                        "Không tìm thấy nhân viên với mã: " + employeeCode,
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Hiển thị thông tin nhân viên lên form
            txt_ma3.setText(staff.getStaffCode() != null ? staff.getStaffCode() : "");
            txt_hoten3.setText(staff.getStaffFullName() != null ? staff.getStaffFullName() : "");
            txt_email3.setText(staff.getStaffEmail() != null ? staff.getStaffEmail() : "");
            txt_sdt3.setText(staff.getStaffSdt() != null ? staff.getStaffSdt() : "");
            // Nếu có trường ngày bắt đầu làm việc, bạn cần xử lý thêm ở đây
            // Set role in radio buttons
            if (staff.getStaffRole() == 1) {
                rdo_QuanLy1.setSelected(true);
            } else {
                rdo_NhanVien1.setSelected(true);
            }
            // Không có trường trạng thái hoặc giới tính nên bỏ qua
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
    }// GEN-LAST:event_txt_ma2ActionPerformed

    private void btn_Tìmbtn_ThemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_Tìmbtn_ThemActionPerformed
        try {
            // Get the search term and trim any whitespace
            String searchTerm = txt_Tim.getText().trim();

            if (searchTerm.isEmpty()) {
                // Nếu ô tìm kiếm rỗng, load lại toàn bộ nhân viên
                loadStaffData();
                return;
            }

            // Lấy model của bảng
            DefaultTableModel model = (DefaultTableModel) tbl_bang1.getModel();
            model.setRowCount(0); // Xóa dữ liệu cũ

            // Lấy toàn bộ nhân viên từ DAO
            List<Staff> staffList = staffDAO.findAll();

            // Lọc nhân viên theo từ khóa (không phân biệt hoa thường)
            List<Staff> filteredStaff = staffList.stream()
                    .filter(staff -> (staff.getStaffCode() != null
                            && staff.getStaffCode().toLowerCase().contains(searchTerm.toLowerCase()))
                            || (staff.getStaffFullName() != null
                                    && staff.getStaffFullName().toLowerCase().contains(searchTerm.toLowerCase()))
                            || (staff.getStaffEmail() != null
                                    && staff.getStaffEmail().toLowerCase().contains(searchTerm.toLowerCase()))
                            || (staff.getStaffSdt() != null && staff.getStaffSdt().contains(searchTerm))
                    )
                    .collect(Collectors.toList());
            
            // If no results found
            if (filteredStaff.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Không tìm thấy nhân viên nào phù hợp với từ khóa: " + searchTerm,
                        "Không có kết quả",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Add filtered employees to the table
            for (Staff emp : filteredStaff) {
                model.addRow(new Object[] {
                        emp.getStaffCode(), // Mã nhân viên
                        emp.getStaffFullName(), // Tên nhân viên
                        emp.getStaffSdt(), // Số điện thoại
                        emp.getStaffEmail(), // Email
                        emp.getStaffPassword() != null ? "***" : "Chưa đặt", // Mật khẩu (ẩn)
                        emp.getStaffRole() == 1 ? "Quản lý" : "Nhân viên", // Vai trò
                        "Đang làm việc" // Trạng thái
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
    }// GEN-LAST:event_btn_Tìmbtn_ThemActionPerformed

    private void btn_Locbtn_ThemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_Locbtn_ThemActionPerformed
        try {
            // Get the selected status from the combo box
            String selectedStatus = cbx_TrangThai.getSelectedItem().toString();

            // Get the table model
            DefaultTableModel model = (DefaultTableModel) tbl_bang1.getModel();
            model.setRowCount(0); // Xóa dữ liệu cũ

            // Lấy toàn bộ nhân viên từ DAO
            List<Staff> staffList = staffDAO.findAll();

            // Nếu không có kết quả
            if (staffList.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Không có nhân viên nào ở trạng thái: " + selectedStatus,
                        "Không có kết quả",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Hiển thị danh sách nhân viên đã lọc lên bảng
            for (Staff emp : staffList) {
                model.addRow(new Object[] {
                        emp.getStaffCode(), // Mã nhân viên
                        emp.getStaffFullName(), // Tên nhân viên
                        emp.getStaffSdt(), // Số điện thoại
                        emp.getStaffEmail(), // Email
                        emp.getStaffPassword() != null ? "***" : "Chưa đặt", // Mật khẩu (ẩn)
                        emp.getStaffRole() == 1 ? "Quản lý" : "Nhân viên", // Vai trò
                        "Đang làm việc" // Trạng thái
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi lọc nhân viên: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }// GEN-LAST:event_btn_Locbtn_ThemActionPerformed

    private void btn_XuatExcelbtn_ThemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_XuatExcelbtn_ThemActionPerformed
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
                        "Mật khẩu", "Vai trò", "Trạng thái"
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
    }// GEN-LAST:event_btn_XuatExcelbtn_ThemActionPerformed

    private void btn_LamMoibtn_ThemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_LamMoibtn_ThemActionPerformed
        try {
            // Đặt lại tất cả các trường nhập liệu
            txt_ma3.setText("");
            txt_hoten3.setText("");
            txt_email3.setText("");
            txt_sdt3.setText("");
            txt_matkhau3.setText("");

            // Đặt lại các nút radio giới tính
            buttonGroup1.clearSelection();

            // Đặt lại các nút radio vai trò
            buttonGroup2.clearSelection();
            rdo_NhanVien1.setSelected(true); // Mặc định chọn nhân viên

            // Đặt lại các nút radio trạng thái
            buttonGroup3.clearSelection();
            rdo_danglamviec3.setSelected(true); // Mặc định chọn đang làm việc

            // Đặt lại trường tìm kiếm
            txt_Tim.setText("");

            // Đặt lại bộ lọc trạng thái
            if (cbx_TrangThai.getItemCount() > 0) {
                cbx_TrangThai.setSelectedIndex(0);
            }

            // Kích hoạt lại trường mã nhân viên
            txt_ma3.setEnabled(true);

            // Kích hoạt/vô hiệu hóa các nút
            btn_Them1.setEnabled(true);
            btn_Sua1.setEnabled(false);
            btn_Xoa1.setEnabled(false);

            // Xóa lựa chọn trong bảng
            tbl_bang1.clearSelection();

            // Focus vào trường mã nhân viên
            txt_ma3.requestFocus();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi làm mới dữ liệu: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }// GEN-LAST:event_btn_LamMoibtn_ThemActionPerformed

    private void btn_XoaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_XoaActionPerformed
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

            try {
                if (confirm == JOptionPane.YES_OPTION) {
                    // Tìm staff theo mã nhân viên
                    Staff staff = staffDAO.findAll().stream()
                        .filter(s -> employeeCode.equals(s.getStaffCode()))
                        .findFirst()
                        .orElse(null);
                    if (staff == null) {
                        JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên để xóa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    boolean isDeleted = staffDAO.deleteById(staff.getStaffId());

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
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Lỗi khi xóa nhân viên: " + ex.getMessage(),
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }// GEN-LAST:event_btn_XoaActionPerformed

    private void btn_SuaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_SuaActionPerformed
        try {
            // Kiểm tra xem có nhân viên nào được chọn không
            if (txt_ma3.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng chọn nhân viên cần sửa từ bảng!",
                        "Cảnh báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Validate required fields
            if (txt_hoten3.getText().trim().isEmpty()
                    || txt_email3.getText().trim().isEmpty()
                    || txt_sdt3.getText().trim().isEmpty()) {

                JOptionPane.showMessageDialog(this,
                        "Vui lòng điền đầy đủ thông tin bắt buộc (*)",
                        "Cảnh báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Validate email format
            if (!txt_email3.getText().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                JOptionPane.showMessageDialog(this,
                        "Email không đúng định dạng!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                txt_email3.requestFocus();
                return;
            }

            // Validate phone number format
            if (!txt_sdt3.getText().matches("(0[3|5|7|8|9])+([0-9]{8})\\b")) {
                JOptionPane.showMessageDialog(this,
                        "Số điện thoại không đúng định dạng!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                txt_sdt3.requestFocus();
                return;
            }

            // Tìm nhân viên hiện tại theo mã để lấy ID
            String staffCode = txt_ma3.getText().trim();
            Staff existingStaff = staffDAO.findAll().stream()
                .filter(s -> staffCode.equals(s.getStaffCode()))
                .findFirst()
                .orElse(null);
                
            if (existingStaff == null) {
                JOptionPane.showMessageDialog(this,
                        "Không tìm thấy nhân viên với mã: " + staffCode,
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Tạo đối tượng Staff với thông tin cập nhật (giữ nguyên ID)
            Staff staff = new Staff();
            staff.setStaffId(existingStaff.getStaffId()); // Quan trọng: giữ nguyên ID
            staff.setStaffCode(staffCode);
            staff.setStaffUsername(staffCode); // Đồng bộ username với mã nhân viên
            staff.setStaffFullName(txt_hoten3.getText().trim());
            staff.setStaffEmail(txt_email3.getText().trim());
            staff.setStaffSdt(txt_sdt3.getText().trim());
            staff.setStaffRole(rdo_QuanLy1.isSelected() ? 1 : 0);
            
            // Xử lý mật khẩu
            if (!txt_matkhau3.getText().trim().isEmpty()) {
                staff.setStaffPassword(txt_matkhau3.getText().trim());
            } else {
                // Giữ nguyên mật khẩu cũ nếu không nhập mật khẩu mới
                staff.setStaffPassword(existingStaff.getStaffPassword());
            }
            
            // Confirm update
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc chắn muốn cập nhật thông tin nhân viên: " + staff.getStaffFullName() + "?",
                    "Xác nhận cập nhật",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    Staff updatedStaff = staffDAO.update(staff);
                    if (updatedStaff != null) {
                        JOptionPane.showMessageDialog(this,
                                "Cập nhật thông tin nhân viên thành công!",
                                "Thành công",
                                JOptionPane.INFORMATION_MESSAGE);
                        // Làm mới bảng
                        loadStaffData();
                        // Reset form
                        btn_LamMoibtn_ThemActionPerformed(null);
                    } else {
                        JOptionPane.showMessageDialog(this,
                                "Cập nhật thông tin thất bại! Vui lòng kiểm tra lại dữ liệu.",
                                "Lỗi",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception updateEx) {
                    JOptionPane.showMessageDialog(this,
                            "Lỗi khi cập nhật: " + updateEx.getMessage(),
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    updateEx.printStackTrace();
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi cập nhật nhân viên: " + ex.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }// GEN-LAST:event_btn_SuaActionPerformed

    private void btn_ThemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_ThemActionPerformed
        try {
            // Kiểm tra các trường bắt buộc (bao gồm mật khẩu)
            if (txt_ma3.getText().trim().isEmpty()
                    || txt_hoten3.getText().trim().isEmpty()
                    || txt_email3.getText().trim().isEmpty()
                    || txt_sdt3.getText().trim().isEmpty()
                    || txt_matkhau3.getText().trim().isEmpty()) {

                JOptionPane.showMessageDialog(this,
                        "Vui lòng điền đầy đủ thông tin bắt buộc (*) bao gồm mật khẩu",
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
            boolean codeExists = staffDAO.findAll().stream()
                .anyMatch(s -> txt_ma3.getText().trim().equalsIgnoreCase(s.getStaffCode()));
            if (codeExists) {
                JOptionPane.showMessageDialog(this,
                        "Mã nhân viên đã tồn tại!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                txt_ma3.requestFocus();
                return;
            }

            Staff staff = new Staff();
            staff.setStaffCode(txt_ma3.getText().trim());
            staff.setStaffUsername(txt_ma3.getText().trim()); // Sử dụng mã nhân viên làm username
            staff.setStaffFullName(txt_hoten3.getText().trim());
            staff.setStaffEmail(txt_email3.getText().trim());
            staff.setStaffSdt(txt_sdt3.getText().trim());
            staff.setStaffRole(rdo_QuanLy1.isSelected() ? 1 : 0);
            staff.setStaffPassword(txt_matkhau3.getText().trim()); // Sử dụng mật khẩu người dùng nhập
            
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
                            "Thêm nhân viên thành công!",
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
    }// GEN-LAST:event_btn_ThemActionPerformed

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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
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
