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

    public PacmanPanel(Pacman pacman, int tileSeize, List<List<Tile>> board, int enemiesSpeed, int rowThatSwitchSides) {
        this.enemiesSpeed = enemiesSpeed;
        this.board = board;
        TILE_SIZE = tileSeize;
        this.pacman = pacman;
        pacmanService = new PacmanService(pacman, board, rowThatSwitchSides);
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(pacman);
        this.setFocusable(true);
        int[] pacmanCoordinate = {pacman.getCoordinateX(), pacman.getCoordinateY()};
        Ghost blinky = new Ghost(
                TILE_SIZE * (board.getFirst().size() - 2),
                TILE_SIZE,
                Direction.LEFT,
                enemiesSpeed,
                TILE_SIZE,
                board,
                pacmanCoordinate,
                new int[]{TILE_SIZE * (board.getFirst().size() - 2), TILE_SIZE},
                "blinky",
                rowThatSwitchSides
        );
        Ghost pinky = new Ghost(
                TILE_SIZE,
                TILE_SIZE,
                Direction.RIGHT,
                enemiesSpeed,
                TILE_SIZE,
                board,
                pacmanCoordinate,
                new int[]{TILE_SIZE, TILE_SIZE},
                "pinky",
                rowThatSwitchSides
        );
        Ghost clyde = new Ghost(
                TILE_SIZE,
                TILE_SIZE * (board.size() - 2),
                Direction.RIGHT,
                enemiesSpeed,
                TILE_SIZE,
                board,
                pacmanCoordinate,
                new int[]{TILE_SIZE, TILE_SIZE * (board.size() - 2)},
                "clyde",
                rowThatSwitchSides
        );
        Ghost inky = new Ghost(
                TILE_SIZE * (board.getFirst().size() - 2),
                TILE_SIZE * (board.size() - 2),
                Direction.LEFT,
                enemiesSpeed,
                TILE_SIZE,
                board,
                pacmanCoordinate,
                new int[]{TILE_SIZE * (board.getFirst().size() - 2), TILE_SIZE * (board.size() - 2)},
                "inky",
                rowThatSwitchSides
        );

        clyde.setGhostMode(CHASE);
        blinky.setGhostMode(CHASE);
        inky.setGhostMode(CHASE);
        pinky.setGhostMode(CHASE);
        enemies.add(clyde);
        enemies.add(blinky);
        enemies.add(inky);
        enemies.add(pinky);
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

}
