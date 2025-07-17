package com.DuAn1.volleyballshoes.app.utils;

import javax.swing.*;
import java.awt.*;

/**
 * Utility class để xử lý thông báo trong ứng dụng
 * Thay thế cho việc sử dụng JOptionPane trực tiếp
 */
public class NotificationUtil {
    
    /**
     * Hiển thị thông báo thành công
     * @param parentComponent Component cha
     * @param message Nội dung thông báo
     */
    public static void showSuccess(Component parentComponent, String message) {
        JOptionPane.showMessageDialog(
            parentComponent,
            message,
            "Thành công",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    /**
     * Hiển thị thông báo lỗi
     * @param parentComponent Component cha
     * @param message Nội dung thông báo
     */
    public static void showError(Component parentComponent, String message) {
        JOptionPane.showMessageDialog(
            parentComponent,
            message,
            "Lỗi",
            JOptionPane.ERROR_MESSAGE
        );
    }
    
    /**
     * Hiển thị thông báo cảnh báo
     * @param parentComponent Component cha
     * @param message Nội dung thông báo
     */
    public static void showWarning(Component parentComponent, String message) {
        JOptionPane.showMessageDialog(
            parentComponent,
            message,
            "Cảnh báo",
            JOptionPane.WARNING_MESSAGE
        );
    }
    
    /**
     * Hiển thị thông báo thông tin
     * @param parentComponent Component cha
     * @param message Nội dung thông báo
     */
    public static void showInfo(Component parentComponent, String message) {
        JOptionPane.showMessageDialog(
            parentComponent,
            message,
            "Thông tin",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    /**
     * Hiển thị dialog xác nhận
     * @param parentComponent Component cha
     * @param message Nội dung thông báo
     * @return true nếu người dùng chọn Yes, false nếu chọn No
     */
    public static boolean showConfirm(Component parentComponent, String message) {
        int result = JOptionPane.showConfirmDialog(
            parentComponent,
            message,
            "Xác nhận",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        return result == JOptionPane.YES_OPTION;
    }
    
    /**
     * Hiển thị dialog xác nhận với tùy chọn tùy chỉnh
     * @param parentComponent Component cha
     * @param message Nội dung thông báo
     * @param title Tiêu đề dialog
     * @return true nếu người dùng chọn Yes, false nếu chọn No
     */
    public static boolean showConfirm(Component parentComponent, String message, String title) {
        int result = JOptionPane.showConfirmDialog(
            parentComponent,
            message,
            title,
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        return result == JOptionPane.YES_OPTION;
    }
    
    /**
     * Hiển thị dialog input
     * @param parentComponent Component cha
     * @param message Nội dung thông báo
     * @param title Tiêu đề dialog
     * @return Chuỗi người dùng nhập, null nếu hủy
     */
    public static String showInput(Component parentComponent, String message, String title) {
        return JOptionPane.showInputDialog(
            parentComponent,
            message,
            title,
            JOptionPane.QUESTION_MESSAGE
        );
    }
    
    /**
     * Hiển thị thông báo lỗi với exception
     * @param parentComponent Component cha
     * @param message Nội dung thông báo
     * @param exception Exception gây lỗi
     */
    public static void showError(Component parentComponent, String message, Exception exception) {
        String fullMessage = message + "\nChi tiết: " + exception.getMessage();
        showError(parentComponent, fullMessage);
    }
    
    /**
     * Hiển thị thông báo thành công với thông tin bổ sung
     * @param parentComponent Component cha
     * @param message Nội dung thông báo
     * @param additionalInfo Thông tin bổ sung
     */
    public static void showSuccessWithInfo(Component parentComponent, String message, String additionalInfo) {
        String fullMessage = message + "\n" + additionalInfo;
        showSuccess(parentComponent, fullMessage);
    }
    
    /**
     * Hiển thị thông báo xác nhận xóa
     * @param parentComponent Component cha
     * @param itemName Tên item cần xóa
     * @return true nếu xác nhận xóa
     */
    public static boolean showDeleteConfirm(Component parentComponent, String itemName) {
        String message = "Bạn có chắc chắn muốn xóa " + itemName + "?";
        return showConfirm(parentComponent, message, "Xác nhận xóa");
    }
    
    /**
     * Hiển thị thông báo validate lỗi
     * @param parentComponent Component cha
     * @param fieldName Tên trường bị lỗi
     * @param errorMessage Thông báo lỗi
     */
    public static void showValidationError(Component parentComponent, String fieldName, String errorMessage) {
        String message = "Lỗi ở trường '" + fieldName + "': " + errorMessage;
        showError(parentComponent, message);
    }
} 