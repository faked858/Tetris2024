import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.*;

public class Tetris extends JFrame implements ActionListener{
    public Canvas canvas;
    public JFrame frame;

    public void actionPerformed(ActionEvent e){
    }

    public Tetris(){//constructor
        PlayEvents.loadHighscore();
    }

    public static void main (String[] args){
        //draw the main window that displays the game
        JFrame window = new JFrame("Tetris");//window name
        window.setDefaultCloseOperation(EXIT_ON_CLOSE);
        window.setResizable(false);//make sure window isnt resizable

        //set window icon
        final String iconFile = "myIcon.png";
        ImageIcon icon = new ImageIcon(iconFile);
        window.setIconImage(icon.getImage());
        
        //add gamePanel to the window
        GamePanel gP = new GamePanel();
        window.add(gP);
        window.pack();

        window.toFront();//put winodw in the front
        window.setVisible(true);//show window
        gP.launchGame();//start loading the game
    }
}