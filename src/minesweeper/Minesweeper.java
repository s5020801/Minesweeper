/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;

import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 *
 * @author Josh
 */
public class Minesweeper {

    public static void main(String[] args) {
        // Default
        int boardSize = 9;
        //Setting images
        Img.initImages();

        JPanel p = new JPanel();
        p.setLayout(null);
        //addBtns(p, 10, 0);
        //System.out.println("sfdsf: " +(int)(hexHeight*0.75)+2);
        //addBtns(p, (int)(hexHeight*0.75)+11, hexWidth/2);
        //addBtns(p, 10+hexHeight, 0);

        // Setting up game
        Game game = (Game)new HexagonGame(boardSize, 10);
        //Game game = (Game)new ClassicGame(boardSize, 30);
        game.init(p);
        
        JMenuBar mb = createMenuBar();

        JFrame f = new JFrame("Minesweeper");
        f.setJMenuBar(mb);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        f.add(p);
        f.setDefaultCloseOperation(3);
        System.out.println("Game size: " + game.getBoardSize() + ", numbombs: "+game.numBombs);
        //f.setSize(game.getBoardSize()*40+20, game.getBoardSize()*40+40+mb.getHeight());
        f.setSize(600, 600);
        f.setVisible(true);

        /*
      // Setting up frame
      JFrame frame = new JFrame("Minesweeper");
      frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
      JPanel panel = new JPanel(new GridLayout(9,9,0,0));
      
      // Setting up game
      Game game = new ClassicGame(boardSize, 10);
      game.init(panel);
      
      // Open frame
      frame.setContentPane(panel);
      frame.pack();
      frame.setVisible(true);
         */
    }

    private static JMenuBar createMenuBar() {

        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("Mode");

        JMenuItem classicMenuItem = new JMenuItem("Classic");
        JMenuItem hexagonMenuItem = new JMenuItem("Hexagon");
        classicMenuItem.addActionListener((event) -> System.out.println("Clicked Classic"));
        hexagonMenuItem.addActionListener((event) -> System.out.println("Clicked Hexagon"));

        fileMenu.add(classicMenuItem);
        fileMenu.add(hexagonMenuItem);
        menuBar.add(fileMenu);

        return menuBar;
    }

}
