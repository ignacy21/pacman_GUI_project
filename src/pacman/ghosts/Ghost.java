package pacman.ghosts;

import pacman.playerControl.Direction;
import pacman.playerControl.Entity;
import pacman.tiles.Tile;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static pacman.ghosts.GhostMode.*;

public class Ghost implements Entity, Runnable {

    private Direction direction;
    private int xPosition;
    private int yPosition;
    private int[] pacmanCoordinate;
    private final int[] cornerCoordinate;
    private final int[] respawnPoint;
    private int speed;
    private final int size;
    private int animationCounter = 0;
    private final List<List<Tile>> board;
    private final List<BufferedImage> animationUp;
    private final List<BufferedImage> animationDown;
    private final List<BufferedImage> animationLeft;
    private final List<BufferedImage> animationRight;
    private final List<BufferedImage> blueAnimationRun;
    private final List<BufferedImage> whiteAnimationRun;
    private final GhostService ghostService;
    private volatile GhostMode ghostMode;
    public String ghostName;
    private volatile boolean running = true;
    private Thread thread;

    public Ghost(int xPosition, int yPosition, Direction direction, int speed, int size, List<List<Tile>> board, int[] pacmanCoordinate, int[] cornerCoordinate, int[] respawnPoint, String ghostName, int rowThatSwitchSides) {
        this.direction = direction;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.respawnPoint = respawnPoint;
        this.speed = speed;
        this.size = size;
        this.ghostName = ghostName;
        this.board = board;
        this.pacmanCoordinate = pacmanCoordinate;
        this.cornerCoordinate = cornerCoordinate;
        ghostService = new GhostService(this, board, rowThatSwitchSides);
        thread = new Thread(this);
//        SwingUtilities.invokeLater(thread);
        thread.start();
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
            blueAnimationRun = List.of(
                    ImageIO.read(new File(String.format("%s/run/blue1.png", pathName))),
                    ImageIO.read(new File(String.format("%s/run/blue2.png", pathName)))
            );
            whiteAnimationRun = List.of(
                    ImageIO.read(new File(String.format("%s/run/white1.png", pathName))),
                    ImageIO.read(new File(String.format("%s/run/white2.png", pathName)))
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
        ghostService.allowGhostToChangeSides();
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

        if (ghostMode == GhostMode.RUN) {
            animationList = blueAnimationRun;
        } else {
            switch (direction) {
                case DOWN -> animationList = animationDown;
                case LEFT -> animationList = animationLeft;
                case RIGHT -> animationList = animationRight;
            }
        }

        int animationUpdate = 4;
        if (animationCounter >= animationList.size() * animationUpdate) {
            animationCounter = 0;
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

    public int[] getRespawnPoint() {
        return respawnPoint;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public void setCoordinateX(int xPosition) {
        this.xPosition = xPosition;
    }

    public GhostMode getGhostMode() {
        return ghostMode;
    }

    public void setGhostMode(GhostMode ghostMode) {
        this.ghostMode = ghostMode;
    }

    public void setCoordinateY(int yPosition) {
        this.yPosition = yPosition;
    }

    @Override
    public String toString() {
        return ghostName;
    }

    @Override
    public void run() {
        while (running) {
            try {
                if (ghostMode == RUN) {
                    Thread.sleep(7000);
                    System.err.println("RUN");
                    ghostMode = CHASE;
                } else if (ghostMode == CHASE) {
                    Thread.sleep(6000);
                    System.err.println("SCATTER");
                    ghostMode = SCATTER;
                } else if (ghostMode == SCATTER) {
                    Thread.sleep(6000);
                    System.err.println("CHASE");
                    ghostMode = CHASE;
                }
            } catch (InterruptedException e) {
                if (ghostMode == RUN) {
                    System.err.println("Interrupted and changing to RUN");
                    continue;
                }
                if (!running) {
                    break;
                }
                throw new RuntimeException(e);
            }
        }
    }
    public void changeToRunMode() {
        ghostMode = RUN;
        thread.interrupt();
    }

    public void stopThread() {
        this.running = false;
        thread.interrupt();
    }
}
