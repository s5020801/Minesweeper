package minesweeper;

import java.util.Random;
import javax.swing.JPanel;

public abstract class Game {

    protected Difficulty difficulty;
    protected GameMode gameMode;
    protected Cell[][] board;
    protected int boardSize;
    protected int numBombs;
    protected int cellWidth;
    protected int cellHeight;
    protected boolean gameOver = false;
    protected boolean gameStarted = false;
    
    //Used for testing. can be used in future for competing in the same game.
    long seed;
    

    void init(JPanel panel) {
        initCells(panel);
        initBombs();
    }

    void initBombs() {
        int bombCount = 0;
        //Random random = new Random(1234);
        //Random random = new Random(System.currentTimeMillis());
        Random random = new Random(seed);
        
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

    public boolean hasWon(){
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length; j++){
                //if(!board[i][j].isBomb() && board[i][j].getState() == State.UNCLICKED)
                if(board[i][j].getState() == State.UNCLICKED)
                    return false;
            }
        }
        return true;
    }
    
    abstract void initCells(JPanel panel);

    abstract void setMouseListener(Cell cell);

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
