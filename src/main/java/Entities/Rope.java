package Entities;

import Environment.Environment;
import Utils.Type;

/**
 * Rope class, climbable by both Professor and Smicks
 */
public class Rope extends StaticEntity{

    public Rope(Environment _env) {
        super(_env);
    }

    @Override
    public boolean isClimbable() { return true; }

    @Override
    public boolean isSupport() {
        return false;
    }

    @Override
    public boolean isCrushable() {
        return false;
    }

    @Override
    public Type getType() {
        return Type.ROPE;
    }
}
