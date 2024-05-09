package pacman.playerControl;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Pacman implements Player {

    private Direction direction;
    private int xPosition;
    private int yPosition;
    private int speed;
    private final int size;
    private boolean isColliding = false;
    private int animationCounter = 0;
    private final List<BufferedImage> animationUp;
    private final List<BufferedImage> animationDown;
    private final List<BufferedImage> animationLeft;
    private final List<BufferedImage> animationRight;


    public Pacman(int xPosition, int yPosition, int speed, int size) {
        this.size = size;
        List<BufferedImage> animationUp;
        List<BufferedImage> animationDown;
        List<BufferedImage> animationLeft;
        List<BufferedImage> animationRight;
        try {
            animationUp = List.of(
                    ImageIO.read(new File("resources/images/pacman/up/pacman_1.png")),
                    ImageIO.read(new File("resources/images/pacman/up/pacman_2.png")),
                    ImageIO.read(new File("resources/images/pacman/pacman.png"))
            );
            animationDown = List.of(
                    ImageIO.read(new File("resources/images/pacman/down/pacman_1.png")),
                    ImageIO.read(new File("resources/images/pacman/down/pacman_2.png")),
                    ImageIO.read(new File("resources/images/pacman/pacman.png"))
                    );
            animationRight = List.of(
                    ImageIO.read(new File("resources/images/pacman/right/pacman_1.png")),
                    ImageIO.read(new File("resources/images/pacman/right/pacman_2.png")),
                    ImageIO.read(new File("resources/images/pacman/pacman.png"))
                    );
            animationLeft = List.of(
                    ImageIO.read(new File("resources/images/pacman/left/pacman_1.png")),
                    ImageIO.read(new File("resources/images/pacman/left/pacman_2.png")),
                    ImageIO.read(new File("resources/images/pacman/pacman.png"))
                    );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.animationUp = animationUp;
        this.animationDown = animationDown;
        this.animationLeft = animationLeft;
        this.animationRight = animationRight;
        this.direction = Direction.UP;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.speed = speed;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_UP) {
            direction = Direction.UP;
        } else if (keyCode == KeyEvent.VK_DOWN) {
            direction = Direction.DOWN;
        } else if (keyCode == KeyEvent.VK_LEFT) {
            direction = Direction.LEFT;
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            direction = Direction.RIGHT;
        }
    }

    @Override
    public void update() {
        if (!isColliding) {
            switch (direction) {
                case UP -> yPosition -= speed;
                case DOWN -> yPosition += speed;
                case LEFT -> xPosition -= speed;
                case RIGHT -> xPosition += speed;
            }
        }
    }

    @Override
    public void drawPackman(Graphics2D g2) {
        List<BufferedImage> animationList = animationUp;
        switch (direction) {
            case DOWN -> animationList = animationDown;
            case LEFT -> animationList = animationLeft;
            case RIGHT -> animationList = animationRight;
        }

        int animationUpdate = 4;
        if (animationCounter >= 3 * animationUpdate) {
            animationCounter = animationUpdate;
        }

        BufferedImage bufferedImage = animationList.get(animationCounter / animationUpdate);
        animationCounter++;
        g2.setColor(Color.RED);
        g2.drawImage(bufferedImage, xPosition, yPosition, size, size, null);
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
    public boolean isColliding() {
        return isColliding;
    }
    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public void setColliding(boolean colliding) {
        isColliding = colliding;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public String toString() {
        return "Pacman{" +
                "direction=" + direction +
                ", xPosition=" + xPosition +
                ", yPosition=" + yPosition +
                ", speed=" + speed +
                ", isColliding=" + isColliding +
                '}';
    }
}
