package pacman.tiles.collision;

import pacman.mainPanel.GamePanel;
import pacman.playerControl.Direction;
import pacman.playerControl.Pacman;
import pacman.playerControl.Player;
import pacman.tiles.Tile;

import java.util.List;

import static pacman.mainPanel.GamePanel.TILE_SIZE;
import static pacman.playerControl.Direction.*;

public class CollisionService {

    private final GamePanel gamePanel;


    public CollisionService(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void checkCollision(Player player) {
        Direction direction = player.getDirection();
        List<List<Tile>> board = gamePanel.getBoard();
        Pacman pacman = gamePanel.getPacman();
        int speed = pacman.getSpeed();
        int halfOfTile = TILE_SIZE / 2;

        int playersUp = player.getCoordinateY();
        int playersLeft = player.getCoordinateX();
        int playersRight = player.getCoordinateX() + TILE_SIZE;
        int playersBottom = player.getCoordinateY() + TILE_SIZE;

        int yUp = (playersUp - speed) / TILE_SIZE;
        int xUpLt = (playersLeft - speed) / TILE_SIZE;
        int xUpRt = (playersRight - speed) / TILE_SIZE;
        Tile tileUpLt = board.get(yUp).get(xUpLt);
        Tile tileUpRt = board.get(yUp).get(xUpRt);

        int xBotLt = (playersLeft + speed) / TILE_SIZE;
        int xBotRt = (playersRight + speed) / TILE_SIZE;
        int yBot = (playersBottom) / TILE_SIZE;
        Tile tileBotLt = board.get(yBot).get(xBotLt);
        Tile tileBotRt = board.get(yBot).get(xBotRt);

        int yRtUp = (playersUp + speed) / TILE_SIZE;
        int yRtBot = (playersBottom + speed) / TILE_SIZE;
        int xRt = (playersRight) / TILE_SIZE;
        Tile tileRightUp = board.get(yRtUp).get(xRt);
        Tile tileRightBot = board.get(yRtBot).get(xRt);

        int yLtUp = (playersUp + speed) / TILE_SIZE;
        int yLtBot = (playersBottom + speed) / TILE_SIZE;
        int xLt = (playersLeft - speed) / TILE_SIZE;
        Tile tileLeftUp = board.get(yLtUp).get(xLt);
        Tile tileLeftBot = board.get(yLtBot).get(xLt);

        if (direction == UP && (tileUpLt.isCollision() || tileUpRt.isCollision())) {
            collisionDetection(playersLeft, playersRight, tileUpRt, pacman, halfOfTile, "Y");
        } else if (direction == DOWN && (tileBotLt.isCollision() || tileBotRt.isCollision())) {
            collisionDetection(playersLeft, playersRight, tileBotLt, pacman, halfOfTile, "Y");
        } else if (direction == LEFT && (tileLeftUp.isCollision() || tileLeftBot.isCollision())) {
            collisionDetection(playersUp, playersBottom, tileLeftUp, pacman, halfOfTile, "X");
            pacman.setColliding(playersUp % TILE_SIZE != 0 || playersBottom % TILE_SIZE != 0 || tileLeftUp.isCollision());
        } else if (direction == RIGHT && (tileRightUp.isCollision() || tileRightBot.isCollision())) {
            collisionDetection(playersUp, playersBottom, tileRightUp, pacman, halfOfTile, "X");
        } else {
            pacman.setColliding(false);
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
