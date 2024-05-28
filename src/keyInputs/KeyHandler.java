package keyInputs;

import gameGraphics.GamePanel;
import gameGraphics.GameState;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    private final GamePanel gp;
    private boolean leftPLayerUpPressed, leftPLayerDownPressed;
    //private boolean leftPLayerLeftPressed, leftPlayerRightPressed;
    private boolean rightPLayerUpPressed, rightPLayerDownPressed;
    //private boolean  rightPLayerLeftPressed, rightPlayerRightPressed;
    private boolean checkDrawTime = false;
    private boolean displayFPS = false;

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
                    }
                    if (gp.getCommandNum() == 3) {
                        gp.playSE(2);
                        gp.setGameState(GameState.CONFIRM_EXIT_STATE);
                        gp.setCommandNum(2);
                    }
                }
                case KeyEvent.VK_ESCAPE -> {
                    gp.playSE(2);
                    gp.setGameState(GameState.CONFIRM_EXIT_STATE);
                    gp.setCommandNum(2);
                }
            }
        } else if (gp.getGameState().equals(GameState.SETTINGS_STATE)) {
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
                        // add later
                    }
                    if (gp.getCommandNum() == 1) {
                        // add later
                    }
                    if (gp.getCommandNum() == 2) {
                        gp.setGameState(GameState.CONTROLS_STATE);
                        gp.setCommandNum(5);
                    }
                    if (gp.getCommandNum() == 3) {
                        gp.setGameState(GameState.TITLE_STATE);
                    }
                }
                case KeyEvent.VK_ESCAPE -> gp.setGameState(GameState.TITLE_STATE);
            }
        } else if (gp.getGameState().equals(GameState.CONTROLS_STATE)) {
            switch (code) {
                case KeyEvent.VK_ESCAPE, KeyEvent.VK_ENTER -> gp.setGameState(GameState.SETTINGS_STATE);
            }
        } else if (gp.getGameState().equals(GameState.CONFIRM_EXIT_STATE)) {
            switch (code) {
                case KeyEvent.VK_W, KeyEvent.VK_UP -> {
                    gp.removeCommandNum();
                    if (gp.getCommandNum() < 1) {
                        gp.setCommandNum(2);
                    }
                }
                case KeyEvent.VK_S, KeyEvent.VK_DOWN -> {
                    gp.addCommandNum();
                    if (gp.getCommandNum() > 2) {
                        gp.setCommandNum(1);
                    }
                }
                case KeyEvent.VK_ENTER -> {
                    if (gp.getCommandNum() == 1) {
                        System.exit(0);
                    }
                    if (gp.getCommandNum() == 2) {
                        gp.setGameState(GameState.TITLE_STATE);
                    }
                }
                case KeyEvent.VK_ESCAPE -> System.exit(0);
            }
        } else if (gp.getGameState().equals(GameState.PLAY_STATE)) {
            switch (code) {
                case KeyEvent.VK_W -> leftPLayerUpPressed = true;
                //    case KeyEvent.VK_A -> leftPLayerLeftPressed = true;
                case KeyEvent.VK_S -> leftPLayerDownPressed = true;
                //    case KeyEvent.VK_D -> leftPlayerRightPressed = true;
                case KeyEvent.VK_UP -> rightPLayerUpPressed = true;
                //    case KeyEvent.VK_LEFT -> rightPLayerLeftPressed = true;
                case KeyEvent.VK_DOWN -> rightPLayerDownPressed = true;
                //    case KeyEvent.VK_RIGHT -> rightPlayerRightPressed = true;
                case KeyEvent.VK_P -> gp.setGameState(GameState.PAUSE_STATE);
                case KeyEvent.VK_ESCAPE -> gp.setGameState(GameState.MENU_STATE);
            }
        } else if (gp.getGameState().equals(GameState.PAUSE_STATE)) {
            switch (code) {
                case KeyEvent.VK_P -> gp.setGameState(GameState.PLAY_STATE);
                case KeyEvent.VK_ESCAPE -> gp.setGameState(GameState.MENU_STATE);
            }
        } else if (gp.getGameState().equals(GameState.MENU_STATE)) {
            switch (code) {
                case KeyEvent.VK_P -> gp.setGameState(GameState.PAUSE_STATE);
                case KeyEvent.VK_W, KeyEvent.VK_UP -> {
                    gp.removeCommandNum();
                    if (gp.getCommandNum() < 0) {
                        gp.setCommandNum(1);
                    }
                }
                case KeyEvent.VK_S, KeyEvent.VK_DOWN -> {
                    gp.addCommandNum();
                    if (gp.getCommandNum() > 1) {
                        gp.setCommandNum(0);
                    }
                }
                case KeyEvent.VK_ENTER -> {
                    if (gp.getCommandNum() == 0) {
                        gp.setGameState(GameState.PLAY_STATE);
                    }
                    if (gp.getCommandNum() == 1) {
                        gp.playSE(2);
                        gp.setGameState(GameState.TITLE_STATE);
                    }
                }
                case KeyEvent.VK_ESCAPE -> gp.setGameState(GameState.PLAY_STATE);
            }
        }
        if (code == KeyEvent.VK_T) {
            checkDrawTime = !checkDrawTime;
        }
        if (code == KeyEvent.VK_F) {
            displayFPS = !displayFPS;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (gp.getGameState().equals(GameState.PLAY_STATE)) {
            int code = e.getKeyCode();
            switch (code) {
                case KeyEvent.VK_W -> leftPLayerUpPressed = false;
                //    case KeyEvent.VK_A -> leftPLayerLeftPressed = false;
                case KeyEvent.VK_S -> leftPLayerDownPressed = false;
                //   case KeyEvent.VK_D -> leftPlayerRightPressed = false;
                case KeyEvent.VK_UP -> rightPLayerUpPressed = false;
                //    case KeyEvent.VK_LEFT -> rightPLayerLeftPressed = false;
                case KeyEvent.VK_DOWN -> rightPLayerDownPressed = false;
                //    case KeyEvent.VK_RIGHT -> rightPlayerRightPressed = false;
            }
        }
    }

    public boolean isLeftPLayerUpPressed() {
        return leftPLayerUpPressed;
    }

    public boolean isLeftPLayerDownPressed() {
        return leftPLayerDownPressed;
    }

    public boolean isRightPLayerUpPressed() {
        return rightPLayerUpPressed;
    }

    public boolean isRightPLayerDownPressed() {
        return rightPLayerDownPressed;
    }

    public boolean isCheckDrawTime() {
        return checkDrawTime;
    }

    public boolean isDisplayFPS() {
        return displayFPS;
    }
}