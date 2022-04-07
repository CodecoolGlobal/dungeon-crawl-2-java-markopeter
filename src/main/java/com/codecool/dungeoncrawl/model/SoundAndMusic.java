package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.Main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundAndMusic {
    public static String opening = "/66350070 (online-audio-converter.com).wav";
    public static String fightSound = "/home/marko/Letöltések/mixkit-arcade-retro-jump-223.wav";
    public static synchronized void playSound(String fileName) {
        new Thread(() -> {
            try {
                Clip clip = AudioSystem.getClip();
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                        Main.class.getResourceAsStream(fileName));
                clip.open(inputStream);
                clip.start();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }).start();
    }
}
