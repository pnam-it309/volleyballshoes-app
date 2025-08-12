package com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewNhanVien;

import com.DuAn1.volleyballshoes.app.entity.Staff;
import com.DuAn1.volleyballshoes.app.dao.StaffDAO;
import com.DuAn1.volleyballshoes.app.dao.impl.StaffDAOImpl;
import java.io.FileOutputStream;
import java.util.List;
import java.util.stream.Collectors;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

public class ViewNhanVien extends javax.swing.JPanel {

    private StaffDAO staffDAO = new StaffDAOImpl();

    public ViewNhanVien() {
        initComponents();
        loadStaffData();
    }

    private void loadStaffData() {
        try {
            // Lấy danh sách nhân viên từ DAO
            List<Staff> staffList = staffDAO.findAll(); // Đúng

            // Tạo model cho bảng
            DefaultTableModel model = (DefaultTableModel) tbl_bang1.getModel();
            model.setRowCount(0); // Xóa dữ liệu cũ

            if (staffList != null) {
                for (Staff staff : staffList) {
                    if (staff == null) continue;
                    Object[] row = new Object[]{
                        staff.getStaffCode(), // Mã nhân viên
                        staff.getStaffUsername(), // Tên đăng nhập nhân viên (đang dùng làm "Họ tên")
                        "***", // Ẩn mật khẩu trên bảng
                        staff.getStaffSdt(), // Số điện thoại
                        staff.getStaffEmail(), // Email
                        staff.getStaffRole() == 1 ? "Quản lý" : "Nhân viên", // 1: Quản lý, 0: Nhân viên
                        staff.getStaff_status() == 1 ? "Nghỉ Việc" : "Đang Làm Việc", // Trạng thái từ entity
                    };
                    model.addRow(row);
                }
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
        Tên = new javax.swing.JPanel();
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
        btn_Tìm = new javax.swing.JButton();
        txt_Tim = new javax.swing.JTextField();
        btn_Loc = new javax.swing.JButton();
        cbx_TrangThai = new javax.swing.JComboBox<>();

        tbl_bang1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã", "Tên", "Mật Khẩu", "Số Điện Thoại", "Email", "Chức Vụ", "Trạng Thái"
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

        Tên.setBackground(new java.awt.Color(255, 255, 255));

        jLabel44.setText("SĐT");

        jLabel46.setText("Tên");

        jLabel49.setText("Mã");

        txt_ma3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_ma2ActionPerformed(evt);
            }
        });

        jLabel50.setText("Chức Vụ");

        jLabel51.setText("Email:");

        jLabel53.setText("Trạng Thái");

        buttonGroup3.add(rdo_danglamviec3);
        rdo_danglamviec3.setText("Đang làm việc");

        buttonGroup3.add(rdo_nghiviec3);
        rdo_nghiviec3.setText("Nghỉ Việc");

        jLabel55.setText("Mật Khẩu");

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

        javax.swing.GroupLayout TênLayout = new javax.swing.GroupLayout(Tên);
        Tên.setLayout(TênLayout);
        TênLayout.setHorizontalGroup(
            TênLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TênLayout.createSequentialGroup()
                .addGroup(TênLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(TênLayout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(txt_hoten3, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(208, 208, 208)
                        .addGroup(TênLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(TênLayout.createSequentialGroup()
                                .addComponent(btn_Them1)
                                .addGap(18, 18, 18)
                                .addComponent(btn_Sua1)
                                .addGap(27, 27, 27)
                                .addComponent(btn_Xoa1)
                                .addGap(18, 18, 18)
                                .addComponent(btn_LamMoi))
                            .addComponent(rdo_NhanVien1)
                            .addComponent(rdo_danglamviec3)))
                    .addGroup(TênLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(TênLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel46)
                            .addGroup(TênLayout.createSequentialGroup()
                                .addComponent(jLabel55)
                                .addGap(14, 14, 14)
                                .addComponent(txt_matkhau3, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, TênLayout.createSequentialGroup()
                                .addComponent(jLabel44)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txt_sdt3, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(36, 36, 36)
                        .addGroup(TênLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(TênLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(TênLayout.createSequentialGroup()
                                    .addComponent(jLabel51)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txt_email3))
                                .addGroup(TênLayout.createSequentialGroup()
                                    .addComponent(jLabel49)
                                    .addGap(18, 18, 18)
                                    .addComponent(txt_ma3, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(TênLayout.createSequentialGroup()
                                .addGroup(TênLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel50)
                                    .addComponent(jLabel53))
                                .addGap(18, 18, 18)
                                .addGroup(TênLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(rdo_nghiviec3)
                                    .addComponent(rdo_QuanLy1))
                                .addGap(183, 183, 183)))))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        TênLayout.setVerticalGroup(
            TênLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TênLayout.createSequentialGroup()
                .addGroup(TênLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, TênLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txt_ma3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(54, 54, 54))
                    .addGroup(TênLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(TênLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel46)
                            .addComponent(txt_hoten3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel49))
                        .addGap(18, 18, 18)
                        .addGroup(TênLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_sdt3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel51)
                            .addComponent(txt_email3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel44))
                        .addGap(20, 20, 20)))
                .addGroup(TênLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel55)
                    .addComponent(txt_matkhau3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel50)
                    .addComponent(rdo_QuanLy1)
                    .addComponent(rdo_NhanVien1))
                .addGap(18, 18, 18)
                .addGroup(TênLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdo_danglamviec3)
                    .addComponent(rdo_nghiviec3)
                    .addComponent(jLabel53))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(TênLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_Xoa1)
                    .addComponent(btn_Sua1)
                    .addComponent(btn_Them1)
                    .addComponent(btn_LamMoi))
                .addGap(16, 16, 16))
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
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(435, 435, 435))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 926, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btn_Tìm)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_Tim)
                        .addGap(40, 40, 40)
                        .addComponent(cbx_TrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(98, 98, 98)
                        .addComponent(btn_Loc)
                        .addGap(396, 396, 396))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Tên, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Tên, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_Tìm)
                    .addComponent(txt_Tim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_Loc)
                    .addComponent(cbx_TrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tbl_bang1MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_tbl_bang1MouseClicked
        try {
            // Get the selected row index
            int selectedRow = tbl_bang1.getSelectedRow();

            // If a valid row is selected
            if (selectedRow >= 0) {
                // Get the table model
                DefaultTableModel model = (DefaultTableModel) tbl_bang1.getModel();

                // Populate form fields with selected row data
                Object maObj = model.getValueAt(selectedRow, 0);
                txt_ma3.setText(maObj == null ? "" : maObj.toString());
                Object tenObj = model.getValueAt(selectedRow, 1);
                txt_hoten3.setText(tenObj == null ? "" : tenObj.toString());
                // Luôn lấy mật khẩu thực từ DB theo mã để tránh dùng giá trị "***" trên bảng
                if (maObj != null) {
                    String code = maObj.toString();
                    Staff s = staffDAO.findAll().stream()
                            .filter(st -> code.equalsIgnoreCase(st.getStaffCode()))
                            .findFirst()
                            .orElse(null);
                    txt_matkhau3.setText(s != null && s.getStaffPassword() != null ? s.getStaffPassword() : "");
                } else {
                    txt_matkhau3.setText("");
                }

                Object sdtObj = model.getValueAt(selectedRow, 3);
                txt_sdt3.setText(sdtObj == null ? "" : sdtObj.toString());
                Object emailObj = model.getValueAt(selectedRow, 4);
                txt_email3.setText(emailObj == null ? "" : emailObj.toString());
                Object vaiTroObj = model.getValueAt(selectedRow, 5);
                String vaiTro = vaiTroObj == null ? "" : vaiTroObj.toString();
                if (vaiTro.equals("Quản lý")) {
                    rdo_QuanLy1.setSelected(true);
                } else {
                    rdo_NhanVien1.setSelected(true);
                }
                Object trangThaiObj = model.getValueAt(selectedRow, 6);
                String trangThai = trangThaiObj == null ? "" : trangThaiObj.toString();
                if (trangThai.equals("Đang Làm Việc")) {
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
    }// GEN-LAST:event_tbl_bang1MouseClicked

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
                    || (staff.getStaffUsername() != null
                    && staff.getStaffUsername().toLowerCase().contains(searchTerm.toLowerCase()))
                    || (staff.getStaffEmail() != null
                    && staff.getStaffEmail().toLowerCase().contains(searchTerm.toLowerCase()))
                    || (staff.getStaffSdt() != null && staff.getStaffSdt().contains(searchTerm))
                    // Nếu có thêm trường khác muốn tìm kiếm thì bổ sung ở đây
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
                model.addRow(new Object[]{
                    emp.getStaffCode(), // Mã nhân viên
                    emp.getStaffUsername(), // Tên nhân viên
                    emp.getStaffPassword(), // Mật khẩu
                    emp.getStaffSdt(), // Số điện thoại
                    emp.getStaffEmail(), // Email
                    emp.getStaffRole() == 1 ? "Quản lý" : "Nhân viên", // Vai trò
                    emp.getStaff_status()==1 ? "Nghỉ Việc" : "Đang Làm Việc" // Trạng thái
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

            // Lọc nhân viên theo trạng thái
            // List<Staff> filteredStaff = staffList.stream()
            // .filter(staff -> {
            // String empStatus = (staff.getStatus() == 1) ? "Đang làm việc" : "Đã nghỉ
            // việc";
            // return selectedStatus.equals("Tất cả") || empStatus.equals(selectedStatus);
            // })
            // .collect(Collectors.toList());
            // Lọc nhân viên theo trạng thái
            // List<Staff> filteredStaff = staffList.stream()
            // .filter(staff -> {
            // String empStatus = (staff.getStatus() == 1) ? "Đang làm việc" : "Đã nghỉ
            // việc";
            // return selectedStatus.equals("Tất cả") || empStatus.equals(selectedStatus);
            // })
            // .collect(Collectors.toList());
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
                model.addRow(new Object[]{
                    emp.getStaffCode(), // Mã nhân viên
                    emp.getStaffUsername(), // Tên nhân viên
                    emp.getStaffPassword(), // Mật khẩu
                    emp.getStaffSdt(), // Số điện thoại
                    emp.getStaffEmail(), // Email
                    emp.getStaffRole() == 1 ? "Quản lý" : "Nhân viên", // Vai trò
                    emp.getStaff_status()==1 ? "Nghỉ Việc" : "Đang Làm Việc" // Trạng thái
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
        }
    }

    private void btn_SuaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_SuaActionPerformed
        try {
            // Validate required fields
            if (txt_ma3.getText().trim().isEmpty()
                    || txt_hoten3.getText().trim().isEmpty()
                    || txt_email3.getText().trim().isEmpty()
                    || txt_sdt3.getText().trim().isEmpty()) {

                JOptionPane.showMessageDialog(this,
                        "Vui lòng điền đầy đủ thông tin bắt buộc (*)",
                        "Cảnh báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Validate phone number format (10-11 digits, starts with 0)
            String phoneNumber = txt_sdt3.getText().trim();
            if (!phoneNumber.matches("^0\\d{9,10}$")) {
                JOptionPane.showMessageDialog(this,
                        "Số điện thoại không hợp lệ!\nVui lòng nhập số điện thoại bắt đầu bằng 0 và có 10-11 chữ số.",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                txt_sdt3.requestFocus();
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

        Staff staff = staffDAO.findAll().stream()
                .filter(s -> txt_ma3.getText().trim().equalsIgnoreCase(s.getStaffCode()))
                .findFirst()
                .orElse(null);
        if (staff == null) {
            JOptionPane.showMessageDialog(this,
                    "Không tìm thấy nhân viên để sửa!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Cập nhật thông tin (KHÔNG thay đổi username để tránh đổi thành số ngoài ý muốn)
        staff.setStaffEmail(txt_email3.getText().trim());
        staff.setStaffSdt(txt_sdt3.getText().trim());
        staff.setStaffRole(rdo_QuanLy1.isSelected() ? 1 : 0);
        // Lưu trạng thái làm việc
        staff.setStaff_status(rdo_nghiviec3.isSelected() ? 1 : 0);
        // Đã cập nhật trạng thái vào entity Staff (staff_status: 0 = Đang làm việc, 1 = Nghỉ việc)
        String password = txt_matkhau3.getText().trim();
        if (!password.isEmpty()) {
            staff.setStaffPassword(password);
        }
        // Xác nhận sửa
        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn sửa thông tin nhân viên này?",
                "Xác nhận sửa",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            Staff updatedStaff = staffDAO.update(staff);
            if (updatedStaff != null) {
                JOptionPane.showMessageDialog(this,
                        "Cập nhật nhân viên thành công!",
                        "Thành công",
                        JOptionPane.INFORMATION_MESSAGE);
                loadStaffData();
                btn_LamMoibtn_ThemActionPerformed(null);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Không thể cập nhật nhân viên. Vui lòng thử lại!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
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
            // Kiểm tra các trường bắt buộc
            if (txt_ma3.getText().trim().isEmpty()
                    || txt_hoten3.getText().trim().isEmpty()
                    || txt_email3.getText().trim().isEmpty()
                    || txt_sdt3.getText().trim().isEmpty()) {

                JOptionPane.showMessageDialog(this,
                        "Vui lòng điền đầy đủ thông tin bắt buộc (*)",
                        "Cảnh báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Validate phone number format (10-11 digits, starts with 0)
            String phoneNumber = txt_sdt3.getText().trim();
            if (!phoneNumber.matches("^0\\d{9,10}$")) {
                JOptionPane.showMessageDialog(this,
                        "Số điện thoại không hợp lệ!\nVui lòng nhập số điện thoại bắt đầu bằng 0 và có 10-11 chữ số.",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                txt_sdt3.requestFocus();
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

            // Kiểm tra trùng username (đang dùng làm Họ tên hiển thị)
            String newUsername = txt_hoten3.getText().trim();
            boolean usernameExists = staffDAO.findAll().stream()
                    .anyMatch(s -> newUsername.equalsIgnoreCase(s.getStaffUsername()));
            if (usernameExists) {
                JOptionPane.showMessageDialog(this,
                        "Họ tên (tên đăng nhập) đã tồn tại, vui lòng nhập tên khác!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                txt_hoten3.requestFocus();
                return;
            }

            // Lấy giới tính từ radio button
            String gender = "Nam"; // Mặc định là Nam vì không có radio button chọn giới tính trong giao diện hiện
            // tại

            // Lấy vai trò từ radio button
            String role = rdo_QuanLy1.isSelected() ? "QUANLY" : "NHANVIEN";

            // Lấy trạng thái từ radio button
            boolean isActive = rdo_danglamviec3.isSelected();

            Staff staff = new Staff();
            staff.setStaffCode(txt_ma3.getText().trim());
            // Đặt username từ trường Họ tên để hiển thị đúng tên
            staff.setStaffUsername(newUsername);
            staff.setStaffEmail(txt_email3.getText().trim());
            staff.setStaffSdt(txt_sdt3.getText().trim());
            staff.setStaffRole(rdo_QuanLy1.isSelected() ? 1 : 0);
            // Lưu trạng thái làm việc
            staff.setStaff_status(rdo_nghiviec3.isSelected() ? 1 : 0);
            // Use password from input field or empty string if not provided
            String password = txt_matkhau3.getText().trim();
            if (password.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng nhập mật khẩu cho nhân viên",
                        "Cảnh báo",
                        JOptionPane.WARNING_MESSAGE);
                txt_matkhau3.requestFocus();
                return;
            }
            staff.setStaffPassword(password); // Sử dụng mật khẩu từ trường nhập
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
            txt_hoten3.setText(staff.getStaffUsername() != null ? staff.getStaffUsername() : "");
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
            java.util.logging.Logger.getLogger(ViewNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewNhanVien().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Tên;
    private javax.swing.JButton btn_LamMoi;
    private javax.swing.JButton btn_Loc;
    private javax.swing.JButton btn_Sua1;
    private javax.swing.JButton btn_Them1;
    private javax.swing.JButton btn_Tìm;
    private javax.swing.JButton btn_Xoa1;
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
