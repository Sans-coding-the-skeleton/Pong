package main;

import gameGraphics.GamePanel;

import javax.swing.*;

/**
 * The Main class is the entry point of the Pong game application.
 * It initializes the game window and starts the game panel.
 */
public class Main {

    /**
     * The main window frame of the game.
     */
    public static JFrame window;

    /**
     * The main method of the application.
     * Initializes the game window, sets up the game panel,
     * and starts the game thread.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        // Create the main window frame
        window = new JFrame();
        window.setResizable(false);
        window.setTitle("Pong");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Create the game panel
        GamePanel gamePanel = new GamePanel();
        // Add the game panel to the window
        window.add(gamePanel);
        // Load the configuration settings
        gamePanel.loadConfig();
        // Set window to full screen if configured
        if (gamePanel.isFullScreenOn()) {
            window.setUndecorated(true);
        }
        // Pack the window and center it on the screen
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        // Set up the game and start the game thread
        gamePanel.setupGame();
        gamePanel.startGameThread();
    }
}
