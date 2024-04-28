package learning;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class LabelWindow extends JFrame {

    public LabelWindow() {
        ImageIcon pacmanLogo = new ImageIcon("resources/images/pacmanLogo.png");

        ImageIcon pacman = new ImageIcon("resources/images/pacman.png");
        Border border = BorderFactory.createLineBorder(Color.YELLOW, 5);

        JLabel jLabel = createLabel(pacman, border);
        jLabel.setBounds(0, 0, 550, 550);
        this.add(jLabel);


        this.setLayout(null);
        this.setSize(1300, 600 );
        this.setVisible(true);
//        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setIconImage(pacmanLogo.getImage());
    }

    private static JLabel createLabel(ImageIcon pacman, Border border) {

        JLabel jLabel = new JLabel();
        jLabel.setText("PACMAN");
        jLabel.setIcon(pacman);

        jLabel.setHorizontalTextPosition(JLabel.CENTER);
        jLabel.setVerticalTextPosition(JLabel.TOP);

        jLabel.setForeground(Color.YELLOW);
        jLabel.setFont(new Font("", Font.BOLD, 64));
        jLabel.setIconTextGap(-30);

        jLabel.setBackground(Color.BLACK);
        jLabel.setOpaque(true);

        jLabel.setBorder(border);

        jLabel.setHorizontalAlignment(JLabel.CENTER);

        return jLabel;
    }
}
