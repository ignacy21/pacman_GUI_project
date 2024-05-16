package pacman;

import pacman.mainPanel.PacmanPanel;
import pacman.mainPanel.GamePanel;
import pacman.mainPanel.PacmanFrame;
import pacman.playerControl.Pacman;
import pacman.tiles.BoardService;
import pacman.tiles.Tile;

import javax.swing.*;
import java.io.IOException;
import java.util.List;

public class RunPacman {

    public static void main(String[] args) throws IOException {
        BoardService boardService = new BoardService();
//        List<List<Tile>> boardFromFile = boardService.createBoardFromFile("src/pacman/tiles/boards/board1.txt");
        List<List<Tile>> boardFromFile = boardService.createBoardFromFile("src/pacman/tiles/boards/board2.txt");
        int tilesHeight = boardFromFile.size();
        int tilesWidth = boardFromFile.getFirst().size();
        SwingUtilities.invokeLater(() -> {
            int TILE_SIZE = 27;
            int width = TILE_SIZE * tilesWidth;
            int height = TILE_SIZE * tilesHeight;



            int displayHeight = TILE_SIZE * 2;

            Pacman pacman = new Pacman(TILE_SIZE, TILE_SIZE, 6, TILE_SIZE, boardFromFile);
            PacmanFrame pacmanFrame = new PacmanFrame(width, height + 30 + displayHeight);

            PacmanPanel pacmanPanel = new PacmanPanel(
                    pacman,
                    TILE_SIZE,
                    boardFromFile,
                    displayHeight,
                    width,
                    height
            );
            GamePanel gamePanel = new GamePanel(pacmanPanel, displayHeight);
            pacmanFrame.add(gamePanel);

        });
    }
}
