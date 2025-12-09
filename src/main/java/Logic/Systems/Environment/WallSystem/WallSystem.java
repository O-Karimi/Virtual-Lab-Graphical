package Logic.Systems.Environment.WallSystem;

import Logic.Utils.NDirection;
import Logic.Utils.Point;

import java.util.ArrayList;
import java.util.List;

public class WallSystem {
    private List<Wall> wallList =  new ArrayList<>();

    public void addWall(Wall wall){
        wallList.add(wall);
    }

    public void addRWall(double x){
        Point point = new Point(x,0);
        NDirection nd = new NDirection(-1,0);
        Wall wall = new Wall(point, nd);
        this.addWall(wall);
    }

    public void addLWall(double x){
        Point point = new Point(x,0);
        NDirection nd = new NDirection(1,0);
        Wall wall = new Wall(point, nd);
        this.addWall(wall);
    }

    public void addUWall(double y){
        Point point = new Point(0,y);
        NDirection nd = new NDirection(0,1);
        Wall wall = new Wall(point, nd);
        this.addWall(wall);
    }

    public void addDWall(double y){
        Point point = new Point(0,y);
        NDirection nd = new NDirection(0,-1);
        Wall wall = new Wall(point, nd);
        this.addWall(wall);
    }

    public Wall getWall(int index){
        return wallList.get(index);
    }
    public List<Wall> getWalls(){
        return wallList;
    }
}
