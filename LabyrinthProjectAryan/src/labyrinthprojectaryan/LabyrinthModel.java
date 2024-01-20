package labyrinthprojectaryan;
import java.awt.event.KeyEvent;
import java.util.Random;

public class LabyrinthModel {
    private static final int SIZE = 30;
    private static final int DRAGON_SIZE = 5;

    
    private int playerX, playerY;
    private int dragonX, dragonY;
    private boolean[][] obstacles;
    private int winCount = 0;
    public int elapsedTimeInSeconds;
    public String playerName;

    public LabyrinthModel() {
        initializeGame();
    }

    public static int getSIZE() {
        return SIZE;
    }

    public static int getDRAGON_SIZE() {
        return DRAGON_SIZE;
    }
    
    public int getPlayerX() {
        return playerX;
    }

    public int getPlayerY() {
        return playerY;
    }

    public int getDragonX() {
        return dragonX;
    }

    public int getDragonY() {
        return dragonY;
    }

    public boolean[][] getObstacles() {
        return obstacles;
    }

    public int getWinCount() {
        return winCount;
    }

    public int getElapsedTimeInSeconds() {
        return elapsedTimeInSeconds;
    }

    public void movePlayer(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_LEFT -> {
                if (playerX > 0 && !obstacles[playerX - 1][playerY]) playerX--;
            }
            case KeyEvent.VK_RIGHT -> {
                if (playerX < SIZE - 1 && !obstacles[playerX + 1][playerY]) playerX++;
            }
            case KeyEvent.VK_UP -> {
                if (playerY > 0 && !obstacles[playerX][playerY - 1]) playerY--;
            }
            case KeyEvent.VK_DOWN -> {
                if (playerY < SIZE - 1 && !obstacles[playerX][playerY + 1]) playerY++;
            }
        }
    }

    public void moveDragon() {
        Random random = new Random();
        int direction = random.nextInt(4);

        switch (direction) {
            case 0 -> {
                if (dragonX > 0 && !obstacles[dragonX - 1][dragonY]) dragonX--;
            }
            case 1 -> {
                if (dragonX < SIZE - DRAGON_SIZE && !obstacles[dragonX + 1][dragonY]) dragonX++;
            }
            case 2 -> {
                if (dragonY > 0 && !obstacles[dragonX][dragonY - 1]) dragonY--;
            }
            case 3 -> {
                if (dragonY < SIZE - DRAGON_SIZE && !obstacles[dragonX][dragonY + 1]) dragonY++;
            }
        }
    }

    public void checkCollision() {
        int playerRight = playerX + 1;
        int playerBottom = playerY + 1;
        int dragonRight = dragonX + DRAGON_SIZE;
        int dragonBottom = dragonY + DRAGON_SIZE;

        if (playerX < dragonRight && playerRight > dragonX && playerY < dragonBottom && playerBottom > dragonY) {
            LeaderboardManager.addToLeaderboard(playerName, winCount);
            initializeGame();
            elapsedTimeInSeconds = 0;
            winCount = 0;

        } else if (playerX == SIZE - 1 && playerY == 0) {
            initializeGame();
            elapsedTimeInSeconds = 0;
            winCount++;
        }
    }

    private void initializeGame() {
        playerX = 0;
        playerY = SIZE - 1;

        Random random = new Random();
        dragonX = random.nextInt(SIZE);
        dragonY = random.nextInt(SIZE);

        generateObstacles();
        elapsedTimeInSeconds = 0;
    }

    private void generateObstacles() {
        obstacles = new boolean[SIZE][SIZE];
        Random random = new Random();

        for (int i = 0; i < SIZE * 8; i++) {
            int obstacleX = random.nextInt(SIZE);
            int obstacleY = random.nextInt(SIZE);

            if (!(obstacleX >= SIZE - 4 && obstacleY < 4) &&
                    !(obstacleX < 4 && obstacleY >= SIZE - 4) &&
                    !(obstacleX == SIZE - 1 && obstacleY == 0) &&
                    !(obstacleX == playerX && obstacleY == playerY)) {
                obstacles[obstacleX][obstacleY] = true;
            }
        }
    }
}

