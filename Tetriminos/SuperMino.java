package Tetriminos;

import java.awt.*;
import java.util.ArrayList;

public class SuperMino
{
    //this class will be the super class for all tetrimios, all minos will extend this class
    public Block b[] = new Block[4];
    public Block tempB[] = new Block[4];//two block classes
    
    final static int DROPINTERVAL = 30; //every 30 frames a tetrimino will drop, or roughly every half second
    public  static int blockMultiplier = 10;//has to be an even number. width of the board, in blocks
    public  static int LEFTX = 0;
    public  static int RIGHTX = Block.CELLSIZE*blockMultiplier;//multiples of 32, to fit 32x32 size blocks evenly
    public  static int TOPY = 0;
    public  static int BOTTOMY = 640;
    
    int autoDrop = 0;//counts the drop interval 
    int direction = 0;//0,1,2,3 represent each possible direction for each tetrimino
    int deactivateCount = 0;//for the sliding when touching the floor mechanic
    public static int addScore;//for counting score when a mino is speedfalling
    
    boolean leftColide, bottomColide, rightColide;
    public boolean minoActive = true;
    public boolean deactivate;

    public static ArrayList<Block> staticBlocks = new ArrayList<>();//all inactive tetriminos
    
    KeyInputs KI;
    public SuperMino()
    {
        KI = new KeyInputs();
    }

    public void createBlock(Color c){//creates each of the four blocks that make up each tetrimino
        b[0] = new Block(c);//each one of these represents a block of a tetrimino
        b[1] = new Block(c);
        b[2] = new Block(c);
        b[3] = new Block(c);
        tempB[0] = new Block(c);
        tempB[1] = new Block(c);
        tempB[2] = new Block(c);
        tempB[3] = new Block(c);
    }

    public void setXY(int x, int y){
    }

    public void updateXY(int direction){
        checkRotationColide();

        if(leftColide == false && rightColide == false && bottomColide == false){//if tetrimino isnt colliding
            this.direction = direction;//temp array used if a rotation needs to be canceled becuase collision
            b[0].x = tempB[0].x;
            b[0].y = tempB[0].y;
            b[1].x = tempB[1].x;
            b[1].y = tempB[1].y;
            b[2].x = tempB[2].x;
            b[2].y = tempB[2].y;
            b[3].x = tempB[3].x;
            b[3].y = tempB[3].y;
        }        
    }

    public void getDirection0(){}

    public void getDirection1(){}

    public void getDirection2(){}

    public void getDirection3(){}

    public void checkMovementColide(){
        rightColide = false;
        leftColide = false;
        bottomColide = false;

        //check static collsion
        checkStaticColide();

        //wall collision
        //left wall
        for(int i = 0; i < b.length; i++){//loop through array and check each block
            if(b[i].x == LEFTX){
                leftColide = true;
            }
        }

        //right wall
        for(int i = 0; i < b.length; i++){
            if(b[i].x + Block.CELLSIZE == RIGHTX){
                rightColide = true;
            }
        }

        //floor collision
        for(int i = 0; i < b.length; i++){
            if(b[i].y + Block.CELLSIZE == BOTTOMY){
                bottomColide = true;
            }
        }
    }

    public void checkRotationColide(){
        rightColide = false;
        leftColide = false;
        bottomColide = false;

        //check static collsion
        checkStaticColide();

        //loop through the TEMP array and check each block
        for(int i = 0; i < b.length; i++){
            if(tempB[i].x < LEFTX){
                leftColide = true;
            }
        }

        //right wall
        for(int i = 0; i < b.length; i++){
            if(tempB[i].x + Block.CELLSIZE > RIGHTX){
                rightColide = true;
            }
        }

        //floor collision
        for(int i = 0; i < b.length; i++){
            if(tempB[i].y + Block.CELLSIZE > BOTTOMY){
                bottomColide = true;
            }
        }
    }

    public void checkStaticColide(){//checks collision with static blocks array, the placed tetriminos
        for(int i=0; i < staticBlocks.size(); i++){
            int staticX = staticBlocks.get(i).x;//check collsion of each block in staticBlocks
            int staticY = staticBlocks.get(i).y;

            //check colision for the bottom
            for(int j = 0; j <b.length; j++){
                if(b[j].y + Block.CELLSIZE == staticY && b[j].x == staticX){
                    bottomColide = true;
                }
            }

            //check colision for the left side
            for(int j = 0; j <b.length; j++){
                if(b[j].x - Block.CELLSIZE == staticX && b[j].y == staticY){
                    leftColide = true;
                }
            }

            //check colision for right side
            for(int j=0; j < b.length; j++){
                if(b[j].x + Block.CELLSIZE == staticX && b[j].y == staticY){
                    rightColide = true;
                }
            }
        }
    }

    public void update(){//updates the screen and handles tetrimino movement
        if(deactivate){
            deactivating();
        }

        if(KI.upPressed){
            switch(direction){
                case 0: getDirection1();
                    break;
                case 1: getDirection2();
                    break;
                case 2: getDirection3();
                    break;
                case 3: getDirection0();
                    break;
            }
            KI.upPressed = false;
        }

        checkMovementColide();
        
        //move right
        if(KI.rightPressed){
            if(rightColide == false){
                b[0].x +=Block.CELLSIZE;
                b[1].x +=Block.CELLSIZE;
                b[2].x +=Block.CELLSIZE;
                b[3].x +=Block.CELLSIZE;
            }
            KI.rightPressed = false;
        }

        //move down
        if(KI.downPressed){
            if(bottomColide == false){
                b[0].y +=Block.CELLSIZE;
                b[1].y +=Block.CELLSIZE;
                b[2].y +=Block.CELLSIZE;
                b[3].y +=Block.CELLSIZE;
                addScore++;//when mino is moved down by player, add score
                autoDrop = 0;
                //reset the autodrop counter everytime user moves it down
            }
            KI.downPressed = false;
        }

        //move left
        if(KI.leftPressed){
            if(leftColide == false){
                b[0].x -=Block.CELLSIZE;
                b[1].x -=Block.CELLSIZE;
                b[2].x -=Block.CELLSIZE;
                b[3].x -=Block.CELLSIZE;
            }
            KI.leftPressed = false;
        }
        
        if(bottomColide){//if tetrimino is coliding with the floor, deactivate
            deactivate = true;
        }else{// if tetrimino is active, allow movement
            //auto falling feature
            autoDrop++;
            if(autoDrop == DROPINTERVAL){//once autodrop reaches 30, reset back to 0
                b[0].y+= Block.CELLSIZE;
                b[1].y+= Block.CELLSIZE;
                b[2].y+= Block.CELLSIZE;
                b[3].y+= Block.CELLSIZE;
                autoDrop = 0;
            }
        }
    }
    
    private void deactivating(){
        deactivateCount++;
        if(deactivateCount == DROPINTERVAL){// wait for time it takes to drop once to set block static
            deactivateCount = 0;
            checkMovementColide();//check if bottom is still touching the floor
            if(bottomColide){
                minoActive = false;//set mino to inactive
            }
        }
    }
    
    public void draw(Graphics2D g2){//draw each block
        g2.setColor(b[0].c);
        g2.fillRect(b[0].x, b[0].y, Block.CELLSIZE, Block.CELLSIZE);
        g2.fillRect(b[1].x, b[1].y, Block.CELLSIZE, Block.CELLSIZE);
        g2.fillRect(b[2].x, b[2].y, Block.CELLSIZE, Block.CELLSIZE);
        g2.fillRect(b[3].x, b[3].y, Block.CELLSIZE, Block.CELLSIZE);
    }
}
