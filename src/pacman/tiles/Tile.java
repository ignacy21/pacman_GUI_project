package pacman.tiles;

import java.awt.image.BufferedImage;

public class Tile {

    private BufferedImage image;
    private boolean collision = false;

    public Tile(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }
}
