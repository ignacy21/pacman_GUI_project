package pacman.tiles;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BoardService {

    public List<List<Integer>> createBoardFromFile(String path) throws IOException {
        List<List<Integer>> resultList = new ArrayList<>();
        try (
                BufferedReader bufferedReader = new BufferedReader(new FileReader(path))
        ) {
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) break;
                List<Integer> list = Arrays.stream(line.split(" "))
                        .map(Integer::parseInt)
                        .toList();
                resultList.add(list);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return resultList;
    }
}
