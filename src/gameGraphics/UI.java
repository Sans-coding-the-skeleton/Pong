package gameGraphics;

import entity.Ball;

import java.awt.*;

public class UI {
    GamePanel gp;
    Font Arial_40;

    public UI(GamePanel gp) {
        this.gp = gp;
        Arial_40 = new Font("Arial", Font.PLAIN, 40);
    }

    public void draw(Graphics2D g2, Ball ball){
        g2.setFont(Arial_40);
        g2.setColor(Color.WHITE);
        g2.drawString(String.valueOf(ball.getLeftScore()), 325, 50);
        g2.setColor(Color.WHITE);
        g2.drawString(String.valueOf(ball.getRightScore()), 450, 50);
        if(gp.getGameState().equals(GameState.pauseState)) { //if the game is paused we draw "PAUSED" on top of everything
            g2.setColor(Color.GRAY);
            g2.drawString("PAUSED", gp.getScreenWidth()/2 -80, gp.getScreenHeight()/2);
        }
    }
}
