package entity;

import gameGraphics.GamePanel;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class Ball extends Entity {
    // TODO Fix Stalemates
    GamePanel gp;
    Random rand = new Random();
    private int leftScore, rightScore;
    private final Player player1;
    private final Player player2;
    private int leftIFrames = 0;
    private int rightIFrames = 0;


    public Ball(GamePanel gp, Player player1, Player player2) {
        this.gp = gp;
        resetBall();
        this.player1 = player1;
        this.player2 = player2;
    }

    public void resetBall() {
        width = 24;
        height = 24;
        x = ((gp.getScreenWidth() / 2) - (width / 2));
        y = rand.nextInt(height, gp.getScreenHeight() - height);
        if (isOnLeft) {
            xSpeed = -5;
        } else {
            xSpeed = 5;
        }
        ySpeed = rand.nextInt(4) - 2;
    }

    public void update() {
        x += xSpeed;
        y += ySpeed;
        if (y >= gp.getScreenHeight() - height) {
            gp.playSE(0);
            ySpeed *= -1;
        }
        if (y <= 0) {
            gp.playSE(0);
            ySpeed *= -1;
        }
        if (x >= gp.getScreenWidth() - width) {
            isOnLeft = true;
            leftScore += 1;
            gp.playSE(1);
            resetBall();
        }
        if (x <= 0) {
            isOnLeft = false;
            rightScore += 1;
            gp.playSE(1);
            resetBall();
        }
        if (new Rectangle2D.Double(x, y, width, height).intersects(new Rectangle2D.Double(player1.x, player1.y, player1.width, player1.height))) {
            if (leftIFrames == 0) {
                xSpeed = -xSpeed;
                leftIFrames = 10;
                gp.playSE(0);
            }
        }
        if (new Rectangle2D.Double(x, y, width, height).intersects(new Rectangle2D.Double(player2.x, player2.y, player2.width, player2.height))) {
            if (rightIFrames == 0) {
                xSpeed = -xSpeed;
                rightIFrames = 10;
                gp.playSE(0);
            }
        }
        if (leftIFrames > 0) {
            leftIFrames--;
        }
        if (rightIFrames > 0) {
            rightIFrames--;
        }
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.fillRect(x, y, width, height);
    }

    public int getLeftScore() {
        return leftScore;
    }

    public int getRightScore() {
        return rightScore;
    }
}
