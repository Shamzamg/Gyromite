package Entities;

import Entities.DynamicEntity;
import Environment.Environment;
import Utils.Type;

public class Smick extends DynamicEntity {

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
}
