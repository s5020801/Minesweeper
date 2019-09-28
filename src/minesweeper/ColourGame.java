package minesweeper;

import java.awt.Dimension;
import java.awt.Insets;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

public class ColourGame extends Game {

    private Colour nextXCol = Colour.RED;
    private Colour nextYCol = Colour.RED;
    
    public ColourGame(Difficulty difficulty) {
        
        
        int size = 6;
        int numberOfBombs = 4;

        if (difficulty == Difficulty.INTERMEDIATE) {
            size = 9;
            numberOfBombs = 8;

        } else if (difficulty == Difficulty.EXPERT) {
            size = 15;
            numberOfBombs = 16;
        }

        this.board = new Cell[size][size];
        this.boardSize = size;
        this.numBombs = numberOfBombs;
        this.cellWidth = 40;
        this.cellHeight = 40;
        this.difficulty = difficulty;
        this.gameMode = GameMode.COLOUR;

        this.seed = System.currentTimeMillis();
        //this.seed = 1234;
    }

    public ColourGame(Difficulty difficulty, long seed) {
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
        this.gameMode = GameMode.COLOUR;

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

                int offset = (GUI.width/2) - ((boardSize * cellWidth) / 2);
                board[i][j].getBtn().setBounds(board[i][j].getX() * cellWidth + offset, board[i][j].getY() * cellWidth, cellWidth, cellHeight);

                Cell cell = board[i][j];
                if(j == 0){
                    nextXCol = getNextYColour();
                }
                cell.setColour(getNextXColour());
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
                    
                    if(cell.getColour() == Colour.RED){
                        cell.getBtn().setIcon(new ImageIcon(Img.redCellImg));
                        cell.setState(State.CLICKED);
                    }
                    if(cell.getColour() == Colour.BLUE){
                        cell.getBtn().setIcon(new ImageIcon(Img.blueCellImg));
                        cell.setState(State.CLICKED);
                    }
                    if(cell.getColour() == Colour.YELLOW){
                        cell.getBtn().setIcon(new ImageIcon(Img.yellowCellImg));
                        cell.setState(State.CLICKED);
                    }
                    
                    if(hasLost()){
                        gameOver = true;
                    }
                    
                } else if (evt.getButton() == 3) {
                    cell.setState(State.FLAGGED);
                    cell.getBtn().setIcon(new ImageIcon(Img.flaggedImgClassic));
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
    
    private boolean hasLost() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j].getState() == State.CLICKED) {
                    Colour cellCol = board[i][j].getColour();
                    //check left cell
                    if(i-1 > 0 && board[i-1][j].getColour() == cellCol && board[i-1][j].getState() == State.CLICKED)
                        return true;
                    //check top cell
                    if(j-1 > 0 && board[i][j-1].getColour() == cellCol && board[i][j-1].getState() == State.CLICKED)
                        return true;
                    //check right cell
                    if(i+1 < boardSize && board[i+1][j].getColour() == cellCol && board[i+1][j].getState() == State.CLICKED)
                        return true;
                    //check bottom cell
                    if(j+1 < boardSize && board[i][j+1].getColour() == cellCol && board[i][j+1].getState() == State.CLICKED)
                        return true;
                    
                    //board[i][j].getBtn().setIcon(new ImageIcon(Img.bombImgClassic));
                }
            }
        }
        System.out.println("Not Lost");
        return false;
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
    
    Colour getNextXColour(){
        Colour colour = nextXCol;
        if(nextXCol == Colour.RED){
            nextXCol = Colour.YELLOW;
        } else if(nextXCol == Colour.YELLOW){
            nextXCol = Colour.BLUE;
        } else if(nextXCol == Colour.BLUE){
            nextXCol = Colour.RED;
        }
        return colour;
    }
    
    Colour getNextYColour(){
        Colour colour = nextYCol;
        if(nextYCol == Colour.RED){
            nextYCol = Colour.BLUE;
        } else if(nextYCol == Colour.YELLOW){
            nextYCol = Colour.RED;
        } else if(nextYCol == Colour.BLUE){
            nextYCol = Colour.YELLOW;
        }
        return colour;
    }

    
    @Override
    void initBombs() {
        System.out.println("OVERRIDING");
        int bombCount = 0;
        //Random random = new Random(1234);
        //Random random = new Random(System.currentTimeMillis());
        Random random = new Random(seed);
        
        while (bombCount < numBombs) {
            int randX = random.nextInt(boardSize - 1);
            int randY = random.nextInt(boardSize - 1);

            board[randX][randY].setColour(getDifferentColour(board[randX][randY].getColour(), random));

            bombCount++;
        }
    }
    
    Colour getDifferentColour(Colour oldColour, Random random){
        boolean randBool = random.nextBoolean();
        if(oldColour == Colour.RED){
            if(randBool)
                return Colour.BLUE;
            return Colour.YELLOW;
        }
        
        if(oldColour == Colour.BLUE){
            if(randBool)
                return Colour.RED;
            return Colour.YELLOW;
        }
        
        if(oldColour == Colour.YELLOW){
            if(randBool)
                return Colour.BLUE;
            return Colour.RED;
        }
        
        return Colour.NONE;
    }
    
}
