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
public class HexagonGameTest {
    
    public HexagonGameTest() {
    }

    /**
     * Test of countAdjacentBombs method, of class HexagonGame.
     */
    @Test
    public void testCountAdjacentBombs() {
        Img.initImages();
        System.out.println("countAdjacentBombs");
        int x = 1;
        int y = 1;
        HexagonGame instance = new HexagonGame(Difficulty.BEGINNER, 1234);
        instance.init(null);
        int expResult = 4;
        int result = instance.countAdjacentBombs(x, y);
        System.out.println("Expected: " + expResult + ", got: " + result);
        assertEquals(expResult, result);
    }
    
}
