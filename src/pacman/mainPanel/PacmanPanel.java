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

import static pacman.ghosts.GhostMode.CHASE;
import static pacman.ghosts.GhostMode.SCATTER;

public class PacmanPanel extends JPanel {

    public static int TILE_SIZE;
    private int SCORE = 0;
    private final Pacman pacman;
    private List<List<Tile>> board;
    private final TileManager tileManager = new TileManager();
    private final PacmanService pacmanService;

    private final PointCounterService pointCounterService = new PointCounterService(this);
    private final List<Ghost> enemies = new ArrayList<>();
    private int enemiesSpeed;
    private int ghostModeCounter = 0;
    private final int width;
    private final int height;

    public PacmanPanel(int width, int height, Pacman pacman, int tileSeize, List<List<Tile>> board, int enemiesSpeed, int rowThatSwitchSides) {
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(pacman);
        this.setFocusable(true);
        this.setPreferredSize(new Dimension(width, height));

        this.enemiesSpeed = enemiesSpeed;
        this.board = board;
        this.pacman = pacman;
        this.height = height;
        this.width = width;

        TILE_SIZE = tileSeize;

        this.pacmanService = new PacmanService(pacman, board, rowThatSwitchSides);

        int[] pacmanCoordinate = {pacman.getCoordinateX(), pacman.getCoordinateY()};
        int[] respawnPoint = new int[]{TILE_SIZE * (board.getFirst().size() / 2), TILE_SIZE * (board.size() / 2)};

        Ghost blinky = new Ghost(
                TILE_SIZE * (board.getFirst().size() - 4),
                TILE_SIZE,
                Direction.LEFT,
                enemiesSpeed,
                TILE_SIZE,
                board,
                pacmanCoordinate,
                new int[]{TILE_SIZE * (board.getFirst().size() - 3), 0},
                respawnPoint,
                "blinky",
                rowThatSwitchSides
        );
        Ghost pinky = new Ghost(
                TILE_SIZE * 3,
                TILE_SIZE,
                Direction.RIGHT,
                enemiesSpeed,
                TILE_SIZE,
                board,
                pacmanCoordinate,
                new int[]{TILE_SIZE * 2, 0},
                respawnPoint,
                "pinky",
                rowThatSwitchSides
        );
        Ghost clyde = new Ghost(
                TILE_SIZE * 3,
                TILE_SIZE * (board.size() - 2),
                Direction.RIGHT,
                enemiesSpeed,
                TILE_SIZE,
                board,
                pacmanCoordinate,
                new int[]{TILE_SIZE * 2, TILE_SIZE * (board.size() - 1)},
                respawnPoint,
                "clyde",
                rowThatSwitchSides
        );
        Ghost inky = new Ghost(
                TILE_SIZE * (board.getFirst().size() - 4),
                TILE_SIZE * (board.size() - 2),
                Direction.LEFT,
                enemiesSpeed,
                TILE_SIZE,
                board,
                pacmanCoordinate,
                new int[]{TILE_SIZE * (board.getFirst().size() - 3), TILE_SIZE * (board.size() - 1)},
                respawnPoint,
                "inky",
                rowThatSwitchSides
        );


        enemies.add(clyde);
        enemies.add(blinky);
        enemies.add(inky);
        enemies.add(pinky);
        enemies.forEach(ghost -> ghost.setGhostMode(CHASE));
    }

    public void steerGhostMode() {
        if (ghostModeCounter == 0) {
            enemies.forEach(ghost -> ghost.setGhostMode(CHASE));
            System.out.println("CHASE");
        } else if (ghostModeCounter == 1000) {
            enemies.forEach(ghost -> ghost.setGhostMode(SCATTER));
            System.out.println("SCATTER");
        } else if (ghostModeCounter == 1500) {
            ghostModeCounter = -1;
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
}
