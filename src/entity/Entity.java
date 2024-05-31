package entity;

import gameGraphics.GamePanel;

import java.awt.*;

/**
 * The abstract Entity class represents a general game entity with properties such as position,
 * speed and dimensions. It serves as a base class for specific game entities.
 */
public abstract class Entity {
    protected int x, y;
    protected int ySpeed;
    protected int xSpeed;
    protected int width;
    protected int height;
    protected boolean isOnLeft;
    protected GamePanel gp;

    /**
     * Returns the height of the entity.
     *
     * @return the height of the entity
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns the Y-coordinate of the entity.
     *
     * @return the Y-coordinate of the entity
     */
    public int getY() {
        return y;
    }

    /**
     * Draws the entity on the screen using the specified Graphics2D object.
     * The default implementation draws a white rectangle representing the entity.
     *
     * @param g2 the Graphics2D object used for drawing the entity
     */
    public void draw(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.fillRect(x, y, width, height);
    }

    /**
     * Updates the state of the entity. The default implementation does nothing and can be overridden
     * by subclasses to provide specific update behavior.
     */
    public void update() {
        // Default implementation does nothing
    }
}
