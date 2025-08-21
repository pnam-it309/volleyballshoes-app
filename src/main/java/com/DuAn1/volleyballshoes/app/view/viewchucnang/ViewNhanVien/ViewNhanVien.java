package com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewNhanVien;

import com.DuAn1.volleyballshoes.app.controller.StaffController;
import com.DuAn1.volleyballshoes.app.entity.Staff;
import com.DuAn1.volleyballshoes.app.dao.StaffDAO;
import com.DuAn1.volleyballshoes.app.dao.impl.StaffDAOImpl;
import com.DuAn1.volleyballshoes.app.dto.request.StaffUpdateRequest;
import com.DuAn1.volleyballshoes.app.dto.response.StaffResponse;
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
            DefaultTableModel model = (DefaultTableModel) tbl_bang.getModel();
            model.setRowCount(0); // Xóa dữ liệu cũ

            if (staffList != null) {
                for (Staff staff : staffList) {
                    if (staff == null) {
                        continue;
                    }
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
            tbl_bang.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        Tên = new javax.swing.JPanel();
        lbl_sdt = new javax.swing.JLabel();
        txt_sdt = new javax.swing.JTextField();
        lbl_ten = new javax.swing.JLabel();
        txt_hoten = new javax.swing.JTextField();
        lbl_ma = new javax.swing.JLabel();
        lbl_chucvu = new javax.swing.JLabel();
        lbl_email = new javax.swing.JLabel();
        txt_email = new javax.swing.JTextField();
        lbl_trangthai = new javax.swing.JLabel();
        rdo_nghiviec = new javax.swing.JRadioButton();
        rdo_lamviec = new javax.swing.JRadioButton();
        lbl_mk = new javax.swing.JLabel();
        txt_matkhau = new javax.swing.JTextField();
        rdo_QuanLy = new javax.swing.JRadioButton();
        rdo_NhanVien = new javax.swing.JRadioButton();
        btn_Them = new javax.swing.JButton();
        btn_Sua = new javax.swing.JButton();
        btn_LamMoi = new javax.swing.JButton();
        txt_ma = new javax.swing.JTextField();
        txt_Tim = new javax.swing.JTextField();
        cbx_TrangThai = new javax.swing.JComboBox<>();
        btn_Tìm = new javax.swing.JButton();
        btn_Loc = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_bang = new javax.swing.JTable();

        jLabel1.setBackground(new java.awt.Color(255, 51, 51));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 153, 255));
        jLabel1.setText("Quản Lý Nhân Viên");

        jLabel2.setBackground(new java.awt.Color(255, 51, 51));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 204, 255));
        jLabel2.setText("Thiết Lập Thông Tin Nhân Viên");

        Tên.setBackground(new java.awt.Color(255, 255, 255));

        lbl_sdt.setText("SĐT");

        lbl_ten.setText("Tên");

        lbl_ma.setText("Mã");

        lbl_chucvu.setText("Chức Vụ");

        lbl_email.setText("Email:");

        lbl_trangthai.setText("Trạng Thái");

        buttonGroup3.add(rdo_nghiviec);
        rdo_nghiviec.setText("Nghỉ việc");

        buttonGroup3.add(rdo_lamviec);
        rdo_lamviec.setText("Đang làm việc");

        lbl_mk.setText("Mật Khẩu");

        buttonGroup2.add(rdo_QuanLy);
        rdo_QuanLy.setText("Quản Lý ");

        buttonGroup2.add(rdo_NhanVien);
        rdo_NhanVien.setText("Nhân Viên");

        btn_Them.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        btn_Them.setText("Thêm");
        btn_Them.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Thembtn_ThemActionPerformed(evt);
            }
        });

        btn_Sua.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        btn_Sua.setText("Sửa");
        btn_Sua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Suabtn_SuaActionPerformed(evt);
            }
        });

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
                .addGap(65, 65, 65)
                .addComponent(txt_hoten, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(214, 214, 214)
                .addComponent(btn_Them)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_Sua)
                .addGap(26, 26, 26)
                .addComponent(btn_LamMoi)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(TênLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(TênLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbl_ten)
                    .addGroup(TênLayout.createSequentialGroup()
                        .addComponent(lbl_mk)
                        .addGap(14, 14, 14)
                        .addComponent(txt_matkhau, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, TênLayout.createSequentialGroup()
                        .addComponent(lbl_sdt)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_sdt, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(TênLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(TênLayout.createSequentialGroup()
                        .addGap(155, 155, 155)
                        .addGroup(TênLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(TênLayout.createSequentialGroup()
                                .addComponent(rdo_lamviec)
                                .addGap(18, 18, 18)
                                .addComponent(rdo_nghiviec))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, TênLayout.createSequentialGroup()
                                .addComponent(rdo_QuanLy)
                                .addGap(41, 41, 41)
                                .addComponent(rdo_NhanVien)))
                        .addContainerGap(239, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, TênLayout.createSequentialGroup()
                        .addGroup(TênLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(TênLayout.createSequentialGroup()
                                .addGap(95, 95, 95)
                                .addComponent(lbl_ma)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_ma, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, TênLayout.createSequentialGroup()
                                .addGap(57, 57, 57)
                                .addGroup(TênLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lbl_chucvu)
                                    .addComponent(lbl_email)
                                    .addComponent(lbl_trangthai))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 192, Short.MAX_VALUE))))
        );
        TênLayout.setVerticalGroup(
            TênLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TênLayout.createSequentialGroup()
                .addGroup(TênLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(TênLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(TênLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_ten)
                            .addComponent(txt_hoten, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, TênLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(TênLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_ma)
                            .addComponent(txt_ma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(TênLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(TênLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_sdt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lbl_sdt))
                    .addGroup(TênLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbl_email)
                        .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20)
                .addGroup(TênLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_mk)
                    .addComponent(txt_matkhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_chucvu)
                    .addComponent(rdo_QuanLy)
                    .addComponent(rdo_NhanVien))
                .addGap(18, 18, 18)
                .addGroup(TênLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdo_nghiviec)
                    .addComponent(rdo_lamviec)
                    .addComponent(lbl_trangthai))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(TênLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_Them)
                    .addComponent(btn_Sua)
                    .addComponent(btn_LamMoi))
                .addGap(16, 16, 16))
        );

        cbx_TrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Đang Làm Việc", "Nghỉ Việc" }));

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

        tbl_bang.setModel(new javax.swing.table.DefaultTableModel(
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
        tbl_bang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_bangMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_bang);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(435, 435, 435))
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(btn_Tìm)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_Tim, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                .addGap(40, 40, 40)
                .addComponent(cbx_TrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_Loc)
                .addGap(476, 476, 476))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 827, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Tên, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(111, Short.MAX_VALUE))
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
                    .addComponent(txt_Tim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbx_TrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_Tìm)
                    .addComponent(btn_Loc))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(85, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_Tìmbtn_ThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Tìmbtn_ThemActionPerformed
        // TODO add your handling code here:
        try {
            // Get the search term and trim any whitespace
            String searchTerm = txt_Tim.getText().trim();

            if (searchTerm.isEmpty()) {
                // Nếu ô tìm kiếm rỗng, load lại toàn bộ nhân viên
                loadStaffData();
                return;
            }

            // Lấy model của bảng
            DefaultTableModel model = (DefaultTableModel) tbl_bang.getModel();
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
                    emp.getStaff_status() == 1 ? "Nghỉ Việc" : "Đang Làm Việc" // Trạng thái
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
        // TODO add your handling code here:
        try {
            // Get the selected status from the combo box
            String selectedStatus = cbx_TrangThai.getSelectedItem().toString();

            // Get the table model
            DefaultTableModel model = (DefaultTableModel) tbl_bang.getModel();
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
                    emp.getStaff_status() == 1 ? "Nghỉ Việc" : "Đang Làm Việc" // Trạng thái
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

    private void btn_Thembtn_ThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Thembtn_ThemActionPerformed
        // TODO add your handling code here:
        try {
            // Kiểm tra các trường bắt buộc
            if (txt_ma.getText().trim().isEmpty()
                    || txt_hoten.getText().trim().isEmpty()
                    || txt_email.getText().trim().isEmpty()
                    || txt_sdt.getText().trim().isEmpty()) {

                JOptionPane.showMessageDialog(this,
                        "Vui lòng điền đầy đủ thông tin bắt buộc (*)",
                        "Cảnh báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Validate phone number format (10-11 digits, starts with 0)
            String phoneNumber = txt_sdt.getText().trim();
            if (!phoneNumber.matches("^0\\d{9,10}$")) {
                JOptionPane.showMessageDialog(this,
                        "Số điện thoại không hợp lệ!\nVui lòng nhập số điện thoại bắt đầu bằng 0 và có 10-11 chữ số.",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                txt_sdt.requestFocus();
                return;
            }

            // Kiểm tra định dạng email
            if (!txt_email.getText().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                JOptionPane.showMessageDialog(this,
                        "Email không đúng định dạng!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                txt_email.requestFocus();
                return;
            }

            // Kiểm tra định dạng số điện thoại
            if (!txt_sdt.getText().matches("(0[3|5|7|8|9])+([0-9]{8})\\b")) {
                JOptionPane.showMessageDialog(this,
                        "Số điện thoại không đúng định dạng!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                txt_sdt.requestFocus();
                return;
            }

            // Kiểm tra mã nhân viên đã tồn tại chưa
            boolean codeExists = staffDAO.findAll().stream()
                    .anyMatch(s -> txt_ma.getText().trim().equalsIgnoreCase(s.getStaffCode()));
            if (codeExists) {
                JOptionPane.showMessageDialog(this,
                        "Mã nhân viên đã tồn tại!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                txt_ma.requestFocus();
                return;
            }

            // Kiểm tra trùng username (đang dùng làm Họ tên hiển thị)
            String newUsername = txt_hoten.getText().trim();
            boolean usernameExists = staffDAO.findAll().stream()
                    .anyMatch(s -> newUsername.equalsIgnoreCase(s.getStaffUsername()));
            if (usernameExists) {
                JOptionPane.showMessageDialog(this,
                        "Họ tên (tên đăng nhập) đã tồn tại, vui lòng nhập tên khác!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                txt_hoten.requestFocus();
                return;
            }

            // Lấy vai trò từ radio button
            String role = rdo_QuanLy.isSelected() ? "QUANLY" : "NHANVIEN";

            // Lấy trạng thái từ radio button
            boolean isActive = rdo_lamviec.isSelected();

            Staff staff = new Staff();
            staff.setStaffCode(txt_ma.getText().trim());
            // Đặt username từ trường Họ tên để hiển thị đúng tên
            staff.setStaffUsername(newUsername);
            staff.setStaffEmail(txt_email.getText().trim());
            staff.setStaffSdt(txt_sdt.getText().trim());
            staff.setStaffRole(rdo_QuanLy.isSelected() ? 1 : 0);
            // Lưu trạng thái làm việc
            staff.setStaff_status(rdo_nghiviec.isSelected() ? 1 : 0);
            // Use password from input field or empty string if not provided
            String password = txt_matkhau.getText().trim();
            if (password.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng nhập mật khẩu cho nhân viên",
                        "Cảnh báo",
                        JOptionPane.WARNING_MESSAGE);
                txt_matkhau.requestFocus();
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
    }//GEN-LAST:event_btn_Thembtn_ThemActionPerformed

    private void btn_Suabtn_SuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Suabtn_SuaActionPerformed
        // TODO add your handling code here:
        try {
            // Validate required fields
            if (txt_ma.getText().trim().isEmpty()
                    || txt_hoten.getText().trim().isEmpty()
                    || txt_email.getText().trim().isEmpty()
                    || txt_sdt.getText().trim().isEmpty()) {

                JOptionPane.showMessageDialog(this,
                        "Vui lòng điền đầy đủ thông tin bắt buộc (*)",
                        "Cảnh báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Validate phone number format (10-11 digits, starts with 0)
            String phoneNumber = txt_sdt.getText().trim();
            if (!phoneNumber.matches("^0\\d{9,10}$")) {
                JOptionPane.showMessageDialog(this,
                        "Số điện thoại không hợp lệ!\nVui lòng nhập số điện thoại bắt đầu bằng 0 và có 10-11 chữ số.",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                txt_sdt.requestFocus();
                return;
            }

            // Validate email format
            if (!txt_email.getText().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                JOptionPane.showMessageDialog(this,
                        "Email không đúng định dạng!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                txt_email.requestFocus();
                return;
            }

            Staff staff = staffDAO.findAll().stream()
                    .filter(s -> txt_ma.getText().trim().equalsIgnoreCase(s.getStaffCode()))
                    .findFirst()
                    .orElse(null);
            if (staff == null) {
                JOptionPane.showMessageDialog(this,
                        "Không tìm thấy nhân viên để sửa!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Cập nhật thông tin
            staff.setStaffEmail(txt_email.getText().trim());
            staff.setStaffSdt(txt_sdt.getText().trim());
            staff.setStaffRole(rdo_QuanLy.isSelected() ? 1 : 0);
            // Lưu trạng thái làm việc
            staff.setStaff_status(rdo_nghiviec.isSelected() ? 1 : 0);
            // Đã cập nhật trạng thái vào entity Staff (staff_status: 0 = Đang làm việc, 1 = Nghỉ việc)
            String password = txt_matkhau.getText().trim();
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

    }//GEN-LAST:event_btn_Suabtn_SuaActionPerformed

    private void btn_LamMoibtn_ThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_LamMoibtn_ThemActionPerformed
        // TODO add your handling code here:
        try {
            // Đặt lại tất cả các trường nhập liệu
            txt_ma.setText("");
            txt_hoten.setText("");
            txt_email.setText("");
            txt_sdt.setText("");
            txt_matkhau.setText("");

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
            btn_Them.setEnabled(true);
            btn_Sua.setEnabled(false);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi làm mới dữ liệu: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_btn_LamMoibtn_ThemActionPerformed

    private void tbl_bangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_bangMouseClicked
        // TODO add your handling code here:
        try {
            // Get the selected row index
            int selectedRow = tbl_bang.getSelectedRow();

            // If a valid row is selected
            if (selectedRow >= 0) {
                // Get the table model
                DefaultTableModel model = (DefaultTableModel) tbl_bang.getModel();

                // Populate form fields with selected row data
                Object maObj = model.getValueAt(selectedRow, 0);
                txt_ma.setText(maObj == null ? "" : maObj.toString());
                Object tenObj = model.getValueAt(selectedRow, 1);
                txt_hoten.setText(tenObj == null ? "" : tenObj.toString());
                // Luôn lấy mật khẩu thực từ DB theo mã để tránh dùng giá trị "***" trên bảng
                if (maObj != null) {
                    String code = maObj.toString();
                    Staff s = staffDAO.findAll().stream()
                            .filter(st -> code.equalsIgnoreCase(st.getStaffCode()))
                            .findFirst()
                            .orElse(null);
                    txt_matkhau.setText(s != null && s.getStaffPassword() != null ? s.getStaffPassword() : "");
                } else {
                    txt_matkhau.setText("");
                }

                Object sdtObj = model.getValueAt(selectedRow, 3);
                txt_sdt.setText(sdtObj == null ? "" : sdtObj.toString());
                Object emailObj = model.getValueAt(selectedRow, 4);
                txt_email.setText(emailObj == null ? "" : emailObj.toString());
                Object vaiTroObj = model.getValueAt(selectedRow, 5);
                String vaiTro = vaiTroObj == null ? "" : vaiTroObj.toString();
                if (vaiTro.equals("Quản lý")) {
                    rdo_QuanLy.setSelected(true);
                } else {
                    rdo_NhanVien.setSelected(true);
                }
                Object trangThaiObj = model.getValueAt(selectedRow, 6);
                String trangThai = trangThaiObj == null ? "" : trangThaiObj.toString();
                if (trangThai.equals("Đang Làm Việc")) {
                    rdo_lamviec.setSelected(true);
                } else {
                    rdo_nghiviec.setSelected(true);
                }

                // Kích hoạt/vô hiệu hóa các nút chức năng
                btn_Sua.setEnabled(true);
                btn_Them.setEnabled(false);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi chọn nhân viên: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_tbl_bangMouseClicked

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
    private javax.swing.JButton btn_Sua;
    private javax.swing.JButton btn_Them;
    private javax.swing.JButton btn_Tìm;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JComboBox<String> cbx_TrangThai;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbl_chucvu;
    private javax.swing.JLabel lbl_email;
    private javax.swing.JLabel lbl_ma;
    private javax.swing.JLabel lbl_mk;
    private javax.swing.JLabel lbl_sdt;
    private javax.swing.JLabel lbl_ten;
    private javax.swing.JLabel lbl_trangthai;
    private javax.swing.JRadioButton rdo_NhanVien;
    private javax.swing.JRadioButton rdo_QuanLy;
    private javax.swing.JRadioButton rdo_lamviec;
    private javax.swing.JRadioButton rdo_nghiviec;
    private javax.swing.JTable tbl_bang;
    private javax.swing.JTextField txt_Tim;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_hoten;
    private javax.swing.JTextField txt_ma;
    private javax.swing.JTextField txt_matkhau;
    private javax.swing.JTextField txt_sdt;
    // End of variables declaration//GEN-END:variables
}
