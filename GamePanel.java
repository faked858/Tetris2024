import Tetriminos.Block;
import Tetriminos.KeyInputs;
import Tetriminos.SuperMino;
import java.awt.*;
import javax.swing.JPanel;
public class GamePanel extends JPanel implements Runnable  
{
    public  static int WIDTH = Block.CELLSIZE*SuperMino.blockMultiplier;
    public  static int HEIGHT = SuperMino.BOTTOMY;
    static  int FRAMETIME = 16;//length of a single tick in time, in miliseconds, roughly 60 FPS
    static  int FPS = 1/60;
    
    Thread gameThread;
    PlayEvents PE;
    Tetris TE;
    public GamePanel()
    {
        this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        this.setBackground(Color.BLACK);
        PE = new PlayEvents();
        TE = new Tetris();
        //adding the key listener
        this.addKeyListener(new KeyInputs());
        this.setFocusable(true);
    }

    public void launchGame(){//initializes game
        gameThread = new Thread(this);
        gameThread.start();//automaticly calls the run method
    }

    public void run(){//game loop, updates the frames and score etc
        while(gameThread != null){
            repaint();
            updateGame();
            try{
                Thread.sleep(FRAMETIME);
            }catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void updateGame(){
        if(KeyInputs.pausePressed == false && PE.gameOver == false && KeyInputs.startOn == true){//only update the game loop when the game isnt paused or game over
            PE.update();
        }
    }
    
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        PE.paint(g2);
    }
}
