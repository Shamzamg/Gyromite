package Environment;

import Entities.*;
import Entities.Void;
import Movements.Camera;
import Movements.Model;
import Movements.DirectionsControl;
import Movements.Gravity;
import Utils.Direction;
import Utils.GameState;
import Utils.Type;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class Environment {

    // count horizontal and vertical movements (1 max at each time-step)
    private HashMap<Entity, Integer> cmptDeplH = new HashMap<Entity, Integer>();
    private HashMap<Entity, Integer> cmptDeplV = new HashMap<Entity, Integer>();

    private Professor professor;

    private HashMap<Entity, Point> hashMap = new HashMap<Entity, Point>(); // allows to get position by entity
    private Entity [][] map; //allows to get an entity by its coordinates
    private Entity [][] bufferMap;

    private Point levelDimensions;
    private Camera camera;
    private GameState gamestate = GameState.START;

    private Model model = new Model(this);
    private int nbDynamites = 0;
    private int currentLevel = 1;
    private int size;
    private int timer = 999;
    private int score = 0;
    private Gravity gravity = null;
    //used to load the next level without bugs
    private boolean firstLoaded = false;

    public Environment(int size){
        this.size = size;
        this.levelDimensions = getLevelDimensions(currentLevel);

        this.map = new Entity[levelDimensions.x][levelDimensions.y];
        this.bufferMap = new Entity[levelDimensions.x][levelDimensions.y];

        loadLevel(1);
        //keep a state of the initial map
        initBufferMap();
        //init the camera
        this.camera = new Camera(this, levelDimensions, size);
    }

    private Point getLevelDimensions(int levelIndex) {

        System.out.println("Current get level dimension: " + levelIndex);

        File level = new File("src/main/resources/maps/level"+levelIndex+".txt");

        int levelWidth = 0;
        int levelHeight = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(level));
            String line = null;
            int i=0;
            while((line = reader.readLine()) != null) {
                String[] singles = line.split(" ");
                levelWidth = singles.length;
                for(int j=0;j<singles.length;j++) {
                }
                i++;
            }
            levelHeight = i;

        } catch(IOException e) {
            System.err.println("Load error"+levelIndex);
        }

        return new Point(levelHeight, levelWidth);
    }

    public void initBufferMap(){
        for(int i=0;i<levelDimensions.x;i++){
            for(int j=0;j<levelDimensions.y;j++){
                //tout ce qui est suceptible de changer à l'écran
                if(!(map[i][j] instanceof DynamicEntity || map[i][j] instanceof Dynamite || map[i][j] instanceof Column))
                    bufferMap[i][j] = map[i][j];
                else bufferMap[i][j] = new Void(this);
            }
        }
    }

    public void resetCmptDepl() {
        cmptDeplH.clear();
        cmptDeplV.clear();
    }

    public void start(long _pause) {
        model.start(_pause);
    }

    public Entity getMap(int i, int j){ return map[i][j]; }

    public Professor getProfessor(){ return this.professor; }

    public void setColumnLookingDirections(){
        for(int i=0;i<levelDimensions.x;i++){
            for(int j=0;j<levelDimensions.y;j++){
                //if the map item is a Column
                if(map[i][j] instanceof Column){
                    //if the column is at the border of the map, it is either a top or a bottom
                    if(i == 0)
                        ((Column) map[i][j]).setLookingDirection(Direction.DOWN);

                    if(i == levelDimensions.x-1)
                        ((Column) map[i][j]).setLookingDirection(Direction.UP);

                    else{
                        //if it is a column above and there's still a column under
                        if(map[i-1][j] instanceof Column){
                            if(map[i+1][j] instanceof Column)
                                ((Column) map[i][j]).setLookingDirection(null);
                        }

                        if(!(map[i-1][j] instanceof Column))
                            ((Column) map[i][j]).setLookingDirection(Direction.DOWN);

                        if(!(map[i+1][j] instanceof Column))
                            ((Column) map[i][j]).setLookingDirection(Direction.UP);

                    }
                }
            }
        }
    }

    public void loadLevel(int levelIndex){

        File level = new File("src/main/resources/maps/level"+levelIndex+".txt");

        DirectionsControl.resetInstance();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(level));
            String line = null;
            int i=0;
            while((line = reader.readLine()) != null) {
                String[] singles = line.split(" ");
                for(int j=0;j<singles.length;j++) {
                    switch(singles[j]) {
                        case "1":
                            this.map[i][j] = new Wall(this);
                            this.hashMap.put(this.map[i][j], new Point(i,j));
                            break;
                        case "0":
                            this.map[i][j] = new Void(this);
                            this.hashMap.put(this.map[i][j], new Point(i,j));
                            break;
                        case "R":
                            this.map[i][j] = new Rope(this);
                            this.hashMap.put(this.map[i][j], new Point(i,j));
                            break;
                        case "B":
                            this.map[i][j] = new Column(this, Type.BLUE_COLUMN);
                            this.hashMap.put(this.map[i][j], new Point(i,j));
                            DirectionsControl.getInstance().addDynamicEntity((DynamicEntity) map[i][j]);
                            break;
                        case "O":
                            this.map[i][j] = new Column(this, Type.ORANGE_COLUMN);
                            this.hashMap.put(this.map[i][j], new Point(i,j));
                            DirectionsControl.getInstance().addDynamicEntity((DynamicEntity) map[i][j]);
                            break;
                        case "D":
                            this.map[i][j] = new Dynamite(this);
                            this.hashMap.put(this.map[i][j], new Point(i,j));
                            this.nbDynamites++;
                            break;
                        case "P":
                            this.map[i][j] = new Professor(this);
                            this.hashMap.put(this.map[i][j], new Point(i,j));
                            this.professor = (Professor) this.map[i][j];
                            break;
                    }
                }
                i++;
            }

            setColumnLookingDirections();

            if(gravity == null) gravity = new Gravity();

            else gravity.resetMovingOperator();

            gravity.addDynamicEntity(professor);

            model.add(gravity);

            DirectionsControl.getInstance().addDynamicEntity(professor);

            model.add(DirectionsControl.getInstance());

        } catch(IOException e) {
            System.err.println("Load error"+levelIndex);
        }
    }

    private boolean inMap(Point p){
        return p.x >=0 && p.x < levelDimensions.x && p.y >= 0 && p.y <= levelDimensions.y;
    }

    private Entity objectAtPosition(Point p){
        Entity ret = null;

        if(inMap(p)){
            ret = map[p.x][p.y];
        }

        return ret;
    }

    public Entity lookDirection(Entity e, Direction d){
        Point position = hashMap.get(e);
        return objectAtPosition(calculateTargetPoint(position, d));
    }

    public Point calculateTargetPoint(Point current, Direction d){
        Point target = null;

        switch(d){
            case UP: target = new Point(current.x-1, current.y); break;
            case DOWN: target = new Point(current.x+1, current.y); break;
            case LEFT: target = new Point(current.x, current.y-1); break;
            case RIGHT: target = new Point(current.x, current.y+1); break;
        }

        return target;
    }

    public boolean moveEntity(Entity e, Direction d){
        boolean ret = false;

        Point current = hashMap.get(e);
        Point target = calculateTargetPoint(current, d);

        //if target isn't on grid and isn't a support
        if(inMap(target) && !(objectAtPosition(target).isSupport())){
            switch(d){
                case DOWN:
                case UP:
                    if (cmptDeplV.get(e) == null) {
                        cmptDeplV.put(e, 1);

                        ret = true;
                    }
                    break;
                case LEFT:
                case RIGHT:
                    if (cmptDeplH.get(e) == null) {
                        cmptDeplH.put(e, 1);
                        ret = true;

                    }
                    break;
            }
        }

        if(ret){
            //here to change the lookingDirection (the sprite) of the moving entity
            ((DynamicEntity) e).setLookingDirection(d);
            moveEntity(current,target, e, d);
        }

        return ret;
    }

    private void resetVariables(){
        nbDynamites = 0;
        score = 0;
        timer = 999;
        cmptDeplH = new HashMap<Entity, Integer>();
        cmptDeplV = new HashMap<Entity, Integer>();

        professor = null;

        hashMap = new HashMap<Entity, Point>();
        map = null;
        bufferMap = null;

        levelDimensions = null;
        camera = null;

    }

    private void loadNextLevel(int nextlevel){

        //check if there is a next level, either way the game is finished
        File level = new File("src/main/resources/maps/level"+nextlevel+".txt");

        if(!level.exists()) {
            setGameState(GameState.FINISH);
            return;
        }

        //if there is a next level

        currentLevel = nextlevel;

        resetVariables();

        this.model.loadNextLevel();

        this.levelDimensions = getLevelDimensions(currentLevel);

        this.map = new Entity[levelDimensions.x][levelDimensions.y];
        this.bufferMap = new Entity[levelDimensions.x][levelDimensions.y];

        loadLevel(currentLevel);
        //keep a state of the initial map
        initBufferMap();
        //init the camera
        this.camera = new Camera(this, levelDimensions, size);

        this.model.replay();

        firstLoaded = true;
    }

    private void moveEntity(Point current, Point target, Entity e, Direction d){
        firstLoaded = false;

        //if the professor is about to pick-up a dynamite
        if(map[current.x][current.y] instanceof Professor){
            if(map[target.x][target.y] instanceof Dynamite){
                nbDynamites--;
                score+=25;

                //if we picked up all the dynamites, we load up the next level
                if(nbDynamites == 0){
                    loadNextLevel(currentLevel + 1);
                }
            }
        }

        //if a column is going to crush for example
        //if a support moves towards a crushable
        if(map[current.x][current.y].isSupport()){
            if(map[target.x][target.y].isCrushable()){

                Point nextTarget = calculateTargetPoint(target, d);
                //if the next target is a support , it will crush it
                if(map[nextTarget.x][nextTarget.y].isSupport()){
                    map[current.x][current.y] = bufferMap[current.x][current.y];

                    if(map[target.x][target.y] instanceof Professor){
                        setGameState(GameState.FINISH);
                    }
                    //l'endroit visé devient l'entite que l'on deplace
                    map[target.x][target.y] = e;

                    //on update cela sur la hashMap
                    hashMap.put(map[current.x][current.y], current);
                    hashMap.put(e, target);
                    return;
                }
                //else if next target is not a support, it will move the entity pushed

                map[current.x][current.y] = bufferMap[current.x][current.y];
                Entity next = map[target.x][target.y];
                //l'endroit visé devient l'entite que l'on deplace
                map[target.x][target.y] = e;
                map[nextTarget.x][nextTarget.y] = next;

                //on update cela sur la hashMap
                hashMap.put(map[current.x][current.y], current);
                hashMap.put(e, target);
                hashMap.put(next, nextTarget);
                return;
            }
        }

        //on change l'endroit actuel par sa valeur initiale
        map[current.x][current.y] = bufferMap[current.x][current.y];

        if(!firstLoaded){
            //l'endroit visé devient l'entite que l'on deplace
            map[target.x][target.y] = e;

            //on update cela sur la hashMap
            hashMap.put(map[current.x][current.y], current);
            hashMap.put(e, target);
        }
    }

    public Model getModel(){ return this.model; }

    public Camera getCamera(){ return this.camera;}

    public HashMap<Entity, Point> getHashMap() { return this.hashMap; }

    public int getTimer(){ return this.timer; }

    public void setTimer(int timer ){ this.timer = timer;}

    public int getScore(){ return this.score; }

    public GameState getGameState(){ return this.gamestate; }

    //called by the viewcontroller
    public void switchGameState(){
        switch(gamestate){
            case START:
                setGameState(GameState.PLAY);
                break;
            case FINISH:
                loadNextLevel(1);
                setGameState(GameState.START);
                break;
            //called by the model if the player runs out of time
            case PLAY:
                setGameState(GameState.FINISH);
                break;
        }
    }

    //only works at the beginning to start the game
    private void setGameState(GameState gs) {
        gamestate = gs;
    }
}
