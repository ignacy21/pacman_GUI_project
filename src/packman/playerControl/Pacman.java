package packman.playerControl;

import java.awt.event.KeyEvent;

public class Pacman implements Player {

    private Direction direction;
    private int xPosition;
    private int yPosition;
    private int speed;

    public Pacman(int xPosition, int yPosition, int speed) {
        this.direction = Direction.UP;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.speed = speed;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

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
    public void keyReleased(KeyEvent e) {}

    @Override
    public int getPlayerX() {
        return xPosition;
    }

    @Override
    public int getPlayerY() {
        return yPosition;
    }

    @Override
    public int getPlayerSpeed() {
        return speed;
    }

    @Override
    public Direction getPlayerDirection() {
        return direction;
    }

    public void setXPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public void setYPosition(int yPosition) {
        this.yPosition = yPosition;
    }


    @Override
    public String toString() {
        return "Pacman{" +
                "direction=" + direction +
                ", xPosition=" + xPosition +
                ", yPosition=" + yPosition +
                ", speed=" + speed +
                '}';
    }
}
