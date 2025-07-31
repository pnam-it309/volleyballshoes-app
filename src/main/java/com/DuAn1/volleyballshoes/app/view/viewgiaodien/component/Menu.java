package com.DuAn1.volleyballshoes.app.view.viewgiaodien.component;

import com.DuAn1.volleyballshoes.app.view.viewgiaodien.event.*;
import com.DuAn1.volleyballshoes.app.view.viewgiaodien.model.ModelMenu;
import com.DuAn1.volleyballshoes.app.view.viewgiaodien.swing.scrollbar.ScrollBarCustom;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Menu extends javax.swing.JPanel {

    public boolean isShowMenu() {
        return showMenu;
    }

    public void addEvent(EventMenuSelected event) {
        this.event = event;
    }

    public void setEnableMenu(boolean enableMenu) {
        this.enableMenu = enableMenu;
    }

    public void setShowMenu(boolean showMenu) {
        this.showMenu = showMenu;
    }

    public void addEventShowPopup(EventShowPopupMenu eventShowPopup) {
        this.eventShowPopup = eventShowPopup;
    }

    private EventMenuSelected event;
    private EventShowPopupMenu eventShowPopup;
    private boolean enableMenu = true;
    private boolean showMenu = true;

    public Menu() {
        initComponents();
        setOpaque(false);
        sp.getViewport().setOpaque(false);
        sp.setVerticalScrollBar(new ScrollBarCustom());
    }

    public void initMenuItem() {
        // Thêm các menu items từ viewchucnang
        addMenu(new ModelMenu("Trang Chủ"), 0);
        addMenu(new ModelMenu("Bán Hàng"), 1);
        addMenu(new ModelMenu("Sản Phẩm"), 2);
        addMenu(new ModelMenu("Hóa Đơn"), 3);
        addMenu(new ModelMenu("Khách Hàng"), 4);
        addMenu(new ModelMenu("Nhân Viên"), 5);
        addMenu(new ModelMenu("Thống Kê"), 6);
    }

    private void addMenu(ModelMenu menu, int index) {
        // Tạo menu item đơn giản
        JButton btn = new JButton(menu.getMenuName());
        btn.setFont(new java.awt.Font("Segoe UI", 0, 14));
        btn.setForeground(new java.awt.Color(255, 255, 255));
        btn.setBackground(new java.awt.Color(33, 105, 249));
        btn.setBorder(new EmptyBorder(10, 20, 10, 20));
        btn.setPreferredSize(new java.awt.Dimension(220, 45));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover effect
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(45, 125, 269));
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(33, 105, 249));
            }
        });
        
        // Thêm action listener với index tương ứng
        final int menuIndex = index;
        btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (event != null) {
                    event.menuSelected(menuIndex, 0);
                }
            }
        });
        
        panel.add(btn);
        panel.add(Box.createVerticalStrut(8)); // Thêm khoảng cách giữa các button
        panel.revalidate();
        panel.repaint();
    }

    private EventMenu getEventMenu() {
        return new EventMenu() {
            @Override
            public boolean menuPressed(Component com, boolean open) {
                if (enableMenu) {
                    if (isShowMenu()) {
                        if (open) {
                        } else {
                        }
                        return true;
                    } else {
                        eventShowPopup.showPopup(com);
                    }
                }
                return false;
            }
        };
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sp = new javax.swing.JScrollPane();
        panel = new javax.swing.JPanel();

        sp.setBorder(null);
        sp.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        panel.setOpaque(false);
        panel.setLayout(new javax.swing.BoxLayout(panel, javax.swing.BoxLayout.Y_AXIS));

        sp.setViewportView(panel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sp, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(146, 146, 146)
                .addComponent(sp))
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint gra = new GradientPaint(0, 0, new Color(33, 105, 249), getWidth(), 0, new Color(93, 58, 196));
        g2.setPaint(gra);
        g2.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(grphcs);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel panel;
    private javax.swing.JScrollPane sp;
    // End of variables declaration//GEN-END:variables
}
