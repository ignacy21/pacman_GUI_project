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
            pacman.setColliding(playersLeft % TILE_SIZE != 0 || playersRight % TILE_SIZE != 0 || tileUpRt.isCollision());
        } else if (direction == DOWN && (tileBotLt.isCollision() || tileBotRt.isCollision())) {
            pacman.setColliding(playersLeft % TILE_SIZE != 0 || playersRight % TILE_SIZE != 0 || tileBotLt.isCollision());
        } else if (direction == LEFT && (tileLeftUp.isCollision() || tileLeftBot.isCollision())) {
            pacman.setColliding(playersUp % TILE_SIZE != 0 || playersBottom % TILE_SIZE != 0 || tileLeftUp.isCollision());
        } else if (direction == RIGHT && (tileRightUp.isCollision() || tileRightBot.isCollision())) {
            pacman.setColliding(playersUp % TILE_SIZE != 0 || playersBottom % TILE_SIZE != 0 || tileRightUp.isCollision());
        } else {
            pacman.setColliding(false);
        }
    }

}
