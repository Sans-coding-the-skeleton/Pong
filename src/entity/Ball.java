package entity;

import gameGraphics.GamePanel;

import java.awt.geom.Rectangle2D;
import java.util.Random;

public class Ball extends Entity {

    private final Random rand = new Random();
    private int leftScore, rightScore;
    private final Paddle paddle1;
    private final Paddle paddle2;
    private int leftIFrames = 0;
    private int rightIFrames = 0;


    public Ball(GamePanel gp, Paddle player1, Paddle player2) {
        this.gp = gp;
        resetBall();
        this.paddle1 = player1;
        this.paddle2 = player2;
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
            gp.playSE(3);
            ySpeed *= -1;
        }
        if (y <= 0) {
            gp.playSE(3);
            ySpeed *= -1;
        }
        if (x >= gp.getScreenWidth() - width) {
            isOnLeft = true;
            leftScore++;
            gp.playSE(4);
            resetBall();
        }
        if (x <= 0) {
            isOnLeft = false;
            rightScore++;
            gp.playSE(4);
            resetBall();
        }
        int maxXSpeed = 17;
        if (x <= paddle1.width + paddle1.x) {
            if (collision(x, y, width, height, paddle1)) {
                if (leftIFrames == 0) {
                    if (-xSpeed <= maxXSpeed) {
                        xSpeed--;
                    }
                    xSpeed *= -1;
                    leftIFrames = 10;
                    gp.playSE(3);
                }
            }
        }
        if (x >= gp.getScreenWidth() - width - paddle2.width - paddle2.x) {
            if (collision(x, y, width, height, paddle2)) {
                if (rightIFrames == 0) {
                    if (xSpeed <= maxXSpeed) {
                        xSpeed++;
                    }
                    xSpeed *= -1;
                    rightIFrames = 10;
                    gp.playSE(3);
                }
            }
        }
        if (leftIFrames > 0) {
            leftIFrames--;
        }
        if (rightIFrames > 0) {
            rightIFrames--;
        }
    }

    public boolean collision(int x, int y, int width, int height, Paddle paddle) {
        boolean doesCollide;
        if (new Rectangle2D.Double(x, y, width, height).intersects(new Rectangle2D.Double(paddle.x, paddle.y, paddle.width, paddle.height))) {
            ySpeed += addYSpeed(paddle);
            doesCollide = true;
        } else {
            doesCollide = false;
        }
        return doesCollide;
    }

    public int addYSpeed(Paddle paddle) {
        int speedToAdd = 0;
        if (paddle.directions.equals(Directions.UP)) {
            speedToAdd = -paddle.ySpeed;
        }
        if (paddle.directions.equals(Directions.DOWN)) {
            speedToAdd = paddle.ySpeed;
        }
        return speedToAdd;
    }

    public int getLeftScore() {
        return leftScore;
    }

    public int getRightScore() {
        return rightScore;
    }
}
