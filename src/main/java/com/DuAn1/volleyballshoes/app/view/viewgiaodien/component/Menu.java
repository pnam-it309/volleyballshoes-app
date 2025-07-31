package com.DuAn1.volleyballshoes.app.view.viewgiaodien.component;

import com.DuAn1.volleyballshoes.app.view.viewgiaodien.event.*;
import com.DuAn1.volleyballshoes.app.view.viewgiaodien.model.ModelMenu;
import com.DuAn1.volleyballshoes.app.view.viewgiaodien.swing.scrollbar.ScrollBarCustom;
import java.awt.*;
import javax.swing.ImageIcon;

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
        // Thêm logo và tên shop ở đầu sidebar
        
        
        // Thêm các menu items
        addMenu(new ModelMenu(new ImageIcon(getClass().getResource("/icon/home.png")), "Trang Chủ"));
        addMenu(new ModelMenu(new ImageIcon(getClass().getResource("/icon/cart.png")), "Bán Hàng"));
        addMenu(new ModelMenu(new ImageIcon(getClass().getResource("/icon/product.png")), "Sản Phẩm", "Sản Phẩm", "Chi Tiết Sản Phẩm", "Thuộc Tính"));
        addMenu(new ModelMenu(new ImageIcon(getClass().getResource("/icon/invoice.png")), "Hóa Đơn"));
        addMenu(new ModelMenu(new ImageIcon(getClass().getResource("/icon/stats.png")), "Thống Kê"));
        addMenu(new ModelMenu(new ImageIcon(getClass().getResource("/icon/employee.png")), "Nhân Viên"));
        addMenu(new ModelMenu(new ImageIcon(getClass().getResource("/icon/customer.png")), "Khách Hàng"));
        addMenu(new ModelMenu(new ImageIcon(getClass().getResource("/icon/discount.png")), "Phiếu Giảm Giá"));
    }

    private void addMenu(ModelMenu menu) {
//        panel.add(new MenuItem(menu, getEventMenu(), event, panel.getComponentCount()));
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

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 312, Short.MAX_VALUE)
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 523, Short.MAX_VALUE)
        );

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
