package pacman.tiles;

import java.awt.image.BufferedImage;

public class Tile {

    private final String name;
    private BufferedImage image;
    private boolean collision = false;

    public Tile(String name, BufferedImage image) {
        this.name = name;
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }

    public boolean isCollision() {
        return collision;
    }

    public String getName() {
        return name;
    }

}
