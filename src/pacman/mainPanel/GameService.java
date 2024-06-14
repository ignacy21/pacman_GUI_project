package pacman.mainPanel;

import pacman.mainPanel.gameData.GameData;
import pacman.mainPanel.gameData.GameDataBuilder;
import pacman.tiles.BoardService;
import pacman.tiles.Tile;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class GameService {

    private final BoardService boardService = new BoardService();


    public GameData runGameBasedOnBoard(String boardPath) {
        List<List<Tile>> board = createBoard(boardPath);
        int width = board.getFirst().size();
        int height = board.size();
        int tileSize = calculateTileSize(width, height);
        double pacmanSpeed = 3;
        double ghostSpeed = 2;

        int[] pacmanRespawn;
        int[] ghostRespawn;
        int rowThatSwitchSides;
        int maximumPoints;

        GameDataBuilder gameDataBuilder = new GameDataBuilder()
                .withBoard(board)
                .withWidthInTiles(width)
                .withHeightInTiles(height)
                .withTileSize(tileSize)
                .withPacmanSpeed(pacmanSpeed)
                .withGhostSpeed(ghostSpeed);

            if (boardPath.contains("board1.txt")) {
                rowThatSwitchSides = 10;
                pacmanRespawn = new int[]{tileSize * 13, tileSize * 10};
                ghostRespawn = new int[]{tileSize * 29, tileSize * 11};
                maximumPoints = 43800;
            } else if (boardPath.contains("board2.txt")) {
                rowThatSwitchSides = 14;
                pacmanRespawn = new int[]{tileSize * 15, tileSize * 23};
                ghostRespawn = new int[]{tileSize * 15, tileSize * 15};
                maximumPoints = 33600;
            } else if (boardPath.contains("board3.txt")) {
                rowThatSwitchSides = 12;
                pacmanRespawn = new int[]{tileSize * 9, tileSize * 12};
                ghostRespawn = new int[]{tileSize * 44, tileSize * 7};
                maximumPoints = 44100;
            } else if (boardPath.contains("board4.txt")) {
                rowThatSwitchSides = 15;
                pacmanRespawn = new int[]{tileSize * 12, tileSize * 14};
                ghostRespawn = new int[]{tileSize * 44, tileSize * 12};
                maximumPoints = 55300;
            } else if (boardPath.contains("board5.txt")) {
                rowThatSwitchSides = 14;
                pacmanRespawn = new int[]{tileSize * 11, tileSize * 14};
                ghostRespawn = new int[]{tileSize * 22, tileSize * 9};
                maximumPoints = 42200;
            } else {
                throw new RuntimeException("There is no such file as: " + boardPath);
            }

        gameDataBuilder
                .withRowThatSwitchSize(rowThatSwitchSides)
                .withPacmanRespawnPoint(pacmanRespawn)
                .withGhostRespawnPoint(ghostRespawn)
                .withMaximumPoints(maximumPoints);

        return gameDataBuilder.build();
    }

    public String rewriteBoard(List<List<Tile>> board, String boardName) {
        return boardService.writeCurrentBoard(board, boardName);
    }

    private int calculateTileSize(int widthInTiles, int heightInTiles) {
        int height = Toolkit.getDefaultToolkit().getScreenSize().height - 80;
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int tileSize = (width / (widthInTiles - 4));
        int tileSize1 = ((height) / (heightInTiles + 2));
        if (tileSize1 < tileSize)
            tileSize = tileSize1;

        return tileSize;
    }

    private List<List<Tile>> createBoard(String filePath) {
        List<List<Tile>> boardFromFile;
        try {
            boardFromFile = boardService.createBoardFromFile(String.format("src/pacman/tiles/boards/%s", filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return boardFromFile;
    }

    public void writeScoreToLeaderBoard(String playersName, int score) {
        File file = new File( "src/pacman/wyniki.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(String.valueOf(score));
            writer.write(";");
            writer.write(playersName);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("An error occurred while writing the score to the leaderboard.");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
