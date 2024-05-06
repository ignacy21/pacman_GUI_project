package pacman;

import pacman.mainPanel.GamePanel;
import pacman.mainPanel.PacmanFrame;
import pacman.playerControl.Pacman;
import pacman.tiles.BoardService;

import javax.swing.*;
import java.io.IOException;
import java.util.List;

public class RunPacman {

    public static void main(String[] args) throws IOException {
        BoardService boardService = new BoardService();
        List<List<Integer>> boardFromFile = boardService.createBoardFromFile("src/pacman/tiles/boards/board1.txt");
        int tilesHeight = boardFromFile.size();
        int tilesWidth = boardFromFile.getFirst().size();
        SwingUtilities.invokeLater(() -> {
            int TILE_SIZE = 45;
            int width = TILE_SIZE * tilesWidth;
            int height = TILE_SIZE * tilesHeight;

            Pacman pacman = new Pacman(300, 300, 5);

            System.out.println(width);
            System.out.println(height);
            PacmanFrame pacmanFrame = new PacmanFrame(width, height + 30);
            GamePanel gamePanel = new GamePanel(
                    pacman,
                    TILE_SIZE,
                    "src/pacman/tiles/boards/board1.txt");
            pacmanFrame.add(gamePanel);

        });
    }
}
