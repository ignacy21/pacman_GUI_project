package packman.tiles;

import packman.board.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import static packman.board.GamePanel.TILE_SIZE;

public class TileManager {

    private final GamePanel gamePanel;
    private Tile[] tiles;

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        tiles = new Tile[1];

        getTileImage();
    }

    private void getTileImage() {
        try {

            tiles[0] = new Tile(ImageIO.read(new File("resources/images/tiles/tile.png")));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void drawTile(Graphics2D graphics2D, int width, int height) {
        int tilesWidth = width / TILE_SIZE;
        int tilesHeight = height / TILE_SIZE;

        for (int i = 0, w = 0; i < tilesWidth; i++, w += TILE_SIZE) {
            graphics2D.drawImage(tiles[0].getImage(), w, 0, TILE_SIZE, TILE_SIZE, null);
            graphics2D.drawImage(tiles[0].getImage(), w, height - TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
        }
        for (int i = 0, h = 0; i < tilesHeight - 2; i++, h += TILE_SIZE) {
            graphics2D.drawImage(tiles[0].getImage(), 0, h + TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
            graphics2D.drawImage(tiles[0].getImage(), width - TILE_SIZE, h + TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
        }


    }
}
