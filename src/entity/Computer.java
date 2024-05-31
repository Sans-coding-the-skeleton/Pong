package entity;

import gameGraphics.GamePanel;

/**
 * The Computer class represents a computer-controlled paddle in the game.
 * It extends the Paddle class and automatically updates its position based on the ball's position.
 */
public class Computer extends Paddle {
    private final int ySpeed = 1;
    private final int yMaxSpeed = 5;

    /**
     * Constructs a Computer object with the specified GamePanel and position.
     *
     * @param gp       the GamePanel object that contains the game environment
     * @param isOnLeft a boolean indicating if the computer paddle is on the left side of the screen
     */
    public Computer(GamePanel gp, boolean isOnLeft) {
        this.gp = gp;
        this.isOnLeft = isOnLeft;
        setDefaultValues();
    }

    /**
     * Updates the computer paddle's position based on the ball's position.
     * The paddle moves towards the ball's Y-coordinate at ySpeed and if the ball is 25 pixels or further then it moves at yMaxSpeed + ySpeed.
     */
    @Override
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
