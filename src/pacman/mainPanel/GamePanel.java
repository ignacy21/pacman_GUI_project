package pacman.mainPanel;

import pacman.tiles.BoardService;
import pacman.tiles.Tile;
import pacman.tiles.collision.PacmanAndGhostCollision;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;


public class GamePanel extends JPanel {
    private final PacmanPanel pacmanPanel;
    private final PacmanAndGhostCollision pacmanAndGhostCollision;
    private final JLabel scoreLabel;
    private int lives;
    private boolean nextLevel;
    private int levelNumber;
    private int time;
    private boolean playTime = true;
    private boolean leaveGame;
    JLabel timeLabel;
    private final int maximumPoints;
    private Thread superpowerThread;

    public GamePanel(PacmanPanel pacmanPanel, int displayHeight, int correctPositionOfPanelToMatchScreen, int lives, int levelNumber, int time, int maximumPoints) {
        this.maximumPoints = maximumPoints;
        this.leaveGame = false;
        this.time = time;
        this.lives = lives;
        this.levelNumber = levelNumber;
        this.pacmanPanel = pacmanPanel;
        pacmanAndGhostCollision = new PacmanAndGhostCollision(pacmanPanel.getPacman(), pacmanPanel.getEnemies());

        JViewport jViewport = pacmanPanel.returnJPanelWithViewPoint(correctPositionOfPanelToMatchScreen);

        this.setLayout(new BorderLayout());
        this.add(jViewport, BorderLayout.CENTER);

        scoreLabel = new JLabel("0", SwingConstants.CENTER);

        JPanel displayPanel = displayPanelCreation(displayHeight);
        this.add(displayPanel, BorderLayout.NORTH);
        startTime();
        SwingUtilities.invokeLater(pacmanPanel::requestFocusInWindow);
        startSuperpowerThread();

        revalidate();
        repaint();
    }

    public boolean startGame() {
        if (pacmanPanel.getSCORE() == maximumPoints * levelNumber) {
            scoreLabel.setText(String.valueOf(pacmanPanel.getSCORE()));
            System.out.println("NEXT LEVEL");
            nextLevel = true;
            levelNumber++;
            return false;
        }
        nextLevel = false;
        pacmanPanel.updatePacmanAndGhosts();
        if (pacmanAndGhostCollision.isGameOver()) {
            scoreLabel.setText(String.valueOf(pacmanPanel.getSCORE()));
            playTime = false;
            return false;
        }
        scoreLabel.setText(String.valueOf(pacmanPanel.getSCORE()));
        repaint();
        return true;
    }

    public void startSuperpowerThread() {
        superpowerThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Random random = new Random();
                int i = random.nextInt(1, 100);
                if (i % 2 == 0) {
                    List<List<Tile>> board = pacmanPanel.getBoard();
                    Tile tile = null;
                    while (tile == null) {
                        int randomY = random.nextInt(1, board.size() - 1);
                        int randomX = random.nextInt(2, board.getFirst().size() - 2);
                        tile = board.get(randomY).get(randomX);
                        if (!"void".equals(tile.getName())) {
                            tile = null;
                        } else {
                            Tile tileToReplace = pacmanPanel.getBoard().get(randomY).get(randomX);
                            BoardService boardService = new BoardService();
                            int whichBoost = random.nextInt(1, 5);
                            Tile boost;
                            switch (whichBoost) {
                                case 1 -> boost = boardService.getNameTileMap().get(10);
                                case 2 -> boost = boardService.getNameTileMap().get(11);
                                case 3 -> boost = boardService.getNameTileMap().get(12);
                                case 4 -> boost = boardService.getNameTileMap().get(13);
                                default -> throw new RuntimeException("No boost :(");
                            }
                            boost.setColumnNumber(tileToReplace.getColumnNumber());
                            boost.setRowNumber(tileToReplace.getRowNumber());
                            pacmanPanel.getBoard().get(randomY).set(randomX, boost);
                        }
                    }

                }

            }
        });
        superpowerThread.start();
    }

    private JPanel displayPanelCreation(int displayHeight) {
        JPanel mainDisplayPanel = new JPanel();
        mainDisplayPanel.setBackground(Color.BLACK);
        mainDisplayPanel.setLayout(new GridLayout(1, 3));
        mainDisplayPanel.setPreferredSize(new Dimension(getWidth(), displayHeight));
        Border border = BorderFactory.createLineBorder(Color.BLUE, 4);
        mainDisplayPanel.setBorder(border);

        JPanel heartsPanel = createLivesPanel(displayHeight, lives);
        JPanel centralPanel = createCentralPanel(displayHeight);
        JPanel rightPanel = createTimerPanel(displayHeight);

        mainDisplayPanel.add(heartsPanel);
        mainDisplayPanel.add(centralPanel);
        mainDisplayPanel.add(rightPanel);

        return mainDisplayPanel;
    }

    private JPanel createTimerPanel(int displayHeight) {
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(2, 1));
        rightPanel.setBackground(Color.BLACK);

        JPanel timerPanel = new JPanel();
        timerPanel.setBackground(Color.BLACK);
        Font pacFont = new Font("Pac-Font", Font.BOLD, displayHeight / 3);
        timeLabel = new JLabel("Time: " + formatTime(time++));
        timeLabel.setFont(pacFont);
        timeLabel.setForeground(Color.WHITE);
        timerPanel.add(timeLabel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);

        JButton restartButton = new JButton("MENU");
        restartButton.setFont(new Font("Pac-Font", Font.BOLD, displayHeight / 4));
        restartButton.setForeground(Color.WHITE);
        restartButton.setBackground(Color.BLACK);
        restartButton.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
        restartButton.setFocusPainted(false);

        restartButton.addActionListener(e -> leaveGame = true);

        buttonPanel.add(restartButton);

        rightPanel.add(timerPanel);
        rightPanel.add(buttonPanel);
        return rightPanel;
    }

    private JPanel createLivesPanel(int displayHeight, int lives) {
        BufferedImage pacmanImage;
        try {
            pacmanImage = ImageIO.read(new File("resources/images/pacman/right/pacman_1.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int newWidth = displayHeight / 2;
        int newHeight = displayHeight / 2;
        BufferedImage scaledBufferedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = scaledBufferedImage.createGraphics();
        g2d.drawImage(pacmanImage, 0, 0, newWidth, newHeight, null);
        g2d.dispose();

        JPanel heartsPanel = new JPanel();
        heartsPanel.setLayout(new BoxLayout(heartsPanel, BoxLayout.X_AXIS));
        heartsPanel.setOpaque(false);

        for (int i = 0; i < lives; i++) {
            JLabel heartLabel = new JLabel(new ImageIcon(scaledBufferedImage));
            heartsPanel.add(heartLabel);
            if (i < 2) {
                heartsPanel.add(Box.createRigidArea(new Dimension(20, 0)));
            }
        }
        return heartsPanel;
    }

    private JPanel createCentralPanel(int displayHeight) {
        JPanel centralPanel = new JPanel();
        centralPanel.setLayout(new GridLayout(1, 2));
        centralPanel.setBackground(Color.BLACK);

        JPanel scoreDisplayPanel = new JPanel();
        scoreDisplayPanel.setLayout(new GridLayout(2, 1));
        scoreDisplayPanel.setBackground(Color.BLACK);
        Font pacFont = new Font("Pac-Font", Font.BOLD, displayHeight / 2);

        JLabel scoreText = new JLabel("SCORE:");
        scoreText.setHorizontalAlignment(SwingConstants.CENTER);
        scoreText.setFont(pacFont);
        scoreText.setForeground(Color.WHITE);

        scoreLabel.setFont(pacFont);
        scoreLabel.setForeground(Color.WHITE);
        scoreDisplayPanel.add(scoreText);
        scoreDisplayPanel.add(scoreLabel);

        JPanel levelDisplayPanel = new JPanel();
        levelDisplayPanel.setBackground(Color.BLACK);
        JLabel level = new JLabel("Level: " + levelNumber);
        level.setFont(pacFont);
        level.setForeground(Color.WHITE);
        levelDisplayPanel.add(level);

        centralPanel.add(levelDisplayPanel);
        centralPanel.add(scoreDisplayPanel);
        return centralPanel;
    }

    private void startTime() {
        Thread timerThread = new Thread(() -> {
            try {
                while (playTime) {
                    Thread.sleep(1000);
                    SwingUtilities.invokeLater(() -> timeLabel.setText("Time: " + formatTime(time++)));
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        timerThread.start();
    }

    private String formatTime(int seconds) {
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int secs = seconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, secs);
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public boolean isNextLevel() {
        return nextLevel;
    }

    public int getTime() {
        return time;
    }

    public boolean isLeaveGame() {
        return leaveGame;
    }
}
