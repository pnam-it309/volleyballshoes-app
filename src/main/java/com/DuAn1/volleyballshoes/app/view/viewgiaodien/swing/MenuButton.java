package com.DuAn1.volleyballshoes.app.view.viewgiaodien.swing;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;


public class MenuButton extends JButton {

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    private int index;
    private Timer timer;
    private int targetSize;
    private float animatSize;
    private Point pressedPoint;
    private float alpha;
    private Color effectColor = new Color(255, 255, 255, 150);
    private long startTime;
    private final int ANIMATION_DURATION = 400; // 400ms animation duration

    public MenuButton(Icon icon, String text) {
        super(text);
        setIcon(icon);
        init();
        setBorder(new EmptyBorder(1, 20, 1, 1));
    }

    public MenuButton(String text) {
        super(text);
        init();
        setBorder(new EmptyBorder(1, 50, 1, 1));
    }

    public MenuButton(String text, boolean subMenu) {
        super(text);
        init();
    }

    private void init() {
        setContentAreaFilled(false);
        setForeground(new Color(255, 255, 255));
        setHorizontalAlignment(JButton.LEFT);
        
        // Initialize timer for animation
        timer = new Timer(16, new ActionListener() { // ~60 FPS
            @Override
            public void actionPerformed(ActionEvent e) {
                long currentTime = System.currentTimeMillis();
                float elapsed = currentTime - startTime;
                float fraction = Math.min(elapsed / ANIMATION_DURATION, 1.0f);
                
                // Update animation values
                if (fraction > 0.5f) {
                    alpha = 1 - fraction;
                } else {
                    alpha = 0.5f;
                }
                animatSize = fraction * targetSize;
                repaint();
                
                // Stop timer when animation completes
                if (fraction >= 1.0f) {
                    timer.stop();
                }
            }
        });
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                targetSize = Math.max(getWidth(), getHeight()) * 2;
                animatSize = 0;
                pressedPoint = me.getPoint();
                alpha = 0.5f;
                startTime = System.currentTimeMillis();
                timer.restart();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (pressedPoint != null) {
            g2.setColor(effectColor);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2.fillOval((int) (pressedPoint.x - animatSize / 2), (int) (pressedPoint.y - animatSize / 2), (int) animatSize, (int) animatSize);
        }
        g2.setComposite(AlphaComposite.SrcOver);
        super.paintComponent(grphcs);
    }
}
