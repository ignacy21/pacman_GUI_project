package pacman;

import pacman.ghosts.Ghost;
import pacman.mainPanel.GamePanel;
import pacman.mainPanel.PacmanFrame;
import pacman.mainPanel.PacmanPanel;
import pacman.playerControl.Pacman;
import pacman.tiles.BoardService;
import pacman.tiles.Tile;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class RunPacman extends Thread {

    private List<List<Tile>> boardFromFile;

    private PacmanFrame pacmanFrame;
    private Pacman pacman;
    private PacmanPanel pacmanPanel;
    private GamePanel gamePanel;
    private final JLayeredPane layeredPane;
    private final JLabel gameOverLabel;
    private boolean isGameContinue = true;
    private final int lives;
    private final String board;

    final BoardService boardService = new BoardService();


    public RunPacman(String board, int lives) {
        this.lives = lives;
        this.board = board;
        createBoard(board);
        int tilesHeight = boardFromFile.size();
        int tilesWidth = boardFromFile.getFirst().size();

        int TILE_SIZE = 25;

        int pacmanPanelWidth = TILE_SIZE * tilesWidth;
        int pacmanPanelHeight = TILE_SIZE * tilesHeight;

        int displayHeight = TILE_SIZE * 2;

        int screenWidth = TILE_SIZE * (tilesWidth - 4);
        int screenHeight = pacmanPanelHeight + displayHeight + 30;

        int rowThatSwitchSides = 14;

        createStructureOfPacman(
                screenWidth,
                screenHeight,
                TILE_SIZE,
                rowThatSwitchSides,
                pacmanPanelWidth,
                pacmanPanelHeight,
                displayHeight
        );
        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(pacmanFrame.getSize());

        gamePanel.setBounds(0, 0, pacmanFrame.getWidth(), pacmanFrame.getHeight());
        layeredPane.add(gamePanel, JLayeredPane.DEFAULT_LAYER);

        gameOverLabel = createGameOverLabel();
        layeredPane.add(gameOverLabel, JLayeredPane.PALETTE_LAYER);

        pacmanFrame.add(layeredPane, BorderLayout.CENTER);
        pacmanFrame.revalidate();
        pacmanFrame.repaint();
    }

    @Override
    public void run() {
        while (isGameContinue) {
            isGameContinue = gamePanel.startGame();
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        if (lives > 1) {
            continueGameWithMinusOneHeart(lives);
        } else {
            endGame();
        }
    }

    private void continueGameWithMinusOneHeart(int lives) {
        pacmanFrame.dispose();
        int finalLives = --lives;
        SwingUtilities.invokeLater(() -> new RunPacman(board, finalLives));
    }

    private void endGame() {
        SwingUtilities.invokeLater(this::showGameOver);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        pacmanFrame.dispose();
        SwingUtilities.invokeLater(RunGame::new);
    }

    private void showGameOver() {
        gameOverLabel.setVisible(true);
        layeredPane.revalidate();
        layeredPane.repaint();
    }

    private void createStructureOfPacman(
            int screenWidth,
            int screenHeight,
            int TILE_SIZE,
            int rowThatSwitchSides,
            int pacmanPanelWidth,
            int pacmanPanelHeight,
            int displayHeight
    ) {
        pacmanFrame = pacmanFrameCreation(screenWidth, screenHeight);
        pacman = pacmanCreation(TILE_SIZE, rowThatSwitchSides);
        pacmanPanel = pacmanPanelCreation(pacmanPanelWidth, pacmanPanelHeight, TILE_SIZE, rowThatSwitchSides);
        gamePanel = gamePanelCreation(displayHeight, TILE_SIZE);
    }

    private GamePanel gamePanelCreation(int displayHeight, int TILE_SIZE) {
        final GamePanel gamePanel;
        gamePanel = new GamePanel(
                pacmanPanel,
                displayHeight,
                TILE_SIZE * 2,
                lives
        );
        return gamePanel;
    }

    private PacmanPanel pacmanPanelCreation(int pacmanPanelWidth, int pacmanPanelHeight, int TILE_SIZE, int rowThatSwitchSides) {
        final PacmanPanel pacmanPanel;
        pacmanPanel = new PacmanPanel(
                pacmanPanelWidth,
                pacmanPanelHeight,
                pacman,
                TILE_SIZE,
                boardFromFile,
                boardService,
                2,
                rowThatSwitchSides
        );
        return pacmanPanel;
    }

    private PacmanFrame pacmanFrameCreation(int screenWidth, int screenHeight) {
        final PacmanFrame pacmanFrame;
        pacmanFrame = new PacmanFrame(screenWidth, screenHeight);
        return pacmanFrame;
    }

    private Pacman pacmanCreation(int TILE_SIZE, int rowThatSwitchSides) {
        final Pacman pacman;
        pacman = new Pacman(
                TILE_SIZE * 11,
                TILE_SIZE * 17,
                3,
                TILE_SIZE,
                boardFromFile,
                rowThatSwitchSides
        );
        return pacman;
    }

    private void createBoard(String filePath) {
        try {
            boardFromFile = boardService.createBoardFromFile(String.format("src/pacman/tiles/boards/%s", filePath));
//            boardFromFile = boardService.createBoardFromFile("src/pacman/tiles/boards/board1_1.txt");
//            boardFromFile = boardService.createBoardFromFile("src/pacman/tiles/boards/board1.txt");
//            boardFromFile = boardService.createBoardFromFile("src/pacman/tiles/boards/board2.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private JLabel createGameOverLabel() {
        JLabel gameOverLabel = new JLabel("GAME OVER", SwingConstants.CENTER);
        gameOverLabel.setForeground(Color.RED);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 30));
        gameOverLabel.setBounds(0, 0, pacmanFrame.getWidth(), pacmanFrame.getHeight());
        gameOverLabel.setVisible(false);
        return gameOverLabel;
    }


}
