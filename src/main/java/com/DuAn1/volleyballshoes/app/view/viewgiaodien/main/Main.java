package com.DuAn1.volleyballshoes.app.view.viewgiaodien.main;

import com.DuAn1.volleyballshoes.app.view.viewgiaodien.component.Menu;
import com.DuAn1.volleyballshoes.app.view.viewgiaodien.component.Profile;
import com.DuAn1.volleyballshoes.app.view.viewgiaodien.event.EventMenuSelected;
import com.DuAn1.volleyballshoes.app.view.viewgiaodien.form.MainForm;
import com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewTrangChu.ViewTrangChu;
import com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewBanHang.ViewBanHang;
import com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewSanPham.ViewSanPham;
import com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewHoaDon.ViewHoaDon;
import com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewKhachHang.ViewKhachHang;
import com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewNhanVien.ViewNhanVien;
import com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewThongKe.ViewThongKe;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

public class Main extends javax.swing.JFrame {

    private Menu menu;
    private Profile profile;
    private MainForm mainForm;

    public Main() {
        initComponents();
        initMainInterface();
    }

    private void initMainInterface() {
        // Tạo sidebar panel
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BorderLayout());
        sidebarPanel.setPreferredSize(new java.awt.Dimension(250, 0));
        sidebarPanel.setBackground(new java.awt.Color(33, 105, 249));

        // Tạo profile component
        profile = new Profile();
        sidebarPanel.add(profile, BorderLayout.NORTH);

        // Tạo menu component
        menu = new Menu();
        sidebarPanel.add(menu, BorderLayout.CENTER);

        // Tạo main form
        mainForm = new MainForm();

        // Thêm event cho menu
        menu.addEvent(new EventMenuSelected() {
            @Override
            public void menuSelected(int menuIndex, int subMenuIndex) {
                // Xử lý khi menu được chọn
                showForm(menuIndex);
            }
        });

        // Khởi tạo menu items
        menu.initMenuItem();
        
        // Thiết lập layout cho frame
        setLayout(new BorderLayout());
        add(sidebarPanel, BorderLayout.WEST);
        add(mainForm, BorderLayout.CENTER);
        
        // Hiển thị trang chủ mặc định
        showForm(0);
    }

    private void showForm(int menuIndex) {
        Component form = null;
        
        try {
            switch (menuIndex) {
                case 0: // Trang Chủ
                    form = new ViewTrangChu();
                    break;
                case 1: // Bán Hàng
                    form = new ViewBanHang();
                    break;
                case 2: // Sản Phẩm
                    form = new ViewSanPham();
                    break;
                case 3: // Hóa Đơn
                    form = new ViewHoaDon();
                    break;
                case 4: // Khách Hàng
                    form = new ViewKhachHang();
                    break;
                case 5: // Nhân Viên
                    form = new ViewNhanVien();
                    break;
                case 6: // Thống Kê
                    form = new ViewThongKe();
                    break;
                default:
                    form = new ViewTrangChu(); // Mặc định hiển thị trang chủ
                    break;
            }
            
            if (form != null) {
                mainForm.showForm(form);
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi load view: " + e.getMessage());
            e.printStackTrace();
            // Tạo một panel đơn giản để hiển thị thay thế
            JPanel errorPanel = new JPanel();
            errorPanel.add(new javax.swing.JLabel("Không thể load view này. Vui lòng thử lại."));
            mainForm.showForm(errorPanel);
        }
    }

    // Helper method để load icon an toàn
    private ImageIcon loadIconSafely(String path) {
        try {
            return new ImageIcon(getClass().getResource(path));
        } catch (Exception e) {
            System.err.println("Không thể load icon: " + path);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg = new javax.swing.JLayeredPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Volleyball Shoes Management System");
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);

        bg.setBackground(new java.awt.Color(245, 245, 245));
        bg.setOpaque(true);

        javax.swing.GroupLayout bgLayout = new javax.swing.GroupLayout(bg);
        bg.setLayout(bgLayout);
        bgLayout.setHorizontalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1400, Short.MAX_VALUE)
        );
        bgLayout.setVerticalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 825, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

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
    // End of variables declaration//GEN-END:variables
}
