package pacman.playerControl;

import pacman.tiles.Tile;
import pacman.tiles.collision.PacmanService;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Pacman implements Player {


    private Direction direction;
    private Direction wantToTurn;
    private int countTurn = 0;
    private final PacmanService pacmanService;
    private int xPosition;
    private int yPosition;
    private double speed;
    private final int size;
    private boolean isColliding = false;
    private int animationCounter = 0;
    private final List<BufferedImage> animationUp;
    private final List<BufferedImage> animationDown;
    private final List<BufferedImage> animationLeft;
    private final List<BufferedImage> animationRight;


    public Pacman(int xPosition, int yPosition, double speed, int size, List<List<Tile>> board, int rowThatSwitchSides) {
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
        pacmanService = new PacmanService(this, board, rowThatSwitchSides);
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
        turn(keyCode);
    }

    private void turn(int keyCode) {
        if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
            if (pacmanService.canPacmanTurn(direction, Direction.UP))
                direction = Direction.UP;
            wantToTurn = Direction.UP;
        } else if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
            if (pacmanService.canPacmanTurn(direction, Direction.DOWN))
                direction = Direction.DOWN;
            wantToTurn = Direction.DOWN;
        } else if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
            if (pacmanService.canPacmanTurn(direction, Direction.LEFT))
                direction = Direction.LEFT;
            wantToTurn = Direction.LEFT;
        } else if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
            if (pacmanService.canPacmanTurn(direction, Direction.RIGHT))
                direction = Direction.RIGHT;
            wantToTurn = Direction.RIGHT;
        }
    }

    @Override
    public void update() {
        if (countTurn < 50) {
            countTurn++;
            if (wantToTurn != null) {
                switch (wantToTurn) {
                    case UP -> turn(KeyEvent.VK_UP);
                    case DOWN -> turn(KeyEvent.VK_DOWN);
                    case RIGHT -> turn(KeyEvent.VK_RIGHT);
                    case LEFT -> turn(KeyEvent.VK_LEFT);
                }
            }

        } else {
            countTurn = 0;
            wantToTurn = null;
        }

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
    public void drawEntity(Graphics2D g2) {
        List<BufferedImage> animationList = animationUp;
        switch (direction) {
            case DOWN -> animationList = animationDown;
            case LEFT -> animationList = animationLeft;
            case RIGHT -> animationList = animationRight;
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


    @Override
    public int getCoordinateX() {
        return xPosition;
    }

    @Override
    public int getCoordinateY() {
        return yPosition;
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
    public double getSpeed() {
        return speed;
    }

    @Override
    public void setCoordinateX(int xPosition) {
        this.xPosition = xPosition;
    }

    @Override
    public void setCoordinateY(int yPosition) {
        this.yPosition = yPosition;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
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

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
