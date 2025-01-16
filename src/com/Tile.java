package com.jonat15.game;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Tile {

    public static final int width = 150;
    public static final int length = 150;
    public static final int slide_speed = 15;
    public static final int arc_width = 30;
    public static final int arc_height = 30;

    private int value;
    private BufferedImage tileimage;
    private Color background;
    private Color text;


    private Font font;
    private Point slideTo;//when slide where does the tile end up
    private int x;
    private int y;

    private boolean beginningAnimation = true;
    private double scaleFirst = 0.1; //start then keep growing
    private BufferedImage beginningImage;

    private boolean combineAnimation = false;
    private double scaleCombine = 1.2; //so scalles up by 20%
    private BufferedImage combineImage;
    private boolean CanCombine = true;

    public Tile(int value,int x , int y){
        this.value = value;
        this.x = x;
        this.y = y;
        slideTo = new Point(x,y);
        tileimage = new BufferedImage(width,length,BufferedImage.TYPE_INT_ARGB);
        beginningImage = new BufferedImage(width,length,BufferedImage.TYPE_INT_ARGB);
        combineImage = new BufferedImage(width*2,length*2,BufferedImage.TYPE_INT_ARGB);

        drawImage();
    }

    private void drawImage(){
        Graphics2D g = (Graphics2D)tileimage.getGraphics();
        if(value == 2) {
            background = new Color(0xECE3D9);
            text = new Color(0x766D65);
        }
        else if(value == 4){
            background = new Color(0xEBDFC7);
            text = new Color(0x766D65);
        }
        else if(value == 8){
            background = new Color(0xF0B078);
            text = new Color(0xF7F4F0);
        }
        else if(value == 16){
            background = new Color(0xF39463);
            text = new Color(0xF7F4F0);
        }
        else if(value == 32){
            background = new Color(0xF47B5F);
            text = new Color(0xF7F4F0);
        }
        else if(value == 64){
            background = new Color(0xF45E3B);
            text = new Color(0xF7F4F0);
        }
        else if(value == 128){
            background = new Color(0xEBCE71);
            text = new Color(0xF7F4F0);
        }
        else if(value == 256){
            background = new Color(0xEBCB61);
            text = new Color(0xF7F4F0);
        }
        else if(value == 512){
            background = new Color(0xF3D258);
            text = new Color(0xFDFDFD);
        }
        else if(value == 1024){
            background = new Color(0xE8C23D);
            text = new Color(0xF5F0E1);
        }
        else if(value == 2048){
            background = new Color(0xECC12E);
            text = new Color(0xF2F6FD);
            GameBoard.won = true;
        }
        else{
            background = new Color(0x000000);
            text = new Color(0xFFFFFF);
        }

        g.setColor(new Color(0,0,0,0));
        g.fillRect(0,0,width,length);

        g.setColor(background);
        g.fillRoundRect(0,0,width,length,arc_width,arc_height);
        g.setColor(text);

        if(value <=64){
            font = new Font("Monospaced", Font.BOLD, 72);
        }
        else{
            font = new Font("Monospaced", Font.BOLD, 72);;
        }
        g.setFont(font);

        int drawX = width / 2 - DrawUtils.getMessageWidth(""+value,font,g)/2;
        int drawY = length -30 - DrawUtils.getMessageHeight(""+value,font,g)/2;
        g.drawString(""+value,drawX,drawY);
        g.dispose();


    }
    //for animation
    public void update(){
        if(beginningAnimation) {
            AffineTransform transform = new AffineTransform();
            transform.translate(width/2 - scaleFirst * width/2 , length/2 - scaleFirst * length/2 );
            transform.scale(scaleFirst, scaleFirst);
            Graphics2D g2d = (Graphics2D) beginningImage.getGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2d.setColor(new Color(0, 0, 0, 0));
            g2d.fillRect(0, 0, width, length);
            g2d.drawImage(tileimage, transform, null);
            scaleFirst += 0.1;//10 % bigger
            g2d.dispose();
            if (scaleFirst >= 1) {
                beginningAnimation = false;
            }
        }
        else if(combineAnimation){
            AffineTransform transform = new AffineTransform();
            transform.translate(width/2 - scaleCombine *width/2,length/2 - scaleCombine * length/2);
            transform.scale(scaleCombine,scaleCombine);
            Graphics2D g2d = (Graphics2D) combineImage.getGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2d.setColor(new Color(0,0,0,0));
            g2d.fillRect(0,0,width,length);
            g2d.drawImage(tileimage,transform,null);
            scaleCombine +=0.1;//10 % smaller
            g2d.dispose();
            if(scaleCombine >= 1){
                combineAnimation = false;
            }
        }
    }
    public void render(Graphics2D g){
        if(beginningAnimation){
            g.drawImage(beginningImage,x,y,null);
        }
        else if(combineAnimation) {
            g.drawImage(combineImage, (int) (x + width / 2 - scaleCombine * width / 2),
                    (int) (y + length / 2 - scaleCombine * length / 2), null);
        }
        else{
            g.drawImage(tileimage,x,y,null);
        }
    }

    public int getValue(){
        return value;
    }
    public void setValue(int value){
        this.value = value;
        drawImage();
    }

    public void setCanCombine(boolean canCombine) {
        CanCombine = canCombine;
    }

    public boolean canCombine() {
        return CanCombine;
    }

    public Point getSlideTo() {
        return slideTo;
    }

    public void setSlideTo(Point slideTo) {
        this.slideTo = slideTo;
    }

    public int getX(){
        return x;
    }
    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public int getY(){
        return y;
    }
    public boolean isCombineAnimation() {
        return combineAnimation;
    }

    public void setCombineAnimation(boolean combineAnimation) {
        this.combineAnimation = combineAnimation;
    }

}