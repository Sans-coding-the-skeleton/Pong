package gameGraphics;

import entity.Ball;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class UI {
    private final GamePanel gp;
    private final Font pressStartRegular;
    private int commandNum;

    public UI(GamePanel gp) {
        this.gp = gp;
        commandNum = 0;
        try {
            InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("press-start-font/PressStartRegular-ay8E.ttf");
            assert stream != null;
            pressStartRegular = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(48f);
        } catch (IOException | FontFormatException e) {
            throw new RuntimeException(e);
        }
    }

    public void draw(Graphics2D g2, Ball ball) {
        g2.setFont(pressStartRegular);
        if (gp.getGameState().equals(GameState.PLAY_STATE)) {
            drawPlayScreen(g2, ball);
        }
        if (gp.getGameState().equals(GameState.PAUSE_STATE)) {
            drawPlayScreen(g2, ball);
            drawPauseScreen(g2);//if the game is paused, "PAUSED" is drawn on top of everything
        }
        if (gp.getGameState().equals(GameState.TITLE_STATE)) {
            drawTitleScreen(g2);
        }
        if (gp.getGameState().equals(GameState.SETTINGS_STATE)) {
            drawSettingsScreen(g2);
        }
        if(gp.getGameState().equals(GameState.CONTROLS_STATE)){
            drawControlsScreen(g2);
        }
        if(gp.getGameState().equals(GameState.CONFIRM_EXIT_STATE)){
            drawConfirmExitScreen(g2);
        }
    }

    public void drawPlayScreen(Graphics2D g2, Ball ball) {
        g2.setColor(Color.WHITE);
        g2.drawString(String.valueOf(ball.getLeftScore()), getXForCenteredText(String.valueOf(ball.getLeftScore()), g2) - 75, 50); //250 50
        g2.setColor(Color.WHITE);
        g2.drawString(String.valueOf(ball.getRightScore()), getXForCenteredText(String.valueOf(ball.getRightScore()), g2) + 75, 50); // 475 50
    }

    public void drawPauseScreen(Graphics2D g2) {
        g2.setColor(Color.GRAY);
        g2.drawString("PAUSED", getXForCenteredText("PAUSED", g2), gp.getScreenHeight() / 2);
    }

    private void drawTitleScreen(Graphics2D g2) {
        String text = "PONG";
        drawTitle(text, g2);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24f));
        text = "Player vs Player";
        drawMenu(text, g2, 0);
        if (commandNum == 0) {
            drawChoice(text, g2, commandNum);
        }
        text = "Player vs Computer";
        drawMenu(text, g2, 1);
        if (commandNum == 1) {
            drawChoice(text, g2, commandNum);
        }
        text = "Settings";
        drawMenu(text, g2, 2);
        if (commandNum == 2) {
            drawChoice(text, g2, commandNum);
        }
        text = "Quit";
        drawMenu(text, g2, 3);
        if (commandNum == 3) {
            drawChoice(text, g2, commandNum);
        }
    }

    private void drawSettingsScreen(Graphics2D g2) {
        String text = "SETTINGS";
        drawTitle(text, g2);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24f));
        text = "Fullscreen";
        int line = 0;
        drawMenu(text, g2, line);
        if (commandNum == line) {
            drawChoice(text, g2, commandNum);
        }
        line+=1;
        text = "Sound";
        drawMenu(text, g2, line);
        if (commandNum == line) {
            drawChoice(text, g2, commandNum);
        }
        line+=1;
        text = "Controls";
        drawMenu(text, g2, line);
        if (commandNum == line) {
            drawChoice(text, g2, commandNum);
        }
        line+=1;
        text = "Back";
        drawMenu(text, g2, line);
        if (commandNum == line) {
            drawChoice(text, g2, commandNum);
        }
    }

    private void drawControlsScreen(Graphics2D g2) {
        String text = "CONTROLS";
        drawTitle(text, g2);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24f));
        int line = 0;
        text = "Left player UP - W";
        drawMenu(text, g2, line);
        line+=1;
        text = "Left player DOWN - S";
        drawMenu(text, g2, line);
        line+=1;
        text = "Right player UP - ARROW UP";
        drawMenu(text, g2, line);
        line+=1;
        text = "Right player DOWN - ARROW DOWN";
        drawMenu(text, g2, line);
        line+=1;
        text = "Pause - P";
        drawMenu(text, g2, line);
        line+=1;
        text = "Back";
        drawMenu(text, g2, line);
        if (commandNum == line) {
            drawChoice(text, g2, commandNum);
        }
    }

    private void drawConfirmExitScreen(Graphics2D g2) {
        String text = "PONG";
        drawTitle(text, g2);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24f));
        int line = 0;
        text = "Are you sure you want to exit?";
        drawMenu(text, g2, line);
        line+=1;
        text = "Yes";
        drawMenu(text, g2, line);
        if(commandNum == line) {
            drawChoice(text, g2, commandNum);
        }
        line+=1;
        text = "No";
        drawMenu(text, g2, line);
        if(commandNum == line) {
            drawChoice(text, g2, commandNum);
        }
    }

    public void drawTitle(String text, Graphics2D g2) {
        g2.setColor(Color.GRAY);
        int centeredText = getXForCenteredText(text, g2);
        g2.drawString(text, centeredText + 5, gp.getScreenHeight() / 2 - 120);
        g2.setColor(Color.WHITE);
        g2.drawString(text, centeredText, gp.getScreenHeight() / 2 - 125);
    }

    public void drawMenu(String text, Graphics2D g2, int line) {
        int centeredText = getXForCenteredText(text, g2);
        g2.drawString(text, centeredText, gp.getScreenHeight() / 2 + pressStartRegular.getSize() * (line-1));
    }

    public void drawChoice(String text, Graphics2D g2, int line) {
        int centeredText = getXForCenteredText(text, g2);
        g2.drawString(">", centeredText - pressStartRegular.getSize(), gp.getScreenHeight() / 2 +pressStartRegular.getSize() * (line -1));
    }

    public int getXForCenteredText(String text, Graphics2D g2) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.getScreenWidth() / 2 - length / 2;
    }

    public void addCommandNum() {
        commandNum++;
    }

    public void removeCommandNum() {
        commandNum--;
    }

    public int getCommandNum() {
        return commandNum;
    }

    public void setCommandNum(int commandNum) {
        this.commandNum = commandNum;
    }
}
