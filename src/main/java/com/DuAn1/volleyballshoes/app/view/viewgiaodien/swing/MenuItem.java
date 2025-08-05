package com.DuAn1.volleyballshoes.app.view.viewgiaodien.swing;

import com.DuAn1.volleyballshoes.app.view.viewgiaodien.event.EventMenu;
import com.DuAn1.volleyballshoes.app.view.viewgiaodien.event.EventMenuSelected;
import com.DuAn1.volleyballshoes.app.view.viewgiaodien.model.ModelMenu;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.Font;

public class MenuItem extends javax.swing.JPanel {

    public ModelMenu getMenu() {
        return menu;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public EventMenuSelected getEventSelected() {
        return eventSelected;
    }

    public void setEventSelected(EventMenuSelected eventSelected) {
        this.eventSelected = eventSelected;
    }

    public int getIndex() {
        return index;
    }

    private float alpha;
    private ModelMenu menu;
    private boolean open;
    private EventMenuSelected eventSelected;
    private int index;

    private JPanel subMenuPanel;

    public MenuItem(ModelMenu menu, EventMenu event, EventMenuSelected eventSelected, int index) {
        initComponents();
        this.menu = menu;
        this.eventSelected = eventSelected;
        this.index = index;
        setOpaque(false);
        
        // Sử dụng BoxLayout với Y_AXIS
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        // Tạo panel chứa nút menu chính
        JPanel mainButtonPanel = new JPanel();
        mainButtonPanel.setOpaque(false);
        mainButtonPanel.setLayout(new BoxLayout(mainButtonPanel, BoxLayout.X_AXIS));
        mainButtonPanel.setMaximumSize(new Dimension(Short.MAX_VALUE, 40));
        
        // Tạo nút menu chính
        MenuButton firstItem = new MenuButton(menu.getIcon(), "      " + menu.getMenuName());
        firstItem.setHorizontalAlignment(SwingConstants.LEFT);
        firstItem.setMaximumSize(new Dimension(Short.MAX_VALUE, 40));
        firstItem.setPreferredSize(new Dimension(0, 40));
        
        mainButtonPanel.add(firstItem);
        
        // Thêm nút mũi tên nếu có submenu
        if (menu.getSubMenu().length > 0) {
            JLabel arrowLabel = new JLabel(open ? "▼" : "▶");
            arrowLabel.setForeground(Color.WHITE);
            arrowLabel.setFont(new Font("Arial", Font.BOLD, 12));
            mainButtonPanel.add(Box.createHorizontalGlue());
            mainButtonPanel.add(arrowLabel);
            mainButtonPanel.add(Box.createHorizontalStrut(10));
        }
        
        // Thêm sự kiện click cho nút menu chính
        firstItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (menu.getSubMenu().length > 0) {
                    open = !open;
                    updateSubMenuVisibility();
                }
                eventSelected.menuSelected(index, -1);
            }
        });
        
        add(mainButtonPanel);
        
        // Tạo panel cho submenu
        subMenuPanel = new JPanel();
        subMenuPanel.setOpaque(false);
        subMenuPanel.setLayout(new BoxLayout(subMenuPanel, BoxLayout.Y_AXIS));
        subMenuPanel.setAlignmentX(LEFT_ALIGNMENT);
        
        // Thêm các mục con vào submenu
        int subMenuIndex = -1;
        for (String st : menu.getSubMenu()) {
            MenuButton item = new MenuButton("    " + st); // Thêm thụt đầu dòng cho đẹp
            item.setIndex(++subMenuIndex);
            item.setAlignmentX(LEFT_ALIGNMENT);
            item.setMaximumSize(new Dimension(Short.MAX_VALUE, 35));
            item.setPreferredSize(new Dimension(0, 35));
            item.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    eventSelected.menuSelected(index, item.getIndex());
                }
            });
            subMenuPanel.add(item);
        }
        
        add(subMenuPanel);
        updateSubMenuVisibility();
    }

    public void updateSubMenuVisibility() {
        if (subMenuPanel != null) {
            // Cập nhật hiển thị submenu
            subMenuPanel.setVisible(open);
            
            // Cập nhật kích thước
            if (open) {
                int height = menu.getSubMenu().length * 35;
                subMenuPanel.setPreferredSize(new Dimension(Short.MAX_VALUE, height));
                subMenuPanel.setMaximumSize(new Dimension(Short.MAX_VALUE, height));
            } else {
                subMenuPanel.setPreferredSize(new Dimension(0, 0));
                subMenuPanel.setMaximumSize(new Dimension(0, 0));
            }
            
            // Cập nhật lại layout
            revalidate();
            repaint();
            
            // Cập nhật container cha
            Container parent = getParent();
            if (parent != null) {
                parent.revalidate();
                parent.repaint();
            }

            System.out.println("Updating " + subMenuPanel.getComponentCount() + " submenu items");
            for (Component comp : subMenuPanel.getComponents()) {
                comp.setVisible(open);
                ((JComponent) comp).revalidate();
            }
            
            // Force layout update
            System.out.println("Triggering revalidation and repaint");
            revalidate();
            repaint();
        } else {
            System.out.println("subMenuPanel is null!");
        }
        System.out.println("updateSubMenuVisibility completed\n");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        // No need for generated layout code as we're using BoxLayout
    }// </editor-fold>//GEN-END:initComponents

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Get component dimensions
        int width = getWidth();
        int height = getPreferredSize().height;

        // Draw background for the main menu item
        g2.setColor(new Color(50, 50, 50));
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2.fillRect(0, 0, width, 40);

        // Draw background for submenu items
        g2.setComposite(AlphaComposite.SrcOver);
        if (menu.getSubMenu().length > 0 && open) {
            g2.setColor(new Color(60, 60, 60));
            g2.fillRect(0, 40, width, height - 40);

            // Draw connecting lines for submenu items
            g2.setColor(new Color(100, 100, 100));
            g2.drawLine(30, 40, 30, height - 17);

            // Draw horizontal lines for each submenu item
            for (int i = 0; i < menu.getSubMenu().length; i++) {
                int y = ((i + 1) * 35 + 40) - 17;
                g2.drawLine(30, y, 38, y);
            }
        }

        // Draw arrow indicator if there are submenus
        if (menu.getSubMenu().length > 0) {
            createArrowButton(g2);
        }

        g2.dispose();
        super.paintComponent(grphcs);
    }

    private void createArrowButton(Graphics2D g2) {
        int size = 4;
        int y = 19;
        int x = getWidth() - 25; // Position from right
        g2.setColor(new Color(230, 230, 230));

        // Draw arrow pointing right or down based on menu state
        if (open) {
            // Down arrow
            g2.drawLine(x, y, x + 4, y + 4);
            g2.drawLine(x + 4, y + 4, x + 8, y);
        } else {
            // Right arrow
            g2.drawLine(x, y, x + 4, y + 4);
            g2.drawLine(x + 4, y + 4, x, y + 8);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
