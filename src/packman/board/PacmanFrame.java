package packman.board;

import javax.swing.*;
import java.awt.*;

public class PacmanFrame extends JFrame {

    public PacmanFrame(int width, int height) {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(new Dimension(width, height));
//        this.getContentPane().setBackground(Color.BLACK);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("PACMAN");
        this.setVisible(true);

    }
}
