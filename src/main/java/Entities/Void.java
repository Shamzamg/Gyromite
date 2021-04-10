package Entities;

import Environment.Environment;
import Utils.Type;

/**
 * Void class used to fill the map when there's nothing
 */
public class Void extends StaticEntity{
    public Void(Environment _env) {
        super(_env);
    }

    @Override
    public boolean isClimbable() {
        return false;
    }

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
        return Type.VOID;
    }
}
