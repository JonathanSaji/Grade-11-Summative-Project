package com.jonat15.game;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Menu extends JPanel implements ActionListener {
    public static final int width = 1920;
    public static final int length = 1080;
    public static Clip WetHandsClip, ExplosionClip,StalClip;
    private boolean StalPLaying,stalShown = false;
    private boolean WetHandsPlaying,WetHandsShown = true;
    private boolean Muted = false;
    public static JFrame window;
    private static JLabel DeathLabel, WinLabel;
    private  JLabel welcome,tutorial;
    private static JLabel highScore;
    private static boolean isDead,isWon;
    private static boolean whileLoopRunning = true;
    private static JPanel panel,End_Panel,tutorial_panel,SongLibrary;
    private JButton unmute, mute, start, exit, exit_tutorial, howToPlay,PlayAgain,MainMenu,Stal,Music,WetHands,nextSong;
    private boolean exit_menu = false;
    public static Game game = new Game();
    private Font font36 = new Font("Monospaced", Font.BOLD, 36);
    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
        window = new JFrame("2048");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Start of the Audio Making...
        File audioFile = new File("src/res/Wet Hands.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
        WetHandsClip = AudioSystem.getClip();
        WetHandsClip.open(audioStream);
        WetHandsClip.loop(Clip.LOOP_CONTINUOUSLY);//Continuous Plays the Clip

        File explosionFile = new File("src/res/Explosion.wav");
        AudioInputStream ExplosionStream = AudioSystem.getAudioInputStream(explosionFile);
        ExplosionClip = AudioSystem.getClip();
        ExplosionClip.open(ExplosionStream);
        //need to start when lost or won
        File StalFile = new File("src/res/Stal - Music disc.wav");
        AudioInputStream StalStream = AudioSystem.getAudioInputStream(StalFile);
        StalClip = AudioSystem.getClip();
        StalClip.open(StalStream);
        StalClip.stop();

        panel = new JPanel();
        panel.setSize(width, length);
        panel.setBackground(Color.BLACK);
        panel.setLayout(null);
        panel.setOpaque(true);

        End_Panel = new JPanel();
        End_Panel.setBackground(Color.BLACK);

        tutorial_panel = new JPanel();
        tutorial_panel.setVisible(false);
        tutorial_panel.setBounds(10,10,600,1000);
        tutorial_panel.setBackground(Color.BLACK);
        tutorial_panel.setLayout(null);
        tutorial_panel.setOpaque(true);
        Border lineBorder = BorderFactory.createLineBorder(Color.RED, 10);
        tutorial_panel.setBorder(lineBorder);
        panel.add(tutorial_panel);

        SongLibrary = new JPanel();
        Menu menu = new Menu(panel,End_Panel,tutorial_panel,SongLibrary);
        SongLibrary.setVisible(false);
        SongLibrary.setBounds(1380,650,430,220);

        SongLibrary.setBackground(Color.darkGray);
        SongLibrary.setLayout(null);
        SongLibrary.setOpaque(true);
        Border LibraryBorder= BorderFactory.createLineBorder(Color.RED, 10);
        SongLibrary.setBorder(LibraryBorder);
        panel.add(SongLibrary);

        window.add(panel);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);

        while(whileLoopRunning) {
                GameBoard.dead = false;
                GameBoard.won = false;
                isDead = false;
                while (!isDead) {
                    if (GameBoard.dead) {
                        game.stop();
                        window.add(End_Panel);
                        ExplosionClip.start();
                        window.remove(game);
                        window.revalidate();
                        window.repaint();
                        WinLabel.setVisible(false);
                        DeathLabel.setVisible(true);
                        isDead = GameBoard.dead;
                        highScore.setText("The Highscore is: " + GameBoard.highScore);
                    }
                    if(GameBoard.won){
                        game.stop();
                        window.add(End_Panel);
                        ExplosionClip.start();
                        window.remove(game);
                        window.revalidate();
                        window.repaint();
                        WinLabel.setVisible(true);
                        DeathLabel.setVisible(false);
                        isWon = false;
                        GameBoard.won = false;
                        highScore.setText("The Highscore is: " + GameBoard.highScore);
                    }
                    System.out.println("Game Is Running");
                }
            System.out.println(isDead);
        }
    }

    public Menu(JPanel panel, JPanel end_panel,JPanel T_panel,JPanel songLibrary){
        Border SoundBorder = BorderFactory.createLineBorder(Color.YELLOW,10);
        start = new JButton();
        start.setIcon(new ImageIcon("src/res/StartButtonGif.gif"));
        start.setBounds(885, 490, 150, 100);
        start.setVisible(true);
        start.addActionListener(this);
        panel.add(start);

        // Exit Button
        exit = new JButton();
        exit.setIcon(new ImageIcon("src/res/ExitButton.jpg"));
        exit.setBounds(885, 610, 150, 100);
        exit.addActionListener(this);
        panel.add(exit);

        // How To Play Button
        howToPlay = new JButton("How To Play");
        howToPlay.setFont(font36);
        howToPlay.setBackground(Color.BLACK);
        howToPlay.setForeground(Color.WHITE);
        howToPlay.setBounds(810, 370, 300, 100);
        howToPlay.addActionListener(this);
        panel.add(howToPlay);

        //Starts Tutorial
        tutorial = new JLabel("<html>2048 is a single-player sliding tile puzzle video game"+
                "<br> written by Italian web developer Gabriele Cirulli and"+
                "<br> published on GitHub. The objective of the game is"+
                "<br> to slide numbered tiles on a grid to combine them to "+
                "<br> create a tile with the number 2048."+
                "<br> "+"<br> Directions: (Arrow Keys)"+"<br> Up: ↑ "+"<br> Down: ↓ "+"<br> Right: → "+"<br> Left: ← "+"<br>",SwingConstants.LEADING);
        tutorial.setBounds(10,10,600,1000);
        tutorial.setForeground(Color.WHITE);
        tutorial.setFont(new Font("Monospaced", Font.BOLD, 36));
        tutorial.setVisible(true);
        T_panel.add(tutorial);

        // Exit Tutorial Button (hidden by default)
        exit_tutorial = new JButton();
        exit_tutorial.setIcon(new ImageIcon("src/res/TutorialExit.png"));
        exit_tutorial.setBounds(540,10,50,50);
        exit_tutorial.setVisible(true);
        exit_tutorial.addActionListener(this);
        T_panel.add(exit_tutorial);

        // Welcome Label
        welcome = new JLabel("Welcome To 2048", SwingConstants.CENTER);
        welcome.setFont(new Font("Monospaced", Font.BOLD, 72));
        welcome.setForeground(Color.WHITE);
        welcome.setBounds(460, 100, 1000, 100);
        panel.add(welcome);

        highScore = new JLabel("The Highscore is: " + GameBoard.highScore,SwingConstants.CENTER);
        highScore.setFont(font36);
        highScore.setForeground(Color.WHITE);
        highScore.setBounds(460, 210, 1000, 100);
        panel.add(highScore);


        // Unmute Button
        unmute = new JButton();
        unmute.setBorder(SoundBorder);
        unmute.setIcon(new ImageIcon("src/res/SoundButton/Sound Off 200x200.gif"));
        unmute.setBounds(1500, 430, 210, 210);
        unmute.setVisible(false);
        unmute.setForeground(Color.black);
        unmute.addActionListener(this);
        panel.add(unmute);

        // Mute Button
        mute = new JButton();
        mute.setBorder(SoundBorder);
        mute.setIcon(new ImageIcon("src/res/SoundButton/Sound On 200x200.gif"));
        mute.setVisible(true);
        mute.setBounds(1500, 430, 210, 210);
        mute.setForeground(Color.black);
        mute.addActionListener(this);
        panel.add(mute);

        Music = new JButton("Music Library");
        Music.setVisible(true);
        Music.setBounds(1500,320, 210, 100);
        Music.setForeground(Color.black);
        Music.addActionListener(this);
        panel.add(Music);


        Stal = new JButton();
        Stal.setIcon(new ImageIcon("src/res/StalMusicImage.jpg"));
        Stal.setVisible(false);
        Stal.setBounds(10,10,253,200);
        Stal.addActionListener(this);
        songLibrary.add(Stal);

        nextSong = new JButton("<html> 1 / 2" + "<br> Next Page ");
        nextSong.setVisible(true);
        nextSong.setBounds(300,65,100,100);
        nextSong.addActionListener(this);
        songLibrary.add(nextSong);

        WetHands = new JButton("<html> Wet Hands" + "<br> - C418");
        WetHands.setFont(font36);
        WetHands.setBackground(Color.darkGray);
        WetHands.setVisible(true);
        WetHands.setBounds(10, 10, 253, 200);
        WetHands.setForeground(Color.black);
        WetHands.addActionListener(this);
        songLibrary.add(WetHands);

        validate();

        //Start of End Panel
        DeathLabel = new JLabel("You Lost",SwingConstants.CENTER);
        DeathLabel.setVisible(false);
        DeathLabel.setFont(new Font("Monospaced", Font.BOLD, 400));
        DeathLabel.setForeground(Color.RED);
        DeathLabel.setBounds(885, 490, 1000, 100);
        end_panel.add(DeathLabel);

        WinLabel = new JLabel("You Won!",SwingConstants.CENTER);
        WinLabel.setVisible(false);
        WinLabel.setFont(new Font("Monospaced", Font.BOLD, 400));
        WinLabel.setForeground(Color.RED);
        WinLabel.setBounds(885, 490, 1000, 100);
        end_panel.add(WinLabel);

        PlayAgain = new JButton("Play Again");
        PlayAgain.setFont(font36);
        PlayAgain.setBounds(885, 490, 150, 100);
        PlayAgain.setVisible(true);
        PlayAgain.addActionListener(this);
        end_panel.add(PlayAgain);

        MainMenu = new JButton("Main Menu");
        MainMenu.setFont(font36);
        MainMenu.setBounds(885, 650, 150, 100);
        MainMenu.setVisible(true);
        MainMenu.addActionListener(this);
        end_panel.add(MainMenu);

    }

    public static void MusicButtons(JPanel panel){

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == unmute) {
            if(WetHandsPlaying){
                Muted = false;
                WetHandsClip.start();
                WetHandsClip.loop(Clip.LOOP_CONTINUOUSLY);
            }
            else if(StalPLaying){
                Muted = false;
                StalClip.start();
                StalClip.loop(Clip.LOOP_CONTINUOUSLY);
            }
            mute.setVisible(true);
            unmute.setVisible(false);

        } else if (e.getSource() == mute) {
            // Mute the sound
            Muted = true;
            WetHandsClip.stop();
            StalClip.stop();
            mute.setVisible(false);
            unmute.setVisible(true);
        } else if (e.getSource() == exit) {
            // Close the game
            window.dispose();
            WetHandsClip.stop();
            StalClip.stop();
            System.exit(0);
        } else if (e.getSource() == howToPlay) {
            tutorial_panel.setVisible(true);
            howToPlay.setVisible(false);

        } else if (e.getSource() == exit_tutorial) {
            tutorial_panel.setVisible(false);
            howToPlay.setVisible(true);

        } else if (e.getSource() == start) {
            // Start the game
            window.add(game);
            window.remove(panel);
            game.start();
            window.revalidate();
            window.repaint();
            GameBoard.dead = false;

        }
        else if(e.getSource() == PlayAgain){
            window.add(game);
            window.remove(End_Panel);
            ExplosionClip.stop();
            game.start();
            window.revalidate();
            window.repaint();
            GameBoard.dead = false;
        }
        else if(e.getSource() == MainMenu){
            window.add(panel);
            window.remove(End_Panel);
            ExplosionClip.stop();
            window.revalidate();
            window.repaint();
        }
        else if(e.getSource() == Stal){
            if(!Muted) {
                StalPLaying = true;
                WetHandsPlaying = false;
                WetHandsClip.stop();
                StalClip.start();
                StalClip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        }
        else if(e.getSource() == Music){
            SongLibrary.setVisible(true);
            Music.setVisible(false);
        }
        else if(e.getSource() == WetHands) {
            if(!Muted) {
                StalPLaying = false;
                WetHandsPlaying = true;
                StalClip.stop();
                WetHandsClip.start();
                WetHandsClip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        }
        else if(e.getSource() == nextSong){
            if(!stalShown){
                nextSong.setText("<html> 2 / 2" + "<br>      Next Page ");
                Stal.setVisible(true);
                WetHands.setVisible(false);
                WetHandsShown = false;
                stalShown = true;
            }
            else if(!WetHandsShown){
                nextSong.setText("<html> 1 / 2" + "<br> Next Page ");
                Stal.setVisible(false);
                WetHands.setVisible(true);
                WetHandsShown = true;
                stalShown = false;
            }
        }

    }
}
