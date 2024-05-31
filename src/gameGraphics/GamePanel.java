package gameGraphics;

import configLoader.Config;
import entity.Ball;
import entity.Computer;
import entity.Paddle;
import keyInputs.KeyHandler;
import entity.Player;
import main.Main;
import sound.Sound;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * GamePanel is the main class responsible for the game graphics and logic.
 * It extends JPanel and implements Runnable to allow the game to run in its own thread.
 */
public class GamePanel extends JPanel implements Runnable {
    // Screen settings
    private final int screenWidth = 800;
    private final int screenHeight = 450;

    protected final KeyHandler keyH = new KeyHandler(this);
    private final Sound sound;
    private final Config config = new Config(this);
    private Thread gameThread;
    private final Paddle paddle1 = new Player(this, keyH, true);
    private Paddle paddle2;
    private Ball ball;
    private final int FPS = 60;
    private long drawTime;
    private GameState gameState;
    protected final UI ui = new UI(this);
    private Image background;
    private int screenWidth2 = screenWidth; // For fullscreen
    private int screenHeight2 = screenHeight;
    private BufferedImage tempScreen;
    private Graphics2D g2;
    private boolean fullScreenOn;

    /**
     * Constructor initializes the GamePanel.
     * Sets preferred size, background color, and adds key listeners.
     */
    public GamePanel() {
        sound = new Sound();
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    /**
     * Sets up the game by preloading images and setting the initial game state.
     */
    public void setupGame() {
        fullScreenOn = Main.window.isUndecorated();
        preloadImages();
        setGameState(GameState.TITLE_STATE);
        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempScreen.getGraphics();
        if (fullScreenOn) {
            setFullscreen();
        }
    }

    /**
     * Preloads necessary images such as the background and icon.
     */
    public void preloadImages() {
        try {
            BufferedImage image = ImageIO.read(new File("res/icon/Icon.png"));
            Main.window.setIconImage(image);
            background = ImageIO.read(new File("res/background/Background.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Starts the game thread which runs the game loop.
     */
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * Main game loop runs at the specified FPS.
     * Updates and renders the game state.
     */
    @Override
    public void run() {
        double drawInternal = 1000000000.0 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        while (gameThread.isAlive()) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInternal;
            timer += currentTime - lastTime;
            lastTime = currentTime;
            if (delta >= 1) {
                update();
                drawToTempScreen(); // Draw everything to the buffered image
                drawToScreen(); // Draw the buffered image to the screen
                delta--;
            }
            if (timer >= 1000000000) {
                timer = 0;
            }
        }
    }

    /**
     * Updates the game state depending on the current game state.
     */
    public void update() {
        if (getGameState().equals(GameState.PVP_PLAY_STATE) || getGameState().equals(GameState.PVC_PLAY_STATE)) {
            if (!Main.window.isFocused()) {
                setGameState(GameState.PAUSE_STATE);
            }
            paddle1.update();
            paddle2.update();
            ball.update();
        }
    }

    /**
     * Sets the game to fullscreen mode.
     */
    public void setFullscreen() {
        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();
        graphicsDevice.setFullScreenWindow(Main.window);
        screenWidth2 = Main.window.getWidth();
        screenHeight2 = Main.window.getHeight();
    }

    /**
     * Draws all game elements to a temporary screen.
     */
    public void drawToTempScreen() {
        if (keyH.isCheckDrawTime()) {
            drawTime = System.nanoTime();
        }
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, getWidth(), getHeight());
        if (getGameState().equals(GameState.PVP_PLAY_STATE) || getGameState().equals(GameState.PAUSE_STATE) || getGameState().equals(GameState.MENU_STATE) || getGameState().equals(GameState.PVC_PLAY_STATE)) {
            if (getGameState().equals(GameState.PVP_PLAY_STATE) || getGameState().equals(GameState.PAUSE_STATE) || getGameState().equals(GameState.PVC_PLAY_STATE)) {
                g2.drawImage(background, 0, 0, screenWidth, screenHeight, null);
            }
            paddle1.draw(g2);
            paddle2.draw(g2);
            ball.draw(g2);
            ui.draw(g2, ball);
        } else {
            ui.draw(g2, ball);
        }
        if (keyH.isCheckDrawTime()) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawTime;
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24f));
            g2.drawString("Draw time:" + passed, 10, 400);
        }
    }

    /**
     * Draws the temporary screen to the main screen.
     */
    public void drawToScreen() {
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();
    }

    /**
     * Returns the current command number from the UI.
     *
     * @return the current command number
     */
    public int getCommandNum() {
        return ui.getCommandNum();
    }

    /**
     * Sets the command number in the UI.
     *
     * @param num the command number to set
     */
    public void setCommandNum(int num) {
        ui.setCommandNum(num);
    }

    /**
     * Increases the command number and plays a sound effect.
     */
    public void addCommandNum() {
        playSE(0);
        ui.addCommandNum();
    }

    /**
     * Decreases the command number and plays a sound effect.
     */
    public void removeCommandNum() {
        playSE(0);
        ui.removeCommandNum();
    }

    /**
     * Plays a sound effect.
     *
     * @param i the index of the sound effect to play
     */
    public void playSE(int i) {
        sound.setFile(i);
        sound.play();
    }

    /**
     * Returns the screen width.
     *
     * @return the screen width
     */
    public int getScreenWidth() {
        return screenWidth;
    }

    /**
     * Returns the screen height.
     *
     * @return the screen height
     */
    public int getScreenHeight() {
        return screenHeight;
    }

    /**
     * Returns the current game state.
     *
     * @return the current game state
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * Sets the current game state and initializes paddles and ball depending on the game state.
     *
     * @param gameState the new game state
     */
    public void setGameState(GameState gameState) {
        if (gameState.equals(GameState.PVP_PLAY_STATE) && this.gameState.equals(GameState.TITLE_STATE)) {
            paddle2 = new Player(this, keyH, false);
            ball = new Ball(this, paddle1, paddle2);
        } else if (gameState.equals(GameState.PVC_PLAY_STATE) && this.gameState.equals(GameState.TITLE_STATE)) {
            paddle2 = new Computer(this, false);
            ball = new Ball(this, paddle1, paddle2);
        }
        this.gameState = gameState;
        setCommandNum(0);
    }

    /**
     * Checks if fullscreen mode is enabled.
     *
     * @return true if fullscreen mode is enabled, false otherwise
     */
    public boolean isFullScreenOn() {
        return fullScreenOn;
    }

    /**
     * Enables or disables fullscreen mode.
     *
     * @param fullScreenOn true to enable fullscreen mode, false to disable
     */
    public void setFullScreenOn(boolean fullScreenOn) {
        this.fullScreenOn = fullScreenOn;
    }

    /**
     * Toggles fullscreen mode on or off.
     */
    public void switchFullScreen() {
        fullScreenOn = !fullScreenOn;
    }

    /**
     * Returns the current volume scale.
     *
     * @return the volume scale
     */
    public int getVolumeScale() {
        return sound.getVolumeScale();
    }

    /**
     * Sets the volume scale.
     *
     * @param scale the new volume scale
     */
    public void setVolumeScale(int scale) {
        sound.setVolumeScale(scale);
    }

    /**
     * Increases the volume and plays a sound effect.
     */
    public void addVolume() {
        sound.addVolume();
        playSE(0);
    }

    /**
     * Decreases the volume and plays a sound effect.
     */
    public void removeVolume() {
        sound.removeVolume();
        playSE(0);
    }

    /**
     * Saves the current configuration.
     */
    public void saveConfig() {
        config.saveConfig();
    }

    /**
     * Loads the configuration.
     */
    public void loadConfig() {
        config.loadConfig();
    }

    /**
     * Returns the Y-coordinate of the ball.
     *
     * @return the Y-coordinate of the ball
     */
    public int getBallY() {
        return ball.getY();
    }

    /**
     * Returns the height of the ball.
     *
     * @return the height of the ball
     */
    public int getBallHeight() {
        return ball.getHeight();
    }
}