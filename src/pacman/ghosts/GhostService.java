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

        int gap = (int) ghost.getSpeed() - 1;
        if (ghostLeft % TILE_SIZE <= gap && ghostBottom % TILE_SIZE <= gap) {
            int gapX = ghostLeft % TILE_SIZE;
            int gapY = ghostBottom % TILE_SIZE;

            int ghostCurrentTileX = (ghostLeft + halfOfTile) / TILE_SIZE;
            int ghostCurrentTileY = (ghostBottom - halfOfTile) / TILE_SIZE;

            Tile[] tilesAroundGhost = tilesAroundGivenTile(ghostCurrentTileX, ghostCurrentTileY);
            Map<Integer, Tile> optionOfTurn = new HashMap<>();

            for (int i = 0; i < tilesAroundGhost.length; i++) {
                if (!tilesAroundGhost[i].isCollision()) {
                    optionOfTurn.put(i, tilesAroundGhost[i]);
                } else {
                    tilesAroundGhost[i] = null;
                }
            }

            switch (direction) {
                case UP -> optionOfTurn.remove(1);
                case DOWN -> optionOfTurn.remove(0);
                case LEFT -> optionOfTurn.remove(2);
                case RIGHT -> optionOfTurn.remove(3);
            }
            if (ghost.getGhostMode() == RESPAWN) {
                Tile[] tilesAroundGhost1 = tilesAroundGivenTile(ghostCurrentTileX, ghostCurrentTileY);
                Map<Integer, Tile> anyTurn = new HashMap<>();
                for (int i = 0; i < tilesAroundGhost1.length; i++) {
                    anyTurn.put(i, tilesAroundGhost1[i]);
                }
                optionOfTurn = anyTurn;
            }

            int directionByNumber = 0;
            if (optionOfTurn.size() > 1) {
                GhostMode ghostMode = ghost.getGhostMode();
                if (ghostMode == CHASE) {
                    int pacmanX = ghost.getPacmanCoordinate()[0];
                    int pacmanY = ghost.getPacmanCoordinate()[1];

                    directionByNumber = getDirectionBasedOnDestinationCoordinate(optionOfTurn, pacmanX, pacmanY, directionByNumber);
                } else if (ghostMode == SCATTER) {
                    int cornerX = ghost.getCornerCoordinate()[0];
                    int cornerY = ghost.getCornerCoordinate()[1];

                    directionByNumber = getDirectionBasedOnDestinationCoordinate(optionOfTurn, cornerX, cornerY, directionByNumber);
                } else if (ghostMode == RUN) {
                    int pacmanX = ghost.getPacmanCoordinate()[0];
                    int pacmanY = ghost.getPacmanCoordinate()[1];
                    double distanceBetweenPacmanAndTile = Integer.MIN_VALUE;
                    for (Map.Entry<Integer, Tile> integerTileEntry : optionOfTurn.entrySet()) {
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
                            optionOfTurn, respawnPointX, respawnPointY, directionByNumber
                    );
                }
            } else {
                try {
                    Integer next = optionOfTurn.keySet().iterator().next();
                    directionByNumber = next;
                } catch (NoSuchElementException e) {
                    System.err.printf("[%s] ghost has nowhere to turn %n", ghost.getGhostName());
                    throw new RuntimeException();
                }
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
        try {
            List<Tile> row = new ArrayList<>(board.get(tileY));
            Tile tileUp = board.get(tileY - 1).get(tileX);
            Tile tileDown = board.get(tileY + 1).get(tileX);
            Tile rightTile = row.get(tileX + 1);
            Tile leftTile = row.get(tileX - 1);
//            System.err.println(ghost.getGhostName());
//            if ("inky".equals(ghost.getGhostName()))
//                System.err.printf("%s %s | tiles around: up: %s down: %s, right: %s left: %s%n", tileX, tileY, tileUp, tileDown, rightTile, leftTile);
            return new Tile[]{tileUp, tileDown, rightTile, leftTile};
        } catch (IndexOutOfBoundsException e) {
            System.err.printf("[%s] ghost is out of bounds%n", ghost.getGhostName());
            System.err.printf("X:[%s]  |  Y:[%s]%n", tileX, tileY);
        }
        return null;
    }
}
