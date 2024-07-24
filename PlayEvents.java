import Tetriminos.Block;
import Tetriminos.KeyInputs;
import Tetriminos.SuperMino;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import javax.swing.*;
public class PlayEvents extends JPanel
{
    //class that will manage all events on the board, including score, clearing lines, save and load highscore, randomisation and picking each mino, and drawing user interface

    //all sides of the play area
    static int leftX = SuperMino.leftX;
    static int rightX = SuperMino.rightX;
    static int topY = SuperMino.topY;
    static int bottomY = SuperMino.bottomY;
    static int rowLength = SuperMino.blockMultiplier;
    
    //starting x.y positions for the tetriminos
    final static int DEFAULTX = rightX/2;
    final static int DEFAULTY = topY + 2*Block.CELLSIZE;

    //for score
    int score;
    static int highScore;
    
    //holds and randomizes the upcoming tetriminos
    ArrayList<SuperMino> minoBag = new ArrayList<>();

    SuperMino currentMino;
    SuperMino nextMino;

    public SuperMino active = new SuperMino();
    boolean gameOver = false;

    public PlayEvents()
    {
        //set starting minos
        currentMino = selectMino();
        currentMino.setXY(DEFAULTX,DEFAULTY);

        nextMino = selectMino();
    }

    public SuperMino selectMino(){
        //add two of each mino to the minobag
        if(minoBag.isEmpty()){
            minoBag.add(new Tetriminos.MinoL1());
            minoBag.add(new Tetriminos.MinoL1());
            minoBag.add(new Tetriminos.MinoL2());
            minoBag.add(new Tetriminos.MinoL2());
            minoBag.add(new Tetriminos.MinoT());
            minoBag.add(new Tetriminos.MinoT());
            minoBag.add(new Tetriminos.MinoI());
            minoBag.add(new Tetriminos.MinoI());
            minoBag.add(new Tetriminos.MinoO());
            minoBag.add(new Tetriminos.MinoO());
            minoBag.add(new Tetriminos.MinoS1());
            minoBag.add(new Tetriminos.MinoS1());
            minoBag.add(new Tetriminos.MinoS2());
            minoBag.add(new Tetriminos.MinoS2());
            Collections.shuffle(minoBag);//randomize them
        }

        //loops through the array and selects a shuffled mino
        for(int i = 0; i <= minoBag.size(); i++){
            if(i <= minoBag.size()){
                Tetriminos.SuperMino temp = minoBag.get(i);
                minoBag.remove(i);
                return temp;//return the selected mino
            }else{
                i = 0;
            }
        }
        return null;
    }

    public void update(){
        if(currentMino.minoActive == false){
            //if the current mino isnt active, put it in staticBlocks
            SuperMino.staticBlocks.add(currentMino.b[0]);
            SuperMino.staticBlocks.add(currentMino.b[1]);
            SuperMino.staticBlocks.add(currentMino.b[2]);
            SuperMino.staticBlocks.add(currentMino.b[3]);

            gameOver();// check if the game is over before procceding

            currentMino.deactivate = false;

            //get the next mino
            currentMino = nextMino;
            currentMino.setXY(DEFAULTX,DEFAULTY);
            nextMino = selectMino();//select the next mino
            lineDelete();//if mino is inactive, check if the line its in can be deleted
        }else{// if current mino is active, update mino
            currentMino.update();
            for(int i = 0; SuperMino.addScore > 0; i++){//adding score when mino is speedfalling
                score++;
                SuperMino.addScore--;
            }
        }
    }

    private void gameOver(){
        //if the currentmino is immediately colliding, then the game is over
        if(currentMino.b[0].x == DEFAULTX && currentMino.b[0].y == DEFAULTY){
            if(score > highScore){
                highScore = score;//set highscore
                highScore += 10;//adjust for descrepency in highscore
                saveHighscore();//save highscore
            }
            gameOver = true;
        }
    }

    private void lineDelete(){
        int blockCount = 0;
        int x = leftX;
        int y = topY;
        while(x < rightX && y < bottomY){//loop through  the play area
            for(int i =0; i < SuperMino.staticBlocks.size(); i++){//loop through staticBlocks
                //if there is a static block where x&y currently are, count it
                if(SuperMino.staticBlocks.get(i).x == x && SuperMino.staticBlocks.get(i).y == y){
                    blockCount++;
                }
            }

            x+= Block.CELLSIZE;

            //once the scan reaches the rigth side, move down and continue from the left side
            if(x == rightX){
                if(blockCount == rowLength){//if the row is filled with static blocks, delete it
                    for(int i = SuperMino.staticBlocks.size()-1; i > -1; i--){//loop through static blocks from top to bottom
                        if(SuperMino.staticBlocks.get(i).y == y){
                            SuperMino.staticBlocks.remove(i);//remove selected block
                            score += 10;//increase score for every block deleted, if board is large and liens are long, more score will be added because its harder
                        }
                    }
                    //once a row has been deleted, move everything above it down
                    for(int i = 0; i < SuperMino.staticBlocks.size(); i++){
                        if(SuperMino.staticBlocks.get(i).y < y){//if the y of current staticBlock is less than current y scan value:
                            SuperMino.staticBlocks.get(i).y +=Block.CELLSIZE;//move it down by CELLSIZE
                        }
                    }
                }
                blockCount = 0;
                x = leftX;
                y += Block.CELLSIZE;
            }
        }
        score += 10;//increase score each time a block is placed
    }

    private void saveHighscore(){
        BufferedWriter bw;
        try{
            bw = new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "/highscore.txt", false));
            bw.write("" + highScore);
            bw.flush();
            bw.close();
            System.out.println("highscore saved: "+ highScore);
        }catch(IOException e){
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error: could not save highscore", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void loadHighscore(){
        BufferedReader br;
        String line = "";
        try{
            br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/highscore.txt"));
            line = br.readLine();
            br.close();
        }catch(IOException e){
            line = "";
        }

        if(line != ""){
            highScore = Integer.parseInt(line);
        }
    }

    public void paint(Graphics g){//where all the drawing happens
        super.paint(g);
        Graphics2D g2 = (Graphics2D)g;

        if(KeyInputs.startOn){//once the game has started
            //draw the currentMino
            if(currentMino != null){
                currentMino.draw(g2);
            }

            //draw the static minos
            for(int i = 0; i < SuperMino.staticBlocks.size(); i++){
                SuperMino.staticBlocks.get(i).draw(g2);
            }
                        
            //draw score counter
            g2.setColor(Color.orange);
            g2.setFont(g2.getFont().deriveFont(25f));
            //x,y location
            String s = String.valueOf(score);
            int scoreWidth = g.getFontMetrics().stringWidth(s);
            int scoreX = rightX-5-scoreWidth;//making sure no matter how big score is, it wont clip outside the board
            g2.drawString(s,scoreX,25);

            //draw highscore
            int x = 10;
            int y = 25;
            g2.drawString("highscore: "+highScore,x,y);
        }

        //draw pause, game over, start game menus
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(40f));
        if(gameOver){
            //draw game over
            int width1 = g.getFontMetrics().stringWidth("GAME OVER");
            int gameOX = rightX/2 - width1/2;//centre the text on screen no matter the board width
            int gameOY = topY + 70;
            g2.drawString("GAME OVER",gameOX ,gameOY);
        }else if(KeyInputs.pausePressed){
            //draw paused
            int width2 = g.getFontMetrics().stringWidth("PASUED");
            int pauseX = rightX/2 - width2/2;
            int pauseY = topY + 70;
            g2.drawString("PAUSED",pauseX ,pauseY);
        }else if(!KeyInputs.startOn){
            //draw anything that shows before the game starts
            int logoX = rightX/2-81;
            //draw the tetris logo
            final String logoFile = "TetrisLogo.png";
            ImageIcon logo = new ImageIcon(logoFile);
            logo.paintIcon(this,g2,logoX,40);
            
            //draw start game button
            final String fileName = "E_button.png";
            ImageIcon image = new ImageIcon(fileName);
            image.paintIcon(this,g2,logoX+45,100);
            
            //draw movement info png
            int infoX = rightX/2 - 72;//72 is roughly half the width of the movementInfo png in pixels
            int infoY = bottomY - 70;
            final String infoFile = "movementInfo.png";
            ImageIcon info = new ImageIcon(infoFile);
            info.paintIcon(this,g2,infoX,infoY);
        }
    }
}