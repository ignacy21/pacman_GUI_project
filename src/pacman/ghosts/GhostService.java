package pacman.ghosts;

import pacman.playerControl.Direction;
import pacman.playerControl.Pacman;
import pacman.tiles.Tile;

import java.util.*;
import java.util.stream.Stream;

import static pacman.ghosts.GhostMode.CHASE;
import static pacman.mainPanel.PacmanPanel.TILE_SIZE;
import static pacman.playerControl.Direction.*;

public class GhostService {

    private final Ghost ghost;
    private final List<List<Tile>> board;

    public GhostService(Ghost ghost, List<List<Tile>> board) {
        this.ghost = ghost;
        this.board = board;
    }

    public void whereGhostShouldTurn() {
        int halfOfTile = TILE_SIZE / 2;

        Direction direction = ghost.getDirection();
        int pacmanX = ghost.getPacmanCoordinate()[0];
        int pacmanY = ghost.getPacmanCoordinate()[1];


        int ghostLeft = ghost.getCoordinateX();
        int ghostBottom = ghost.getCoordinateY() + TILE_SIZE;

        int gap = ghost.getSpeed() - 1;
        if (ghostLeft % TILE_SIZE <= gap && ghostBottom % TILE_SIZE <= gap) {
            int gapX = ghostLeft % TILE_SIZE;
            int gapY = ghostBottom % TILE_SIZE;

            int ghostCurrentTileX = (ghostLeft + halfOfTile) / TILE_SIZE;
            int ghostCurrentTileY = (ghostBottom - halfOfTile) / TILE_SIZE;

            Random random = new Random();
            Tile[] tiles = tilesAroundGivenTile(ghostCurrentTileX, ghostCurrentTileY);
            Map<Integer, Tile> map = new HashMap<>();
//            map.put(0, tiles[0]);   // upper tile
//            map.put(1, tiles[1]);   // lower tile
//            map.put(2, tiles[2]);   // right tile
//            map.put(3, tiles[3]);   // left tile

            List<Integer> tileOptionsToTurnNum = new ArrayList<>();
            for (int i = 0; i < tiles.length; i++) {
                if (!tiles[i].isCollision()) {
                    tileOptionsToTurnNum.add(i);
                    map.put(i, tiles[i]);
                } else {
                    tiles[i] = null;
                }
            }

            switch (direction) {
                case UP -> map.remove(1);
                case DOWN -> map.remove(0);
                case LEFT -> map.remove(2);
                case RIGHT -> map.remove(3);
            }

            int directionByNumber = 0;
            if (ghost.getGhostMode() == CHASE && map.size() > 1) {
                double distanceBetweenPacmanAndTile = Integer.MAX_VALUE;
                for (Map.Entry<Integer, Tile> integerTileEntry : map.entrySet()) {
                    Tile tile = integerTileEntry.getValue();
                    int tileX = tile.getColumnNumber() * TILE_SIZE;
                    int tileY = tile.getRowNumber() * TILE_SIZE + TILE_SIZE;
                    double v = distanceBetweenTwoPoints(tileX, tileY, pacmanX, pacmanY);
                    if (v <= distanceBetweenPacmanAndTile) {
                        distanceBetweenPacmanAndTile = v;
                        directionByNumber = integerTileEntry.getKey();
                        System.out.println(ghostCurrentTileX);
                        System.out.println(ghostCurrentTileY);
                    }
                }

            } else {
                Integer next = map.keySet().iterator().next();
                directionByNumber = next;
            }

            switch (directionByNumber) {
                case 0 -> {
                    correctXCoordinate(gapX, halfOfTile, ghost);
                    ghost.setDirection(UP);
                }
                case 1 -> {
                    correctXCoordinate(gapX, halfOfTile, ghost);
                    ghost.setDirection(DOWN);
                }
                case 2 -> {
                    correctYCoordinate(gapY, halfOfTile, ghost);
                    ghost.setDirection(RIGHT);
                }
                case 3 -> {
                    correctYCoordinate(gapY, halfOfTile, ghost);
                    ghost.setDirection(LEFT);
                }
            }
        }
    }

    private static void correctYCoordinate(int mod, int halfOfTile, Ghost ghost) {
        if (mod < halfOfTile) {
            ghost.setYPosition(ghost.getCoordinateY() - mod);
        } else {
            ghost.setYPosition(ghost.getCoordinateY() + TILE_SIZE - mod);
        }
    }

    private static void correctXCoordinate(int mod, int halfOfTile, Ghost ghost) {
        if (mod < halfOfTile) {
            ghost.setXPosition(ghost.getCoordinateX() - mod);
        } else {
            ghost.setXPosition(ghost.getCoordinateX() + TILE_SIZE - mod);
        }
    }

    private static double distanceBetweenTwoPoints(int x1, int y1, int x2, int y2) {
        int diffX = x1 - x2;
        int diffY = y1 - y2;
        return Math.sqrt(Math.pow(diffX, 2) + Math.pow(diffY, 2));
    }

    private Tile[] tilesAroundGivenTile(int tileX, int tileY) {

        List<Tile> row = new ArrayList<>(board.get(tileY));
        Tile tileUp = board.get(tileY - 1).get(tileX);
        Tile tileDown = board.get(tileY + 1).get(tileX);
        Tile rightTile = row.get(tileX + 1);
        Tile leftTile = row.get(tileX - 1);
        return new Tile[]{tileUp, tileDown, rightTile, leftTile};
    }
}
