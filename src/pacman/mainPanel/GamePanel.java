package pacman.mainPanel;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    private final Thread thread = new Thread(this);

    private final PacmanPanel pacmanPanel;

    public GamePanel(PacmanPanel pacmanPanel) {
        this.pacmanPanel = pacmanPanel;
        thread.start();

        setLayout(new BorderLayout());
        add(pacmanPanel, BorderLayout.CENTER);
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
            repaint();
        }

    }
}
