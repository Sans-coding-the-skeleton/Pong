package entity;

import gameGraphics.GamePanel;

import java.awt.geom.Rectangle2D;
import java.util.Random;

/**
 * The Ball class represents a ball entity in the game.
 * It extends the Entity class and manages the movement and collision detection of the ball.
 */
public class Ball extends Entity {

    private final Random rand = new Random();
    private int leftScore, rightScore;
    private final Paddle paddle1;
    private final Paddle paddle2;
    private int leftIFrames = 0;
    private int rightIFrames = 0;

    /**
     * Constructs a Ball object with the specified GamePanel and paddles for player 1 and player 2.
     *
     * @param gp      the GamePanel object that contains the game environment
     * @param player1 the Paddle object representing player 1
     * @param player2 the Paddle object representing player 2
     */
    public Ball(GamePanel gp, Paddle player1, Paddle player2) {
        this.gp = gp;
        resetBall();
        this.paddle1 = player1;
        this.paddle2 = player2;
    }

    /**
     * Resets the position and speed of the ball to its initial state.
     */
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

    /**
     * Updates the position of the ball, handles collisions with walls and paddles,
     * and manages the scoring.
     */
    @Override
    public void update() {
        // Update position
        x += xSpeed;
        y += ySpeed;
        // Handle collisions with top and bottom walls
        if (y >= gp.getScreenHeight() - height || y <= 0) {
            gp.playSE(3);
            ySpeed *= -1;
        }
        // Handle scoring and ball reset
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
        // Handle collisions with paddles
        leftIFrames = handlePaddleCollisions(paddle1, leftIFrames);
        rightIFrames = handlePaddleCollisions(paddle2, rightIFrames);
    }

    /**
     * Handles collisions with paddles.
     *
     * @param paddle  the Paddle object to check collision with
     * @param iFrames the invincibility frame counter for the paddle
     * @return the updated invincibility frame counter
     */
    private int handlePaddleCollisions(Paddle paddle, int iFrames) {
        int maxXSpeed = 17;
        if (x <= paddle.width + paddle.x && collision(x, y, width, height, paddle)) {
            if (iFrames == 0) {
                if (x > gp.getScreenWidth() / 2 && xSpeed <= maxXSpeed) {
                    xSpeed++;

                } else if (x < gp.getScreenWidth() / 2 && -xSpeed <= maxXSpeed) {
                    xSpeed--;
                }
                xSpeed *= -1;
                iFrames = 10;
                gp.playSE(3);
            }
        }
        if (iFrames > 0) {
            iFrames--;
        }
        return iFrames;
    }

    /**
     * Checks collision between the ball and a paddle.
     *
     * @param x      the X-coordinate of the ball
     * @param y      the Y-coordinate of the ball
     * @param width  the width of the ball
     * @param height the height of the ball
     * @param paddle the Paddle object to check collision with
     * @return true if there's a collision, false otherwise
     */
    private boolean collision(int x, int y, int width, int height, Paddle paddle) {
        if (new Rectangle2D.Double(x, y, width, height).intersects(new Rectangle2D.Double(paddle.x, paddle.y, paddle.width, paddle.height))) {
            ySpeed += addYSpeed(paddle);
            return true;
        }
        return false;
    }

    /**
     * Adjusts the Y-speed of the ball based on the direction of the paddle's movement.
     *
     * @param paddle the Paddle object to check direction
     * @return the adjustment to the Y-speed
     */
    private int addYSpeed(Paddle paddle) {
        int speedToAdd = 0;
        if (paddle.directions.equals(Directions.UP)) {
            speedToAdd = -paddle.ySpeed / 4;
        }
        if (paddle.directions.equals(Directions.DOWN)) {
            speedToAdd = paddle.ySpeed / 4;
        }
        return speedToAdd;
    }

    /**
     * Gets the left player's current score.
     *
     * @return the left player's score
     */
    public int getLeftScore() {
        return leftScore;
    }

    /**
     * Gets the right player's current score.
     *
     * @return the right player's score
     */
    public int getRightScore() {
        return rightScore;
    }
}