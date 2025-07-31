package com.DuAn1.volleyballshoes.app.view.viewgiaodien.component;

import com.DuAn1.volleyballshoes.app.view.viewgiaodien.event.*;
import com.DuAn1.volleyballshoes.app.view.viewgiaodien.model.ModelMenu;
import com.DuAn1.volleyballshoes.app.view.viewgiaodien.swing.scrollbar.ScrollBarCustom;
import java.awt.*;
import java.awt.image.BufferedImage;
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
        setOpaque(true);
        sp.getViewport().setOpaque(true);
        sp.setVerticalScrollBar(new ScrollBarCustom());
    }

    public void initMenuItem() {
        // Thêm các menu items từ viewchucnang với icon
        addMenu(new ModelMenu("Trang Chủ"), 0, "1.png");
        addMenu(new ModelMenu("Bán Hàng"), 1, "2.png");
        addMenu(new ModelMenu("Sản Phẩm"), 2, "3.png");
        addMenu(new ModelMenu("Hóa Đơn"), 3, "4.png");
        addMenu(new ModelMenu("Khách Hàng"), 4, "5.png");
        addMenu(new ModelMenu("Nhân Viên"), 5, "6.png");
        addMenu(new ModelMenu("Thống Kê"), 6, "7.png");
    }

    private void addMenu(ModelMenu menu, int index, String iconPath) {
        // Tạo menu item với icon
        JButton btn = new JButton();
        
        // Load icon
        try {
            // Sử dụng đường dẫn đúng từ resources
            String fullIconPath = "/com/DuAn1/volleyballshoes/app/icons/" + iconPath;
            java.net.URL iconUrl = getClass().getResource(fullIconPath);
            
            if (iconUrl != null) {
                System.out.println("Load icon thành công: " + fullIconPath);
                ImageIcon icon = new ImageIcon(iconUrl);
                Image img = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                btn.setIcon(new ImageIcon(img));
            } else {
                System.err.println("Không tìm thấy icon: " + fullIconPath);
                // Tạo icon với text thay vì hình vuông trắng
                btn.setIcon(createTextIcon(menu.getMenuName().substring(0, 1)));
            }
        } catch (Exception e) {
            System.err.println("Lỗi load icon: " + iconPath + " - " + e.getMessage());
            btn.setIcon(createTextIcon(menu.getMenuName().substring(0, 1)));
        }
        
        btn.setText(menu.getMenuName());
        btn.setFont(new java.awt.Font("Segoe UI", 1, 14));
        btn.setForeground(new java.awt.Color(255, 255, 255));
        btn.setBackground(new java.awt.Color(33, 105, 249));
        btn.setBorder(new EmptyBorder(12, 25, 12, 25));
        btn.setPreferredSize(new java.awt.Dimension(230, 50));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);
        btn.setContentAreaFilled(true);
        btn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btn.setIconTextGap(15);
        
        // Hover effect
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(45, 125, 255));
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
        panel.add(Box.createVerticalStrut(10)); // Thêm khoảng cách giữa các button
        panel.revalidate();
        panel.repaint();
        
        // Đảm bảo panel hiển thị
        sp.revalidate();
        sp.repaint();
    }

    private ImageIcon createTextIcon(String text) {
        // Tạo icon với text
        BufferedImage img = new BufferedImage(20, 20, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 12));
        
        // Căn giữa text
        FontMetrics fm = g2.getFontMetrics();
        int x = (20 - fm.stringWidth(text)) / 2;
        int y = (20 - fm.getHeight()) / 2 + fm.getAscent();
        
        g2.drawString(text, x, y);
        g2.dispose();
        return new ImageIcon(img);
    }

    private ImageIcon createDefaultIcon() {
        // Tạo icon mặc định màu trắng
        BufferedImage img = new BufferedImage(20, 20, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, 20, 20);
        g2.dispose();
        return new ImageIcon(img);
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

        panel.setOpaque(true);
        panel.setLayout(new javax.swing.BoxLayout(panel, javax.swing.BoxLayout.Y_AXIS));
        panel.setBackground(new Color(33, 105, 249));

        sp.setViewportView(panel);
        sp.setOpaque(true);
        sp.getViewport().setOpaque(true);
        sp.setBackground(new Color(33, 105, 249));

        setLayout(new BorderLayout());
        add(sp, BorderLayout.CENTER);
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
