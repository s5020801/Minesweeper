/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Josh
 */
public class GUI {
    public static JPanel panel;
    public static JFrame frame;
    public static JLabel timer = new JLabel("Score: 0");
    public static int width;
    
    public static void init(){
        
        panel = new JPanel();
        panel.setLayout(null);

        // Setting up game
        JMenuBar mb = Minesweeper.createMenuBar();
        timer.setBorder(new EmptyBorder(0,0,0,20));
        mb.add(timer);

        frame = new JFrame("Minesweeper");
        frame.setJMenuBar(mb);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        panel.setBackground(Color.LIGHT_GRAY);
        frame.add(panel);
        frame.setDefaultCloseOperation(3);
        int tempWidth = Minesweeper.game.getBoardSize() * Minesweeper.game.getCellWidth() + 20;
        int height = Minesweeper.game.getBoardSize() * Minesweeper.game.getCellHeight() + 60;
        frame.setSize(tempWidth, height);
        frame.setVisible(true);
    }
    
    public static void setTimerText(String text){
        timer.setText(text);
    }
    
    
}
