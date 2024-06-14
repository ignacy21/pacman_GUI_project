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
    Map<String, Integer> nameIntTileMap = new HashMap<>();
    private String currentMap;

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
        Tile strawberry = new Tile("strawberry", findImage("strawberry.png"));
        nameTileMap.put(9, strawberry);
    }

    {
        nameIntTileMap.put("void", 0);
        nameIntTileMap.put("block", 1);
        nameIntTileMap.put("point1", 2);
        nameIntTileMap.put("point2", 3);
        nameIntTileMap.put("gate", 4);
        nameIntTileMap.put("gate2", 5);
        nameIntTileMap.put("gate3", 6);
        nameIntTileMap.put("gate4", 7);
        nameIntTileMap.put("blockade", 8);
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
            System.err.printf("cannot find file:[%s]%n", path);
            throw new RuntimeException(e);
        }
        System.out.printf("BOARD: width[%s]  height[%s]%n", tiles.getFirst().size(), tiles.size());
        return tiles;
    }

    private Tile getTileByItsInt(Integer tileNumber) {
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

    public String writeCurrentBoard(List<List<Tile>> board, String boardName) {
        if (boardName.contains("current")) {
            currentMap = boardName;
        } else {
            currentMap = "current_%s".formatted(boardName);
        }
        createFileIfItDoesntExist("src/pacman/tiles/boards/" + currentMap);
        List<String> boardAsStrings = boardToStringList("src/pacman/tiles/boards/" + boardName);
        for (int rowNum = 0; rowNum < boardAsStrings.size(); rowNum++) {
            StringBuilder sb = correctAllCollectedPoints(board, boardAsStrings, rowNum);
            boardAsStrings.set(rowNum, sb.toString());
        }
        writeLine(boardAsStrings);
        return currentMap;
    }

    private static StringBuilder correctAllCollectedPoints(List<List<Tile>> board, List<String> boardAsStrings, int rowNum) {
        String row = boardAsStrings.get(rowNum);
        String[] splitRow = row.split(" ");

        StringBuilder sb = new StringBuilder(row);

        for (int colNum = 0; colNum < splitRow.length; colNum++) {
            String tileAsString = splitRow[colNum];
            if ("2".equals(tileAsString) || "3".equals(tileAsString)) {
                Tile tile = board.get(rowNum).get(colNum);
                if ("void".equals(tile.getName())) {
                    sb.setCharAt(colNum * 2, '0');
                }
            }
        }
        return sb;
    }

    private void writeLine(List<String> boardAsStrings) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/pacman/tiles/boards/" + currentMap))) {
            for (String line : boardAsStrings) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.printf("while rewriting updated game board:[%s] error occurred%n", currentMap);
            throw new RuntimeException(e);
        }
    }

    private static void createFileIfItDoesntExist(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.printf("creating file:[%s] went wrong%n", fileName);
                throw new RuntimeException(e);
            }
        }
    }

    private List<String> boardToStringList(String path) {
        List<String> stringBoard = new ArrayList<>();
        try (
                BufferedReader bufferedReader = new BufferedReader(new FileReader(path))
        ) {
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) break;
                stringBoard.add(line);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return stringBoard;
    }
}
