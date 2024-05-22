package learning.JVievport;

import javax.swing.*;
import java.awt.*;

public class SplitColorPanelExample {
    public static void main(String[] args) {

        JFrame frame = new JFrame("Split Color JPanel Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 250);


        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int width = getWidth();
                int height = getHeight();


                g.setColor(Color.RED);
                g.fillRect(0, 0, 50, height);
                g.fillRect(width - 50, 0, 50, height);


                g.setColor(Color.GREEN);
                g.fillRect(50, 0, (width - 100) / 2, height);
                g.setColor(Color.BLUE);
                g.fillRect(50 + (width - 100) / 2, 0, (width - 100) / 2, height);
            }
        };
        mainPanel.setPreferredSize(new Dimension(700, 200));


        JViewport viewport = new JViewport() {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(500, 200);
            }
        };
        viewport.setView(mainPanel);
        viewport.setViewPosition(new Point(100, 0));


        JPanel container = new JPanel();
        container.setLayout(new BorderLayout());
        container.add(viewport, BorderLayout.CENTER);


        JPanel topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(500, 50));
        JLabel label = new JLabel("Cześć");
        topPanel.add(label);


        container.add(topPanel, BorderLayout.NORTH);


        frame.setLayout(new BorderLayout());
        frame.add(container, BorderLayout.CENTER);


        frame.setVisible(true);
    }
}
