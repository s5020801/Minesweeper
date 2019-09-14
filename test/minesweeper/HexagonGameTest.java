/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;

import javax.swing.JPanel;
import org.junit.AfterClass;
import org.junit.BeforeClass;
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
        HexagonGame instance = new HexagonGame(9, 10);
        instance.init(null);
        int expResult = 3;
        int result = instance.countAdjacentBombs(x, y);
        System.out.println("Expected: " + expResult + ", got: " + result);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
