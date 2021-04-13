package Entities;

import Environment.Environment;
import Utils.Direction;
import Utils.Type;

import java.awt.*;

/**
 * Professor class, used only once, can be controlled by the player
 */
public class Professor extends DynamicEntity {

    private Point position;

    private Type type = Type.PROFESSOR;

    public Professor(Environment _env) {
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
    public Type getType() { return type; }

    public void setType(Type t){ this.type = t; }

}
