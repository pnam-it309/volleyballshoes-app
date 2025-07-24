package com.DuAn1.volleyballshoes.app.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class ImageUtil {
    /**
     * Hiển thị hộp thoại chọn file ảnh, trả về đường dẫn file đã chọn hoặc null nếu hủy.
     */
    public static String pickImage(Component parent) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Chọn ảnh sản phẩm");
        chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif"));
        int result = chooser.showOpenDialog(parent);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            return file.getAbsolutePath();
        }
        return null;
    }

    /**
     * Đọc file ảnh và trả về ImageIcon đã resize theo kích thước mong muốn.
     */
    public static ImageIcon readAndResize(String path, int width, int height) {
        try {
            BufferedImage img = ImageIO.read(new File(path));
            Image scaled = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaled);
        } catch (Exception ex) {
            return null;
        }
    }
}
