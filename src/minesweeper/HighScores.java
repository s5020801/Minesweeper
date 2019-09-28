/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;

import java.util.ArrayList;
import java.util.List;
import static minesweeper.FileUtils.writeFile;

/**
 *
 * @author Josh
 */
public class HighScores {

    public static List<String> scores = new ArrayList();
    public static long currentScore = 0;

    public static void loadScores(String[] fileLines) {
        for (String line : fileLines) {
            scores.add(line);
        }
        sortScores();
    }

    public static void add(String name) {
        /*for (int i = 0; i < scores.size(); i++) {
            if (currentScore < Integer.parseInt(scores.get(i).split(":")[1])) {
                int index = Math.max(0, i - 1);
                scores.add(index, name + ":" + currentScore);
                break;
            }
        }
         */
        scores.add(name + ":" + currentScore);
        sortScores();
        FileUtils.saveScores();
    }

    public static void sortScores() {
        List<String> newScores = new ArrayList();

        for (int i = 0; i < scores.size(); i++) {
            int scoreNum = Integer.parseInt(scores.get(i).split(":")[1]);
            boolean added = false;
            for (int j = 0; j < newScores.size(); j++) {
                if (scoreNum < Integer.parseInt(newScores.get(j).split(":")[1])) {
                    newScores.add(j, scores.get(i));
                    added = true;
                    break;
                }
            }
            if (!added)
                newScores.add(scores.get(i));
            
        }
        scores.clear();
        for (String score : newScores) {
            scores.add(score);
        }
    }
    
}
