package configLoader;

import gameGraphics.GamePanel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConfigTest {
    GamePanel gp = new GamePanel();
    Config config = new Config(gp);
    @Test
    void saveConfig() {
       config.saveConfig();
    }

    @Test
    void loadConfig() {
        config.loadConfig();
    }
}