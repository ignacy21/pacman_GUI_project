import javax.swing.*;
import java.awt.*;

public class WindowWithPanels extends JFrame {

    public WindowWithPanels() {
        JPanel bluePanel = createJPanel(Color.BLUE, 0, 0, 300, 300);
        JPanel redPanel = createJPanel(Color.RED, 300, 0, 300, 300);
        JPanel greenPanel = createJPanel(Color.GREEN, 300, 300, 300, 600);

//        bluePanel.setLayout(new BorderLayout());
        bluePanel.setLayout(null);

        JLabel labelHi = new JLabel();
        labelHi.setText("Hi");
        labelHi.setForeground(Color.YELLOW);
        labelHi.setHorizontalAlignment(JLabel.CENTER);
        labelHi.setBounds(0, 0, 69, 69);

        bluePanel.add(labelHi);

        this.add(bluePanel);
        this.add(greenPanel);
        this.add(redPanel);

        this.setSize(new Dimension(900, 900));
        this.getContentPane().setBackground(Color.BLACK);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    private static JPanel createJPanel(Color color, int x, int y, int width, int height) {
        JPanel jPanel = new JPanel();
        jPanel.setBackground(color);
        jPanel.setBounds(x, y, width, height);

        return jPanel;
    }
}
