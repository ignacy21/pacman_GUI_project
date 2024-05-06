package pacman.playerControl;

import java.awt.*;

public interface Entity {

    int getCoordinateX();
    int getCoordinateY();
    int getSpeed();
    void update();
    void drawPackman(Graphics2D graphics2D);
}
