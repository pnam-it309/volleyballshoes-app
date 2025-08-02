package com.DuAn1.volleyballshoes.app.view.viewgiaodien.main;

import com.DuAn1.volleyballshoes.app.view.viewgiaodien.component.Menu;
import com.DuAn1.volleyballshoes.app.view.viewgiaodien.component.Profile;
import com.DuAn1.volleyballshoes.app.view.viewgiaodien.event.EventMenuSelected;
import com.DuAn1.volleyballshoes.app.view.viewgiaodien.form.MainForm;
import com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewTrangChu.ViewTrangChu;
import com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewBanHang.*;
import com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewSanPham.*;
import com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewHoaDon.ViewHoaDon;
import com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewKhachHang.ViewKhachHang;
import com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewNhanVien.ViewNhanVien;
import com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewThongKe.ViewThongKe;
import com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewDotGiamGia.ViewDotGiamGia;
import com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewPhieuGiamGia.ViewPhieuGiamGia;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Main extends javax.swing.JFrame {

    private Menu menu;
    private Profile profile;
    private MainForm mainForm;

    public Main() {
        initComponents();
        initMainInterface();
    }

    private void initMainInterface() {
        // Xóa layout cũ và tạo layout mới
        getContentPane().removeAll();
        
        // Tạo sidebar panel
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BorderLayout());
        sidebarPanel.setPreferredSize(new java.awt.Dimension(250, 0));
        sidebarPanel.setMinimumSize(new java.awt.Dimension(250, 0));
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
                System.out.println("Menu selected: " + menuIndex);
                showForm(menuIndex, subMenuIndex);
            }
        });

        // Khởi tạo menu items
        menu.initMenuItem();
        
        // Thiết lập layout cho frame
        setLayout(new BorderLayout());
        add(sidebarPanel, BorderLayout.WEST);
        add(mainForm, BorderLayout.CENTER);
        
        // Hiển thị trang chủ mặc định
        showForm(0,0);
        
        // Đảm bảo sidebar hiển thị
        revalidate();
        repaint();
    }

    private void showForm(int menuIndex, int subMenuIndex) {
        // Đảm bảo subMenuIndex là hợp lệ
        if (subMenuIndex < 0) {
            subMenuIndex = 0;
        }
        Component form = null;
        
        System.out.println("Đang load form cho menu index: " + menuIndex + ", subMenuIndex: " + subMenuIndex);
        
        try {
            switch (menuIndex) {
                case 0: // Trang Chủ
                    System.out.println("Loading ViewTrangChu...");
                    form = new ViewTrangChu();
                    break;
                case 1: // Bán Hàng
                    // Xử lý các màn hình con của Bán Hàng
                    switch (subMenuIndex) {
                        case 0: // Tạo hóa đơn bán hàng
                            System.out.println("Loading ViewBanHang...");
                            form = new ViewBanHang();
                            break;
                        case 1: // Thêm khách hàng mới
                            System.out.println("Loading ViewThemKhachHang...");
                            form = new JPanel(); // TODO: Thay thế bằng một JPanel phù hợp hoặc refactor ViewThemKhachHang thành JPanel nếu cần
                            break;
                        default:
                            form = new ViewBanHang(); // Mặc định hiển thị tạo hóa đơn
                    }
                    break;
                case 2: // Sản Phẩm
                    // Xử lý các màn hình con của Sản phẩm
                    switch (subMenuIndex) {
                        case 0: // Danh sách sản phẩm
                            System.out.println("Loading ViewSanPham (Danh sách sản phẩm)...");
                            form = new ViewSanPham();
                            break;
                        case 1: // Thêm sản phẩm mới
                            System.out.println("Loading ViewThemSanPhamm...");
                            form = new ViewThemSanPhamm();
                            break;
                        case 2: // Chi tiết sản phẩm
                            System.out.println("Loading ViewSanPhamChiTiet...");
                            form = new ViewSanPhamChiTiet();
                            break;
                        case 3: // Quản lý thuộc tính
                            System.out.println("Loading ViewThuocTinh...");
                            form = new ViewThuocTinh();
                            break;
                        case 4: // Quét QR sản phẩm
                            System.out.println("Loading QuetQRSanPham...");
                            form = new JPanel(); // TODO: Thay thế bằng JPanel phù hợp hoặc refactor QuetQRSanPham thành JPanel nếu cần
                            break;
                        default:
                            form = new ViewSanPham(); // Mặc định hiển thị danh sách sản phẩm
                    }
                    break;
                case 3: // Hóa Đơn
                    System.out.println("Loading ViewHoaDon...");
                    form = new ViewHoaDon();
                    break;
                case 4: // Khách Hàng
                    System.out.println("Loading ViewKhachHang...");
                    form = new ViewKhachHang();
                    break;
                case 5: // Nhân Viên
                    System.out.println("Loading ViewNhanVien...");
                    form = new ViewNhanVien();
                    break;
                case 6: // Thống Kê
                    System.out.println("Loading ViewThongKe...");
                    form = new ViewThongKe();
                    break;
                case 7: // Khuyến Mãi
                    // Xử lý các màn hình con của Khuyến Mãi
                    switch (subMenuIndex) {
                        case 0: // Đợt giảm giá
                            System.out.println("Loading ViewDotGiamGia...");
                            form = new ViewDotGiamGia();
                            break;
                        case 1: // Phiếu giảm giá
                            System.out.println("Loading ViewPhieuGiamGia...");
                            form = new ViewPhieuGiamGia();
                            break;
                        default:
                            form = new ViewDotGiamGia(); // Mặc định hiển thị đợt giảm giá
                    }
                    break;
                default:
                    System.out.println("Loading default ViewTrangChu...");
                    form = new ViewTrangChu(); // Mặc định hiển thị trang chủ
                    break;
            }
            
            if (form != null) {
                System.out.println("Form loaded successfully, showing in mainForm...");
                mainForm.showForm(form);
                mainForm.revalidate();
                mainForm.repaint();
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
