package Tetriminos;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
//handles all key inputs for the game
public class KeyInputs implements KeyListener
{     
    public static boolean upPressed, downPressed, leftPressed, rightPressed, pausePressed, startPressed;//each button the game uses
    public static boolean startOn;
    public KeyInputs()
    {
        upPressed = false;
        downPressed = false;
        leftPressed = false;
        rightPressed = false;
        startPressed = false;
    }

    public void keyTyped(KeyEvent e){}//dont need this

    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        switch(key){
            case KeyEvent.VK_W://WASD keys
                upPressed = true;
                break;
            case KeyEvent.VK_UP://arrow keys
                upPressed = true;
                break;
                
            case KeyEvent.VK_A:
                leftPressed = true;
                break;
            case KeyEvent.VK_LEFT:
                leftPressed = true;
                break;
                
            case KeyEvent.VK_S:
                downPressed = true;
                break;
            case KeyEvent.VK_DOWN:
                downPressed = true;
                break;
                
            case KeyEvent.VK_D:
                rightPressed = true;
                break;
            case KeyEvent.VK_RIGHT:
                rightPressed = true;
                break;
                
            case KeyEvent.VK_SPACE://pasuing the game
                if(pausePressed){
                    pausePressed = false;
                }else pausePressed = true;
                break;
            case KeyEvent.VK_E://starting the game
                startPressed = true;
                if(startPressed){
                    startOn = true;
                }
                break;
        }
    }

    public void keyReleased(KeyEvent e){
    }
}
