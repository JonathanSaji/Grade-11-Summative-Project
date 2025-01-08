package com.jonat15.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Menu extends JFrame implements ActionListener {
    JLabel welcome,tutorial,directions;
    JButton start,exit,howToPlay,exit_tutorial;
    JPanel panel;
    boolean start_game = false;
    JFrame frame;
    public Menu() {
        frame  = new JFrame("Welcome");
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        start = new JButton("Start");
        start.setFont(Game.main.deriveFont(36f));
        start.setBounds(885,490,150,100);
        start.setVisible(true);
        frame.add(start);
        start.addActionListener(this);

        exit = new JButton("Exit");
        exit.setFont(Game.main.deriveFont(36f));
        exit.setBounds(885,610,150,100);
        frame.add(exit);
        exit.addActionListener(this);

        howToPlay = new JButton("How To Play");
        howToPlay.setFont(Game.main.deriveFont(36f));
        howToPlay.setBounds(810,370,300,100);
        frame.add(howToPlay);
        howToPlay.addActionListener(this);

        exit_tutorial = new JButton("x");
        exit_tutorial.setVisible(false);
        exit_tutorial.setFont(Game.main.deriveFont(20f));
        exit_tutorial.setBounds(670,20,50,50);
        frame.add(exit_tutorial);
        exit_tutorial.addActionListener(this);

        welcome = new JLabel("Welcome",SwingConstants.CENTER);
        welcome.setFont(Game.main.deriveFont(72f));
        welcome.setForeground(Color.RED);
        welcome.setVisible(true);
        welcome.setBounds(460,100,1000,100);
        frame.add(welcome);

        panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setVisible(false);
        panel.setBounds(20,20,700,950);
        panel.setBackground(Color.white);

        JLabelAdder(20,20,660,100,tutorial,"<html><br><br>To play 2048, use arrow keys to<br>matching tiles with the same number,<br>" +
                "which will merge into one tile " + "<br> with the sum of the two numbers." +
                "<br><br> The goal is to keep combining tiles" + " <br> until you create the 2048 tile.<html>");


        JLabelAdder(20,80,660,100,directions,"<html><br><br>Direction's<br><br>Right: → (Arrow Key)<br><br>" +
                "Left: ← (Arrow Key) <br><br>" +
                "Up: ↑ (Arrow Key)<br><br>" +
                "Down: ↓ (Arrow Key)<html>");





    }

    public void JLabelAdder(int x,int y, int width, int length, JLabel label, String txt){
        label = new JLabel(txt);
        label.setBounds(x,y,width,length);
        label.setFont(Game.main.deriveFont(36f));
        label.setVisible(true);
        label.setForeground(Color.red);
        panel.add(label);
        frame.add(panel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == start){
            start_game = true;
            Start.SetStatus(true);
        }
        else if(e.getSource() == exit){
            frame.dispose();
        }
        else if(e.getSource() == howToPlay){
            panel.setVisible(true);
            exit_tutorial.setVisible(true);
        }
        else if(e.getSource() == exit_tutorial){
            panel.setVisible(false);
            exit_tutorial.setVisible(false);
        }
    }


    public void remove_menu(){
        frame.dispose();
    }
}
