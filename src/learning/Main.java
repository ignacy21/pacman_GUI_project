package learning;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

//        SwingUtilities.invokeLater(learning.Window::new);
//        SwingUtilities.invokeLater(learning.Window2::new);
//        SwingUtilities.invokeLater(learning.Window3::new);
//        SwingUtilities.invokeLater(learning.LabelWindow::new);
//        SwingUtilities.invokeLater(WindowWithPanels::new);

        List<Integer> integers = new ArrayList<>(List.of(1, 2, 3, 4, 1, 2, 3, 5, 7, 3, 9));
        integers.removeIf(x -> x == 1);
        System.out.println(integers);
        integers.removeIf(x -> x == 9);
        System.out.println(integers);
        integers.removeIf(x -> x == 0);
        System.out.println(integers);

        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            System.out.println(random.nextInt(0, 3));
        }
    }
}
