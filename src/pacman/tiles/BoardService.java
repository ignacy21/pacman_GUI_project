package pacman.tiles;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

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
                    Tile tile = convertIntToTile(Integer.parseInt(s[i]));
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

    private Tile convertIntToTile(Integer tileNumber)  {
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
