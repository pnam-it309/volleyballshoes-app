//package com.DuAn1.volleyballshoes.app.view.viewgiaodien.swing;
//
//import java.awt.Component;
//import java.awt.Dimension;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import javax.swing.Timer;
//
//public class MenuAnimation {
//
//    private final MenuItem menuItem;
//    private Timer timer;
//    private boolean open;
//    private long startTime;
//    private int animationDuration;
//    private int targetHeight;
//    private float currentFraction;
//
//    public MenuAnimation(Component component) {
//        this(component, 200);
//    }
//
//    public MenuAnimation(Component component, int duration) {
//        this.menuItem = (MenuItem) component;
//        this.animationDuration = duration;
//        this.targetHeight = component.getPreferredSize().height;
//        initAnimator(component);
//    }
//
//    private void initAnimator(Component component) {
//        timer = new Timer(16, new ActionListener() { // ~60 FPS
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                long currentTime = System.currentTimeMillis();
//                float elapsed = currentTime - startTime;
//                currentFraction = Math.min(elapsed / (float) animationDuration, 1.0f);
//                
//                // Apply easing function (ease-out)
//                float easedFraction = easeOutCubic(currentFraction);
//                
//                // Calculate height and alpha based on animation state
//                float h, alpha;
//                if (open) {
//                    h = 40 + ((targetHeight - 40) * easedFraction);
//                    alpha = easedFraction;
//                } else {
//                    h = 40 + ((targetHeight - 40) * (1 - easedFraction));
//                    alpha = 1 - easedFraction;
//                }
//                
//                // Update component
//                menuItem.setAlpha(alpha);
//                component.setPreferredSize(new Dimension(component.getWidth(), (int) h));
//                component.revalidate();
//                component.repaint();
//                
//                // Stop timer when animation completes
//                if (currentFraction >= 1.0f) {
//                    timer.stop();
//                }
//            }
//        });
//    }
//    
//    // Easing function for smooth animation (ease-out cubic)
//    private float easeOutCubic(float x) {
//        return (float) (1 - Math.pow(1 - x, 3));
//    }
//
//    public void openMenu() {
//        if (timer.isRunning()) {
//            timer.stop();
//        }
//        open = true;
//        startTime = System.currentTimeMillis();
//        timer.start();
//    }
//
//    public void closeMenu() {
//        if (timer.isRunning()) {
//            timer.stop();
//        }
//        open = false;
//        startTime = System.currentTimeMillis();
//        timer.start();
//    }
//    
//    // Clean up resources when done
//    public void dispose() {
//        if (timer != null && timer.isRunning()) {
//            timer.stop();
//        }
//    }
//}
