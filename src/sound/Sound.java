package sound;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class Sound {
    private Clip clip;
    private final URL[] soundURL = new URL[10];

    public Sound() {
        soundURL[0] = getClass().getResource("Choice01.wav");
        soundURL[1] = getClass().getResource("EnterChoice.wav");
        soundURL[2] = getClass().getResource("Quit.wav");
        soundURL[3] = getClass().getResource("Bounce03.wav");
        soundURL[4] = getClass().getResource("Goal02.wav");
        soundURL[5] = getClass().getResource("GoalFast.wav");
    }

    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    public void play() {
        clip.start();
    }
/*
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }
    */
}
