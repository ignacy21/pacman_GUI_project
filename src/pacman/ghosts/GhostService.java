package pacman.ghosts;

import pacman.playerControl.Direction;
import pacman.tiles.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        int[] whereToGo = ghost.getWhereToGo();


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
//        Tile tileUp = tiles[0];
//        Tile tileDown = tiles[1];
//        Tile rightTile = tiles[2];
//        Tile leftTile = tiles[3];

            List<Integer> tileOptionsToTurn = new ArrayList<>();
            int optionsToTurn = -1;
            for (int i = 0; i < tiles.length; i++) {
                if (!tiles[i].isCollision()) {
                    tileOptionsToTurn.add(i);
                    optionsToTurn++;
                }
            }
            switch (direction) {
                case UP -> tileOptionsToTurn.removeIf(d -> d == 1);
                case DOWN -> tileOptionsToTurn.removeIf(d -> d == 0);
                case LEFT -> tileOptionsToTurn.removeIf(d -> d == 2);
                case RIGHT -> tileOptionsToTurn.removeIf(d -> d == 3);
            }
            int i = random.nextInt(0, optionsToTurn);
            Integer i1 = tileOptionsToTurn.get(i);

            switch (i1) {
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

    private Tile[] tilesAroundGivenTile(int tileX, int tileY) {

        List<Tile> row = new ArrayList<>(board.get(tileY));
        Tile tileUp = board.get(tileY - 1).get(tileX);
        Tile tileDown = board.get(tileY + 1).get(tileX);
        Tile rightTile = row.get(tileX + 1);
        Tile leftTile = row.get(tileX - 1);
        return new Tile[]{tileUp, tileDown, rightTile, leftTile};
    }
}
