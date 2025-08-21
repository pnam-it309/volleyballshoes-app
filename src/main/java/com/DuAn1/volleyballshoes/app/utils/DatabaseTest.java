package com.DuAn1.volleyballshoes.app.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseTest {
    public static void main(String[] args) {
        String connectionUrl = "jdbc:sqlserver://localhost:1433;database=VShoesApp;encrypt=true;trustserverCertificate=true;";
        String username = "sa";
        String password = "123";

        try (Connection conn = DriverManager.getConnection(connectionUrl, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as count FROM Customer")) {
            
            System.out.println("Kết nối CSDL thành công!");
            
            if (rs.next()) {
                int count = rs.getInt("count");
                System.out.println("Số lượng khách hàng trong bảng: " + count);
                
                if (count > 0) {
                    System.out.println("\n5 khách hàng đầu tiên:");
                    ResultSet customers = stmt.executeQuery(
                        "SELECT TOP 5 customer_id, customer_code, customer_full_name, customer_phone, customer_email FROM Customer");
                    
                    while (customers.next()) {
                        System.out.println(String.format(
                            "ID: %d, Mã: %s, Tên: %s, SĐT: %s, Email: %s",
                            customers.getInt("customer_id"),
                            customers.getString("customer_code"),
                            customers.getString("customer_full_name"),
                            customers.getString("customer_phone"),
                            customers.getString("customer_email")
                        ));
                    }
                }
            }
            
        } catch (Exception e) {
            System.err.println("Lỗi kết nối CSDL:");
            e.printStackTrace();
            
            // Kiểm tra xem database có tồn tại không
            try (Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;encrypt=true;trustserverCertificate=true;", username, password);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT name FROM sys.databases WHERE name = 'VShoesApp'")) {
                
                if (rs.next()) {
                    System.out.println("\nDatabase VShoesApp tồn tại.");
                    System.out.println("Vui lòng kiểm tra lại tên bảng 'Customer' trong database.");
                } else {
                    System.out.println("\nDatabase VShoesApp không tồn tại.");
                    System.out.println("Vui lòng tạo database VShoesApp trước khi chạy ứng dụng.");
                }
                
            } catch (Exception ex) {
                System.err.println("Không thể kết nối tới SQL Server:");
                ex.printStackTrace();
                System.out.println("\nVui lòng kiểm tra:");
                System.out.println("1. SQL Server đã được cài đặt và đang chạy");
                System.out.println("2. SQL Server đã bật chế độ SQL Server Authentication");
                System.out.println("3. Tài khoản 'sa' có mật khẩu '123' có quyền truy cập");
                System.out.println("4. Cổng kết nối mặc định là 1433");
            }
        }
    }
}
