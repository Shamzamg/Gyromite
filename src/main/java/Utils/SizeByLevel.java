package Utils;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;


public class SizeByLevel {

    private HashMap<Integer, Point> sizeByLevel = new HashMap<Integer, Point>();

    public SizeByLevel(){
        this.sizeByLevel.put(1, new Point(30,30));
    }

    public HashMap<Integer, Point> getSizeByLevel() {return this.sizeByLevel;}

}
