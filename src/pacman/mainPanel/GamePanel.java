package pacman.mainPanel;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    private final Thread thread = new Thread(this);
    private final JLabel scoreLabel;
    private final PacmanPanel pacmanPanel;

    public GamePanel(PacmanPanel pacmanPanel, int displayHeight) {
        this.pacmanPanel = pacmanPanel;
        thread.start();

        setLayout(new BorderLayout());

        JPanel displayPanel = new JPanel();
        displayPanel.setLayout(new GridLayout(2, 1));
        displayPanel.setBackground(Color.BLUE);
        displayPanel.setPreferredSize(new Dimension(getWidth(), displayHeight));

        JLabel scoreText = new JLabel("SCORE:");
        scoreText.setBackground(Color.BLUE);
        scoreText.setHorizontalAlignment(SwingConstants.CENTER);

        scoreLabel = new JLabel("0", SwingConstants.CENTER);
        scoreLabel.setBackground(Color.BLUE);

        displayPanel.add(scoreText, BorderLayout.CENTER);
        displayPanel.add(scoreLabel, BorderLayout.SOUTH);

        this.add(displayPanel, BorderLayout.NORTH);
        this.add(pacmanPanel, BorderLayout.CENTER);
    }

    @Override
    public void run() {
        while (thread != null) {


            pacmanPanel.updatePacman();
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            scoreLabel.setText(String.valueOf(pacmanPanel.getSCORE()));
            repaint();
        }
    }
}
