package packman.board;

import javax.swing.*;
import java.awt.*;

public class PacmanFrame extends JFrame {

    public PacmanFrame() {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(new Dimension(1200, 700));
        this.setLocationRelativeTo(null);
//        this.getContentPane().setBackground(Color.BLACK);
        this.setResizable(false);
        this.setTitle("PACMAN");
        this.setVisible(true);

    }
}
