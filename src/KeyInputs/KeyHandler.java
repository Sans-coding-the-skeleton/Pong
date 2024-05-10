package KeyInputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean leftPLayerUpPressed, leftPLayerDownPressed, leftPLayerLeftPressed, leftPlayerRightPressed;
    public boolean rightPLayerUpPressed, rightPLayerDownPressed, rightPLayerLeftPressed, rightPlayerRightPressed;

    @Override
    public void keyTyped(KeyEvent e) {
        //not used
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        switch (code) {
            case KeyEvent.VK_W -> leftPLayerUpPressed = true;
            case KeyEvent.VK_A -> leftPLayerLeftPressed = true;
            case KeyEvent.VK_S -> leftPLayerDownPressed = true;
            case KeyEvent.VK_D -> leftPlayerRightPressed = true;
            case KeyEvent.VK_UP -> rightPLayerUpPressed = true;
            case KeyEvent.VK_LEFT -> rightPLayerLeftPressed = true;
            case KeyEvent.VK_DOWN -> rightPLayerDownPressed = true;
            case KeyEvent.VK_RIGHT -> rightPlayerRightPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
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