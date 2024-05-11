package pacman.mainPanel;

import pacman.playerControl.Pacman;
import pacman.tiles.Tile;
import pacman.tiles.TileManager;
import pacman.tiles.collision.CollisionService;
import pacman.tiles.point.PointCounterService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GamePanel extends JPanel implements Runnable {

    public static int TILE_SIZE;
    private final Pacman pacman;
    private final List<List<Tile>> board;
    private final TileManager tileManager = new TileManager();

    private final Thread thread = new Thread(this);

    private final CollisionService collisionService = new CollisionService(this);
    private final PointCounterService pointCounterService = new PointCounterService(this);


    public GamePanel(Pacman pacman, int tileSeize, List<List<Tile>> board) {
        this.board = board;
        TILE_SIZE = tileSeize;
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

//            System.out.println(pacman);
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

        tileManager.drawTile(g2, board);
        collisionService.checkCollision(pacman);
        pointCounterService.collectPoints(pacman);
        pacman.drawPackman(g2);
    }

    public List<List<Tile>> getBoard() {
        return board;
    }

    public Pacman getPacman() {
        return pacman;
    }
}
