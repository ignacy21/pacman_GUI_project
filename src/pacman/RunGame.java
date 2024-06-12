package pacman;

import pacman.mainPanel.PacmanFrame;
import pacman.mainPanel.RunPacman;
import pacman.tiles.point.HighScoresFrame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RunGame {

    private JFrame frame;
    private JButton newGameButton;
    private JButton highScoresButton;
    private JButton exitButton;
    private List<JCheckBox> mapCheckboxes;
    private String selectedMap;
    private Map<String, String> mapList = new HashMap<>();
    public RunGame() {
        this.frame = new PacmanFrame(500, 400);
        this.newGameButton = new JButton("New Game");
        this.highScoresButton = new JButton("High Scores");
        this.exitButton = new JButton("Exit");
        this.mapCheckboxes = new ArrayList<>();

        JPanel mapSelectionPanel = new JPanel(new GridLayout(0, 1));

        mapList.put("board1.txt", "54 x 21");
        mapList.put("board2.txt", "28 x 31");
        mapList.put("board3.txt", "50 x 25");
        mapList.put("board4.txt", "60 x 30");
        ButtonGroup mapButtonGroup = new ButtonGroup();
        for (Map.Entry<String, String> stringStringEntry : mapList.entrySet()) {
            String map = stringStringEntry.getKey();
            String mapDimensions = stringStringEntry.getValue();
            JCheckBox checkBox = new JCheckBox(mapDimensions);
            checkBox.addActionListener(e -> selectedMap = map);
            mapCheckboxes.add(checkBox);
            mapButtonGroup.add(checkBox);
            mapSelectionPanel.add(checkBox);
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
        buttonPanel.add(newGameButton);
        buttonPanel.add(highScoresButton);
        buttonPanel.add(exitButton);

        frame.add(mapSelectionPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.NORTH);
        frame.revalidate();
        frame.repaint();
    }

    private void startGame() {
        newGameButton.setEnabled(false);
        frame.dispose();
        SwingUtilities.invokeLater(() -> new RunPacman(selectedMap, 3));
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
