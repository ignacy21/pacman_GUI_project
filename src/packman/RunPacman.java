package packman;

import packman.board.PacmanFrame;
import packman.board.GamePanel;
import packman.playerControl.Pacman;

import javax.swing.*;

public class RunPacman {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            int TILE_SIZE = 45;
            int width = TILE_SIZE * 32;
            int height = TILE_SIZE * 16;
            Pacman pacman = new Pacman(300, 300, 1);

            PacmanFrame pacmanFrame = new PacmanFrame(width, height);
            GamePanel gamePanel = new GamePanel(pacman, width, height, TILE_SIZE);
            pacmanFrame.add(gamePanel);

        });
    }
}
