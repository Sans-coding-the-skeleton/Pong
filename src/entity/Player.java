package entity;

import gameGraphics.GamePanel;
import keyInputs.KeyHandler;

public class Player extends Paddle {
    private final KeyHandler keyH;

    public Player(GamePanel gp, KeyHandler keyH, boolean isOnLeft) {
        this.gp = gp;
        this.keyH = keyH;
        this.isOnLeft = isOnLeft;
        setDefaultValues();
    }

    public void update() {
        directions = Directions.NONE;
        if (isOnLeft) {
            if (keyH.isLeftPLayerUpPressed()) {
                if (y >= 0) {
                    y -= ySpeed;
                    directions = Directions.UP;
                }
            }
            if (keyH.isLeftPLayerDownPressed()) {
                if (y <= gp.getScreenHeight() - height) {
                    y += ySpeed;
                    directions = Directions.DOWN;
                }
            }
        } else {
            if (keyH.isRightPLayerUpPressed()) {
                if (y >= 0) {
                    y -= ySpeed;
                    directions = Directions.UP;
                }
            }
            if (keyH.isRightPLayerDownPressed()) {
                if (y <= gp.getScreenHeight() - height) {
                    y += ySpeed;
                    directions = Directions.DOWN;
                }
            }
        }
    }
}
