/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.DuAn1.volleyballshoes.app.ui.component;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseListener;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author nickh
 */
public class Sidebar extends JPanel {
    private JPanel panelMenu;

    /**
     * Creates new form sideber
     */
    public Sidebar() {
        setOpaque(false);
        setBackground(new Color(51, 51, 51));
        setPreferredSize(new Dimension(150, 400));
        setMinimumSize(new Dimension(150, 400));
        setMaximumSize(new Dimension(150, 400));
        setLayout(new java.awt.BorderLayout());
        panelMenu = new JPanel();
        panelMenu.setOpaque(false);
        panelMenu.setLayout(new BoxLayout(panelMenu, BoxLayout.Y_AXIS));
        // Xoá dòng padding phía trên để sidebar dính sát header
        add(panelMenu, java.awt.BorderLayout.CENTER);
    }

    /**
     * Thêm item vào sidebar
     * @param text tên menu
     * @param iconPath đường dẫn icon (tính từ resource, ví dụ: "/com/DuAn1/volleyballshoes/app/icons/home.png")
     * @param active true nếu là mục đang chọn
     * @param listener MouseListener cho sự kiện click
     */
    public void addSidebarItem(String text, String iconPath, boolean active, MouseListener listener) {
        JLabel label = new JLabel(text);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setForeground(active ? new Color(255, 200, 0) : Color.WHITE);
        label.setCursor(new Cursor(Cursor.HAND_CURSOR));
        label.setMaximumSize(new Dimension(150, 40));
        label.setMinimumSize(new Dimension(100, 40));
        label.setPreferredSize(new Dimension(120, 40));
        label.setFont(label.getFont().deriveFont(16f));
        if (iconPath != null) {
            try {
                label.setIcon(new ImageIcon(getClass().getResource(iconPath)));
                label.setIconTextGap(10);
            } catch (Exception e) {
                // Không tìm thấy icon thì bỏ qua
            }
        }
        if (listener != null) {
            label.addMouseListener(listener);
        }
        panelMenu.add(label);
        panelMenu.add(Box.createRigidArea(new Dimension(0, 10)));
        panelMenu.revalidate();
        panelMenu.repaint();
    }

    @Override
    public void paint(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        Area area = new Area(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 15, 15));
        g2.fill(area);
        g2.dispose();
        super.paint(grphcs);
    }
}
