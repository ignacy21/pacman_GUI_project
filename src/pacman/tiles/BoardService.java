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
        Tile blockTile = new Tile("block", findImage("tile.png"));
        blockTile.setCollision(true);
        nameTileMap.put(1, blockTile);
        Tile point1Tile = new Tile("point1", findImage("point_1.png"));
        nameTileMap.put(2, point1Tile);
        Tile point2Tile = new Tile("point2", findImage("point_2.png"));
        nameTileMap.put(3, point2Tile);
    }

    public List<List<Tile>> createBoardFromFile(String path) throws IOException {
        List<List<Tile>> tiles = new ArrayList<>();
        try (
                BufferedReader bufferedReader = new BufferedReader(new FileReader(path))
        ) {
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) break;
                List<Tile> row = Arrays.stream(line.split(" "))
                        .map(Integer::parseInt)
                        .map(this::convertIntToTile)
                        .toList();
                tiles.add(row);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return tiles;
    }

    private Tile convertIntToTile(Integer tileNumber) {
        return nameTileMap.get(tileNumber);
    }

    private static BufferedImage findImage(String tileName) {
        try {
            return ImageIO.read(new File("resources/images/tiles/" + tileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
