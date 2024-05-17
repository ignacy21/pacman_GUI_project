package pacman.mainPanel;

import pacman.ghosts.Ghost;
import pacman.playerControl.Direction;
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
    private final Pacman pacman;
    private List<List<Tile>> board;
    private final TileManager tileManager = new TileManager();
    private final CollisionService collisionService;
    private final PointCounterService pointCounterService = new PointCounterService(this);
    private final List<Ghost> enemies;
    private int enemiesSpeed;

    public PacmanPanel(Pacman pacman, int tileSeize, List<List<Tile>> board, int enemiesSpeed) {
        this.enemiesSpeed = enemiesSpeed;
        this.board = board;
        TILE_SIZE = tileSeize;
        this.pacman = pacman;
        collisionService = new CollisionService(pacman, board);
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(pacman);
        this.setFocusable(true);
        Ghost blinky = new Ghost(
                TILE_SIZE * (board.getFirst().size() - 2),
                TILE_SIZE,
                Direction.LEFT,
                enemiesSpeed,
                TILE_SIZE,
                board,
                "blinky"
        );
        Ghost pinky = new Ghost(
                TILE_SIZE,
                TILE_SIZE,
                Direction.RIGHT,
                enemiesSpeed,
                TILE_SIZE,
                board,
                "pinky"
        );
        Ghost clyde = new Ghost(
                TILE_SIZE,
                TILE_SIZE * (board.size() - 2),
                Direction.RIGHT,
                enemiesSpeed,
                TILE_SIZE,
                board,
                "clyde"
        );
        Ghost inky = new Ghost(
                TILE_SIZE * (board.getFirst().size() - 2),
                TILE_SIZE * (board.size() - 2),
                Direction.LEFT,
                enemiesSpeed,
                TILE_SIZE,
                board,
                "inky"
        );
        enemies = List.of(clyde, blinky, inky, pinky);
    }

    public void updatePacman() {
        pacman.update();
        enemies.forEach(Ghost::update);
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        drawTiles(g2);
        checkCollisions();
        drawPacman(g2);

        enemies.forEach(ghost -> {
                    ghost.setWhereToGo(new int[]{pacman.getCoordinateX(), pacman.getCoordinateY()});
                    ghost.drawEntity(g2);
                }
        );
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
        tileManager.paintTiles(g2, board);
    }

    private void checkCollisions() {
        collisionService.checkCollision();
        List<List<Tile>> lists = pointCounterService.collectPoints(pacman, board);
        this.board = lists;
    }

    private void drawPacman(Graphics2D g2) {
        pacman.drawEntity(g2);
    }

}
