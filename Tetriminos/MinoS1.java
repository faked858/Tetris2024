package Tetriminos;
import java.awt.*;

public class MinoS1 extends SuperMino//handles creating S1 tetrimino and its directions
{

    public MinoS1()
    {
        createBlock(Color.red);//call the create block method, make it orange
    }

    public void setXY(int x, int y){////creates the shape of the block by setting each individual block to its respective coordinates
        //   o
        // o o
        // o

        b[0].x = x;
        b[0].y = y;
        b[1].x = x; 
        b[1].y = y - Block.CELLSIZE;
        b[2].x = x - Block.CELLSIZE;
        b[2].y = y;
        b[3].x = x - Block.CELLSIZE;
        b[3].y = y + Block.CELLSIZE;
        //when b0 gets updated, all other blocks update to match its position
    }

    public void getDirection0(){
        //   o
        // o o
        // o

        tempB[0].x = b[0].x;
        tempB[0].y = b[0].y;
        tempB[1].x = b[0].x;
        tempB[1].y = b[0].y - Block.CELLSIZE;
        tempB[2].x = b[0].x - Block.CELLSIZE;
        tempB[2].y = b[0].y;
        tempB[3].x = b[0].x - Block.CELLSIZE;
        tempB[3].y = b[0].y + Block.CELLSIZE;

        updateXY(0);
    }

    public void getDirection1(){
        // o o
        //   o o

        tempB[0].x = b[0].x;
        tempB[0].y = b[0].y;
        tempB[1].x = b[0].x + Block.CELLSIZE;
        tempB[1].y = b[0].y;
        tempB[2].x = b[0].x;
        tempB[2].y = b[0].y - Block.CELLSIZE;
        tempB[3].x = b[0].x - Block.CELLSIZE;
        tempB[3].y = b[0].y - Block.CELLSIZE;

        updateXY(1);
    }

    public void getDirection2(){//again, only two directions
        getDirection0();
    }

    public void getDirection3(){
        getDirection1();
    }
}
