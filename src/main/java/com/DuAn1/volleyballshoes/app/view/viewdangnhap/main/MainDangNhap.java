/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.DuAn1.volleyballshoes.app.view.viewdangnhap.main;

import com.DuAn1.volleyballshoes.app.view.viewgiaodien.main.Main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *  
 * @author trinh
 */
public class MainDangNhap extends javax.swing.JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnDangNhap;
    private JButton btnThoat;
  
    public MainDangNhap() {
        initComponents();
        initLoginForm();
    }
    
    private void initLoginForm() {
        // Tạo panel chính
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245));
        
        // Panel đăng nhập
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout());
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Logo/Tiêu đề
        JLabel titleLabel = new JLabel("VOLLEYBALL SHOES");
        titleLabel.setFont(new java.awt.Font("Segoe UI", 1, 28));
        titleLabel.setForeground(new Color(33, 105, 249));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        loginPanel.add(titleLabel, gbc);
        
        JLabel subtitleLabel = new JLabel("Hệ thống quản lý giày bóng chuyền");
        subtitleLabel.setFont(new java.awt.Font("Segoe UI", 0, 14));
        subtitleLabel.setForeground(new Color(100, 100, 100));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        loginPanel.add(subtitleLabel, gbc);
        
        // Username
        JLabel userLabel = new JLabel("Tài khoản:");
        userLabel.setFont(new java.awt.Font("Segoe UI", 0, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        loginPanel.add(userLabel, gbc);
        
        txtUsername = new JTextField(20);
        txtUsername.setFont(new java.awt.Font("Segoe UI", 0, 14));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        loginPanel.add(txtUsername, gbc);
        
        // Password
        JLabel passLabel = new JLabel("Mật khẩu:");
        passLabel.setFont(new java.awt.Font("Segoe UI", 0, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        loginPanel.add(passLabel, gbc);
        
        txtPassword = new JPasswordField(20);
        txtPassword.setFont(new java.awt.Font("Segoe UI", 0, 14));
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        loginPanel.add(txtPassword, gbc);
        
        // Buttons panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        buttonPanel.setOpaque(false);
        
        btnDangNhap = new JButton("Đăng nhập");
        btnDangNhap.setFont(new java.awt.Font("Segoe UI", 1, 14));
        btnDangNhap.setBackground(new Color(33, 105, 249));
        btnDangNhap.setForeground(Color.WHITE);
        btnDangNhap.setFocusPainted(false);
        btnDangNhap.setPreferredSize(new java.awt.Dimension(120, 35));
        
        btnThoat = new JButton("Thoát");
        btnThoat.setFont(new java.awt.Font("Segoe UI", 1, 14));
        btnThoat.setBackground(new Color(220, 53, 69));
        btnThoat.setForeground(Color.WHITE);
        btnThoat.setFocusPainted(false);
        btnThoat.setPreferredSize(new java.awt.Dimension(120, 35));
        
        GridBagConstraints btnGbc = new GridBagConstraints();
        btnGbc.insets = new Insets(20, 10, 0, 10);
        
        btnGbc.gridx = 0;
        btnGbc.gridy = 0;
        buttonPanel.add(btnDangNhap, btnGbc);
        
        btnGbc.gridx = 1;
        btnGbc.gridy = 0;
        buttonPanel.add(btnThoat, btnGbc);
        
        // Thêm button panel vào login panel
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        loginPanel.add(buttonPanel, gbc);
        
        // Thêm login panel vào main panel
        mainPanel.add(loginPanel, BorderLayout.CENTER);
        
        // Thêm vào frame
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
        
        // Thêm event listeners
        btnDangNhap.addActionListener(e -> {
            // Đăng nhập thành công luôn
            this.dispose(); // Đóng form đăng nhập
            SwingUtilities.invokeLater(() -> {
                new Main().setVisible(true); // Mở form chính
            });
        });
        
        btnThoat.addActionListener(e -> {
            System.exit(0);
        });
        
        // Enter key để đăng nhập
        txtPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    btnDangNhap.doClick();
                }
            }
        });
    }
   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Đăng nhập - Volleyball Shoes Management System");
        setResizable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
     
    }//GEN-LAST:event_button1ActionPerformed

    private void btnQuenMKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuenMKActionPerformed

        
    }//GEN-LAST:event_btnQuenMKActionPerformed

    private void btnThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoatActionPerformed

    }//GEN-LAST:event_btnThoatActionPerformed

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
            java.util.logging.Logger.getLogger(MainDangNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainDangNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainDangNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainDangNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainDangNhap().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
