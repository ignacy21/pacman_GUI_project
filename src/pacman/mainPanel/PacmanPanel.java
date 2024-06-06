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

import static pacman.ghosts.GhostMode.*;

public class PacmanPanel extends JPanel {

    public static int TILE_SIZE;
    private int SCORE = 0;
    private final Pacman pacman;
    private List<List<Tile>> board;
    private final TileManager tileManager = new TileManager();
    private final PacmanService pacmanService;

    private final PointCounterService pointCounterService = new PointCounterService(this);
    private List<Ghost> enemies = new ArrayList<>();
    private int enemiesSpeed;
    private int ghostModeCounter = 499;
    private final int rowThatSwitchSides;
    private final int[] respawnPoint;
    private boolean ghostHunt = false;
    private final int width;
    private final int height;

    public PacmanPanel(int width, int height, Pacman pacman, int tileSeize, List<List<Tile>> board, int enemiesSpeed, int rowThatSwitchSides) {
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(pacman);
        this.setFocusable(true);
        this.setPreferredSize(new Dimension(width, height));

        this.rowThatSwitchSides = rowThatSwitchSides;
        this.enemiesSpeed = enemiesSpeed;
        this.board = board;
        this.pacman = pacman;
        this.height = height;
        this.width = width;

        TILE_SIZE = tileSeize;

        this.pacmanService = new PacmanService(pacman, board, rowThatSwitchSides);

        int[] pacmanCoordinate = {pacman.getCoordinateX(), pacman.getCoordinateY()};
        this.respawnPoint = new int[]{TILE_SIZE * (board.getFirst().size() / 2), TILE_SIZE * (board.size() / 2)};

        enemies = createGhosts(board, pacmanCoordinate, respawnPoint);
        enemies.forEach(ghost -> ghost.setGhostMode(CHASE));
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
        Ghost blinky = new Ghost(
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
        return blinky;
    }

    public void steerGhostMode() {
        if (ghostHunt) {
            enemies.forEach(ghost -> ghost.setGhostMode(RUN));
            ghostModeCounter = -1;
            ghostHunt = false;
        } else {
            if (ghostModeCounter == 500) {
                enemies.forEach(ghost -> ghost.setGhostMode(CHASE));
                System.out.println("CHASE");
            } else if (ghostModeCounter == 1500) {
                enemies.forEach(ghost -> ghost.setGhostMode(SCATTER));
                System.out.println("SCATTER");
            } else if (ghostModeCounter == 2000) {
                ghostModeCounter = 499;
            }
        }
        ghostModeCounter++;
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

    public void setGhostHunt(boolean ghostHunt) {
        this.ghostHunt = ghostHunt;
    }
}
