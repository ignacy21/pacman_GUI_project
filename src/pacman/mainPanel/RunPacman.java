package pacman.mainPanel;

import pacman.RunGame;
import pacman.ghosts.Ghost;
import pacman.mainPanel.gameData.GameData;
import pacman.playerControl.Pacman;
import pacman.tiles.Tile;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RunPacman implements Runnable {

    private final List<List<Tile>> boardFromFile;

    private PacmanFrame pacmanFrame;
    private Pacman pacman;
    private PacmanPanel pacmanPanel;
    private GamePanel gamePanel;
    private final JLayeredPane layeredPane;
    private final JLabel gameOverLabel;
    private boolean isGameContinue = true;
    private final int lives;
    private final String board;
    private double pacmanSpeed;
    private double ghostSpeed;
    private final GameService gameService = new GameService();
    private final int[] ghostRespawnPoint;
    private final int[] pacmanRespawnPoint;
    private int score;
    public RunPacman(String board, int lives, int score) {
        this.lives = lives;
        this.board = board;
        this.score = score;

        GameData gameData = gameService.runGameBasedOnBoard(board);
        boardFromFile = gameData.getBoard();
        pacmanSpeed = gameData.getPacmanSpeed();
        ghostSpeed = gameData.getGhostSpeed();
//        pacmanSpeed = 0;
//        ghostSpeed = 0;
        int widthInTiles = gameData.getWidthInTiles();
        int heightInTiles = gameData.getHeightInTiles();
        int TILE_SIZE = gameData.getTileSize();
        int rowThatSwitchSide = gameData.getRowThatSwitchSide();
        ghostRespawnPoint = gameData.getGhostRespawnPoint();
        pacmanRespawnPoint = gameData.getPacmanRespawnPoint();

        int pacmanPanelWidth = TILE_SIZE * widthInTiles;
        int pacmanPanelHeight = TILE_SIZE * heightInTiles;

        int displayHeight = TILE_SIZE * 2;

        int screenWidth = TILE_SIZE * (widthInTiles - 4);
        int screenHeight = pacmanPanelHeight + displayHeight + 30;


        createStructureOfPacman(
                screenWidth,
                screenHeight,
                TILE_SIZE,
                rowThatSwitchSide,
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
        pacmanPanel.setSCORE(score);

        run();
    }

    @Override
    public void run() {
        new Thread(() -> {
            while (isGameContinue) {
                isGameContinue = gamePanel.startGame();
                try {
                    Thread.sleep(16);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            if (lives > 1) {
                score = pacmanPanel.getSCORE();
                continueGameWithMinusOneHeart(lives, score);
            } else {
                endGame(score);
            }
        }).start();
    }

    private void continueGameWithMinusOneHeart(int lives, int score) {
        pacmanPanel.getEnemies().forEach(Ghost::stopThread);
        pacmanFrame.dispose();
        int finalLives = --lives;
        List<List<Tile>> updatedBoard = pacmanPanel.getBoard();
        String mapPath = gameService.rewriteBoard(updatedBoard, board);
        SwingUtilities.invokeLater(() -> new RunPacman(mapPath, finalLives, score));
    }

    private void endGame(int score) {
        SwingUtilities.invokeLater(this::showGameOver);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        String playerName;

        do {
            playerName = JOptionPane.showInputDialog(pacmanFrame, "Enter your name:", "Game Over", JOptionPane.PLAIN_MESSAGE);
        } while (playerName == null || playerName.trim().isEmpty());

        gameService.writeScoreToLeaderBoard(playerName.trim(), score);
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
                ghostSpeed,
                rowThatSwitchSides,
                ghostRespawnPoint
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
                pacmanRespawnPoint[0],
                pacmanRespawnPoint[1],
                pacmanSpeed,
                TILE_SIZE,
                boardFromFile,
                rowThatSwitchSides
        );
        return pacman;
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
