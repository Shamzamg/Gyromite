package ViewController;

import Entities.DynamicEntity;
import Entities.Entity;
import Entities.Professor;
import Environment.Environment;
import Movements.DirectionsControl;
import Utils.*;
import org.apache.commons.collections.map.MultiKeyMap;

import java.awt.*;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;


/**
 *
 * @author shams
 */
public class ViewController extends JFrame implements Observer {

    Environment env;
    int sizeX, sizeY;
    MultiKeyMap assetByParam;
    JLabel [][] fenetre;
    Direction blueDirection = Direction.DOWN;
    Direction orangeDirection = Direction.DOWN;
    //used to not keep loading start and end screen
    boolean startScreen=false, endScreen=false;

    public ViewController(Environment env, int size) {

        super();

        this.env = env;
        this.sizeX = size + 1; //we add header size
        this.sizeY = size;

        build();

        AssetByParam a = new AssetByParam();
        this.assetByParam = a.getAssetByParam();

        listenKeyboardInputs();
    }

    private Direction switchBlueDirection(){
        if(blueDirection == Direction.UP)
            blueDirection = Direction.DOWN;
        else
            blueDirection = Direction.UP;
        return blueDirection;
    }

    private Direction switchOrangeDirection(){
        if(orangeDirection == Direction.UP)
            orangeDirection = Direction.DOWN;
        else
            orangeDirection = Direction.UP;
        return orangeDirection;
    }

    private void listenKeyboardInputs() {
        addKeyListener(new KeyAdapter() { // new KeyAdapter() { ... } est une instance de classe anonyme, il s'agit d'un objet qui correspond au controleur dans MVC
            @Override
            public void keyPressed(KeyEvent e) {
                switch(e.getKeyCode()) {  // we check which key was pressed
                    //if it is an arrow key, we move the Professor
                    case KeyEvent.VK_LEFT : DirectionsControl.getInstance().setCurrentDirection(Direction.LEFT, Utils.Type.PROFESSOR); break;
                    case KeyEvent.VK_RIGHT : DirectionsControl.getInstance().setCurrentDirection(Direction.RIGHT, Utils.Type.PROFESSOR); break;
                    case KeyEvent.VK_UP : DirectionsControl.getInstance().setCurrentDirection(Direction.UP, Utils.Type.PROFESSOR); break;
                    case KeyEvent.VK_DOWN : DirectionsControl.getInstance().setCurrentDirection(Direction.DOWN, Utils.Type.PROFESSOR); break;
                    //if we press A, we move the blue columns
                    case KeyEvent.VK_A: DirectionsControl.getInstance().setCurrentDirection(switchBlueDirection(), Utils.Type.BLUE_COLUMN); break;
                    //if we press B, we move the orange columns
                    case KeyEvent.VK_B: DirectionsControl.getInstance().setCurrentDirection(switchOrangeDirection(), Utils.Type.ORANGE_COLUMN); break;
                    //if the game hasn't started yet
                    case KeyEvent.VK_ENTER:
                        if(!(env.getGameState() == GameState.PLAY))
                            env.switchGameState();
                        break;
                    case KeyEvent.VK_ESCAPE: System.exit(0); break;
                }
            }
        });
    }

    public void build() {

        //JMenu jm = new JMenu();
        JMenuBar jm = new JMenuBar();
        
        JMenu m = new JMenu("Jeu");
        
        JMenuItem mi = new JMenuItem("Partie");
        
        //ItemListener i = new Item
        
        m.add(mi);
        
        jm.add(m);

        setJMenuBar(jm);
        
        setTitle("Gyromite");
        setSize(sizeY*sizeY, sizeX*sizeX);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // permet de terminer l'application à la fermeture de la fenêtre

        fenetre = new JLabel[sizeX][sizeY];

        setLayout(new GridLayout(sizeX, sizeY));

        setResizable(false);

        for(int i=0;i<sizeX;i++){
            for(int j=0;j<sizeY;j++){
                fenetre[i][j] = new JLabel();
                add(fenetre[i][j]);
            }
        }
    }

    private ImageIcon loadIcon(String url){
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream(url));
        } catch (IOException ex) {
            Logger.getLogger(ViewController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

        return new ImageIcon(image);
    }

    public void placeHeaderBricks(int start, int stop){
        for(int i=start;i<stop;i++)
            fenetre[0][i].setIcon(loadIcon("/assets/header_brick.png"));
    }

    public void showScoreInHeader(){
        fenetre[0][2].setIcon(loadIcon("/assets/score_s.png"));
        fenetre[0][3].setIcon(loadIcon("/assets/score_c.png"));
        fenetre[0][4].setIcon(loadIcon("/assets/score_separator.png"));

        //calculate to show the score
        int score = env.getScore();

        for(int i=2; i>=0;i--){
            int numberToShow = (int) (score/Math.pow(10,i));
            fenetre[0][7-i].setIcon(loadIcon("/assets/"+numberToShow+".png"));
            score -= numberToShow*Math.pow(10,i);
        }
    }

    public void showTimeInHeader(){
        fenetre[0][10].setIcon(loadIcon("/assets/time_t.png"));
        fenetre[0][11].setIcon(loadIcon("/assets/time_i.png"));
        fenetre[0][12].setIcon(loadIcon("/assets/time_m.png"));
        fenetre[0][13].setIcon(loadIcon("/assets/time_e.png"));

        fenetre[0][14].setIcon(loadIcon("/assets/header_brick.png"));

        //calculate to show the time
        int timer = env.getTimer();

        for(int i=2; i>=0;i--){
            int numberToShow = (int) (timer/Math.pow(10,i));
            fenetre[0][17-i].setIcon(loadIcon("/assets/"+numberToShow+".png"));
            timer -= numberToShow*Math.pow(10,i);
        }

    }

    public void loadGameHeader(){

        placeHeaderBricks(0,2);

        showScoreInHeader();

        placeHeaderBricks(8,10);

        showTimeInHeader();

        placeHeaderBricks(18,20);

    }

    private void loadGameScreen(){
        loadGameHeader();

        Point coordinates = env.getCamera().getRenderCoordinates();

        //we start after header
        for(int i=1;i<sizeX;i++){
            for(int j=0;j<sizeY;j++){

                //we substract the header size
                Entity e = env.getMap(coordinates.x + i - 1, coordinates.y + j);
                Direction currentDirection = null;

                //dynamic entities can look in different directions
                if(e instanceof DynamicEntity){
                    currentDirection = ((DynamicEntity) e).getLookingDirection();
                }

                fenetre[i][j].setIcon((ImageIcon) assetByParam.get(e.getType(), currentDirection));
            }
        }
    }

    private void loadGameEndScreen() {
        for(int i=0;i<sizeX;i++){
            for(int j=0;j<sizeY; j++){
                fenetre[0][j].setIcon(loadIcon("/assets/void.png"));
                fenetre[1][j].setIcon(loadIcon("/assets/wall.png"));
                if(!(((j > 6) && (j < 13)) && ((i > 6) && (i < 9))))
                    fenetre[i][j].setIcon(loadIcon("/assets/void.png"));
                if(j == 2)
                    fenetre[i][j].setIcon(loadIcon("/assets/wall.png"));
                if(j == sizeY - 3)
                    fenetre[i][j].setIcon(loadIcon("/assets/wall.png"));
                fenetre[sizeX-2][j].setIcon(loadIcon("/assets/wall.png"));
                fenetre[sizeX-1][j].setIcon(loadIcon("/assets/void.png"));
            }
        }


        //show "sc:score"
        fenetre[8][7].setIcon(loadIcon("/assets/score_end_s.png"));
        fenetre[8][8].setIcon(loadIcon("/assets/score_end_c.png"));
        fenetre[8][9].setIcon(loadIcon("/assets/score_end_separator.png"));

        //calculate to show the score
        int score = env.getScore();

        for(int i=10; i<13;i++){
            int numberToShow = (int) (score/Math.pow(10,i));
            fenetre[8][i].setIcon(loadIcon("/assets/"+numberToShow+".png"));
            score -= numberToShow*Math.pow(10,i);
        }

        //show "enter: again"
        fenetre[9][7].setIcon(loadIcon("/assets/enter.png"));
        fenetre[9][8].setIcon(loadIcon("/assets/commas.png"));
        fenetre[9][9].setIcon(loadIcon("/assets/again_a.png"));
        fenetre[9][10].setIcon(loadIcon("/assets/again_g.png"));
        fenetre[9][11].setIcon(loadIcon("/assets/again_a.png"));
        fenetre[9][12].setIcon(loadIcon("/assets/quit_i.png"));
        fenetre[9][13].setIcon(loadIcon("/assets/again_n.png"));

        //show "esc: quit"
        fenetre[10][7].setIcon(loadIcon("/assets/escape.png"));
        fenetre[10][8].setIcon(loadIcon("/assets/commas.png"));
        fenetre[10][9].setIcon(loadIcon("/assets/quit_q.png"));
        fenetre[10][10].setIcon(loadIcon("/assets/quit_u.png"));
        fenetre[10][11].setIcon(loadIcon("/assets/quit_i.png"));
        fenetre[10][12].setIcon(loadIcon("/assets/start_t.png"));
    }

    private void loadStartingScreen() {
        for(int i=0;i<sizeX;i++){
            for(int j=0;j<sizeY; j++){
                fenetre[0][j].setIcon(loadIcon("/assets/void.png"));
                fenetre[1][j].setIcon(loadIcon("/assets/wall.png"));
                if(!(((j > 6) && (j < 13)) && (i == 8)))
                        fenetre[i][j].setIcon(loadIcon("/assets/void.png"));
                if(j == 2)
                    fenetre[i][j].setIcon(loadIcon("/assets/wall.png"));
                if(j == sizeY - 3)
                    fenetre[i][j].setIcon(loadIcon("/assets/wall.png"));
                fenetre[sizeX-2][j].setIcon(loadIcon("/assets/wall.png"));
                fenetre[sizeX-1][j].setIcon(loadIcon("/assets/void.png"));
            }
        }
        fenetre[8][7].setIcon(loadIcon("/assets/enter.png"));
        fenetre[8][8].setIcon(loadIcon("/assets/commas.png"));
        fenetre[8][9].setIcon(loadIcon("/assets/start_s.png"));
        fenetre[8][10].setIcon(loadIcon("/assets/start_t.png"));
        fenetre[8][11].setIcon(loadIcon("/assets/start_a.png"));
        fenetre[8][12].setIcon(loadIcon("/assets/start_r.png"));
        fenetre[8][13].setIcon(loadIcon("/assets/start_t.png"));

    }

    public void updateScreen(){

        //know which screen to load depending on the state of the game
        switch(env.getGameState()){
            case START:
                if(!startScreen){
                    loadStartingScreen();
                    endScreen = false;
                    startScreen = true;
                }
                break;
            case PLAY:
                loadGameScreen();
                startScreen = false;
                endScreen = false;
                break;
            case FINISH:
                if(!endScreen){
                    loadGameEndScreen();
                    startScreen = false;
                    endScreen = true;
                }
                break;
        }

    }

    @Override
    public void update(Observable o, Object arg) {
        updateScreen();
    }
}
