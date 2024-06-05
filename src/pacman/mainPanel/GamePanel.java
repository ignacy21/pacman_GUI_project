package pacman.mainPanel;

import pacman.tiles.collision.PacmanAndGhostCollision;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class GamePanel extends JPanel {
//    private final Thread thread = new Thread(this);
    private final PacmanPanel pacmanPanel;
    private final PacmanAndGhostCollision pacmanAndGhostCollision;

    private final JLabel scoreLabel;

    public GamePanel(PacmanPanel pacmanPanel, int displayHeight, int correctPositionOfPanelToMatchScreen) {
//        thread.start();
        this.pacmanPanel = pacmanPanel;
        pacmanAndGhostCollision = new PacmanAndGhostCollision(pacmanPanel.getPacman(), pacmanPanel.getEnemies());

        JViewport jViewport = pacmanPanel.returnJPanelWithViewPoint(correctPositionOfPanelToMatchScreen);


        this.setLayout(new BorderLayout());
        this.add(jViewport, BorderLayout.CENTER);

        scoreLabel = new JLabel("0", SwingConstants.CENTER);

        JPanel displayPanel = displayPanelCreation(displayHeight);
        this.add(displayPanel, BorderLayout.NORTH);
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
        JPanel displayPanel = new JPanel();
        displayPanel.setLayout(new GridLayout(2, 1));
        displayPanel.setBackground(Color.BLACK);
        displayPanel.setPreferredSize(new Dimension(getWidth(), displayHeight));
        Border border = BorderFactory.createLineBorder(Color.BLUE, 4);
        displayPanel.setBorder(border);

        Font pacFont = new Font("Pac-Font", Font.BOLD, displayHeight / 2);

        JLabel scoreText = new JLabel("SCORE:");
        scoreText.setHorizontalAlignment(SwingConstants.CENTER);
        scoreText.setFont(pacFont);
        scoreText.setForeground(Color.WHITE);

        scoreLabel.setFont(pacFont);
        scoreLabel.setForeground(Color.WHITE);

        displayPanel.add(scoreText, BorderLayout.CENTER);
        displayPanel.add(scoreLabel, BorderLayout.SOUTH);
        return displayPanel;
    }
}
