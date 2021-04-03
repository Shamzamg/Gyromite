package Entities;

import Environment.Environment;
import Utils.Direction;
import Utils.Type;

import java.awt.*;

public abstract class DynamicEntity extends Entity{

    protected Direction lookingDirection = null;

    public DynamicEntity(Environment _env){
        super(_env);
    }

    public boolean moveDirection(Direction d) {
        return env.moveEntity(this, d);
    }

    public Entity lookDirection(Direction d){ return env.lookDirection(this, d); }

    public void setLookingDirection(Direction lookingDirection){
        this.lookingDirection = lookingDirection;
    }

    public Direction getLookingDirection(){
        return this.lookingDirection;
    }
}
