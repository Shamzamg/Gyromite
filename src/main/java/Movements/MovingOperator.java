package Movements;

import Entities.DynamicEntity;
import Utils.Direction;
import Utils.Type;

import javax.management.DynamicMBean;
import java.util.ArrayList;

public abstract class MovingOperator {
    protected ArrayList<DynamicEntity> lstDynamicEntities = new ArrayList<DynamicEntity>();
    public abstract boolean realizeMovement();
    public abstract Type getType();
    public abstract Direction getCurrentDirection();
    public void addDynamicEntity(DynamicEntity de) {lstDynamicEntities.add(de);};
    public void resetMovingOperator(){lstDynamicEntities = new ArrayList<DynamicEntity>();}
}
