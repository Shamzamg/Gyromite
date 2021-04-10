package Entities;

import Entities.DynamicEntity;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import Environment.Environment;
import Utils.Direction;
import Utils.Type;

/**
 * Smick class, used as enemies for the Professor
 */
public class Smick extends DynamicEntity {

    private int initialSpeed = 5;
    boolean isClimbing = false;
    //every 3 ticks it will move
    private int speed = 5;
    Direction movementDirection = Direction.RIGHT;

    public void startClimbing(){ isClimbing = true; }

    public boolean hasStartedClimbing(){ return isClimbing; }

    public void stopClimbing(){ isClimbing = false; }

    public void resetSpeed(){
        speed = initialSpeed;
    }

    public void resetDirection(){
        switch(movementDirection){
            case LEFT:
                movementDirection = Direction.RIGHT;
                break;
            case RIGHT:
                movementDirection = Direction.LEFT;
                break;
            case UP:
                if(!isClimbing){
                    Random rd = new Random(); // creating Random object
                    boolean rand = rd.nextBoolean();

                    if(rand) movementDirection = Direction.RIGHT;

                    else movementDirection = Direction.LEFT;
                    break;
                }
                movementDirection = Direction.DOWN;
                break;

            case DOWN:
                if(!isClimbing){
                    Random rd = new Random(); // creating Random object
                    boolean rand = rd.nextBoolean();

                    if(rand) movementDirection = Direction.RIGHT;

                    else movementDirection = Direction.LEFT;
                    break;
                }
                movementDirection = Direction.UP;
                break;
        }
    }

    public void reduceSpeed(){ speed--; }

    public Smick(Environment _env) {
        super(_env);
        Random rd = new Random(); // creating Random object
        boolean rand = rd.nextBoolean();

        if(rand) movementDirection = Direction.RIGHT;

        else movementDirection = Direction.LEFT;
    }

    @Override
    public boolean isCrushable() {
        return true;
    }

    @Override
    public boolean isSupport() {
        return false;
    }

    @Override
    public boolean isClimbable() {
        return false;
    }

    @Override
    public Type getType() {
        return Type.SMICK;
    }

    public Direction getMovementDirection(){ return movementDirection; }

    public void setMovementDirection(Direction md){
        movementDirection = md;
        setLookingDirection(md);
    }

    public int getSpeed() { return speed; }
}
