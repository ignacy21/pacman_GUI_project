package pacman.mainPanel;

import pacman.playerControl.Pacman;
import pacman.tiles.Tile;
import pacman.tiles.TileManager;
import pacman.tiles.collision.CollisionService;
import pacman.tiles.point.PointCounterService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PacmanPanel extends JPanel {

    public static int TILE_SIZE;
    private int SCORE = 0;
    private final int displayHeight = 0;
    private final Pacman pacman;
    private List<List<Tile>> board;
    private final TileManager tileManager = new TileManager();
    private final CollisionService collisionService;
    private final PointCounterService pointCounterService = new PointCounterService(this);

    public PacmanPanel(Pacman pacman, int tileSeize, List<List<Tile>> board, int displayHeight, int width, int height) {
        this.board = board;
        TILE_SIZE = tileSeize;
        this.pacman = pacman;
        collisionService = new CollisionService(pacman, board);
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(pacman);
        this.setFocusable(true);
    }

    public void updatePacman() {
        pacman.update();
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        drawTiles(g2);
        checkCollisions();
        drawPacman(g2);
    }

    public void setSCORE(int SCORE) {
        this.SCORE = SCORE;
    }

    public int getSCORE() {
        return SCORE;
    }

    public Pacman getPacman() {
        return pacman;
    }

    private void drawTiles(Graphics2D g2) {
        tileManager.paintTiles(g2, board, displayHeight);
    }

    private void checkCollisions() {
        collisionService.checkCollision();
        List<List<Tile>> lists = pointCounterService.collectPoints(pacman, board);
        this.board = lists;
    }

    private void drawPacman(Graphics2D g2) {
        pacman.drawEntity(g2, displayHeight);
    }

}
