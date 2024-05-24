package keyInputs;

import gameGraphics.GamePanel;
import gameGraphics.GameState;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    GamePanel gp;
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
        if (gp.getGameState().equals(GameState.titleState)) {
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
                        gp.setGameState(GameState.playState);
                    }
                    if(gp.getCommandNum() == 1) {
                        // add later
                    }
                    if(gp.getCommandNum() == 2){
                        // add later
                    }
                    if(gp.getCommandNum() == 3){
                        System.exit(0);
                    }
                }
            }
        }
        if (gp.getGameState().equals(GameState.playState)) {
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
                    if (gp.getGameState().equals(GameState.pauseState)) {
                        gp.setGameState(GameState.playState);
                    } else if (gp.getGameState().equals(GameState.playState)) {
                        gp.setGameState(GameState.pauseState);
                    }
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (gp.getGameState().equals(GameState.playState)) {
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