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
    public Type getType() {
        return Type.PROFESSOR;
    }

}
