import Tetriminos.Block;
import Tetriminos.KeyInputs;
import Tetriminos.SuperMino;
import java.awt.*;
import javax.swing.JPanel;
public class GamePanel extends JPanel implements Runnable//handles launching the game and updating the game loop 
{
    public static int width = Block.CELLSIZE*SuperMino.blockMultiplier;
    public static int height = SuperMino.bottomY;
    static final int FRAMETIME = 16;//length of a single tick in time, in miliseconds, roughly 60 FPS

    Thread gameThread;
    PlayEvents pe;
    Tetris te;
    public GamePanel()
    {
        this.setPreferredSize(new Dimension(width,height));
        this.setBackground(Color.BLACK);//set dimensions and colour of window
        pe = new PlayEvents();
        te = new Tetris();
        //adding the key listener
        this.addKeyListener(new KeyInputs());
        this.setFocusable(true);
    }

    public void launchGame(){//initializes game
        gameThread = new Thread(this);
        gameThread.start();//automaticly calls the run method
    }

    public void run(){//game loop, updates the frames and score etc
        while(gameThread != null){//while program is running
            repaint();
            updateGame();
            try{
                Thread.sleep(FRAMETIME);
            }catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void updateGame(){//updates the game loop
        if(KeyInputs.pausePressed == false && pe.gameOver == false && KeyInputs.startOn == true){//only update the game loop when the game isnt paused or game over
            pe.update();
        }
    }

    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        pe.paint(g2);
    }
}
