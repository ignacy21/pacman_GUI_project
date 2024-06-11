package pacman;

import pacman.mainPanel.PacmanFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

        mapList.put("board1.txt", "10 x 20");
        mapList.put("board2.txt", "10 x 20");
        mapList.put("board3.txt", "10 x 20");
        mapList.put("board4.txt", "10 x 20");
        for (Map.Entry<String, String> stringStringEntry : mapList.entrySet()) {
            String map = stringStringEntry.getKey();
            String dimensions = stringStringEntry.getValue();
            JCheckBox checkBox = new JCheckBox(dimensions);
            checkBox.addActionListener(e -> selectedMap = map);
            mapCheckboxes.add(checkBox);
            mapSelectionPanel.add(checkBox);
        }

        newGameButton.addActionListener(e -> startGame());
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
