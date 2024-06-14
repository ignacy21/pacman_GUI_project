package pacman.tiles;

import java.awt.image.BufferedImage;

public class Tile implements Cloneable {

    private final String name;
    private final BufferedImage image;
    private boolean collision = false;
    private int columnNumber;
    private int rowNumber;
    private boolean isPoint;

    public Tile(String name, BufferedImage image, boolean isPoint) {
        this.name = name;
        this.image = image;
        this.isPoint = isPoint;

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

    public void setColumnNumber(int columnNumber) {
        this.columnNumber = columnNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "tileName: " + name;

    }

    public boolean isPoint() {
        return isPoint;
    }
}
