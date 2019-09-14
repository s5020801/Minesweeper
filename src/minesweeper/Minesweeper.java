/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;

import java.awt.MenuBar;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

/**
 *
 * @author Josh
 */
public class Minesweeper {
    
    public static void main(String[] args) {
       JPanel p = new JPanel();
       p.setLayout(null);
       //addBtns(p, 10, 0);
       //System.out.println("sfdsf: " +(int)(hexHeight*0.75)+2);
       //addBtns(p, (int)(hexHeight*0.75)+11, hexWidth/2);
       //addBtns(p, 10+hexHeight, 0);
       
       JMenuBar mb = createMenuBar();

       JFrame f = new JFrame();
       f.setJMenuBar(mb);
       
       f.add(p);
       f.setDefaultCloseOperation(3);
       f.setSize(400, 400);
       f.setVisible(true);
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
