package packman.board;

import packman.playerControl.Pacman;

import javax.swing.*;
import java.awt.*;

public class PacmanPanel extends JPanel implements Runnable {

    private Pacman pacman = new Pacman(300, 300, 5);

    private final Thread thread = new Thread(this);

    public PacmanPanel() {
        thread.start();

        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(pacman);
        this.setFocusable(true);
    }

    @Override
    public void run() {
        while (thread != null) {

            update();
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            repaint();
        }
    }

    public void update() {
        pacman.update();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        pacman.repaint(g2);
    }
}
