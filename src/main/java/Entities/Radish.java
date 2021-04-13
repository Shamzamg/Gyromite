package Entities;

import Environment.Environment;
import Utils.Type;

/**
 * Radish class used as a collectible for the professor
 */
public class Radish extends StaticEntity{

    public Radish(Environment _env) {
        super(_env);
    }

    @Override
    public boolean isClimbable() { return false; }

    @Override
    public boolean isSupport() {
        return true;
    }

    @Override
    public boolean isCrushable() {
        return false;
    }

    @Override
    public Type getType() {
        return Type.RADISH;
    }

}
