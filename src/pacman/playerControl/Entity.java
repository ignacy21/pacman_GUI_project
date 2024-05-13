package pacman.playerControl;

import java.awt.*;

public interface Entity {

    int getCoordinateX();
    int getCoordinateY();
    int getSpeed();
    void update();
    void drawEntity(Graphics2D graphics2D, int displayHeight);
}
