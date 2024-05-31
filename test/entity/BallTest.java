package entity;

import gameGraphics.GamePanel;
import keyInputs.KeyHandler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BallTest {
    GamePanel gp = new GamePanel();
    KeyHandler keyH = new KeyHandler(gp);
    Player player1 = new Player(gp, keyH, true);
    Player player2 = new Player(gp, keyH, false);
    Ball ball = new Ball(gp, player1, player2);


    @Test
    void getLeftScore() {
        ball.x = 10;
        ball.y = 10;
        ball.ySpeed = 0;
        ball.xSpeed = -10;
        player1.x = 10;
        player1.y = 100;
        ball.update();
        assertEquals(1, ball.getRightScore());
    }

    @Test
    void update() {
        ball.x = 21;
        ball.y = 10;
        ball.ySpeed = 0;
        ball.xSpeed = -10;
        player1.x = 10;
        player1.y = 10;
        player1.directions = Directions.UP;
        ball.update();
        assertEquals(11, ball.xSpeed);
    }
}