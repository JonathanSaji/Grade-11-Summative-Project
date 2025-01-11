package com.jonat15.game;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;


public class Menu extends JPanel implements ActionListener {
    public static final int width = 1920;
    public static final int length = 1080;
    public static Clip clip;
    public static JFrame window;
    private static JLabel DeathLabel, WinLabel;
    private  JLabel welcome;
    private static JLabel highScore;
    private static boolean isDead,isWon;
    private static boolean whileLoopRunning = true;
    private static JPanel panel,End_Panel;
    private JButton unmute, mute, start, exit, exit_tutorial, howToPlay,PlayAgain,MainMenu;
    private boolean exit_menu = false;
    public static Game game = new Game();
    private Font font36 = new Font("Monospaced", Font.BOLD, 36);
    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        window = new JFrame("2048");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Load the WAV file
        File audioFile = new File("src/res/Wet Hands.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
        // Get a sound clip resource

        clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        // Let the music play for its duration (optional, for demonstration)// Wait until the music finishes
        panel = new JPanel();
        panel.setSize(width, length);
        panel.setBackground(Color.BLACK);
        panel.setLayout(null);
        panel.setOpaque(true);

        End_Panel = new JPanel();
        Menu menu = new Menu(panel,End_Panel);
        End_Panel.setBackground(Color.BLACK);


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
                        window.remove(game);
                        window.revalidate();
                        window.repaint();
                        WinLabel.setVisible(true);
                        DeathLabel.setVisible(false);
                        isWon = false;
                        GameBoard.won = false;
                        highScore.setText("The Highscore is: " + GameBoard.highScore);
                    }
                    System.out.println(isDead + " Flip");
                }
            System.out.println(isDead);
        }
    }

    public Menu(JPanel panel, JPanel end_panel){
        start = new JButton("Start");
        start.setFont(font36);
        start.setBounds(885, 490, 150, 100);
        start.setVisible(true);
        start.addActionListener(this);
        panel.add(start);

        // Exit Button
        exit = new JButton("Exit");
        exit.setFont(font36);
        exit.setBounds(885, 610, 150, 100);
        exit.addActionListener(this);
        panel.add(exit);

        // How To Play Button
        howToPlay = new JButton("How To Play");
        howToPlay.setFont(font36);
        howToPlay.setBounds(810, 370, 300, 100);
        howToPlay.addActionListener(this);
        panel.add(howToPlay);

        // Exit Tutorial Button (hidden by default)
        exit_tutorial = new JButton("x");
        exit_tutorial.setFont(new Font("Monospaced", Font.BOLD, 20));
        exit_tutorial.setBounds(670, 20, 50, 50);
        exit_tutorial.setVisible(false);
        exit_tutorial.addActionListener(this);
        panel.add(exit_tutorial);

        // Welcome Label
        welcome = new JLabel("Welcome To 2048", SwingConstants.CENTER);
        welcome.setFont(new Font("Monospaced", Font.BOLD, 72));
        welcome.setForeground(Color.RED);
        welcome.setBounds(460, 100, 1000, 100);
        panel.add(welcome);

        highScore = new JLabel("The Highscore is: " + GameBoard.highScore,SwingConstants.CENTER);
        highScore.setFont(font36);
        highScore.setForeground(Color.RED);
        highScore.setBounds(460, 210, 1000, 100);
        panel.add(highScore);


        // Unmute Button
        unmute = new JButton();
        unmute.setIcon(new ImageIcon("src/res/Sound On.png"));
        unmute.setBounds(1500, 430, 100, 100);
        unmute.setFont(new Font("Times New Roman", Font.BOLD, 20));
        unmute.setVisible(false);
        unmute.setForeground(Color.black);
        unmute.addActionListener(this);
        panel.add(unmute);

        // Mute Button
        mute = new JButton();
        mute.setIcon(new ImageIcon("src/res/Sound Off.png"));
        mute.setFont(new Font("Times New Roman", Font.BOLD, 20));
        mute.setVisible(true);
        mute.setBounds(1500, 430, 100, 100);
        mute.setForeground(Color.black);
        mute.addActionListener(this);
        panel.add(mute);

        validate();

        //Start of End Panel
        DeathLabel = new JLabel("You Died",SwingConstants.CENTER);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == unmute) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            mute.setVisible(true);
            unmute.setVisible(false);
        } else if (e.getSource() == mute) {
            // Mute the sound
            clip.stop();
            mute.setVisible(false);
            unmute.setVisible(true);
        } else if (e.getSource() == exit) {
            // Close the game
            window.dispose();
            clip.stop();
            System.exit(0);
        } else if (e.getSource() == howToPlay) {
            // Show tutorial
            exit_tutorial.setVisible(true);
        } else if (e.getSource() == exit_tutorial) {
            // Close tutorial
            exit_tutorial.setVisible(false);

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
            game.start();
            window.revalidate();
            window.repaint();
            GameBoard.dead = false;
        }
        else if(e.getSource() == MainMenu){
            window.add(panel);
            window.remove(End_Panel);
            window.revalidate();
            window.repaint();
        }

    }
}
