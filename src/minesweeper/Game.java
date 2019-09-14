package minesweeper;

import java.awt.Dimension;
import java.awt.Insets;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public abstract class Game {

    Cell[][] board;
    int boardSize;
    int numBombs;

    void init(JPanel panel) {
        initCells(panel);
        initBombs();
    }

    abstract void initCells(JPanel panel);

    abstract void setMouseListener(Cell cell);

    abstract void initBombs();

    abstract int countAdjacentBombs(int x, int y);

    abstract void openCellsFrom(int x, int y);

    abstract void showBombs();

    public int getBoardSize() {
        return boardSize;
    }
}
