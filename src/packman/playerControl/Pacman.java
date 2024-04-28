package packman.playerControl;

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

    private int animationCounter = 0;
    private final List<BufferedImage> animation;


    public Pacman(int xPosition, int yPosition, int speed) {
        List<BufferedImage> animation1;
        try {
            animation1 = List.of(
                    ImageIO.read(new File("resources/images/pacman_1.png")),
                    ImageIO.read(new File("resources/images/pacman_2.png")),
                    ImageIO.read(new File("resources/images/pacman_3.png"))
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.animation = animation1;
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
        switch (direction) {
            case UP -> yPosition -= speed;
            case DOWN -> yPosition += speed;
            case LEFT -> xPosition -= speed;
            case RIGHT -> xPosition += speed;
        }
    }

    @Override
    public void repaint(Graphics2D g2) {
//        try {
//            Thread.sleep(100);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        if (animationCounter >= 3) {
            animationCounter = 0;
        }
        BufferedImage bufferedImage = animation.get(animationCounter++);
        g2.setColor(Color.RED);
//        g2.fillRect(xPosition, yPosition, 45, 45);
        g2.drawImage(bufferedImage, xPosition, yPosition, 45, 45, null);
    }

}
