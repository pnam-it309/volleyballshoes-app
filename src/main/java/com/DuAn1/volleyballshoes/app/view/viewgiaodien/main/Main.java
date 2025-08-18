package com.DuAn1.volleyballshoes.app.view.viewgiaodien.main;

import com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewBanHang.ViewBanHang;
import com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewDotGiamGia.ViewDotGiamGia;
import com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewHoaDon.ViewHoaDon;
import com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewKhachHang.ViewKhachHang;
import com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewNhanVien.ViewNhanVien;
import com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewPhieuGiamGia.ViewPhieuGiamGia;
import com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewSanPham.ViewSanPham;
import com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewThongKe.ViewThongKe;
import com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewTrangChu.ViewTrangChu;
import java.awt.CardLayout;
import javax.swing.JPanel;

public class Main extends javax.swing.JFrame {

    /**
     * Creates new form Main
     */
    private ViewBanHang viewBanHang;
    private ViewSanPham viewSanPham;
    private ViewNhanVien viewNhanVien;
    private ViewPhieuGiamGia viewDotGiamGia;
    private ViewKhachHang viewKhachHang;
    private ViewTrangChu viewTrangChu;
    private ViewHoaDon viewHoaDon;
    private ViewThongKe viewThongKe;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public Main() {
        initComponents();
        initViews();
        showDefaultView();
    }

    private void initViews() {
        // Initialize card layout for main content area
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Initialize all views
        viewBanHang = new ViewBanHang();
        viewSanPham = new ViewSanPham();
        viewNhanVien = new ViewNhanVien();
        viewDotGiamGia = new ViewPhieuGiamGia();
        viewKhachHang = new ViewKhachHang();
        viewTrangChu = new ViewTrangChu();
        viewHoaDon = new ViewHoaDon();
        viewThongKe = new ViewThongKe();

        // Add views to card layout with unique names
        mainPanel.add(viewTrangChu, "trangchu");
        mainPanel.add(viewBanHang, "banhang");
        mainPanel.add(viewSanPham, "sanpham");
        mainPanel.add(viewNhanVien, "nhanvien");
        mainPanel.add(viewDotGiamGia, "giamgia");
        mainPanel.add(viewKhachHang, "khachhang");
        mainPanel.add(viewHoaDon, "hoadon");
        mainPanel.add(viewThongKe, "thongke");

        // Add main panel to the center of the bg layered pane
        bg.setLayout(new java.awt.BorderLayout());
        bg.add(mainPanel, java.awt.BorderLayout.CENTER);
    }

    private void showDefaultView() {
        cardLayout.show(mainPanel, "trangchu");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg = new javax.swing.JLayeredPane();
        pnl_sidebar = new javax.swing.JPanel();
        btn_ViewBanHang = new javax.swing.JButton();
        btn_ViewSanPham = new javax.swing.JButton();
        btn_viewnhanvien = new javax.swing.JButton();
        btn_viewgiamgia = new javax.swing.JButton();
        btn_viewkhachhang = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        bg.setBackground(new java.awt.Color(245, 245, 245));
        bg.setOpaque(true);
        bg.setLayout(new java.awt.BorderLayout());

        btn_ViewBanHang.setText("Bán Hàng");
        btn_ViewBanHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ViewBanHangActionPerformed(evt);
            }
        });

        btn_ViewSanPham.setText("Sản Phẩm");
        btn_ViewSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ViewSanPhamActionPerformed(evt);
            }
        });

        btn_viewnhanvien.setText("Nhân Viên");
        btn_viewnhanvien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_viewnhanvienActionPerformed(evt);
            }
        });

        btn_viewgiamgia.setText("Giảm giá");
        btn_viewgiamgia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_viewgiamgiaActionPerformed(evt);
            }
        });

        btn_viewkhachhang.setText("Khách Hàng");
        btn_viewkhachhang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_viewkhachhangActionPerformed(evt);
            }
        });

        jButton6.setText("Trang chủ");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton1.setText("Thống kê");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Hóa đơn");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 255, 255));
        jLabel1.setText("VSHOESAPP");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(44, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnl_sidebarLayout = new javax.swing.GroupLayout(pnl_sidebar);
        pnl_sidebar.setLayout(pnl_sidebarLayout);
        pnl_sidebarLayout.setHorizontalGroup(
            pnl_sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_sidebarLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 12, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_sidebarLayout.createSequentialGroup()
                .addGroup(pnl_sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_viewkhachhang, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_viewgiamgia, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_viewnhanvien, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_ViewSanPham, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_ViewBanHang, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnl_sidebarLayout.setVerticalGroup(
            pnl_sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_sidebarLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_ViewBanHang, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_ViewSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_viewnhanvien, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_viewgiamgia, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_viewkhachhang, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnl_sidebar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bg, javax.swing.GroupLayout.DEFAULT_SIZE, 1362, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnl_sidebar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(bg)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_ViewBanHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ViewBanHangActionPerformed
        cardLayout.show(mainPanel, "banhang");
        setTitle("Bán Hàng");
    }//GEN-LAST:event_btn_ViewBanHangActionPerformed

    private void btn_ViewSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ViewSanPhamActionPerformed
        cardLayout.show(mainPanel, "sanpham");
        setTitle("Sản Phẩm");
    }//GEN-LAST:event_btn_ViewSanPhamActionPerformed

    private void btn_viewgiamgiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_viewgiamgiaActionPerformed
        cardLayout.show(mainPanel, "giamgia");
        setTitle("Đợt Giảm Giá");
    }//GEN-LAST:event_btn_viewgiamgiaActionPerformed

    private void btn_viewnhanvienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_viewnhanvienActionPerformed
        cardLayout.show(mainPanel, "nhanvien");
        setTitle("Nhân Viên");
    }//GEN-LAST:event_btn_viewnhanvienActionPerformed

    private void btn_viewkhachhangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_viewkhachhangActionPerformed
        cardLayout.show(mainPanel, "khachhang");
        setTitle("Khách Hàng");
    }//GEN-LAST:event_btn_viewkhachhangActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        cardLayout.show(mainPanel, "trangchu");
        setTitle("Trang chủ");
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        cardLayout.show(mainPanel, "thongke");
        setTitle("Thống kê");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        cardLayout.show(mainPanel, "hoadon");
        setTitle("Hóa đơn");
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane bg;
    private javax.swing.JButton btn_ViewBanHang;
    private javax.swing.JButton btn_ViewSanPham;
    private javax.swing.JButton btn_viewgiamgia;
    private javax.swing.JButton btn_viewkhachhang;
    private javax.swing.JButton btn_viewnhanvien;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel pnl_sidebar;
    // End of variables declaration//GEN-END:variables
}
