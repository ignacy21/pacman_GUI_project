package pacman.tiles.point;

import pacman.ghosts.Ghost;
import pacman.mainPanel.PacmanPanel;
import pacman.playerControl.Player;
import pacman.tiles.Tile;

import java.util.ArrayList;
import java.util.List;

import static pacman.mainPanel.PacmanPanel.TILE_SIZE;

public class PointCounterService {

    private final PacmanPanel pacmanPanel;

    public PointCounterService(PacmanPanel pacmanPanel) {
        this.pacmanPanel = pacmanPanel;
    }

    public List<List<Tile>> collectPoints(Player player, List<List<Tile>> board) {
        Tile voidTile = new Tile("void", null);
        int halfOfTile = TILE_SIZE / 2;

        int playersLeft = player.getCoordinateX();
        int playersBottom = player.getCoordinateY() + TILE_SIZE;

        int playersCurrentX = (playersLeft + halfOfTile) / TILE_SIZE;
        int playersCurrentY = (playersBottom - halfOfTile) / TILE_SIZE;
        List<Tile> row = new ArrayList<>(board.get(playersCurrentY));
        Tile tile = row.get(playersCurrentX);

        if (tile.getName().contains("point")) {
            voidTile.setColumnNumber(tile.getColumnNumber());
            voidTile.setRowNumber(tile.getRowNumber());
            row.set(playersCurrentX, voidTile);
            board.set(playersCurrentY, row);
            if ("point1".equals(tile.getName())) {
                pacmanPanel.setSCORE(pacmanPanel.getSCORE() + 100);
            } else if ("point2".equals(tile.getName())) {
                pacmanPanel.setSCORE(pacmanPanel.getSCORE() + 500);
                pacmanPanel.getEnemies().forEach(Ghost::changeToRunMode);
            }
        }
        return board;
    }
}
