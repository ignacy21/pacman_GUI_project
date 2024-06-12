package pacman.mainPanel.gameData;

import pacman.tiles.Tile;

import java.util.List;

public class GameData {

    private final List<List<Tile>> board;
    private final int widthInTiles;
    private final int heightInTiles;
    private final int TILE_SIZE;
    private final int pacmanSpeed;
    private final int ghostSpeed;
    private final int rowThatSwitchSide;
    private final int[] ghostRespawnPoint;
    private final int[] pacmanRespawnPoint;

    public GameData(
            List<List<Tile>> board,
            int widthInTiles,
            int heightInTiles,
            int TILE_SIZE,
            int pacmanSpeed,
            int ghostSpeed,
            int rowThatSwitchSide,
            int[] ghostRespawnPoint,
            int[] pacmanRespawnPoint
    ) {
        this.board = board;
        this.widthInTiles = widthInTiles;
        this.heightInTiles = heightInTiles;
        this.TILE_SIZE = TILE_SIZE;
        this.pacmanSpeed = pacmanSpeed;
        this.ghostSpeed = ghostSpeed;
        this.rowThatSwitchSide = rowThatSwitchSide;
        this.ghostRespawnPoint = ghostRespawnPoint;
        this.pacmanRespawnPoint = pacmanRespawnPoint;
    }

    public List<List<Tile>> getBoard() {
        return board;
    }

    public int getWidthInTiles() {
        return widthInTiles;
    }

    public int getHeightInTiles() {
        return heightInTiles;
    }

    public int getTileSize() {
        return TILE_SIZE;
    }

    public int getPacmanSpeed() {
        return pacmanSpeed;
    }

    public int getGhostSpeed() {
        return ghostSpeed;
    }

    public int getRowThatSwitchSide() {
        return rowThatSwitchSide;
    }

    public int[] getGhostRespawnPoint() {
        return ghostRespawnPoint;
    }

    public int[] getPacmanRespawnPoint() {
        return pacmanRespawnPoint;
    }
}
