package pacman.tiles;

import pacman.mainPanel.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static pacman.mainPanel.GamePanel.TILE_SIZE;

public class TileManager {

    private BoardService boardService = new BoardService();

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

    public void drawTile(Graphics2D graphics2D, String boardPath) {
        List<List<Integer>> boardFromFile;

        try {
            boardFromFile = boardService.createBoardFromFile(boardPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0, y = 0; i < boardFromFile.size(); i++, y += TILE_SIZE) {
            List<Integer> integers = boardFromFile.get(i);
//            System.out.println(integers);
            for (int j = 0, x = 0; j < integers.size(); j++, x += TILE_SIZE) {
                if (integers.get(j) == 1) {
                    graphics2D.drawImage(tiles[0].getImage(), x, y, TILE_SIZE, TILE_SIZE, null);
//                    System.out.printf("TILE x[%s] y[%s]\n", x, y);
                }
            }
//            System.out.println();
        }



    }
}
