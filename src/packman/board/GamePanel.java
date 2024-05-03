package packman.board;

import packman.playerControl.Pacman;
import packman.tiles.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    public static int TILE_SIZE;
    private final Pacman pacman;
    private final int width;
    private final int height;
    private final TileManager tileManager = new TileManager(this);

    private final Thread thread = new Thread(this);

    public GamePanel(Pacman pacman, int width, int height, int tileSeize) {
        TILE_SIZE = tileSeize;
        this.width = width;
        this.height = height;

        thread.start();
        this.pacman = pacman;
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

        tileManager.drawTile(g2, width, height);
        pacman.drawPackman(g2);
    }
}
