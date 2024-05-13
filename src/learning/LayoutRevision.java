package learning;

import javax.swing.*;
import java.awt.*;

public class LayoutRevision extends JFrame {

    public LayoutRevision() {
        // Ustawienie tytułu ramki
        setTitle("Przykładowy JFrame");

        setSize(400, 300);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel bluePanel = new JPanel();
        bluePanel.setBackground(Color.BLUE);
        bluePanel.setPreferredSize(new Dimension(400, 100));

        JPanel redPanel = new JPanel();
        redPanel.setBackground(Color.RED);
        redPanel.setPreferredSize(new Dimension(400, 100));

//        mainPanel.add(redPanel, BorderLayout.NORTH);
        mainPanel.add(bluePanel, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
//        pack();
    }

    public static void main(String[] args) {
        // Tworzenie nowej instancji klasy MainFrame
        new LayoutRevision();
    }
}
