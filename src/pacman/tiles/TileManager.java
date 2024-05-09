package pacman.tiles;

import java.awt.*;
import java.util.List;

import static pacman.mainPanel.GamePanel.TILE_SIZE;

public class TileManager {

    public void drawTile(Graphics2D graphics2D, List<List<Tile>> board) {

        for (int i = 0, y = 0; i < board.size(); i++, y += TILE_SIZE) {
            List<Tile> tileRow = board.get(i);
            for (int j = 0, x = 0; j < tileRow.size(); j++, x += TILE_SIZE) {
                Tile tile = tileRow.get(j);
                switch (tile.getName()) {
                    case "block" -> graphics2D.drawImage(tile.getImage(), x, y, TILE_SIZE, TILE_SIZE, null);
                }
//                System.out.printf("TILE [%s] x[%s] y[%s]\n",tile.getName(), x, y);
            }
        }


    }
}
