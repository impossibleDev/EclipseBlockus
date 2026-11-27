package view;

import javax.sound.sampled.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

// yanjun & melinda
public class SoundService {
    // Stores pre-loaded audio clips in memory
    private static final Map<String, Clip> soundMap = new HashMap<>();
    private static boolean musicOn = true;
    private static boolean sfxOn = true;

    // 1. Call this once on startup to load audio into RAM
    public static void loadSound(String name, String filePath) {
        try {
            File audioFile = new File(filePath);
            if (!audioFile.exists()) {
                System.err.println("Audio file not found: " + filePath);
                return;
            }
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            soundMap.put(name, clip);
        } catch (Exception e) {
            System.out.println("Error loading sound into memory: " + filePath);
            e.printStackTrace();
        }
    }

    // 2. Play background music (loops continuously)
    public static void playMusic(String name) {
        Clip clip = soundMap.get(name);
        if (clip != null && musicOn) {
            if (!clip.isRunning()) {
                clip.setFramePosition(0);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                clip.start();
            }
        }
    }

    
    public static void playSound(String name) {
        if (!sfxOn) return;

        Clip clip = soundMap.get(name);
        if (clip != null) {
            clip.stop();              // stop if it is currently running
            clip.setFramePosition(0); // rewind to beginning
            clip.start();             // replay
        }
    }

    // 4. Toggle background music
    public static void setMusicOn(boolean on) {
        musicOn = on;
        Clip clip = soundMap.get("bgm");
        if (clip != null) {
            if (!musicOn && clip.isRunning()) {
                clip.stop();
            } else if (musicOn && !clip.isRunning()) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                clip.start();
            }
        }
    }

    // 5. Toggle sound effects
    public static void setSfxOn(boolean on) {
        sfxOn = on;
    }
}