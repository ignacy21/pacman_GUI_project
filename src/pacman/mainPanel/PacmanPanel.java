package pacman.mainPanel;

import pacman.ghosts.Ghost;
import pacman.playerControl.Direction;
import pacman.playerControl.Pacman;
import pacman.tiles.Tile;
import pacman.tiles.TileManager;
import pacman.tiles.collision.PacmanService;
import pacman.tiles.point.PointCounterService;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static pacman.ghosts.GhostMode.SCATTER;

public class PacmanPanel extends JPanel {

    public static int TILE_SIZE;
    private int SCORE = 0;
    private final Pacman pacman;
    private List<List<Tile>> board;
    private final TileManager tileManager = new TileManager();
    private final PacmanService pacmanService;

    private final PointCounterService pointCounterService;
    private List<Ghost> enemies = new ArrayList<>();
    private double enemiesSpeed;
    private final int rowThatSwitchSides;

    public PacmanPanel(
            int width,
            int height,
            Pacman pacman,
            int tileSeize,
            List<List<Tile>> board,
            double enemiesSpeed,
            int rowThatSwitchSides,
            int[] respawnPoint
    ) {
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(pacman);
        this.setFocusable(true);
        this.setPreferredSize(new Dimension(width, height));

        this.rowThatSwitchSides = rowThatSwitchSides;
        this.enemiesSpeed = enemiesSpeed;
        this.board = board;
        this.pacman = pacman;

        TILE_SIZE = tileSeize;

        this.pacmanService = new PacmanService(pacman, board, rowThatSwitchSides);

        int[] pacmanCoordinate = {pacman.getCoordinateX(), pacman.getCoordinateY()};

        enemies = createGhosts(board, pacmanCoordinate, respawnPoint);
        pointCounterService = new PointCounterService(this);

        revalidate();
        repaint();
    }

    private List<Ghost> createGhosts(
            List<List<Tile>> board,
            int[] pacmanCoordinate,
            int[] respawnPoint
    ) {
        Ghost blinky = crateGhost(
                "blinky",
                respawnPoint,
                pacmanCoordinate,
                new int[]{TILE_SIZE * (board.getFirst().size() - 3), 0}
        );
        Ghost pinky = crateGhost(
                "pinky",
                respawnPoint,
                pacmanCoordinate,
                new int[]{TILE_SIZE * 3, TILE_SIZE}
        );
        Ghost clyde = crateGhost(
                "clyde",
                respawnPoint,
                pacmanCoordinate,
                new int[]{TILE_SIZE * 3, TILE_SIZE * (board.size() - 2)}
        );
        Ghost inky = crateGhost(
                "inky",
                respawnPoint,
                pacmanCoordinate,
                new int[]{TILE_SIZE * (board.getFirst().size() - 4), TILE_SIZE * (board.size() - 2)}
        );
        return List.of(clyde, blinky, inky, pinky);
    }

    private Ghost crateGhost(String name, int[] respawnPoint, int[] pacmanCoordinate, int[] corner) {
        Ghost ghost = new Ghost(
                respawnPoint[0],
                respawnPoint[1],
                Direction.LEFT,
                enemiesSpeed,
                TILE_SIZE,
                board,
                pacmanCoordinate,
                corner,
                respawnPoint,
                name,
                rowThatSwitchSides
        );
        ghost.setGhostMode(SCATTER);
        return ghost;
    }

    public void updatePacmanAndGhosts() {
        pacman.update();
        enemies.forEach(Ghost::update);
    }

    public JViewport returnJPanelWithViewPoint(int correctPositionOfPanelToMatchScreen) {
        JViewport viewport = new JViewport();
        viewport.setView(this);
        viewport.setViewPosition(new Point(correctPositionOfPanelToMatchScreen, 0));
        return viewport;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        drawTiles(g2);
        checkCollisions();
        drawPacman(g2);

        enemies.forEach(ghost -> {
                    ghost.setPacmanCoordinate(new int[]{pacman.getCoordinateX(), pacman.getCoordinateY()});
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
        pacmanService.allowPacmanToChangeSides();
        pacmanService.checkCollision();
        this.board = pointCounterService.collectPoints(pacman, board);
    }

    private void drawPacman(Graphics2D g2) {
        pacman.drawEntity(g2);
    }

    public List<Ghost> getEnemies() {
        return enemies;
    }

    public List<List<Tile>> getBoard() {
        return board;
    }
}
