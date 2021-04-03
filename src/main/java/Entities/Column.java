package Entities;

import Environment.Environment;
import Utils.Type;

public class Column extends DynamicEntity{

    Type type = null;

    public Column(Environment _env) {
        super(_env);
    }

    public Column(Environment _env, Type t) {
        super(_env);
        setType(t);
    }

    @Override
    public boolean isCrushable() {
        return false;
    }

    @Override
    public boolean isSupport() {
        return true;
    }

    @Override
    public boolean isClimbable() {
        return true;
    }

    @Override
    public Type getType() {
        return type;
    }

    public void setType(Type t){
        type = t;
    }
}
