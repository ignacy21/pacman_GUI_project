package pacman.playerControl;

import java.awt.*;

public interface Entity {

    int getCoordinateX();
    int getCoordinateY();
    void setCoordinateX(int x);
    void setCoordinateY(int y);
    int getSpeed();
    void update();
    void drawEntity(Graphics2D graphics2D);
}
