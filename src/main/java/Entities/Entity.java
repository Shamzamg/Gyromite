package Entities;

import Environment.Environment;
import Utils.Direction;
import Utils.Type;

public abstract class Entity {

    protected Environment env;

    public Entity(Environment _env){ env = _env; }

    public Entity lookDirection(Direction d){ return env.lookDirection(this, d); }

    public abstract boolean isCrushable();

    public abstract boolean isSupport();

    public abstract boolean isClimbable();

    public abstract Type getType();
}
