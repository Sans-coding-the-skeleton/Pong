package entity;

import gameGraphics.GamePanel;

public class Computer extends Paddle {
    int ySpeed = 1;
    int yMaxSpeed = 5;

    public Computer(GamePanel gp, boolean isOnLeft) {
        this.gp = gp;
        this.isOnLeft = isOnLeft;
        setDefaultValues();
    }

    public void update() {
        if (y + height / 2 - 25 > gp.getBallY() + gp.getBallHeight() / 2) {
            if (y >= 0) {
                y -= yMaxSpeed;
            }
        } else if (y + height / 2 > gp.getBallY() + gp.getBallHeight() / 2) {
            if (y >= 0) {
                y -= ySpeed;
            }
        }
        if (y + height / 2 + 25 < gp.getBallY() + gp.getBallHeight() / 2) {
            if (y <= gp.getScreenHeight() - height) {
                y += yMaxSpeed;
            }
        } else if (y + height / 2 < gp.getBallY() + gp.getBallHeight() / 2) {
            if (y <= gp.getScreenHeight() - height) {
                y += ySpeed;
            }
        }

    }
}