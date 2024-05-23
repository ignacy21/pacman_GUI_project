package pacman.ghosts;

import pacman.playerControl.Direction;
import pacman.tiles.Tile;

import java.util.*;

import static pacman.ghosts.GhostMode.*;
import static pacman.mainPanel.PacmanPanel.TILE_SIZE;
import static pacman.playerControl.Direction.*;

public class GhostService {

    private final Ghost ghost;
    private final int rowThatSwitchSides;
    private final List<List<Tile>> board;

    public GhostService(Ghost ghost, List<List<Tile>> board, int rowThatSwitchSides) {
        this.ghost = ghost;
        this.rowThatSwitchSides = rowThatSwitchSides;
        this.board = board;
    }

    public void whereGhostShouldTurn() {
        int halfOfTile = TILE_SIZE / 2;

        Direction direction = ghost.getDirection();

        int ghostLeft = ghost.getCoordinateX();
        int ghostBottom = ghost.getCoordinateY() + TILE_SIZE;

        int gap = ghost.getSpeed() - 1;
        if (ghostLeft % TILE_SIZE <= gap && ghostBottom % TILE_SIZE <= gap) {
            int gapX = ghostLeft % TILE_SIZE;
            int gapY = ghostBottom % TILE_SIZE;

            int ghostCurrentTileX = (ghostLeft + halfOfTile) / TILE_SIZE;
            int ghostCurrentTileY = (ghostBottom - halfOfTile) / TILE_SIZE;

            Tile[] tiles = tilesAroundGivenTile(ghostCurrentTileX, ghostCurrentTileY);
            Map<Integer, Tile> map = new HashMap<>();

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
            if (map.size() > 1) {
                GhostMode ghostMode = ghost.getGhostMode();
                if (ghostMode == CHASE) {
                    int pacmanX = ghost.getPacmanCoordinate()[0];
                    int pacmanY = ghost.getPacmanCoordinate()[1];

                    directionByNumber = getDirectionBasedOnDestinationCoordinate(map, pacmanX, pacmanY, directionByNumber);
                } else if (ghostMode == SCATTER) {
                    int cornerX = ghost.getCornerCoordinate()[0];
                    int cornerY = ghost.getCornerCoordinate()[1];

                    directionByNumber = getDirectionBasedOnDestinationCoordinate(map, cornerX, cornerY, directionByNumber);
                } else if (ghostMode == RUN) {
                    int pacmanX = ghost.getPacmanCoordinate()[0];
                    int pacmanY = ghost.getPacmanCoordinate()[1];
                    double distanceBetweenPacmanAndTile = Integer.MAX_VALUE;
                    for (Map.Entry<Integer, Tile> integerTileEntry : map.entrySet()) {
                        Tile tile = integerTileEntry.getValue();
                        int tileX = tile.getColumnNumber() * TILE_SIZE;
                        int tileY = tile.getRowNumber() * TILE_SIZE + TILE_SIZE;
                        double v = distanceBetweenTwoPoints(tileX, tileY, pacmanX, pacmanY);
                        if (v >= distanceBetweenPacmanAndTile) {
                            distanceBetweenPacmanAndTile = v;
                            directionByNumber = integerTileEntry.getKey();
                        }
                    }
                } else if (ghostMode == RESPAWN) {
                    int respawnPointX = ghost.getRespawnPoint()[0];
                    int respawnPointY = ghost.getRespawnPoint()[1];
                    directionByNumber = getDirectionBasedOnDestinationCoordinate(
                            map, respawnPointX, respawnPointY, directionByNumber
                    );
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

    private static int getDirectionBasedOnDestinationCoordinate(
            Map<Integer, Tile> map,
            int pacmanX,
            int pacmanY,
            int directionByNumber
    ) {
        double distanceBetweenPacmanAndTile = Integer.MAX_VALUE;
        for (Map.Entry<Integer, Tile> integerTileEntry : map.entrySet()) {
            Tile tile = integerTileEntry.getValue();
            int tileX = tile.getColumnNumber() * TILE_SIZE;
            int tileY = tile.getRowNumber() * TILE_SIZE + TILE_SIZE;
            double v = distanceBetweenTwoPoints(tileX, tileY, pacmanX, pacmanY);
            if (v <= distanceBetweenPacmanAndTile) {
                distanceBetweenPacmanAndTile = v;
                directionByNumber = integerTileEntry.getKey();
            }
        }
        return directionByNumber;
    }

    public void allowGhostToChangeSides() {
        int tileY = ghost.getCoordinateY() / TILE_SIZE;
        if (tileY == rowThatSwitchSides) {
            int ghostX = ghost.getCoordinateX();
            if (ghostX <= TILE_SIZE) {
                ghost.setDirection(LEFT);
                ghost.setCoordinateX(board.getFirst().size() * TILE_SIZE - TILE_SIZE * 2);
            } else if (ghostX >= board.getFirst().size() * TILE_SIZE - TILE_SIZE * 2) {
                ghost.setDirection(RIGHT);
                ghost.setCoordinateX(TILE_SIZE * 2);
            }
        }
    }

    private static void correctYCoordinate(int mod, int halfOfTile, Ghost ghost) {
        if (mod < halfOfTile) {
            ghost.setCoordinateY(ghost.getCoordinateY() - mod);
        } else {
            ghost.setCoordinateY(ghost.getCoordinateY() + TILE_SIZE - mod);
        }
    }

    private static void correctXCoordinate(int mod, int halfOfTile, Ghost ghost) {
        if (mod < halfOfTile) {
            ghost.setCoordinateX(ghost.getCoordinateX() - mod);
        } else {
            ghost.setCoordinateX(ghost.getCoordinateX() + TILE_SIZE - mod);
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
