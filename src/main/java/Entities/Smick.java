package Entities;

import Entities.DynamicEntity;
import java.util.concurrent.ThreadLocalRandom;
import Environment.Environment;
import Utils.Direction;
import Utils.Type;

public class Smick extends DynamicEntity {

    private double movementTime = -1;
    private int initialSpeed = 5;
    //every 3 ticks it will move
    private int speed = 5;
    Direction movementDirection;

    public void resetSpeed(){
        speed = initialSpeed;
    }

    public void resetMovementTime(){
        int min = 2000;
        int max = 8000;
        movementTime = ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public void resetDirection(){
        int rand = ThreadLocalRandom.current().nextInt(0, 1 + 1);

        if(rand > 0){
            lookingDirection = Direction.LEFT;
            movementDirection = Direction.LEFT;
        }

        else{
            lookingDirection = Direction.RIGHT;
            movementDirection = Direction.RIGHT;
        }

    }

    public void reduceSpeed(){ speed--; }

    public void resetMovement(){
        resetMovementTime();
        resetDirection();
    }

    public Smick(Environment _env) {
        super(_env);
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

    public double getMovementTime(){ return movementTime; }

    public void setMovementTime(double m) { movementTime = m;}

    public Direction getMovementDirection(){ return movementDirection; }

    public int getSpeed() { return speed; }
}
