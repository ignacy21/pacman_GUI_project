package pacman.tiles.collision;

import pacman.playerControl.Direction;
import pacman.playerControl.Pacman;
import pacman.tiles.Tile;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import static pacman.mainPanel.PacmanPanel.TILE_SIZE;
import static pacman.playerControl.Direction.*;

public class CollisionService {


    private final Pacman player;
    private final List<List<Tile>> board;

    public CollisionService(Pacman player, List<List<Tile>> board) {
        this.player = player;
        this.board = board;
    }

    public boolean canPacmanTurn(Direction currentDirection, Direction direction) {
        int halfOfTile = TILE_SIZE / 2;

        int playersLeft = player.getCoordinateX();
        int playersBottom = player.getCoordinateY() + TILE_SIZE;

        int playersCurrentX = (playersLeft + halfOfTile) / TILE_SIZE;
        int playersCurrentY = (playersBottom - halfOfTile) / TILE_SIZE;
        List<Tile> row = new ArrayList<>(board.get(playersCurrentY));
        Tile rightTile = row.get(playersCurrentX + 1);
        Tile leftTile = row.get(playersCurrentX - 1);
        Tile tileDown = board.get(playersCurrentY + 1).get(playersCurrentX);
        Tile tileUp = board.get(playersCurrentY - 1).get(playersCurrentX);

        boolean wantToReverse = false;
        switch (currentDirection) {
            case UP -> wantToReverse = direction == DOWN;
            case DOWN -> wantToReverse = direction == UP;
            case LEFT -> wantToReverse = direction == RIGHT;
            case RIGHT -> wantToReverse = direction == LEFT;
        }
        if (wantToReverse) {
            return wantToReverse;
        }

        if (playersBottom % TILE_SIZE == 0 && playersLeft % TILE_SIZE == 0) {
            return switch (direction) {
                case UP -> !tileUp.isCollision();
                case DOWN -> !tileDown.isCollision();
                case LEFT -> !leftTile.isCollision();
                case RIGHT -> !rightTile.isCollision();
            };
        }
        return false;
    }

    public void checkCollision() {
        Direction direction = player.getDirection();
        int spareAdd = 1;
        int halfOfTile = TILE_SIZE / 2;

        int playersUp = player.getCoordinateY();
        int playersLeft = player.getCoordinateX();
        int playersRight = player.getCoordinateX() + TILE_SIZE;
        int playersBottom = player.getCoordinateY() + TILE_SIZE;

        int yUp = (playersUp - spareAdd) / TILE_SIZE;
        int xUpLt = (playersLeft - spareAdd) / TILE_SIZE;
        int xUpRt = (playersRight - spareAdd) / TILE_SIZE;
        Tile tileUpLt = board.get(yUp).get(xUpLt);
        Tile tileUpRt = board.get(yUp).get(xUpRt);

        int xBotLt = (playersLeft + spareAdd) / TILE_SIZE;
        int xBotRt = (playersRight + spareAdd) / TILE_SIZE;
        int yBot = (playersBottom) / TILE_SIZE;
        Tile tileBotLt = board.get(yBot).get(xBotLt);
        Tile tileBotRt = board.get(yBot).get(xBotRt);

        int yRtUp = (playersUp + spareAdd) / TILE_SIZE;
        int yRtBot = (playersBottom + spareAdd) / TILE_SIZE;
        int xRt = (playersRight) / TILE_SIZE;
        Tile tileRightUp = board.get(yRtUp).get(xRt);
        Tile tileRightBot = board.get(yRtBot).get(xRt);

        int yLtUp = (playersUp + spareAdd) / TILE_SIZE;
        int yLtBot = (playersBottom + spareAdd) / TILE_SIZE;
        int xLt = (playersLeft - spareAdd) / TILE_SIZE;
        Tile tileLeftUp = board.get(yLtUp).get(xLt);
        Tile tileLeftBot = board.get(yLtBot).get(xLt);

        if (direction == UP && (tileUpLt.isCollision() || tileUpRt.isCollision())) {
            collisionDetection(playersLeft, playersRight, tileUpRt, player, halfOfTile, "Y");
        } else if (direction == DOWN && (tileBotLt.isCollision() || tileBotRt.isCollision())) {
            collisionDetection(playersLeft, playersRight, tileBotLt, player, halfOfTile, "Y");
        } else if (direction == LEFT && (tileLeftUp.isCollision() || tileLeftBot.isCollision())) {
            collisionDetection(playersUp, playersBottom, tileLeftUp, player, halfOfTile, "X");
            player.setColliding(playersUp % TILE_SIZE != 0 || playersBottom % TILE_SIZE != 0 || tileLeftUp.isCollision());
        } else if (direction == RIGHT && (tileRightUp.isCollision() || tileRightBot.isCollision())) {
            collisionDetection(playersUp, playersBottom, tileRightUp, player, halfOfTile, "X");
        } else {
            player.setColliding(false);
        }
    }

    private void collisionDetection(
            int crucialCoordinate1,
            int crucialCoordinate2,
            Tile tileThatMayBeVoid,
            Pacman pacman,
            int halfOfTile,
            String correctCoordinate
    ) {
        if (crucialCoordinate1 % TILE_SIZE == 0 && crucialCoordinate2 % TILE_SIZE == 0 && !tileThatMayBeVoid.isCollision()) {
            pacman.setColliding(false);
        } else {
            if (correctCoordinate.equals("Y")) {
                int mod = pacman.getCoordinateY() % TILE_SIZE;
                correctYCoordinate(mod, halfOfTile, pacman);
            } else  {
                int mod = pacman.getCoordinateX() % TILE_SIZE;
                correctXCoordinate(mod, halfOfTile, pacman);
            }
            pacman.setColliding(true);
        }
    }

    private static void correctYCoordinate(int mod, int halfOfTile, Pacman pacman) {
        if (mod < halfOfTile) {
            pacman.setYPosition(pacman.getCoordinateY() - mod);
        } else {
            pacman.setYPosition(pacman.getCoordinateY() + TILE_SIZE - mod);
        }
    }

    private static void correctXCoordinate(int mod, int halfOfTile, Pacman pacman) {
        if (mod < halfOfTile) {
            pacman.setXPosition(pacman.getCoordinateX() - mod);
        } else {
            pacman.setXPosition(pacman.getCoordinateX() + TILE_SIZE - mod);
        }
    }

}
