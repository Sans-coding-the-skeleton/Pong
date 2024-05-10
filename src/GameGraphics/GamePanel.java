package GameGraphics;

import KeyInputs.KeyHandler;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    // screen settings
    final int screenWidth = 800;
    final int screenHeight = 450;
    final int scale = 2;

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;

    int leftPlayerX = 10;
    int leftPlayerY = 100;
    int rightPlayerX = screenWidth - 10;
    int rightPlayerY = screenHeight - 100;
    int leftPlayerPaddleWidth = 10;
    int rightPlayerPaddleWidth = 10;

    int leftPlayerPaddleHeight = 100;
    int rightPlayerPaddleHeight = 100;

    int leftPlayerSpeed = 4;
    int rightPlayerSpeed = 4;

    //FPS
    int FPS = 60;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        while (gameThread.isAlive()) {
            //1. UPDATE: update information such as character position
            update();
            //2. DRAW: draw the screen with the updated information
            repaint();


        }
    }

    public void update() {
        if (keyH.leftPLayerUpPressed) {
            leftPlayerY -= leftPlayerSpeed;
        }
        if (keyH.leftPLayerDownPressed) {
            leftPlayerY += leftPlayerSpeed;
        }
        if (keyH.rightPLayerUpPressed) {
            rightPlayerY -= rightPlayerSpeed;
        }
        if (keyH.rightPLayerDownPressed) {
            rightPlayerY += rightPlayerSpeed;
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);
        g2.fillRect(leftPlayerX, leftPlayerY, leftPlayerPaddleWidth, leftPlayerPaddleHeight);
        g2.setColor(Color.WHITE);
        g2.fillRect(rightPlayerX, rightPlayerY, rightPlayerPaddleWidth, rightPlayerPaddleHeight);
        g2.dispose();
    }
}