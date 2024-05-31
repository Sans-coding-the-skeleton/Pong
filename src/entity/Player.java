package entity;

import gameGraphics.GamePanel;
import keyInputs.KeyHandler;

/**
 * The Player class represents a player-controlled paddle in the game.
 * It extends the Paddle class and handles the movement based on player input.
 */
public class Player extends Paddle {
    private final KeyHandler keyH;

    /**
     * Constructs a Player object with the specified GamePanel, KeyHandler, and position.
     *
     * @param gp       the GamePanel object that contains the game environment
     * @param keyH     the KeyHandler object that manages the player's input
     * @param isOnLeft a boolean indicating if the player is on the left side of the screen
     */
    public Player(GamePanel gp, KeyHandler keyH, boolean isOnLeft) {
        this.gp = gp;
        this.keyH = keyH;
        this.isOnLeft = isOnLeft;
        setDefaultValues();
    }

    /**
     * Updates the player's position based on the key inputs for up and down movements.
     * The paddle's direction is updated based on the movement.
     */
    @Override
    public void update() {
        directions = Directions.NONE;
        if (isOnLeft) {
            if (keyH.isLeftPlayerUpPressed()) {
                if (y >= 0) {
                    y -= ySpeed;
                    directions = Directions.UP;
                }
            }
            if (keyH.isLeftPlayerDownPressed()) {
                if (y <= gp.getScreenHeight() - height) {
                    y += ySpeed;
                    directions = Directions.DOWN;
                }
            }
        } else {
            if (keyH.isRightPlayerUpPressed()) {
                if (y >= 0) {
                    y -= ySpeed;
                    directions = Directions.UP;
                }
            }
            if (keyH.isRightPlayerDownPressed()) {
                if (y <= gp.getScreenHeight() - height) {
                    y += ySpeed;
                    directions = Directions.DOWN;
                }
            }
        }
    }
}
