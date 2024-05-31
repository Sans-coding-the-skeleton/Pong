package configLoader;

import gameGraphics.GamePanel;

import java.io.*;
import java.nio.file.Path;

/**
 * The Config class handles saving and loading game configuration settings.
 */
public class Config {
    private final GamePanel gp;
    private final Path saveData = Path.of("gameConfig/gameConfig.txt");

    /**
     * Constructs a Config object with the specified GamePanel.
     *
     * @param gp the GamePanel object that contains the game environment
     */
    public Config(GamePanel gp) {
        this.gp = gp;
    }

    /**
     * Saves the current game configuration settings to a file.
     */
    public void saveConfig() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(saveData.toFile()));
            if (gp.isFullScreenOn()) {
                bw.write("On");
            } else {
                bw.write("Off");
            }
            bw.newLine();
            bw.write(String.valueOf(gp.getVolumeScale()));
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Loads the game configuration settings from a file.
     * If no saved configuration is found, default settings are applied.
     */
    public void loadConfig() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(saveData.toFile()));
            if (saveData.toFile().exists()) {
                String s = br.readLine();
                if (s.equalsIgnoreCase("On")) {
                    gp.setFullScreenOn(true);
                } else if (s.equalsIgnoreCase("Off")) {
                    gp.setFullScreenOn(false);
                }
                s = br.readLine();
                gp.setVolumeScale(Integer.parseInt(s));
                br.close();
            }
        } catch (IOException e) {
            gp.setFullScreenOn(false);
            gp.setVolumeScale(3);
        }
    }
}
