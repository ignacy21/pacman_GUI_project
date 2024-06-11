package pacman.tiles.boards;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class MirrorBoard {

    public static void main(String[] args) {
        List<String> mirror = mirror("src/pacman/tiles/boards/board1.txt");
        for (String s : mirror) {
            System.out.println(s);
        }

    }

    private static List<String> mirror(String path) {
        List<String> result;
        try {
            result = Files.readAllLines(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < result.size(); i++) {
            StringBuilder sb = new StringBuilder();
            String s = result.get(i);
            sb.append(s);
            String[] s1 = s.split(" ");
            for (int j = s1.length - 1; j >= 0; j--) {
                sb.append(" ").append(s1[j]);
            }
            result.set(i, sb.toString());
        }
        return result;
    }
}
