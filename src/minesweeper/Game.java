package minesweeper;

import java.awt.Dimension;
import java.awt.Insets;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public abstract class Game {

    Difficulty difficulty;
    GameMode gameMode;
    Cell[][] board;
    int boardSize;
    int numBombs;
    int cellWidth;
    int cellHeight;
    boolean gameOver = false;
    boolean gameStarted = false;
    

    void init(JPanel panel) {
        initCells(panel);
        initBombs();
    }

    abstract void initCells(JPanel panel);

    abstract void setMouseListener(Cell cell);

    void initBombs() {
        int bombCount = 0;
        //Random random = new Random(1234);
        Random random = new Random(System.currentTimeMillis());
        while (bombCount < numBombs) {
            int randX = random.nextInt(boardSize - 1);
            int randY = random.nextInt(boardSize - 1);

            if (board[randX][randY].isBomb()) {
                continue;
            }

            board[randX][randY].setBomb(true);
            bombCount++;
        }
    }

    abstract int countAdjacentBombs(int x, int y);

    abstract void openCellsFrom(int x, int y);

    abstract void showBombs();

    public int getBoardSize() {
        return boardSize;
    }

    public int getCellWidth() {
        return cellWidth;
    }

    public int getCellHeight() {
        return cellHeight;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }
    
}
