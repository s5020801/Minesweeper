package minesweeper;
import java.awt.Dimension;
import java.awt.Insets;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ClassicGame extends Game {
   
    public ClassicGame(int size, int numBombs) {
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
         for(int i = 0; i < boardSize; i++) {
            for(int j = 0; j < boardSize; j++) {
               board[i][j] = new Cell(new JButton(new ImageIcon(Img.unclickedImgClassic)) , State.UNCLICKED, false, i, j);
               
               board[i][j].getBtn().setPreferredSize(new Dimension(40, 40));
               board[i][j].getBtn().setMargin(new Insets(0, 0, 0, 0));
               board[i][j].getBtn().setBorder(null);
               board[i][j].getBtn().setContentAreaFilled(false);
               board[i][j].getBtn().setBorderPainted(false);
               board[i][j].getBtn().setOpaque(false);
               
               board[i][j].getBtn().setBounds(board[i][j].getX()*40+10, board[i][j].getY()*40, 40, 40);
               
               Cell cell = board[i][j];
               
               // Set up mouse listener for each cell.
               setMouseListener(cell);
               
               panel.add(board[i][j].getBtn());
            }
         }
      }
   
   void setMouseListener(Cell cell) {
      cell.getBtn().addMouseListener(new java.awt.event.MouseAdapter() {
         public void mouseClicked(java.awt.event.MouseEvent evt) {
            
            if(cell.isBomb()) {
               cell.getBtn().setIcon(new ImageIcon(Img.bombRedImgClassic));
               cell.setState(State.RED_BOMB);
               showBombs();
               // Lost game.
               return;
            }
            
            int numBombs = countAdjacentBombs(cell.getX(), cell.getY());
            if(numBombs == 0) {
               cell.setState(State.CLICKED);
               cell.getBtn().setIcon(new ImageIcon(Img.clickedImgClassic));
               openCellsFrom(cell.getX(), cell.getY());
               return;
            }
            
            cell.setState(State.CLICKED);
            cell.getBtn().setIcon(new ImageIcon(Img.classicNumBombsImgArr[numBombs]));
            
         }
      });
   }
   
   void initBombs() {
      int bombCount = 0;
      while(bombCount < numBombs) {
         int randX = ThreadLocalRandom.current().nextInt(0, boardSize-1);
         int randY = ThreadLocalRandom.current().nextInt(0, boardSize-1);
         
         if(board[randX][randY].isBomb())
            continue;
         
         board[randX][randY].setBomb(true);
         bombCount++;
      }
   }
   

   
   int countAdjacentBombs(int x, int y) {
      int count = 0;
      for(int i = x-1; i <= x+1; i++) {
         for(int j = y-1; j <= y+1; j++) {
            
            if(i > 8 || j > 8 || i < 0 || j < 0)
               continue;
            
            if(board[i][j].isBomb())
               count++;
            
         }
      }
      return count;
   }
   
   void openCellsFrom(int x, int y) {
      for(int i = x-1; i <= x+1; i++) {
         for(int j = y-1; j <= y+1; j++) {
            if(i > 8 || j > 8 || i < 0 || j < 0)
               continue;
            int adjBombs = countAdjacentBombs(i, j);
            if(adjBombs == 0 && board[i][j].getState() != State.CLICKED) {
               board[i][j].setState(State.CLICKED);
               board[i][j].getBtn().setIcon(new ImageIcon(Img.clickedImgClassic));
               openCellsFrom(i,j);
            }
            board[i][j].setState(State.CLICKED);
            board[i][j].getBtn().setIcon(new ImageIcon(Img.classicNumBombsImgArr[adjBombs]));
            
         }
      }
   }
   
   void showBombs() {
      for(int i = 0; i < 9; i++) {
         for(int j = 0; j < 9; j++) {
            if(board[i][j].isBomb() && !board[i][j].getState().equals(State.RED_BOMB))
               board[i][j].getBtn().setIcon(new ImageIcon(Img.bombImgClassic));
         }
      }
   }
   
}
