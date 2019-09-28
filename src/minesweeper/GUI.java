/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 *
 * @author Josh
 */
public class GUI {
    public static JPanel panel;
    public static JFrame frame;
    public static int width;
    
    public static void init(){
        
        panel = new JPanel();
        panel.setLayout(null);

        // Setting up game
        //Minesweeper.game.init(panel);
        JMenuBar mb = Minesweeper.createMenuBar();

        frame = new JFrame("Minesweeper");
        frame.setJMenuBar(mb);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        panel.setBackground(Color.LIGHT_GRAY);
        frame.add(panel);
        frame.setDefaultCloseOperation(3);
        System.out.println("Game size: " + Minesweeper.game.getBoardSize() + ", numbombs: " + Minesweeper.game.numBombs);
        //f.setSize(game.getBoardSize()*40+20, game.getBoardSize()*40+40+mb.getHeight());
        //frame.setSize(400, 600);
        System.out.println("menu height: " + mb.getHeight());
        int width = Minesweeper.game.getBoardSize() * Minesweeper.game.getCellWidth() + 20;
        int height = Minesweeper.game.getBoardSize() * Minesweeper.game.getCellHeight() + 60;
        frame.setSize(width, height);
        frame.setVisible(true);
        
        
    }
    
    
    
}
