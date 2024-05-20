package pacman.ghosts;

import pacman.playerControl.Direction;
import pacman.playerControl.Entity;
import pacman.tiles.Tile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Ghost implements Entity {

    private Direction direction;
    private int xPosition;
    private int yPosition;
    private int[] pacmanCoordinate;
    private final int[] cornerCoordinate;
    private int speed;
    private final int size;
    private int animationCounter = 0;
    private final List<List<Tile>> board;
    private final List<BufferedImage> animationUp;
    private final List<BufferedImage> animationDown;
    private final List<BufferedImage> animationLeft;
    private final List<BufferedImage> animationRight;
    private final GhostService ghostService;
    private GhostMode ghostMode;
    public String ghostName;

    public Ghost(int xPosition, int yPosition, Direction direction, int speed, int size, List<List<Tile>> board, int[] pacmanCoordinate,int[] cornerCoordinate, String ghostName) {
        this.direction = direction;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.speed = speed;
        this.size = size;
        this.ghostName = ghostName;
        this.board = board;
        this.pacmanCoordinate = pacmanCoordinate;
        this.cornerCoordinate = cornerCoordinate;
        ghostService = new GhostService(this, board);
        String pathName = "resources/images/ghosts";
        try {
            animationDown = List.of(
                    ImageIO.read(new File(String.format("%s/%s/%s_d_1.png", pathName, ghostName, ghostName))),
                    ImageIO.read(new File(String.format("%s/%s/%s_d_2.png", pathName, ghostName, ghostName)))
            );
            animationUp = List.of(
                    ImageIO.read(new File(String.format("%s/%s/%s_u_1.png", pathName, ghostName, ghostName))),
                    ImageIO.read(new File(String.format("%s/%s/%s_u_2.png", pathName, ghostName, ghostName)))
            );
            animationLeft = List.of(
                    ImageIO.read(new File(String.format("%s/%s/%s_l_1.png", pathName, ghostName, ghostName))),
                    ImageIO.read(new File(String.format("%s/%s/%s_l_2.png", pathName, ghostName, ghostName)))
            );
            animationRight = List.of(
                    ImageIO.read(new File(String.format("%s/%s/%s_r_1.png", pathName, ghostName, ghostName))),
                    ImageIO.read(new File(String.format("%s/%s/%s_r_2.png", pathName, ghostName, ghostName)))
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public int getCoordinateX() {
        return xPosition;
    }

    @Override
    public int getCoordinateY() {
        return yPosition;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public void update() {
        ghostService.whereGhostShouldTurn();
        switch (direction) {
            case UP -> yPosition -= speed;
            case DOWN -> yPosition += speed;
            case LEFT -> xPosition -= speed;
            case RIGHT -> xPosition += speed;
        }
    }

    @Override
    public void drawEntity(Graphics2D g2) {
        List<BufferedImage> animationList = animationUp;

        switch (direction) {
            case DOWN -> animationList = animationDown;
            case LEFT -> animationList = animationLeft;
            case RIGHT -> animationList = animationRight;
        }
        int animationUpdate = 4;
        if (animationCounter >= animationList.size() * animationUpdate) {
            animationCounter = animationUpdate;
        }

        BufferedImage bufferedImage = animationList.get(animationCounter / animationUpdate);
        animationCounter++;
        g2.setColor(Color.RED);
        g2.drawImage(bufferedImage, xPosition, yPosition, size, size, null);
    }

    public void setPacmanCoordinate(int[] pacmanCoordinate) {
        this.pacmanCoordinate = pacmanCoordinate;
    }

    public Direction getDirection() {
        return direction;
    }

    public int[] getPacmanCoordinate() {
        return pacmanCoordinate;
    }

    public int[] getCornerCoordinate() {
        return cornerCoordinate;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setXPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public GhostMode getGhostMode() {
        return ghostMode;
    }

    public void setGhostMode(GhostMode ghostMode) {
        this.ghostMode = ghostMode;
    }

    public void setYPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    @Override
    public String toString() {
        return ghostName;
    }
}
