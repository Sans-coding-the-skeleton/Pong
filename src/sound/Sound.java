package sound;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class Sound {
    private Clip clip;
    private final URL[] soundURL = new URL[10];
    FloatControl floatControl;
    int volumeScale = 3;
    float volume;

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
            floatControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            checkVolume();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    public void play() {
        clip.start();
    }

    public void checkVolume() {
        switch (volumeScale) {
            case 0:
                volume = -80f;
                break;
            case 1:
                volume = -20f;
                break;
            case 2:
                volume = -12f;
                break;
            case 3:
                volume = -5f;
                break;
            case 4:
                volume = 1f;
                break;
            case 5:
                volume = 6f;
                break;
        }
        floatControl.setValue(volume);
    }

    public int getVolumeScale() {
        return volumeScale;
    }

    public void setVolumeScale(int volumeScale) {
        this.volumeScale = volumeScale;
    }

    public void addVolume() {
        if (volumeScale < 5) {
            volumeScale++;
        }
    }

    public void removeVolume() {
        if (volumeScale > 0) {
            volumeScale--;
        }
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
