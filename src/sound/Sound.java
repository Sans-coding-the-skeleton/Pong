package sound;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

/**
 * The Sound class represents sound effects to be used in the game.
 */
public class Sound {
    private Clip clip;
    private final URL[] soundURL = new URL[5];
    FloatControl floatControl;
    int volumeScale = 3;
    float volume;

    /**
     * Constructor for the Sound class. Initializes sound URLs for various sound effects.
     */
    public Sound() {
        soundURL[0] = getClass().getResource("Choice.wav");
        soundURL[1] = getClass().getResource("EnterChoice.wav");
        soundURL[2] = getClass().getResource("Quit.wav");
        soundURL[3] = getClass().getResource("Bounce.wav");
        soundURL[4] = getClass().getResource("Goal.wav");
    }

    /**
     * Sets the sound file to be played.
     *
     * @param i The index of the sound URL in the array.
     */
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

    /**
     * Plays the sound.
     */
    public void play() {
        clip.start();
    }

    /**
     * Adjusts the volume based on the volume scale.
     */
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

    /**
     * Gets the current volume scale.
     *
     * @return The volume scale.
     */
    public int getVolumeScale() {
        return volumeScale;
    }

    /**
     * Sets the volume scale.
     *
     * @param volumeScale The volume scale to set.
     */
    public void setVolumeScale(int volumeScale) {
        this.volumeScale = volumeScale;
    }

    /**
     * Increases the volume scale by one.
     */
    public void addVolume() {
        if (volumeScale < 5) {
            volumeScale++;
        }
    }

    /**
     * Decreases the volume scale by one.
     */
    public void removeVolume() {
        if (volumeScale > 0) {
            volumeScale--;
        }
    }
}
