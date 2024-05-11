package entity;

import gameGraphics.GamePanel;

import java.awt.*;
import java.util.Random;

public class Ball extends Entity {
    GamePanel gp;
    Random rand = new Random();
    private int leftScore, rightScore;

    public Ball(GamePanel gp) {
        this.gp = gp;
        resetBall();
    }

    public void resetBall() {
        width = 24;
        height = 24;
        x = ((gp.getScreenWidth() / 2) - (width / 2));
        y = rand.nextInt(height, gp.getScreenHeight() - height);
        xSpeed = 5;
        ySpeed = 5;
    }

    public void update() {
        x += xSpeed;
        y += ySpeed;
        if (y >= gp.getScreenHeight() - height) {
            ySpeed *= -1;
        }
        if (y <= 0) {
            ySpeed *= -1;
        }
        if (x >= gp.getScreenWidth()) {
            leftScore += 1;
            resetBall();
        }
        if (x <= 0) {
            rightScore += 1;
            resetBall();
        }

    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.fillRect(x, y, width, height);
    }
}
