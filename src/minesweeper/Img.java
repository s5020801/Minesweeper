package minesweeper;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Img {

    //Classic
    public static Image bombImgClassic;
    public static Image bombRedImgClassic;
    public static Image unclickedImgClassic;
    public static Image clickedImgClassic;

    public static Image oneBombClassic;
    public static Image twoBombClassic;
    public static Image threeBombClassic;
    public static Image fourBombClassic;
    public static Image fiveBombClassic;
    public static Image sixBombClassic;
    public static Image sevenBombClassic;
    public static Image eightBombClassic;

    public static Image[] classicNumBombsImgArr;

    
    //Hexagon
    static int hexWidth = 36;//180;
    static int hexHeight = 40;//207;
    public static Image bombImgHex;
    public static Image bombRedImgHex;
    public static Image unclickedImgHex;
    public static Image clickedImgHex;

    public static Image oneBombHex;
    public static Image twoBombHex;
    public static Image threeBombHex;
    public static Image fourBombHex;
    public static Image fiveBombHex;
    public static Image sixBombHex;

    public static Image[] hexNumBombsImgArr;

    public static void initImages() {
        try {
            //Classic
            unclickedImgClassic = ImageIO.read(Minesweeper.class.getResource("/resources/classic/facingDown.png")).getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
            clickedImgClassic = ImageIO.read(Minesweeper.class.getResource("/resources/classic/0.png")).getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
            bombImgClassic = ImageIO.read(Minesweeper.class.getResource("/resources/classic/bomb.png")).getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
            bombRedImgClassic = ImageIO.read(Minesweeper.class.getResource("/resources/classic/bombRed.png")).getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);

            oneBombClassic = ImageIO.read(Minesweeper.class.getResource("/resources/classic/1.png")).getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
            twoBombClassic = ImageIO.read(Minesweeper.class.getResource("/resources/classic/2.png")).getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
            threeBombClassic = ImageIO.read(Minesweeper.class.getResource("/resources/classic/3.png")).getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
            fourBombClassic = ImageIO.read(Minesweeper.class.getResource("/resources/classic/4.png")).getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
            fiveBombClassic = ImageIO.read(Minesweeper.class.getResource("/resources/classic/5.png")).getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
            sixBombClassic = ImageIO.read(Minesweeper.class.getResource("/resources/classic/6.png")).getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
            sevenBombClassic = ImageIO.read(Minesweeper.class.getResource("/resources/classic/7.png")).getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
            eightBombClassic = ImageIO.read(Minesweeper.class.getResource("/resources/classic/8.png")).getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);

            classicNumBombsImgArr = new Image[]{
                clickedImgClassic,
                oneBombClassic,
                twoBombClassic,
                threeBombClassic,
                fourBombClassic,
                fiveBombClassic,
                sixBombClassic,
                sevenBombClassic,
                eightBombClassic
            };

            //Hexagon
            unclickedImgHex = ImageIO.read(Minesweeper.class.getResource("/resources/hexagon/UnclickedHexagon.png")).getScaledInstance(hexWidth, hexHeight, java.awt.Image.SCALE_SMOOTH);
            clickedImgHex = ImageIO.read(Minesweeper.class.getResource("/resources/hexagon/0.png")).getScaledInstance(hexWidth, hexHeight, java.awt.Image.SCALE_SMOOTH);
            bombImgHex = ImageIO.read(Minesweeper.class.getResource("/resources/hexagon/BombHexagon.png")).getScaledInstance(hexWidth, hexHeight, java.awt.Image.SCALE_SMOOTH);
            bombRedImgHex = ImageIO.read(Minesweeper.class.getResource("/resources/hexagon/BombRedHexagon.png")).getScaledInstance(hexWidth, hexHeight, java.awt.Image.SCALE_SMOOTH);

            oneBombHex = ImageIO.read(Minesweeper.class.getResource("/resources/hexagon/1.png")).getScaledInstance(hexWidth, hexHeight, java.awt.Image.SCALE_SMOOTH);
            twoBombHex = ImageIO.read(Minesweeper.class.getResource("/resources/hexagon/2.png")).getScaledInstance(hexWidth, hexHeight, java.awt.Image.SCALE_SMOOTH);
            threeBombHex = ImageIO.read(Minesweeper.class.getResource("/resources/hexagon/3.png")).getScaledInstance(hexWidth, hexHeight, java.awt.Image.SCALE_SMOOTH);
            fourBombHex = ImageIO.read(Minesweeper.class.getResource("/resources/hexagon/4.png")).getScaledInstance(hexWidth, hexHeight, java.awt.Image.SCALE_SMOOTH);
            fiveBombHex = ImageIO.read(Minesweeper.class.getResource("/resources/hexagon/5.png")).getScaledInstance(hexWidth, hexHeight, java.awt.Image.SCALE_SMOOTH);
            sixBombHex = ImageIO.read(Minesweeper.class.getResource("/resources/hexagon/6.png")).getScaledInstance(hexWidth, hexHeight, java.awt.Image.SCALE_SMOOTH);
            
            hexNumBombsImgArr = new Image[]{
                clickedImgHex,
                oneBombHex,
                twoBombHex,
                threeBombHex,
                fourBombHex,
                fiveBombHex,
                sixBombHex
            };
            
            
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Couldn't load images!");
        }
    }
}
