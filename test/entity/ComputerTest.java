package entity;

import gameGraphics.GamePanel;
import keyInputs.KeyHandler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ComputerTest {

    @Test
    void update() {
        GamePanel gp = new GamePanel();
        Computer computer = new Computer(gp, false);
        KeyHandler keyH = new KeyHandler(gp);
        Player player1 = new Player(gp,keyH,true);
        Ball ball = new Ball(gp, player1, computer);
        gp.setBall(ball);
        computer.y = 20;
        computer.x = gp.getWidth() - 20;
        ball.x = gp.getWidth() -30;
        ball.y = 50;
        ball.ySpeed = 0;
        ball.xSpeed = 10;
        computer.update();
        assertEquals(19, computer.y);
    }
}