package gameGraphics;

import entity.Ball;
import keyInputs.KeyHandler;
import entity.Player;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class GamePanel extends JPanel implements Runnable {

    // screen settings
    private final int screenWidth = 800;
    private final int screenHeight = 450;
    //  final int scale = 2; TODO

    private final KeyHandler keyH = new KeyHandler(this);
    private Thread gameThread;
    private final Player player1 = new Player(this, keyH, true);
    private final Player player2 = new Player(this, keyH, false);
    private final Ball ball = new Ball(this, player1, player2);
    private final int FPS = 60;
    private GameState gameState;
    private final UI ui = new UI(this);

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        setGameState(GameState.playState);
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
                repaint();
                delta--;
                drawCount++;
            }
            if (timer >= 1000000000) {
             //   System.out.println("FPS:" + drawCount);
                drawCount = 0;
                timer = 0;
            }

        }
    }

    public void update() {
        if (getGameState().equals(GameState.playState)) {
            player1.update();
            player2.update();
            ball.update();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        Image background = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Background/Background02.png"))).getImage();
        g2.drawImage(background, 0, 0, 800, 450, null);
        player1.draw(g2);
        player2.draw(g2);
        ball.draw(g2);
        ui.draw(g2, ball);
        g2.dispose();
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
        this.gameState = gameState;
    }
}