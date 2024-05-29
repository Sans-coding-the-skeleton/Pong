package main;

import gameGraphics.GamePanel;

import javax.swing.*;

public class Main {

    public static JFrame window;

    public static void main(String[] args) {
        window = new JFrame();
        window.setResizable(false);
        window.setTitle("Pong");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        gamePanel.loadConfig();
        if(gamePanel.isFullScreenOn()){
            window.setUndecorated(true);
        }
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        gamePanel.setupGame();
        gamePanel.startGameThread();
    }
}