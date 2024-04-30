package packman;

import packman.board.PacmanFrame;
import packman.board.PacmanPanel;
import packman.playerControl.Pacman;

import javax.swing.*;

public class RunPacman {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PacmanFrame pacmanFrame = new PacmanFrame();
            PacmanPanel panel = new PacmanPanel(new Pacman(300, 300, 7));
            pacmanFrame.add(panel);

        });
    }
}
