package gameGraphics;

import entity.Ball;
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
    // TODO Player vs Computer
    // TODO Settings
    // TODO DISPLAY FPS ON SCREEN

    protected final KeyHandler keyH = new KeyHandler(this);
    private final Sound sound;
    private Thread gameThread;
    private final Player player1 = new Player(this, keyH, true);
    private final Player player2 = new Player(this, keyH, false);
    private final Ball ball = new Ball(this, player1, player2);
    private final int FPS = 60;
    private long drawTime;
    private GameState gameState;
    private final UI ui = new UI(this);
    private Image background;
    //for fullscreen
    private int screenWidth2 = screenWidth;
    private int screenHeight2 = screenHeight;
    private BufferedImage tempScreen;
    private Graphics2D g2;

    public GamePanel() {
        sound = new Sound();
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {
        preloadImages();
        setGameState(GameState.TITLE_STATE);
        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempScreen.getGraphics();
        setFullscreen();
    }

    public void preloadImages() {
        try {
            background = ImageIO.read(new File("res/background/Background02.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // background = new ImageIcon(Objects.requireNonNull(getClass().getResource("/background/Background02.png"))).getImage();
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
        if (getGameState().equals(GameState.PLAY_STATE)) {
            player1.update();
            player2.update();
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
            //DEBUG
            drawTime = System.nanoTime();
        }
        if (getGameState().equals(GameState.PLAY_STATE) || getGameState().equals(GameState.PAUSE_STATE) || getGameState().equals(GameState.MENU_STATE)) {
            ui.draw(g2, ball);
            if (getGameState().equals(GameState.PLAY_STATE) || getGameState().equals(GameState.PAUSE_STATE)) {
                g2.drawImage(background, 0, 0, screenWidth, screenHeight, null);
            }
            player1.draw(g2);
            player2.draw(g2);
            ball.draw(g2);
        } else {
            ui.draw(g2, ball);
        }
        if (keyH.isCheckDrawTime()) {
            //DEBUG
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawTime;
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24f));
            g2.drawString("Draw time:" + passed, 10, 400);
            //   System.out.println("Draw time:" + passed);
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
        /*
        if (gameState.equals(GameState.PLAY_STATE) && getGameState().equals(GameState.PAUSE_STATE)) {
            gameState = GameState.COUNTDOWN_STATE;
        }
         */
        this.gameState = gameState;
        setCommandNum(0);
    }
}