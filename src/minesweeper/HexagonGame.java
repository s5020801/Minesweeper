package minesweeper;

import java.awt.Dimension;
import java.awt.Insets;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class HexagonGame extends Game {

    public HexagonGame(Difficulty difficulty) {
        int size = Const.BEGINNER_BOARD_SIZE;
        int numberOfBombs = Const.BEGINNER_NUM_BOMBS;

        if (difficulty == Difficulty.INTERMEDIATE) {
            size = Const.INTERMEDIATE_BOARD_SIZE;
            numberOfBombs = Const.INTERMEDIATE_NUM_BOMBS;

        } else if (difficulty == Difficulty.EXPERT) {
            size = Const.EXPERT_BOARD_SIZE;
            numberOfBombs = Const.EXPERT_NUM_BOMBS;
        }

        this.board = new Cell[size][size];
        this.boardSize = size;
        this.numBombs = numberOfBombs;
        this.cellWidth = 36;//180;
        this.cellHeight = 40;//207;
        this.difficulty = difficulty;
        this.gameMode = GameMode.HEXAGON;

        this.seed = System.currentTimeMillis();
    }

    public HexagonGame(Difficulty difficulty, long seed) {
        int size = Const.BEGINNER_BOARD_SIZE;
        int numberOfBombs = Const.BEGINNER_NUM_BOMBS;

        if (difficulty == Difficulty.INTERMEDIATE) {
            size = Const.INTERMEDIATE_BOARD_SIZE;
            numberOfBombs = Const.INTERMEDIATE_NUM_BOMBS;

        } else if (difficulty == Difficulty.EXPERT) {
            size = Const.EXPERT_BOARD_SIZE;
            numberOfBombs = Const.EXPERT_NUM_BOMBS;
        }

        this.board = new Cell[size][size];
        this.boardSize = size;
        this.numBombs = numberOfBombs;
        this.cellWidth = 36;//180;
        this.cellHeight = 40;//207;
        this.difficulty = difficulty;
        this.gameMode = GameMode.HEXAGON;

        this.seed = seed;
    }

    void initCells(JPanel panel) {
        // Set up initial board cells.
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                board[i][j] = new Cell(new JButton(new ImageIcon(Img.unclickedImgHex)), State.UNCLICKED, false, i, j);

                board[i][j].getBtn().setPreferredSize(new Dimension(cellWidth, cellHeight));
                board[i][j].getBtn().setMargin(new Insets(0, 0, 0, 0));
                board[i][j].getBtn().setBorder(null);
                board[i][j].getBtn().setContentAreaFilled(false);
                board[i][j].getBtn().setBorderPainted(false);
                board[i][j].getBtn().setOpaque(false);

                int xOffset = 0;
                if (j % 2 == 1) {
                    xOffset = cellWidth / 2;
                }

                int yOffset = cellHeight / 4 * 3;//(hexHeight*0.75);

                board[i][j].getBtn().setBounds(board[i][j].getX() * cellWidth + 10 + xOffset, board[i][j].getY() * yOffset, cellWidth, cellHeight);

                Cell cell = board[i][j];

                // Set up mouse listener for each cell.
                setMouseListener(cell);

                if (panel != null) {
                    panel.add(board[i][j].getBtn());
                }
            }
        }
    }

    void setMouseListener(Cell cell) {
        cell.getBtn().addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {

                if (!gameStarted) {
                    gameStarted = true;
                    Minesweeper.startTimer();
                }
                if (gameOver) {
                    return;
                }

                if (evt.getButton() == 1) {

                    if (cell.isBomb()) {
                        cell.getBtn().setIcon(new ImageIcon(Img.bombRedImgHex));
                        cell.setState(State.RED_BOMB);
                        showBombs();
                        // Lost game.
                        gameOver = true;
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

                    cell.getBtn().setIcon(new ImageIcon(Img.hexNumBombsImgArr[numBombs]));
                } else if (evt.getButton() == 3) {
                    if (cell.getState() == State.FLAGGED) {
                        
                        cell.setState(State.UNCLICKED);
                        cell.getBtn().setIcon(new ImageIcon(Img.unclickedImgHex));
                    } else {
                        
                        cell.setState(State.FLAGGED);
                        cell.getBtn().setIcon(new ImageIcon(Img.flaggedImgHex));
                    }
                }
                if (hasWon()) {
                    gameOver = true;
                    System.out.println("Player won!!!");
                    String s = (String) JOptionPane.showInputDialog(
                            GUI.frame,
                            "Congrats, you won!\n"
                            + "Would you like to save to high scores?",
                            "",
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            null,
                            "Name");
                }

            }
        });
    }

    int countAdjacentBombs(int x, int y) {
        if (board == null || board.length == 0) {
            System.out.println("Board is broken.");
            return -100;
        }

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
            if (x + rowOffset > 0 && x + rowOffset < board.length && board[x + rowOffset][y - 1].isBomb()) {
                count++;
            }
            if (board[x][y - 1].isBomb()) {
                count++;
            }
        }
        if (y + 1 < board.length) {
            if (x + rowOffset > 0 && x + rowOffset < board.length && board[x + rowOffset][y + 1].isBomb()) {
                count++;
            }
            if (board[x][y + 1].isBomb()) {
                count++;
            }
        }

        return count;
    }

    void openCellsFrom(int x, int y) {

        if (x - 1 >= 0) {
            setOpenCells(x - 1, y);
        }

        if (x + 1 < board.length) {
            setOpenCells(x + 1, y);
        }

        int rowOffset = 1;
        if (y == 0 || y % 2 == 0) {
            System.out.println("Row is not offset., opening cells.");
            rowOffset = -1;
        }

        if (y - 1 >= 0) {
            if (x + rowOffset > 0 && x + rowOffset < board.length) {
                setOpenCells(x + rowOffset, y - 1);
            }

            setOpenCells(x, y - 1);
        }
        if (y + 1 < board.length) {
            if (x + rowOffset > 0 && x + rowOffset < board.length) {
                setOpenCells(x + rowOffset, y + 1);
            }

            setOpenCells(x, y + 1);
        }

    }

    void setOpenCells(int x, int y) {
        int adjBombs = countAdjacentBombs(x, y);
        if (adjBombs == 0 && board[x][y].getState() != State.CLICKED) {
            board[x][y].setState(State.CLICKED);
            board[x][y].getBtn().setIcon(new ImageIcon(Img.clickedImgHex));
            openCellsFrom(x, y);
        }
        board[x][y].setState(State.CLICKED);
        board[x][y].getBtn().setIcon(new ImageIcon(Img.hexNumBombsImgArr[adjBombs]));
    }

    void showBombs() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j].isBomb() && !board[i][j].getState().equals(State.RED_BOMB)) {
                    board[i][j].getBtn().setIcon(new ImageIcon(Img.bombImgHex));
                }
            }
        }
    }

}
