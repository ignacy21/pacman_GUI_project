package packman.playerControl;

import java.awt.event.KeyListener;

public interface Player extends KeyListener {

    int getPlayerX();
    int getPlayerY();
    int getPlayerSpeed();
    Direction getPlayerDirection();
}
