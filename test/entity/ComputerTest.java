package entity;

import gameGraphics.GamePanel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ComputerTest {

    @Test
    void update() {
        Computer computer = new Computer(new GamePanel(), true);
    }
}