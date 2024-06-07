package pacman;

import pacman.mainPanel.PacmanFrame;

import javax.swing.*;
import java.awt.*;

public class RunGame {

    private JFrame frame;
    private JButton newGameButton;


    public RunGame() {
        this.frame = new PacmanFrame(500, 400);
        this.newGameButton = new JButton("New Game");
        newGameButton.addActionListener(e -> startGame());

        frame.add(newGameButton, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    private void startGame() {
        newGameButton.setEnabled(false);
        frame.dispose();
        SwingUtilities.invokeLater(() -> new RunPacman("board2_2.txt", 3).start());
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(RunGame::new);
    }


}
