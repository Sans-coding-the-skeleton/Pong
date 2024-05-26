package keyInputs;

import gameGraphics.GamePanel;
import gameGraphics.GameState;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    private final GamePanel gp;
    public boolean leftPLayerUpPressed, leftPLayerDownPressed, leftPLayerLeftPressed, leftPlayerRightPressed;
    public boolean rightPLayerUpPressed, rightPLayerDownPressed, rightPLayerLeftPressed, rightPlayerRightPressed;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //not used
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (gp.getGameState().equals(GameState.TITLE_STATE)) {
            switch (code) {
                case KeyEvent.VK_W, KeyEvent.VK_UP -> {
                    gp.removeCommandNum();
                    if (gp.getCommandNum() < 0) {
                        gp.setCommandNum(3);
                    }
                }
                case KeyEvent.VK_S, KeyEvent.VK_DOWN -> {
                    gp.addCommandNum();
                    if (gp.getCommandNum() > 3) {
                        gp.setCommandNum(0);
                    }
                }
                case KeyEvent.VK_ENTER -> {
                    if (gp.getCommandNum() == 0) {
                        gp.playSE(1);
                        gp.setGameState(GameState.PLAY_STATE);
                    }
                    if (gp.getCommandNum() == 1) {
                        gp.playSE(1);
                        // add later
                    }
                    if (gp.getCommandNum() == 2) {
                        gp.setGameState(GameState.SETTINGS_STATE);
                        gp.setCommandNum(0);
                    }
                    if (gp.getCommandNum() == 3) {
                        gp.playSE(2);
                        System.exit(0);
                    }
                }
            }
        } else if (gp.getGameState().equals(GameState.SETTINGS_STATE)) {
            switch (code) {
                case KeyEvent.VK_W, KeyEvent.VK_UP -> {
                    gp.removeCommandNum();
                    if (gp.getCommandNum() < 0) {
                        gp.setCommandNum(2);
                    }
                }
                case KeyEvent.VK_S, KeyEvent.VK_DOWN -> {
                    gp.addCommandNum();
                    if (gp.getCommandNum() > 2) {
                        gp.setCommandNum(0);
                    }
                }
                case KeyEvent.VK_ENTER -> {
                    if (gp.getCommandNum() == 0) {
                        // add later
                    }
                    if (gp.getCommandNum() == 1) {
                        // add later
                    }
                    if (gp.getCommandNum() == 2) {
                        gp.setGameState(GameState.TITLE_STATE);
                        gp.setCommandNum(0);
                    }
                }
            }
        }
        if (gp.getGameState().equals(GameState.PLAY_STATE)) {
            switch (code) {
                case KeyEvent.VK_W -> leftPLayerUpPressed = true;
                case KeyEvent.VK_A -> leftPLayerLeftPressed = true;
                case KeyEvent.VK_S -> leftPLayerDownPressed = true;
                case KeyEvent.VK_D -> leftPlayerRightPressed = true;
                case KeyEvent.VK_UP -> rightPLayerUpPressed = true;
                case KeyEvent.VK_LEFT -> rightPLayerLeftPressed = true;
                case KeyEvent.VK_DOWN -> rightPLayerDownPressed = true;
                case KeyEvent.VK_RIGHT -> rightPlayerRightPressed = true;
                case KeyEvent.VK_P -> {
                    if (gp.getGameState().equals(GameState.PAUSE_STATE)) {
                        gp.setGameState(GameState.PLAY_STATE);
                    } else if (gp.getGameState().equals(GameState.PLAY_STATE)) {
                        gp.setGameState(GameState.PAUSE_STATE);
                    }
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (gp.getGameState().equals(GameState.PLAY_STATE)) {
            int code = e.getKeyCode();
            switch (code) {
                case KeyEvent.VK_W -> leftPLayerUpPressed = false;
                case KeyEvent.VK_A -> leftPLayerLeftPressed = false;
                case KeyEvent.VK_S -> leftPLayerDownPressed = false;
                case KeyEvent.VK_D -> leftPlayerRightPressed = false;
                case KeyEvent.VK_UP -> rightPLayerUpPressed = false;
                case KeyEvent.VK_LEFT -> rightPLayerLeftPressed = false;
                case KeyEvent.VK_DOWN -> rightPLayerDownPressed = false;
                case KeyEvent.VK_RIGHT -> rightPlayerRightPressed = false;
            }
        }
    }
}