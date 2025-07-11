/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DuAn1.volleyballshoes.app.ui;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author nickh
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Model_Menu {
    public String icon;
    public String name;
    public MenuType type;
    
    public Icon toIcon() {
        String path = "/com/DuAn1/volleyballshoes/app/icons/" + icon + ".png";
        java.net.URL location = getClass().getResource(path);
        System.out.println("Đang load icon: " + path + " | URL: " + location);
        if (location != null) {
            return new ImageIcon(location);
        } else {
            System.err.println("Không tìm thấy icon: " + path);
            return null;
        }
    }
    public static enum MenuType{
        TITLE,MENU,EMTY   
    }
} 