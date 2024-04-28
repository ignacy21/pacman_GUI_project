package packman.playerControl;

import java.awt.*;

public interface Entity {

    int getCoordinateX();
    int getCoordinateY();
    int getSpeed();
    void update();
    void repaint(Graphics2D graphics2D);
}
