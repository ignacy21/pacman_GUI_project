package pacman.playerControl;

import java.awt.event.KeyListener;

public interface Player extends Entity, KeyListener {

    Direction getDirection();

    void setColliding(boolean colliding);

}
