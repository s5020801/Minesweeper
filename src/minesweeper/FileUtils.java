/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class FileUtils {
    // READING FILES
    public static String[] readFile(Path path) {
        String[] lines = null;
        try {
            lines = Files.readAllLines(path).toArray(new String[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public static String[] readFile(String pathStr) {
        Path path = new File(pathStr).toPath();
        return readFile(path);
    }

    public static File[] getFiles(String directoryStr) {
        File directory = new File(directoryStr);
        if (directory != null && directory.exists()) {
            return directory.listFiles();
        }
        return null;
    }


    // WRITING FILES
    public static void createFile(File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeFile(String fileName, List<String> lines) {
        String directoryName = System.getProperty("user.home") + "/Golf";

        File directory = new File(directoryName);
        if (!directory.exists()) {
            directory.mkdir();
        }

        File file = new File(directoryName + "/" + fileName);
        try {
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            for (String text : lines) {
                bw.write(text);
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public static void saveScores() {
        
        File directory = new File(Const.SAVE_DIR);
        if (!directory.exists()) {
            directory.mkdir();
        }

        File file = new File(Const.SAVE_DIR + Const.HIGH_SCORES_FILE_NAME);
        try {
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            for (String text : HighScores.scores) {
                bw.write(text);
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    // Creates save directory. Creates two save files, comp data location and default config.
    public static void setupDirectory() {
        File directory = new File(Const.SAVE_DIR);

        if (!directory.exists()) {
            directory.mkdir();
            System.out.println("Directory probably created.");
        }

        File highScoreFile = new File(Const.SAVE_DIR + Const.HIGH_SCORES_FILE_NAME);
        if (!highScoreFile.exists())
            createFile(highScoreFile);
    }

    public static void saveScoreBoardPathFile(String scoreboardPath, String scoreboardDirectory) {
        List<String> lines = Arrays.asList(scoreboardPath, scoreboardDirectory);
        writeFile("ScoreboardDataLocation.ini", lines);
    }

    public static void saveDefaultConfigFile(String defaultConfigFilePath) {
        List<String> lines = Arrays.asList(defaultConfigFilePath);
        writeFile("DefaultConfigFile.ini", lines);
    }

    public static void loadHighScores() {
        String[] fileLines = readFile(Const.SAVE_DIR + Const.HIGH_SCORES_FILE_NAME);

        if (fileLines == null || fileLines.length == 0) {
            System.out.println("High score file lines is not long enough. cannot proceed. Length: " + fileLines.length);
            return;
        }
        System.out.println("First line: " + fileLines[0]);
        HighScores.loadScores(fileLines);
    }

    // DELETING
    public static void deleteFile(String fileName) {

        String directoryName = System.getProperty("user.home") + "/Golf";
        File file = new File(directoryName + "/" + fileName);
        if (file.exists()) {
            file.delete();
        }

    }


}