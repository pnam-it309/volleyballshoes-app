package com.DuAn1.volleyballshoes.app.ui.swing;

import com.DuAn1.volleyballshoes.app.ui.Model_Menu;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class MenuItem extends javax.swing.JPanel {

    private boolean selected;

    public MenuItem(Model_Menu data) {
        initComponents();
        setOpaque(false);
        lblname.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblname.setForeground(new Color(255, 255, 255));
        lblicon.setPreferredSize(new java.awt.Dimension(32, 32));
        lblicon.setMinimumSize(new java.awt.Dimension(32, 32));
        lblicon.setMaximumSize(new java.awt.Dimension(32, 32));
        lblicon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblicon.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
        if (data.type == com.DuAn1.volleyballshoes.app.ui.Model_Menu.MenuType.MENU) {
            Icon rawIcon = data.toIcon();
            if (rawIcon != null && rawIcon instanceof ImageIcon) {
                java.awt.Image img = ((ImageIcon) rawIcon).getImage();
                java.awt.Image scaled = img.getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH);
                lblicon.setIcon(new ImageIcon(scaled));
            } else {
                lblicon.setIcon(rawIcon);
            }
            lblname.setText(data.name);
        } else if (data.type == com.DuAn1.volleyballshoes.app.ui.Model_Menu.MenuType.TITLE) {
            lblicon.setText(data.name);
            lblicon.setFont(new Font("Segoe UI", Font.BOLD, 16));
            lblname.setVisible(false);
        } else {
            lblname.setText(" ");
        }
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblicon = new javax.swing.JLabel();
        lblname = new javax.swing.JLabel();

        lblname.setForeground(new java.awt.Color(255, 255, 255));
        lblname.setText("MENU NAME");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblicon, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblname)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblicon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblname))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    protected void paintComponent(Graphics grphscs) {
        Graphics2D g2 = (Graphics2D) grphscs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (selected) {
            g2.setColor(new Color(30, 162, 255, 180)); // màu xanh nhạt khi chọn
            g2.fillRoundRect(5, 2, getWidth()-10, getHeight()-4, 15, 15);
        }
        super.paintComponent(grphscs);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblicon;
    private javax.swing.JLabel lblname;
    // End of variables declaration//GEN-END:variables

}
