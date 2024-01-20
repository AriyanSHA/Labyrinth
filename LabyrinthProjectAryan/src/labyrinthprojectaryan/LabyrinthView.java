package labyrinthprojectaryan;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LabyrinthView extends JFrame {
    private static final int CELL_SIZE = 20;

    private LabyrinthModel model;
    private boolean nameObtained = false;

    public LabyrinthView() {
        setTitle("Labyrinth Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        model = new LabyrinthModel();

        if (!nameObtained) {
            obtainPlayerName();
            nameObtained = true;
        }

        addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                model.movePlayer(evt.getKeyCode());
                model.checkCollision();
                repaint();
            }
        });

        Timer timer = new Timer(1000, (ActionEvent evt) -> {
            model.elapsedTimeInSeconds++;
            model.moveDragon();
            model.checkCollision();
            repaint();
        });
        timer.start();

        JMenu menu = new JMenu("Game");
        JMenuItem leaderboardItem = new JMenuItem("Display Leaderboard");
        leaderboardItem.addActionListener((ActionEvent evt) -> {
            displayLeaderboard();
        } //Anonymous inner class to Lambda
        );
        menu.add(leaderboardItem);

        JMenuItem newGameItem = new JMenuItem("New Game");
        newGameItem.addActionListener((ActionEvent evt) -> {
            startNewGame();
        });
        menu.add(newGameItem);

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener((ActionEvent evt) -> {
            System.exit(0);
        });
        menu.add(exitItem);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(menu);
        setJMenuBar(menuBar);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawGrid(g);
                drawObstacles(g);
                drawPlayer(g);
                drawDragon(g);
                drawElapsedTime(g);
                drawExit(g);
                g.setColor(Color.BLACK);
                g.drawString(model.playerName + "'s Wins: " + model.getWinCount(), 5, getSize().height - 585);
            }
        };

        add(panel);
        int dynamicWidth = (LabyrinthModel.getSIZE() + 1) * CELL_SIZE;
        int dynamicHeight = (LabyrinthModel.getSIZE() + 3) * CELL_SIZE;
        setPreferredSize(new Dimension(dynamicWidth, dynamicHeight));
        pack();
        setLocationRelativeTo(null);
    }

    private void displayLeaderboard() {
        LeaderboardManager.displayLeaderboard();
    }

    private void obtainPlayerName() {
        model.playerName = JOptionPane.showInputDialog(this, "Enter your name:");
        if (model.playerName == null || model.playerName.trim().isEmpty()) {
            model.playerName = "Player";
        }
    }

    private void startNewGame() {
        model = new LabyrinthModel();
        obtainPlayerName();
        repaint();
    }

    private void drawElapsedTime(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawString("Time: " + model.getElapsedTimeInSeconds() + " seconds", 500, getSize().height - 75);
    }

    private void drawGrid(Graphics g) {
        int startCol = Math.max(0, model.getPlayerX() - 1);
        int endCol = Math.min(LabyrinthModel.getSIZE() - 1, model.getPlayerX() + 1);

        int startRow = Math.max(0, model.getPlayerY() - 1);
        int endRow = Math.min(LabyrinthModel.getSIZE() - 1, model.getPlayerY() + 1);

        for (int i = startCol; i <= endCol; i++) {
            for (int j = startRow; j <= endRow; j++) {
                int x = i * CELL_SIZE;
                int y = j * CELL_SIZE;
                g.setColor(Color.LIGHT_GRAY);
                g.drawRect(x, y, CELL_SIZE, CELL_SIZE);
            }
        }
    }

    private void drawObstacles(Graphics g) {
        int startCol = Math.max(0, model.getPlayerX() - 1);
        int endCol = Math.min(LabyrinthModel.getSIZE() - 1, model.getPlayerX() + 1);

        int startRow = Math.max(0, model.getPlayerY() - 1);
        int endRow = Math.min(LabyrinthModel.getSIZE() - 1, model.getPlayerY() + 1);

        g.setColor(Color.pink);
        for (int i = startCol; i <= endCol; i++) {
            for (int j = startRow; j <= endRow; j++) {
                if (model.getObstacles()[i][j]) {
                    int x = i * CELL_SIZE;
                    int y = j * CELL_SIZE;
                    g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                }
            }
        }
    }

    private void drawPlayer(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(model.getPlayerX() * CELL_SIZE, model.getPlayerY() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
    }

    private void drawDragon(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(model.getDragonX() * CELL_SIZE, model.getDragonY() * CELL_SIZE,
                CELL_SIZE * LabyrinthModel.getDRAGON_SIZE(), CELL_SIZE * LabyrinthModel.getDRAGON_SIZE());
    }

    private void drawExit(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect((LabyrinthModel.getSIZE() - 1) * CELL_SIZE, 0, CELL_SIZE, CELL_SIZE);
    }
}
