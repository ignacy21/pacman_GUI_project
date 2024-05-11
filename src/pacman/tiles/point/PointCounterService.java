package pacman.tiles.point;

import pacman.mainPanel.GamePanel;
import pacman.playerControl.Direction;
import pacman.playerControl.Pacman;
import pacman.playerControl.Player;
import pacman.tiles.Tile;

import java.util.List;

import static pacman.mainPanel.GamePanel.TILE_SIZE;

public class PointCounterService {

    private final GamePanel gamePanel;

    public PointCounterService(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void collectPoints(Player player) {
        Direction direction = player.getDirection();
        List<List<Tile>> board = gamePanel.getBoard();
        Pacman pacman = gamePanel.getPacman();
        int speed = pacman.getSpeed();
        int halfOfTile = TILE_SIZE / 2;

        int playersUp = player.getCoordinateY();
        int playersLeft = player.getCoordinateX();
        int playersRight = player.getCoordinateX() + TILE_SIZE;
        int playersBottom = player.getCoordinateY() + TILE_SIZE;

        int playersCurrentX = (playersLeft + halfOfTile) / 45;
        int playersCurrentY = (playersBottom - halfOfTile) / 45;
        Tile tile = board.get(playersCurrentY).get(playersCurrentX);

        if ("point".equals(tile.getName())) {
            System.out.println("YEYEYEYE");
        }
    }
}
