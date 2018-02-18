package edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.GameMap;

import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gameobjects.Position;

/**
 * Created by johan on 07.10.2017.
 *
 * The map consists of cells that contain a position and a terrain type
 */
public final class Cell {
    private Position location;
    private TerrainType terrainType;

    /**
     * Creates a new instance of a Cell given the x,y coordinates and the TerrainType
     * @param x x coordinate
     * @param y y coordinate
     * @param tt TerrainType at this coordinate
     */
    public Cell(int x, int y,TerrainType tt){
        this(new Position(x, y), tt);
    }

    /**
     * Creates a new instance of a Cell given the x,y coordinates and the TerrainType
     * y
     * @param position Position that this cell represents
     * @param tt TerrainType at this coordinate
     */
    public Cell(Position position,TerrainType tt){
        location = position;
        terrainType = tt;
    }

    /**
     * Returns true if this cell contains walkable terrain
     * @return
     */
    public boolean isWalkable(){
        return  terrainType.isWalkable();
    }

    /**
     * Returns the x coordinate of this cell
     * @return x coordinate
     */
    public int getX(){
        return (int)location.getX();
    }

    /**
     * Returns the y coordinate of this cell
     * @return y coordinate
     */
    public int getY(){
        return (int)location.getY();
    }

    /**
     * Returns a the Position of this cell
     * @return instance of Position
     */
    public Position getPosition(){
        return location;
    }

    public String toString(){
        return location.toString();
    }
}
