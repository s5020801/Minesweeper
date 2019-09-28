package minesweeper;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Josh
 */
public class Minesweeper {

    //The Beginner (8x8) and Intermediate (16x16)
    //Beginner has 10 mines, Intermediate has 40 mines, and Expert has 99 mines.
    // Default
    public static Game game = (Game) new ClassicGame(Difficulty.BEGINNER);
    public static JLabel timer = new JLabel("Score: 0");
    public static long startTime = System.currentTimeMillis();
    //Game game = (Game)new HexagonGame(boardSize, 10);
    //Game game = (Game)new ClassicGame(boardSize, 30);

    public static void main(String[] args) {
        FileUtils.setupDirectory();
        FileUtils.loadHighScores();
        Img.initImages();
        GUI.init();
    }

    public static JMenuBar createMenuBar() {

        JMenuBar menuBar = new JMenuBar();

        JMenu modeMenu = new JMenu("Mode");

        JMenuItem classicMenuItem = new JMenuItem("Classic");
        classicMenuItem.addActionListener((event) -> {
            Difficulty currentDifficulty = game.getDifficulty();
            restartGame(GameMode.CLASSIC, currentDifficulty);
        });

        JMenuItem hexagonMenuItem = new JMenuItem("Hexagon");
        hexagonMenuItem.addActionListener((event) -> {

            Difficulty currentDifficulty = game.getDifficulty();
            restartGame(GameMode.HEXAGON, currentDifficulty);
        });

        JMenuItem colourMenuItem = new JMenuItem("Colour");
        colourMenuItem.addActionListener((event) -> {

            Difficulty currentDifficulty = game.getDifficulty();
            restartGame(GameMode.COLOUR, currentDifficulty);
        });

        modeMenu.add(classicMenuItem);
        modeMenu.add(hexagonMenuItem);
        modeMenu.add(colourMenuItem);

        JMenu difficultyMenu = new JMenu("Difficulty");

        JMenuItem beginnerItem = new JMenuItem("Beginner");
        beginnerItem.addActionListener((event) -> {
            GameMode currentGameMode = game.getGameMode();
            restartGame(currentGameMode, Difficulty.BEGINNER);
        });

        JMenuItem intermediateItem = new JMenuItem("Intermediate");
        intermediateItem.addActionListener((event) -> {
            GameMode currentGameMode = game.getGameMode();
            restartGame(currentGameMode, Difficulty.INTERMEDIATE);
        });

        JMenuItem expertItem = new JMenuItem("Expert");
        expertItem.addActionListener((event) -> {
            GameMode currentGameMode = game.getGameMode();
            restartGame(currentGameMode, Difficulty.EXPERT);
        });

        difficultyMenu.add(beginnerItem);
        difficultyMenu.add(intermediateItem);
        difficultyMenu.add(expertItem);
        
        
        JButton restartBtn = new JButton("Restart");
        restartBtn.addActionListener((event) -> {
            GameMode currentGameMode = game.getGameMode();
            Difficulty currentDifficulty = game.getDifficulty();
            restartGame(currentGameMode, currentDifficulty);
        });
        JButton highScoresBtn = new JButton("Scores");
        highScoresBtn.addActionListener((event) -> {
            System.out.println("High scores");
            
            String highScoresStr = "";
            for(String highScore : HighScores.scores){
                highScoresStr += highScore.replace(":", " - ") + "\n";
            }
            
            JOptionPane.showMessageDialog(
                            GUI.frame,
                            highScoresStr,
                            "High Scores",
                            JOptionPane.PLAIN_MESSAGE);
        });

        menuBar.add(modeMenu);
        menuBar.add(difficultyMenu);
        menuBar.add(restartBtn);
        menuBar.add(highScoresBtn);
        menuBar.add(Box.createHorizontalGlue());
        timer.setBorder(new EmptyBorder(0,0,0,20));
        menuBar.add(timer);

        return menuBar;
    }

    public static void restartGame(GameMode gameMode, Difficulty difficulty) {
        GUI.panel.removeAll();
        GUI.panel.revalidate();
        GUI.panel.repaint();
        int width = 0;
        int height = 0;
        if (gameMode.equals(GameMode.CLASSIC)) {
            game = (Game) new ClassicGame(difficulty);
            width = Minesweeper.game.getBoardSize() * Minesweeper.game.getCellWidth() + 20;
            height = Minesweeper.game.getBoardSize() * Minesweeper.game.getCellHeight() + 60;
        } else if (gameMode.equals(GameMode.HEXAGON)) {
            game = (Game) new HexagonGame(difficulty);
            width = Minesweeper.game.getBoardSize() * Minesweeper.game.getCellWidth() + 20 + (Minesweeper.game.getCellWidth() / 2);
            height = Minesweeper.game.getBoardSize() * (Minesweeper.game.getCellHeight() / 4 * 3) + 60;
        } else if (gameMode.equals(GameMode.COLOUR)) {
            game = (Game) new ColourGame(difficulty);
            width = Minesweeper.game.getBoardSize() * Minesweeper.game.getCellWidth() + 20;
            height = Minesweeper.game.getBoardSize() * Minesweeper.game.getCellHeight() + 60;
        } 
        game.init(GUI.panel);
        startTimer();
        GUI.frame.setSize(width, height);
    }

    public static void startTimer() {
        startTime = System.currentTimeMillis();
        long savedTime = startTime;
        new Thread(() -> {
            while (savedTime == startTime && !game.isGameOver()) {

                long millisRun = System.currentTimeMillis() - startTime;
                long secondsRun = millisRun / 1000;
                HighScores.currentScore = secondsRun;
                timer.setText("Score: " + secondsRun);
                
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Minesweeper.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();
    }

}
