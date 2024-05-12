package pacman.tiles.point;

import pacman.mainPanel.GamePanel;
import pacman.playerControl.Direction;
import pacman.playerControl.Pacman;
import pacman.playerControl.Player;
import pacman.tiles.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static pacman.mainPanel.GamePanel.TILE_SIZE;

public class PointCounterService {

    private final GamePanel gamePanel;

    public PointCounterService(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public List<List<Tile>> collectPoints(Player player) {
        Tile voidTile = new Tile("void", null);
        List<List<Tile>> board = new ArrayList<>(gamePanel.getBoard());
        int halfOfTile = TILE_SIZE / 2;

        int playersLeft = player.getCoordinateX();
        int playersBottom = player.getCoordinateY() + TILE_SIZE;

        int playersCurrentX = (playersLeft + halfOfTile) / 45;
        int playersCurrentY = (playersBottom - halfOfTile) / 45;
        List<Tile> row = new ArrayList<>(board.get(playersCurrentY));
        Tile tile = row.get(playersCurrentX);

        if (tile.getName().contains("point")) {
            System.out.println("YEYEYEYE");
            row.set(playersCurrentX, voidTile);
            board.set(playersCurrentY, row);
            if ("point1".equals(tile.getName())) {
                gamePanel.setSCORE(gamePanel.getSCORE() + 100);
            } else if ("point2".equals(tile.getName())) {
                gamePanel.setSCORE(gamePanel.getSCORE() + 500);
            }
        }
        return board;
    }
}
