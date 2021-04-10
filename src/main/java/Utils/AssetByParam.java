package Utils;

import ViewController.ViewController;
import org.apache.commons.collections.map.MultiKeyMap;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class used by the ViewController to render assets easier
 */
public class AssetByParam {

    private MultiKeyMap assetByParam = new MultiKeyMap();

    // icones affichées dans la grille
    private ImageIcon icoProfessorRight;
    private ImageIcon icoProfessorLeft;
    private ImageIcon icoBlueColumnHead;
    private ImageIcon icoBlueColumnBody;
    private ImageIcon icoBlueColumnBottom;
    private ImageIcon icoOrangeColumnHead;
    private ImageIcon icoOrangeColumnBody;
    private ImageIcon icoOrangeColumnBottom;
    private ImageIcon icoVoid;
    private ImageIcon icoDynamite;
    private ImageIcon icoWall;
    private ImageIcon icoRope;
    private ImageIcon icoSmickRight;
    private ImageIcon icoSmickLeft;

    public AssetByParam(){
        loadIcons();
        this.assetByParam.put(Type.PROFESSOR, Direction.RIGHT, icoProfessorRight);
        this.assetByParam.put(Type.PROFESSOR, Direction.DOWN, icoProfessorRight);
        this.assetByParam.put(Type.PROFESSOR, Direction.UP, icoProfessorRight);
        this.assetByParam.put(Type.PROFESSOR, Direction.LEFT, icoProfessorLeft);
        this.assetByParam.put(Type.BLUE_COLUMN, Direction.UP, icoBlueColumnBottom);
        this.assetByParam.put(Type.BLUE_COLUMN, Direction.DOWN, icoBlueColumnHead);
        this.assetByParam.put(Type.BLUE_COLUMN, null, icoBlueColumnBody);
        this.assetByParam.put(Type.ORANGE_COLUMN, Direction.UP, icoOrangeColumnBottom);
        this.assetByParam.put(Type.ORANGE_COLUMN, Direction.DOWN, icoOrangeColumnHead);
        this.assetByParam.put(Type.ORANGE_COLUMN, null, icoOrangeColumnBody);
        this.assetByParam.put(Type.VOID, null, icoVoid);
        this.assetByParam.put(Type.WALL, null, icoWall);
        this.assetByParam.put(Type.ROPE, null, icoRope);
        this.assetByParam.put(Type.DYNAMITE, null, icoDynamite);
        this.assetByParam.put(Type.SMICK, null, icoSmickRight);
        this.assetByParam.put(Type.SMICK, Direction.LEFT, icoSmickLeft);
        this.assetByParam.put(Type.SMICK, Direction.RIGHT, icoSmickRight);
        this.assetByParam.put(Type.SMICK, Direction.UP, icoSmickRight);
        this.assetByParam.put(Type.SMICK, Direction.DOWN, icoSmickLeft);
    }

    private void loadIcons(){
        icoProfessorRight = loadIcon("/assets/professor_right.png");
        icoProfessorLeft = loadIcon("/assets/professor_left.png");
        icoRope = loadIcon("/assets/rope.png");
        icoSmickRight = loadIcon("/assets/smick_right.png");
        icoSmickLeft = loadIcon("/assets/smick_left.png");
        icoWall = loadIcon("/assets/wall.png");
        icoVoid = loadIcon("/assets/void.png");
        icoDynamite = loadIcon("/assets/dynamite.png");
        icoBlueColumnHead = loadIcon("/assets/column_head_blue.png");
        icoBlueColumnBody = loadIcon("/assets/column_body_blue.png");
        icoBlueColumnBottom = loadIcon("/assets/column_bottom_blue.png");
        icoOrangeColumnHead = loadIcon("/assets/column_head_orange.png");
        icoOrangeColumnBody = loadIcon("/assets/column_body_orange.png");
        icoOrangeColumnBottom = loadIcon("/assets/column_bottom_orange.png");
    }

    private ImageIcon loadIcon(String url){
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream(url));
        } catch (IOException ex) {
            Logger.getLogger(ViewController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

        return new ImageIcon(image);
    }

    public MultiKeyMap getAssetByParam() {return this.assetByParam;}

}
