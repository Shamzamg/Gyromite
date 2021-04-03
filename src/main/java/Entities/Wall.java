package Entities;

import Environment.Environment;
import Utils.Type;

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
