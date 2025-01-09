package com.jonat15.game;

import javax.swing.*;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;


public class Start extends JFrame {
    public static Clip clip;
    public static JFrame window;
    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        Game game = new Game();
        window = new JFrame("2048");
        Menu menu = new Menu(window);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.add(menu);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        // Load the WAV file
        File audioFile = new File("src/Kahoot Lobby Music (HD).wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
        // Get a sound clip resource
        clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        // Let the music play for its duration (optional, for demonstration)// Wait until the music finishes
        }
    }

