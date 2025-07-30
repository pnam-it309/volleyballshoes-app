/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewBanHang;


/**
 *
 * @author trinh
 */
public class ViewNhanVienGiaoHang extends javax.swing.JFrame {

    /**
     * Creates new form ViewNhanVienGiaoHang
     */
    public ViewNhanVienGiaoHang() {
        initComponents();

    }

 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblNhanVIen = new javax.swing.JTable();
        btn_Them4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tblNhanVIen.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "STT", "Mã nhân viên", "Tên nhân viên", "SDT"
            }
        ));
        tblNhanVIen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhanVIenMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblNhanVIen);

        btn_Them4.setBackground(new java.awt.Color(0, 102, 255));
        btn_Them4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_Them4.setForeground(new java.awt.Color(255, 255, 255));
        btn_Them4.setText("Chọn");
        btn_Them4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Them4btn_ThemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(107, 107, 107)
                .addComponent(btn_Them4, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(119, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(60, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_Them4)
                .addGap(40, 40, 40))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_Them4btn_ThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Them4btn_ThemActionPerformed
 
    }//GEN-LAST:event_btn_Them4btn_ThemActionPerformed

    private void tblNhanVIenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhanVIenMouseClicked

    }//GEN-LAST:event_tblNhanVIenMouseClicked

    private void txtTimKiemCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtTimKiemCaretUpdate

    }//GEN-LAST:event_txtTimKiemCaretUpdate

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(ViewNhanVienGiaoHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewNhanVienGiaoHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewNhanVienGiaoHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewNhanVienGiaoHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewNhanVienGiaoHang().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Them4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblNhanVIen;
    // End of variables declaration//GEN-END:variables
}
