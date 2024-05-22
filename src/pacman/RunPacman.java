package pacman;

import pacman.mainPanel.GamePanel;
import pacman.mainPanel.PacmanFrame;
import pacman.mainPanel.PacmanPanel;
import pacman.playerControl.Pacman;
import pacman.tiles.BoardService;
import pacman.tiles.Tile;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class RunPacman {

    public static void main(String[] args) throws IOException {
        BoardService boardService = new BoardService();
//        List<List<Tile>> boardFromFile = boardService.createBoardFromFile("src/pacman/tiles/boards/board1_1.txt");
//        List<List<Tile>> boardFromFile = boardService.createBoardFromFile("src/pacman/tiles/boards/board1.txt");
//        List<List<Tile>> boardFromFile = boardService.createBoardFromFile("src/pacman/tiles/boards/board2.txt");
        List<List<Tile>> boardFromFile = boardService.createBoardFromFile("src/pacman/tiles/boards/board2_2.txt");
        int tilesHeight = boardFromFile.size();
        int tilesWidth = boardFromFile.getFirst().size();
        SwingUtilities.invokeLater(() -> {
            int TILE_SIZE = 25;
            int frameWidth = TILE_SIZE * (tilesWidth - 4) ;
            int frameHeight = TILE_SIZE * tilesHeight;
            int rowThatSwitchSides = 14;

            int gamePanelWidth = TILE_SIZE * tilesWidth;


            int displayHeight = TILE_SIZE * 2;

            Pacman pacman = new Pacman(TILE_SIZE * 12, TILE_SIZE * 17, 3, TILE_SIZE, boardFromFile, rowThatSwitchSides);
//            Pacman pacman = new Pacman(TILE_SIZE, TILE_SIZE, 3, TILE_SIZE, boardFromFile, rowThatSwitchSides);
            PacmanFrame pacmanFrame = new PacmanFrame(frameWidth, frameHeight + 30 + displayHeight);

            PacmanPanel pacmanPanel = new PacmanPanel(
                    pacman,
                    TILE_SIZE,
                    boardFromFile,
                    2,
                    rowThatSwitchSides
            );
            GamePanel gamePanel = new GamePanel(pacmanPanel, displayHeight, gamePanelWidth, frameHeight + 30);

            pacmanFrame.setLayout(new BorderLayout());

            pacmanFrame.add(gamePanel);

        });
    }
}
