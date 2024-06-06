package pacman.mainPanel;

import pacman.tiles.collision.PacmanAndGhostCollision;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GamePanel extends JPanel {
    private final PacmanPanel pacmanPanel;
    private final PacmanAndGhostCollision pacmanAndGhostCollision;
    private final JLabel scoreLabel;
    private int lives;

    public GamePanel(PacmanPanel pacmanPanel, int displayHeight, int correctPositionOfPanelToMatchScreen, int lives) {
        this.lives = lives;
        this.pacmanPanel = pacmanPanel;
        pacmanAndGhostCollision = new PacmanAndGhostCollision(pacmanPanel.getPacman(), pacmanPanel.getEnemies());

        JViewport jViewport = pacmanPanel.returnJPanelWithViewPoint(correctPositionOfPanelToMatchScreen);

        this.setLayout(new BorderLayout());
        this.add(jViewport, BorderLayout.CENTER);

        scoreLabel = new JLabel("0", SwingConstants.CENTER);

        JPanel displayPanel = displayPanelCreation(displayHeight);
        this.add(displayPanel, BorderLayout.NORTH);
        revalidate();
        repaint();
    }

    public boolean startGame() {

        pacmanPanel.updatePacmanAndGhosts();
        if (pacmanAndGhostCollision.isGameOver()) {
            return false;
        }
        try {
            Thread.sleep(16);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        scoreLabel.setText(String.valueOf(pacmanPanel.getSCORE()));
        pacmanPanel.steerGhostMode();
        repaint();
        return true;
    }

    private JPanel displayPanelCreation(int displayHeight) {
        JPanel mainDisplayPanel = createRightPanel();
        mainDisplayPanel.setLayout(new GridLayout(1, 3));
        mainDisplayPanel.setPreferredSize(new Dimension(getWidth(), displayHeight));
        Border border = BorderFactory.createLineBorder(Color.BLUE, 4);
        mainDisplayPanel.setBorder(border);

        JPanel heartsPanel = createLivesPanel(displayHeight, lives);
        JPanel scoreDisplayPanel = createScorePanel(displayHeight);
        JPanel rightPanel = createRightPanel();

        mainDisplayPanel.add(heartsPanel);
        mainDisplayPanel.add(scoreDisplayPanel);
        mainDisplayPanel.add(rightPanel);

        return mainDisplayPanel;
    }

    private static JPanel createRightPanel() {
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.BLACK);
        return rightPanel;
    }

    private static JPanel createLivesPanel(int displayHeight, int lives) {
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

    private JPanel createScorePanel(int displayHeight) {
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
        return scoreDisplayPanel;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getLives() {
        return lives;
    }
}
