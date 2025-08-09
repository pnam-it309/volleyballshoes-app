/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author congnguyen
 */
import com.DuAn1.volleyballshoes.app.view.viewchucnang.ViewKhachHang.ViewKhachHang;
    import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class TestViewKhachHang {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Test ViewKhachHang");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Tạo instance của JPanel ViewKhachHang
            ViewKhachHang panel = new ViewKhachHang();

            // Thêm panel vào frame
            frame.add(panel);

            frame.pack(); // Tự động điều chỉnh kích thước vừa với nội dung
            frame.setLocationRelativeTo(null); // Căn giữa màn hình
            frame.setVisible(true);
        });
    }
}


