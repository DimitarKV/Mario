package types;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.sound.sampled.*;

/**
 * Contains a number of different sounds used in the game and handles their loading.
 */
public class Sound {
    private Map<String, Clip> clips;
    private static Sound instance = null;

    /**
     * Constructor for the game sound.
     */
    private Sound() {
        this.clips = new HashMap<>();

        try {
            File root = new File("./resources/sounds/");
            if (root.listFiles() != null) {
                for (var soundFile : root.listFiles()) {
                    AudioInputStream currentAudio = AudioSystem.getAudioInputStream(soundFile);
                    Clip currentClip = AudioSystem.getClip();
                    currentClip.open(currentAudio);
                    clips.put(
                            soundFile
                                    .getName()
                                    .substring(0, soundFile.getName().lastIndexOf('.')),
                            currentClip
                    );
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Play the specified file from the beginning.
     * @param file to play
     */
    public void play(String file) {
        if (clips.get(file).isRunning()) {
            clips.get(file).stop();
        }
        clips.get(file).setFramePosition(0);
        clips.get(file).start();
    }

    public void resume(String file) {
        clips.get(file).start();
    }

    public void stop(String file) {
        clips.get(file).stop();
    }

    public void loop(String file) {
        clips.get(file).loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Mute all clips.
     */
    public void mute() {
        for (var clip : this.clips.entrySet()) {
            ((BooleanControl)
                    clip
                            .getValue()
                            .getControl(BooleanControl.Type.MUTE))
                    .setValue(!((BooleanControl)
                            clip.getValue().getControl(BooleanControl.Type.MUTE))
                            .getValue());
        }
    }

    /**
     * Stop all clips.
     */
    public void stopAll() {
        for (var clip : this.clips.entrySet()) {
            clip.getValue().stop();
        }
    }

    /**
     * Create a singleton instance for the class.
     * @return the instance.
     */
    public static Sound getInstance() {
        if (instance == null) {
            instance = new Sound();
        }
        return instance;
    }

    /**
     * Set volume for all clips.
     * @param value from 0 to 100.
     */
    public void setCurrentVolume(int value) {
        for (var clip : clips.entrySet()) {
            var control = (FloatControl)clip.getValue().getControl(FloatControl.Type.MASTER_GAIN);
            float range = control.getMaximum() - -30;
            float normalValue = (float) value / 100;
            control.setValue(range * normalValue - 30);
        }
    }
}
