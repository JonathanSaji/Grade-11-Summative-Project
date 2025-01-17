package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class Game extends JPanel implements KeyListener,Runnable{
    public static final int width = 1920;
    public static final int length = 1080;
    public static final Font main = new Font("Arial",Font.BOLD,28);
    private Thread game;
    private boolean running;
    private BufferedImage image = new BufferedImage(width,length,BufferedImage.TYPE_INT_RGB); //no flicker
    private GameBoard board;

    public Game(){
        setFocusable(true);//let's keyboard input to be set
        setPreferredSize(new Dimension(width,length));
        addKeyListener(this);

        board = new GameBoard(width/2 - GameBoard.board_width/2,length -GameBoard.board_length-320);//center on x-axis and left 10 up from the screen

        JButton btn = new JButton("Click Me!");
        btn.setBounds(40, 40, 100, 50);
        btn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Button Clicked!"));
        setLayout(null); // Use null layout to set absolute positions
        add(btn);

    }

    private void update(){
        board.update();
        Keyboard.update();
    }
    private void render(){
        Graphics2D g = (Graphics2D)  image.getGraphics();// how to draw to the image
        g.setColor(Color.BLACK);
        g.fillRect(0,0,width,length);//white rectangle on the screen
        //rendering board
        board.render(g);
        g.dispose();

        if(running) {
            Graphics2D g2d = (Graphics2D) getGraphics();//JPanel graphics
            g2d.drawImage(image, 0, 0, null);//final image to the JPanel
            g2d.dispose();
        }

    }


    @Override
    //when thread is running(GAME LOOP)
    public void run() {
        //setting vars
        int fps = 0,update = 0;
        long fpsTimer = System.currentTimeMillis();
        double nsPerUpdate = 1000000000.0/60; //because a billion nana seconds in one second

        //last update time in nanoseconds
        double then = System.nanoTime();
        double unprocessed = 0;

        while(running) {
            boolean shouldRender = false;
            double now = System.nanoTime();
            unprocessed +=(now - then)/nsPerUpdate;
            then = now;
            //update queue
            while (unprocessed >= 1) {
                update++;
                update();
                unprocessed--;
                shouldRender = true;
            }
            //render
            if (shouldRender) {
                fps++;
                render();
                shouldRender = false;
            } else {
                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                    System.out.println("Error");//just in case but usually won't be error.
                }
            }
        }
        //FPS timer
        if (System.currentTimeMillis() - fpsTimer > 1000) {
            System.out.printf("%d fps %d update", fps, update);
            System.out.println();
            fps = 0;
            update = 0;
            fpsTimer += 1000;
        }
    }
    public synchronized void start(){

        if (running) return;
        running = true;
        game = new Thread(this, "game");
        game.start();
    }
    public synchronized void stop(){
        if(!running) return;
        running = false;
        board =  new GameBoard(width/2 - GameBoard.board_width/2,length -GameBoard.board_length-320);
    }


    @Override
    public void keyTyped(KeyEvent e) {
        //we don't use this one, because it is invalid
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Keyboard.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Keyboard.keyReleased(e);
    }

}