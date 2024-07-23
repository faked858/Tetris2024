package Tetriminos;
import java.awt.*;
public class Block extends Rectangle
{
    //class for creating a single block, used to build the tetriminos
    public int x, y;
    public static final int CELLSIZE = 32;//size of each block
        public Color c;
    public Block(Color c)
    {
        this.c=c;
    }

    public void draw(Graphics2D g2){
        g2.setColor(c);
        g2.fillRect(x,y,CELLSIZE, CELLSIZE);
    }
}
