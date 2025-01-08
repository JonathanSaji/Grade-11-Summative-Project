package com.jonat15.game;

import javax.swing.*;
import java.awt.*;

public class Start {
    public static boolean _status = false;
    public static void main(String[] args) {
        Game game = new Game();
        Menu menu = new Menu();

        boolean status = false;
        Start.SetStatus(status);

        while(!status){
            status = _status;
            if(status){
                menu.remove_menu();
                JFrame window = new JFrame("2048");

                window.getContentPane().setBackground(Color.BLACK);
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                window.setExtendedState(JFrame.MAXIMIZED_BOTH);
                window.pack();
                window.setLocationRelativeTo(null);
                window.setVisible(true);
                window.add(game);
                game.start();
                System.out.println("Game Started");
                break;
            }
            System.out.println("Running");
        }
        }

        public static boolean getStatus(){
        return _status;
        }
        public static void SetStatus(boolean STATUS){
            _status = STATUS;
        }

    }