package gameGraphics;

import entity.Ball;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class UI {
    GamePanel gp;
    Font pressStartRegular;

    public UI(GamePanel gp) {
        this.gp = gp;
        try {
            InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("press-start-font/PressStartRegular-ay8E.ttf");
            assert stream != null;
            pressStartRegular = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(48f);
        } catch (IOException | FontFormatException e) {
            throw new RuntimeException(e);
        }
    }

    public void draw(Graphics2D g2, Ball ball){
        g2.setFont(pressStartRegular);
        g2.setColor(Color.WHITE);
        g2.drawString(String.valueOf(ball.getLeftScore()), 300, 50);
        g2.setColor(Color.WHITE);
        g2.drawString(String.valueOf(ball.getRightScore()), 475, 50);
        if(gp.getGameState().equals(GameState.pauseState)) { //if the game is paused, "PAUSED" is drawn on top of everything
            g2.setColor(Color.GRAY);
            g2.drawString("PAUSED", gp.getScreenWidth()/2 -80, gp.getScreenHeight()/2);
        }
    }
}
