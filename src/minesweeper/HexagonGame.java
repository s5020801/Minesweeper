package minesweeper;

import java.awt.Dimension;
import java.awt.Insets;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class HexagonGame extends Game {

    int hexWidth = 36;//180;
    int hexHeight = 40;//207;

    public HexagonGame(int size, int numBombs) {
        this.board = new Cell[size][size];
        this.boardSize = size;
        this.numBombs = numBombs;
    }

    void init(JPanel panel) {
        initCells(panel);
        initBombs();
    }

    void initCells(JPanel panel) {
        // Set up initial board cells.
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                board[i][j] = new Cell(new JButton(new ImageIcon(Img.unclickedImgHex)), State.UNCLICKED, false, i, j);

                board[i][j].getBtn().setPreferredSize(new Dimension(hexWidth, hexHeight));
                board[i][j].getBtn().setMargin(new Insets(0, 0, 0, 0));
                board[i][j].getBtn().setBorder(null);
                board[i][j].getBtn().setContentAreaFilled(false);
                board[i][j].getBtn().setBorderPainted(false);
                board[i][j].getBtn().setOpaque(false);

                int xOffset = 0;
                if (j % 2 == 1) {
                    xOffset = hexWidth / 2;
                }

                int yOffset = hexHeight / 4 * 3;//(hexHeight*0.75);

                board[i][j].getBtn().setBounds(board[i][j].getX() * hexWidth + 10 + xOffset, board[i][j].getY() * yOffset, hexWidth, hexHeight);

                Cell cell = board[i][j];

                // Set up mouse listener for each cell.
                setMouseListener(cell);

                if(panel != null)
                    panel.add(board[i][j].getBtn());
            }
        }
    }

    void setMouseListener(Cell cell) {
        cell.getBtn().addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {

                if (cell.isBomb()) {
                    cell.getBtn().setIcon(new ImageIcon(Img.bombRedImgHex));
                    cell.setState(State.RED_BOMB);
                    showBombs();
                    // Lost game.
                    return;
                }

                int numBombs = countAdjacentBombs(cell.getX(), cell.getY());
                if (numBombs == 0) {
                    cell.setState(State.CLICKED);
                    cell.getBtn().setIcon(new ImageIcon(Img.clickedImgHex));
                    openCellsFrom(cell.getX(), cell.getY());
                    return;
                }

                cell.setState(State.CLICKED);

                if (numBombs >= Img.hexNumBombsImgArr.length) {
                    System.out.println("adj Bombs: " + numBombs + ", img length: " + Img.hexNumBombsImgArr.length);
                }
                cell.getBtn().setIcon(new ImageIcon(Img.hexNumBombsImgArr[numBombs]));

            }
        });
    }

    void initBombs() {
        int bombCount = 0;
        Random random = new Random(1234);
        while (bombCount < numBombs) {
            //int randX = ThreadLocalRandom.current().nextInt(0, boardSize - 1);
            //int randY = ThreadLocalRandom.current().nextInt(0, boardSize - 1);
            int randX = random.nextInt(boardSize - 1);
            int randY = random.nextInt(boardSize - 1);

            if (board[randX][randY].isBomb()) {
                continue;
            }

            board[randX][randY].setBomb(true);
            bombCount++;
        }
    }

    int countAdjacentBombs(int x, int y) {
        if(board == null || board.length == 0){ System.out.println("Board is broken."); return -100;}
        
        System.out.println("X: " + x + ", Y: " + y + ", board length: " + board.length);

        int count = 0;
        if (x > 0 && board[x - 1][y].isBomb()) {
            System.out.println("Found bomb left of click");
            count++;
        }
        if (x + 1 < board.length && board[x + 1][y].isBomb()) {
            System.out.println("Found bomb right of click");
            count++;
        }

        int rowOffset = 1;
        if (y == 0 || y % 2 == 0) {
            System.out.println("Row is not offset.");
            rowOffset = -1;
        }

        if (y - 1 >= 0) {
            if (x+rowOffset > 0 && x+rowOffset < board.length && board[x+rowOffset][y-1].isBomb()) {
                count++;
            }
            if (board[x][y-1].isBomb()) {
                count++;
            }
        }
        if (y + 1 < board.length) {
            if (x+rowOffset > 0 && x+rowOffset < board.length && board[x+rowOffset][y+1].isBomb()) {
                count++;
            }
            if (board[x][y+1].isBomb()) {
                count++;
            }
        }

        return count;
    }

    void openCellsFrom(int x, int y) {
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i > 8 || j > 8 || i < 0 || j < 0) {
                    continue;
                }
                int adjBombs = countAdjacentBombs(i, j);
                if (adjBombs == 0 && board[i][j].getState() != State.CLICKED) {
                    board[i][j].setState(State.CLICKED);
                    board[i][j].getBtn().setIcon(new ImageIcon(Img.clickedImgHex));
                    openCellsFrom(i, j);
                }
                board[i][j].setState(State.CLICKED);
                if (adjBombs >= Img.hexNumBombsImgArr.length) {
                    System.out.println("adj Bombs: " + adjBombs + ", img length: " + Img.hexNumBombsImgArr.length);
                }
                board[i][j].getBtn().setIcon(new ImageIcon(Img.hexNumBombsImgArr[adjBombs]));

            }
        }
    }

    void showBombs() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j].isBomb() && !board[i][j].getState().equals(State.RED_BOMB)) {
                    board[i][j].getBtn().setIcon(new ImageIcon(Img.bombImgHex));
                }
            }
        }
    }

}
