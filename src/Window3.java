import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window3 extends JFrame {


    public Window3() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JButton b1 = new JButton("PageStart");
        JButton b2 = new JButton("PageEnd");
        JButton b3 = new JButton("LineStart");
        JButton b4 = new JButton("LineEnd");
        JButton b5 = new JButton("Center");

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout());
        jPanel.add(b1);
        jPanel.add(new JButton("AAAAA"));
        jPanel.add(new JButton("1111"));
        jPanel.add(new JButton("222222"));
        jPanel.add(new JButton("333333"));

        b5.addActionListener(e -> b5.setText("JABADABADU"));

        add(jPanel, BorderLayout.PAGE_START);
        add(b2, BorderLayout.PAGE_END);
        add(b3, BorderLayout.LINE_START);
        add(b4, BorderLayout.LINE_END);
        add(b5, BorderLayout.CENTER);

        setSize(400, 400);
        setLocationRelativeTo(null); // wyśrodkowanie okna
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }
}
