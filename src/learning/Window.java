package learning;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {


    public Window() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.setColor(Color.BLUE);
                int width = getWidth();
                int height = getHeight();
                for (int i = 10; i < width / 2; i += 10) {
                    g.drawRect(i, i, width - i * 2, height - i * 2);
                }
            }

        };

        panel.setBackground(Color.BLACK);

        add(panel);
        setSize(200, 200);
        setLocationRelativeTo(null); // wyśrodkowanie okna
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }
}
