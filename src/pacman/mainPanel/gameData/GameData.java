package pacman.mainPanel.gameData;

import pacman.tiles.Tile;

import java.util.List;

public class GameData {

    private final List<List<Tile>> board;
    private final int width;
    private final int height;
    private final int widthInTiles;
    private final int heightInTiles;
    private final int TILE_SIZE;
    private final int pacmanSpeed;
    private final int ghostSpeed;
    private final int rowThatSwitchSize;
    private final int[] ghostRespawnPoint;
    private final int[] pacmanRespawnPoint;

    public GameData(
            List<List<Tile>> board,
            int width,
            int height,
            int widthInTiles,
            int heightInTiles,
            int TILE_SIZE,
            int pacmanSpeed,
            int ghostSpeed,
            int rowThatSwitchSize,
            int[] ghostRespawnPoint,
            int[] pacmanRespawnPoint
    ) {
        this.board = board;
        this.width = width;
        this.height = height;
        this.widthInTiles = widthInTiles;
        this.heightInTiles = heightInTiles;
        this.TILE_SIZE = TILE_SIZE;
        this.pacmanSpeed = pacmanSpeed;
        this.ghostSpeed = ghostSpeed;
        this.rowThatSwitchSize = rowThatSwitchSize;
        this.ghostRespawnPoint = ghostRespawnPoint;
        this.pacmanRespawnPoint = pacmanRespawnPoint;
    }

    public List<List<Tile>> getBoard() {
        return board;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getWidthInTiles() {
        return widthInTiles;
    }

    public int getHeightInTiles() {
        return heightInTiles;
    }

    public int getTILE_SIZE() {
        return TILE_SIZE;
    }

    public int getPacmanSpeed() {
        return pacmanSpeed;
    }

    public int getGhostSpeed() {
        return ghostSpeed;
    }

    public int getRowThatSwitchSize() {
        return rowThatSwitchSize;
    }

    public int[] getGhostRespawnPoint() {
        return ghostRespawnPoint;
    }

    public int[] getPacmanRespawnPoint() {
        return pacmanRespawnPoint;
    }
}
