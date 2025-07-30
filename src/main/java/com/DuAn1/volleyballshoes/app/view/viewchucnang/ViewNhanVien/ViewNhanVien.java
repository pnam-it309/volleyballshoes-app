package com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewNhanVien;


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
        jLabel45 = new javax.swing.JLabel();
        rdo_nam3 = new javax.swing.JRadioButton();
        rdo_nu3 = new javax.swing.JRadioButton();
        jLabel46 = new javax.swing.JLabel();
        txt_hoten3 = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        txt_diachi3 = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        txt_cccd3 = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        txt_ma3 = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        txt_email3 = new javax.swing.JTextField();
        jLabel52 = new javax.swing.JLabel();
        date_ngaysinh3 = new com.toedter.calendar.JDateChooser();
        jLabel53 = new javax.swing.JLabel();
        rdo_danglamviec3 = new javax.swing.JRadioButton();
        rdo_nghiviec3 = new javax.swing.JRadioButton();
        jLabel54 = new javax.swing.JLabel();
        txt_luong3 = new javax.swing.JTextField();
        jLabel55 = new javax.swing.JLabel();
        txt_matkhau3 = new javax.swing.JTextField();
        jLabel56 = new javax.swing.JLabel();
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
        cbx_GioiTinh = new javax.swing.JComboBox<>();
        cbx_TrangThai = new javax.swing.JComboBox<>();

        tbl_bang1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "ma", "Tên", "SĐT", "Ngày Sinh", "Địa Chỉ", "Email", "Giơi Tính", "Ngày Bắt Đầu", "Chức Vụ ", "Trạng  Thái"
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

        jLabel45.setText("Giới Tính");

        buttonGroup1.add(rdo_nam3);
        rdo_nam3.setText("Nam");

        buttonGroup1.add(rdo_nu3);
        rdo_nu3.setText("Nữ");

        jLabel46.setText("Họ và Tên:");

        jLabel47.setText("Địa Chỉ");

        jLabel48.setText("CCCD:");

        jLabel49.setText("Mã:");

        txt_ma3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_ma2ActionPerformed(evt);
            }
        });

        jLabel50.setText("Chức Vụ");

        jLabel51.setText("Email:");

        jLabel52.setText("Ngày Bắt Đầu");

        date_ngaysinh3.setDateFormatString("yyyy-MM-dd");

        jLabel53.setText("Trạng Thái");

        buttonGroup3.add(rdo_danglamviec3);
        rdo_danglamviec3.setText("Đang làm việc");

        buttonGroup3.add(rdo_nghiviec3);
        rdo_nghiviec3.setText("Nghỉ Việc");

        jLabel54.setText("Lương");

        jLabel55.setText("Mật Khẩu");

        jLabel56.setText("Ngày Sinh:");

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
                        .addGap(13, 13, 13)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel54)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel47)
                                    .addComponent(jLabel48))
                                .addGap(9, 9, 9))
                            .addComponent(jLabel44)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel55))
                    .addComponent(jLabel45, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txt_matkhau3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE)
                    .addComponent(txt_luong3, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_cccd3, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rdo_nam3, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rdo_nu3, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_hoten3, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_sdt3, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_diachi3, javax.swing.GroupLayout.Alignment.LEADING))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                            .addGap(108, 108, 108)
                            .addComponent(jLabel52)
                            .addGap(551, 551, 551))
                        .addGroup(jPanel6Layout.createSequentialGroup()
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel6Layout.createSequentialGroup()
                                    .addGap(150, 150, 150)
                                    .addComponent(jLabel51))
                                .addGroup(jPanel6Layout.createSequentialGroup()
                                    .addGap(126, 126, 126)
                                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel6Layout.createSequentialGroup()
                                            .addComponent(jLabel53)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(rdo_nghiviec3)
                                            .addGap(354, 354, 354))
                                        .addGroup(jPanel6Layout.createSequentialGroup()
                                            .addComponent(jLabel56)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(rdo_danglamviec3)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addGroup(jPanel6Layout.createSequentialGroup()
                                                            .addGap(0, 0, Short.MAX_VALUE)
                                                            .addComponent(txt_email3, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(date_ngaysinh3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                    .addGap(32, 32, 32))))))
                                .addGroup(jPanel6Layout.createSequentialGroup()
                                    .addGap(188, 188, 188)
                                    .addComponent(date_ngaybatdau3, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(92, 92, 92)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(79, 79, 79)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel50)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(btn_Them1)
                                .addGap(18, 18, 18))
                            .addComponent(jLabel49))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rdo_NhanVien1)
                            .addComponent(rdo_QuanLy1)
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
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rdo_nam3)
                    .addComponent(jLabel45)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rdo_QuanLy1)
                        .addComponent(jLabel50)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdo_nu3)
                    .addComponent(rdo_NhanVien1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 13, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44)
                    .addComponent(txt_sdt3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel51)
                    .addComponent(txt_email3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(date_ngaysinh3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel47)
                        .addComponent(txt_diachi3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel56)))
                .addGap(45, 45, 45)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel48)
                    .addComponent(txt_cccd3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdo_nghiviec3)
                    .addComponent(jLabel53))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdo_danglamviec3)
                .addGap(19, 19, 19)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_luong3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel54)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(date_ngaybatdau3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel52))))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_matkhau3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel55)
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

        cbx_GioiTinh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nam", "Nữ" }));

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
                .addGap(165, 165, 165)
                .addComponent(cbx_GioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
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
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 1097, Short.MAX_VALUE)
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
                    .addComponent(cbx_GioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbx_TrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tbl_bang1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_bang1MouseClicked

    }//GEN-LAST:event_tbl_bang1MouseClicked

    private void btn_XoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_XoaActionPerformed

    }//GEN-LAST:event_btn_XoaActionPerformed

    private void btn_SuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SuaActionPerformed
  


    }//GEN-LAST:event_btn_SuaActionPerformed

    private void btn_ThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ThemActionPerformed
        
  

    }//GEN-LAST:event_btn_ThemActionPerformed

    private void txt_ma2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_ma2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_ma2ActionPerformed

    private void btn_Tìmbtn_ThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Tìmbtn_ThemActionPerformed
 
    }//GEN-LAST:event_btn_Tìmbtn_ThemActionPerformed

    private void btn_LamMoibtn_ThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_LamMoibtn_ThemActionPerformed

    }//GEN-LAST:event_btn_LamMoibtn_ThemActionPerformed

    private void btn_Locbtn_ThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Locbtn_ThemActionPerformed
   
    }//GEN-LAST:event_btn_Locbtn_ThemActionPerformed

    private void btn_XuatExcelbtn_ThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_XuatExcelbtn_ThemActionPerformed
  
    }//GEN-LAST:event_btn_XuatExcelbtn_ThemActionPerformed

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
    private javax.swing.JComboBox<String> cbx_GioiTinh;
    private javax.swing.JComboBox<String> cbx_TrangThai;
    private com.toedter.calendar.JDateChooser date_ngaybatdau3;
    private com.toedter.calendar.JDateChooser date_ngaysinh3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rdo_NhanVien1;
    private javax.swing.JRadioButton rdo_QuanLy1;
    private javax.swing.JRadioButton rdo_danglamviec3;
    private javax.swing.JRadioButton rdo_nam3;
    private javax.swing.JRadioButton rdo_nghiviec3;
    private javax.swing.JRadioButton rdo_nu3;
    private javax.swing.JTable tbl_bang1;
    private javax.swing.JTextField txt_Tim;
    private javax.swing.JTextField txt_cccd3;
    private javax.swing.JTextField txt_diachi3;
    private javax.swing.JTextField txt_email3;
    private javax.swing.JTextField txt_hoten3;
    private javax.swing.JTextField txt_luong3;
    private javax.swing.JTextField txt_ma3;
    private javax.swing.JTextField txt_matkhau3;
    private javax.swing.JTextField txt_sdt3;
    // End of variables declaration//GEN-END:variables
}
