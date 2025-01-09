package com.jonat15.game;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JPanel implements ActionListener {
    public static final int width = 1920;
    public static final int length = 1080;
    public static final Font main = new Font("Arial", Font.BOLD, 28);

    public static JPanel panel;
    private JLabel welcome;
    private JButton unmute, mute, start, exit, exit_tutorial, howToPlay;
    private boolean exit_menu = false;

    public Menu(JFrame frame) {
        panel = new JPanel();
        panel.setSize(width, length);
        panel.setBackground(Color.BLACK);
        panel.setLayout(null);
        panel.setOpaque(true);
        frame.add(panel);  // Ensure Start.window is initialized before this point

        // Start Button
        start = new JButton("Start");
        start.setFont(Game.main.deriveFont(36f));
        start.setBounds(885, 490, 150, 100);
        start.setVisible(true);
        start.addActionListener(this);
        panel.add(start);

        // Exit Button
        exit = new JButton("Exit");
        exit.setFont(new Font("Arial", Font.BOLD, 36));
        exit.setBounds(885, 610, 150, 100);
        exit.addActionListener(this);
        panel.add(exit);

        // How To Play Button
        howToPlay = new JButton("How To Play");
        howToPlay.setFont(new Font("Arial", Font.BOLD, 36));
        howToPlay.setBounds(810, 370, 300, 100);
        howToPlay.addActionListener(this);
        panel.add(howToPlay);

        // Exit Tutorial Button (hidden by default)
        exit_tutorial = new JButton("x");
        exit_tutorial.setFont(new Font("Arial", Font.PLAIN, 20));
        exit_tutorial.setBounds(670, 20, 50, 50);
        exit_tutorial.setVisible(false);
        exit_tutorial.addActionListener(this);
        panel.add(exit_tutorial);

        // Welcome Label
        welcome = new JLabel("Welcome To 2048", SwingConstants.CENTER);
        welcome.setFont(new Font("Arial", Font.BOLD, 72));
        welcome.setForeground(Color.RED);
        welcome.setBounds(460, 100, 1000, 100);
        panel.add(welcome);

        // Unmute Button
        unmute = new JButton("Sound On");
        unmute.setBounds(1500, 430, 100, 100);
        unmute.setForeground(Color.black);
        unmute.addActionListener(this);
        panel.add(unmute);

        // Mute Button
        mute = new JButton("Sound Off");
        mute.setBounds(1500, 550, 100, 100);
        mute.setForeground(Color.black);
        mute.addActionListener(this);
        panel.add(mute);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == unmute) {
            // Unmute the sound
            Start.clip.loop(Clip.LOOP_CONTINUOUSLY);
            Start.clip.start();
        } else if (e.getSource() == mute) {
            // Mute the sound
            Start.clip.stop();
        } else if (e.getSource() == exit) {
            // Close the game
            Start.window.dispose();
            Start.clip.stop();
            exit_menu = true;
        } else if (e.getSource() == howToPlay) {
            // Show tutorial
            showTutorial();
        } else if (e.getSource() == exit_tutorial) {
            // Close tutorial
            exitTutorial();
        } else if (e.getSource() == start) {
            // Start the game
        }
    }

    private void showTutorial() {
        // Display tutorial information
        exit_tutorial.setVisible(true);  // Show the exit tutorial button
        // You can add tutorial content display logic here (e.g., a new panel or a message)
    }

    private void exitTutorial() {
        // Hide tutorial and return to main menu
        exit_tutorial.setVisible(false);
        // Add logic to hide tutorial content if any (e.g., removing panels or resetting labels)
    }

    public static void StartGame(JFrame frame, Menu menu, Game game) {
        // Start the game
        frame.remove(menu);
        frame.add(game);
        frame.revalidate();
        frame.repaint();
        game.start();
    }
}
