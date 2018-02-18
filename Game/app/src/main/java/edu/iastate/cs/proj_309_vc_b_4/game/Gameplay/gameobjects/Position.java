package edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gameobjects;

import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.GameMap.Map;

/**
 * Created by Joe on 10/30/2017.
 *
 * The Position class is used to store positions in the game
 */
public class Position {

    private float x,y;

    /**
     * Creates a new Position
     * @param x x coordinate of the new position
     * @param y y coordinate of the new position
     */
    public Position(float x, float y){
        set(x,y);
    }

    /**
     * Returns the x coordinate of this position
     * @return x coordinate
     */
    public float getX(){
        return x;
    }

    /**
     * Returns the y coordinate of this position
     * @return y coordinate
     */
    public float getY() {
        return y;
    }

    /**
     * Sets the x and y coordinates of this position
     * @param x x coordinate
     * @param y y coordinate
     */
    public void set(float x,float y){
        this.x=x;
        this.y=y;
    }

    /**
     * Returns the distance between two positions
     * @param a position 1
     * @param b position 2
     * @return distance between position 1 and 2
     */
    public static float distance(Position a,Position b){
        return (float)Math.sqrt(pow2(a.getX() - b.getX()) + pow2(Math.abs(a.getY() - b.getY())));
    }

    private static float pow2(float b){
        return b*b;
    }

    /**
     * Returns a hard copy of this position
     * @return new Position that is equal to the current position
     */
    public Position hardCopy(){
        return new Position(x, y);
    }

    public String toString(){
        return "{"+x +"," + y + "}";
    }

    public int hashCode() {
        return Math.abs((int)x * 31 + (int)y);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj!=null) {
            if (obj.getClass() == Position.class) {
                Position t = (Position) obj;
                return t.getY() == y && t.getX() == x;
            }
        }
        return false;
    }

    /**
     * Returns if the whole parts of the position coordinates are equal
     * @param pos position to compare with
     * @return true if the whole coordinate parts are equal
     */
    public boolean roughlyEquals(Position pos){
        if(pos!=null){
            int x1,y1,x2,y2;
            x1 = (int) x;
            x2 = (int)pos.getX();
            y1 = (int) y;
            y2 = (int) pos.getY();
            return x1==x2 && y1==y2;
        }
        return false;
    }

    /**
     * computes the neirbourgh position if possible.
     * If meeting a border the corresponding coordinate will remain unchanged.
     * @param dx the difference in x
     * @param dy the difference in y
     * @return the neighbour position.
     */
    public Position neighbourgh(int dx, int dy){
        int newX = (int)Math.min(Math.max(0,x+dx), Map.WIDTH);
        int newY = (int)Math.min(Math.max(0,y+dy), Map.HEIGHT);
        return new Position(newX,newY);

    }

}
