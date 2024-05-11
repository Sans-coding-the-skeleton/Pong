package entity;

import gameGraphics.GamePanel;
import keyInputs.KeyHandler;

import java.awt.*;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;


    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        setDefaultValues();
    }

    public void setDefaultValues() {
        x = 100;
        y = gp.getHeight() / 2;
        speed = 10;
        height = 100;
        width = 10;
    }

    public void update() {
        if (keyH.leftPLayerUpPressed) {
            y -= speed;
        }
        if (keyH.leftPLayerDownPressed) {
            y += speed;
        }
        /*
        if (keyH.rightPLayerUpPressed) {
            rightPlayerY -= rightPlayerSpeed;
        }
        if (keyH.rightPLayerDownPressed) {
            rightPlayerY += rightPlayerSpeed;
        }
         */
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.fillRect(x, y, width, height);
        /*
        g2.setColor(Color.WHITE);
        g2.fillRect(rightPlayerX, rightPlayerY, rightPlayerPaddleWidth, rightPlayerPaddleHeight);
         */
    }
}
