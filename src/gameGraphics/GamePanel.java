package gameGraphics;

import config.Config;
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

public class GamePanel extends JPanel implements Runnable {

    //screen settings
    private final int screenWidth = 800;
    private final int screenHeight = 450;
    // TODO DISPLAY FPS ON SCREEN
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
    //for fullscreen
    private int screenWidth2 = screenWidth;
    private int screenHeight2 = screenHeight;
    private BufferedImage tempScreen;
    private Graphics2D g2;
    private boolean fullScreenOn;

    public GamePanel() {
        sound = new Sound();
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        fullScreenOn = Main.window.isUndecorated();
    }

    public void setupGame() {
        preloadImages();
        setGameState(GameState.TITLE_STATE);
        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempScreen.getGraphics();
        if (fullScreenOn) {
            setFullscreen();
        }
    }

    public void preloadImages() {
        try {
            background = ImageIO.read(new File("res/background/Background02.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }  // background = new ImageIcon(Objects.requireNonNull(getClass().getResource("/background/Background02.png"))).getImage();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    //delta method
    @Override
    public void run() {
        double drawInternal = 1000000000.0 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;
        while (gameThread.isAlive()) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInternal;
            timer += currentTime - lastTime;
            lastTime = currentTime;
            if (delta >= 1) {
                update();
                drawToTempScreen(); //draw everything to the buffered image
                drawToScreen(); //draw the buffered image to the screen
                delta--;
                drawCount++;
            }
            if (timer >= 1000000000) {
                if (keyH.isDisplayFPS()) {
                    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24f));
                    g2.drawString("FPS: " + drawCount, 10, 300);
                    System.out.println("FPS:" + drawCount);
                }
                drawCount = 0;
                timer = 0;
            }
        }
    }

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

    public void setFullscreen() {
        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();
        graphicsDevice.setFullScreenWindow(Main.window);
        screenWidth2 = Main.window.getWidth();
        screenHeight2 = Main.window.getHeight();
    }

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

    public void drawToScreen() {
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();
    }

    public int getCommandNum() {
        return ui.getCommandNum();
    }

    public void setCommandNum(int num) {
        ui.setCommandNum(num);
    }

    public void addCommandNum() {
        playSE(0);
        ui.addCommandNum();
    }

    public void removeCommandNum() {
        playSE(0);
        ui.removeCommandNum();
    }

    public void playSE(int i) {
        sound.setFile(i);
        sound.play();
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public GameState getGameState() {
        return gameState;
    }

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

    public boolean isFullScreenOn() {
        return fullScreenOn;
    }

    public void setFullScreenOn(boolean fullScreenOn) {
        this.fullScreenOn = fullScreenOn;
    }

    public void switchFullScreen() {
        fullScreenOn = !fullScreenOn;
    }

    public int getVolumeScale() {
        return sound.getVolumeScale();
    }

    public void setVolumeScale(int scale) {
        sound.setVolumeScale(scale);
    }

    public void addVolume() {
        sound.addVolume();
        playSE(0);
    }

    public void removeVolume() {
        sound.removeVolume();
        playSE(0);
    }

    public void saveConfig() {
        config.saveConfig();
    }

    public void loadConfig() {
        config.loadConfig();
    }

    public int getBallY() {
        return ball.getY();
    }

    public int getBallHeight() {
        return ball.getHeight();
    }
}