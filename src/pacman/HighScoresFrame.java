package pacman;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HighScoresFrame extends JFrame {

    public HighScoresFrame() {
        setTitle("High Scores");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel topPanel = createTopPanel();
        mainPanel.add(topPanel, BorderLayout.NORTH);

        JTable scoreTable = createScoreTableHeader();

        JScrollPane scrollPane = new JScrollPane(scoreTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        scrollPane.setBackground(Color.BLUE);
        add(mainPanel);
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(Color.BLACK);
        topPanel.setBorder(new LineBorder(Color.BLUE, 5));

        JButton goBackButton = new JButton("Go Back");
        goBackButton.setFont(new Font("PacFont", Font.PLAIN, 16));
        goBackButton.setBackground(Color.BLUE);
        goBackButton.setForeground(Color.WHITE);
        goBackButton.setFocusPainted(false);
        goBackButton.setOpaque(true);
        goBackButton.setBorderPainted(false);
        goBackButton.addActionListener(e -> goBack());

        topPanel.add(goBackButton);
        return topPanel;
    }

    private JTable createScoreTableHeader() {
        JTable scoreTable = createScoreTable();

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        scoreTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        scoreTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        scoreTable.getTableHeader().setReorderingAllowed(false);
        scoreTable.getTableHeader().setBorder(new LineBorder(Color.BLUE, 2));
        ((DefaultTableCellRenderer) scoreTable
                .getTableHeader()
                .getDefaultRenderer())
                .setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        return scoreTable;
    }

    private JTable createScoreTable() {
        JTable scoreTable = new JTable(loadAndSortScores());
        scoreTable.setFont(new Font("PacFont", Font.PLAIN, 18));
        scoreTable.setRowHeight(25);
        scoreTable.getTableHeader().setFont(new Font("PacFont", Font.BOLD, 20));
        scoreTable.getTableHeader().setBackground(Color.BLACK);
        scoreTable.getTableHeader().setForeground(Color.BLUE);
        scoreTable.setBackground(Color.BLACK);
        scoreTable.setForeground(Color.WHITE);
        scoreTable.setGridColor(Color.BLUE);
        return scoreTable;
    }

    private void goBack() {
        dispose();
        SwingUtilities.invokeLater(RunGame::new);
    }

    private DefaultTableModel loadAndSortScores() {
        String[] columnNames = {"Score", "Player"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        List<String[]> scores = new ArrayList<>();
        File file = new File("src/pacman/wyniki.txt");

        if (!file.exists()) {
            JOptionPane.showMessageDialog(this, "High scores file not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return model;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String name = line.substring(line.indexOf(";") + 1);
                String score = line.substring(0, line.indexOf(";"));
                scores.add(new String[]{score, name});
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading high scores: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        scores.sort(Comparator.comparingInt(this::extractScore).reversed());

        for (String[] score : scores) {
            model.addRow(score);
        }
        return model;
    }

    private int extractScore(String[] scoreArray) {
        try {
            return Integer.parseInt(scoreArray[0]);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

}
