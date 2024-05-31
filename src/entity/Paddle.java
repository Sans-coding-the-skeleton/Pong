package entity;

/**
 * The abstract Paddle class represents a paddle entity in the game, extending the Entity class.
 * It provides default values for the paddle's position and dimensions and includes a direction attribute.
 */
public abstract class Paddle extends Entity {

    /**
     * Sets the default values for the paddle's position, dimensions, and speed.
     * The paddle's X-coordinate is set based on its position (left or right) on the screen.
     * The Y-coordinate is set to vertically center the paddle on the screen.
     */
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

    /**
     * The current direction of the paddle, initially set to NONE.
     */
    protected Directions directions = Directions.NONE;
}
