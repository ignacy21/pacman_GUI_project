package pacman.tiles.point;

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

    public List<List<Tile>> collectPoints(Player player) {
        Tile voidTile = new Tile("void", null);
        List<List<Tile>> board = new ArrayList<>(pacmanPanel.getBoard());
        int halfOfTile = TILE_SIZE / 2;

        int playersLeft = player.getCoordinateX();
        int playersBottom = player.getCoordinateY() + TILE_SIZE;

        int playersCurrentX = (playersLeft + halfOfTile) / 45;
        int playersCurrentY = (playersBottom - halfOfTile) / 45;
        List<Tile> row = new ArrayList<>(board.get(playersCurrentY));
        Tile tile = row.get(playersCurrentX);

        if (tile.getName().contains("point")) {
            row.set(playersCurrentX, voidTile);
            board.set(playersCurrentY, row);
            if ("point1".equals(tile.getName())) {
                pacmanPanel.setSCORE(pacmanPanel.getSCORE() + 100);
            } else if ("point2".equals(tile.getName())) {
                pacmanPanel.setSCORE(pacmanPanel.getSCORE() + 500);
            }
        }
        return board;
    }
}
