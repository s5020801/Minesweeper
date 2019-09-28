package minesweeper;

import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

public class ClassicGame extends Game {

    public ClassicGame(Difficulty difficulty) {
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
        this.cellWidth = 40;
        this.cellHeight = 40;
        this.difficulty = difficulty;
        this.gameMode = GameMode.CLASSIC;

        this.seed = System.currentTimeMillis();
        this.seed = 1234;
    }

    public ClassicGame(Difficulty difficulty, long seed) {
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
        this.cellWidth = 40;
        this.cellHeight = 40;
        this.difficulty = difficulty;
        this.gameMode = GameMode.CLASSIC;

        this.seed = seed;
    }

    void initCells(JPanel panel) {
        // Set up initial board cells.
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                board[i][j] = new Cell(new JButton(new ImageIcon(Img.unclickedImgClassic)), State.UNCLICKED, false, i, j);

                board[i][j].getBtn().setPreferredSize(new Dimension(40, 40));
                board[i][j].getBtn().setMargin(new Insets(0, 0, 0, 0));
                board[i][j].getBtn().setBorder(null);
                board[i][j].getBtn().setContentAreaFilled(false);
                board[i][j].getBtn().setBorderPainted(false);
                board[i][j].getBtn().setOpaque(false);

                board[i][j].getBtn().setBounds(board[i][j].getX() * 40 + 10, board[i][j].getY() * 40, 40, 40);

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
                        cell.getBtn().setIcon(new ImageIcon(Img.bombRedImgClassic));
                        cell.setState(State.RED_BOMB);
                        showBombs();
                        // Lost game.
                        gameOver = true;
                        return;
                    }

                    int numBombs = countAdjacentBombs(cell.getX(), cell.getY());
                    if (numBombs == 0) {
                        cell.setState(State.CLICKED);
                        cell.getBtn().setIcon(new ImageIcon(Img.clickedImgClassic));
                        openCellsFrom(cell.getX(), cell.getY());
                        return;
                    }

                    cell.setState(State.CLICKED);
                    cell.getBtn().setIcon(new ImageIcon(Img.classicNumBombsImgArr[numBombs]));
                } else if (evt.getButton() == 3) {
                    if (cell.getState() == State.FLAGGED) {

                        cell.setState(State.UNCLICKED);
                        cell.getBtn().setIcon(new ImageIcon(Img.unclickedImgClassic));
                    } else {

                        cell.setState(State.FLAGGED);
                        cell.getBtn().setIcon(new ImageIcon(Img.flaggedImgClassic));
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
                    System.out.println("S: " + s);
                    HighScores.add(s);
                }
            }
        });
    }

    int countAdjacentBombs(int x, int y) {
        int count = 0;
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {

                if (i >= boardSize || j >= boardSize || i < 0 || j < 0) {
                    continue;
                }

                if (board[i][j].isBomb()) {
                    count++;
                }

            }
        }
        return count;
    }

    void openCellsFrom(int x, int y) {
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i >= boardSize || j >= boardSize || i < 0 || j < 0) {
                    continue;
                }
                int adjBombs = countAdjacentBombs(i, j);
                if (adjBombs == 0 && board[i][j].getState() != State.CLICKED) {
                    board[i][j].setState(State.CLICKED);
                    board[i][j].getBtn().setIcon(new ImageIcon(Img.clickedImgClassic));
                    openCellsFrom(i, j);
                }
                board[i][j].setState(State.CLICKED);
                board[i][j].getBtn().setIcon(new ImageIcon(Img.classicNumBombsImgArr[adjBombs]));

            }
        }
    }

    void showBombs() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j].isBomb() && !board[i][j].getState().equals(State.RED_BOMB)) {
                    board[i][j].getBtn().setIcon(new ImageIcon(Img.bombImgClassic));
                }
            }
        }
    }

}
