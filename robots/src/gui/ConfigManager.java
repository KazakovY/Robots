package gui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigManager {
    private static final String CONFIG_FILE_PATH = System.getProperty("user.home") + File.separator + "window_config.txt";

    public static void saveWindowConfig(String windowName, int x, int y, int width, int height, boolean isMaximized) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CONFIG_FILE_PATH, true))) {
            writer.write(windowName + "," + x + "," + y + "," + width + "," + height + "," + isMaximized);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String[] getWindowConfig(String windowName) {
        String[] config = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(CONFIG_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(windowName)) {
                    config = parts;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return config;
    }
}

