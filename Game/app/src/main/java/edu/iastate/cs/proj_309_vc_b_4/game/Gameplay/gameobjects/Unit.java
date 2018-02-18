package edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gameobjects;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import java.util.ArrayList;

import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gamelogic.DamageTypes;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gamelogic.GameLogic;
import edu.iastate.cs.proj_309_vc_b_4.game.R;

/**
 * Created by Alex on 10/21/2017.
 *
 * Base class for Units
 */

public class Unit implements GameObject {

    private GameLogic game;

    private Position position;
    private float positionInPath;
    public int ID;
    private int health;
    private int speed;
    private boolean isFlying;
    private boolean isBurrowing;
    private DamageTypes unitType;
    private ArrayList<Position> path = new ArrayList<>();
    private int killReward;
    private boolean rewardCollected = false;

    /**
     * @param path
     * @param ID
     * @param health
     * @param unitType Basic unit template for units in game
     */
    public Unit(GameLogic game, ArrayList<Position> path, int ID, int health, int speed, DamageTypes unitType, int killReward) {
        this.game = game;
        this.health = health;
        this.speed = speed;
        this.ID = ID;
        if(path!=null&&path.size()>0){
            this.position = path.get(0);
        } else {
            this.position = null;
        }
        this.unitType = unitType;
        positionInPath = 0;
        this.killReward = killReward;
        this.path = path;
    }

    /**
     *
     * @param game
     * @param path
     * @param ID
     * @param health
     * @param speed
     * @param unitType
     * @param killReward
     * @param isFlying
     * @param isBurrowing
     * allows units to be set as burrowing or flying
     */
    public Unit(GameLogic game, ArrayList<Position> path, int ID, int health, int speed, DamageTypes unitType, int killReward, boolean isFlying, boolean isBurrowing) {
        this(game, path, ID, health, speed, unitType, killReward);
        this.isFlying = isFlying;
        this.isBurrowing = isBurrowing;
    }


    /**
     *
     * @param damage amount of damage being dealt to the unit
     * @param type type of damage being dealt to the unit. This damage types is compared to the damage type the unit is resistant to. If the unit is resistant the
     *             damage dealt is halved
     */
    public void calculate_Damage(int damage, DamageTypes type) {
        if (type == unitType) {
            damage = damage / 2;
        }
        if (health - damage <= 0 && isAlive()) {
            game.addToScore(killReward);
        }
        health = health - damage;
    }

    /**
     * updates the units position based on its speed while following the path
     */
    @Override
    public void update() {
        if (isAlive() && path != null) {
            positionInPath += speed * 0.001;
            position = path.get((int) positionInPath);
            //reached the base
            if ((int) positionInPath >= path.size() - 1) {
                health = 0;
            } else {

                int dy, dx;
                float x, y, nx, ny;
                x = position.getX();
                y = position.getY();

                nx = path.get((int) positionInPath + 1).getX();
                ny = path.get((int) positionInPath + 1).getY();

                dx = (int) nx - (int) x;
                dy = (int) ny - (int) y;

                x = x + dx * getProgress();
                y = y + dy * getProgress();

                position = new Position(x, y);
            }
        }
    }

    /**
     * kills the unit by setting it's health to zero
     */
    public void kill(){
        health = 0;
    }

    /**
     *
     * @return unit's current position on screen
     */
    public Position getPosition() {
        return position;
    }

    /**
     *
     * @return if the unit's health is above zero if it is then it's alive
     */
    public boolean isAlive() {
        return health > 0;
    }

    /**
     *
     * @return if the unit is flying
     */
    public boolean isFlying() {
        return isFlying;
    }

    /**
     *
     * @return if the unit is burrowing
     */
    public boolean isBurrowing() {
        return isBurrowing;
    }

    /**
     *
     * @return unit's progress along the path
     */

    public float getProgress() {
        return positionInPath % 1;
    }

    /**
     * sets unit's referenced GameLogic
     * @param game
     */
    public void setGame(GameLogic game){
        this.game =game;
    }

    /**
     * sets unit's path to the provided path
     * @param path
     */
    public void setPath(ArrayList<Position> path){
        this.path = path;
    }

    /**
     * sets the unit's position
     * @param position
     */
    public void setPosition(Position position){
        this.position = position.hardCopy();
    }

    public Unit hardCopy(){
        return new Unit(game, path, ID, health, speed, unitType, killReward,isFlying,isBurrowing);
    }

    /**
     *
     * @return unit's id which is used to identify what type and race it is
     */
    public int getID(){
        return ID;
    }

}
