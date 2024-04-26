import javax.swing.*;
import java.awt.*;

public class Window2 extends JFrame {


    public Window2() {

        JTextArea jTextArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(jTextArea);

        int bold = Font.BOLD | Font.ITALIC;
        int size = 24;
        String serif = Font.SERIF;

        Font font = new Font(serif, bold, size);
        jTextArea.setFont(font);
        jTextArea.setBackground(Color.BLACK);
        jTextArea.setForeground(Color.RED);

        add(scrollPane);
        setSize(200, 200);
        setLocationRelativeTo(null); // wyśrodkowanie okna
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }
}
