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
        int spare = TILE_SIZE / 2;


        int playersUp = player.getCoordinateY();
        int playersLeft = player.getCoordinateX();
        int playersRight = player.getCoordinateX() + TILE_SIZE;
        int playersBottom = player.getCoordinateY() + TILE_SIZE;

        int yU = (playersUp - speed) / TILE_SIZE;
        int xU = (playersLeft + speed) / TILE_SIZE;
//        System.out.println("xU: " + xU);
//        System.out.println("yU: " + yU);
        Tile tileUp = board.get(yU).get(xU);

        int xB = (playersLeft + speed) / TILE_SIZE;
        int yB = (playersBottom + speed) / TILE_SIZE;
//        System.out.println("xB: " + xB);
//        System.out.println("yB: " + yB);
        Tile tileBottom = board.get(yB).get(xB);

        int yR = (playersUp + speed) / TILE_SIZE;
        int xR = (playersRight + speed) / TILE_SIZE;
//        System.out.println("xR: " + xR);
//        System.out.println("yR: " + yR);
        Tile tileRight = board.get(yR).get(xR);

        int yL = (playersUp + speed) / TILE_SIZE;
        int xL = (playersLeft - speed) / TILE_SIZE;
//        System.out.println("xL: " + xL);
//        System.out.println("yL: " + yL);
        Tile tileLeft = board.get(yL).get(xL);

        if (direction == UP && tileUp.isCollision()) {
            pacman.setColliding(true);
            System.out.println("colliding: UP");
//            return false;
        } else if (direction == DOWN && tileBottom.isCollision()) {
            pacman.setColliding(true);
            System.out.println("colliding: DOWN");
//            return false;
        } else if (direction == LEFT && tileLeft.isCollision()) {
            pacman.setColliding(true);
            System.out.println("colliding: LEFT");
//            return false;
        } else if (direction == RIGHT && tileRight.isCollision()) {
            pacman.setColliding(true);
            System.out.println("colliding: RIGHT");
//            return false;
        } else {
            pacman.setColliding(false);
//            return true;
        }
    }

}
