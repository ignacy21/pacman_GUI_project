package pacman;

import pacman.mainPanel.PacmanFrame;

import javax.swing.*;
import java.awt.*;

public class RunGame {

    private JFrame frame;
    private JButton newGameButton;
    private JButton highScoresButton;
    private JButton exitButton;

    public RunGame() {
        this.frame = new PacmanFrame(500, 400);
        this.newGameButton = new JButton("New Game");
        this.highScoresButton = new JButton("High Scores");
        this.exitButton = new JButton("Exit");

        newGameButton.addActionListener(e -> startGame());
        highScoresButton.addActionListener(e -> showHighScores());
        exitButton.addActionListener(e -> exitGame());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(newGameButton);
        buttonPanel.add(highScoresButton);
        buttonPanel.add(exitButton);

        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    private void startGame() {
        newGameButton.setEnabled(false);
        frame.dispose();
        SwingUtilities.invokeLater(() -> new RunPacman("board2_2.txt", 3));
    }

    private void showHighScores() {
        frame.dispose();
        SwingUtilities.invokeLater(() -> {
            HighScoresFrame highScoresFrame = new HighScoresFrame();
            highScoresFrame.setVisible(true);
        });
    }

    private void exitGame() {
        frame.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RunGame::new);
    }
}
