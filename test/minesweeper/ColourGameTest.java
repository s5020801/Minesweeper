/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Josh
 */
public class ColourGameTest {

    public ColourGameTest() {
    }

    /**
     * Test of hasWon method, of class ColourGame.
     */
    @Test
    public void testHasWon() {
        Img.initImages();
        System.out.println("hasWon");
        ColourGame instance = new ColourGame(Difficulty.BEGINNER, 1234);
        instance.init(null);
        for (int i = 0; i < instance.boardSize; i++) {
            for (int j = 0; j < instance.boardSize; j++) {
                instance.board[i][j].setState(State.CLICKED);
            }
        }
        boolean expResult = false;
        boolean result = instance.hasWon();
        assertEquals(expResult, result);
    }

    /**
     * Test of hasLost method, of class ColourGame.
     */
    @Test
    public void testHasLost() {
        Img.initImages();
        System.out.println("hasLost");
        ColourGame instance = new ColourGame(Difficulty.BEGINNER, 1234);
        instance.init(null);
        for (int i = 0; i < instance.boardSize; i++) {
            for (int j = 0; j < instance.boardSize; j++) {
                instance.board[i][j].setState(State.CLICKED);
            }
        }
        boolean expResult = true;
        boolean result = instance.hasLost();
        assertEquals(expResult, result);
    }

}
