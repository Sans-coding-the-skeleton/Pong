package gameGraphics;

import entity.Ball;
import main.Main;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * The UI class handles the graphical user interface elements of the game.
 * It manages the drawing of various screens, menus, and text.
 */
public class UI {
    private final GamePanel gp;
    private Font pressStartRegular;
    private int commandNum = 0;
    private final int offset = 25; //offset to make all options visible in controls

    /**
     * Constructs a UI object with the given game panel.
     *
     * @param gp The game panel.
     */
    public UI(GamePanel gp) {
        this.gp = gp;
        try {
            InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("press-start-font/PressStartRegular-ay8E.ttf");
            assert stream != null;
            pressStartRegular = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(48f);
        } catch (IOException | FontFormatException e) {
            pressStartRegular = new Font("Arial", Font.PLAIN, 48); //backup font for the case when the font is not found
        }
    }

    /**
     * Draws the user interface elements based on the current game state.
     *
     * @param g2   The Graphics2D object.
     * @param ball The ball object.
     */
    public void draw(Graphics2D g2, Ball ball) {
        g2.setFont(pressStartRegular);
        if (gp.getGameState().equals(GameState.PVP_PLAY_STATE) || gp.getGameState().equals(GameState.PVC_PLAY_STATE)) {
            drawScore(g2, ball);
        } else if (gp.getGameState().equals(GameState.PAUSE_STATE)) {
            drawScore(g2, ball);
            drawPauseScreen(g2);//if the game is paused, "PAUSED" is drawn on top of everything
        } else if (gp.getGameState().equals(GameState.TITLE_STATE)) {
            drawTitleScreen(g2);
        } else if (gp.getGameState().equals(GameState.SETTINGS_STATE)) {
            drawSettingsScreen(g2);
        } else if (gp.getGameState().equals(GameState.CONTROLS_STATE)) {
            drawControlsScreen(g2);
        } else if (gp.getGameState().equals(GameState.CONFIRM_EXIT_STATE)) {
            drawConfirmExitScreen(g2);
        } else if (gp.getGameState().equals(GameState.MENU_STATE)) {
            drawInGameMenu(g2);
        } else if (gp.getGameState().equals(GameState.CREDITS_STATE)) {
            drawCredits(g2);
        }
    }

    /**
     * Draws the score on the screen.
     *
     * @param g2   The Graphics2D object.
     * @param ball The ball object.
     */
    private void drawScore(Graphics2D g2, Ball ball) {
        g2.setColor(Color.WHITE);
        g2.drawString(String.valueOf(ball.getLeftScore()), getXForCenteredText(String.valueOf(ball.getLeftScore()), g2) - 75, 50); //250 50
        g2.drawString(String.valueOf(ball.getRightScore()), getXForCenteredText(String.valueOf(ball.getRightScore()), g2) + 75, 50); // 475 50
    }

    /**
     * Draws the pause screen.
     *
     * @param g2 The Graphics2D object.
     */
    private void drawPauseScreen(Graphics2D g2) {
        g2.setColor(Color.GRAY);
        g2.drawString("PAUSED", getXForCenteredText("PAUSED", g2), gp.getScreenHeight() / 2);
    }

    /**
     * Draws the title screen with menu options.
     *
     * @param g2 The Graphics2D object.
     */
    private void drawTitleScreen(Graphics2D g2) {
        String text = "PONG";
        drawTitle(text, g2);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24f));
        text = "Player vs Player";
        int line = 0;
        if (commandNum == line) {
            drawChoice(text, g2, commandNum);
        }
        line = drawMenu(text, g2, line);
        text = "Player vs Computer";
        if (commandNum == line) {
            drawChoice(text, g2, commandNum);
        }
        line = drawMenu(text, g2, line);
        text = "Settings";
        if (commandNum == line) {
            drawChoice(text, g2, commandNum);
        }
        line = drawMenu(text, g2, line);
        text = "Quit";
        drawMenu(text, g2, line);
        if (commandNum == line) {
            drawChoice(text, g2, commandNum);
        }
    }

    /**
     * Draws the settings screen with options.
     *
     * @param g2 The Graphics2D object.
     */
    private void drawSettingsScreen(Graphics2D g2) {
        String text = "SETTINGS";
        drawTitle(text, g2);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24f));
        text = "Fullscreen";
        int line = 0;
        boolean fullScreenNotification = (Main.window.isUndecorated() && !gp.isFullScreenOn()) || (!Main.window.isUndecorated() && gp.isFullScreenOn());
        if (gp.isFullScreenOn()) {
            drawFilledCheckbox(text, g2, line);
        }
        drawCheckbox(text, g2, line);
        if (commandNum == line) {
            drawChoice(text, g2, commandNum);
        }
        line = drawMenu(text, g2, line);
        text = "Sound";
        drawBar(text, g2, line, 5);
        drawFilledBar(text, g2, line, gp.getVolumeScale());
        if (commandNum == line) {
            drawChoice(text, g2, commandNum);
        }
        line = drawMenu(text, g2, line);
        text = "Controls";
        if (commandNum == line) {
            drawChoice(text, g2, commandNum);
        }
        line = drawMenu(text, g2, line);
        text = "Credits";
        if (commandNum == line) {
            drawChoice(text, g2, commandNum);
        }
        line = drawMenu(text, g2, line);
        text = "Back";
        if (commandNum == line) {
            drawChoice(text, g2, commandNum);
        }
        line = drawMenu(text, g2, line);
        if (fullScreenNotification) {
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 12f));
            text = "The change will take effect after restarting the game.";
            line = drawMenu(text, g2, line);
            if (gp.isFullScreenOn()) {
                text = "Turning this option on may slow down draw times.";
                drawMenu(text, g2, line);
            }
        }
        gp.saveConfig();
    }

    /**
     * Draws the controls screen with key mappings.
     *
     * @param g2 The Graphics2D object.
     */
    private void drawControlsScreen(Graphics2D g2) {
        String text = "CONTROLS";
        drawTitle(text, g2);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24f));
        int line = 0;
        text = "Left player UP - W";
        line = drawMenu(text, g2, line);
        text = "Left player DOWN - S";
        line = drawMenu(text, g2, line);
        text = "Right player UP - ARROW UP";
        line = drawMenu(text, g2, line);
        text = "Right player DOWN - ARROW DOWN";
        line = drawMenu(text, g2, line);
        text = "Pause - P";
        line = drawMenu(text, g2, line);
        text = "Menu - ESC";
        line = drawMenu(text, g2, line);
        text = "Back";
        drawMenu(text, g2, line);
        drawChoice(text, g2, commandNum);
    }

    /**
     * Draws the confirmation screen for exiting the game.
     *
     * @param g2 The Graphics2D object.
     */
    private void drawConfirmExitScreen(Graphics2D g2) {
        String text = "PONG";
        drawTitle(text, g2);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24f));
        int line = 0;
        text = "Are you sure you want to exit?";
        line = drawMenu(text, g2, line);
        text = "Yes";
        if (commandNum == line) {
            drawChoice(text, g2, commandNum);
        }
        line = drawMenu(text, g2, line);
        text = "No";
        if (commandNum == line) {
            drawChoice(text, g2, commandNum);
        }
        drawMenu(text, g2, line);
    }

    /**
     * Draws the in-game menu with options.
     *
     * @param g2 The Graphics2D object.
     */
    private void drawInGameMenu(Graphics2D g2) {
        String text = "MENU";
        drawTitle(text, g2);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24f));
        int line = 0;
        text = "Back to game";

        if (commandNum == line) {
            drawChoice(text, g2, commandNum);
        }
        line = drawMenu(text, g2, line);
        text = "Go to menu";
        drawMenu(text, g2, line);
        if (commandNum == line) {
            drawChoice(text, g2, commandNum);
        }
    }

    /**
     * Draws the credits screen.
     *
     * @param g2 The Graphics2D object.
     */
    private void drawCredits(Graphics2D g2) {
        String text = "CREDITS";
        drawTitle(text, g2);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24f));
        int line = 0;
        text = "Art by Sans-coding-the-skeleton";
        line = drawMenu(text, g2, line);
        text = "SFX by Sans-coding-the-skeleton";
        line = drawMenu(text, g2, line);
        text = "Code by Sans-coding-the-skeleton";
        line = drawMenu(text, g2, line);
        text = "Font by codeman38";
        line = drawMenu(text, g2, line);
        line++;
        text = "Back";
        drawMenu(text, g2, line);
        drawChoice(text, g2, commandNum);
    }

    /**
     * Draws the title text.
     *
     * @param text The text to be drawn.
     * @param g2   The Graphics2D object.
     */
    private void drawTitle(String text, Graphics2D g2) {
        g2.setColor(Color.GRAY);
        int centeredText = getXForCenteredText(text, g2);
        g2.drawString(text, centeredText + 6, gp.getScreenHeight() / 2 - 120);
        g2.setColor(Color.WHITE);
        g2.drawString(text, centeredText, gp.getScreenHeight() / 2 - 126);
    }

    /**
     * Draws a menu item.
     *
     * @param text The text to be drawn.
     * @param g2   The Graphics2D object.
     * @param line The line number to draw the text.
     */
    private int drawMenu(String text, Graphics2D g2, int line) {
        int centeredText = getXForCenteredText(text, g2);
        g2.drawString(text, centeredText, gp.getScreenHeight() / 2 + pressStartRegular.getSize() * (line - 1) - offset);
        line++;
        return line;
    }

    /**
     * Draws an empty checkbox.
     *
     * @param text The text associated with the checkbox.
     * @param g2   The Graphics2D object.
     * @param line The line number to draw the checkbox.
     */
    private void drawCheckbox(String text, Graphics2D g2, int line) {
        int centeredText = getXForCenteredText(text, g2);
        g2.drawRect(centeredText + (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth() + 24, gp.getScreenHeight() / 2 + pressStartRegular.getSize() * (line - 1) - 24 - offset, 24, 24);
    }

    /**
     * Draws a filled checkbox.
     *
     * @param text The text associated with the checkbox.
     * @param g2   The Graphics2D object.
     * @param line The line number to draw the checkbox.
     */
    private void drawFilledCheckbox(String text, Graphics2D g2, int line) {
        int centeredText = getXForCenteredText(text, g2);
        g2.fillRect(centeredText + (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth() + 24, gp.getScreenHeight() / 2 + pressStartRegular.getSize() * (line - 1) - 24 - offset, 24, 24);
    }

    /**
     * Draws a volume bar with a maximum scale.
     *
     * @param text     The text associated with the volume bar.
     * @param g2       The Graphics2D object.
     * @param line     The line number to draw the volume bar.
     * @param maxScale The maximum scale to determine the length of the bar.
     */
    private void drawBar(String text, Graphics2D g2, int line, int maxScale) {
        int centeredText = getXForCenteredText(text, g2);
        g2.drawRect(centeredText + (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth() + 24, gp.getScreenHeight() / 2 + pressStartRegular.getSize() * (line - 1) - 24 - offset, 24 * maxScale, 24);
    }

    /**
     * Draws a filled volume bar depending on scale.
     *
     * @param text  The text associated with the volume bar.
     * @param g2    The Graphics2D object.
     * @param line  The line number to draw the volume bar.
     * @param scale The scale to determine the length of the filled bar.
     */
    private void drawFilledBar(String text, Graphics2D g2, int line, int scale) {
        int centeredText = getXForCenteredText(text, g2);
        g2.fillRect(centeredText + (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth() + 24, gp.getScreenHeight() / 2 + pressStartRegular.getSize() * (line - 1) - 24 - offset, 24 * scale, 25);
    }

    /**
     * Draws the selection indicator.
     *
     * @param text The text associated with the selection.
     * @param g2   The Graphics2D object.
     * @param line The line number to draw the selection.
     */
    private void drawChoice(String text, Graphics2D g2, int line) {
        int centeredText = getXForCenteredText(text, g2);
        g2.drawString(">", centeredText - pressStartRegular.getSize(), gp.getScreenHeight() / 2 + pressStartRegular.getSize() * (line - 1) - offset);
    }

    /**
     * Calculates the x-coordinate for centered text.
     *
     * @param text The text to be centered.
     * @param g2   The Graphics2D object.
     * @return The x-coordinate for centered text.
     */
    private int getXForCenteredText(String text, Graphics2D g2) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.getScreenWidth() / 2 - length / 2;
    }

    /**
     * Increments the command number for menu navigation.
     */
    public void addCommandNum() {
        commandNum++;
    }

    /**
     * Decrements the command number for menu navigation.
     */
    public void removeCommandNum() {
        commandNum--;
    }

    /**
     * Gets the current command number for menu navigation.
     *
     * @return The current command number.
     */
    public int getCommandNum() {
        return commandNum;
    }

    /**
     * Sets the command number for menu navigation.
     *
     * @param commandNum The command number to set.
     */
    public void setCommandNum(int commandNum) {
        this.commandNum = commandNum;
    }
}
