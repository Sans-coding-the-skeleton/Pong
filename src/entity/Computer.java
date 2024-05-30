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
        directions = Directions.NONE;
        if (y + height / 2 - 25 > gp.getBallY() + gp.getBallHeight() / 2) {
            if (y >= 0) {
                y -= yMaxSpeed;
                directions = Directions.UP;
            }
        } else if (y + height / 2 > gp.getBallY() + gp.getBallHeight() / 2) {
            if (y >= 0) {
                y -= ySpeed;
                directions = Directions.UP;
            }
        }
        if (y + height / 2 + 25 < gp.getBallY() + gp.getBallHeight() / 2) {
            if (y <= gp.getScreenHeight() - height) {
                y += yMaxSpeed;
                directions = Directions.DOWN;
            }
        } else if (y + height / 2 < gp.getBallY() + gp.getBallHeight() / 2) {
            if (y <= gp.getScreenHeight() - height) {
                y += ySpeed;
                directions = Directions.DOWN;
            }
        }
    }
}