package com.jonat15.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.*;

import java.util.Random;

public class GameBoard {

    public static final int ROWS = 4;
    public static final int COLS = 4;
    private Color game_border,gameTile_background;
    private final int STARTINGTILES = 2;//capitalize
    private Tile[][] board;
    static public boolean dead;
    static public boolean won;
    private BufferedImage gameBoard;
    private BufferedImage finalBoard;
    private int x;
    private int y;
    private int score = 0;
    public static int highScore = 0;
    private Font scoreFont;

    private static int spacing = 10;
    public static int board_width = (COLS+1)*spacing+COLS*Tile.width;
    public static int board_length = (ROWS+1)*spacing+ROWS*Tile.length;

    private boolean hasStarted;


    //Saving
    private String saveDataPath;
    private String fileName = "SaveData";


    public GameBoard(int x, int y){
        try{
            saveDataPath = GameBoard.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        }catch(Exception e){
            e.printStackTrace();
        }
        scoreFont = new Font("Monospaced", Font.BOLD, 52);
        this.x = x;
        this.y = y;
        board = new Tile[ROWS][COLS];
        gameBoard = new BufferedImage(board_width,board_length,BufferedImage.TYPE_INT_RGB);
        finalBoard = new BufferedImage(board_width,board_length,BufferedImage.TYPE_INT_RGB);
        loadHighScore();
        createBoardImage();
        start();

    }

    private void createSaveData(){
        try{

            File file = new File(saveDataPath,fileName);

            FileWriter fr = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(fr);
            writer.write(""+0);
            //for fastest time
        }catch(Exception e){
            System.err.println("Error" + e);
        }
    }
    private void loadHighScore(){
        try{
            File f = new File(saveDataPath,fileName);
            if(!f.isFile()){
                createSaveData();
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
            highScore = Integer.parseInt(reader.readLine());
            reader.close();
        }catch(Exception e){
            System.out.println("Error" + e);
        }
    }
    private void setHighScore()
    {
        FileWriter output = null;

        try{
            File f = new File(saveDataPath,fileName);
            output = new FileWriter(f);
            BufferedWriter writer = new BufferedWriter(output);

            writer.write(""+highScore);

            writer.close();
        }catch(Exception e){
            System.out.println("Error" + e);
        }
    }
    private void createBoardImage(){
        Graphics2D g = (Graphics2D) gameBoard.getGraphics();
        game_border = new Color(186, 172, 159);
        gameTile_background = new Color(204, 192, 179);
        g.setColor(game_border);
        g.fillRect(0,0,board_width,board_length);
        g.setColor(gameTile_background);

        for(int row = 0; row < ROWS; row++){
            for(int col = 0; col< COLS;col++){
                int x = spacing+spacing*col +Tile.width*col;
                int y = spacing+spacing*row+Tile.length*row;
                g.fillRoundRect(x,y,Tile.width,Tile.length,Tile.arc_width,Tile.arc_height);
            }
        }
    }
    private void start(){
        for(int i= 0; i< STARTINGTILES;i++){
            spawnRandom();
        }
    }

    private void spawnRandom(){
        Random random = new Random();
        boolean notValid = true;
        while(notValid){
            int location =random.nextInt(ROWS*COLS); //instead of 16 for more efficiency
            int row = location/ROWS;//single to two dimension
            int col = location%COLS;

            Tile current = board[row][col];
            if(current == null){
                int value = random.nextInt(10)< 9 ? 2:4; //90% chance for 2 to get spawned and 10% chance for 4 to spawn
                Tile tile = new Tile(value,getTileX(col),getTileY(row));
                board[row][col] = tile;
                notValid = false;
            }
        }
    }

    public int getTileX(int col){
        return spacing+col*Tile.width+col*spacing;
    }
    public int getTileY(int row){
        return spacing+row*Tile.length+row*spacing;
    }


    public void render(Graphics2D g){
        Graphics2D g2d = (Graphics2D) finalBoard.getGraphics();
        g2d.drawImage(gameBoard,0,0,null);

        for(int row = 0;row<ROWS;row++){
            for(int col = 0; col<COLS;col++){
                Tile current = board[row][col];
                if(current == null){
                    continue; //it skips through the loop iteration and goes to the next one
                }
                current.render(g2d);
            }
        }
        g.drawImage(finalBoard,x,y,null);
        g2d.dispose();

        g.setColor(Color.lightGray);
        g.setFont(scoreFont);
        g.drawString("" + score,800,790);
        g.setColor(Color.red);
        g.drawString("HighScore: " + highScore,Game.width - DrawUtils.getMessageWidth("Best ",scoreFont,g)-900,790);

        g.setColor(Color.yellow);
        g.drawString("2048 - Jonathan Saji©",640,100);
    }

    public void update(){
        checkKeys();

        if(score > highScore){
            highScore = score;
        }



        for(int row = 0;row<ROWS; row++){
            for(int col = 0; col<COLS;col++){
                Tile current = board[row][col];
                if(current == null){
                    continue;
                }
                current.update();
                resetPositon(current,row,col);
                if(current.getValue() == 2048){
                    won = true;
                }
            }
        }
    }

    private void resetPositon(Tile current,int row,int col){
        if(current == null){
            return;//cant move it
        }

        int x = getTileX(col);
        int y = getTileY(row);

        int distX = current.getX() - x;
        int distY = current.getY() -y;

        if(Math.abs(distX)<Tile.slide_speed){
            current.setX(current.getX()-distX);
        }
        if(Math.abs(distY)<Tile.slide_speed){
            current.setY(current.getY()-distY);
        }

        if(distX < 0){
            current.setX(current.getX() + Tile.slide_speed);
        }
        if(distY < 0){
            current.setY(current.getY()+ Tile.slide_speed);
        }
        if(distX > 0){
            current.setX(current.getX() - Tile.slide_speed);
        }
        if(distY > 0){
            current.setY(current.getY() - Tile.slide_speed);
        }


    }

    private boolean move(int row,int col,int horizontalDirection,int verticalDirection,Direction dir){
        boolean canMove = false;

        Tile current = board[row][col];
        if(current ==null){
            return false;
        }
        boolean move = true;
        int newCol = col;
        int newRow = row;
        while(move){
            newCol += horizontalDirection;
            newRow += verticalDirection;
            if(checkOutofBounds(dir,newRow,newCol)){
                break;
            }
            if(board[newRow][newCol] == null){
                board[newRow][newCol]= current;
                board[newRow-verticalDirection][newCol-horizontalDirection] = null;//new place going to will equal to that then the old space on where it was will be left blank
                board[newRow][newCol].setSlideTo(new Point(newRow,newCol));
                canMove = true;
            }
            else if(board[newRow][newCol].getValue() == current.getValue() && board[newRow][newCol].canCombine()){
                board[newRow][newCol].setCanCombine(false);//cant no longer combine anymore
                board[newRow][newCol].setValue(board[newRow][newCol].getValue()*2);
                canMove = true;
                board[newRow - verticalDirection][newCol - horizontalDirection] = null;
                board[newRow][newCol].setSlideTo(new Point(newRow,newCol));
                board[newRow][newCol].setCombineAnimation(true);//for animation
                score+=board[newRow][newCol].getValue();
            }
            else{
                move = false;//probably won't get hit
            }
        }
        return canMove;
    }

    private boolean checkOutofBounds(Direction dir, int row, int col) {
        if(dir == Direction.LEFT){
            return col  < 0;
        }
        else if(dir == Direction.RIGHT){
            return col > COLS -1;
        }
        else if(dir == Direction.UP){
            return row < 0;
        }
        else if(dir == Direction.DOWN){
            return row > ROWS -1;
        }
        return false;
    }

    private void moveTiles(Direction dir){
        boolean canMove = false;
        int horizontalDirection = 0;
        int verticalDirection = 0;

        if(dir == Direction.LEFT){
            horizontalDirection = -1;
            for(int row = 0; row<ROWS; row++){
                for(int col = 0; col<COLS; col++){//bubble
                    if(!canMove){
                        canMove = move(row,col,horizontalDirection,verticalDirection,dir);//predicts the function if the tile can move

                    }
                    else{
                        move(row,col,horizontalDirection,verticalDirection,dir);
                    }
                }
            }
        }

        else if(dir == Direction.RIGHT){
            horizontalDirection = 1;
            for(int row = 0; row<ROWS; row++){
                for(int col = COLS -1 ; col >=0;col--){//insertion right to the left. cant do left to right so using insertion we can solve this.
                    if(!canMove){
                        canMove = move(row,col,horizontalDirection,verticalDirection,dir);//predicts the function if the tile can move

                    }
                    else{
                        move(row,col,horizontalDirection,verticalDirection,dir);
                    }
                }
            }   //2248 ----> //0448 //right to left correct other way we would get 00016 this would not be right
        }

        else if(dir == Direction.UP){
            verticalDirection = -1;
            for(int row = 0; row<ROWS; row++){
                for(int col = 0; col<COLS; col++){
                    if(!canMove){
                        canMove = move(row,col,horizontalDirection,verticalDirection,dir);//predicts the function if the tile can move

                    }
                    else{
                        move(row,col,horizontalDirection,verticalDirection,dir);
                    }
                }
            }
        }

        else if(dir == Direction.DOWN) {
            verticalDirection = 1;
            for (int row = ROWS - 1; row >= 0; row--) {
                for (int col = 0; col < COLS; col++) {
                    if (!canMove) {
                        canMove = move(row, col, horizontalDirection, verticalDirection, dir);//predicts the function if the tile can move

                    } else {
                        move(row, col, horizontalDirection, verticalDirection, dir);
                    }
                }
            }
        }
        else{
            System.out.println(dir+" is not a valid direction");
        }

        for(int row = 0; row<ROWS;row++){
            for(int col = 0; col<COLS; col++){
                Tile current = board[row][col];
                if(current == null){
                    continue;
                }
                current.setCanCombine(true);
            }
        }
        if(canMove){
            spawnRandom();
            checkDead();
        }

    }

    private void checkDead(){
        for(int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (board[row][col] == null) {
                    return;//method does NOT return any values
                }
                if(checkSurroundTiles(row,col,board[row][col])){
                    return;//method does NOT return any values
                }

            }
        }
        dead = true;
        setHighScore();//this means that none of the tiles returned and you died or lost to be more precise.
    }

    private boolean checkSurroundTiles(int row,int col,Tile current){
        if(row > 0 ){
            Tile check = board[row-1][col];
            if(check == null){
                return true;
            }
            if(current.getValue() ==check.getValue()){
                return true;
            }
        }
        if(row < ROWS -1){
            Tile check = board[row+1][col];
            if(check == null){
                return true;
            }
            if(current.getValue() == check.getValue()) {
                return true;
            }
        }
        if(col > 0){
            Tile check = board[row][col-1];
            if(check == null){
                return true;
            }
            if(current.getValue() == check.getValue()) {
                return true;
            }
        }
        if(col < COLS -1 ){
            Tile check = board[row][col+1];
            if(check == null){
                return true;
            }
            if(current.getValue() == check.getValue()) {
                return true;
            }
        }
        return false; //if not of it add then this will return
    }

    private void checkKeys(){

        if(Keyboard.typed(KeyEvent.VK_LEFT)){
            moveTiles(Direction.LEFT);
            if(!hasStarted){
                hasStarted = true;
            }
        }
        if(Keyboard.typed(KeyEvent.VK_RIGHT)){
            moveTiles(Direction.RIGHT);
            if(!hasStarted){
                hasStarted = true;
            }
        }
        if(Keyboard.typed(KeyEvent.VK_UP)){
            moveTiles(Direction.UP);
            if(!hasStarted){
                hasStarted = true;
            }
        }
        if(Keyboard.typed(KeyEvent.VK_DOWN)){
            moveTiles(Direction.DOWN);
            if(!hasStarted){
                hasStarted = true;
            }
        }
    }

}