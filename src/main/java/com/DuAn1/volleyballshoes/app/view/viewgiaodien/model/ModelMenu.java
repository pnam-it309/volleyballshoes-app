package com.DuAn1.volleyballshoes.app.view.viewgiaodien.model;

import javax.swing.Icon;

public class ModelMenu {
    private Icon icon;
    private String menuName;
    private int menuIndex;

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }
    
    public ModelMenu(Icon icon, String menuName) {
        this.icon = icon;
        this.menuName = menuName;
    }

    public ModelMenu() {
    }
    
    public int getMenuIndex() {
        return menuIndex;
    }
    
    public void setMenuIndex(int menuIndex) {
        this.menuIndex = menuIndex;
    }
}
