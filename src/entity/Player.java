package entity;

import gameGraphics.GamePanel;
import keyInputs.KeyHandler;

import java.awt.*;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;


    public Player(GamePanel gp, KeyHandler keyH, boolean isOnLeft) {
        this.gp = gp;
        this.keyH = keyH;
        this.isOnLeft = isOnLeft;
        setDefaultValues();
    }

    public void setDefaultValues() {
        if (isOnLeft) {
            x = 10;
        } else {
            x = gp.getScreenWidth() - 20;
        }
        height = 100;
        width = 10;
        y = (gp.getScreenHeight() / 2) - (height / 2);
        ySpeed = 10;
    }

    public void update() {
        if (isOnLeft) {
            if (keyH.leftPLayerUpPressed) {
                if (y >= 0) {
                    y -= ySpeed;
                }
            }
            if (keyH.leftPLayerDownPressed) {
                if (y <= gp.getScreenHeight() - height) {
                    y += ySpeed;
                }
            }
        } else {
            if (keyH.rightPLayerUpPressed) {
                if (y >= 0) {
                    y -= ySpeed;
                }
            }
            if (keyH.rightPLayerDownPressed) {
                if (y <= gp.getScreenHeight() - height) {
                    y += ySpeed;
                }
            }
        }
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.fillRect(x, y, width, height);
    }
}
