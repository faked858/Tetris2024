package Tetriminos;
import java.awt.*;
import java.util.ArrayList;

public class SuperMino//handles rotation, collision, and drawing/updating all tetriminos
{
    public Block b[] = new Block[4];
    public Block tempB[] = new Block[4];//temp array used to test if collision will happen before rotating
    
    final static int DROPINTERVAL = 30; //every 30 frames a tetrimino will drop, or roughly every half second
    public static int blockMultiplier = 10;//has to be an even number. width of the board, in blocks
    public static int yBlockMultiplier = 20;//same as above variable, but for y axis
    //edges of the board
    public  static int leftX = 0;
    public  static int rightX = Block.CELLSIZE*blockMultiplier;//multiples of 32, to fit 32x32 size blocks evenly
    public  static int bottomY = Block.CELLSIZE*yBlockMultiplier;
    public static int topY = 0;
    
    int autoDrop = 0;//counts the drop interval 
    int direction = 0;//0,1,2,3 represent each possible direction for each tetrimino
    int deactivateCount = 0;//for the sliding when touching the floor mechanic
    public static int addScore;//for counting score when a mino is speedfalling
    
    boolean leftColide, bottomColide, rightColide;//used to help determine if tetriminos are coliding with walls and other tetriminos
    public boolean minoActive = true;//if active, tetrimino can freely move, used to help determine if mino is static or not
    public boolean deactivate;//true when bottomColide is true, used to make mino static once coliding with floor or static minos

    public static ArrayList<Block> staticBlocks = new ArrayList<>();//all inactive tetriminos
    
    KeyInputs ki;
    public SuperMino()
    {
        ki = new KeyInputs();
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
        checkRotationColide();//before updating the minos x,y, check it isnt colliding before its rotated
        if(leftColide == false && rightColide == false && bottomColide == false){//if tetrimino isnt colliding
            this.direction = direction;
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
        //changes these global variables based on weather or not the tetriminos are coliding with the left, right, and bottom walls
        rightColide = false;
        leftColide = false;
        bottomColide = false;

        //check if tetrimino is coliding with static tetriminos
        checkStaticColide();

        //wall collision
        //left wall
        for(int i = 0; i < b.length; i++){//loop through array and check each block
            if(b[i].x == leftX){
                leftColide = true;
            }
        }

        //right wall
        for(int i = 0; i < b.length; i++){
            if(b[i].x + Block.CELLSIZE == rightX){
                rightColide = true;
            }
        }

        //floor collision
        for(int i = 0; i < b.length; i++){
            if(b[i].y + Block.CELLSIZE == bottomY){
                bottomColide = true;
            }
        }
    }

    public void checkRotationColide(){//checks if tetrimino will colide with anything before its rotated
        rightColide = false;
        leftColide = false;
        bottomColide = false;

        //check if tetrimino is coliding with static tetriminos
        checkStaticColide();

        //loop through the TEMP array and check each block
        //left wall
        for(int i = 0; i < b.length; i++){
            if(tempB[i].x < leftX){
                leftColide = true;
            }
        }

        //right wall
        for(int i = 0; i < b.length; i++){
            if(tempB[i].x + Block.CELLSIZE > rightX){
                rightColide = true;
            }
        }

        //floor collision
        for(int i = 0; i < b.length; i++){
            if(tempB[i].y + Block.CELLSIZE > bottomY){
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
        if(deactivate){//if mino is coliding, deactivate it and make it static
            deactivating();
        }

        if(ki.upPressed){//get direction based on key inputs
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
            ki.upPressed = false;
        }

        checkMovementColide();//before moving mino, check if its coliding
        
        //move right
        if(ki.rightPressed){
            if(rightColide == false){
                b[0].x +=Block.CELLSIZE;
                b[1].x +=Block.CELLSIZE;
                b[2].x +=Block.CELLSIZE;
                b[3].x +=Block.CELLSIZE;
            }
            ki.rightPressed = false;
        }

        //move down
        if(ki.downPressed){
            if(bottomColide == false){
                b[0].y +=Block.CELLSIZE;
                b[1].y +=Block.CELLSIZE;
                b[2].y +=Block.CELLSIZE;
                b[3].y +=Block.CELLSIZE;
                addScore++;//when mino is moved down by player, add score
                autoDrop = 0;
                //reset the autodrop counter everytime user moves it down
            }
            ki.downPressed = false;
        }

        //move left
        if(ki.leftPressed){
            if(leftColide == false){
                b[0].x -=Block.CELLSIZE;
                b[1].x -=Block.CELLSIZE;
                b[2].x -=Block.CELLSIZE;
                b[3].x -=Block.CELLSIZE;
            }
            ki.leftPressed = false;
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
