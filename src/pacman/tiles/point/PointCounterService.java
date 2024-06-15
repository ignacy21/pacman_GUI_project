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
        Tile voidTile = new Tile("void", null, false);
        int halfOfTile = TILE_SIZE / 2;

        int playersLeft = player.getCoordinateX();
        int playersBottom = player.getCoordinateY() + TILE_SIZE;

        int playersCurrentX = (playersLeft + halfOfTile) / TILE_SIZE;
        int playersCurrentY = (playersBottom - halfOfTile) / TILE_SIZE;
        List<Tile> row = new ArrayList<>(board.get(playersCurrentY));
        Tile tile = row.get(playersCurrentX);

        if (tile.isPoint()) {
            voidTile.setColumnNumber(tile.getColumnNumber());
            voidTile.setRowNumber(tile.getRowNumber());
            row.set(playersCurrentX, voidTile);
            board.set(playersCurrentY, row);
            if ("point1".equals(tile.getName())) {
                pacmanPanel.setSCORE(pacmanPanel.getSCORE() + 100);
            } else if ("point2".equals(tile.getName())) {
                pacmanPanel.setSCORE(pacmanPanel.getSCORE() + 500);
                pacmanPanel.getEnemies().forEach(Ghost::changeToRunMode);
            } else if ("strawberry".equals(tile.getName())) {
                pacmanPanel.getPacman().setSpeed(pacmanPanel.getPacman().getSpeed() + 1);
            } else if ("cherry".equals(tile.getName())) {
                pacmanPanel.getEnemies().forEach(g -> {
                    if (g.getGhostChaseModeTime() > 5000)
                        g.setGhostChaseModeTime(g.getGhostChaseModeTime() - 1000);
                });
            } else if ("banana".equals(tile.getName())) {
                pacmanPanel.getEnemies().forEach(g -> {
                    if (g.getGhostRunModeTime() < 10000)
                        g.setGhostRunModeTime(g.getGhostRunModeTime() + 1000);
                });
            } else if ("pear".equals(tile.getName())) {
                pacmanPanel.getEnemies().forEach(g -> {
                    if (g.getSpeed() >= 3)
                        g.setSpeed(g.getSpeed() - 1);
                });
            }
        }
        return board;
    }
}
