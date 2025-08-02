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

    private JPanel currentSubMenu = null;
    private JButton currentMenuButton = null;
    
    public void initMenuItem() {
        // Thêm các menu items từ viewchucnang với icon
        ModelMenu homeMenu = new ModelMenu("Trang Chủ");
        addMenu(homeMenu, 0, "1.png");
        
        // Menu Bán Hàng với submenu
        ModelMenu salesMenu = new ModelMenu("Bán Hàng");
        salesMenu.setSubMenu(new String[]{
            "Tạo hóa đơn bán hàng",
            "Thêm khách hàng mới"
        });
        addMenu(salesMenu, 1, "2.png");
        
        // Menu Sản phẩm với submenu
        ModelMenu productMenu = new ModelMenu("Sản Phẩm");
        productMenu.setSubMenu(new String[]{
            "Danh sách sản phẩm",
            "Thêm sản phẩm mới",
            "Chi tiết sản phẩm",
            "Quản lý thuộc tính",
            "Quét QR sản phẩm"
        });
        addMenu(productMenu, 2, "3.png");
        
        // Menu Hóa đơn
        ModelMenu invoiceMenu = new ModelMenu("Hóa Đơn");
        addMenu(invoiceMenu, 3, "4.png");
        
        // Menu Khách hàng
        ModelMenu customerMenu = new ModelMenu("Khách Hàng");
        addMenu(customerMenu, 4, "5.png");
        
        // Menu Nhân viên
        ModelMenu staffMenu = new ModelMenu("Nhân Viên");
        addMenu(staffMenu, 5, "6.png");
        
        // Menu Thống kê
        ModelMenu statsMenu = new ModelMenu("Thống Kê");
        addMenu(statsMenu, 6, "7.png");
        
        // Menu Khuyến mãi với submenu
        ModelMenu promotionMenu = new ModelMenu("Khuyến Mãi");
        promotionMenu.setSubMenu(new String[]{
            "Đợt giảm giá",
            "Phiếu giảm giá"
        });
        addMenu(promotionMenu, 7, "8.png");
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
        
        // Thêm mũi tên nếu có submenu
        if (menu.getSubMenu() != null && menu.getSubMenu().length > 0) {
            btn.setIcon(createIconWithArrow(btn.getIcon()));
        }
        
        // Hover effect
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(45, 125, 255));
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (currentMenuButton != btn) {
                    btn.setBackground(new Color(33, 105, 249));
                }
            }
        });
        
        // Thêm action listener với index tương ứng
        final int menuIndex = index;
        btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (menu.getSubMenu() != null && menu.getSubMenu().length > 0) {
                    // Nếu có submenu, hiển thị/ẩn submenu
                    toggleSubMenu(btn, menu, menuIndex);
                } else {
                    // Nếu không có submenu, gọi sự kiện bình thường
                    if (currentSubMenu != null) {
                        panel.remove(currentSubMenu);
                        currentSubMenu = null;
                        currentMenuButton = null;
                        panel.revalidate();
                        panel.repaint();
                    }
                    if (event != null) {
                        event.menuSelected(menuIndex, 0);
                    }
                }
            }
        });
        
        panel.add(btn);
        panel.add(Box.createVerticalStrut(5)); // Thêm khoảng cách giữa các button
        
        // Nếu là menu Sản phẩm, thêm submenu
        if (menu.getMenuName().equals("Sản Phẩm")) {
            addSubMenu(menu, menuIndex);
        }
        
        panel.revalidate();
        panel.repaint();
    }
    
    private void toggleSubMenu(JButton btn, ModelMenu menu, int menuIndex) {
        if (currentSubMenu != null) {
            // Nếu đang mở submenu khác, đóng nó lại
            if (currentMenuButton == btn) {
                // Đóng submenu hiện tại
                panel.remove(currentSubMenu);
                currentSubMenu = null;
                currentMenuButton = null;
                btn.setBackground(new Color(33, 105, 249));
            } else {
                // Mở submenu mới, đóng submenu cũ
                panel.remove(currentSubMenu);
                currentMenuButton.setBackground(new Color(33, 105, 249));
                addSubMenu(menu, menuIndex);
                currentMenuButton = btn;
                btn.setBackground(new Color(60, 135, 255));
            }
        } else {
            // Mở submenu mới
            addSubMenu(menu, menuIndex);
            currentMenuButton = btn;
            btn.setBackground(new Color(60, 135, 255));
        }
        panel.revalidate();
        panel.repaint();
    }
    
    private void addSubMenu(ModelMenu menu, int menuIndex) {
        if (menu.getSubMenu() == null || menu.getSubMenu().length == 0) {
            return;
        }
        
        // Tạo panel chứa submenu
        JPanel subMenuPanel = new JPanel();
        subMenuPanel.setLayout(new BoxLayout(subMenuPanel, BoxLayout.Y_AXIS));
        subMenuPanel.setBackground(new Color(25, 95, 239));
        subMenuPanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 10, 0));
        
        // Thêm các item vào submenu
        for (int i = 0; i < menu.getSubMenu().length; i++) {
            String subMenuItem = menu.getSubMenu()[i];
            JButton subMenuBtn = new JButton(subMenuItem);
            
            // Cấu hình button submenu
            subMenuBtn.setFont(new java.awt.Font("Segoe UI", 0, 13));
            subMenuBtn.setForeground(new java.awt.Color(220, 220, 220));
            subMenuBtn.setBackground(new Color(25, 95, 239));
            subMenuBtn.setBorder(new EmptyBorder(8, 15, 8, 15));
            subMenuBtn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
            subMenuBtn.setFocusPainted(false);
            subMenuBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            subMenuBtn.setOpaque(true);
            subMenuBtn.setContentAreaFilled(true);
            
            // Hover effect cho submenu
            subMenuBtn.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    subMenuBtn.setBackground(new Color(45, 115, 255));
                }
                
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    subMenuBtn.setBackground(new Color(25, 95, 239));
                }
            });
            
            // Sự kiện khi click vào submenu
            final int subMenuIndex = i;
            subMenuBtn.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    if (event != null) {
                        event.menuSelected(menuIndex, subMenuIndex + 1);
                    }
                }
            });
            
            subMenuPanel.add(subMenuBtn);
        }
        
        // Thêm submenu vào panel chính
        panel.add(subMenuPanel, panel.getComponentCount() - 1);
        currentSubMenu = subMenuPanel;
    }
    
    private Icon createIconWithArrow(Icon originalIcon) {
        // Tạo icon mới với mũi tên bên phải
        BufferedImage img = new BufferedImage(30, 20, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        
        // Vẽ icon gốc
        if (originalIcon != null) {
            originalIcon.paintIcon(null, g2, 0, 0);
        }
        
        // Vẽ mũi tên
        g2.setColor(Color.WHITE);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int[] xPoints = {15, 20, 25};
        int[] yPoints = {10, 15, 10};
        g2.fillPolygon(xPoints, yPoints, 3);
        
        g2.dispose();
        return new ImageIcon(img);
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
