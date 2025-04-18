package pacman;

import pacman.mainPanel.PacmanFrame;
import pacman.mainPanel.RunPacman;
import pacman.tiles.point.HighScoresFrame;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.*;
import java.util.List;

public class RunGame {

    private final JFrame frame;
    private final JButton newGameButton;
    private final JButton highScoresButton;
    private final JButton exitButton;
    private final List<JCheckBox> mapCheckboxes;
    private final List<JPanel> chooseBoardPanels;
    private String selectedMap;
    private final Map<String, String> mapList = Map.of(
            "board1.txt", "54x21",
            "board2.txt", "28x31",
            "board3.txt", "50x25",
            "board4.txt", "60x30",
            "board5.txt", "35x29"
    );

    private final Map<String, JLabel> mapIcons = new HashMap<>();

    {
        for (Map.Entry<String, String> stringStringEntry : mapList.entrySet()) {
            String map = stringStringEntry.getKey();
            String mapDimensions = stringStringEntry.getValue();
            String[] split = mapDimensions.split("x");
            int dim1 = Integer.parseInt(split[0]);
            int dim2 = Integer.parseInt(split[1]);
            int scaleImg = 5;
            ImageIcon originalIcon = new ImageIcon(String.format("src/resources/images/maps/%s.png", mapDimensions));
            Image scaledImage = originalIcon.getImage().getScaledInstance(
                    dim1 * scaleImg,
                    dim2 * scaleImg,
                    Image.SCALE_SMOOTH
            );
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            JLabel imageLabel = new JLabel(scaledIcon);
            mapIcons.put(map, imageLabel);
        }
    }

    public RunGame() {
        this.frame = new PacmanFrame(1200, 600);

        JButton newGameBtn = new JButton("New Game");
        JButton highScoresBtn = new JButton("High Scores");
        JButton exitBtn = new JButton("Exit");
        prettyingButtons(newGameBtn, highScoresBtn, exitBtn);

        this.newGameButton = newGameBtn;
        this.highScoresButton = highScoresBtn;
        this.exitButton = exitBtn;
        this.mapCheckboxes = new ArrayList<>();
        this.chooseBoardPanels = new ArrayList<>();

        JPanel mapSelectionPanel = new JPanel(new GridLayout(0, 2));
        mapSelectionPanel.setBackground(Color.BLACK);


        ButtonGroup mapButtonGroup = new ButtonGroup();
        for (Map.Entry<String, String> stringStringEntry : mapList.entrySet()) {
            String map = stringStringEntry.getKey();
            String mapDimensions = stringStringEntry.getValue();
            JCheckBox checkBox = new JCheckBox(mapDimensions);
            checkBox.setForeground(Color.WHITE);

            mapCheckboxes.add(checkBox);
            mapButtonGroup.add(checkBox);

            JPanel chooseBoardPanel = new JPanel();
            chooseBoardPanel.setBackground(Color.BLACK);
            chooseBoardPanel.add(checkBox);
            chooseBoardPanel.add(mapIcons.get(map));
            chooseBoardPanels.add(chooseBoardPanel);
            mapSelectionPanel.add(chooseBoardPanel);

            checkBox.addActionListener(e -> {
                selectedMap = map;
                Border border = new LineBorder(Color.GREEN);
                for (JPanel boardPanel : chooseBoardPanels) {
                    boardPanel.setBorder(null);
                }
                chooseBoardPanel.setBorder(border);
            });
        }

        newGameButton.addActionListener(e -> {
            if (selectedMap != null) {
                startGame();
            } else {
                JOptionPane.showMessageDialog(frame, "Choose map from below first!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        highScoresButton.addActionListener(e -> showHighScores());
        exitButton.addActionListener(e -> exitGame());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.setBorder(new LineBorder(Color.BLUE));
        buttonPanel.add(newGameButton);
        buttonPanel.add(highScoresButton);
        buttonPanel.add(exitButton);

        frame.add(mapSelectionPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.NORTH);
        frame.revalidate();
        frame.repaint();
    }

    private static void prettyingButtons(JButton newGameBtn, JButton highScoresBtn, JButton exitBtn) {
        List<JButton> headButtons = List.of(
                newGameBtn,
                highScoresBtn,
                exitBtn
        );
        for (JButton headButton : headButtons) {
            headButton.setFont(new Font("PacFont", Font.PLAIN, 16));
            headButton.setBackground(Color.BLUE);
            headButton.setForeground(Color.WHITE);
            headButton.setFocusPainted(false);
            headButton.setOpaque(true);
            headButton.setBorderPainted(false);

        }
    }

    private void startGame() {
        newGameButton.setEnabled(false);
        frame.dispose();
        SwingUtilities.invokeLater(() -> new RunPacman(
                selectedMap,
                3,
                0,
                1,
                0,
                new int[]{7000, 6000, 6000},
                3
        ));
    }

    private void showHighScores() {
        frame.dispose();
        SwingUtilities.invokeLater(() -> {
            HighScoresFrame highScoresFrame = new HighScoresFrame();
            highScoresFrame.setVisible(true);
        });
    }

    private void exitGame() {
        frame.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RunGame::new);
    }
}
