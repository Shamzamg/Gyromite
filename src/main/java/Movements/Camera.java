package Movements;

import Entities.Professor;
import Environment.Environment;

import java.awt.*;

import static java.lang.Math.floor;

/**
 * Camera class used to follow Professor movements
 */
public class Camera {

    int renderDistance;
    int levelWidth;
    int levelHeight;
    Environment env;

    public Camera(Environment _env, Point levelDimensions, int renderDistance){
        this.env = _env;
        this.renderDistance = renderDistance;
        this.levelWidth = levelDimensions.y;
        this.levelHeight = levelDimensions.x;
    }

    public Point getRenderCoordinates(){

        int x=0; int y=0;
        Point prof = env.getHashMap().get(env.getProfessor());

        //Partie verticale
        if(prof.x - floor(renderDistance/2) >=0){
            if(prof.x + floor(renderDistance/2) <= levelHeight){
                x = prof.x - (int) floor(renderDistance/2);
            }
            else{
                x = levelHeight - renderDistance;
            }
        }
        if(prof.x - floor(renderDistance/2) < 0){
            x = 0;
        }

        //Partie horizontale
        if(prof.y - floor(renderDistance/2) >= 0){
            if(prof.y + floor(renderDistance/2) <= levelWidth){
                y = prof.y - (int) floor(renderDistance/2);
            }
            else{
                y = levelWidth - renderDistance;
            }
        }
        if(prof.y - floor(renderDistance/2) < 0){
            y = 0;
        }

        return new Point(x,y);
    }

}
