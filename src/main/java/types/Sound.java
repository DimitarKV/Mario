package types;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;
import java.util.TreeMap;

public class Sound {
    private Clip clip;
    private float previousVolume;
    private float currentVolume;
    private FloatControl floatControl;
    private AudioInputStream audio;
    private TreeMap<String,File> sounds;

    public Sound(){
        previousVolume = 0;
        currentVolume = 0;
        sounds = new TreeMap<>();
        sounds.put("themeSong",new File("./resources/sounds/themeSong.wav"));
        sounds.put("jump", new File("./resources/sounds/jump.wav"));
    }
    public void setFile(String fileKey){
        try{
            audio = AudioSystem.getAudioInputStream(sounds.get(fileKey));
            clip = AudioSystem.getClip();
            clip.open(audio);
            floatControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
        } catch (Exception e){
            System.out.println("Audio error");
        }
    }
    public void play(String fileKey){
        clip.start();
    }

    public void loop(String fileKey){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

}
