package com.DuAn1.volleyballshoes.app.view.viewgiaodien.swing;

import com.DuAn1.volleyballshoes.app.view.viewgiaodien.event.EventMenuSelected;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.lang.reflect.Method;



public class PopupMenu extends javax.swing.JDialog {

    private Timer timer;
    private boolean show = true;
    private float opacity = 0f;
    private static final int ANIMATION_DURATION = 200; // ms
    private static final int ANIMATION_STEP = 16; // ~60 FPS

    public PopupMenu(java.awt.Frame parent, int index, EventMenuSelected eventSelected, String... subMenu) {
        super(parent, false);
        initComponents();
        setOpacity(0f);
        setBackground(new Color(0, 0, 0, 0));
        
        // Create content panel with GridLayout
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(0, 1, 0, 0));
        contentPanel.setBackground(new Color(60, 60, 60));
        contentPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8));
        
        int subMenuIndex = -1;
        for (String st : subMenu) {
            MenuButton item = new MenuButton(st, true);
            item.setIndex(++subMenuIndex);
            item.setPreferredSize(new Dimension(120, 35));
            item.setMaximumSize(new Dimension(Short.MAX_VALUE, 35));
            item.setAlignmentX(LEFT_ALIGNMENT);
            item.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    eventSelected.menuSelected(index, item.getIndex());
                    closeMenu();
                }
            });
            contentPanel.add(item);
        }
        
        // Set the size based on content
        int height = 35 * subMenu.length + 16; // 16px for padding
        setSize(new Dimension(136, height)); // 120 + 16 (8px padding on each side)
        
        // Add content panel to the dialog
        setLayout(new java.awt.BorderLayout());
        add(contentPanel, java.awt.BorderLayout.CENTER);
        
        // Initialize animation timer
        initAnimationTimer();
    }
    
    private void initAnimationTimer() {
        timer = new Timer(ANIMATION_STEP, new ActionListener() {
            private long startTime = -1;
            
            @Override
            public void actionPerformed(ActionEvent e) {
                if (startTime < 0) {
                    startTime = System.currentTimeMillis();
                }
                
                long elapsed = System.currentTimeMillis() - startTime;
                float fraction = (float) elapsed / ANIMATION_DURATION;
                
                if (fraction >= 1.0f) {
                    fraction = 1.0f;
                    timer.stop();
                    
                    if (!show) {
                        setVisible(false);
                    }
                }
                
                // Apply easing function for smoother animation
                float progress = show ? fraction : (1.0f - fraction);
                setOpacity(progress);
            }
        });
    }

    @Override
    public void setVisible(boolean bln) {
        super.setVisible(bln);
        if (bln) {
            show = true;
            opacity = 0f;
            if (timer != null) {
                timer.stop();
                timer.start();
            }
        }
    }

    private void closeMenu() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
        show = false;
        if (timer != null) {
            timer.restart();
        } else {
            setVisible(false);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelPopup1 = new com.DuAn1.volleyballshoes.app.view.viewgiaodien.swing.PanelPopup();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
                formWindowLostFocus(evt);
            }
        });

        javax.swing.GroupLayout panelPopup1Layout = new javax.swing.GroupLayout(panelPopup1);
        panelPopup1.setLayout(panelPopup1Layout);
        panelPopup1Layout.setHorizontalGroup(
            panelPopup1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 252, Short.MAX_VALUE)
        );
        panelPopup1Layout.setVerticalGroup(
            panelPopup1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 8, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPopup1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelPopup1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowLostFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowLostFocus
        // Only close if we don't have a popup menu showing
        if (!hasFocus()) {
            closeMenu();
        }
    }//GEN-LAST:event_formWindowLostFocus
    
    @Override
    public void setOpacity(float opacity) {
        this.opacity = opacity;
        if (opacity < 0) opacity = 0;
        if (opacity > 1) opacity = 1;
        
        // Apply opacity to the window using reflection for compatibility
        try {
            // Using reflection to avoid direct dependency on com.sun classes
            Class<?> awtUtilitiesClass = Class.forName("com.sun.awt.AWTUtilities");
            Method mSetWindowOpacity = awtUtilitiesClass.getMethod("setWindowOpacity", java.awt.Window.class, float.class);
            Method mIsWindowOpaque = awtUtilitiesClass.getMethod("isWindowOpaque", java.awt.Window.class);
            
            if ((boolean)mIsWindowOpaque.invoke(null, this)) {
                mSetWindowOpacity.invoke(null, this, opacity);
            }
        } catch (Exception e) {
            // Fallback if setting opacity is not supported
            // Try using Java 9+ API if available
            try {
                Method mSetOpacity = this.getClass().getMethod("setOpacity", float.class);
                mSetOpacity.invoke(this, opacity);
            } catch (Exception ex) {
                // If all else fails, just set the background to be semi-transparent
                setBackground(new Color(0, 0, 0, (int)(opacity * 255)));
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.DuAn1.volleyballshoes.app.view.viewgiaodien.swing.PanelPopup panelPopup1;
    // End of variables declaration//GEN-END:variables
}
