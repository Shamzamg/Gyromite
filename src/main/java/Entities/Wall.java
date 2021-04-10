package Entities;

import Environment.Environment;
import Utils.Type;

/**
 * Wall class used all around the map and also as a floor
 */
public class Wall extends StaticEntity{

    public Wall(Environment _env) {
        super(_env);
    }

    @Override
    public boolean isClimbable() {
        return false;
    }

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
        return Type.WALL;
    }
}
