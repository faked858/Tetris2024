package Tetriminos;
import java.awt.*;


public class MinoT extends SuperMino
{
    
    public MinoT()
    {
        createBlock(Color.magenta);//call the create block method, make it orange
    }
    
    public void setXY(int x, int y){////creates the shape of the block by setting each individual block to its respective coordinates
        //  o   b1
        //o o o b2, b0, b3
        
        b[0].x = x;
        b[0].y = y;
        b[1].x = x; 
        b[1].y = y - Block.CELLSIZE;
        b[2].x = x - Block.CELLSIZE;
        b[2].y = y;
        b[3].x = x + Block.CELLSIZE;
        b[3].y = y;
        //when b0 gets updated, all other blocks update to match its position
    }
    
    public void getDirection0(){
        //   o   
        // o o o
        
        tempB[0].x = b[0].x;
        tempB[0].y = b[0].y;
        tempB[1].x = b[0].x;
        tempB[1].y = b[0].y - Block.CELLSIZE;
        tempB[2].x = b[0].x - Block.CELLSIZE;
        tempB[2].y = b[0].y;
        tempB[3].x = b[0].x + Block.CELLSIZE;
        tempB[3].y = b[0].y;
        
        updateXY(0);
    }
    
    public void getDirection1(){
        // o    b2
        // o o  b0,b1
        // o    b3
        
        tempB[0].x = b[0].x;
        tempB[0].y = b[0].y;
        tempB[1].x = b[0].x + Block.CELLSIZE;
        tempB[1].y = b[0].y;
        tempB[2].x = b[0].x;
        tempB[2].y = b[0].y - Block.CELLSIZE;
        tempB[3].x = b[0].x;
        tempB[3].y = b[0].y + Block.CELLSIZE;
        
        updateXY(1);
    }
    
    public void getDirection2(){
        // o o o
        //   o
        
        tempB[0].x = b[0].x;
        tempB[0].y = b[0].y;
        tempB[1].x = b[0].x;
        tempB[1].y = b[0].y + Block.CELLSIZE;
        tempB[2].x = b[0].x + Block.CELLSIZE;
        tempB[2].y = b[0].y;
        tempB[3].x = b[0].x - Block.CELLSIZE;
        tempB[3].y = b[0].y;
        
        updateXY(2);
    }
    
    public void getDirection3(){
        //   o
        // o o     
        //   o
        
        tempB[0].x = b[0].x;
        tempB[0].y = b[0].y;
        tempB[1].x = b[0].x - Block.CELLSIZE;
        tempB[1].y = b[0].y;
        tempB[2].x = b[0].x;
        tempB[2].y = b[0].y + Block.CELLSIZE;
        tempB[3].x = b[0].x;
        tempB[3].y = b[0].y - Block.CELLSIZE;
        
        updateXY(3);
    }
}