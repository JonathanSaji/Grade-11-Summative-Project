package Game;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Menu extends JPanel implements ActionListener {
    public boolean StalNeedPlay = false;
    public boolean WetHandsNeedPlay = true;
    private static Clip WetHandsClip,StalClip,ExplosionClip;
    private boolean stalShown = false;
    private boolean WetHandsShown = true;
    private boolean Muted = false;
    public static JFrame window;
    private static JLabel DeathLabel, WinLabel;
    private JLabel welcome,tutorial, DevelopedBy;
    private static JLabel highScore;
    private static boolean isDead,isWon;
    private static boolean whileLoopRunning = true;
    private static JPanel panel,End_Panel,tutorial_panel,SongLibrary;
    private JButton unmute, mute, start, exit, exit_tutorial,exitMusic, howToPlay,PlayAgain,MainMenu,Stal,Music,WetHands,nextSong,TileSize;
    public static Game game = new Game();
    private Font font36 = new Font("Monospaced", Font.BOLD, 36);
    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {

        window = new JFrame("2048");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Start of the Audio Making...

        File audioFile = new File("src/res/Wet Hands.wav");
        AudioInputStream WetHandsStream = AudioSystem.getAudioInputStream(audioFile);
        WetHandsClip = AudioSystem.getClip();
        WetHandsClip.open(WetHandsStream);
        WetHandsClip.loop(Clip.LOOP_CONTINUOUSLY);

        File explosionFile = new File("src/res/Explosion.wav");
        AudioInputStream ExplosionStream = AudioSystem.getAudioInputStream(explosionFile);
        ExplosionClip = AudioSystem.getClip();
        ExplosionClip.open(ExplosionStream);

        File StalFile = new File("src/res/Stal - Music disc.wav");
        AudioInputStream StalStream = AudioSystem.getAudioInputStream(StalFile);
        StalClip = AudioSystem.getClip();
        StalClip.open(StalStream);
        StalClip.stop();

        //ending of making audio

        panel = new JPanel();
        PanelCreator(0,0,1920,1080,panel,null,Color.black,true,null,false);

        End_Panel = new JPanel();
        PanelCreator(0,0,1920,1080,End_Panel,null,Color.black,true,null,false);

        tutorial_panel = new JPanel();
        PanelCreator(10,10,600,1000,tutorial_panel,BorderCreator(Color.red,10,true),Color.black,false,panel,true);

        SongLibrary = new JPanel();
        Menu menu = new Menu(panel,End_Panel,tutorial_panel,SongLibrary);
        PanelCreator(1380,650,430,220,SongLibrary,BorderCreator(Color.red,10,true),Color.darkGray,false,panel,true);

        window.add(panel);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);

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
    }

    public Menu(JPanel panel, JPanel end_panel,JPanel T_panel,JPanel songLibrary){
        Border SoundBorder = BorderFactory.createLineBorder(Color.YELLOW,10,true);

        //Start of Panel...
        //Start Button (gif)
        start = new JButton();
        ButtonCreator(start,885,490,150,100,panel,null,null,"src/res/StartButtonGif.gif",null,null,null,true);

        // Exit Button (img)
        exit = new JButton();
        ButtonCreator(exit,885,610,150,100,panel,null,null,"src/res/ExitButton.jpg",null,null,null,true);

        // How To Play Button (txt)
        howToPlay = new JButton();
        ButtonCreator(howToPlay,810,370,300,100,panel,"How To Play",null,null,Color.WHITE,Color.BLACK,font36,true);

        // Tutorial (On Tutorial Panel) (hidden on default) (txt)
        tutorial = new JLabel();
        LabelCreator(tutorial,10,10,600,1000,T_panel,"<html>2048 is a single-player sliding tile puzzle video game"+
                "<br> written by Italian web developer Gabriele Cirulli and"+
                "<br> published on GitHub. The objective of the game is"+
                "<br> to slide numbered tiles on a grid to combine them to "+
                "<br> create a tile with the number 2048."+
                "<br> "+"<br> Directions: (Arrow Keys)"+"<br> Up: ↑ "+"<br> Down: ↓ "+"<br> Right: → "+"<br> Left: ← "+"<br>",null,Color.WHITE,null,font36,true);

        // Exit Tutorial Button (hidden by default) (img)
        exit_tutorial = new JButton();
        ButtonCreator(exit_tutorial,540,10,50,50,T_panel,"x",null,null,Color.WHITE,Color.BLACK,FontCreator(22),true);

        // Welcome (txt)
        welcome = new JLabel("", SwingConstants.CENTER);
        LabelCreator(welcome,460,100,1000,100,panel,"Welcome To 2048",null,Color.WHITE,null,FontCreator(72),true);

        // Highscore (txt) Gets updated everytime 1 game session ends
        highScore = new JLabel("",SwingConstants.CENTER);
        LabelCreator(highScore,460,210,1000,100,panel,"The Highscore is: " + GameBoard.highScore,null,Color.WHITE,null,font36,true);
        //End of Panel

        //Start of Sound...
        // Un-mute Button (img) This button un-mutes the selected music in the game
        unmute = new JButton();
        ButtonCreator(unmute,1500,430,210,210,panel,null,SoundBorder,"src/res/SoundButton/Sound Off 200x200.gif",null,null,null,false);

        // Mute Button (img) This mutes all the music in the game
        mute = new JButton();
        ButtonCreator(mute,1500,430,210,210,panel,null,SoundBorder,"src/res/SoundButton/Sound On 200x200.gif",null,null,null,true);
        //End of Sound...

        TileSize = new JButton();
        ButtonCreator(TileSize,1390,200,420,100,panel,"Tile Size (BETA)",BorderCreator(Color.white,2,true),null,Color.white,Color.black,font36,true);

        //Start of Music...
        //Music Button (txt) This opens the song library panel
        Music = new JButton("Music Library");
        ButtonCreator(Music,1390,320,420,100,panel,"Music Library",BorderCreator(Color.white,2,true),null,Color.WHITE,Color.BLACK,font36,true);

        //Stal button (img) This toggles the music to play audio Stal - C418
        Stal = new JButton();
        ButtonCreator(Stal,10,10,253,200,songLibrary,null,null,"src/res/StalMusicImage.jpg",null,null,null,false);

        //NextSong button (txt) This button is used to go to toggle the songs that I have in the song library
        nextSong = new JButton();
        ButtonCreator(nextSong,300,65,100,100,songLibrary,"<html> 1 / 2" + "<br> Next Page ",null,null,Color.red,Color.black,FontCreator(22),true);

        //Exit Music (img) This button exits the Library Song Panel and Lets Music Button Reappear
        exitMusic = new JButton();
        ButtonCreator(exitMusic,370,10,50,50,songLibrary,null,null,"src/res/TutorialExit.png",null,null,null,true);

        //Wet Hands (txt) This Button lets the music Wet Hands - C418 play
        WetHands = new JButton();
        ButtonCreator(WetHands,10, 10, 253, 200,songLibrary,"<html> Wet Hands" + "<br> - C418",null,null,Color.black,Color.darkGray,font36,true);
        //End of Music...

        //Developed By (txt) This button is just a way to say that I designed this game
        DevelopedBy = new JLabel("",SwingConstants.CENTER);
        LabelCreator(DevelopedBy,480,750,1000,100,panel,"Developed By Jonathan Saji",null,Color.white,null,font36,true);


        //Start of End Panel...
        //Label for death or LOST
        DeathLabel = new JLabel();
        LabelCreator(DeathLabel,0, 50, 1920, 400,end_panel,"You Lost",null,Color.red,null,FontCreator(400),false);

        //Label for WIN
        WinLabel = new JLabel("",SwingConstants.CENTER);
        LabelCreator(WinLabel,0, 50, 1920, 400,end_panel,"You Won",null,Color.red,null,FontCreator(400),false);

        //To Play the game (2048) again
        PlayAgain = new JButton();
        ButtonCreator(PlayAgain,380, 490, 200, 200,end_panel,null,null,"src/res/ReturnImage.jpg",null,null,null,true);

        //Return back to the starting screen or main menu
        MainMenu = new JButton("<html> Main <br> Menu");
        ButtonCreator(MainMenu,1540, 490, 200, 200,end_panel,null,null,"src/res/MainMenuImage.jpg",null,null,null,true);
        //End of End Panel...

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == unmute) {
            if(WetHandsNeedPlay){
                Muted = false;
                WetHandsClip.start();
                WetHandsClip.loop(Clip.LOOP_CONTINUOUSLY);
            }
            else if(StalNeedPlay){
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
            int input = JOptionPane.showConfirmDialog(null,"Do You Want to Leave","Quit Game?",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
            if(input == JOptionPane.YES_OPTION) {
                System.exit(0);
            }

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
            ExplosionClip.setMicrosecondPosition(0);
            ExplosionClip.stop();
            game.start();
            window.revalidate();
            window.repaint();
            GameBoard.dead = false;

        }
        else if(e.getSource() == PlayAgain){
            window.add(game);
            window.remove(End_Panel);
            ExplosionClip.setMicrosecondPosition(0);
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
                WetHandsClip.stop();
                StalClip.start();
                StalClip.loop(Clip.LOOP_CONTINUOUSLY);
                StalNeedPlay = true;
                WetHandsNeedPlay = false;
            }
        }
        else if(e.getSource() == Music){
            SongLibrary.setVisible(true);
            Music.setVisible(false);
        }
        else if(e.getSource() == WetHands) {
            if(!Muted) {
                StalClip.stop();
                WetHandsClip.start();
                WetHandsClip.loop(Clip.LOOP_CONTINUOUSLY);
                StalNeedPlay = false;
                WetHandsNeedPlay = true;
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
        else if(e.getSource() == exitMusic){
            Music.setVisible(true);
            SongLibrary.setVisible(false);
        }

    }
    //This method is used for every JButton in this class
    public void ButtonCreator(JButton btn, int x,int y,int width,int length,JPanel jPanel,String txt,Border border,String imgPath,Color foreground, Color background,Font font,boolean visible){
        btn.setIcon(new ImageIcon(imgPath));
        btn.setText(txt);
        btn.setBackground(background);
        btn.setForeground(foreground);
        btn.setBorder(border);
        btn.setFont(font);
        btn.setBounds(x, y, width, length);
        btn.setVisible(visible);
        btn.addActionListener(this);
        jPanel.add(btn);
    }
    //This method is used by every JLabel in this class
    public void LabelCreator(JLabel label,int x,int y,int width, int length, JPanel jPanel, String txt, Border border,Color foreground, Color background, Font font,boolean visible){
        label.setText(txt);
        label.setBorder(border);
        label.setBackground(background);
        label.setForeground(foreground);
        label.setFont(font);
        label.setBounds(x,y,width,length);
        label.setVisible(visible);
        jPanel.add(label);
    }
    public Font FontCreator(int size){
        Font font = new Font("Monospaced", Font.BOLD, size);
        return font;
    }
    public static Border BorderCreator(Color color,int thickness,boolean rounded){
        Border border = new LineBorder(color,thickness,rounded);
        return border;
    }
    public static void PanelCreator(int x,int y,int width, int length,JPanel panel,Border border, Color background,boolean visible, JPanel ParentPanel, boolean useParentPanel){
        panel.setVisible(visible);
        panel.setBounds(x,y,width,length);
        panel.setBackground(background);
        panel.setLayout(null);
        panel.setBorder(border);
        if(useParentPanel) {
            ParentPanel.add(panel);
        }
    }
}