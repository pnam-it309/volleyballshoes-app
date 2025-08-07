package com.DuAn1.volleyballshoes.app.view.viewgiaodien.main;



public class Main extends javax.swing.JFrame {
    

    
    /**
     * Creates new form Main
     */
    public Main() {
        initComponents();
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

        javax.swing.GroupLayout pnl_sidebarLayout = new javax.swing.GroupLayout(pnl_sidebar);
        pnl_sidebar.setLayout(pnl_sidebarLayout);
        pnl_sidebarLayout.setHorizontalGroup(
            pnl_sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_sidebarLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(pnl_sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btn_viewkhachhang)
                        .addComponent(btn_viewgiamgia)
                        .addComponent(btn_viewnhanvien)
                        .addGroup(pnl_sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_ViewBanHang)
                            .addComponent(btn_ViewSanPham)))
                    .addGroup(pnl_sidebarLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jButton6)))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        pnl_sidebarLayout.setVerticalGroup(
            pnl_sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_sidebarLayout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(jButton6)
                .addGap(85, 85, 85)
                .addComponent(btn_ViewBanHang)
                .addGap(31, 31, 31)
                .addComponent(btn_ViewSanPham)
                .addGap(27, 27, 27)
                .addComponent(btn_viewnhanvien)
                .addGap(26, 26, 26)
                .addComponent(btn_viewgiamgia)
                .addGap(18, 18, 18)
                .addComponent(btn_viewkhachhang)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(pnl_sidebar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bg, javax.swing.GroupLayout.PREFERRED_SIZE, 1064, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(bg, javax.swing.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE)
                    .addComponent(pnl_sidebar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_ViewBanHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ViewBanHangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_ViewBanHangActionPerformed

    private void btn_ViewSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ViewSanPhamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_ViewSanPhamActionPerformed

    private void btn_viewgiamgiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_viewgiamgiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_viewgiamgiaActionPerformed

    private void btn_viewnhanvienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_viewnhanvienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_viewnhanvienActionPerformed

    private void btn_viewkhachhangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_viewkhachhangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_viewkhachhangActionPerformed

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
    private javax.swing.JButton jButton6;
    private javax.swing.JPanel pnl_sidebar;
    // End of variables declaration//GEN-END:variables
}
