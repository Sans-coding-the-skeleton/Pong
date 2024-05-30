package config;

import gameGraphics.GamePanel;

import java.io.*;
import java.nio.file.Path;

public class Config {
    GamePanel gp;
    Path saveData = Path.of("gameConfig.txt");

    public Config(GamePanel gp) {
        this.gp = gp;
    }

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

    public void loadConfig() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(saveData.toFile()));
            if (saveData.toFile().exists()) {
                String s = br.readLine();
                if (s.equalsIgnoreCase("On")) {
                    gp.setFullScreenOn(true);
                }
                if (s.equalsIgnoreCase("Off")) {
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
