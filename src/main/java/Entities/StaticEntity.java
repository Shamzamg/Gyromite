package Entities;

import Environment.Environment;
import Utils.Type;

public abstract class StaticEntity extends Entity{
    public StaticEntity(Environment _env) {
        super(_env);
    }
    public boolean isCrushable() { return false; }
    public boolean isSupport() { return true; }
    public boolean isClimbable() { return false; };
}
