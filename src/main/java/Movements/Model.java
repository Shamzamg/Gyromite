package Movements;


import Entities.Column;
import Entities.Professor;
import Environment.Environment;
import Movements.DirectionsControl;
import Movements.MovingOperator;
import Utils.Direction;
import Utils.GameState;
import Utils.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import static java.lang.Thread.*;


public class Model extends Observable implements Runnable{

    Environment env;
    private ArrayList<MovingOperator> lstMovements = new ArrayList<MovingOperator>();
    private long pause;
    private boolean running;
    private Thread thread;
    private int test = 0;

    public void add(MovingOperator movement){
        lstMovements.add(movement);
    }

    public Model(Environment _env){
        env = _env;
    }

    public void start(long _pause) {
        pause = _pause;
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    private void interrupt(){
        running = false;
    }

    public void replay(){
        running = true;
    }

    public void loadNextLevel(){
        interrupt();
        env.resetCmptDepl();
        lstMovements = new ArrayList<MovingOperator>();
    }

    public void run(){
        boolean update = false;

        while(running) {
            env.resetCmptDepl();

            boolean columnMoving = false;
            Direction columnDirection = null;
            Type columnType = null;

            //move all the Smicks
            env.moveSmicks(pause);

            for (MovingOperator m : lstMovements) {
                //if something moved
                if (m.realizeMovement()){
                    //if it is a column, we move it until it is not possible anymore
                    Type mType = m.getType();
                    if((mType == Type.BLUE_COLUMN) || (mType == Type.ORANGE_COLUMN)){
                        columnMoving = true;
                        columnDirection = m.getCurrentDirection();
                        columnType = mType;
                    }
                    update = true;
                }

            }

            //System.out.println(Thread.currentThread().getName());
            DirectionsControl.getInstance().reset();

            if(columnMoving)
                DirectionsControl.getInstance().setCurrentDirection(columnDirection, columnType);

            env.setColumnLookingDirections();

            if (update) {
                setChanged();
                notifyObservers();
            }

            try {
                //if we're playing, we decrease the timer at each timeloop
                if(env.getGameState() == GameState.PLAY){
                    if(env.getTimer() <= 0){
                        env.switchGameState();
                    }
                    //decrement the timer
                    env.setTimer(env.getTimer() - 1);
                }

                sleep(pause);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

