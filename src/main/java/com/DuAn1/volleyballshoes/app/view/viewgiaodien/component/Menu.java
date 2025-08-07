//package com.DuAn1.volleyballshoes.app.view.viewgiaodien.component;
//
//import com.DuAn1.volleyballshoes.app.view.viewgiaodien.event.*;
//import com.DuAn1.volleyballshoes.app.view.viewgiaodien.model.ModelMenu;
//import com.DuAn1.volleyballshoes.app.view.viewgiaodien.swing.*;
//import com.DuAn1.volleyballshoes.app.view.viewgiaodien.swing.scrollbar.ScrollBarCustom;
//
//import java.awt.Color;
//import java.awt.Component;
//import java.awt.Dimension;
//import java.awt.GradientPaint;
//import java.awt.Graphics;
//import java.awt.Graphics2D;
//import java.awt.Rectangle;
//import java.awt.RenderingHints;
//import java.awt.event.ComponentAdapter;
//import java.awt.event.ComponentEvent;
//import javax.swing.BoxLayout;
//import javax.swing.ImageIcon;
//import javax.swing.JPanel;
//
//public class Menu extends javax.swing.JPanel {
//
//    private static final int COLLAPSED_WIDTH = 60;
//    private static final int EXPANDED_WIDTH = 230;
//
//    public boolean isShowMenu() {
//        return showMenu;
//    }
//
//    public void addEvent(EventMenuSelected event) {
//        this.event = event;
//        // Update all existing menu items with the new event
//        if (panel != null) {
//            for (Component comp : panel.getComponents()) {
//                if (comp instanceof MenuItem) {
//                    ((MenuItem) comp).setEventSelected(event);
//                }
//            }
//        }
//    }
//
//    public void setEnableMenu(boolean enableMenu) {
//        this.enableMenu = enableMenu;
//    }
//
//    public void setShowMenu(boolean showMenu) {
//        this.showMenu = showMenu;
//    }
//
//    public void addEventShowPopup(EventShowPopupMenu eventShowPopup) {
//        this.eventShowPopup = eventShowPopup;
//    }
//
//    private EventMenuSelected event;
//    private EventShowPopupMenu eventShowPopup;
//    private boolean enableMenu = true;
//    private boolean showMenu = true;
//    private boolean isAnimating = false;
//    private java.util.List<ModelMenu> menus = new java.util.ArrayList<>();
//
//    public Menu() {
//        initComponents();
//        setOpaque(false);
//        sp.getViewport().setOpaque(false);
//        sp.setVerticalScrollBar(new ScrollBarCustom());
//        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
//
//        // Set initial size
//        setPreferredSize(new Dimension(EXPANDED_WIDTH, getPreferredSize().height));
//
//        // Add component listener to handle resizing of menu items
//        addComponentListener(new ComponentAdapter() {
//            @Override
//            public void componentResized(ComponentEvent e) {
//                updateMenuItemsWidth();
//            }
//        });
//    }
//
//    private ImageIcon createSafeImageIcon(String path) {
//        try {
//            java.net.URL imgURL = getClass().getResource(path);
//            if (imgURL != null) {
//                return new ImageIcon(imgURL);
//            } else {
//                System.err.println("Couldn't find file: " + path);
//                // Trả về một ImageIcon trống nếu không tìm thấy ảnh
//                return new ImageIcon();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ImageIcon();
//        }
//    }
//    
//    private void updateMenuItemsWidth() {
//        int width = getWidth();
//        for (Component comp : panel.getComponents()) {
//            if (comp instanceof MenuItem) {
//                comp.setPreferredSize(new Dimension(width - 20, comp.getPreferredSize().height));
//                comp.setSize(comp.getPreferredSize());
//            }
//        }
//        panel.revalidate();
//    }
//
//    public void initMenuItem() {
//        // Clear existing menus
//        menus.clear();
//        panel.removeAll();
//        
//        // Sử dụng đường dẫn tương đối từ thư mục hiện tại
//        String basePath = "../icon/";
//        
//        // Thêm các menu với xử lý ngoại lệ khi không tìm thấy ảnh
//        try {
//            // Thêm menu Trang Chủ
//            ModelMenu trangChu = new ModelMenu(createSafeImageIcon(basePath + "1.png"), "Trang Chủ");
//            addMenu(trangChu);
//            
//            // Thêm menu Bán Hàng
//            ModelMenu banHang = new ModelMenu(createSafeImageIcon(basePath + "2.png"), "Bán Hàng");
//            addMenu(banHang);
//            
//            // Thêm menu Sản Phẩm với các submenu
//            ModelMenu menuSanPham = new ModelMenu(createSafeImageIcon(basePath + "3.png"), "Sản Phẩm");
//            // Thêm submenu
//            menuSanPham.setSubMenu(new String[]{"Sản Phẩm", "Chi Tiết Sản Phẩm", "Thuộc Tính"});
//            addMenu(menuSanPham);
//            
//            // Các menu khác
//            ModelMenu hoaDon = new ModelMenu(createSafeImageIcon(basePath + "4.png"), "Hóa Đơn");
//            addMenu(hoaDon);
//            
//            ModelMenu thongKe = new ModelMenu(createSafeImageIcon(basePath + "5.png"), "Thống Kê");
//            addMenu(thongKe);
//            
//            ModelMenu nhanVien = new ModelMenu(createSafeImageIcon(basePath + "6.png"), "Nhân Viên");
//            addMenu(nhanVien);
//            
//            ModelMenu khachHang = new ModelMenu(createSafeImageIcon(basePath + "7.png"), "Khách Hàng");
//            addMenu(khachHang);
//            
//            ModelMenu phieuGiamGia = new ModelMenu(createSafeImageIcon(basePath + "9.png"), "Phiếu Giảm Giá");
//            addMenu(phieuGiamGia);
//            
//            // Thêm menu Thoát
//            ModelMenu thoat = new ModelMenu(createSafeImageIcon(basePath + "10.png"), "Thoát");
//            thoat.setMenuName("Thoát");
//            addMenu(thoat);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void addMenu(ModelMenu menu) {
//        menus.add(menu);
//        menu.setMenuIndex(menus.size() - 1);
//        // Ensure we have an event handler - use a dummy one if none is set
//        EventMenuSelected handler = this.event != null ? this.event : new EventMenuSelected() {
//            @Override
//            public void menuSelected(int menuIndex, int subMenuIndex) {
//                System.out.println("Default handler - Menu selected: " + menuIndex + ", " + subMenuIndex);
//            }
//        };
//        MenuItem item = new MenuItem(menu, getEventMenu(), handler, panel.getComponentCount());
//        item.setPreferredSize(new java.awt.Dimension(EXPANDED_WIDTH - 20, 45));
//        item.setMaximumSize(new java.awt.Dimension(Short.MAX_VALUE, 45));
//        item.setMinimumSize(new java.awt.Dimension(0, 45));
//        panel.add(item);
//        updateMenuItemsWidth();
//    }
//    
//    public ModelMenu getMenuByIndex(int index) {
//        if (index >= 0 && index < menus.size()) {
//            return menus.get(index);
//        }
//        return null;
//    }
//    
//    public void addEventSelected(com.DuAn1.volleyballshoes.app.view.viewgiaodien.event.EventMenuSelected event) {
//        this.event = event;
//    }
//
//    private EventMenu getEventMenu() {
//        return new EventMenu() {
//            @Override
//            public boolean menuPressed(Component com, boolean open) {
//                System.out.println("\n--- menuPressed called ---");
//                System.out.println("enableMenu: " + enableMenu);
//                System.out.println("isShowMenu: " + isShowMenu());
//                
//                if (enableMenu) {
//                    if (isShowMenu()) {
//                        // Toggle submenu visibility
//                        if (com instanceof MenuItem) {
//                            MenuItem clickedItem = (MenuItem) com;
//                            System.out.println("Clicked menu item: " + clickedItem.getMenu().getMenuName());
//                            System.out.println("Current open state: " + clickedItem.isOpen());
//                            System.out.println("Requested open state: " + open);
//                            
//                            // Close all other open menus first
//                            System.out.println("Closing other open menus...");
//                            int closedCount = 0;
//                            for (Component component : panel.getComponents()) {
//                                if (component instanceof MenuItem) {
//                                    MenuItem item = (MenuItem) component;
//                                    if (item != clickedItem && item.isOpen()) {
//                                        System.out.println("  - Closing menu: " + item.getMenu().getMenuName());
//                                        item.setOpen(false);
//                                        item.updateSubMenuVisibility();
//                                        closedCount++;
//                                    }
//                                }
//                            }
//                            System.out.println("Closed " + closedCount + " other menus");
//                            
//                            // Only update if the state is actually changing
//                            if (clickedItem.isOpen() != open) {
//                                System.out.println("Toggling menu state from " + clickedItem.isOpen() + " to " + open);
//                                clickedItem.setOpen(open);
//                                clickedItem.updateSubMenuVisibility();
//                                
//                                // Scroll to show the clicked item if needed
//                                Rectangle rect = com.getBounds();
//                                rect.setLocation(0, rect.y);
//                                scrollRectToVisible(rect);
//                                
//                                System.out.println("Menu update complete");
//                            } else {
//                                System.out.println("Menu state already " + open + ", no change needed");
//                            }
//                        } else {
//                            System.out.println("Component is not a MenuItem");
//                        }
//                        return true;
//                    } else {
//                        System.out.println("Menu is collapsed, showing popup");
//                        // Show popup if menu is collapsed
//                        if (eventShowPopup != null) {
//                            eventShowPopup.showPopup(com);
//                        }
//                    }
//                } else {
//                    System.out.println("Menu is disabled");
//                }
//                return false;
//            }
//        };
//    }
//
//    public void hideallMenu() {
//        for (Component com : panel.getComponents()) {
//            if (com instanceof MenuItem) {
//                MenuItem item = (MenuItem) com;
//                if (item.isOpen()) {
//                    item.setOpen(false);
//                }
//            }
//        }
//        panel.revalidate();
//    }
//
//    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sp = new javax.swing.JScrollPane();
        panel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        sp.setBorder(null);
        sp.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        panel.setOpaque(false);

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        sp.setViewportView(panel);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 255, 255));
        jLabel1.setText("VShoesApp");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(jLabel1)
                .addContainerGap(67, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sp, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sp, javax.swing.GroupLayout.DEFAULT_SIZE, 507, Short.MAX_VALUE))
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel panel;
    private javax.swing.JScrollPane sp;
    // End of variables declaration//GEN-END:variables
}
