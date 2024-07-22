package Tetriminos;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
//handles all key inputs for the game
public class KeyInputs implements KeyListener
{     
    public static boolean upPressed, downPressed, leftPressed, rightPressed, pausePressed, startPressed;
    public static boolean startOn;
    public KeyInputs()
    {
        System.out.println(this);
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
            case KeyEvent.VK_W:
                upPressed = true;
                break;
            case KeyEvent.VK_A:
                leftPressed = true;
                break;
            case KeyEvent.VK_S:
                downPressed = true;
                break;
            case KeyEvent.VK_D:
                rightPressed = true;
                break;
            case KeyEvent.VK_SPACE:
                if(pausePressed){
                    pausePressed = false;
                }else pausePressed = true;
                break;
            case KeyEvent.VK_E:
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
