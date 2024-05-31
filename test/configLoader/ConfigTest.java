package configLoader;

import gameGraphics.GamePanel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;


class ConfigTest {
    private final GamePanel gp = new GamePanel();
    private final Config config = new Config(gp);
    private final Path savePath = Path.of("gameConfig/gameConfig.txt");
    private boolean fullscreenOn;
    private int volumeScale;

    @Test
    void loadConfig() {
        config.loadConfig();
        try {
            BufferedReader br = new BufferedReader(new FileReader(savePath.toFile()));
            if (savePath.toFile().exists()) {
                String s = br.readLine();
                if (s.equalsIgnoreCase("On")) {
                    fullscreenOn = true;
                } else if (s.equalsIgnoreCase("Off")) {
                    fullscreenOn = false;
                }
                s = br.readLine();
                volumeScale = Integer.parseInt(s);
                br.close();
            }
            Assertions.assertEquals(fullscreenOn, gp.isFullScreenOn());
            Assertions.assertEquals(volumeScale, gp.getVolumeScale());
        } catch (IOException e) {
            Assertions.fail(e);
        }
    }
}