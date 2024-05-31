package entity;

import gameGraphics.GamePanel;
import keyInputs.KeyHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PlayerTest {
    GamePanel gp = new GamePanel();
    KeyHandler keyH = new KeyHandler(gp);
    Player player = new Player(gp, keyH, true);

    @Test
    void update() {
        player.setDefaultValues();
        player.y = 20;
        keyH.setLeftPlayerDownPressed(true);
        player.update();
        Assertions.assertEquals(30, player.y);
        keyH.setLeftPlayerDownPressed(false);
        keyH.setLeftPlayerUpPressed(true);
        player.update();
        Assertions.assertEquals(20, player.y);
    }
}