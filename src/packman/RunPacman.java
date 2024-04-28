package packman;

import packman.board.PacmanFrame;
import packman.board.PacmanPanel;

import javax.swing.*;

public class RunPacman {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PacmanFrame pacmanFrame = new PacmanFrame();
            PacmanPanel panel = new PacmanPanel();
            pacmanFrame.add(panel);

        });
    }
}
