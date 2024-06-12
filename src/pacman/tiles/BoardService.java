package pacman.tiles;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardService {

    Map<Integer, Tile> nameTileMap = new HashMap<>();

    {
        Tile voidTile = new Tile("void", null);
        nameTileMap.put(0, voidTile);
        Tile blockTile = new Tile("block", findImage("tile_16x16.png"));
        blockTile.setCollision(true);
        nameTileMap.put(1, blockTile);
        Tile point1Tile = new Tile("point1", findImage("point_1.png"));
        nameTileMap.put(2, point1Tile);
        Tile point2Tile = new Tile("point2", findImage("point2.png"));
        nameTileMap.put(3, point2Tile);
        Tile gate = new Tile("gate", findImage("gate.png"));
        nameTileMap.put(4, gate);
        Tile gate2 = new Tile("gate2", findImage("gate2.png"));
        nameTileMap.put(5, gate2);
        Tile gate3 = new Tile("gate3", findImage("gate3.png"));
        nameTileMap.put(6, gate3);
        Tile gate4 = new Tile("gate4", findImage("gate4.png"));
        nameTileMap.put(7, gate4);
        Tile blockade = new Tile("blockade", findImage("blockade.png"));
        blockade.setCollision(true);
        nameTileMap.put(8, blockade);
    }

    public List<List<Tile>> createBoardFromFile(String path) throws IOException {
        List<List<Tile>> tiles = new ArrayList<>();
        int rowCount = 0;
        try (
                BufferedReader bufferedReader = new BufferedReader(new FileReader(path))
        ) {
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) break;
                List<Tile> row = new ArrayList<>();
                String[] s = line.split(" ");
                for (int i = 0; i < s.length; i++) {
                    Tile tile = getTileByItsInt(Integer.parseInt(s[i]));
                    tile.setRowNumber(rowCount);
                    tile.setColumnNumber(i);
                    row.add(tile);
                }
                tiles.add(row);
                rowCount++;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.printf("BOARD: width[%s]  height[%s]%n", tiles.getFirst().size(), tiles.size());
        return tiles;
    }

    public Tile getTileByItsInt(Integer tileNumber)  {
        Tile clone;
        try {
            clone = (Tile) nameTileMap.get(tileNumber).clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        return clone;
    }

    private static BufferedImage findImage(String tileName) {
        try {
            return ImageIO.read(new File("resources/images/tiles/" + tileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
