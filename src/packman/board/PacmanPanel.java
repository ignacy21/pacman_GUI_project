package packman.board;

import packman.playerControl.Pacman;

import javax.swing.*;
import java.awt.*;

public class PacmanPanel extends JPanel implements Runnable {

    private Pacman pacman = new Pacman(0, 0, 15);

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
        int pacmanX = pacman.getPlayerX();
        int pacmanY = pacman.getPlayerY();
        int pacmanSpeed = pacman.getPlayerSpeed();
        switch (pacman.getPlayerDirection()) {
            case UP -> pacman.setYPosition(pacmanY - pacmanSpeed);
            case DOWN -> pacman.setYPosition(pacmanY + pacmanSpeed);
            case LEFT -> pacman.setXPosition(pacmanX - pacmanSpeed);
            case RIGHT -> pacman.setXPosition(pacmanX + pacmanSpeed);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.RED);
        g2.fillRect(pacman.getPlayerX(), pacman.getPlayerY(), 45, 45);

    }
}
