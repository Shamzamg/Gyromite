package Movements;

import Entities.DynamicEntity;
import Entities.Entity;
import Utils.Direction;
import Utils.Type;

/**
 * Gravity class is a MovingOperator that pulls entities
 * we had applied Gravity
 */
public class Gravity extends MovingOperator {

    @Override
    public boolean realizeMovement() {
        boolean ret = false;

        for (DynamicEntity e : lstDynamicEntities) {
            Entity eBas = e.lookDirection(Direction.DOWN);
            if (eBas == null || (eBas != null && !(eBas.isSupport() || eBas.isClimbable()))) {
                if (e.moveDirection(Direction.DOWN))
                    ret = true;
            }
        }

        return ret;
    }

    @Override
    public Type getType() {
        return null;
    }

    @Override
    public Direction getCurrentDirection() {
        return null;
    }
}
