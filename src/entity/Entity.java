package entity;

import gameGraphics.GamePanel;

import java.awt.*;

public abstract class Entity {
    protected int x, y;
    protected int ySpeed;
    protected int xSpeed;
    protected int width;
    protected int height;
    protected boolean isOnLeft;
    protected GamePanel gp;

    public int getHeight() {
        return height;
    }

    public int getY() {
        return y;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.fillRect(x, y, width, height);
    }

    public void update() {
    }
}
