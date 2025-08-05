package com.DuAn1.volleyballshoes.app.view.viewgiaodien.main;

import com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewBanHang.ViewBanHang;
import com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewHoaDon.ViewHoaDon;
import com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewKhachHang.ViewKhachHang;
import com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewNhanVien.ViewNhanVien;
import com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewPhieuGiamGia.ViewPhieuGiamGia;
import com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewSanPham.ViewSanPham;
import com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewSanPham.ViewSanPhamChiTiet;
import com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewSanPham.ViewThuocTinh;
import com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewThongKe.ViewThongKe;
import com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewTrangChu.ViewTrangChu;
import com.DuAn1.volleyballshoes.app.view.viewdangnhap.main.MainDangNhap;
import com.DuAn1.volleyballshoes.app.view.viewgiaodien.component.Menu;
import com.DuAn1.volleyballshoes.app.view.viewgiaodien.event.EventMenuSelected;
import com.DuAn1.volleyballshoes.app.view.viewgiaodien.event.EventShowPopupMenu;
import com.DuAn1.volleyballshoes.app.view.viewgiaodien.form.MainForm;
import com.DuAn1.volleyballshoes.app.view.viewgiaodien.swing.MenuItem;
import com.DuAn1.volleyballshoes.app.view.viewgiaodien.swing.PopupMenu;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;



public class Main extends javax.swing.JFrame {

    private Menu menu;
    private MainForm main;
    private Timer timer;
    private boolean isMenuShowing = true;
    private int menuWidth = 230;
    private int currentWidth = 230;

    public Main() {
        initComponents();
        init();
    }

    private void init() {
        // Sử dụng BorderLayout thay cho MigLayout
        bg.setLayout(new BorderLayout());
        
        menu = new Menu();
        main = new MainForm();
        menu.setPreferredSize(new Dimension(menuWidth, menu.getPreferredSize().height));
        menu.addEvent(new EventMenuSelected() {
            @Override
            public void menuSelected(int menuIndex, int subMenuIndex) {
                MainDangNhap DN = new MainDangNhap();
                if (menuIndex == 0) {
                    main.showForm(new ViewTrangChu());
                }
                if (menuIndex == 1) {
                    main.showForm(new ViewBanHang());
                }
                if (menuIndex == 2) {
                    if (subMenuIndex == 0) {
                        main.showForm(new ViewSanPham());
                    } else if (subMenuIndex == 1) {
                        main.showForm(new ViewSanPhamChiTiet());
                    } else if (subMenuIndex == 2) {
                        main.showForm(new ViewThuocTinh());
                    }
                }
                if (menuIndex == 3) {
                    main.showForm(new ViewHoaDon());
                }
                if (menuIndex == 4) {
//                    if (MainDangNhap.chucVu == 1) {
//                        Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Bạn không có quyền truy cập");
//                    } else {
                        main.showForm(new ViewThongKe());
//                    }
                }
                if (menuIndex == 5) {
//                    if (MainDangNhap.chucVu == 1) {
//                        Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Bạn không có quyền truy cập");
//                    } else {
                        main.showForm(new ViewNhanVien());
                    //}
                }
                if (menuIndex == 6) {
                    main.showForm(new ViewKhachHang());
                }
                if (menuIndex == 7) {
//                    if (MainDangNhap.chucVu == 1) {
//                        Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Bạn không có quyền truy cập");
//                    } else {
                        main.showForm(new ViewPhieuGiamGia());
                    //}
                }
                if (menuIndex == 13) {
                    setVisible(false);
                    DN.setVisible(true);
                    dispose();
                }

            }

        });

        menu.addEventShowPopup(new EventShowPopupMenu() {
            @Override
            public void showPopup(Component com) {
                MenuItem item = (MenuItem) com;
                PopupMenu popup = new PopupMenu(Main.this, item.getIndex(), item.getEventSelected(), item.getMenu().getSubMenu());
                int x = Main.this.getX() + 66;
                int y = Main.this.getY() + com.getY() + 150;
                popup.setLocation(x, y);
                popup.setVisible(true);
            }
        });
        menu.initMenuItem();
        
        // Thêm menu và main form vào panel
        bg.add(menu, BorderLayout.WEST);
        bg.add(main, BorderLayout.CENTER);
        
        // Tạo hiệu ứng mở/đóng menu bằng Timer
        timer = new Timer(15, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int step = 10;  // Tốc độ di chuyển
                if (!isMenuShowing) {
                    currentWidth = Math.max(60, currentWidth - step);
                    if (currentWidth <= 60) {
                        currentWidth = 60;
                        timer.stop();
                        menu.setShowMenu(false);
                        menu.setEnableMenu(true);
                    }
                } else {
                    currentWidth = Math.min(menuWidth, currentWidth + step);
                    if (currentWidth >= menuWidth) {
                        currentWidth = menuWidth;
                        timer.stop();
                        menu.setShowMenu(true);
                        menu.setEnableMenu(true);
                    }
                }
                // Cập nhật kích thước menu
                menu.setPreferredSize(new Dimension(currentWidth, menu.getPreferredSize().height));
                menu.revalidate();
                bg.revalidate();
            }
        });

        main.showForm(new ViewTrangChu());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg = new javax.swing.JLayeredPane();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        bg.setBackground(new java.awt.Color(245, 245, 245));
        bg.setOpaque(true);

        jPanel1.setLayout(new java.awt.BorderLayout());

        bg.setLayer(jPanel1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout bgLayout = new javax.swing.GroupLayout(bg);
        bg.setLayout(bgLayout);
        bgLayout.setHorizontalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bgLayout.createSequentialGroup()
                .addGap(0, 1095, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        bgLayout.setVerticalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 819, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 296, Short.MAX_VALUE)
                .addComponent(bg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.Alignment.TRAILING)
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
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
