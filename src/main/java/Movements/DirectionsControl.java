package Movements;

import Entities.Column;
import Entities.DynamicEntity;
import Entities.Entity;
import Entities.Professor;
import Utils.Direction;
import Utils.Type;

import java.util.ArrayList;

public class DirectionsControl extends MovingOperator{
    private Direction currentDirection;
    private Type type;

    private static DirectionsControl c3d;

    public DirectionsControl(){
        super();
    }

    public static void resetInstance(){
        c3d = null;
    }

    public static DirectionsControl getInstance(){
        if(c3d == null){
            c3d = new DirectionsControl();
        }
        return c3d;
    }

    public void setCurrentDirection(Direction _currentDirection, Type t){
        currentDirection = _currentDirection;
        type = t;
    }

    public boolean realizeMovement(){
        boolean ret = false;
        for(DynamicEntity e: lstDynamicEntities){
            if(currentDirection != null){
                if(e.getType() == type){
                    //if the professor is moving
                    if(type == Type.PROFESSOR){
                        switch(currentDirection){
                            case LEFT:
                            case RIGHT:
                                if(e.moveDirection(currentDirection))
                                    ret = true;
                                break;

                            case UP:
                                Entity eBas = e.lookDirection(Direction.DOWN);
                                if(eBas != null && (eBas.isSupport() || eBas.isClimbable())){
                                    if(e.moveDirection(Direction.UP)){
                                        ret = true;
                                    }
                                }
                                break;
                            case DOWN:
                                Entity eBas2 = e.lookDirection(Direction.DOWN);
                                if(eBas2 != null && (eBas2.isClimbable()) && !(eBas2.isSupport())){
                                    if(e.moveDirection(Direction.DOWN)){
                                        ret = true;
                                    }
                                }
                                break;
                        }
                    }
                    //if a column is moving
                    if(e instanceof Column){
                        switch(currentDirection){
                            case UP:
                                Entity eTop = e.lookDirection(Direction.UP);
                                if(eTop != null && !(eTop.isSupport()) && !(eTop instanceof Column)){
                                    if(e.moveDirection(Direction.UP)){
                                        Entity temp = e.lookDirection(Direction.DOWN); //temporary void above the column bottom
                                        Entity eBas = temp.lookDirection(Direction.DOWN); //void looking for the column above
                                        while((eBas instanceof Column) && ((Column) eBas).moveDirection(Direction.UP)){
                                            temp = eBas.lookDirection(Direction.DOWN);
                                            eBas = temp.lookDirection(Direction.DOWN);
                                        }
                                        ret = true;
                                    }
                                }
                                break;
                            case DOWN:
                                //the entity below e
                                Entity eBas = e.lookDirection(Direction.DOWN);
                                if(eBas != null && !(eBas.isSupport()) && !(eBas instanceof Column)){
                                    if(e.moveDirection(Direction.DOWN)){
                                        Entity temp = e.lookDirection(Direction.UP); //temporary void above the column bottom
                                        Entity eTop2 = temp.lookDirection(Direction.UP); //void looking for the column above

                                        //move every part of the column
                                        while((eTop2 instanceof Column) && ((Column) eTop2).moveDirection(Direction.DOWN)){
                                            temp = eTop2.lookDirection(Direction.UP);
                                            eTop2 = temp.lookDirection(Direction.UP);
                                        }
                                        ret = true;
                                    }
                                }
                                break;
                        }
                    }
                }
            }
        }
        return ret;
    }

    public void reset() {
        currentDirection = null;
        type = null;
    }

    public void set(Direction d, Type t){
        this.currentDirection = d;
        this.type = t;
    }

    public Direction getCurrentDirection(){ return this.currentDirection; }

    public Type getType(){ return this.type; }

}
