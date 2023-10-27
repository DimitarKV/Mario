package types;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Sound {
    private Clip clip;
    private float currentVolume;
    private FloatControl floatControl;
    private AudioInputStream audio;
    private Map<String,File> sounds;
    private static Sound instance = null;

    public Sound() {
        currentVolume = 0;
        sounds = new HashMap<>();
        sounds.put("themeSong", new File("./resources/sounds/themeSong.wav"));
        sounds.put("jump", new File("./resources/sounds/jump.wav"));
        sounds.put("die", new File("./resources/sounds/die.wav"));
        sounds.put("win", new File("./resources/sounds/win.wav"));
        sounds.put("coin", new File("./resources/sounds/coin.wav"));
    }

    public void setFile(String fileKey) {
        try {
            audio = AudioSystem.getAudioInputStream(sounds.get(fileKey));
            clip = AudioSystem.getClip();
            clip.open(audio);
            floatControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        } catch (Exception e) {
            System.out.println("Audio error");
        }
    }

    public AudioInputStream getAudio() {
        return audio;
    }

    public void play(String fileKey) {
        clip.start();
    }

    public void stop(String fileKey) {
        clip.stop();
    }

    public void loop(String fileKey) {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public FloatControl getFloatControl() {
        return floatControl;
    }

    public void setCurrentVolume(float currentVolume) {
        this.currentVolume = currentVolume;
    }

    public static Sound getInstance() {
        if (instance == null) {
            Sound.instance = new Sound();
        }
        return instance;
    }
}
