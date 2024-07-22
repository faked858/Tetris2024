import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;

public class Tetris extends JFrame implements ActionListener{
    public Canvas canvas;
    public JFrame frame;
    
    public void actionPerformed(ActionEvent e){
    }
    
    public Tetris(){//constructor
        PlayEvents.loadHighscore();
    }
    
    public static void main (String[] args){ 
        JFrame window = new JFrame("Tetris");//window name
        window.setDefaultCloseOperation(EXIT_ON_CLOSE);//exit on close 
        window.setResizable(false);//make sure window isnt resizable
        //window.setLocationRelativeTo(null);// window pops up on the centre of the screen
        
        GamePanel gp = new GamePanel();
        window.add(gp);//adds the GamePanel class to my window
        window.pack();
        //repaint();

        window.toFront();//put winodw in the front
        window.setVisible(true);//show window
        gp.launchGame();
    }

    public void gamePanel(){
        
    }
}