

import com.DuAn1.volleyballshoes.app.ui.ProductManagement;
import javax.swing.*;
import java.awt.*;

public class ProductManagementTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Test ProductManagement Panel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1200, 700);
            frame.setLayout(new BorderLayout());
            frame.add(new ProductManagement(), BorderLayout.CENTER);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
